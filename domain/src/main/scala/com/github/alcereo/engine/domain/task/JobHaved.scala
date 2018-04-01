package com.github.alcereo.engine.domain.task

import com.github.alcereo.engine.domain.TaskJob
import com.github.alcereo.engine.domain.task.Task.{ErrorInJob, TaskError}

trait JobHaved extends Task{

  this: Task =>

  val job: TaskJob

  def setJob(newJob: TaskJob): JobHaved

  def finish(): Either[TaskError, Task] = {
    job.finish().fold(
      err => Left(ErrorInJob(err)),
      newJob => Right(setJob(newJob))
    )
  }

}
