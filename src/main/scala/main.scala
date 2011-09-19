package com.wgen

object main extends App{
  println("Hello, World!")

  try {
    val xml = xmlParser.getXMLFromFile("wgen_project/wgen_project__xml_schema.xsd", "wgen_project/wgen_project__xml_sample_data.xml")

    //val schools = for(school <- xml) yield School(school)
    //println("Number of schools:" + schools.size)

    val school = School(xml.last)
    val csvBuilder = new CSVBuilder
    csvBuilder.toFile(school, "out.csv")
    var csvSchool = csvBuilder.loadFile("out.csv")
    //var school = School(xml \ School.label)

    /*
    for (xs <- (xml \\ Classroom.label)) {
      val classroom = Classroom(xs)
      println(classroom.name + " size:" + classroom.students.size)
    }
    println("AAAAAAAAAAAA!")
    val loadnoad = loadXMLFromFile("wgen_project/wgen_project__xml_sample_data.xml")
    //println(loadnoad.toString)
    */
  } catch {
    case ex: Exception => { //FileNotFoundException
      Console.err.println("error: unable to convert xml: " + ex )
    }
  }

  def loadXMLFromFile(filename: String): scala.xml.Elem = {
      xml.XML.loadFile(filename)
  }

}

/* scratch

//import java.io.FileNotFoundException

  val node = <a><b><c/></b></a>

  scala.xml.XML.save("test.xml", node)

  println("AAAAAAAAAAAA!")

*/
