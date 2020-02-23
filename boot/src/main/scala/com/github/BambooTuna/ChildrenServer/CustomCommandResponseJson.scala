package com.github.BambooTuna.ChildrenServer

import scala.util.Try

case class CustomCommandResponseJson(reboot_count: Int,
                                     elapsed_startup_time: Long,
                                     requestCommand: String)

object CustomCommandResponseJson {
  def create(reboot_count: Int)(
      setting: ChildrenServerSetting,
      json: CustomCommandRequestJson): Try[CustomCommandResponseJson] = Try {
    require(setting.token == json.token, "Token is not match")
    CustomCommandResponseJson(
      reboot_count,
      java.time.Instant.now().getEpochSecond - setting.startedTimeStamp,
      json.command
    )
  }
}
