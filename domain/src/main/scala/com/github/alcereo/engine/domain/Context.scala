package com.github.alcereo.engine.domain

trait Context {
  def exchangeData(externalContext: Context, propsExchangeNames: List[PropsExchangeNames]):Context
  def getProperty(name:String): Option[Object]
  def result: Option[TaskResult]
  def setResult(result: TaskResult): Context
}

object Context{

  def emptyContext = ContextImpl(Map.empty, None)

  def apply(properties: Map[String, Object], result: Option[TaskResult] = None) =
    ContextImpl(properties, result)


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