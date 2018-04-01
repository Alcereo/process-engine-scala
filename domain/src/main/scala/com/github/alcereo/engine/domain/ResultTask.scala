package com.github.alcereo.engine.domain
import java.util.UUID

import com.github.alcereo.engine.domain.Task.TaskError


sealed abstract class ResultTask(val uid: UUID) extends Task{

  def this() = this(UUID.randomUUID())

  override def job: TaskJob = TaskJob.EmptyJob()

  override def propsExchangeData: PropertiesExchangeData =
    PropertiesExchangeData(List(), List())

  override def acceptPreviousTaskContext(context: Context): Either[TaskError, Task] = Right(this)

  override def acceptBaseContext(baseContext: Context): Either[TaskError, Task] = Right(this)

  override def finish(): Either[TaskError, Task] = Right(this)

  override def nextTaskOpt: Option[Task] = None

}

object ResultTask {

  case class ResultSuccessTask(override val uid: UUID) extends ResultTask(uid)

  case class ResultFailureTask(override val uid: UUID, message: String = "dsfdfgs") extends ResultTask(uid)

}
