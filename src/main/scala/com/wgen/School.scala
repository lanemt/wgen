package com.wgen

case class School(id: String, name: String) {
  var grades = Map[String,Grade]()
  require(id.matches("[0-9]{1,11}"))
  require(!name.isEmpty)

  def containsGrade(uid: String): Boolean = grades.contains(uid)
  def getGrade(uid: String): Grade = grades(uid)
  def addGrade(g: Grade) { grades += (g.id -> g) }

  def toXML: xml.Node = {
    val xml =
      <school id={id} name={name} xmlns="http://www.wirelessgeneration.com/wgen.xsd">
        {grades.values map { g => g.toXML } }
      </school>
    scala.xml.Utility.trim(xml)
  }
}

object School{
  def label = "school"

  def apply(node: xml.Node): School = {
    require(label == node.label)
    val id = (node \ "@id").toString()
    val name = (node \ "@name").toString()
    var school = School(id, name)

    getGrades(node) foreach school.addGrade
    school
  }

  private def getGrades(node: xml.Node) = {
    for(grade <- (node \ Grade.label)) yield Grade(grade)
  }
}