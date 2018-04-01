package com.github.alcereo.engine.domain.context

trait Context {

  def properties: Map[String, Object]
  def exchangeData(externalContext: Context, propsExchangeNames: List[PropsExchangeNames]):Context
  def getProperty(name:String): Option[Object]

  def result: Option[TaskResult]
  def setResult(result: TaskResult): Context
}

object Context{

  def empty = ContextImpl(Map.empty, None)

  def apply(properties: Map[String, Object], result: Option[TaskResult] = None) =
    ContextImpl(properties, result)

  def unapply(self: Context): Option[(Map[String, Object], Option[TaskResult])] =
    Some((self.properties, self.result))

  case class ContextImpl(properties: Map[String, Object],
                         result: Option[TaskResult] = None)
    extends Context {

    override def exchangeData(externalContext: Context,
                              propsExchangeNames: List[PropsExchangeNames]): Context = {

      val newProps = for {
        exchangePropsNames <- propsExchangeNames
      } yield (
        exchangePropsNames.inner,
        externalContext.getProperty(exchangePropsNames.external).get
      )

      copy(properties ++ newProps)
    }

    override def getProperty(name: String): Option[Object] = properties.get(name)

    override def setResult(result: TaskResult): Context = copy(result = Some(result))

  }

}