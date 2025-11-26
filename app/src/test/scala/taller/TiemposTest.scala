package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TiemposTest extends AnyFunSuite {
  val obj: Tiempos.type = Tiempos

  test("tIR con finca y programación vacía debe retornar Vector()") {
    assert(obj.tIR(Vector(), Vector()) == Vector())
  }

  test("tIR con un solo tablón debe retornar Vector(0)") {
    val finca = Vector((5, 3, 1))  // (ts, tr, p)
    val pi    = Vector(0)
    assert(obj.tIR(finca, pi) == Vector(0))
  }

  test("tIR con tablones en orden [1,0] asigna tiempos correctos") {
    val finca = Vector(
      (10, 4, 2),  // tablón 0, tr = 4
      (8,  2, 3)   // tablón 1, tr = 2
    )
    val pi = Vector(1, 0)
    // Resultado esperado: [2,0] (tablón 1 empieza en 0, tablón 0 empieza en 2)
    assert(obj.tIR(finca, pi) == Vector(2, 0))
  }

  test("tIR con varios tablones y orden arbitrario") {
    val finca = Vector(
      (7,  3, 1),  // 0 → tr=3
      (12, 2, 4),  // 1 → tr=2
      (5,  4, 2),  // 2 → tr=4
      (9,  1, 3)   // 3 → tr=1
    )
    val pi = Vector(2, 3, 0, 1)
    // Orden: 2(0), 3(4), 0(5), 1(8)
    assert(obj.tIR(finca, pi) == Vector(5, 8, 0, 4))
  }

  test("tIR debe lanzar excepción si pi contiene un índice inexistente") {
    val finca = Vector((5, 3, 1))
    val pi = Vector(0, 2)  // 2 no existe
    intercept[IndexOutOfBoundsException] {
      obj.tIR(finca, pi)
    }
  }

}
