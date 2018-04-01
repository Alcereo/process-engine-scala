package com.github.alcereo.engine.domain.context

sealed trait TaskResult {
}

object TaskResult{

  def success = SuccessResult()
  def failure = FailureResult()

  case class SuccessResult() extends TaskResult
  case class FailureResult() extends TaskResult

}
