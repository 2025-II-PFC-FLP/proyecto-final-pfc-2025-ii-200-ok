package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ProgramacionesParaleloTests extends AnyFunSuite {

  type Finca = Vector[(Int,Int,Int)]
  type ProgRiego = Vector[Int]

  test("generarProgramacionesRiegoPar coincide con secuencial") {
    val f: Finca = Vector((1,1,1),(2,1,1),(3,1,1))

    val seq = ProgramacionesRiego.generarProgramacionesRiego(f).toSet
    val par = Paralelo.generarProgramacionesRiegoPar(f).toSet

    assert(seq === par)
  }

  test("generarProgramacionesRiegoPar tamaño 1") {
    val f = Vector((5,1,1))
    assert(Paralelo.generarProgramacionesRiegoPar(f) === Vector(Vector(0)))
  }

  test("generarProgramacionesRiegoPar tamaño 2") {
    val f = Vector((1,1,1),(2,1,1))
    val esperado = Set(Vector(0,1), Vector(1,0))
    assert(Paralelo.generarProgramacionesRiegoPar(f).toSet === esperado)
  }

  test("generarProgramacionesRiegoPar finca vacía") {
    val f = Vector.empty[(Int,Int,Int)]
    assert(Paralelo.generarProgramacionesRiegoPar(f) === Vector())
  }

  test("generarProgramacionesRiegoPar sin duplicados") {
    val f = Vector((1,1,1),(2,1,1),(3,1,1),(4,1,1))
    val par = Paralelo.generarProgramacionesRiegoPar(f)
    assert(par.size == par.toSet.size)
  }
}
