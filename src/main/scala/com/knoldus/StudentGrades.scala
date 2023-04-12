package com.knoldus

import com.typesafe.scalalogging.Logger

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}
import scala.io.Source

object StudentGrades {

  val logger: Logger = Logger(getClass.getName)


  def parseCsv(path: String): Future[List[Map[String, String]]] = Future {
    val listOfMap = Try {
      val lines = Source.fromFile(path).getLines().toList
      val headers = lines.head.split(",").toList
      val data = lines.tail.map(_.split(",").toList)
      data.map(row => headers.zip(row).toMap)
    }

    listOfMap match {
      case Success(parsedData) => parsedData
      case Failure(ex) => throw new Exception(s"Failed to parse CSV: ${ex.getMessage}")
    }
  }

  logger.info("file parsing completed")

  def calculateStudentAverages(parsedData: Future[List[Map[String, String]]]): Future[List[(String, Double)]] =
    parsedData.map { data =>
      data.map { row =>
        val id = row("StudentID")
        val grades = List(row("English"), row("Physics"), row("Chemistry"), row("Maths")).map(_.toDouble)
        val totalSumOfGrads = grades.foldLeft(0.0)(_ + _)
        val average = totalSumOfGrads / grades.length

        (id, average)
      }
    }

  logger.info("student average calculation completed")

  private def calculateClassAverage(studentAverages: Future[List[(String, Double)]]): Future[Double] =
    studentAverages.map { data =>
      val sum = data.map(_._2).sum
      val classAverage = sum / data.length
      classAverage
    }

  logger.info("class average calculation completed")

  def calculateGrades(path: String): Future[Double] = {
    val parsedData = parseCsv(path)
    val studentAverages = calculateStudentAverages(parsedData)
    calculateClassAverage(studentAverages).recoverWith {
      case ex => Future.failed(new Exception(s"Failed to calculate class average: ${ex.getMessage}"))
    }
  }
}

