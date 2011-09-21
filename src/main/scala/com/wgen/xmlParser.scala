package com.wgen

import javax.xml.validation.SchemaFactory
import javax.xml.transform.stream.StreamSource
import javax.xml.XMLConstants
import java.io.File

object xmlParser {
  def getAndValidateXML(schemaFileName: String, xmlFileName: String): xml.Node = {
    val factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
    val schema = factory.newSchema(new StreamSource(new File(schemaFileName)))
    val xmlSource = scala.xml.Source.fromFile(xmlFileName)
    new SchemaAwareFactoryAdapter(schema).load(xmlSource)
  }

  def getXMLFromFile(filename: String): scala.xml.Elem = {
    xml.XML.loadFile(filename)
  }
}

