package taller

import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class BenchmarkingTests extends AnyFunSuite {
  test("generarFinca produce datos aleatorios y válidos") {
    val f1 = Benchmarking.generarFinca(5)
    val f2 = Benchmarking.generarFinca(5)

    assert(f1.size == 5)
    assert(f2.size == 5)

    assert(f1.forall { case (ts, tr, p) =>
      ts >= 5 && ts <= 20 &&
      tr >= 1 && tr <= 5 &&
      p  >= 1 && p  <= 10
    })

    assert(f1 != f2)   //muy poco probable que salgan idénticos
  }

  test("generarFinca tamaño correcto") {
    val f = Benchmarking.generarFinca(5)
    assert(f.size == 5)
  }

  test("generarDistancias NxN") {
    val d = Benchmarking.generarDistancias(4)
    assert(d.size == 4)
    assert(d.forall(_.size == 4))
  }

  test("generarDistancias simétrica") {
    val d = Benchmarking.generarDistancias(5)
    for (i <- 0 until 5; j <- 0 until 5)
      assert(d(i)(j) == d(j)(i))
  }

  test("generarFinca valores dentro del rango esperado") {
    val f = Benchmarking.generarFinca(10)
    assert(f.forall { case (ts, tr, p) =>
      ts >= 5 && ts <= 20 &&
      tr >= 1 && tr <= 5 &&
      p  >= 1 && p  <= 10
    })
  }
}
