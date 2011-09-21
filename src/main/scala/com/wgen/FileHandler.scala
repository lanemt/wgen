package com.wgen

class FileHandler(schemaFile: String) extends SchoolIOHandler{
  private val xmlHandler = new XMLHandler(schemaFile)
  private val fileExtensionToHandler = collection.mutable.Map[String,SchoolIOHandler]()

  def parsesFileExtension = fileExtensionToHandler.keys.toList

  def addHandler(handler: SchoolIOHandler) {
    for (extension <- handler.parsesFileExtension())
      fileExtensionToHandler += (extension -> handler)
  }

  def saveToFile(school: School, outputFile: String) {
    try {
      verifySchemaIntegrity(school)

      val handler = getHandler(outputFile)
      handler.saveToFile(school, outputFile)
    } catch {
      case e: Exception =>
      Console.err.println("error: Unable to save school to file "
        + outputFile + "\nexception: " + e)
      throw e
    }
  }

  def loadFile(inputFile: String): School = {
    try{
      val handler = getHandler(inputFile)
      var school = handler.loadFile(inputFile)

      verifySchemaIntegrity(school)
      school
    } catch {
      case e: Exception =>
      Console.err.println("error: Unable to load school from file " +
        inputFile + "\nexception: " + e)
      throw e
    }
  }

  private def verifySchemaIntegrity(school: School) {
    xmlHandler.verifySchemaIntegrity(school)
  }

  private def getHandler(fileName: String): SchoolIOHandler = {
    val extension = fileName.split('.').last
    if(!fileExtensionToHandler.contains(extension))
      throw new RuntimeException("Unsupported file type " + fileName)
    fileExtensionToHandler(extension)
  }
}