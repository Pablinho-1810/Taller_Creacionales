# Justificación de patrones creacionales – DomiExpress

## Integrantes
- Juan Pablo Gonzalez Castillo (código 20241020089)
- Nelson de Jesus Navarro de la Rosa (código )

### Taller integrador: patrones creacionales

---

## 1) Singleton — GeneradorConsecutivo

El problema principal es que, en el fragmento de la sección 3, el contador es un `static int` suelto que se repite en cada clase que necesita numerar. Si dos clases tienen su propio contador, dos pedidos distintos podrían recibir el mismo `PED-0001` y no habría una sola fuente de verdad.

### ¿Por qué es la mejor opción?
- Existe una única instancia, con constructor privado y acceso estático.
- La numeración se entrega en orden y de forma segura entre hilos usando el patrón *holder* y un método `siguiente()` sincronizado.
- El `Builder` consulta el consecutivo solo después de validar, por lo que un pedido rechazado no consume número.

### Cuándo no lo usaría
- Cuando cada sucursal deba llevar su propia numeración.
- Cuando las pruebas unitarias necesiten reiniciar el estado con frecuencia.
- En esos casos, el `Singleton` representa un estado global oculto y sería mejor inyectar una instancia normal por constructor.

---

## 2) Builder — Pedido.Builder

El fragmento original crea un pedido con un constructor de 8 parámetros posicionales. Esto hace que el código sea poco claro, obligue a pasar valores vacíos como `""` o `0`, y permita errores como escribir "DOMICILIO" de forma inconsistente sin que el compilador lo detecte.

### ¿Por qué es la mejor opción?
- El `Builder` nombra cada dato (`conCliente`, `conCupon`, etc.).
- Asigna valores por defecto y valida al llamar `construir()` con los mensajes requeridos.
- El constructor de `Pedido` queda privado, evitando la creación de pedidos inválidos.
- Si mañana aparece un nuevo dato opcional, solo se agrega un método `conXxx()` y el código existente no se rompe.

### Cuándo no lo usaría
- En una clase pequeña con todos los datos obligatorios, como `ItemPedido` (nombre, precio y cantidad).
- En ese caso, un constructor directo es más corto, claro y suficiente; el `Builder` solo añadiría complejidad innecesaria.

---

## 3) Factory Method — ProcesadorPago

El bloque `if / else if` del fragmento obliga a modificar el mismo código cada vez que llega un nuevo medio de pago, por ejemplo Nequi, y mezcla el flujo común con las reglas específicas de cada forma de pago.

### ¿Por qué es la mejor opción?
- La lógica de `procesar()` se escribe una sola vez en la clase base y es `final`.
- Lo único que cambia es `crearPasarela()`, que cada subclase implementa.
- Por eso Nequi se agregó con dos archivos nuevos: `ProcesadorNequi` y `PasarelaNequi`.
- Ninguna clase existente se modificó, cumpliendo con el principio abierto/cerrado.

### Cuándo no lo usaría
- Si la empresa aceptara solo tarjeta para siempre.
- Con una sola variante, la jerarquía de creadores y productos sería más compleja de lo necesario, y una clase concreta bastaría.

---

## 4) Reto extra — Prototype (`Pedido.clonar`)

Este patrón no resuelve un problema del fragmento de la sección 3, sino la funcionalidad de “Repetir mi último pedido”.

### ¿Por qué es útil aquí?
- `clonar()` copia los datos y la lista de ítems.
- La lista se crea como una nueva colección, sin compartir la referencia con el original.
- Se asigna un nuevo número de pedido y se deja el cupón en `0`.
- El objeto original no se altera.

### Cuándo no lo usaría
- Cuando los objetos son baratos de crear desde cero.
- En ese caso, clonar no aporta valor real frente a un constructor simple.

---

## Conclusión

Los patrones elegidos ayudan a mejorar la claridad, seguridad y escalabilidad del sistema. El `Singleton` centraliza la numeración, el `Builder` evita objetos inválidos y mejora la legibilidad del código, el `Factory Method` facilita la extensión para nuevos medios de pago y el `Prototype` aporta una solución útil para duplicar pedidos sin afectar el original.

