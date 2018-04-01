package com.github.alcereo.engine.domain.task
import com.github.alcereo.engine.domain.context.{Context, TaskResult}
import com.github.alcereo.engine.domain.task.SimpleResultDecisionTask.ResultNotSet
import com.github.alcereo.engine.domain.task.Task.ErrorInJob

trait JobResulted extends JobHaved with JobContexted{
  this:JobContexted =>

  val previousResultOpt: Option[TaskResult]
  def setResult(result: Option[TaskResult]): JobResulted

  override def acceptPreviousTaskContext(externalContext: Context): Either[Task.TaskError, JobResulted] = {

    externalContext.result match {
      case None => Left(ResultNotSet())
      case result =>
        super.acceptPreviousTaskContext(externalContext) match {
          case taskResult:Either[Task.TaskError, JobResulted] => taskResult.map(_.setResult(result))
          case _ => throw new RuntimeException("Task after accept context change his type")
        }
    }

  }
}
