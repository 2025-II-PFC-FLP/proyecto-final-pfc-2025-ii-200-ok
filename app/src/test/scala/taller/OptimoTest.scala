package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import taller.Optimo
@RunWith(classOf[JUnitRunner])
class TestOptimo extends AnyFunSuite {

  test("Optimo 1: movilidad dominante, varias soluciones óptimas") {
    val f = Vector((10,2,1),(10,2,1),(10,2,1))
    val d = Vector(
      Vector(0,1,5),
      Vector(1,0,1),
      Vector(5,1,0)
    )

    val (pi, costo) = Optimo.programacionRiegoOptimo(f, d)

    assert(costo == 20)
    assert(pi.length == 3)
  }


  test("Optimo 2: prioridad alta no siempre implica ir primero") {
    val f = Vector(
      (4,2,1),
      (3,1,4)
    )

    val d = Vector(
      Vector(0,5),
      Vector(5,0)
    )

    val (pi, costo) = Optimo.programacionRiegoOptimo(f, d)

    assert(pi == Vector(0,1))
    assert(costo == 7)
  }


  test("Optimo 3: tablon con supervivencia muy baja debe ir primero") {

    val f = Vector(
      (8,2,1),
      (1,1,2)   // sobrevivencia muy baja
    )

    val d = Vector(
      Vector(0,1),
      Vector(1,0)
    )

    val (pi, costo) = Optimo.programacionRiegoOptimo(f, d)

    assert(pi == Vector(1,0))
    assert(costo >= 0) // costo válido, no trivial
  }

  test("Optimo 4: combinación movilidad + riego lleva al orden 0-2-1") {

    val f = Vector(
      (3,1,3),   // prioridad alta, tr corto
      (10,3,1),
      (10,1,1)
    )

    val d = Vector(
      Vector(0,5,1),
      Vector(5,0,5),
      Vector(1,5,0)
    )

    val (pi, costo) = Optimo.programacionRiegoOptimo(f, d)

    assert(pi == Vector(0,2,1))
  }

  test("Optimo 5: finca 4 tablones con solución única π = Vector(1,0,2,3) (coste 10)") {

    val f = Vector(
      (5,1,1),  // 0: ts=5, tr=1, p=1
      (2,1,10), // 1: ts=2, tr=1, p=10 (baja supervivencia + alta penalización)
      (6,1,1),  // 2
      (7,1,1)   // 3
    )

    // Matriz simétrica: 0 para (1-0), (0-2), (2-3) y 10 para el resto
    val d = Vector(
      Vector(0, 0, 0, 10),
      Vector(0, 0, 10, 10),
      Vector(0, 10, 0, 0),
      Vector(10,10, 0, 0)
    )

    val (pi, costo) = Optimo.programacionRiegoOptimo(f, d)

    // Comprobaciones deterministas: permutación exacta y coste total esperado
    assert(pi == Vector(1,0,2,3))
    assert(costo == 10)
  }
}
