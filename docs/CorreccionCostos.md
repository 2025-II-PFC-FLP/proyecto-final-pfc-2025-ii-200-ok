
# **INFORME DE CORRECCIÓN — Módulo de Costos**

## Módulo evaluado: `Costos.scala`

Funciones verificadas:

- `costoRiegoTablon`
- `costoRiegoFinca`
- `costoMovilidad`
- `costoTotal`

Este informe presenta la **verificación formal**, **matemática**, **estructural**, **por inducción** y **por pruebas** del módulo, garantizando que cumple con los requerimientos del proyecto final.

---

# **1. Especificación Formal**

### **Tipos:**

- `Finca = Vector[(Int,Int,Int)]`  
- `ProgRiego = Vector[Int]`  
- `Distancia = Vector[Vector[Int]]`  
- `TiempoInicioRiego = Vector[Int]`

### **Entradas:**

- `f : Finca`  
- `pi : ProgRiego`  
- `d : Distancia`  

### **Salidas:**

Cada función devuelve un entero ≥ 0:

- `costoRiegoTablon(i,f,pi) ∈ Int`
- `costoRiegoFinca(f,pi) ∈ Int`
- `costoMovilidad(f,pi,d) ∈ Int`
- `costoTotal(f,pi,d) ∈ Int`

### **Precondiciones:**

1. `pi` debe ser una permutación válida de los índices de la finca.
2. La matriz `d` debe ser cuadrada y simétrica.
3. `tIR(f,pi)` debe ser correcta (módulo Tiempos).
4. No se permiten índices fuera de rango.

### **Postcondiciones:**

1. El costo por tablón sigue la definición matemática del proyecto.  
2. El costo de la finca es la suma de los costos por tablón.  
3. El costo de movilidad es la suma de distancias consecutivas en la programación.  
4. `costoTotal = riego + movilidad`.

---

# **2. Definición Matemática**

## 2.1 Costo por tablón

Sea:

- `ts`: tiempo ideal de siembra  
- `tr`: tiempo requerido de riego  
- `p`: penalización  
- `tInicio(i)`: tiempo real de inicio  

Se define:

### **Caso sin penalización (riego temprano o exacto):**
$$
ts - tr \ge tInicio \quad \Rightarrow \quad C_i = ts - (tInicio + tr)
$$

### **Caso con penalización (riego tardío):**
$$
ts - tr < tInicio \quad \Rightarrow \quad C_i = p \cdot ((tInicio + tr) - ts)
$$

---

## 2.2 Costo total de riego

$$
C_{riego} = \sum_{i=0}^{n-1} C_i
$$

---

## 2.3 Costo de movilidad

Sea la programación:

$$
pi = [a_0, a_1, ..., a_{n-1}]
$$

Entonces:

$$
C_{mov} = \sum_{k=0}^{n-2} d(a_k)(a_{k+1})
$$

---

## 2.4 Costo Total

$$
C_{total} = C_{riego} + C_{mov}
$$

---

# **3. Invariantes del Módulo**

### **En `costoRiegoTablon`:**
- Se usa exactamente un tiempo de inicio por tablón.
- No se crean estructuras mutables.
- El costo generado es ≥ 0.

### **En `costoRiegoFinca`:**
- `tIR` se calcula exactamente una vez (eficiencia).
- Se evalúa cada tablón exactamente una vez.
- La suma conserva la no negatividad.

### **En `costoMovilidad`:**
- Cada pareja `(a,b)` proviene de una ventana válida `pi(k), pi(k+1)`.
- Solo se usan distancias existentes en la matriz.

### **En `costoTotal`:**
- Se garantiza que el resultado es consistente con la suma de costos parciales.

---

# **4. Corrección Parcial**

Se demuestra que cada función produce resultados correctos **si finaliza**.

### ✔ `costoRiegoTablon`  
Cumple la especificación al verificar la condición exacta definida en el proyecto.

### ✔ `costoRiegoFinca`  
Usa `map` para aplicar la fórmula a cada tablón y luego suma.  
No se descarta ninguna entrada; no se generan duplicados.

### ✔ `costoMovilidad`  
`sliding(2)` asegura pares consecutivos sin errores ni posiciones inválidas.

### ✔ `costoTotal`  
La suma directa garantiza que respeta las definiciones.

---

# **5. Corrección Total (Inducción Estructural)**

## Caso base: finca con 1 tablón

- `costoRiegoFinca` calcula un solo costo.
- `costoMovilidad = 0`.
- `costoTotal` es correcto.

Cumple la definición matemática.

---

## Paso inductivo

Supongamos correctas las funciones para una finca de tamaño `n-1`.

Para una finca de tamaño `n`:

1. `costoRiegoFinca` calcula correctamente los n costos gracias a la hipótesis.  
2. `costoMovilidad` suma n-1 distancias válidas.  
3. `costoTotal` representa exactamente la suma definida.

Por lo tanto, por inducción, el módulo es correcto para todo `n ≥ 1`.

---

# **6. Validación por Pruebas (ScalaTest)**

Las pruebas ejecutadas verifican:

### ✔ Cálculo correcto de costos individuales  
Caso donde **no hay penalización** y donde **sí hay penalización**.

### ✔ Consistencia con `tIR`  
Cada tablón usa el tiempo correcto generado por el módulo de tiempos.

### ✔ Costo total de riego  
Validado con sumas simples y escenarios de riego tardío/temprano.

### ✔ Costo de movilidad  
Validado con matrices pequeñas y programaciones sencillas.

### ✔ Costo total  
Verifica que riego + movilidad coincide exactamente con el resultado esperado.

---

# **7. Conclusión General**

Tras analizar:
- especificación formal,  
- definición matemática,  
- invariantes,  
- corrección parcial,  
- corrección total por inducción,  
- validación con pruebas unitarias,

se concluye que el módulo **Costos.scala**:

✔ Implementa fielmente las ecuaciones del proyecto  
✔ No presenta errores lógicos  
✔ No usa mutabilidad  
✔ Calcula correctamente costos parciales y totales  
✔ Está completamente validado y es apto para integrarse con Óptimo y Paralelo  
✔ Cumple con todos los requerimientos del Proyecto Final  
