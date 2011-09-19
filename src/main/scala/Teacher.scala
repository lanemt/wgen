package com.wgen

case class Teacher(val id: String, val firstName: String, val lastName: String) extends Person {
  require(!firstName.isEmpty)
  require(!lastName.isEmpty)
  require(id.matches("[0-9]{1,11}"))

  def toXML(): xml.Elem = {
    <teacher id={id} first_name={firstName} last_name={lastName}/>
  }

}

object Teacher{
  def label = "teacher"

  def apply(node: xml.Node): Teacher = {
    require(node.label == label)
    val id = (node \ "@id").toString
    val first = (node \ "@first_name").toString
    val last = (node \ "@last_name").toString
    Teacher(id, first, last)
  }
}