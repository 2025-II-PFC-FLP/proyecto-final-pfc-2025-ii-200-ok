package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import taller.Optimo
@RunWith(classOf[JUnitRunner])
class TestOptimo extends AnyFunSuite {

  test("Optimo 1: movilidad dominante, camino 0-1-2 es óptimo") {

    val f = Vector(
      (10,2,1),
      (10,2,1),
      (10,2,1)
    )

    val d = Vector(
      Vector(0,1,5),
      Vector(1,0,1),
      Vector(5,1,0)
    )

    val (pi, costo) = Optimo.programacionRiegoOptimo(f, d)

    assert(pi == Vector(0,1,2))
    assert(costo == 2) // movilidad 1 + 1
  }

  test("Optimo 2: prioridad alta obliga a regar primero el tablón 1") {

    val f = Vector(
      (4,2,1),
      (3,1,4)   // prioridad alta
    )

    val d = Vector(
      Vector(0,5),
      Vector(5,0)
    )

    val (pi, costo) = Optimo.programacionRiegoOptimo(f, d)

    assert(pi == Vector(1,0))
    assert(costo == 5) // movilidad
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

  test("Optimo 5: finca de 4 tablones donde el primero debe ser el 1") {

    val f = Vector(
      (8,4,2),
      (2,1,3),  // prioridad alta + baja supervivencia
      (9,2,1),
      (7,1,1)
    )

    val d = Vector(
      Vector(0,5,5,5),
      Vector(5,0,1,1),
      Vector(5,1,0,4),
      Vector(5,1,4,0)
    )

    val (pi, costo) = Optimo.programacionRiegoOptimo(f, d)

    assert(pi.head == 1)   // siempre debe iniciar en 1
    assert(pi.length == 4)
  }

}
