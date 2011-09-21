package com.wgen

import com.frugalmechanic.optparse._

object main extends OptParse{
  def usage = "__Usage__\nDescription: Wgen project parses and outputs school data files.\n" +
              "Args: --input <fileIn> --output <fileout> --schema <schemafile.xsd>\n" +
              "Supported input/output filetypes: .xml and .csv"

  val inFile = StrOpt("input")
  val outFile = StrOpt("output")
  val schemaFile = StrOpt("schema")

  def main(args:Array[String]) {
    parse(args)
    if (inFile.getOrElse("").isEmpty ||
        outFile.getOrElse("").isEmpty ||
        schemaFile.getOrElse("").isEmpty) {
      Console.err.println("Missing parameters\n" + usage)
      return
    }

    val fileHandler = new FileHandler(schemaFile())
    fileHandler.addHandler(new CSVHandler)
    fileHandler.addHandler(new XMLHandler(schemaFile()))

    val school = fileHandler.loadFile(inFile())
    fileHandler.saveToFile(school, outFile())
  }
}