package com.github.BambooTuna.ChildrenServer

case class ChildrenServerSetting(port: Int,
                                 name: String,
                                 details: String,
                                 token: String,
                                 startedTimeStamp: Long)

object ChildrenServerSetting {
  def create(port: Int, name: String, details: String): ChildrenServerSetting =
    ChildrenServerSetting(
      port = port,
      name = name,
      details = details,
      token = java.util.UUID.randomUUID.toString.replaceAll("-", ""),
      startedTimeStamp = java.time.Instant.now().getEpochSecond
    )
}
