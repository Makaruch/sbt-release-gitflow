//lazy val `sbt-release` = project in file(".")

organization := "org.lancegatlin"

name := "sbt-release-gitflow"

sbtPlugin := true
publishMavenStyle := false
scalacOptions += "-deprecation"
scalaVersion := "2.12.3"
sbtVersion in Global := "1.2.8"

//libraryDependencies ++= Seq(
//  "org.specs2" %% "specs2-core" % "3.6" % "test"
//)
crossSbtVersions := Vector("0.13.16", "1.0.3")

libraryDependencies ++= Seq(
  "org.eclipse.jgit" % "org.eclipse.jgit" % "3.3.2.201404171909-r"
)

scalaCompilerBridgeSource := {
  val sv = appConfiguration.value.provider.id.version
  ("org.scala-sbt" % "compiler-interface" % sv % "component").sources
}
