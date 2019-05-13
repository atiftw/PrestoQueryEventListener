name := "PrestoEventListener"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies += "io.prestosql" % "presto-spi" % "310" % "provided"

lazy val myProject = (project in file(".")).
  settings(
    assemblyOutputPath in assembly:=   file(s"${baseDirectory.value}/PrestoQueryEventListener.jar")
  )
