package com.knoldus

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Driver extends App {
  private val path = "src/main/resources/grades.csv"

  private val parsedDataFuture = StudentGrades.parseCsv(path)
  private val studentAveragesFuture = StudentGrades.calculateStudentAverages(parsedDataFuture)
  private val classAverageFuture = StudentGrades.calculateGrades(path)

  // Print the average grade for each student
  studentAveragesFuture.onComplete {
    case Success(studentAverages) =>
      println("StudentID | averages:")
      studentAverages.foreach {
        case (id, avg) => println(s"       $id  | $avg")
      }
    case Failure(ex) => println(s"Error: ${ex.getMessage}")
  }

  // Print the class average
  classAverageFuture.onComplete {
    case Success(classAverage) =>
      println(s"\nClass average: $classAverage")
    case Failure(ex) => println(s"Error: ${ex.getMessage}")
  }

  // Wait for all futures to complete
  Thread.sleep(2000)
}
