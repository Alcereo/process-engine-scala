package com.github.alcereo.engine.domain

import java.util.UUID

import com.github.alcereo.engine.domain.TaskJob.JobError
import com.github.alcereo.engine.domain.context.{Context, PropsExchangeNames}

trait TaskJob {

  def taskType: JobType

  def finished: Boolean
  def finish(): Either[JobError, TaskJob]

  def context: Context
  def contextExchange(externalContext: Context,
                      exchangeData: List[PropsExchangeNames]): Either[JobError, TaskJob]

}

object TaskJob {

  abstract class JobError(val message: String)

  case class AlreadyFinished(uid: UUID)
    extends JobError(s"Task($uid) already finished")

  def empty: TaskJob = EmptyJob()

  private case class EmptyJob() extends TaskJob{
    override def context: Context = Context.empty
    override def taskType: JobType = JobType.EmptyBehaviorJobType()
    override def finished: Boolean = true
    override def finish(): Either[JobError, TaskJob] = Right(this)
    override def contextExchange(externalContext: Context,
                                 exchangeData: List[PropsExchangeNames]): Either[JobError, TaskJob] = Right(this)
  }

}