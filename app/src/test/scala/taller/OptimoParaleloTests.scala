package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class OptimoParaleloTests extends AnyFunSuite {

  type Finca = Vector[(Int, Int, Int)]
  type Distancia = Vector[Vector[Int]]

  private def dist(n: Int): Distancia =
    Vector.tabulate(n, n)((i,j) => if (i == j) 0 else (i+j)+1)

  test("optimo paralelo coincide con secuencial") {
    val f = Vector((10,3,1),(12,4,2),(8,1,3))
    val d = dist(3)

    val (piPar, _) = Paralelo.programacionRiegoOptimoPar(f, d)
    val (piSeq, _) = Optimo.programacionRiegoOptimo(f, d)

    assert(piPar == piSeq)
  }

  test("optimoPar - movilidad variable") {
    val f = Vector((6,2,1),(10,3,4),(9,1,2))
    val d = dist(3)

    val (piPar, _) = Paralelo.programacionRiegoOptimoPar(f, d)
    val (piSeq, _) = Optimo.programacionRiegoOptimo(f, d)

    assert(piPar == piSeq)
  }

  test("optimoPar - caso simple de dos tablones con solución única") {
    val f = Vector(
      (5, 1, 3),
      (10, 2, 1)
    )

    val d = Vector(
      Vector(0, 4),
      Vector(4, 0)
    )
    
    val (piPar, costoPar) = Paralelo.programacionRiegoOptimoPar(f, d)
    val (piSeq, costoSeq) = Optimo.programacionRiegoOptimo(f, d)

    assert(piPar == piSeq)
    assert(costoPar == costoSeq)
  }


  test("optimoPar - vacío") {
    assertThrows[NoSuchElementException] {
      Optimo.programacionRiegoOptimo(Vector.empty, Vector.empty)
    }
    val (piPar, _) = Paralelo.programacionRiegoOptimoPar(Vector.empty, Vector.empty)
    assert(piPar.isEmpty)
  }

  test("optimoPar - aleatorio") {
    val f = Vector((10,3,1),(9,2,3),(12,3,2),(8,1,4))
    val d = dist(4)

    val (piPar, _) = Paralelo.programacionRiegoOptimoPar(f, d)
    val (piSeq, _) = Optimo.programacionRiegoOptimo(f, d)

    assert(piPar == piSeq)
  }
}
