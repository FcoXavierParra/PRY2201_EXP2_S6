/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.exp2_s6_francisco_parra;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Exp2_S6_Francisco_Parra {

    static int totalEntradasVendidas = 0;
    static int totalReservas = 0;

    static String[][] carrito = new String[100][5];
    static String[][] reservas = new String[100][5];

    static boolean[][] asientosZonaA = new boolean[3][10];
    static boolean[][] asientosZonaB = new boolean[3][10];
    static boolean[][] asientosZonaC = new boolean[3][10];
    
        public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        int indiceCarrito = 0;
        int indiceReserva = 0;
        final double PRECIO_BASE = 10000;

        while (continuar) {
            
            int intentos = 0;
            int opcion = -1;

            while (intentos < 2) {
                mostrarMenu();
                String entrada = scanner.nextLine();

                try {
                    opcion = Integer.parseInt(entrada);
                    if (opcion >= 1 && opcion <= 6) break;
                    else System.out.println("Opción fuera de rango.");
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Debe ingresar un número.");
                }
                intentos++;
            }

            switch (opcion) {
                case 1 -> indiceCarrito = comprarEntrada(scanner, indiceCarrito, PRECIO_BASE);
                case 2 -> indiceReserva = reservarEntrada(scanner, indiceReserva, PRECIO_BASE);
                case 3 -> indiceCarrito = confirmarReservaComoCompra(scanner, indiceCarrito);
                case 4 -> modificarEntrada(scanner, indiceCarrito);
                case 5 -> imprimirBoleta(indiceCarrito);
                case 6 -> continuar = false;
                default -> System.out.println("Opción inválida.");
            }
        }
        scanner.close();
    }
       
    public static void mostrarMenu() {
        System.out.println("===== MENU TEATRO MORO =====");
        System.out.println("1. Comprar entrada");
        System.out.println("2. Reservar entrada");
        System.out.println("3. Confirmar reserva como compra");
        System.out.println("4. Modificar entrada existente");
        System.out.println("5. Imprimir boleta");
        System.out.println("6. Salir");
        System.out.print("Seleccione opción: ");
    }

    public static int seleccionarAsientoFilaConIntento(Scanner scanner, String zona) {
        boolean[][] asientos = switch (zona) {
            case "A" -> asientosZonaA;
            case "B" -> asientosZonaB;
            case "C" -> asientosZonaC;
            default -> null;
        };
        if (asientos == null) return -1;

        for (int fila = 0; fila < asientos.length; fila++) {
            System.out.print("Fila " + (fila + 1) + ": ");
            for (int asiento = 0; asiento < asientos[fila].length; asiento++) {
                System.out.print(asientos[fila][asiento] ? "[X]" : "[" + (asiento + 1) + "]");
            }
            System.out.println();
        }

        int filaSel = -1;
        for (int i = 0; i < 2; i++) {
            System.out.print("Seleccione fila (1-3): ");
            try {
                filaSel = Integer.parseInt(scanner.nextLine());
                if (filaSel >= 1 && filaSel <= 3) break;
                else System.out.println("Fila inválida.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida.");
            }
            if (i == 1) {
                System.out.println("Se excedieron los intentos. Volviendo al menú principal...");
                return -1;
            }
        }

        int asientoSel = -1;
        for (int i = 0; i < 2; i++) {
            System.out.print("Seleccione número de asiento (1-10): ");
            try {
                asientoSel = Integer.parseInt(scanner.nextLine());
                if (asientoSel >= 1 && asientoSel <= 10 && !asientos[filaSel - 1][asientoSel - 1]) break;
                else System.out.println("Asiento inválido o ya ocupado.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida.");
            }
            if (i == 1) {
                System.out.println("Se excedieron los intentos. Volviendo al menú principal...");
                return -1;
            }
        }

        asientos[filaSel - 1][asientoSel - 1] = true;
        return (filaSel - 1) * 10 + asientoSel;
    }
    
    // Original:
    public static int seleccionarAsientoFila(Scanner scanner, String zona) {
        boolean[][] asientos = switch (zona) {
            case "A" -> asientosZonaA;
            case "B" -> asientosZonaB;
            case "C" -> asientosZonaC;
            default -> null;
        };

        if (asientos == null) return -1;

        for (int fila = 0; fila < asientos.length; fila++) {
            System.out.print("Fila " + (fila + 1) + ": ");
            for (int asiento = 0; asiento < asientos[fila].length; asiento++) {
                System.out.print(asientos[fila][asiento] ? "[X]" : "[" + (asiento + 1) + "]");
            }
            System.out.println();
        }

        System.out.print("Seleccione fila (1-3): ");
        int filaSel = scanner.nextInt();
        scanner.nextLine();
        if (filaSel < 1 || filaSel > 3) {
            System.out.println("Fila inválida.");
            return -1;
        }

        System.out.print("Seleccione número de asiento (1-10): ");
        int asientoSel = scanner.nextInt();
        scanner.nextLine();
        if (asientoSel < 1 || asientoSel > 10 || asientos[filaSel - 1][asientoSel - 1]) {
            System.out.println("Asiento inválido o ya ocupado.");
            return -1;
        }

        asientos[filaSel - 1][asientoSel - 1] = true;
        return (filaSel - 1) * 10 + asientoSel; // Codificar fila y asiento como número único
    }

    
    public static void liberarAsiento(String zona, int asientoCodificado) {
        int fila = (asientoCodificado - 1) / 10;
        int asiento = (asientoCodificado - 1) % 10;
        switch (zona) {
            case "A" -> asientosZonaA[fila][asiento] = false;
            case "B" -> asientosZonaB[fila][asiento] = false;
            case "C" -> asientosZonaC[fila][asiento] = false;
        }
    }

     public static int comprarEntrada(Scanner scanner, int indice, double base) {
        String zona = "";
        boolean zonaValida = false;
        for (int intento = 0; intento < 2; intento++) {
            System.out.print("Ingrese zona (A/B/C): ");
            zona = scanner.nextLine().toUpperCase();
            if (zona.equals("A") || zona.equals("B") || zona.equals("C")) {
                zonaValida = true;
                break;
            }
            System.out.println("Zona inválida. Intenta nuevamente...");
        }
        if (!zonaValida) {
            System.out.println("Se excedieron los intentos. Volviendo al menú principal...");
            return indice;
        }

        int asientoCodificado = seleccionarAsientoFilaConIntento(scanner, zona);
        if (asientoCodificado == -1) return indice;

        int edad = -1;
        for (int intento = 0; intento < 2; intento++) {
            try {
                System.out.print("Ingrese edad: ");
                edad = Integer.parseInt(scanner.nextLine());
                if (edad > 0) break;
            } catch (NumberFormatException e) {
                System.out.println("Edad inválida.");
            }
    
        }

        String estudiante = "";
        
   boolean estudianteValido = false;
for (int intento = 0; intento < 2; intento++) {
    System.out.print("¿Es estudiante? (s/n): ");
    estudiante = scanner.nextLine().toLowerCase();
    if (estudiante.equals("s") || estudiante.equals("n")) {
        estudianteValido = true;
        break;
    } else {
        System.out.println("Entrada inválida. Debe ser 's' o 'n'.");
    }
}
if (!estudianteValido) {
    System.out.println("Se excedieron los intentos. Volviendo al menú principal...");
    return indice;
}
        double precio = base + switch (zona) {
            case "A" -> 2000;
            case "B" -> 0;
            case "C" -> -2000;
            default -> 0;
        };

        double descuento = (edad >= 60) ? 0.15 : (estudiante.equals("s") ? 0.10 : 0);
        double finalPrecio = precio * (1 - descuento);

        carrito[indice][0] = zona;
        carrito[indice][1] = String.valueOf(precio);
        carrito[indice][2] = descuento * 100 + "%";
        carrito[indice][3] = String.valueOf(finalPrecio);
        carrito[indice][4] = String.valueOf(asientoCodificado);

        totalEntradasVendidas++;
        System.out.println("Entrada agregada a Carrito de Compra");
        System.out.println("Entrada " + (indice + 1) + ": Zona: " + carrito[indice][0] + " | Fila: " + (((Integer.parseInt(carrito[indice][4]) - 1) / 10) + 1) + " | Asiento: " + carrito[indice][4] + " | Precio: $" + carrito[indice][3]);
        return indice + 1;
    }

   public static int reservarEntrada(Scanner scanner, int indice, double base) {
    String zona = "";
    boolean zonaValida = false;
    for (int intento = 0; intento < 2; intento++) {
        System.out.print("Ingrese zona para reserva (A/B/C): ");
        zona = scanner.nextLine().toUpperCase();
        if (zona.equals("A") || zona.equals("B") || zona.equals("C")) {
            zonaValida = true;
            break;
        }
        System.out.println("Zona inválida. Intenta nuevamente...");
    }
    if (!zonaValida) {
        System.out.println("Se excedieron los intentos. Volviendo al menú principal...");
        return indice;
    }

    int asientoCodificado = seleccionarAsientoFilaConIntento(scanner, zona);
    if (asientoCodificado == -1) return indice;

        int edad = -1;
        for (int intento = 0; intento < 2; intento++) {
            try {
                System.out.print("Ingrese edad: ");
                edad = Integer.parseInt(scanner.nextLine());
                if (edad > 0) break;
            } catch (NumberFormatException e) {
                System.out.println("Edad inválida.");
            }
            if (intento == 1) {
                System.out.println("Se excedieron los intentos. Volviendo al menú principal...");
                return indice;
            }
        }

        String estudiante = "";
        boolean estudianteValido = false;
for (int intento = 0; intento < 2; intento++) {
    System.out.print("¿Es estudiante? (s/n): ");
    estudiante = scanner.nextLine().toLowerCase();
    if (estudiante.equals("s") || estudiante.equals("n")) {
        estudianteValido = true;
        break;
    } else {
        System.out.println("Entrada inválida. Debe ser 's' o 'n'.");
    }
}
if (!estudianteValido) {
    System.out.println("Se excedieron los intentos. Volviendo al menú principal...");
    return indice;
}

        double precio = base + switch (zona) {
            case "A" -> 2000;
            case "B" -> 0;
            case "C" -> -2000;
            default -> 0;
        };

        double descuento = (edad >= 60) ? 0.15 : (estudiante.equals("s") ? 0.10 : 0);
        double finalPrecio = precio * (1 - descuento);

        reservas[indice][0] = zona;
        reservas[indice][1] = String.valueOf(precio);
        reservas[indice][2] = descuento * 100 + "%";
        reservas[indice][3] = String.valueOf(finalPrecio);
        reservas[indice][4] = String.valueOf(asientoCodificado);

        int fila = ((asientoCodificado - 1) / 10) + 1;
        int asiento = ((asientoCodificado - 1) % 10) + 1;
      
        int reservaIndex = indice;
        String zonaFinal = zona;
        int asientoFinal = asientoCodificado;
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (reservas[reservaIndex][0] != null) {
                    System.out.println("\n[!] La reserva :"+indice+ "en zona: " + zonaFinal + " fila: " + fila +" asiento: " + asiento + " ha expirado.");
                    reservas[reservaIndex] = new String[5];
                    liberarAsiento(zonaFinal, asientoFinal);
                }
            }
        }, 2 * 60 * 1000);

      totalReservas++;
        System.out.println("Reserva registrada. Tienes 2 minutos para confirmarla.");
        System.out.println("Reserva " + (indice + 1) + ": Zona: " + zona +
            " | Fila: " + fila + " | Asiento: " + asiento +
            " | Precio: $" + finalPrecio);

        return indice + 1;
    }

  public static int confirmarReservaComoCompra(Scanner scanner, int indiceCarrito) {
    System.out.println("\n===== RESERVAS ACTIVAS =====");
    boolean hayReservas = false;
    for (int i = 0; i < reservas.length; i++) {
        if (reservas[i][0] != null) {
            hayReservas = true;
            int codificado = Integer.parseInt(reservas[i][4]);
            int fila = ((codificado - 1) / 10) + 1;
            int asiento = ((codificado - 1) % 10) + 1;

            System.out.println((i + 1) + ". Zona: " + reservas[i][0] +
                " | Fila: " + fila + " | Asiento: " + asiento +
                " | Precio: $" + reservas[i][3]);
        }
    }

    if (!hayReservas) {
        System.out.println("[!] No hay reservas válidas.");
        return indiceCarrito;
    }

    int seleccion = -1;
    for (int intento = 0; intento < 2; intento++) {
        System.out.print("Seleccione número de reserva a confirmar: ");
        try {
            seleccion = Integer.parseInt(scanner.nextLine());
            if (seleccion >= 1 && seleccion <= reservas.length && reservas[seleccion - 1][0] != null) break;
        } catch (Exception e) {}
        if (intento == 1) {
            System.out.println("Selección inválida. Volviendo al menú principal...");
            return indiceCarrito;
        }
    }

    carrito[indiceCarrito] = reservas[seleccion - 1].clone();
    reservas[seleccion - 1] = new String[5];
    totalEntradasVendidas++;
    System.out.println("Reserva confirmada como compra.");
    return indiceCarrito + 1;
}
    public static void modificarEntrada(Scanner scanner, int indiceCarrito) {
        if (indiceCarrito == 0) {
            System.out.println("No hay entradas para modificar.");
            return;
        }

        System.out.println("===== Entradas en Carrito =====");
        for (int i = 0; i < indiceCarrito; i++) {
            System.out.println("Entrada " + (i + 1) + ": Zona: " + carrito[i][0] + " | Fila: " + (((Integer.parseInt(carrito[i][4]) - 1) / 10) + 1) + " | Asiento: " + carrito[i][4] + " | Precio: $" + carrito[i][3]);
        }

        int num = -1;
        for (int intento = 0; intento < 2; intento++) {
            System.out.print("Seleccione número de entrada a modificar: ");
            try {
                num = Integer.parseInt(scanner.nextLine());
                if (num >= 1 && num <= indiceCarrito) break;
            } catch (Exception e) {}
            if (intento == 1) {
                System.out.println("Selección inválida. Volviendo al menú principal...");
                return;
            }
        }

        String zonaAntigua = carrito[num - 1][0];
        int asientoCodAntiguo = Integer.parseInt(carrito[num - 1][4]);
        liberarAsiento(zonaAntigua, asientoCodAntiguo);

        String nuevaZona = "";
        boolean zonaValida = false;
        for (int intento = 0; intento < 2; intento++) {
            System.out.print("Nueva zona (A/B/C): ");
            nuevaZona = scanner.nextLine().toUpperCase();
            if (nuevaZona.equals("A") || nuevaZona.equals("B") || nuevaZona.equals("C")) {
                zonaValida = true;
                break;
            }
            System.out.println("Zona inválida. Intenta nuevamente...");
        }
        if (!zonaValida) {
            System.out.println("Se excedieron los intentos. Volviendo al menú principal...");
            return;
        }

        int nuevoAsientoCod = seleccionarAsientoFilaConIntento(scanner, nuevaZona);
        if (nuevoAsientoCod == -1) return;

        int edad = -1;
        for (int intento = 0; intento < 2; intento++) {
            try {
                System.out.print("Nueva edad: ");
                edad = Integer.parseInt(scanner.nextLine());
                if (edad > 0) break;
            } catch (Exception e) {
                System.out.println("Edad inválida.");
            }
            if (intento == 1) {
                System.out.println("Se excedieron los intentos. Volviendo al menú principal...");
                return;
            }
        }

        String estudiante = "";
        boolean estudianteValido = false;
        for (int intento = 0; intento < 2; intento++) {
            System.out.print("¿Es estudiante? (s/n): ");
            estudiante = scanner.nextLine().toLowerCase();
            if (estudiante.equals("s") || estudiante.equals("n")) {
                estudianteValido = true;
                break;
            } else {
                System.out.println("Entrada inválida. Debe ser 's' o 'n'.");
            }
        }
        if (!estudianteValido) {
            System.out.println("Se excedieron los intentos. Volviendo al menú principal...");
            return;
        }

        double precio = 10000 + switch (nuevaZona) {
            case "A" -> 2000;
            case "B" -> 0;
            case "C" -> -2000;
            default -> 0;
        };

        double descuento = (edad >= 60) ? 0.15 : (estudiante.equals("s") ? 0.10 : 0);
        double finalPrecio = precio * (1 - descuento);

        carrito[num - 1][0] = nuevaZona;
        carrito[num - 1][1] = String.valueOf(precio);
        carrito[num - 1][2] = descuento * 100 + "%";
        carrito[num - 1][3] = String.valueOf(finalPrecio);
        carrito[num - 1][4] = String.valueOf(nuevoAsientoCod);

        System.out.println("Entrada modificada exitosamente.");
        // Mostrar resumen de la entrada modificada
int fila = ((nuevoAsientoCod - 1) / 10) + 1;
int asiento = ((nuevoAsientoCod - 1) % 10) + 1;
System.out.println("Nueva entrada: Zona: " + nuevaZona + " | Fila: " + fila + " | Asiento: " + asiento + " | Precio: $" + finalPrecio);
    }


    public static void imprimirBoleta(int indiceCarrito) {
        double total = 0;
        System.out.println("===== BOLETA TEATRO MORO =====");
        for (int i = 0; i < indiceCarrito; i++) {
            System.out.println("Entrada " + (i + 1) + ": Zona: " + carrito[i][0] + " | Fila: " + (((Integer.parseInt(carrito[i][4]) - 1) / 10) + 1) + " | Asiento: " + carrito[i][4] + " | Precio: $" + carrito[i][3]);
            total += Double.parseDouble(carrito[i][3]);
        }
        System.out.println("TOTAL A PAGAR: $" + total);
        System.out.println("Gracias por su compra. Cerrando el sistema...");
        System.exit(0);
    }

// ===================== MÉTODOS REUTILIZABLES =====================

// Valida ingreso de zona (A/B/C) con intento y contexto personalizado
public static String pedirZona(Scanner scanner, String contexto) {
    for (int intento = 0; intento < 2; intento++) {
        System.out.print("Ingrese zona" + contexto + " (A/B/C): ");
        String zona = scanner.nextLine().toUpperCase();
        if (zona.equals("A") || zona.equals("B") || zona.equals("C")) return zona;
        System.out.println("Zona inválida. Intenta nuevamente...");
    }
    System.out.println("Se excedieron los intentos. Volviendo al menú principal...");
    return null;
}

// Valida ingreso de edad (> 0)
public static int pedirEdad(Scanner scanner) {
    for (int intento = 0; intento < 2; intento++) {
        try {
            System.out.print("Ingrese edad: ");
            int edad = Integer.parseInt(scanner.nextLine());
            if (edad > 0) return edad;
        } catch (NumberFormatException e) {
            System.out.println("Edad inválida.");
        }
    }
    System.out.println("Se excedieron los intentos. Volviendo al menú principal...");
    return -1;
}

// Valida si es estudiante ('s' o 'n')
public static String pedirEstudiante(Scanner scanner) {
    for (int intento = 0; intento < 2; intento++) {
        System.out.print("¿Es estudiante? (s/n): ");
        String estudiante = scanner.nextLine().toLowerCase();
        if (estudiante.equals("s") || estudiante.equals("n")) return estudiante;
        System.out.println("Entrada inválida. Debe ser 's' o 'n'.");
    }
    System.out.println("Se excedieron los intentos. Volviendo al menú principal...");
    return null;
}

// Codifica un asiento (fila y número) en un entero único
public static int codificarAsiento(int fila, int asiento) {
    return (fila - 1) * 10 + asiento;
}

// Decodifica un valor entero en fila y asiento [fila, asiento]
public static int[] decodificarAsiento(int codificado) {
    int fila = ((codificado - 1) / 10) + 1;
    int asiento = ((codificado - 1) % 10) + 1;
    return new int[]{fila, asiento};
}

}