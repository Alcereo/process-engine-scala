package com.github.alcereo.engine.domain

sealed trait TaskType {
}

object TaskType {



  case class EmptyBehaviorTaskType() extends TaskType

}


