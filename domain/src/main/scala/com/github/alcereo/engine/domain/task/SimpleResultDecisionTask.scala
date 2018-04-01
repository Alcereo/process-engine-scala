package com.github.alcereo.engine.domain.task

import java.util.UUID

import com.github.alcereo.engine.domain.context.{Context, PropertiesExchangeData, TaskResult}
import com.github.alcereo.engine.domain.task.Task.TaskError
import com.github.alcereo.engine.domain.TaskJob
import com.github.alcereo.engine.domain.context.TaskResult.{FailureResult, SuccessResult}

trait SimpleResultDecisionTask
  extends Task
    with JobHaved
    with JobContexted
    with JobResulted{

  def successResultTask: Task
  def failureResultTask: Task

}

object SimpleResultDecisionTask{

  case class ResultNotSet()
    extends TaskError("Previous result not set")

  def apply(uid: UUID,
            job: TaskJob,
            propsExchangeData: PropertiesExchangeData,
            previousResultOpt: Option[TaskResult] = None,
            successResultTask: Task,
            failureResultTask: Task
           ): SimpleResultDecisionTask = SimpleResultDecisionTaskImpl(uid, job, propsExchangeData, previousResultOpt, successResultTask, failureResultTask)

  def unapply(self: SimpleResultDecisionTask): Option[(UUID, TaskJob, PropertiesExchangeData, Option[TaskResult], Task, Task)] =
    Some((self.uid, self.job, self.propsExchangeData, self.previousResultOpt, self.successResultTask, self.failureResultTask))

  case class SimpleResultDecisionTaskImpl(uid: UUID,
                                          job: TaskJob,
                                          propsExchangeData: PropertiesExchangeData,
                                          previousResultOpt: Option[TaskResult],
                                          successResultTask: Task,
                                          failureResultTask: Task)
    extends SimpleResultDecisionTask {

    override def setResult(result: Option[TaskResult]): JobResulted = copy(previousResultOpt = result)

    override def setJob(newJob: TaskJob):SimpleResultDecisionTaskImpl = copy(job = newJob)

    override def nextTaskOpt: Option[Task] = {
      previousResultOpt match {
        case None => None
        case Some(previousResult) =>
          previousResult match {
            case SuccessResult() => Some(successResultTask)
            case FailureResult() => Some(failureResultTask)
          }
      }
    }

  }

}
