package com.github.alcereo.engine.domain.task

import java.util.UUID

import com.github.alcereo.engine.domain.TaskJob.JobError

trait Task {

  def uid: UUID

  def nextTaskOpt: Option[Task]

}

object Task {

  abstract class TaskError(val message: String)

  case class ErrorInJob(jobError: JobError)
    extends TaskError(s"Error in job: $jobError: ${jobError.message}")

}