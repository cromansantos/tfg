package org.dummy

import org.apache.spark.SparkContext

object ScalaApp extends App {
  val logFile = "EstacionesBase.csv"
  val sc = new SparkContext("local", "Simple", "$SPARK_HOME", List("target/spark-ejercicio-1.0.jar"))

  //Todo el fichero csv es cargado en este RDD
  val file = sc.textFile(logFile)

  //linea a linea se aplica un split o separacion utilizando la ","
  //eso nos arrojara un arreglo de palabras (o numeros)
  //y construimos una instancia del tipo OlympicMedalRecords
  println(file)
  val estacionesUbicacionRDD = file.map(x => {
    val arr = x.split(";")
    new EstacionesUbicacion(
      arr(0),
      arr(1).toDouble,
      arr(2).toDouble
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
      Utilities.calculateAngle(latitude, longitude, estacion.getLat, estacion.getLon)
    ))

  println("Angulos < 120")
  distances.filter(estacionDistancia => estacionDistancia.getAngulo <= 120)
    .sortBy(_.getDistancia)
    .foreach(distancia => println("ID: " + distancia.getId + " Distancia: " + distancia.getDistancia + " Angulo: " + distancia.getAngulo))

  println("Angulos > 120 && < 240")
  distances.filter(estacionDistancia => estacionDistancia.getAngulo > 120 && estacionDistancia.getAngulo < 240)
    .sortBy(_.getDistancia)
    .foreach(distancia => println("ID: " + distancia.getId + " Distancia: " + distancia.getDistancia + " Angulo: " + distancia.getAngulo))

  println("Angulos > 240")
  distances.filter(estacionDistancia => estacionDistancia.getAngulo >= 240)
    .sortBy(_.getDistancia)
    .foreach(distancia => println("ID: " + distancia.getId + " Distancia: " + distancia.getDistancia + " Angulo: " + distancia.getAngulo))
}

