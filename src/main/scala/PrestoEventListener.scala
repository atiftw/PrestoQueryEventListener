package com.syedatifakhtar

import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util
import java.util.NoSuchElementException
import java.util.logging.FileHandler
import java.util.logging.Logger
import java.util.logging.SimpleFormatter

import io.prestosql.spi.eventlistener._

import scala.util.{Failure, Success, Try}


class PrestoEventListener() extends EventListener {
  createLogFile()
  private[PrestoEventListener] var logger: Logger = null
  private[PrestoEventListener] var fh: FileHandler = null
  final private[PrestoEventListener] val loggerName = "QueryLog"

  def this(config: util.Map[String, String]) {
    this()
    createLogFile()
  }

  override def queryCreated(queryCreatedEvent: QueryCreatedEvent): Unit = {
    val msg = new StringBuilder
    try {
      msg.append("---------------Query Created----------------------------")
      msg.append("\n")
      msg.append("     ")
      msg.append("Query ID: ")
      msg.append(queryCreatedEvent.getMetadata.getQueryId)
      msg.append("\n")
      msg.append("Query: ")
      msg.append(queryCreatedEvent.getMetadata.getQuery)
      msg.append("\n")
      msg.append("     ")
      msg.append("Query State: ")
      msg.append(queryCreatedEvent.getMetadata.getQueryState)
      msg.append("\n")
      msg.append("     ")
      msg.append("User: ")
      msg.append(queryCreatedEvent.getContext.getUser)
      msg.append("\n")
      msg.append("     ")
      msg.append("Create Time: ")
      msg.append(queryCreatedEvent.getCreateTime)
      msg.append("\n")
      msg.append("     ")
      msg.append("Principal: ")
      msg.append(queryCreatedEvent.getContext.getPrincipal)
      msg.append("\n")
      msg.append("     ")
      msg.append("Remote Client Address: ")
      msg.append(queryCreatedEvent.getContext.getRemoteClientAddress)
      msg.append("\n")
      msg.append("     ")
      msg.append("Source: ")
      msg.append(queryCreatedEvent.getContext.getSource)
      msg.append("\n")
      msg.append("     ")
      msg.append("User Agent: ")
      msg.append(queryCreatedEvent.getContext.getUserAgent)
      msg.append("\n")
      msg.append("     ")
      msg.append("Catalog: ")
      msg.append(queryCreatedEvent.getContext.getCatalog)
      msg.append("\n")
      msg.append("     ")
      msg.append("Schema: ")
      msg.append(queryCreatedEvent.getContext.getSchema)
      msg.append("\n")
      msg.append("     ")
      msg.append("Server Address: ")
      msg.append(queryCreatedEvent.getContext.getServerAddress)
      logger.info(msg.toString)
    } catch {
      case ex: Exception =>
        logger.info(ex.getMessage)
    }
  }

  override def queryCompleted(queryCompletedEvent: QueryCompletedEvent): Unit = {
    val msg = new StringBuilder
    val errorCode: Try[String] = Try {
      queryCompletedEvent.getFailureInfo.get.getErrorCode.getName
    }
    msg.append("---------------Query Completed----------------------------")
    msg.append("\n")
    msg.append("     ")
    msg.append("Query ID: ")
    msg.append(queryCompletedEvent.getMetadata.getQueryId)
    msg.append("\n")
    msg.append("     ")
    msg.append("Create Time: ")
    msg.append(queryCompletedEvent.getCreateTime)
    msg.append("\n")
    msg.append("     ")
    msg.append("User: ")
    msg.append(queryCompletedEvent.getContext.getUser)
    msg.append("\n")
    msg.append("     ")
    msg.append("Complete: ")
    msg.append(queryCompletedEvent.getStatistics.isComplete)
    msg.append("\n")
    msg.append("     ")
    errorCode match {
      case Failure(_: NoSuchElementException) =>
      case Success(error) =>
        msg.append("Query Failure Error: ")
        msg.append(error)
        msg.append("\n")
      case Failure(ex: Throwable) =>
        msg.append("Query Failure Error: ")
        msg.append(s"Error while fetching state: ${ex.getMessage}")
        msg.append("\n")

    }
    msg.append("     ")
    msg.append("Remote Client Address: ")
    msg.append(queryCompletedEvent.getContext.getRemoteClientAddress.toString)
    logger.info(msg.toString)

  }

  override def splitCompleted(splitCompletedEvent: SplitCompletedEvent): Unit = {
    val msg = new StringBuilder
    try {
      msg.append("---------------Split Completed----------------------------")
      msg.append("\n")
      msg.append("     ")
      msg.append("Query ID: ")
      msg.append(splitCompletedEvent.getQueryId)
      msg.append("\n")
      msg.append("     ")
      msg.append("Stage ID: ")
      msg.append(splitCompletedEvent.getStageId)
      msg.append("\n")
      msg.append("     ")
      msg.append("Task ID: ")
      msg.append(splitCompletedEvent.getTaskId)
      logger.info(msg.toString)
    } catch {
      case ex: Exception =>
        logger.info(ex.getMessage)
    }
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