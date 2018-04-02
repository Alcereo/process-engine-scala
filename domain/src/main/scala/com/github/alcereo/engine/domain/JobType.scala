package com.github.alcereo.engine.domain

sealed trait JobType {
}

object JobType {

  def empty: JobType = EmptyBehaviorJobType()

  private case class EmptyBehaviorJobType() extends JobType

}


