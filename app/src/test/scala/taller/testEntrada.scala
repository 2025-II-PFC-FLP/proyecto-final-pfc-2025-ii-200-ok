package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import proyecto.Entradas._  // Importa tu objeto Entradas

@RunWith(classOf[JUnitRunner])
class EntradaTest extends AnyFunSuite {

  test("Generar finca de tamaño 5") {
    val f = fincaAlAzar(5)
    assert(f.length == 5, "La finca debe tener 5 tablones")
    f.foreach { t =>
      assert(t._1 >= 1 && t._1 <= 10)
      assert(t._2 >= 1 && t._2 <= 5)
      assert(t._3 >= 1 && t._3 <= 4)
    }
  }

  test("Tiempo de supervivencia y riego") {
    val f = Vector((5, 2, 3), (8, 4, 1))
    assert(tsup(f, 0) == 5)
    assert(tsup(f, 1) == 8)
    assert(treg(f, 0) == 2)
    assert(treg(f, 1) == 4)
  }

  test("Prioridad de tablones") {
    val f = Vector((5, 2, 3), (8, 4, 1))
    assert(prio(f, 0) == 3)
    assert(prio(f, 1) == 1)
  }

  test("Generar matriz de distancias simétrica de tamaño 4") {
    val d = distanciaAlAzar(4)
    assert(d.length == 4 && d.forall(_.length == 4))
    for (i <- d.indices; j <- d.indices) {
      if (i == j) assert(d(i)(j) == 0)
      else assert(d(i)(j) == d(j)(i), "La matriz debe ser simétrica")
      assert(d(i)(j) >= 0 && d(i)(j) <= 12) // 4*3=12
    }
  }

  test("Funciones tsup, treg y prio no fallan con índices válidos") {
    val f = fincaAlAzar(3)
    for (i <- 0 until 3) {
      tsup(f, i)
      treg(f, i)
      prio(f, i)
    }
  }
}
