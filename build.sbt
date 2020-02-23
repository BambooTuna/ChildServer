import Settings._

lazy val boot = (project in file("boot"))
  .enablePlugins(JavaAppPackaging, AshScriptPlugin, DockerPlugin)
  .settings(commonSettings)
  .settings(packageSetting)
  .settings(
    resolvers ++= Seq(
      "Maven Repo on github" at "https://BambooTuna.github.io/CryptoExchangeAPI"
    ),
    libraryDependencies ++= Seq(
      "com.github.BambooTuna" %% "cryptoexchangeapi" % "1.0.0-SNAPSHOT"
    )
  )


lazy val root =
  (project in file("."))
    .aggregate(boot)
    .settings(packageSetting)

