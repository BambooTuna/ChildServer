import Settings._

lazy val boot = (project in file("boot"))
  .enablePlugins(JavaAppPackaging, AshScriptPlugin, DockerPlugin)
  .settings(commonSettings)
  .settings(
    organization := "com.github.BambooTuna",
    scalaVersion := "2.12.8",
    version := "1.0.0-SNAPSHOT",
    name := "ChildrenServer",
    publishTo := Some(Resolver.file("ChildrenServer",file("."))(Patterns(true, Resolver.mavenStyleBasePattern)))
  )
  .settings(
    resolvers ++= Seq(
      "Maven Repo on github" at "https://BambooTuna.github.io/CryptoExchangeAPI",
      "Maven Repo on github" at "https://BambooTuna.github.io/AuthenticationRouterSupport"
    ),
    libraryDependencies ++= Seq(
      "com.github.BambooTuna" %% "cryptoexchangeapi" % "1.0.0-SNAPSHOT",
      "com.github.BambooTuna" %% "authenticationroutersupport" % "1.0.0-SNAPSHOT"
    )
  )

lazy val root =
  (project in file("."))
    .aggregate(boot)
