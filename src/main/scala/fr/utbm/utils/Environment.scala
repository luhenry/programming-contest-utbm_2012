package fr.utbm.utils

import java.io.File
import java.io.FileWriter
import java.io.IOException

import scala.io.Source

object Environment {

  def read(filename: String): Map = {
    val lines = Source.fromFile(filename).getLines.toSeq
    val size = if (!lines.isEmpty) { lines.head.toInt } else 0
    val map = new Map(size, new Array[Square](size * size))

    for (i <- 1 until size) yield {
      val values = lines(i).split(" ")

      for (j <- 0 until size) yield {
        val square =
          if (values(j).startsWith("T"))
            new Town(i, j, map, values(j).substring(1).toInt)
          else
            new Normal(i, j, map, values(j).toInt)

        map.update(i, j, square)
      }
    }

    map
  }

  def write(routes: List[Route], solution: Solution) = {
    try {
      val croutes = new File("croutes")

      if (!croutes.exists) {
        croutes.createNewFile()
      }

      val fw = new FileWriter(croutes.getAbsoluteFile)
      fw.write(routeToString(routes))
      fw.close()
    } catch {
      case e: IOException => e.printStackTrace()
    }

    try {
      val csolution = new File("csolution")

      if (!csolution.exists) {
        csolution.createNewFile()
      }

      val fw = new FileWriter(csolution.getAbsoluteFile)
      fw.write(solutionToString(solution))
      fw.close()
    } catch {
      case e: IOException => e.printStackTrace()
    }
  }

  def routeToString(routes: List[Route]): String = {
    val sb = new StringBuilder();

    for (route <- routes) yield {
      sb.append("T").append(route.path.head.id).append(" ")
      sb.append("T").append(route.path.last.id).append(";")

      for (point <- route.path) yield {
        sb.append(point.x).append(":").append(point.y).append(" ")
      }

      sb.append("\n")
    }

    sb.mkString
  }

  def solutionToString(solution: Solution): String = {
    val sb = new StringBuilder()

    if (solution != null) {
      sb.append(solution.cost).append("\n")

      for (town <- solution.towns) yield {
        sb.append("T").append(town.id).append(" ")
      }
    }

    sb.mkString
  }
}