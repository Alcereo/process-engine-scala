package com.github.alcereo.engine.domain


sealed trait TaskResult {
}

object TaskResult{

  case class SuccessResult() extends TaskResult
  case class FailureResult() extends TaskResult

}
