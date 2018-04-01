package com.github.alcereo.engine.domain.task

import java.util.UUID

import com.github.alcereo.engine.domain.TaskJob
import com.github.alcereo.engine.domain.context.PropertiesExchangeData

trait OneDirectionTask
  extends Task
    with JobHaved
    with JobContexted

object OneDirectionTask {

  def apply(uid: UUID,
            job: TaskJob,
            nextTask: Task,
            propsExchangeData: PropertiesExchangeData): OneDirectionTask =
    OneDirectionTaskImpl(uid, job, propsExchangeData, nextTask)

  def unapply(self: OneDirectionTask): Option[(UUID, TaskJob, Task, PropertiesExchangeData)] =
    Some((self.uid, self.job, self.nextTaskOpt.get, self.propsExchangeData))


  private case class OneDirectionTaskImpl(uid: UUID,
                                          job: TaskJob,
                                          propsExchangeData: PropertiesExchangeData,
                                          nextTask: Task)
    extends OneDirectionTask{

    override def setJob(newJob: TaskJob):OneDirectionTaskImpl = copy(job = newJob)

    override def nextTaskOpt: Option[Task] = Some(nextTask)

  }


}