package org.dummy

import scala.math.{asin, cos, pow, sin, sqrt}

object Utilities {
  private val R = 6372.8 //radius in km

  def calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double) = {
    val dLat = (lat2 - lat1).toRadians
    val dLon = (lon2 - lon1).toRadians

    val a = pow(sin(dLat / 2), 2) + pow(sin(dLon / 2), 2) * cos(lat1.toRadians) * cos(lat2.toRadians)
    val c = 2 * asin(sqrt(a))
    R * c
  }

  def calculateAngle(lat1: Double, lon1: Double, lat2: Double, lon2: Double) = {
    val lat1Rad = lat1.toRadians
    val lon1Rad = lon1.toRadians
    val lat2Rad = lat2.toRadians
    val lon2Rad = lon2.toRadians

    val dLon = lon2Rad - lon1Rad
    val y = Math.sin(dLon) * Math.cos(lat2Rad)
    val x = Math.cos(lat1Rad) * Math.sin(lat2Rad) - Math.sin(lat1Rad) * Math.cos(lat2Rad) * Math.cos(dLon)
    val brng = Math.atan2(y, x).toDegrees
    if (brng > 0) {
      brng
    } else {
      360 + brng
    }
  }

  def calculateRange(distance: Double) = {
    distance match {
      case x if x <= 0.2 => new Rango(0, 0.2)
      case x if x > 0.2 && x <= 3.5 => new Rango(0.2, 3.5)
      case x if x > 3.5 && x <= 7 => new Rango(3.5, 7)
      case x if x > 7 && x <= 17 => new Rango(7, 17)
      case _ => new Rango(17, Double.MaxValue)
    }
  }
}
