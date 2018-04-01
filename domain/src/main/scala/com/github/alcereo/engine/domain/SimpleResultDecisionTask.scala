package com.github.alcereo.engine.domain
import java.util.UUID

import com.github.alcereo.engine.domain.Task.TaskError
import com.github.alcereo.engine.domain.TaskResult.{FailureResult, SuccessResult}


object SimpleResultDecisionTask{

  case class ResultNotSet()
    extends TaskError("Previous result not set")


  case class SimpleResultDecisionTaskImpl(override val uid: UUID,
                                          override val job: TaskJob,
                                          override val propsExchangeData: PropertiesExchangeData,
                                          previousResultOpt: Option[TaskResult],
                                          successResultTask: Task,
                                          failureResultTask: Task)
    extends AbstractTask(uid, job, propsExchangeData) {


    override def acceptPreviousTaskContext(externalContext: Context): Either[TaskError, Task] = {

      externalContext.result match {
        case None => Left(ResultNotSet())
        case result =>
          super.acceptPreviousTaskContext(externalContext)
            .map(task => copy(
              job = task.job,
              previousResultOpt = result
            ))
      }
    }

    override def setJob(newJob: TaskJob): Task = copy(job = newJob)

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
