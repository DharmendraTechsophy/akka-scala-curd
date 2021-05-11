name := "akka-scala"

version := "0.1"

scalaVersion := "2.13.5"

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.4"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-slf4j"% AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "org.json4s" %% "json4s-native" % "3.6.11",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.jason-goodwin" %% "authentikat-jwt" % "0.4.5",
  "org.scalatest" %% "scalatest" % "3.2.6" % Test,

)

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.20.13-play28",
  "org.reactivemongo" %% "reactivemongo-play-json-compat" % "1.0.1-play28",
  "org.reactivemongo" %% "reactivemongo-bson-compat" % "0.20.13",
  "com.typesafe.play" %% "play-json-joda" % "2.7.4",
  "org.reactivemongo" %% "reactivemongo" % "1.0.3",
  "org.reactivemongo" %% "reactivemongo-test" % "1.0.0",
  "org.reactivemongo" %% "reactivemongo-scalafix" % "1.0.3",
  "com.github.jwt-scala" %% "jwt-core" % "7.1.3",
)
