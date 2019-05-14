package com.syedatifakhtar.PrestoQueryPlugin

import com.facebook.presto.spi.Plugin
import com.facebook.presto.spi.eventlistener.EventListenerFactory

class PrestoQueryEventListenerPlugin extends Plugin {
  override def getEventListenerFactories: java.lang.Iterable[EventListenerFactory] = {
    val eventListeners = new java.util.ArrayList[EventListenerFactory]()
    eventListeners.add(new PrestoQueryEventListenerFactory())
    eventListeners
  }
}
