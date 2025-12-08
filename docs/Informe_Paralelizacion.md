#  **# Informe de Paralelización — Programación de Riego**


# 1. **Introducción**

El presente informe documenta el proceso de **paralelización del sistema de cálculo de programaciones de riego** presentado en el proyecto. El sistema original contaba únicamente con implementaciones **secuenciales**, mientras que el objetivo fue implementar alternativas **paralelas**, analizar su comportamiento y compararlas mediante _benchmarking_ usando **ScalaMeter**.

La paralelización se aplicó a cuatro componentes principales:

1.  **Cálculo del Tiempo de Inicio de Riego (tIR)**
    
2.  **Cálculo del costo total (Costos de riego + movilidad)**
    
3.  **Generación de permutaciones (programaciones de riego)**
    
4.  **Búsqueda de la programación óptima**
    

Finalmente, se analizaron los resultados experimentales y se calculó el **speedup** para cada módulo y tamaño de entrada.

----------

# 2. **Paralelización realizada**

A continuación, se describe la estrategia utilizada en cada función paralela del archivo `Paralelo.scala`.

----------

## 2.1 `tIRPar`: Cálculo paralelo del tiempo de inicio de riego

### 🔧 Estrategia aplicada

-   Se usa un **arreglo buffer compartido** de tamaño `n`.
    
-   Se calcula el vector de tiempos acumulados secuencialmente (su costo es insignificante).
    
-   Se paraleliza únicamente la distribución de los tiempos sobre los tablones usando:
    

```scala
pi.zip(acumulados.init).par.foreach(...)
```

###  Razonamiento

La operación crucial (asignar el tiempo a cada tablón) es independiente, perfecta para map paralelo.

### ✔ Complejidad

-   Secuencial: $O(n)$
    
-   Paralela: $O(n / p) + overhead$
    

----------

## 2.2 `costoRiegoFincaPar` y `costoMovilidadPar`

### Estrategia aplicada

-   Ambos usan **map paralelo**, ya que el costo de cada tablón y cada transición es independiente:
    

```scala
tiempos.indices.toVector.par.map(...)
pi.sliding(2).toVector.par.map(...)
```

### ✔ Complejidad

-   Secuencial: $O(n)$
    
-   Paralela: $O(n/p) + overhead$
    

----------

## 2.3 `generarProgramacionesRiegoPar`

###  Estrategia aplicada

El módulo más costoso del sistema es la generación de permutaciones, cuyo costo es factorial:

-   Se paraleliza la rama superior del árbol de recursión:
    

```scala
v.par.flatMap { elem => ... }
```

Cada rama genera `aux(resto)`, que sigue siendo recursivo y costoso.

###  Razonamiento

Paralelizar en niveles muy profundos solo aumenta overhead.  
Por eso se paraleliza solo **la capa superior** (similar al patrón "parallel-map" de permutaciones).

----------

## 2.4 `programacionRiegoOptimoPar`

###  Estrategia aplicada

Consiste en:

1.  Generar todas las programaciones (paralelo)
    
2.  Calcular sus costos (map paralelo)
    

```scala
todas.par.map(pi => (pi, costoTotalPar(f, pi, d)))
```

### ✔ Complejidad

-   Secuencial: $O(n! × n)$
    
-   Paralela: $O((n! × n)/p) + overhead$
    

----------

# 3. **Benchmarking y Speedup**

Los datos fueron obtenidos con ScalaMeter evaluando tres tamaños:  
**n = 6, 7 y 8 tablones.**

----------

##  **Tabla Comparativa con Speedup**

> **Speedup = tSecuencial / tParalelo**

###  **Resultados completos**


| n  | Módulo          | Secuencial (ms) | Paralelo (ms) | Speedup |
|----|------------------|------------------|----------------|---------|
| **6** | Tiempos        | 0.209           | 1.0032         | 0.20×   |
|   -  | Programaciones  | 1.5434          | 5.1659         | 0.30×   |
|   -  | Costos          | 0.2143          | 1.7886         | 0.12×   |
|   -  | Óptimo          | 3.6129          | 9.2645         | 0.39×   |
| **7** | Tiempos        | 0.0032          | 0.0769         | 0.04×   |
|   - | Programaciones   | 3.5706          | 4.0445         | 0.88×   |
|   - | Costos           | 0.0076          | 0.3835         | 0.02×   |
|   - | Óptimo           | 18.7852         | 77.8895        | 0.24×   |
| **8** | Tiempos        | 0.0042          | 0.0719         | 0.06×   |
|   - | **Programaciones**   | **29.1254**     | **21.5941**    | **1.34×**|
|   - | Costos           | 0.0068          | 0.1912         | 0.03×   |
|   - | **Óptimo**           | **183.5716**        | **174.384**        | **1.05×**|


----------

# 4. **Interpretación de Resultados**

## 4.1 Módulos lineales (O(n))

-   `tIR`, `costos`, `movilidad`  
    Tienen sobrecosto por:
    
-   creación del scheduler
    
-   bifurcación de threads
    
-   sincronización implícita
    

 **Conclusión:** Para operaciones pequeñas, el paralelismo **no vale la pena**.

----------

## 4.2 Generación de permutaciones

El comportamiento mejora a partir de **n = 8**, donde el paralelismo obtiene:

-   **Speedup = 1.34×**
    

 Esto es consistente con la naturaleza factorial del problema.  
A mayor cantidad de trabajo, más se amortiza el overhead.

----------

## 4.3 Cálculo de programación óptima

Este módulo depende directamente de:

-   Generación de permutaciones
    
-   Cálculo de costos
    

Cuando `n=8`, los tiempos ya son suficientemente grandes para que el paralelismo sea rentable:

-   Speedup **1.05×**
    

Aunque pequeño, indica que para entradas mayores, la versión paralela mejorará cada vez más.

----------

# 5. Conclusiones Generales

✔ La paralelización se aplicó correctamente a las partes independientes del algoritmo  
✔ Los módulos **lineales** no se benefician debido al bajo costo computacional  
✔ Los módulos **factoriales** sí muestran mejora al aumentar n  
✔ El comportamiento obtenido coincide con los fundamentos teóricos del paralelismo  
✔ El diseño del paralelismo respeta el modelo “divide and conquer” y “parallel-map”

###  Conclusión principal

> La paralelización en este proyecto es **efectiva únicamente para los componentes con complejidad factorial**, donde la cantidad de trabajo supera ampliamente el costo de administración del paralelismo.
    
---------