package ru.github.alcereo.engine.domain

import java.util.UUID

import com.github.alcereo.engine.domain.{ResultTask, _}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FunSpec, Matchers}

class TasksSpec extends FunSpec with Matchers with MockitoSugar{

  describe("One-direction task"){

    it("should give next task"){

      val mockTask = mock[Task]

      OneDirectionTask(
        uid = UUID.randomUUID(),
        job = TaskJob.EmptyJob(),
        nextTask = mockTask,
        propsExchangeData = PropertiesExchangeData.empty()
      ).nextTaskOpt shouldBe Some(mockTask)
    }

  }

  describe("Result task"){

    it("should not be changing"){

      resultTaskChangeAndAssert(
        ResultTask.ResultSuccessTask(UUID.randomUUID())
      )

      resultTaskChangeAndAssert(
        ResultTask.ResultFailureTask(UUID.randomUUID())
      )
    }

    def resultTaskChangeAndAssert(failureResultTask: ResultTask) = {
      failureResultTask
        .acceptPreviousTaskContext(Context.emptyContext)
        .flatMap(_.finish())
        .getOrElse() shouldEqual failureResultTask
    }

  }

  describe(""){

  }

}
