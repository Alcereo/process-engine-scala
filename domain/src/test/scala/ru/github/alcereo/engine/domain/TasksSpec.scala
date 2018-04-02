package ru.github.alcereo.engine.domain

import java.util.UUID

import com.github.alcereo.engine.domain.TaskJob
import com.github.alcereo.engine.domain.context.{Context, PropertiesExchangeData, TaskResult}
import com.github.alcereo.engine.domain.task.{OneDirectionTask, SimpleResultDecisionTask, Task}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FunSpec, Matchers}


class TasksSpec extends FunSpec with Matchers with MockitoSugar{

  describe("One-direction task"){

    it("should give next task"){

      val mockTask = mock[Task]

      OneDirectionTask(
        uid = UUID.randomUUID(),
        job = TaskJob.empty,
        nextTask = mockTask,
        propsExchangeData = PropertiesExchangeData.empty
      ).nextTaskOpt shouldBe Some(mockTask)
    }

  }

  describe("Simple-result decision task"){

    val successTaskMock = mock[Task]
    val failureTaskMock = mock[Task]

    it("should give success task on success result"){

      val task = SimpleResultDecisionTask(
        uid = UUID.randomUUID(),
        job = TaskJob.empty,
        propsExchangeData = PropertiesExchangeData.empty,
        successResultTask = successTaskMock,
        failureResultTask = failureTaskMock
      )

      task.acceptPreviousTaskContext(
        Context(
          properties = Map(),
          result = Some(TaskResult.success)
        )
      ).map(_.nextTaskOpt).getOrElse(None) shouldBe Some(successTaskMock)

    }

    it("should give failure task on failure result"){

      val task = SimpleResultDecisionTask(
        uid = UUID.randomUUID(),
        job = TaskJob.empty,
        propsExchangeData = PropertiesExchangeData.empty,
        successResultTask = successTaskMock,
        failureResultTask = failureTaskMock
      )

      task.acceptPreviousTaskContext(
        Context(
          properties = Map(),
          result = Some(TaskResult.failure)
        )
      ).map(_.nextTaskOpt).getOrElse(None) shouldBe Some(failureTaskMock)

    }

  }

}
