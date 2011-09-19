package com.wgen

case class Classroom(val id: String,
                     val name: String,
                     val students: Seq[Student],
                     val teachers: Seq[Teacher]) {
  require(id.matches("[0-9]{1,11}"))
  require(!name.isEmpty)
  require(!teachers.isEmpty)
}

object Classroom{
  def label = "classroom"

  def apply(node: xml.Node): Classroom = {
    require(node.label == label)
    val id = (node \ "@id").toString
    val name = (node \ "@name").toString
    val students = getStudents(node)
    val teachers = getTeachers(node)

    Classroom(id, name, students, teachers)
  }

  private def getTeachers(node: xml.Node) = {
    for(teacher <- (node \ Teacher.label)) yield Teacher(teacher)
  }

  private def getStudents(node: xml.Node) = {
    for(student <- (node \ Student.label)) yield Student(student)
  }
}