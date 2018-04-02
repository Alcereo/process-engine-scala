package com.github.alcereo.engine.domain.scenario

import com.github.alcereo.engine.domain.scenario.Scenario.{New, ScenarioError}
import com.github.alcereo.engine.domain.scenario.TaskHeadered.WrongStageToSetHeaderTask
import com.github.alcereo.engine.domain.task.Task

trait TaskHeadered extends Scenario{

  this: Scenario =>

  def headerTask: Option[Task]

  def currentTask: Option[Task]

  protected def setHeaderTask(headerTask: Task): TaskHeadered

  def acceptHeaderTask(headerTask: Task): Either[ScenarioError, TaskHeadered] = {
    stage match {
      case New() => Right(setHeaderTask(headerTask))
      case _ => Left(WrongStageToSetHeaderTask(stage))
    }
  }

}

object TaskHeadered {

  case class WrongStageToSetHeaderTask(stage: Scenario.Stage)
    extends ScenarioError(s"Can't set header task in scenario stage: $stage. Required stage: New()")

}

