package com.wgen

case class Classroom(id: String, name: String) {
  require(id matches("[0-9]{1,11}"))
  require(name nonEmpty)
  var students = Map[String, Student]()
  var teachers = Map[String, Teacher]()

  def containsTeacher(uid: String): Boolean = teachers.contains(uid)
  def getTeacher(uid: String): Teacher = teachers(uid)
  def addTeacher(t: Teacher) { teachers += (t.id -> t) }

  def containsStudent(uid: String): Boolean = students.contains(uid)
  def getStudent(uid: String): Student = students(uid)
  def addStudent(s: Student) { students += (s.id -> s) }

  def toXML: xml.Elem = {
    <classroom id={id} name={name}>
      {teachers.values map { t => t.toXML } }
      {students.values map { s => s.toXML } }
    </classroom>
  }
}

object Classroom {
  def label = "classroom"

  def apply(node: xml.Node): Classroom = {
    require(node.label == label)
    val id = (node \ "@id").toString()
    val name = (node \ "@name").toString()
    val classroom = Classroom(id, name)

    getStudents(node) foreach classroom.addStudent
    getTeachers(node) foreach classroom.addTeacher

    classroom
  }

  private def getTeachers(node: xml.Node) = {
    for(teacher <- (node \ Teacher.label)) yield Teacher(teacher)
  }

  private def getStudents(node: xml.Node) = {
    for(student <- (node \ Student.label)) yield Student(student)
  }
}