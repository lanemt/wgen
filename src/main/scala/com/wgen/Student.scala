package com.wgen

case class Student(id: String, firstName: String,
                   lastName: String, grade: Int) extends Person {
  require(!firstName.isEmpty)
  require(!lastName.isEmpty)
  require(id.matches("[0-9]{1,11}"))
  require(0 <= grade && grade <= 13)

  def toXML: xml.Elem = {
    <student id={id} first_name={firstName} last_name={lastName} grade={grade.toString}/>
  }
}

object Student{
  def label = "student"

  def apply(node: xml.Node): Student = {
    require(node.label == label)
    val id = (node \ "@id").toString()
    val first = (node \ "@first_name").toString()
    val last = (node \ "@last_name").toString()
    val grade = (node \ "@grade").toString().toInt
    Student(id, first, last, grade)
  }
}