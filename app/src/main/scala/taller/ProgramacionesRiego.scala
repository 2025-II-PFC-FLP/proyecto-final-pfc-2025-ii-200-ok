package taller

object ProgramacionesRiego {


  type Finca = Vector[(Int, Int, Int)]
  type ProgRiego = Vector[Int]

  /**
   * Genera todas las posibles programaciones de riego (permutaciones)
   * para una finca dada.
   *
   * @param f finca (solo importa su tamaño)
   * @return vector con todas las programaciones posibles
   */
  def generarProgramacionesRiego(f: Finca): Vector[ProgRiego] = {

    val indices: Vector[Int] = f.indices.toVector

    /**
     * Función auxiliar recursiva para generar permutaciones.
     * @param v vector de números por permutar
     * @return todas las permutaciones del vector v
     */
    def aux(v: Vector[Int]): Vector[Vector[Int]] = v match {

      case Vector(x) => Vector(Vector(x))
      case _ =>

        v.flatMap { elem =>

          val resto = v.filterNot(_ == elem)

          val permsResto = aux(resto)

          permsResto.map(p => elem +: p)

        }

    }

    aux(indices)

  }

  def programacionRiegoOptimo(f:Finca,d: Distancia): (ProgRiego,Int) = {

  }


}
