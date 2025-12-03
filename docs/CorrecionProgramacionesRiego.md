# **INFORME DE CORRECCIÓN PROGRAMACIONES DE RIEGO**

Función `generarProgramacionesRiego`**

La siguiente sección presenta la corrección formal y práctica de la función:

```scala
def generarProgramacionesRiego(f: Finca): Vector[ProgRiego]
```

# 1. ✔ **Especificación formal**

### **Tipo:**

`generarProgramacionesRiego : Finca → Vector[ProgRiego]`

### **Entrada:**

* `f` — un vector cuyos elementos representan tablones de la forma `(ts, tr, p)`.
  *(Solo se utiliza `f.indices` para obtener la cantidad de tablones.)*

### **Salida:**

Un `Vector[ProgRiego]` que contiene **todas las permutaciones posibles** de los índices de la finca.

### **Precondiciones:**

1. `f.length ≥ 0`
2. Los valores `(ts, tr, p)` no afectan el cálculo.

### **Postcondiciones:**

El resultado `P` cumple:

1. `|P| = n!`
2. Cada `prog ∈ P` es una permutación de `{0,1,…,n−1}`
3. `P` no contiene duplicados
4. Si `f = Vector()`, entonces `P = Vector()`

---

# 2. ✔ **Definición matemática de la función**

Sea:

$$indices = [0,1,\ldots,n-1]$$

La función auxiliar `aux(v)` está definida por:

### Caso base:

$$aux([x]) = [[x]]$$

### Caso general:

$$aux(v) = \bigcup_{e \in v} { e :: p \mid p \in aux(v - {e}) }$$

La función completa es:

$$generarProgramacionesRiego(f) = aux(indices)$$

---

# 3. ✔ **Invariante recursivo**

En cada llamada `aux(v)`, se mantiene el siguiente invariante:

### **Invariante I(v):**

1. Todas las permutaciones generadas contienen exactamente los elementos de `v`
2. No existen permutaciones duplicadas
3. Ninguna permutación contiene elementos repetidos
4. Cada permutación tiene longitud `|v|`
5. El número total de permutaciones generadas es `|v|!`

Este invariante asegura que en cada paso recursivo se conserva la corrección estructural de las permutaciones.

---

# 4. ✔ **Corrección parcial**

Si la función termina, el resultado es correcto porque:

### 1. **Caso base correcto**

`aux(Vector(x)) = Vector(Vector(x))`
Produce la única permutación posible.

### 2. **Construcción correcta de permutaciones**

`v.flatMap { elem => aux(resto).map(elem +: _) }` garantiza:

* todas las permutaciones del resto son generadas,
* el elemento que se extrae se antepone exactamente a cada permutación del resto,
* no se generan duplicados.

### 3. **No se pierden permutaciones**

La unión mediante `flatMap` incluye:

* todas las permutaciones que empiezan con cada elemento,
* ninguna permutación queda por fuera.

### 4. **La salida cumple la postcondición**

El resultado contiene:

* todas las permutaciones posibles,
* con las longitudes correctas,
* sin repetir permutaciones.

Por lo tanto, si la función finaliza, la salida es correcta.

---

# 5. ✔ **Corrección total (inducción estructural)**

### **Caso base:**

Si `v = Vector(x)`:

$$aux([x]) = [[x]]$$

Correcto: hay una sola permutación.

---

### **Paso inductivo:**

Supóngase correcta la función para todo vector de tamaño `n-1`.

Sea `v` un vector de tamaño `n ≥ 2`.

Cada llamada:

1. selecciona un elemento `elem`,
2. calcula `aux(resto)` donde `|resto| = n-1`,
3. antepone `elem` a cada permutación de `aux(resto)`.

Por hipótesis inductiva:

* cada `aux(resto)` es correcto,
* produce `(n−1)!` permutaciones,
* ninguna es incorrecta ni duplicada.

Por tanto, para `v`:

$$n \cdot (n-1)! = n!$$

Permutaciones válidas y completas.

Cada llamada reduce el tamaño de `v`, así que la recursión **siempre termina**.

✔ **Por inducción, la función es correcta para cualquier tamaño de entrada.**

---

# 6. ✔ **Corrección mediante pruebas unitarias (ScalaTest)**

Las pruebas incluidas en `TestProgramacionesRiego` validan la implementación desde distintos ángulos:

---

## 6.1 Caso base: finca vacía

La función debe retornar `Vector()`.

Esto confirma que:

* no intenta permutar índices inexistentes,
* respeta la postcondición para $n=0$.

✔ **Aprobado**

---

## 6.2 Un solo tablón

Se espera exactamente:

```
Vector(Vector(0))
```

Valida:

* el caso base recursivo,
* no se producen elementos extra.

✔ **Aprobado**

---

## 6.3 Finca con 2 tablones

Confirma:

* número correcto de permutaciones $(2)$,
* contenido correcto,
* orden sin importancia (se compara como conjuntos).

✔ **Aprobado**

---

## 6.4 Finca con 3 tablones (6 permutaciones)

Prueba importante: verifica que el resultado:

* tenga tamaño 6,
* coincida exactamente con las 6 permutaciones posibles,
* no falte ninguna.

✔ **Aprobado**

---

## 6.5 No existan permutaciones duplicadas

Validación:

```scala
assert(res.length == res.toSet.size)
```

Demuestra:

* `flatMap` está usado correctamente,
* no se generan combinaciones repetidas.

✔ **Aprobado**

---

## 6.6 Cada permutación contiene todos los índices

Tipo de validación estructural:

```scala
prog.sorted == Vector(0,1,2,3)
```

Garantiza que:

* no hay índices perdidos,
* no hay índices inválidos,
* todas las permutaciones son completas.

✔ **Aprobado**

---

# 7. **Conclusión general**

Después de analizar:

* la especificación formal,
* la definición matemática,
* el invariante recursivo,
* la corrección parcial,
* la corrección total por inducción,
* y la verificación experimental con pruebas,

se concluye que la función:

```
generarProgramacionesRiego(f)
```

✔ Genera correctamente todas las permutaciones
✔ No repite ninguna
✔ Cumple las precondiciones y postcondiciones
✔ Es formalmente correcta
✔ Está completamente validada mediante pruebas unitarias

**Por tanto, la implementación es correcta y completa.**


