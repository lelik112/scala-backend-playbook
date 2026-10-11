ThisBuild / scalaVersion := "2.13.18"
ThisBuild / organization := "io.github.lelik112"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / publish / skip := true

lazy val root = (project in file("."))
  .settings(
    name := "scala-backend-playbook",
    Compile / unmanagedSources := Seq(baseDirectory.value / "examples/domain/time-range/TimeRange.scala"),
    Test / unmanagedSources := Seq(
      baseDirectory.value / "examples/domain/time-range/TimeRangeTest.scala",
      baseDirectory.value / "examples/domain/time-range/TimeRangeCharacterizationTest.scala"
    ),
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % "0.14.10",
      "io.circe" %% "circe-parser" % "0.14.10" % Test,
      "dev.zio" %% "zio" % "2.1.21",
      "org.scalatest" %% "scalatest" % "3.2.19" % Test
    )
  )
