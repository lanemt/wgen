package com.wgen

import javax.xml.parsers.{SAXParser, SAXParserFactory}
import javax.xml.validation.Schema
import org.xml.sax.InputSource
import xml.parsing.NoBindingFactoryAdapter
import xml.{TopScope, Elem}

class SchemaAwareFactoryAdapter(schema:Schema) extends NoBindingFactoryAdapter {

  override def load(source: InputSource): Elem = {
    // create parser
    val parser: SAXParser = try {
      val f = SAXParserFactory.newInstance()
      f.setNamespaceAware(true)
      f.setFeature("http://xml.org/sax/features/namespace-prefixes", true)
      f.newSAXParser()
    } catch {
      case e: Exception =>
        Console.err.println("error: Unable to instantiate parser")
        throw e
    }

    val xr = parser.getXMLReader
    val vh = schema.newValidatorHandler
    vh.setContentHandler(this)
    xr.setContentHandler(vh)

    // parse file
    scopeStack.push(TopScope)
    xr.parse(source)
    scopeStack.pop()
    rootElem.asInstanceOf[Elem]
  }

}