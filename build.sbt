name := "PrestoEventListener"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies +=  "com.facebook.presto" % "presto-spi" % "0.219" % "provided"

lazy val myProject = (project in file(".")).
  settings(
    assemblyOutputPath in assembly:=   file(s"${baseDirectory.value}/PrestoQueryEventListener.jar")
  )
