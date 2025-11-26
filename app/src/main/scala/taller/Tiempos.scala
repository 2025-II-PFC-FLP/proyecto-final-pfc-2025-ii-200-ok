package taller

object Tiempos {

  type Finca = Vector[(Int, Int, Int)]      // (ts, tr, p)
  type ProgRiego = Vector[Int]              // orden de riego
  type TiempoInicioRiego = Vector[Int]      // vector de tiempos

  /**
   * Calcula el tiempo de inicio del riego para cada tablón,
   * dadas la finca y la programación de riego.
   *
   * @param f  finca con los datos de cada tablón
   * @param pi orden de riego (programación)
   * @return vector de tiempos de inicio del riego para cada tablón
   */
  def tIR(f: Finca, pi: ProgRiego): TiempoInicioRiego = {

    /**
     * Función auxiliar recursiva.
     * Acumula pares (tablón, tiempoInicio) en orden de riego,
     * mientras actualiza el tiempo según la duración del riego.
     */
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
