package taller

import scala.collection.parallel.CollectionConverters._

object Paralelo {
  type Finca = Vector[(Int, Int, Int)]     // (ts, tr, p)
  type ProgRiego = Vector[Int]             // orden de riego
  type Distancia = Vector[Vector[Int]]     // matriz de distancias
  type TiempoInicioRiego = Vector[Int]    // vector de tiempos

  import Costos.{costoRiegoTablonConTiempos => _, _}

  def tIRPar(f: Finca, pi: ProgRiego): TiempoInicioRiego = {
    if (pi.isEmpty) Vector.empty
    else {
      val acumulados = pi.scanLeft(0)((acc, idx) => acc + f(idx)._2)
      val buffer = Array.fill[Int](f.size)(0)
      pi.zip(acumulados.init).par.foreach { case (tablon, inicio) =>
        buffer(tablon) = inicio
      }
      buffer.toVector
    }
  }

  def costoRiegoFincaPar(f: Finca, pi: ProgRiego): Int = {
    val tiempos = tIRPar(f, pi)

    tiempos.indices.toVector.par
      .map { i =>
        val (ts, tr, p) = f(i)
        val tInicio = tiempos(i)
        if (ts - tr >= tInicio)
          ts - (tInicio + tr)
        else
          p * ((tInicio + tr) - ts)
      }
      .sum
  }

  def costoMovilidadPar(f: Finca, pi: ProgRiego, d: Distancia): Int = {
    pi.zip(pi.drop(1)).par
      .map { case (a, b) => d(a)(b) }
      .sum
  }

  def costoTotalPar(f: Finca, pi: ProgRiego, d: Distancia): Int =
    costoRiegoFincaPar(f, pi) + costoMovilidadPar(f, pi, d)

  def generarProgramacionesRiegoPar(f: Finca): Vector[ProgRiego] = {
    val indices = f.indices.toVector

    def aux(v: Vector[Int]): Vector[Vector[Int]] = v match {
      case Vector()  => Vector.empty
      case Vector(x) => Vector(Vector(x))
      case _ =>
        v.par.flatMap { elem =>
          val resto = v.filterNot(_ == elem)
          aux(resto).map(p => elem +: p)
        }.toVector
    }

    aux(indices).toVector
  }

  def programacionRiegoOptimoPar(f: Finca, d: Distancia): (ProgRiego, Int) = {
    val todas = generarProgramacionesRiegoPar(f)

    if (todas.isEmpty) return (Vector.empty[Int], Int.MaxValue)

    val costos = todas.par.map { pi =>
      (pi, costoTotalPar(f, pi, d))
    }.toVector

    costos.minBy(_._2)
  }
}
