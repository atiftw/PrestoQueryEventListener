package com.syedatifakhtar.PrestoQueryPlugin

import java.util

import com.facebook.presto.spi.eventlistener.{EventListener, EventListenerFactory}

class PrestoQueryEventListenerFactory() extends EventListenerFactory {
  override def getName: String = "event-listener"

  override def create(config: util.Map[String, String]): EventListener = new PrestoEventListener(config)
}
