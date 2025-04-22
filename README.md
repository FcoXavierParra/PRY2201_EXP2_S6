🎭 Sistema de Venta y Reserva de Entradas - Teatro Moro
Este proyecto en Java implementa una aplicación de consola para gestionar la compra, reserva, modificación y confirmación de entradas para un teatro dividido en tres zonas: A, B y C.

🧾 Funcionalidades Principales
Comprar Entrada
Permite seleccionar zona, fila y asiento, además de ingresar edad y condición de estudiante para aplicar descuentos. Agrega la entrada al carrito de compra.

Reservar Entrada
Igual al proceso de compra, pero mantiene la entrada en estado de "reserva" con un temporizador de 2 minutos antes de su expiración automática.

Confirmar Reserva como Compra
Lista las reservas activas y permite moverlas al carrito de compra antes de que expiren.

Modificar Entrada Existente
Permite cambiar la zona y asiento de una entrada ya agregada al carrito, recalculando su precio y descuento.

Imprimir Boleta
Muestra todas las entradas del carrito con el total a pagar y finaliza la ejecución del programa.

🪑 Estructura del Teatro
Zonas: A (más cara), B (precio base), C (más barata)

Asientos por zona: 3 filas × 10 asientos por fila

💸 Precios y Descuentos
Precio base: $10.000

Incremento por zona:

Zona A: +$2.000

Zona B: $0

Zona C: -$2.000

Descuentos:

Estudiantes: 10%

Personas mayores de 60 años: 15%

🛒 Estructuras Internas
carrito[][]: Almacena entradas compradas

reservas[][]: Almacena entradas reservadas (temporalmente)

asientosZona[A|B|C][][]: Matrices booleanas para gestión de disponibilidad

⏱️ Temporizador de Reservas
Cada reserva tiene un tiempo límite de 2 minutos para ser confirmada, tras lo cual se libera el asiento automáticamente.

🔧 Tecnologías Usadas
java.util.Scanner, java.util.Timer, java.util.TimerTask

📁 Estructura del Código
main(): ciclo principal del sistema

mostrarMenu(): despliega el menú de opciones

Métodos auxiliares: comprarEntrada(), reservarEntrada(), modificarEntrada(), imprimirBoleta() y validadores reutilizables para edad, zona y condición de estudiante

✅ Validaciones Incluidas
Número limitado de intentos para cada entrada de usuario

Validación de zonas, edad, selección de asiento y condición de estudiante

Control de asientos ocupados

Mensajes informativos ante errores o acciones exitosas
