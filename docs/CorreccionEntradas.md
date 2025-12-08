# Informe de Corrección - Generacion de Entradas

---

---
## 1. Corrección de las Funciones Implementadas

### 1.1 Análisis de Corrección Formal

#### Función `tsup(f: Finca, i: Int): Int`

**Definición:**
```scala
def tsup(f: Finca, i: Int): Int =
  if (i >= 0 && i < f.length) f(i)._1 else 0
```

**Prueba de Corrección:**

1. **Precondición:** $f$ es una instancia válida de `Finca = Vector[Tablon]`
2. **Postcondición:** Retorna el primer elemento de la tupla en posición $i$ o 0 si el índice es inválido
3. **Casos:**
    - **Caso base 1:** Si $0 \leq i < f.\text{length}$, retorna $f(i)._1$ ✓
    - **Caso base 2:** Si $i < 0$ o $i \geq f.\text{length}$, retorna 0 ✓
4. **Terminación:** La función siempre termina pues es una expresión condicional simple

#### Función `treg(f: Finca, i: Int): Int`

**Problema Detectado:**
```scala
def treg(f: Finca, i: Int): Int = {
  f(i)._2  // ❌ No verifica límites del índice
}
```

**Incorrección:** Esta función lanzará `IndexOutOfBoundsException` si $i$ está fuera del rango $[0, f.\text{length}-1]$.

**Corrección Propuesta:**
```scala
def treg(f: Finca, i: Int): Int =
  if (i >= 0 && i < f.length) f(i)._2 else 0
```

**Prueba de Corrección de la Versión Corregida:**

1. **Precondición:** $f$ es una instancia válida de `Finca`
2. **Postcondición:** Retorna el segundo elemento de la tupla en posición $i$ o 0 si el índice es inválido
3. **Casos:** Idénticos a `tsup`
4. **Terminación:** Garantizada

#### Función `prio(f: Finca, i: Int): Int`

**Mismo problema que `treg`** - requiere la misma corrección.

---

### 1.2 Propiedades que deben Cumplirse

Para todo $f: \text{Finca}$ y $i: \text{Int}$:

**1. Propiedad de Bounded Access:**

$$\forall f: \text{Finca}, \forall i: \text{Int}, (i \geq 0 \land i < f.\text{length}) \Rightarrow$$
$$\text{tsup}(f, i) = f(i)._1 \land \text{treg}(f, i) = f(i)._2 \land \text{prio}(f, i) = f(i)._3$$

**2. Propiedad de Safe Default:**

$$\forall f: \text{Finca}, \forall i: \text{Int}, (i < 0 \lor i \geq f.\text{length}) \Rightarrow$$
$$\text{tsup}(f, i) = 0 \land \text{treg}(f, i) = 0 \land \text{prio}(f, i) = 0$$

**3. Propiedad de Consistencia de Rangos:**

Para $f = \text{fincaAlAzar}(n)$:

$$\forall i \in [0, n-1], \quad 1 \leq \text{tsup}(f, i) \leq 2n \land 1 \leq \text{treg}(f, i) \leq n \land 1 \leq \text{prio}(f, i) \leq 4$$

---

### 1.3 Corrección de `fincaAlAzar(long: Int): Finca`

**Prueba por Inducción Estructural:**

- **Caso base:** $\text{long} = 0 \Rightarrow \text{Vector.fill}(0)(...) = \text{Vector}()$ ✓
- **Hipótesis inductiva:** Para $\text{long} = k$, se genera Vector de tamaño $k$
- **Paso inductivo:** Para $\text{long} = k+1$:

$$\text{Vector.fill}(k+1)(\text{generador}) = \text{Vector.fill}(k)(\text{generador}) \mathbin{:\!+} \text{generador}()$$

Por HI, $\text{Vector.fill}(k)(\text{generador})$ tiene tamaño $k$, al añadir un elemento resulta en tamaño $k+1$ ✓

**Propiedades del Generador:**

1. **Tamaño Correcto:** $\forall n \geq 0, |\text{fincaAlAzar}(n)| = n$
2. **Rangos Válidos:** Cada tablón $(ts, tr, p)$ cumple:
    - $ts \in [1, 2n]$ (pues `random.nextInt(n*2) + 1`)
    - $tr \in [1, n]$ (pues `random.nextInt(n) + 1`)
    - $p \in [1, 4]$ (pues `random.nextInt(4) + 1`)

---

### 1.4 Corrección de `distanciaAlAzar(long: Int): Distancia`

**Propiedades:**

1. **Matriz Cuadrada:**
   $$\forall n \geq 0, |\text{distanciaAlAzar}(n)| = n \land \forall i, |\text{distanciaAlAzar}(n)(i)| = n$$

2. **Diagonal Cero:**
   $$\forall i \in [0, n-1], \text{distanciaAlAzar}(n)(i)(i) = 0$$

3. **Simetría:**
   $$\forall i,j \in [0, n-1], \text{distanciaAlAzar}(n)(i)(j) = \text{distanciaAlAzar}(n)(j)(i)$$

4. **No Negatividad:**
   $$\forall i,j \in [0, n-1], \text{distanciaAlAzar}(n)(i)(j) \geq 0$$

5. **Cota Superior:**
   $$\forall i,j \in [0, n-1], \text{distanciaAlAzar}(n)(i)(j) \leq 3n$$

**Prueba de Simetría:**

Para $i \neq j$:
- Si $i < j$: usa $\text{base}(i)(j)$
- Si $i > j$: usa $\text{base}(j)(i)$ que es el mismo valor por definición de `base` ✓

---

---

## 2. Conjunto de Pruebas

### 2.1 Pruebas Unitarias Adicionales Necesarias

```scala
test("Funciones de acceso con índices inválidos deben retornar 0") {
  val f = Vector((5, 2, 3), (8, 4, 1))
  
  // Índices negativos
  assert(tsup(f, -1) == 0)
  assert(treg(f, -1) == 0)
  assert(prio(f, -1) == 0)
  
  // Índices mayores que el tamaño
  assert(tsup(f, 5) == 0)
  assert(treg(f, 5) == 0)
  assert(prio(f, 5) == 0)
}

test("Generar finca de tamaño 0") {
  val f = fincaAlAzar(0)
  assert(f.length == 0)
}

test("Propiedad: todos los tablones tienen rangos válidos") {
  for (n <- 1 to 10) {
    val f = fincaAlAzar(n)
    f.foreach { case (ts, tr, p) =>
      assert(ts >= 1 && ts <= 2*n, s"ts=$ts debe estar en [1, ${2*n}]")
      assert(tr >= 1 && tr <= n, s"tr=$tr debe estar en [1, $n]")
      assert(p >= 1 && p <= 4, s"p=$p debe estar en [1, 4]")
    }
  }
}

test("Matriz de distancias cumple propiedades básicas") {
  val d = distanciaAlAzar(5)
  
  // Reflexividad
  for (i <- 0 until 5) {
    assert(d(i)(i) == 0)
  }
  
  // Simetría
  for (i <- 0 until 5; j <- 0 until 5) {
    assert(d(i)(j) == d(j)(i))
  }
  
  // No negatividad
  for (i <- 0 until 5; j <- 0 until 5) {
    assert(d(i)(j) >= 0)
  }
}

test("Propiedad: funciones de acceso son consistentes") {
  val f = fincaAlAzar(5)
  for (i <- 0 until 5) {
    val tablon = f(i)
    assert(tsup(f, i) == tablon._1)
    assert(treg(f, i) == tablon._2)
    assert(prio(f, i) == tablon._3)
  }
}
```

### 2.2 Pruebas de Esquina (Edge Cases)

```scala
test("Finca con un solo tablón") {
  val f = fincaAlAzar(1)
  assert(f.length == 1)
  assert(tsup(f, 0) == f(0)._1)
  assert(tsup(f, 1) == 0) // Índice fuera de rango
}

test("Matriz 1x1") {
  val d = distanciaAlAzar(1)
  assert(d.length == 1)
  assert(d(0).length == 1)
  assert(d(0)(0) == 0)
}

test("Funciones con Vector vacío") {
  val f = Vector.empty[Tablon]
  assert(tsup(f, 0) == 0)
  assert(treg(f, 0) == 0)
  assert(prio(f, 0) == 0)
}
```

---

---

## 3. Análisis de Complejidad

### 3.1 Complejidad Temporal

| Función | Complejidad | Justificación |
|---------|-------------|---------------|
| `fincaAlAzar(n)` | $O(n)$ | `Vector.fill(n)` es $O(n)$ |
| `distanciaAlAzar(n)` | $O(n^2)$ | `Vector.tabulate(n, n)` es $O(n^2)$ |
| `tsup(f, i)` | $O(1)$ | Acceso por índice en Vector |
| `treg(f, i)` | $O(1)$ | Acceso por índice en Vector |
| `prio(f, i)` | $O(1)$ | Acceso por índice en Vector |

### 3.2 Complejidad Espacial

| Función | Espacio | Justificación |
|---------|---------|---------------|
| `fincaAlAzar(n)` | $O(n)$ | Almacena $n$ tuplas |
| `distanciaAlAzar(n)` | $O(n^2)$ | Almacena matriz $n \times n$ |

---

---

## 4. Correcciones Aplicadas

### 4.1 Correcciones Realizadas

1. **Función `treg`:** Añadida verificación de límites
2. **Función `prio`:** Añadida verificación de límites
3. **Consistencia:** Las tres funciones ahora tienen el mismo patrón de verificación

### 4.2 Código Corregido

```scala
// Tiempo de supervivencia del tablón i
def tsup(f: Finca, i: Int): Int =
  if (i >= 0 && i < f.length) f(i)._1 else 0

// Tiempo de riego del tablón i
def treg(f: Finca, i: Int): Int =
  if (i >= 0 && i < f.length) f(i)._2 else 0

// Prioridad del tablón i
def prio(f: Finca, i: Int): Int =
  if (i >= 0 && i < f.length) f(i)._3 else 0
```

### 4.3 Verificación de la Corrección

La corrección se verifica mediante:

1. **Pruebas unitarias existentes:** Siguen pasando
2. **Nuevas pruebas:** Verifican los casos límite
3. **Propiedades matemáticas:** Se mantienen invariantes

**Teorema:** Las funciones corregidas son totales (siempre retornan un valor) y seguras (no lanzan excepciones para cualquier entrada).

**Demostración:** Para cualquier entrada $(f: \text{Finca}, i: \text{Int})$:

- La condición `if (i >= 0 && i < f.length)` evalúa a `true` o `false`
- Si `true`: retorna $f(i)._k$ que existe pues $i$ es índice válido
- Si `false`: retorna 0 que siempre está definido

$\therefore$ La función siempre termina con un valor entero. $\square$

---

---


## 5. Limitaciones y Mejoras Futuras

### 5.1 Limitaciones Actuales

1. **Distancia aleatoria:** No garantiza ser una métrica (no cumple necesariamente desigualdad triangular)
2. **Generación independiente:** Los tiempos de supervivencia y riego no están correlacionados
3. **Valor por defecto 0:** Podría no ser semánticamente correcto para todos los contextos

### 5.2 Sugerencias de Mejora

**1. Usar `Option[Int]`** en lugar de retornar 0 para índices inválidos:

```scala
def tsupOpt(f: Finca, i: Int): Option[Int] =
  if (i >= 0 && i < f.length) Some(f(i)._1) else None
```

**2. Añadir restricciones** a la generación de distancias para que sea métrica:

Para que $d$ sea una métrica, debe cumplir la desigualdad triangular:

$$\forall i, j, k: \quad d(i,j) \leq d(i,k) + d(k,j)$$

**3. Incluir correlaciones** realistas entre $ts$, $tr$ y prioridad

---

---

## Conclusión


El código actual es correcto dentro de sus especificaciones, pero las correcciones aplicadas lo hacen más robusto frente 
a entradas inesperadas. Las funciones de acceso ahora son totales y seguras, garantizando que nunca lanzarán excepciones 
por acceso a índices inválidos.