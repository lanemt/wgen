package com.wgen

import java.io.PrintWriter
import scala.io.Source

class CSVBuilder {
  private def delim = ", "
  private def numValues = 13

  def toFile(school: School, filename: String) {
    val file = new java.io.File(filename)
    val out = new PrintWriter( file )
    try {
      out.println( getCSV(school) )
    } finally {
      out.close()
    }
  }

  def loadFile(filename: String): School = {
    var school = School("100","school")
    val source = Source.fromFile(filename)
    val lines = source.getLines().toList
    source.close()

    for {
      line <- lines
      val tokens = line.split(delim, numValues)
      if (tokens.size > 1)
      val Array(gradeId, classroomId, classroomName,
        teacher1Id, teacher1LastName, teacher1FirstName,
        teacher2Id, teacher2LastName, teacher2FirstName,
        studentId, studentLastName, studentFirstName, studentGrade) =
        tokens.padTo(numValues, "") } {

      val grade = addGrade(school, gradeId)
      val classroom = addClass(grade, classroomId, classroomName)
      addTeacher(classroom, teacher1Id, teacher1LastName, teacher1FirstName)
      addTeacher(classroom, teacher2Id, teacher2LastName, teacher2FirstName)
      addStudent(classroom, studentId, studentLastName, studentFirstName, studentGrade)
    }

    def addGrade(school: School, gradeId: String): Grade = {
      if (school.containsGrade(gradeId))
        school.getGrade(gradeId)
      else {
        val newGrade = new Grade(gradeId)
        school.addGrade(newGrade)
        newGrade
      }
    }
    def addClass(grade: Grade, id: String, name: String): Classroom = {
      if (grade.containsClassroom(id))
        grade.getClassroom(id)
      else {
        val newClassroom = new Classroom(id, name)
        grade.addClassroom(newClassroom)
        newClassroom
      }
    }
    def addTeacher(classroom: Classroom, id: String,
                   firstName: String, lastName: String) {
      if (id.nonEmpty && !classroom.containsTeacher(id))
      {
        val teacher = new Teacher(id, firstName, lastName)
        classroom.addTeacher(teacher)
      }
    }
    def addStudent(classroom: Classroom, id: String,
                   firstName: String, lastName: String, grade: String) {
      if (id.nonEmpty && !classroom.containsStudent(id))
      {
        var student = new Student(id, firstName, lastName, grade.toInt)
        classroom.addStudent(student)
      }
    }
    
    return school
  }

  private def getCSV(school: School): String = {
    val csvBuffer = new StringBuilder
    for {
      grade <- school.grades.values
      classroom <- grade.classrooms.values
    } {
      val classCSV = grade.id + delim +
                     classroom.id + delim + classroom.name + delim +
                     getTeacherCSV(classroom)

      if (classroom.students.isEmpty) {
        csvBuffer.append(classCSV)
        csvBuffer.append(delim)
        csvBuffer.append(getEmptyStudentCSV)
        csvBuffer.append("\n")
      }

      for (student <- classroom.students.values) {
        val studentCSV = getStudentCSV(student)

        csvBuffer.append(classCSV)
        csvBuffer.append(delim)
        csvBuffer.append(studentCSV)
        csvBuffer.append("\n")
      }
    }
    csvBuffer.toString()
  }

  private def getTeacherCSV(classroom: Classroom) = {
    def teacherCSV(teacher: Teacher) = {
      teacher.id + delim + teacher.lastName + delim + teacher.firstName
    }

    val teacherIter = classroom.teachers.iterator
    val first = if (teacherIter.hasNext) teacherCSV(teacherIter.next()._2)
                else delim + delim
    val second = if (teacherIter.hasNext) teacherCSV(teacherIter.next()._2)
                 else delim + delim
    first + delim + second
  }

  private def getStudentCSV(student: Student) = {
    student.id + delim +
    student.lastName + delim + student.firstName + delim +
    student.grade
  }
  private def getEmptyStudentCSV = {
    delim + delim + delim
  }

}