package taller

import org.scalameter._
import scala.util.Random

object Benchmarking {

  type Finca = Vector[(Int, Int, Int)]          //(ts, tr, p)
  type Distancia = Vector[Vector[Int]]          // matriz de distancias
  type ProgRiego = Vector[Int]                  // orden de riego

  private def rand(min: Int, max: Int): Int =
    min + Random.nextInt((max - min) + 1)

  def generarFinca(n: Int): Finca =
    Vector.fill(n)(
      (
        rand(5, 20),   // ts
        rand(1, 5),    // tr
        rand(1, 10)    // p
      )
    )

  def generarDistancias(n: Int): Distancia = {
    val base = Array.ofDim[Int](n, n)
    for (i <- 0 until n; j <- 0 until n) {
      if (i == j) base(i)(j) = 0
      else {
        val d = rand(1, 10)
        base(i)(j) = d
        base(j)(i) = d
      }
    }
    base.map(_.toVector).toVector
  }
}

