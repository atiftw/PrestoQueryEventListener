package com.syedatifakhtar


import io.prestosql.spi.Plugin
import io.prestosql.spi.eventlistener.EventListenerFactory

class PrestoQueryEventListenerPlugin extends Plugin {
  override def getEventListenerFactories: java.lang.Iterable[EventListenerFactory] = {
    val eventListeners = new java.util.ArrayList[EventListenerFactory]()
    eventListeners.add(new PrestoQueryEventListenerFactory())
    eventListeners
  }
}
