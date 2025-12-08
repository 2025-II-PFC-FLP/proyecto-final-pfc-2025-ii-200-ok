package taller

object  Optimo {

  import Costos._
  import ProgramacionesRiego._

  def programacionRiegoOptimo(f:Finca, d:Distancia):(ProgRiego,Int) = {
    val todas = generarProgramacionesRiego(f)

    def aux(pendientes: Vector[ProgRiego],mejorPi:ProgRiego,mejorCosto: Int): (ProgRiego,Int)= pendientes match{
      case Vector() => (mejorPi,mejorCosto)
      case pi +: resto =>
          val costoActual = costoTotal(f,pi,d)
          if (costoActual < mejorCosto)
              aux(resto,pi,costoActual)
           else
              aux(resto, mejorPi, mejorCosto)

    }

    val primera = todas.head
    val costoPrimera = costoTotal(f,primera,d)
    aux(todas.tail,primera,costoPrimera)

  }
}