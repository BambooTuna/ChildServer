package com.github.BambooTuna.ChildrenServer

case class RegisterRequestJson(port: Int,
                               name: String,
                               details: String,
                               token: String)

object RegisterRequestJson {
  def create(setting: ChildrenServerSetting): RegisterRequestJson =
    RegisterRequestJson(
      setting.port,
      setting.name,
      setting.details,
      setting.token
    )
}
