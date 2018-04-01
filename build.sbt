import Dependencies.{ScalaTest, _}

lazy val commonSettings = Seq(
  organization := "com.github.alcereo",
  version := "0.1-SNAPSHOT",
  scalaVersion := "2.12.5"
)

val domain = (project in file("domain"))
  .settings(
    name := "process-engine-domain",
    commonSettings,
    libraryDependencies ++= Seq(
      Slf4J.api,
      Logback.classic       % Test,
      ScalaTest.scalactic   % Test,
      ScalaTest.scalaTest   % Test,
      ScalaTest.mockito     % Test,
      ScalaCheck.scalaCheck % Test
    )
  )

val `process-engine` = (project in file("."))
  .settings(
    name := "process-engine",
    commonSettings
  ).aggregate(domain)