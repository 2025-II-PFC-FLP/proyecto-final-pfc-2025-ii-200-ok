package taller

object ProgramacionesRiego {

  type Finca = Vector[(Int, Int, Int)]     // (ts, tr, p)
  type ProgRiego = Vector[Int]            // orden de riego

  def generarProgramacionesRiego(f: Finca): Vector[ProgRiego] = {
    val indices: Vector[Int] = f.indices.toVector
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
}
