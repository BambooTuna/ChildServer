import sbt.Keys._
import sbt._
import com.lucidchart.sbt.scalafmt.ScalafmtCorePlugin.autoImport._
import com.typesafe.sbt.SbtNativePackager.autoImport.{maintainer, packageName}
import com.typesafe.sbt.packager.archetypes.scripts.BashStartScriptPlugin.autoImport.bashScriptDefines
import com.typesafe.sbt.packager.docker.DockerPlugin.autoImport._


object Settings {

  lazy val commonSettings = Seq(
    resolvers += "Maven Repo on github" at "https://BambooTuna.github.io/CryptoExchangeAPI",
    libraryDependencies ++= Seq(
      Circe.core,
      Circe.generic,
      Circe.parser,
      Akka.http,
      Akka.stream,
      Akka.slf4j,
      Akka.contrib,
      Akka.`akka-http-crice`,
      Logback.classic,
      LogstashLogbackEncoder.encoder,
      Config.core,
      Kamon.datadog,
      Monix.version,
      MySQLConnectorJava.version,
      Redis.client,
      "com.github.BambooTuna" %% "cryptoexchangeapi" % "1.0.0-SNAPSHOT"
    ) ++ `doobie-quill`.all,
    scalafmtOnCompile in Compile := true,
    scalafmtTestOnCompile in Compile := true
  )

  lazy val packageSetting = Seq(
    organization := "com.github.BambooTuna",
    scalaVersion := "2.12.8",
    version := "1.0.7-SNAPSHOT",
    name := "ChildServer",
    publishTo := Some(Resolver.file("ChildServer",file("."))(Patterns(true, Resolver.mavenStyleBasePattern)))
  )

}
