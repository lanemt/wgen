package com.wgen

import javax.xml.validation.SchemaFactory
import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import java.io.{StringReader, File, PrintWriter}
import org.xml.sax.InputSource

class XMLHandler(schemaFileName: String) extends SchoolIOHandler {
  def parsesFileExtension = List[String]("xml","xsd")
  private val factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
  private val schema = factory.newSchema(new StreamSource(new File(schemaFileName)))
  private val schemaAdaptor = new SchemaAwareFactoryAdapter(schema)
  private def width = 100
  private def step = 2
  private val prettyPrinter = new scala.xml.PrettyPrinter(width, step)

  def saveToFile(school: School, filename: String) {
    val file = new java.io.File(filename)
    val out = new PrintWriter( file )
    try {
      out.println(prettyPrinter.format(school.toXML))
    } finally {
      out.close()
    }
    println("Saved school '" + school.name + "' to file " + filename + ".")
  }

  def loadFile(xmlFileName: String): School = {
    val fileSource = scala.xml.Source.fromFile(xmlFileName)
    val xml = schemaAdaptor.load(fileSource)
    School(xml.last)
  }

  def verifySchemaIntegrity(school: School) {
    val xmlString = school.toXML.toString()
    val is = new InputSource(new StringReader(xmlString))
    schemaAdaptor.load(is)
  }
}