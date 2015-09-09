import AssemblyKeys._
//import NativePackagerKeys._
//import com.typesafe.sbt.SbtNativePackager._

assemblySettings

mainClass in assembly := Some("play.core.server.NettyServer")

fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value)


name := """finagle-elasticsearch-microservice"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

maintainer := "ginduc"
dockerExposedPorts in Docker := Seq(9002)

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws exclude ("org.apache.httpcomponents" , "httpclient"),
  specs2 % Test,
  "com.twitter"                 % "finagle-http_2.11"       % "6.25.0",
  "com.twitter"                 % "bijection-util_2.11"     % "0.7.2"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

//https://www.playframework.com/documentation/2.4.x/CorsFilter
libraryDependencies += filters

play.PlayImport.PlayKeys.playDefaultPort := 9001

