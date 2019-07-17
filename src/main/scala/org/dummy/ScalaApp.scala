package org.dummy

import org.apache.spark.SparkContext

object ScalaApp extends App {
  val logFile = "EstacionesBase.csv"
  val sc = new SparkContext("local", "Simple", "$SPARK_HOME", List("target/spark-ejercicio-1.0.jar"))

  //Todo el fichero csv es cargado en este RDD
  val file = sc.textFile(logFile)

  //linea a linea se aplica un split o separacion utilizando ";"
  //eso nos arrojara un arreglo de palabras (o numeros)
  //y construimos una instancia del tipo OlympicMedalRecords
  val estacionesUbicacionRDD = file.map(x => {
    val arr = x.split(";")
    new EstacionesUbicacion(
      arr(0),
      arr(1).toDouble,
      arr(2).toDouble,
      arr(3).toInt
    )
  })

  println("Introduce coordenadas separados por , -> Ej// 39.217070,-3.626943")
  val coordenadas = readLine().split(",")
  val latitude = coordenadas(0).toDouble
  val longitude = coordenadas(1).toDouble

  println("Lista de Estaciones Base")
  estacionesUbicacionRDD.foreach(estacion => println(estacion.getId + " " + estacion.getLat + "," + estacion.getLon))
  val distances = estacionesUbicacionRDD
    .map(estacion => new EstacionDistancia(
      estacion.getId,
      Utilities.calculateDistance(latitude, longitude, estacion.getLat, estacion.getLon),
      Utilities.calculateAngle(latitude, longitude, estacion.getLat, estacion.getLon),
      estacion.getQuality
    ))

  //TODO Lleva a función
  println("Angulos < 120")
  val angle120 = distances.filter(estacionDistancia => estacionDistancia.getAngulo <= 120)
    .sortBy(_.getDistancia)
  angle120.foreach(distancia => println("ID: " + distancia.getId + " Distancia: " + distancia.getDistancia + " Angulo: " + distancia.getAngulo))
  if (angle120.count() > 0) {
    val range = Utilities.calculateRange(angle120.first().getDistancia)
    val filtered = angle120.filter(estacion => estacion.getDistancia >= range.getMin)//&& estacion.getDistancia <= range.getMax && estacion.getQuality == 3)
    if (filtered.count() > 0) {
      filtered.first()
    }
    println("Solución: " + angle120.first().getId)
  } else {
    println("No hay solución")
  }

  //TODO Aplicar función anterior creada
  println("Angulos > 120 && < 240")
  val angle240 = distances.filter(estacionDistancia => estacionDistancia.getAngulo > 120 && estacionDistancia.getAngulo < 240)
    .sortBy(_.getDistancia)
  angle240.foreach(distancia => println("ID: " + distancia.getId + " Distancia: " + distancia.getDistancia + " Angulo: " + distancia.getAngulo))
  println(Utilities.calculateRange(angle240.first().getDistancia).getMin)

  println("Angulos > 240")
  val angle360 = distances.filter(estacionDistancia => estacionDistancia.getAngulo >= 240)
    .sortBy(_.getDistancia)
  angle360.foreach(distancia => println("ID: " + distancia.getId + " Distancia: " + distancia.getDistancia + " Angulo: " + distancia.getAngulo))
  println(Utilities.calculateRange(angle360.first().getDistancia).getMin)
}

