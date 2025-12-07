# 📝 Informe de Corrección – Función `tIR`

## 1. Especificación formal

**Tipo:**
tIR : Finca × ProgRiego → TiempoInicioRiego

**Entrada:**
- `f`: vector de tablones, donde cada tablón tiene la forma `(ts, tr, p)`
- `pi`: vector que define un orden de riego válido

**Salida:**
Un vector `t` tal que `t(i)` es el tiempo exacto de inicio del riego del tablón `i`.

**Precondiciones:**
1. `pi` es una permutación válida de los índices de `f`.
2. Para cada tablón `(ts, tr, p)` se cumple `tr ≥ 0`.

**Postcondición:**
Para cada tablón `i`, el tiempo asignado cumple:

$t(i) = Σ tr(pi[k])$ 

donde k recorre las posiciones de `pi` hasta que `pi[k] = i`.

---

## 2. Definición matemática de la función

Sea la función auxiliar definida como:

- $aux([], T, acc) = acc$
- $aux(x :: xs, T, acc) = aux(xs, T + tr(x), acc ++ [(x, T)])$

Y:

$tIR(f, pi) = ordenarPorÍndice( aux(pi, 0, []) )$

---

## 3. Invariante

En cada llamada recursiva:

### **Invariante I(T, acc, resto):**

Para cada par `(i, ti)` en `acc`:

> **ti es exactamente la suma de los tiempos de riego `tr` de todos los tablones que preceden a `i` en la programación original `pi`.**

Es decir:

- Todos los tiempos acumulados hasta ese punto son correctos.
- No se pierde información.
- Ningún tiempo acumulado puede ser incorrecto si el invariante se mantiene.

---

## 4. Corrección parcial

Si el algoritmo termina, el resultado es correcto:

1. En cada paso se agrega `(tablón, tiempoActual)`  
   donde `tiempoActual` es exactamente la suma de los tiempos anteriores.

2. El invariante garantiza que **todos los elementos en `acc` están correctamente calculados**.

3. Cuando `resto = []`, `acc` contiene los tiempos correctos para todos los tablones.

4. Ordenar por índice solo reacomoda los pares, **no altera los valores**.

Por tanto, el vector final cumple la postcondición.

---

## 5. Corrección total (inducción estructural sobre `pi`)

### **Caso base:**

$pi = []$

$aux([], 0, []) = []$

El resultado es correcto y satisface el invariante.

---

### **Paso inductivo:**

Suponga que:

$aux(xs, T', acc')$

es correcta para cualquier lista `xs` más pequeña que `pi`.

Queremos demostrar que:

$aux(x :: xs, T, acc)$

también lo es.

1. Al procesar `x`, la función agrega el par `(x, T)`  
   y por definición `T` es la suma de los tiempos anteriores → esto satisface el invariante.

2. Por hipótesis inductiva,  
   aux(xs, T + tr(x), acc ++ [(x,T)])  
   es correcta para la parte restante.

3. Por lo tanto, la llamada completa:

$aux(x :: xs, T, acc)$

produce un `acc` final correcto.

Dado que `tIR` solo ordena este resultado final, la salida es correcta para toda la entrada.

---

# ✔ Conclusión

La función `tIR` cumple:
- Su especificación formal
- El invariante en cada paso
- La corrección parcial (si termina, es correcta)
- La corrección total (termina y es correcta para cualquier entrada válida)

Por tanto, **la implementación es correcta**.
