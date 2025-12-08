package taller

import org.scalatest.funsuite.AnyFunSuite

class CostosTest extends AnyFunSuite {

  import Costos._
  import Tiempos.tIR

  val finca1: Finca =
    Vector((10,3,4), (5,3,3), (2,2,1), (8,1,1), (6,4,2))

  val d1: Distancia =
    Vector(
      Vector(0,2,2,4,4),
      Vector(2,0,4,2,6),
      Vector(2,4,0,2,2),
      Vector(4,2,2,0,4),
      Vector(4,6,2,4,0)
    )

  val pi1 = Vector(0,1,4,2,3)
  val pi2 = Vector(2,1,4,3,0)

  test("costoRiegoTablon - ejemplo PDF tablón 0 con pi1") {
    assert(costoRiegoTablon(0, finca1, pi1) == 7)
  }

  test("costoRiegoTablon - ejemplo PDF tablón 2 con pi1") {
    assert(costoRiegoTablon(2, finca1, pi1) == 10)
  }

  test("costoRiegoTablon - sin sufrimiento (tiempo suficiente)") {
    val f = Vector((10,3,1)) // ts=10, tr=3
    val pi = Vector(0)       // solo un tablón
    assert(costoRiegoTablon(0, f, pi) == 7)
  }

  test("costoRiegoTablon - con sufrimiento y prioridad alta") {
    val f = Vector((5,3,4))  // prioridad 4
    val pi = Vector(0)
    assert(costoRiegoTablon(0, f, pi) == 4 * ((3) - 5 + 3)) // tInicio=0 → (tr+0)=3
  }

  test("costoRiegoTablon - varios tablones calculados correctamente") {
    val f = Vector((8,2,3),(6,1,1))
    val pi = Vector(1,0)
    // tiempos: tab1=0; tab0=1
    val cr0 = costoRiegoTablon(0, f, pi)
    val cr1 = costoRiegoTablon(1, f, pi)
    assert(cr0 == 8 - (1 + 2))
    assert(cr1 == 6 - (0 + 1))
  }

  test("costoRiegoFinca - ejemplo PDF pi1 = 33") {
    assert(costoRiegoFinca(finca1, pi1) == 33)
  }

  test("costoRiegoFinca - ejemplo PDF pi2 = 20") {
    assert(costoRiegoFinca(finca1, pi2) == 20)
  }

  test("costoRiegoFinca - finca trivial 1 tablón") {
    val f = Vector((10,3,2))
    val pi = Vector(0)
    assert(costoRiegoFinca(f, pi) == costoRiegoTablon(0, f, pi))
  }

  test("costoRiegoFinca - finca pequeña con tiempos distintos") {
    val f = Vector((7,3,1),(5,2,2))
    val pi = Vector(1,0)
    assert(costoRiegoFinca(f, pi) == (5 - (0+2)) + (7 - (2+3)))
  }

  test("costoRiegoFinca - prioridades altas causan penalización") {
    val f = Vector((4,3,4),(3,2,4))
    val pi = Vector(0,1)
    val cr = costoRiegoFinca(f, pi)
    assert(cr > 0)
  }

  test("costoMovilidad - ejemplo PDF pi1 = 12") {
    assert(costoMovilidad(finca1, pi1, d1) == 12)
  }

  test("costoMovilidad - ejemplo PDF pi2 = 18") {
    assert(costoMovilidad(finca1, pi2, d1) == 18)
  }

  test("costoMovilidad - finca 1 tablón = 0") {
    val f = Vector((5,2,2))
    val pi = Vector(0)
    val d = Vector(Vector(0))
    assert(costoMovilidad(f, pi, d) == 0)
  }

  test("costoMovilidad - matriz pequeña simétrica correcta") {
    val d = Vector(
      Vector(0,1),
      Vector(1,0)
    )
    val pi = Vector(0,1)
    assert(costoMovilidad(Vector((5,1,1),(4,1,1)), pi, d) == 1)
  }

  test("costoMovilidad - orden inverso cambia resultado") {
    val d = Vector(
      Vector(0, 5, 2),
      Vector(5, 0, 3),
      Vector(2, 3, 0)
    )
    val f = Vector((1,1,1),(1,1,1),(1,1,1))
    val piA = Vector(0,1,2)
    val piB = Vector(2,1,0)
    assert(costoMovilidad(f, piA, d) != costoMovilidad(f, piB, d))
  }

  test("costoTotal - ejemplo PDF pi1 = 45") {
    assert(costoTotal(finca1, pi1, d1) == 45)
  }

  test("costoTotal - ejemplo PDF pi2 = 38") {
    assert(costoTotal(finca1, pi2, d1) == 38)
  }
}

