ThisBuild / scalaVersion := "2.13.18"
ThisBuild / organization := "io.github.lelik112"
ThisBuild / version      := "0.1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .settings(
    name := "scala-backend-playbook",
    libraryDependencies ++= Seq(
      "io.circe"     %% "circe-core"   % "0.14.10",
      "io.circe"     %% "circe-parser" % "0.14.10",
      "org.scalameta" %% "munit"       % "1.0.4" % Test
    )
  )
