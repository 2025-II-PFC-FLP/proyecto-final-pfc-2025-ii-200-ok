package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TiemposParaleloTests extends AnyFunSuite {

  type Finca = Vector[(Int, Int, Int)]
  type ProgRiego = Vector[Int]

  test("tIRPar debe producir el mismo resultado que tIR - ejemplo simple") {
    val f: Finca = Vector((10,3,1), (12,4,2))
    val pi: ProgRiego = Vector(0,1)
    assert( Paralelo.tIRPar(f, pi) === Tiempos.tIR(f, pi) )
  }

  test("tIRPar - finca con 3 tablones en orden inverso") {
    val f: Finca = Vector((8,2,1), (7,3,2), (10,1,3))
    val pi = Vector(2,1,0)
    assert( Paralelo.tIRPar(f, pi) === Tiempos.tIR(f, pi) )
  }

  test("tIRPar - finca con órdenes aleatorios") {
    val f = Vector((9,2,1),(8,1,3),(12,4,5),(6,2,1))
    val pi = Vector(3,1,0,2)
    assert( Paralelo.tIRPar(f, pi) === Tiempos.tIR(f, pi) )
  }

  test("tIRPar - finca vacía") {
    val f = Vector.empty[(Int,Int,Int)]
    val pi = Vector.empty[Int]
    assert( Paralelo.tIRPar(f, pi) === Tiempos.tIR(f, pi) )
  }

  test("tIRPar - tr altos y ts bajos") {
    val f = Vector((5,5,2),(4,3,1),(3,2,1))
    val pi = Vector(1,2,0)
    assert( Paralelo.tIRPar(f, pi) === Tiempos.tIR(f, pi) )
  }
}
