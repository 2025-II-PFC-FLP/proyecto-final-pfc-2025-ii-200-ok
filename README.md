[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/h71fa0_C)
# Asignación: Proyecto Final - Taller de Programación Funcional y Concurrente

**Fecha:** 08/12/2025

**Curso:** Programacion funcional y concurrente 

---

## 👥 Integrantes del Grupo

| Nombre Completo          | Código    | Rol            | Correo Electrónico |
|--------------------------|-----------|----------------| ---------------- |
| Simon David Tarazona     | 202459421 | [Líder/Colab.] | simon.tarazona@correounivalle.edu.co |
| Sebastian Devia	Acosta   | 202459664 | [Colaborador]  | devia.sebastian@correounivalle.edu.co |
| Camilo Riscanevo Cotrina | 202459753 | [Colaborador]  | camilo.riscanevo@correounivalle.edu.co |
| Karen Andrea Sanabria    | 202459413 | [Colaborador]  | karen.sanabria@correounivalle.edu.co |
| Angel Nicolas Castañeda  | 202459426 | [Colaborador]  | castaneda.angel@correounivalle.edu.co |


---

## 📌 Descripción de la Asignación

Este taller trata sobre crear un programa en Scala que resuelva un problema de optimización: tienes una finca con varios
tablones que necesitan ser regados, pero solo puedes regar uno a la vez. El objetivo es encontrar el mejor orden para 
regarlos de manera que gastes lo menos posible. ¿Y qué es lo que gastas? Pues dos cosas: primero, si llegas tarde a regar 
un tablón que ya necesitaba agua urgentemente, pagas una penalización (como una multa); segundo, cuando te mueves de un 
tablón a otro, recorres una distancia que también suma al costo total. Entonces tu trabajo es programar funciones que 
calculen a qué hora llegas a cada tablón, cuánto te cuesta regarlo (sumando la penalización si llegaste tarde más la 
distancia que recorriste), y luego probar todas las posibles combinaciones de orden de riego para ver cuál es la más 
barata. Como no sabemos de antemano cuál es el mejor orden, usamos un método llamado "fuerza bruta" que básicamente significa
probar todas las opciones posibles. Acá viene lo interesante: debes programar todo usando estilo funcional, o sea, sin usar ciclos 
como for o while, solo recursión, y sin cambiar variables (todo debe ser inmutable). Además, como este proceso puede 
volverse muy lento cuando hay muchos tablones, necesitas crear dos versiones del programa: una secuencial que hace todo 
paso a paso, y otra paralela que divide el trabajo entre varios procesadores para que sea más rápido. Por último, entregas
un informe donde explicas cómo funcionan tus funciones, demuestras con pruebas que están correctas, analizas cómo funcionan 
los procesos recursivos y comparas el rendimiento de ambas versiones midiendo cuánto tiempo tarda cada una.
