package com.wgen

case class School(val id: String, val name: String) {
  var grades = List[Grade]()
  require(id.matches("[0-9]{1,11}"))
  require(!name.isEmpty)
}

object School{
  def label = "school"

  def apply(node: xml.Node): School = {
    require(label == node.label)
    val id = (node \ "@id").toString
    val name = (node \ "@name").toString
    var school = School(id, name)

    school.grades = getGrades(node).toList
    school
  }

  private def getGrades(node: xml.Node) = {
    for(grade <- (node \ Grade.label)) yield Grade(grade)
  }
}