package com.github.alcereo.engine.domain

import java.util.UUID

import com.github.alcereo.engine.domain.TaskJob.JobError

trait TaskJob {

  def context: Context
  def taskType: TaskType
  def finished: Boolean

  def finish(): Either[JobError, TaskJob]
  def contextExchange(externalContext: Context,
                      exchangeData: List[PropsExchangeNames]): Either[JobError, TaskJob]

}

object TaskJob {

  abstract class JobError(val message: String)

  case class AlreadyFinished(uid: UUID)
    extends JobError(s"Task($uid) already finished")

  case class EmptyJob() extends TaskJob{
    override def context: Context = Context.emptyContext
    override def taskType: TaskType = TaskType.EmptyBehaviorTaskType()
    override def finished: Boolean = true
    override def finish(): Either[JobError, TaskJob] = Right(this)
    override def contextExchange(externalContext: Context,
                                 exchangeData: List[PropsExchangeNames]): Either[JobError, TaskJob] = Right(this)
  }

}