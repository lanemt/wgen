package com.wgen

trait SchoolIOHandler
{
  def saveToFile(school: School, filename: String);
  def loadFile(filename: String): School;
  def parsesFileExtension(): List[String];
}