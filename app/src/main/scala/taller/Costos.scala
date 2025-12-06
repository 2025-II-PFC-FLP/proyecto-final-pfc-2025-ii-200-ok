package taller

object Costos {

  type Finca = Vector[(Int, Int, Int)]       // (ts, tr, p)
  type ProgRiego = Vector[Int]               // orden de riego (permutación)
  type Distancia = Vector[Vector[Int]]       // matriz de distancias (simétrica)
  type TiempoInicioRiego = Vector[Int]

  import Tiempos.tIR

  private def costoRiegoTablonConTiempos(i: Int, f: Finca, tiempos: TiempoInicioRiego): Int = {
    val (ts, tr, p) = f(i)
    val tInicio = tiempos(i)
    if (ts - tr >= tInicio) ts - (tInicio + tr)
    else p * ((tInicio + tr) - ts)
  }

  def costoRiegoTablon(i: Int, f: Finca, pi: ProgRiego): Int = {
    val tiempos = tIR(f, pi)
    costoRiegoTablonConTiempos(i, f, tiempos)
  }

  def costoRiegoFinca(f: Finca, pi: ProgRiego): Int = {
    val tiempos = tIR(f, pi)                         // se calcula una vez (O(n))
    tiempos.indices.toVector                         // Vector de índices 0..n-1
      .map(i => costoRiegoTablonConTiempos(i, f, tiempos))
      .sum
  }

  def costoMovilidad(f: Finca, pi: ProgRiego, d: Distancia): Int = {
    pi.sliding(2).toVector
      .map { case Vector(a, b) => d(a)(b) }
      .sum
  }

  def costoTotal(f: Finca, pi: ProgRiego, d: Distancia): Int = {
    costoRiegoFinca(f, pi) + costoMovilidad(f, pi, d)
  }
}
