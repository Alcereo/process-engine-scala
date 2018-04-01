package com.github.alcereo.engine.domain.context

case class PropertiesExchangeData(fromPreviousTask: List[PropsExchangeNames],
                                  fromBase:         List[PropsExchangeNames])

object PropertiesExchangeData {
  def empty = PropertiesExchangeData(List(), List())
}
