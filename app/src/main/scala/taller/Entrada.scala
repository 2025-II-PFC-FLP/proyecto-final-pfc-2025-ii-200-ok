package proyecto

import scala.util.Random

object Entradas {

  // Tipos del proyecto
  type Tablon = (Int, Int, Int)
  type Finca = Vector[Tablon]
  type Distancia = Vector[Vector[Int]]

  val random = new Random()


  def fincaAlAzar(long: Int): Finca = {
    Vector.fill(long)(
      (
        random.nextInt(long * 2) + 1, // tiempo de supervivencia
        random.nextInt(long) + 1,     // tiempo de riego
        random.nextInt(4) + 1         // prioridad
      )
    )
  }



  def distanciaAlAzar(long: Int): Distancia = {
    val base = Vector.fill(long, long)(random.nextInt(long * 3) + 1)

    Vector.tabulate(long, long) { (i, j) =>
      if (i == j) 0            // distancia consigo mismo
      else if (i < j) base(i)(j)
      else base(j)(i)          // simetría DF[i][j] = DF[j][i]
    }
  }


  def tsup(f: Finca, i: Int): Int =
    if (i >= 0 && i < f.length) f(i)._1 else 0

  // Tiempo de riego del tablón i
  def treg(f: Finca, i: Int): Int = {
    f(i)._2
  }

  // Prioridad del tablón i
  def prio(f: Finca, i: Int): Int = {
    f(i)._3
  }

}