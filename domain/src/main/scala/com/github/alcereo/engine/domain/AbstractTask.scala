package com.github.alcereo.engine.domain

import java.util.UUID

import com.github.alcereo.engine.domain.Task.{ErrorInJob, TaskError}

abstract class AbstractTask(val uid: UUID,
                            val job: TaskJob,
                            val propsExchangeData: PropertiesExchangeData) extends Task{

  def setJob(newJob: TaskJob): Task

  override def acceptPreviousTaskContext(externalContext: Context): Either[TaskError, Task] = {
    job.contextExchange(
      externalContext,
      propsExchangeData.fromPreviousTask
    ).fold(
      err => Left(ErrorInJob(err)),
      newJob => Right(setJob(newJob))
    )
  }

  override def acceptBaseContext(baseContext: Context): Either[TaskError, Task] = {
    job.contextExchange(
      baseContext,
      propsExchangeData.fromBase
    ).fold(
      err => Left(ErrorInJob(err)),
      newJob => Right(setJob(newJob))
    )
  }

  override def finish(): Either[TaskError, Task] = {
    job.finish().fold(
      err => Left(ErrorInJob(err)),
      newJob => Right(setJob(newJob))
    )
  }

}