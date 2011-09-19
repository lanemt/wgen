package com.wgen

import javax.xml.validation.SchemaFactory
import javax.xml.transform.stream.StreamSource
import javax.xml.XMLConstants
import java.io.File

object xmlParser {
  def getXMLFromFile(schemaFileName: String, xmlFileName: String): xml.Node = {
    val factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
    val schema = factory.newSchema(new StreamSource(new File(schemaFileName)))
    val xmlSource = scala.xml.Source.fromFile(xmlFileName)
    new SchemaAwareFactoryAdapter(schema).load(xmlSource)
  }
}

/*
    // and whenever we would want to do something like:
    //val is = new InputSource(new File("foo.xml"))
    //val xml = XML.load(is)


    val factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
    val xsdStream = getClass.getResourceAsStream(schemaFileName)

    val schema = factory.newSchema(new StreamSource(xsdStream))
    val source = getClass.getResourceAsStream(xmlFileName)
    val xml = new SchemaAwareFactoryAdapter(schema).load(source)
    xml
*/

