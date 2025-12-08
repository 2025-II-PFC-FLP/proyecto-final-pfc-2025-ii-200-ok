package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CostosParaleloTests extends AnyFunSuite {

  type Finca = Vector[(Int, Int, Int)]
  type Distancia = Vector[Vector[Int]]
  type ProgRiego = Vector[Int]

  private def dist(n: Int): Distancia =
    Vector.tabulate(n, n)((i,j) => if (i == j) 0 else (i+j)+1)

  test("costoTotalPar coincide con costoTotal - caso simple") {
    val f = Vector((10,3,2),(12,4,3))
    val d = dist(2)
    val pi = Vector(0,1)
    assert( Paralelo.costoTotalPar(f, pi, d) === Costos.costoTotal(f, pi, d) )
  }

  test("costoTotalPar - con penalización") {
    val f = Vector((7,3,5),(10,2,3),(6,3,2))
    val d = dist(3)
    val pi = Vector(2,0,1)
    assert( Paralelo.costoTotalPar(f, pi, d) === Costos.costoTotal(f, pi, d) )
  }

  test("costoTotalPar - movilidad alta") {
    val f = Vector((9,2,2),(8,1,3),(13,1,1))
    val d = Vector(
      Vector(0,10,10),
      Vector(10,0,10),
      Vector(10,10,0)
    )
    val pi = Vector(1,0,2)
    assert( Paralelo.costoTotalPar(f, pi, d) === Costos.costoTotal(f, pi, d) )
  }

  test("costoTotalPar - finca vacía") {
    val f = Vector.empty[(Int,Int,Int)]
    val d = Vector.empty[Vector[Int]]
    val pi = Vector.empty[Int]
    assert( Paralelo.costoTotalPar(f, pi, d) === Costos.costoTotal(f, pi, d) )
  }

  test("costoTotalPar - caso aleatorio fijo") {
    val f = Vector((10,3,1),(7,2,4),(12,1,2),(8,2,3))
    val d = dist(4)
    val pi = Vector(3,2,0,1)
    assert( Paralelo.costoTotalPar(f, pi, d) === Costos.costoTotal(f, pi, d) )
  }

}
