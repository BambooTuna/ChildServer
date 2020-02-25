import Settings._

lazy val root =
  (project in file("."))
    .enablePlugins(JavaAppPackaging, AshScriptPlugin, DockerPlugin)
    .settings(commonSettings)
    .settings(packageSetting)

