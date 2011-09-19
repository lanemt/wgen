package com.wgen

case class Grade(val id: String, val classrooms: Seq[Classroom]) {
  require(id.matches("[0-9]{1,11}"))
}

object Grade{
  def label = "grade"

  def apply(node: xml.Node): Grade = {
    require(node.label == label)
    val id = (node \ "@id").toString
    val classrooms = getClassrooms(node)
    Grade(id, classrooms)
  }

  private def getClassrooms(node: xml.Node) = {
    for(classroom <- (node \ Classroom.label)) yield Classroom(classroom)
  }
}
