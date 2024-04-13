name := "JokeLuke"

version := "1.0"

scalaVersion := "2.10.0"

exportJars := true

seq(com.github.retronym.SbtOneJar.oneJarSettings: _*)

mainClass in oneJar := Some("com.jokeluke.Bot")

libraryDependencies ++= Seq(
  "org.twitter4j" % "twitter4j-core" % "3.0.6",
  "org.twitter4j" % "twitter4j-stream" % "3.0.6",
  "com.typesafe.akka" %% "akka-actor" % "2.1.2"
)
