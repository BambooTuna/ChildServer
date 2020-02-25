package com.github.BambooTuna.ChildServer

case class ChildServerSetting(port: Int,
                              name: String,
                              details: String,
                              token: String,
                              startedTimeStamp: Long)

object ChildServerSetting {
  def create(port: Int, name: String, details: String): ChildServerSetting =
    ChildServerSetting(
      port = port,
      name = name,
      details = details,
      token = java.util.UUID.randomUUID.toString.replaceAll("-", ""),
      startedTimeStamp = java.time.Instant.now().getEpochSecond
    )
}
