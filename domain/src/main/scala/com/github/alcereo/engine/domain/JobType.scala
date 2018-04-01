package com.github.alcereo.engine.domain

sealed trait JobType {
}

object JobType {

  case class EmptyBehaviorJobType() extends JobType

}


