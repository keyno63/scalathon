name := "scalathon-scalaFx"

version := "0.1"

scalaVersion := "2.12.9"

// https://mvnrepository.com/artifact/org.scalafx/scalafx
libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "12.0.1-R17",

  // https://mvnrepository.com/artifact/org.scala-lang/scala-swing
  "org.scala-lang" % "scala-swing" % "2.11.0-M7",

  // skinny
  "org.skinny-framework" %% "skinny-http-client" % "3.0.1",
  "log4j" % "log4j" % "1.2.17",
  "org.slf4j" % "slf4j-log4j12" % "1.7.26" % Test,
)

val circeVersion = "0.9.3"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)
