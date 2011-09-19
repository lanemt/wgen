package com.wgen

import java.io.{PrintWriter, File}
import collection.mutable.ArrayBuilder

import scala.io.Source

class CSVBuilder {
  def delim = ", "

  def toFile(school: School, filename: String) {
    val file = new java.io.File(filename)
    val out = new PrintWriter( file )
    try {
      out.println( getCSV(school) )
    } finally {
      out.close
    }
  }

  def loadFile(filename: String): School = {
    var school = School("100","school")
    val src = Source.fromFile(filename)
    val lines = src.getLines().toList
    val linetok = lines.map(_.split(delim))

    println(linetok.toArray.deepMkString(delim))
    //val students = iter.map(_.dropRight(4).mkString).toList
    //println(students)
    //println("num students: " + students.size)
    //println("num unique students: " + students.distinct.size)
    //val teachers =
    src.close()
    School("1","school")
  }

  def processCSV(a: Array[String]) = {

  }

  private def getCSV(school: School): String = {
    val csvBuffer = new StringBuilder
    for {
      grade <- school.grades
      classroom <- grade.classrooms
    }{
      val classCSV = grade.id + delim +
                     classroom.id + delim +
                     classroom.name + delim +
                     getTeacherCSV(classroom)
      for (student <- classroom.students) {
        val studentCSV = getStudentCSV(student)
        //csvBuffer.
        csvBuffer.append(classCSV)
        csvBuffer.append(delim)
        csvBuffer.append(studentCSV)
        csvBuffer.append("\n")
      }
    }
    csvBuffer.toString
  }

  private def getTeacherCSV(classroom: Classroom) = {
    def teacherCSV(teacher: Teacher) = {
      teacher.id + delim + teacher.lastName + delim + teacher.firstName
    }

    val teachers = classroom.teachers
    val first = if (!teachers.isEmpty) teacherCSV(teachers(0))
                else delim + delim
    val second = if (teachers.size >= 2) teacherCSV(teachers(1))
                 else delim + delim
    first + delim + second
  }

  private def getStudentCSV(student: Student) = {
    student.id + delim +
    student.lastName + delim + student.firstName + delim +
    student.grade
  }

}