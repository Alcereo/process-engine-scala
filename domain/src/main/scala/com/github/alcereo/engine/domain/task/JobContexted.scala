package com.github.alcereo.engine.domain.task

import com.github.alcereo.engine.domain.context.{Context, PropertiesExchangeData}
import com.github.alcereo.engine.domain.task.Task.{ErrorInJob, TaskError}

trait JobContexted extends JobHaved{
  this: JobHaved =>

  def propsExchangeData: PropertiesExchangeData

  def acceptPreviousTaskContext(externalContext: Context): Either[TaskError, JobHaved] = {
    job.contextExchange(
      externalContext,
      propsExchangeData.fromPreviousTask
    ).fold(
      err => Left(ErrorInJob(err)),
      newJob => Right(setJob(newJob))
    )
  }

  def acceptBaseContext(baseContext: Context): Either[TaskError, JobHaved] = {
    job.contextExchange(
      baseContext,
      propsExchangeData.fromBase
    ).fold(
      err => Left(ErrorInJob(err)),
      newJob => Right(setJob(newJob))
    )
  }

}
