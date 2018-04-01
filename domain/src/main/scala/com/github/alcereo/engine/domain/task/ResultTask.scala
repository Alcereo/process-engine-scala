package com.github.alcereo.engine.domain.task

import java.util.UUID


sealed abstract class ResultTask extends Task{

  override def nextTaskOpt: Option[Task] = None

}

object ResultTask {

  case class ResultSuccessTask(uid: UUID) extends ResultTask

  case class ResultFailureTask(uid: UUID, message: String = "") extends ResultTask

}
