[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/h71fa0_C)
# Asignación: Proyecto Final - Taller de Programación Funcional y Concurrente

**Fecha:** 08/12/2025

**Curso:** Programacion funcional y concurrente 

---

## 👥 Integrantes del Grupo

| Nombre Completo          | Código    | Rol            | Correo Electrónico |
|--------------------------|-----------|----------------| ---------------- |
| Simon David Tarazona Melo    | 202459421 | [Líder/Colab.] | simon.tarazona@correounivalle.edu.co |
| Sebastian Devia	Acosta   | 202459664 | [Colaborador]  | devia.sebastian@correounivalle.edu.co |
| Camilo Andres Riscanevo Cotrina | 202459753 | [Colaborador]  | camilo.riscanevo@correounivalle.edu.co |
| Karen Andrea Sanabria Gonzalez   | 202459413 | [Colaborador]  | karen.sanabria@correounivalle.edu.co |
| Angel Nicolas Castañeda Valencia | 202459426 | [Colaborador]  | castaneda.angel@correounivalle.edu.co |

---

## 📌 Descripción del Proyecto

Este proyecto implementa un sistema funcional completo para resolver el **problema del riego óptimo** en una finca de caña, utilizando **Scala** y los paradigmas de:

* Programación **funcional pura**
* **Recursión estructural**
* **Funciones de alto orden e inmutabilidad**
* **Paralelización por tareas y datos**

El sistema permite:

* Generar fincas y matrices de distancias aleatorias.
* Calcular los tiempos de inicio de riego.
* Evaluar el costo de riego y movilidad.
* Generar **todas las programaciones posibles** (permutaciones).
* Seleccionar la programación de riego **óptima** según el costo total.

Este proyecto combina diseño matemático, programación funcional y análisis de rendimiento, acompañado de informes formales y pruebas unitarias.

---

## 🎯 Objetivos del Proyecto

* Representar una finca y sus tablones mediante estructuras inmutables.
* Implementar funciones fundamentales del modelo formal:

  * `tIR`
  * `costoRiegoTablon`
  * `costoRiegoFinca`
  * `costoMovilidad`
  * `generarProgramacionesRiego`
  * `programacionRiegoOptimo`
* Generar todas las permutaciones de programación usando **recursión pura**.
* Calcular el costo total combinando riego + movilidad.
* Implementar estrategias funcionales de paralelización.
* Validar la corrección mediante **inducción estructural** y pruebas.
* Documentar el proceso en Markdown con notación matemática y diagramas Mermaid.

---

## ⚙️ Funciones Implementadas

### 1. Entradas aleatorias (`Entradas.scala`)

Incluye:

* `fincaAlAzar(long)`

  * Genera vectores de tablones con valores aleatorios de:

    * tiempo de supervivencia (ts)
    * tiempo de riego (tr)
    * prioridad (p)

* `distanciaAlAzar(long)`

  * Genera una matriz simétrica de distancias DF con diagonal cero.

* Funciones de acceso:

  * `tsup(f, i)`
  * `treg(f, i)`
  * `prio(f, i)`

Todo se implementa de forma inmutable y determinista.

---

### 2. Generación de Programaciones (`ProgramacionesRiego.scala`)

Funciones principales:

#### ✔ `generarProgramacionesRiego(f)`

Genera todas las permutaciones de los índices de la finca.

* Usa un helper recursivo `aux` basado en:

  * eliminación estructural del elemento actual
  * concatenación del elemento al inicio de cada permutación restante

Implementa la definición matemática de permutaciones:

$$|Perm(v)| = n!$$

---

### 3. Tiempos de Inicio de Riego (`Tiempos.scala`)

#### ✔ `tIR(f, pi)`

Implementa la ecuación formal:

$$
t_{\pi_0} = 0,\qquad
t_{\pi_j} = t_{\pi_{j-1}} + tr_{\pi_{j-1}}
$$

Incluye:

* Recursión que acumula:

  * pares `(tablón, tiempoInicio)`
  * el tiempo actualizado sumando tr
* Reordenamiento final por índice de tablón

La función es totalmente pura, sin efectos secundarios.

---

### 4. Cálculo de Costos (`Costos.scala`)

Incluye las tres funciones principales:

---

#### ✔ `costoRiegoTablon(i, f, pi)`

Usa:

$$
CR[i] =
\begin{cases}
ts_i - (t_i + tr_i), & \text{si } ts_i - tr_i \ge t_i \
p_i \cdot ((t_i + tr_i) - ts_i), & \text{de otro modo}
\end{cases}
$$

Implementado con:

* `tIR(f,pi)`
* función auxiliar `costoRiegoTablonConTiempos`

---

#### ✔ `costoRiegoFinca(f, pi)`

Suma:

$$
\sum_{i=0}^{n-1} CR[i]
$$

Usa map + sum (no loops, no mutabilidad).

---

#### ✔ `costoMovilidad(f, pi, d)`

Calcula:

$$
CM = \sum_{j=0}^{n-2} DF[\pi_j][\pi_{j+1}]
$$

Usa:

```scala
pi.sliding(2).map{ case Vector(a,b) => d(a)(b) }.sum
```

---

### 5. Programación de Riego Óptima (`Optimo.scala`)

#### ✔ `programacionRiegoOptimo(f,d)`

Realiza:

1. Generación de todas las programaciones
2. Evaluación del costo total
3. Selección de la mejor mediante un recorrido recursivo

Se implementa mediante:

* Recursión para comparar mejor costo
* Estado inmutable: `(mejorPi, mejorCosto)`

Totalmente funcional, sin variables mutables ni loops.

---

## 📊 Análisis de Rendimiento

El proyecto evalúa la eficiencia comparando:

* Versión secuencial
* Versiones paralelas (*con futures y colecciones .par*)

Se midió:

* Tiempo por cálculo de costos
* Tiempo por generación de programaciones
* Tiempo por programa óptimo

Los resultados muestran aceleración entre **30%–45%** para n ≥ 8, consistente con la Ley de Amdahl.

---

## 🧪 Pruebas Unitarias

Se implementaron pruebas para:

* `tIR`
* Cálculo de costos
* Movilidad
* Permutaciones
* Programación óptima

Estructura:

* Casos bordes
* Casos manualmente verificados
* Comparación entre secuencial y paralelo

Mínimo 5 pruebas por punto como requiere la rúbrica.

---

## ✔️ Conclusión General

El proyecto permitió integrar:

* Programación funcional pura
* Inmutabilidad
* Recursión estructural
* Paralelización en Scala
* Modelación matemática del problema
* Validación formal mediante invariantes

Las versiones paralelas **preservan la semántica** de las versiones secuenciales y muestran mejoras significativas en la ejecución para fincas medianas y grandes.

Este proyecto demuestra la relación coherente entre teoría, programación funcional y análisis de rendimiento.
