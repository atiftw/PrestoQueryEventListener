name := "PrestoEventListener"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++=  Seq(
  "com.facebook.presto" % "presto-spi" % "0.219" % "provided",
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.12" % "2.9.8",
)

lazy val myProject = (project in file(".")).
  settings(
    assemblyOutputPath in assembly:=   file(s"${baseDirectory.value}/PrestoQueryEventListener.jar")
  )
