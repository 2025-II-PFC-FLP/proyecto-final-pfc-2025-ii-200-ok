
#  **INFORME DE CORRECCIÓN — Módulo Paralelo**


## 1. Objetivo del informe
Este informe analiza la **corrección funcional** de las funciones paralelas implementadas en  
`Paralelo.scala`, comparándolas con sus versiones secuenciales y verificando que:

- Producen **resultados equivalentes** a las funciones originales.  
- Cumplen las **precondiciones** y **postcondiciones** del proyecto.  
- Respetan la definición matemática del modelo (tiempos, costos, distancia y óptimo).  
- No introducen errores de concurrencia ni comportamientos no deterministas.  
- Mantienen la **correspondencia semántica** entre la versión secuencial y la paralela.

El análisis se basa en especificación formal, razonamiento lógico y comprobaciones empíricas mediante tests y benchmarking.

---

# 2. Especificación formal del módulo

A continuación se describen formalmente las funciones principales del archivo `Paralelo.scala`.

---

## 2.1 Función `tIRPar(f,π)`
**Propósito:** calcular los tiempos de inicio de riego en paralelo.

### Especificación matemática
Para cada tablón $i$ en el orden dado por $\pi$:

$$
t_i = \sum_{k=0}^{pos_{\pi}(i)-1} tr_{\pi(k)}
$$

donde:

- $tr_j$ es el tiempo de riego del tablón $j$
- $pos_{\pi}(i)$ es la posición donde aparece $i$ en la permutación $\pi$

### Precondiciones
- $\pi$ es una permutación válida de los índices de la finca.
- $|f| = |\pi|$

### Postcondición
- El vector devuelto tiene tamaño $|f|$.
- $t_i$ coincide exactamente con la definición matemática anterior.

### Corrección
`tIRPar` conserva la lógica secuencial porque:

1. Usa `scanLeft` para calcular acumulados (sin paralelizar esa parte).  
2. Solo paraleliza la asignación del acumulado al índice correspondiente, operación sin dependencias.  
3. No hay modificaciones concurrentes sobre la misma celda del buffer.

Por tanto:

$$
tIRPar(f,\pi) = tIR(f,\pi)
$$

---

## 2.2 Función `costoRiegoFincaPar`

### Definición matemática

El costo total de riego es:

$$
C_r(f,\pi) = \sum_{i=0}^{n-1} c_i
$$

donde:

$$
c_i =
\begin{cases}
ts_i - (t_i + tr_i), & \text{si } t_i \le ts_i - tr_i \\
p_i \cdot \big( (t_i + tr_i) - ts_i \big), & \text{si } t_i > ts_i - tr_i
\end{cases}
$$

### Corrección
Cada $c_i$ depende únicamente de:

- valores de `f(i)`
- el tiempo ya calculado `t_i`
- sin efectos colaterales

Paralelizar el mapeo:
```scala
tiempos.indices.par.map(...)
```
es seguro, porque cada evaluación es independiente.  
Por tanto:

$$
costoRiegoFincaPar(f,\pi) = costoRiegoFinca(f,\pi)
$$

---

## 2.3 Función `costoMovilidadPar`

### Definición matemática

$$
C_m(f,\pi,d) = \sum_{k=0}^{n-2} d(\pi_k,\pi_{k+1})
$$

### Corrección

- Cada término depende solo del par $(\pi_k,\pi_{k+1})$  
- No hay dependencia entre iteraciones  
- Paralelizar el cálculo no altera el resultado

Por tanto:

$$
costoMovilidadPar = costoMovilidad
$$

---

## 2.4 Función `costoTotalPar`

Por definición:

$$
costoTotalPar = costoRiegoFincaPar + costoMovilidadPar
$$

Dado que ambas son correctas de forma independiente:

$$
costoTotalPar(f,\pi,d) = costoTotal(f,\pi,d)
$$

---

## 2.5 Función `generarProgramacionesRiegoPar`

### Definición matemática

Sea:

$$
\Pi(f) = \text{todas las permutaciones de } \{0,1,\ldots,n-1\}
$$

La función debe cumplir:

$$
generarProgramacionesRiegoPar(f) = \Pi(f)
$$

### Corrección

- La función paraleliza únicamente el nivel superior del árbol de permutaciones.
- Cada rama generada para un “primer elemento” es independiente.
- `aux(resto)` conserva la lógica secuencial (no paralelizada recursivamente), lo cual evita explosión de tareas.

Por tanto:

$$
\text{El paralelismo no altera el conjunto de permutaciones.}
$$

---

## 2.6 Función `programacionRiegoOptimoPar`

### Definición matemática

El óptimo es:

$$
\pi^* = \arg\min_{\pi \in \Pi(f)} costoTotal(f,\pi,d)
$$

La función paralela implementa:

$$
programacionRiegoOptimoPar(f,d) =
\min_\pi \big( costoTotalPar(f,\pi,d) \big)
$$

### Corrección

Dado que:

- $costoTotalPar = costoTotal$
- $generarProgramacionesRiegoPar = \Pi(f)$

entonces:

$$
programacionRiegoOptimoPar(f,d) = programacionRiegoOptimo(f,d)
$$

---

# 3. Ausencia de Errores de Concurrencia

Todas las funciones cumplen:

1. **Operaciones independientes**  
   Los cálculos paralelos no comparten estado mutable salvo el buffer inicializado con escritura en posiciones distintas.

2. **No hay dependencias de orden**  
   Los sumatorios y mapeos son operaciones conmutativas y paralelizables.

3. **No hay condiciones de carrera**  
   Ninguna celda del arreglo es escrita por más de un hilo.

4. **Funciones puras**  
   Los cálculos sobre cada elemento son deterministas y no afectan a otros elementos.

---

# 4. Validación empírica

Los tests verifican:

- Igualdad entre versiones secuenciales/paralelas  
- Casos base: finca vacía, permutación trivial  
- Casos pequeños donde el óptimo es calculable manualmente  
- Casos aleatorios comparando exactitud secuencial vs paralela

Además, el benchmarking confirma que:

- La paralela produce los **mismos resultados**
- La diferencia está solo en el tiempo de ejecución (no en exactitud)

Esto completa la demostración de corrección práctica.

---

# 5. Conclusión

El módulo `Paralelo.scala`:

- Respeta todas las especificaciones matemáticas del proyecto  
- Mantiene equivalencia con las funciones secuenciales  
- Es seguro frente a errores de concurrencia  
- Su paralelización es coherente y no altera resultados  
- Fue validado por pruebas unitarias y benchmarking

Por tanto, **cumple los requisitos de corrección exigidos por el proyecto**.

----------