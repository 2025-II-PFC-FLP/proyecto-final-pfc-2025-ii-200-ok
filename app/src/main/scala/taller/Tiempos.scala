package taller
object Tiempos {

  type Finca = Vector[(Int, Int, Int)]      // (ts, tr, p)
  type ProgRiego = Vector[Int]              // orden de riego
  type TiempoInicioRiego = Vector[Int]      // vector de tiempos

  def tIR(f: Finca, pi: ProgRiego): TiempoInicioRiego = {
    def aux(resto: ProgRiego, tiempoActual: Int, acc: Vector[(Int, Int)]): Vector[(Int, Int)] =
      resto match {
        case Vector() => acc
        case primero +: tail =>
          val (_, tr, _) = f(primero)
          val nuevoAcc = acc :+ (primero, tiempoActual)
          aux(tail, tiempoActual + tr, nuevoAcc)
      }
    val tiempos = aux(pi, 0, Vector())
    tiempos.sortBy(_._1).map(_._2)
  }
}
