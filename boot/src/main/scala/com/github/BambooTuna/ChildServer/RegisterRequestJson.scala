package com.github.BambooTuna.ChildServer

case class RegisterRequestJson(port: Int,
                               name: String,
                               details: String,
                               token: String)

object RegisterRequestJson {
  def create(setting: ChildServerSetting): RegisterRequestJson =
    RegisterRequestJson(
      setting.port,
      setting.name,
      setting.details,
      setting.token
    )
}
