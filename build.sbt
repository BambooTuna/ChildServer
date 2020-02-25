import Settings._

lazy val root =
  (project in file("."))
    .enablePlugins(JavaAppPackaging, AshScriptPlugin, DockerPlugin)
    .settings(commonSettings)
    .settings(
      resolvers ++= Seq(
        "Maven Repo on github" at "https://BambooTuna.github.io/CryptoExchangeAPI"
      ),
      libraryDependencies ++= Seq(
        "com.github.BambooTuna" %% "cryptoexchangeapi" % "1.0.0-SNAPSHOT"
      )
    )
    .settings(packageSetting)

