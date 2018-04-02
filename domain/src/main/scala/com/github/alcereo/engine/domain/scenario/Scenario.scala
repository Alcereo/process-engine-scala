package com.github.alcereo.engine.domain.scenario

import java.util.UUID

import com.github.alcereo.engine.domain.context.Context
import com.github.alcereo.engine.domain.scenario.Scenario.ScenarioError
import com.github.alcereo.engine.domain.task.Task

trait Scenario {

  def uid: UUID

  def context: Context

  def allTasks: Seq[Task]

  def acceptCurrentTaskResultContext(context: Context): Either[ScenarioError, Scenario]

  def stage: Scenario.Stage

}

object Scenario {

  abstract class ScenarioError(val message: String)

  sealed trait Stage
  case class New() extends Stage
  case class Started() extends Stage
  case class Finished() extends Stage

}
