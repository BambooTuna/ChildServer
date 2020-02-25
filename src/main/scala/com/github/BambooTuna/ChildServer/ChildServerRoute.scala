package com.github.BambooTuna.ChildServer

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpMethods.POST
import akka.http.scaladsl.model.{
  ContentTypes,
  HttpEntity,
  HttpRequest,
  HttpResponse,
  StatusCodes,
  Uri
}
import akka.http.scaladsl.server.{Directive, Route}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.github.BambooTuna.CryptoExchangeAPI.core.http.{
  HttpInternalException,
  HttpInterpreter,
  HttpInterpreterResponse
}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.syntax._
import io.circe.generic.auto._
import monix.execution.Scheduler.Implicits.global

import scala.concurrent.Future
import scala.util.{Failure, Success}

trait ChildServerRoute extends FailFastCirceSupport {

  type QueryP[Q] = Directive[Q] => Route
  val parentSetting: ParentServerSetting
  val childSetting: ChildServerSetting

  var rebootCount = 0
  def f: String => Future[Unit]

  def customCommand: QueryP[Unit] = _ {
    entity(as[CustomCommandRequestJson]) { json =>
      val result =
        CustomCommandResponseJson.create(rebootCount)(childSetting, json)
      onComplete(f(json.command).flatMap(_ => Future.successful(result))) {
        case Success(Success(value)) =>
          successResponse(value.asJson.noSpaces)
        case Success(Failure(exception)) =>
          forbiddenResponse(exception.getMessage)
        case Failure(exception) =>
          failureResponse(exception.getMessage)
      }
    }
  }

  def sendRegisterRequest(implicit system: ActorSystem,
                          materializer: ActorMaterializer)
    : Future[Either[HttpInternalException, HttpInterpreterResponse[String]]] = {
    val request = HttpRequest(
      method = POST,
      uri = Uri.from(
        scheme = "http",
        host = parentSetting.host,
        port = parentSetting.port,
        path = "/server/register"
      ),
      entity =
        HttpEntity(ContentTypes.`application/json`,
                   RegisterRequestJson.create(childSetting).asJson.noSpaces)
    )
    HttpInterpreter
      .runRequest[String](request)(HttpInterpreter.checkStatusCode())
      .value
      .runToFuture
  }

  protected def successResponse(response: String) =
    complete(
      HttpResponse(status = StatusCodes.OK,
                   entity =
                     HttpEntity(ContentTypes.`application/json`, response)))

  protected def failureResponse(errorMessage: String) =
    complete(
      HttpResponse(status = StatusCodes.BadRequest,
                   entity = HttpEntity(ContentTypes.`application/json`,
                                       s"""{"error":"$errorMessage"}""")))

  protected def forbiddenResponse(errorMessage: String) =
    complete(
      HttpResponse(status = StatusCodes.Forbidden,
                   entity = HttpEntity(ContentTypes.`application/json`,
                                       s"""{"error":"$errorMessage"}""")))

}
