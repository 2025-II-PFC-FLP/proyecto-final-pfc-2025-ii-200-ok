package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestProgramacionesRiego extends AnyFunSuite {

    val obj: ProgramacionesRiego.type = ProgramacionesRiego

    test("generarProgramacionesRiego con finca vacía debe retornar Vector()") {
      assert(obj.generarProgramacionesRiego(Vector()) == Vector())
    }

    test("generarProgramacionesRiego con finca de 1 tablón debe retornar Vector(Vector(0))") {
      val finca = Vector((5, 3, 1))
      val esperado = Vector(Vector(0))
      assert(obj.generarProgramacionesRiego(finca) == esperado)
    }

    test("generarProgramacionesRiego con 2 tablones debe generar 2 permutaciones") {
      val finca = Vector(
        (5, 2, 1),
        (7, 3, 2)
      )
      val res = obj.generarProgramacionesRiego(finca)

      val esperado = Vector(
        Vector(0, 1),
        Vector(1, 0)
      )

      assert(res.toSet == esperado.toSet)
      assert(res.length == 2)
    }

    test("generarProgramacionesRiego con 3 tablones genera 6 permutaciones") {
      val finca = Vector(
        (5, 2, 1),
        (7, 3, 2),
        (9, 1, 3)
      )

      val res = obj.generarProgramacionesRiego(finca)

      val esperado = Set(
        Vector(0,1,2),
        Vector(0,2,1),
        Vector(1,0,2),
        Vector(1,2,0),
        Vector(2,0,1),
        Vector(2,1,0)
      )

      assert(res.length == 6)
      assert(res.toSet == esperado)
    }

    test("generarProgramacionesRiego no debe repetir permutaciones") {
      val finca = Vector(
        (3,1,1),
        (4,2,2),
        (6,2,3)
      )

      val res = obj.generarProgramacionesRiego(finca)

      assert(res.length == res.toSet.size) // no hay duplicados
    }

    test("generarProgramacionesRiego produce permutaciones válidas (todos los índices presentes)") {
      val finca = Vector(
        (7,2,1),
        (5,1,4),
        (9,3,2),
        (8,2,3)
      )

      val res = obj.generarProgramacionesRiego(finca)

      assert(res.forall(prog => prog.sorted == Vector(0,1,2,3)))
    }

}
