package ru.github.alcereo.engine.domain

import java.util.UUID

import com.github.alcereo.engine.domain.context.Context
import com.github.alcereo.engine.domain.scenario.Scenario.{Finished, New, ScenarioError, Started}
import com.github.alcereo.engine.domain.scenario.{Scenario, TaskHeadered}
import com.github.alcereo.engine.domain.task.Task
import org.scalatest.{FunSpec, Matchers}
import org.scalatest.mockito.MockitoSugar

class ScenarionSpec extends FunSpec with Matchers with MockitoSugar {


  describe("Task headered scenario") {

    case class TestHeaderedScenario(uid: UUID,
                                    context: Context,
                                    allTasks: Seq[Task],
                                    stage: Scenario.Stage,
                                    headerTask: Option[Task],
                                    currentTask: Option[Task]
                                   )
      extends Scenario with TaskHeadered {

      override def acceptCurrentTaskResultContext(context: Context): Either[ScenarioError, Scenario] =
        Left(new ScenarioError(message = "") {})

      override protected def setHeaderTask(headerTask: Task): TaskHeadered =
        copy(headerTask = Some(headerTask))

    }

    val mockedTask = mock[Task]

    it("should set header task in stage New()") {

      TestHeaderedScenario(
        uid = UUID.randomUUID(),
        context = Context.empty,
        allTasks = Seq(),
        stage = New(),
        headerTask = None,
        currentTask = None
      ).acceptHeaderTask(mockedTask)
        .map(_.headerTask)
        .getOrElse(None) shouldEqual Some(mockedTask)

    }

    it("should not set header task in any stage, except New()"){

//      TODO: Create property based test here

      Seq(
        Started(),
        Finished()
      ).foreach(stage => {

        TestHeaderedScenario(
          uid = UUID.randomUUID(),
          context = Context.empty,
          allTasks = Seq(),
          stage = New(),
          headerTask = None,
          currentTask = None
        ).acceptHeaderTask(mockedTask)
          .map(_.headerTask)
          .getOrElse(None) shouldEqual Some(mockedTask)

      })

    }

  }

}
