package com.github.alcereo.engine.domain

import java.util.UUID

import com.github.alcereo.engine.domain.Task.{ErrorInJob, TaskError}
import com.github.alcereo.engine.domain.TaskJob.JobError

trait Task {

  def uid: UUID

  def job: TaskJob

  def propsExchangeData: PropertiesExchangeData

  def nextTaskOpt: Option[Task]

  def acceptPreviousTaskContext(context: Context): Either[TaskError, Task]

  def acceptBaseContext(baseContext: Context = Context.emptyContext): Either[TaskError, Task]

  def finish(): Either[TaskError, Task]

}

object Task {

  abstract class TaskError(val message: String)

  case class ErrorInJob(jobError: JobError)
    extends TaskError(s"Error in job: $jobError: ${jobError.message}")

}