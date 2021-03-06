package ru.github.alcereo.engine.domain

import com.github.alcereo.engine.domain.context.{Context, PropsExchangeNames, TaskResult}
import org.scalatest.{FunSpec, Matchers}

class ContextSpec extends FunSpec with Matchers{

  describe("Context"){

    it("should exchange data"){

      val context = Context.empty

      context.getProperty("prop1") shouldBe None

      val externalContext = Map(
        ("prop", new Integer(3)),
        ("propr2", new Integer(4))
      )

      val exchangeProperties = List(
        PropsExchangeNames(
          external = "prop",
          inner = "prop1"
        )
      )

      context.exchangeData(
        Context(externalContext),
        exchangeProperties
      ).getProperty("prop1") shouldBe Some(3)

    }

    it("should set result"){

      val context = Context.empty

      context.result shouldBe None

      val result = TaskResult.success

      context.setResult(
        result
      ).result shouldEqual Some(result)

    }

  }

}
