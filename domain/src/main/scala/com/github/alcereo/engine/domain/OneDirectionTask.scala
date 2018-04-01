package com.github.alcereo.engine.domain
import java.util.UUID

object OneDirectionTask {

  def apply(uid: UUID,
            job: TaskJob,
            nextTask: Task,
            propsExchangeData: PropertiesExchangeData): Task =
    OneDirectionTaskImpl(uid, job, propsExchangeData, nextTask)

  def unapply(self: Task): Option[(UUID, TaskJob, Task, PropertiesExchangeData)] =
    Some((self.uid, self.job, self.nextTaskOpt.get, self.propsExchangeData))


  private case class OneDirectionTaskImpl(override val uid: UUID,
                                          override val job: TaskJob,
                                          override val propsExchangeData: PropertiesExchangeData,
                                          nextTask: Task)
    extends AbstractTask(uid, job, propsExchangeData) {

    override def setJob(newJob: TaskJob): Task = copy(job = newJob)

    override def nextTaskOpt: Option[Task] = Some(nextTask)

  }


}