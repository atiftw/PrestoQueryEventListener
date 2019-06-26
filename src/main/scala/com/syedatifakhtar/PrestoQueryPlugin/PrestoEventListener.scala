package com.syedatifakhtar.PrestoQueryPlugin

import java.io.IOException
import java.text.SimpleDateFormat
import java.util
import java.util.Date
import java.util.logging.{FileHandler, Logger, SimpleFormatter}

import com.facebook.presto.spi.eventlistener._
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule




class PrestoEventListener() extends EventListener {
  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  createLogFile()
  private[PrestoEventListener] var logger: Logger = null
  private[PrestoEventListener] var fh: FileHandler = null
  final private[PrestoEventListener] val loggerName = "QueryLog"

  def this(config: util.Map[String, String]) {
    this()
    createLogFile()
  }

  override def queryCreated(queryCreatedEvent: QueryCreatedEvent): Unit = {
    logger.info(mapper.writeValueAsString(queryCreatedEvent))
  }

  override def queryCompleted(queryCompletedEvent: QueryCompletedEvent): Unit = {
    logger.info(mapper.writeValueAsString(queryCompletedEvent))

  }

  override def splitCompleted(splitCompletedEvent: SplitCompletedEvent): Unit = {
    logger.info(mapper.writeValueAsString(splitCompletedEvent ))
  }

  def createLogFile(): Unit = {
    val dateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val timeStamp = dateTime.format(new Date)
    val logPath = new StringBuilder
    logPath.append("/var/log/presto/queries-")
    logPath.append(timeStamp)
    logPath.append(".%g.log")
    try {
      logger = Logger.getLogger(loggerName)
      fh = new FileHandler(logPath.toString, 524288000, 5, true)
      logger.addHandler(fh)
      logger.setUseParentHandlers(false)
      val formatter = new SimpleFormatter
      fh.setFormatter(formatter)
    } catch {
      case e: IOException =>
        logger.info(e.getMessage)
    }
  }
}