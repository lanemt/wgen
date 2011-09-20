package com.wgen

case class Grade(id: String) {
  var classrooms = Map[String, Classroom]()
  require(id.matches("[0-9]{1,11}"))

  def containsClassroom(uid: String): Boolean = classrooms.contains(uid)
  def getClassroom(uid: String): Classroom = classrooms(uid)
  def addClassroom(c: Classroom) { classrooms += (c.id -> c) }

  def toXML: xml.Elem = {
    <grade id={id}>
      {classrooms.values map { c => c.toXML } }
    </grade>
  }
}

object Grade{
  def label = "grade"

  def apply(node: xml.Node): Grade = {
    require(node.label == label)
    val id = (node \ "@id").toString()

    val grade = Grade(id)
    getClassrooms(node) foreach grade.addClassroom
    grade
  }

  private def getClassrooms(node: xml.Node) = {
    for(classroom <- (node \ Classroom.label)) yield Classroom(classroom)
  }
}
