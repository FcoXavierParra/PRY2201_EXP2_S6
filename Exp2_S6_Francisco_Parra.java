/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.exp2_s6_francisco_parra;

/**
 *
 * @author fparraa
 */

import java.util.Scanner;


public class Exp2_S6_Francisco_Parra {

    // Variables estáticas globales (mínimo 3)
    static int totalEntradasVendidas = 0;
    static int totalReservas = 0;
    
    // Matrices para ventas y reservas (zona, precio base, descuento %, precio final, asiento)
    static String[][] carrito = new String[100][5];
    static String[][] reservas = new String[100][5];

    // Control por zonas
    static boolean[] asientosA = new boolean[35];
    static boolean[] asientosB = new boolean[35];
    static boolean[] asientosC = new boolean[35];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        int indiceCarrito = 0;
        int indiceReserva = 0;
        final double PRECIO_BASE = 10000;

        while (continuar) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> indiceCarrito = comprarEntrada(scanner, indiceCarrito, PRECIO_BASE);
                case 2 -> indiceReserva = reservarEntrada(scanner, indiceReserva, PRECIO_BASE);
                case 3 -> indiceCarrito = confirmarReservaComoCompra(scanner, indiceCarrito, indiceReserva);
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

    public static int comprarEntrada(Scanner scanner, int indice, double base) {
        String zona = "";
        int intentosZona = 0;
        while (intentosZona < 2) {
            System.out.print("Ingrese zona (A/B/C): ");
            zona = scanner.nextLine().toUpperCase();
            if (zona.equals("A") || zona.equals("B") || zona.equals("C")) {
                break;
            } else {
                System.out.println("Zona inválida. Ingrese A, B o C.");
                intentosZona++;
            }
        }
        if (!zona.equals("A") && !zona.equals("B") && !zona.equals("C")) {
            System.out.println("Número máximo de intentos alcanzado. Cancelando la acción...");
            return indice;
        }

        int asiento = seleccionarAsiento(scanner, zona);
        if (asiento == -1) return indice;

        int edad = -1;
        int intentosEdad = 0;
        while (intentosEdad < 2) {
            System.out.print("Ingrese su edad: ");
            if (scanner.hasNextInt()) {
                edad = scanner.nextInt();
                scanner.nextLine();
                if (edad >= 1 && edad <= 120) {
                    break;
                } else {
                    System.out.println("Edad inválida. Debe estar entre 1 y 120.");
                }
            } else {
                scanner.nextLine(); // limpiar entrada no numérica
                System.out.println("Dato no numérico. Intente nuevamente.");
            }
            intentosEdad++;
        }
        if (edad < 1 || edad > 120) {
            System.out.println("Número máximo de intentos alcanzado. Cancelando la acción...");
            return indice;
        }

        String estudiante = "";
        int intentos = 0;
        while (intentos < 2) {
            System.out.print("¿Es estudiante? (s/n): ");
            estudiante = scanner.nextLine().toLowerCase();
            if (estudiante.equals("s") || estudiante.equals("n")) {
                break;
            } else {
                System.out.println("Dato inválido. Ingrese 's' para sí o 'n' para no.");
                intentos++;
            }
        }
        if (!estudiante.equals("s") && !estudiante.equals("n")) {
            System.out.println("Número máximo de intentos alcanzado. Cancelando la compra...");
            return indice;
        }

        double precio = base;
        switch (zona) {
            case "A" -> precio += 2000;
            case "B" -> precio += 0;
            case "C" -> precio -= 2000;
            default -> {
                System.out.println("Zona inválida");
                return indice;
            }
        }

        double descuento = (edad >= 60) ? 0.15 : (estudiante.equals("s") ? 0.10 : 0);
        double finalPrecio = precio * (1 - descuento);

        carrito[indice][0] = zona;
        carrito[indice][1] = String.valueOf(precio);
        carrito[indice][2] = descuento * 100 + "%";
        carrito[indice][3] = String.valueOf(finalPrecio);
        carrito[indice][4] = String.valueOf(asiento);

        marcarAsiento(zona, asiento);

        totalEntradasVendidas++;
        System.out.println("===== RESUMEN COMPRA =====");
        System.out.println("Zona: " + zona + " | Asiento: " + asiento + " | Base: $" + precio + " | Desc: " + (descuento * 100) + "% | Total: $" + finalPrecio);
        System.out.println("Entrada agregada al carrito.");
        return indice + 1;
    }
    
   public static int reservarEntrada(Scanner scanner, int indice, double base) {
        System.out.print("Ingrese zona para reserva (A/B/C): ");
        String zona = scanner.nextLine().toUpperCase();

        int asiento = seleccionarAsiento(scanner, zona);
        if (asiento == -1) return indice;

        int edad = -1;
        int intentosEdad = 0;
        while (intentosEdad < 2) {
            System.out.print("Ingrese su edad: ");
            if (scanner.hasNextInt()) {
                edad = scanner.nextInt();
                scanner.nextLine();
                if (edad >= 1 && edad <= 120) {
                    break;
                } else {
                    System.out.println("Edad inválida. Debe estar entre 1 y 120.");
                }
            } else {
                scanner.nextLine(); // limpiar entrada no numérica
                System.out.println("Dato no numérico. Intente nuevamente.");
            }
            intentosEdad++;
        }
        if (edad < 1 || edad > 120) {
            System.out.println("Número máximo de intentos alcanzado. Cancelando la acción...");
            return indice;
        }

        String estudiante = "";
        int intentos = 0;
        while (intentos < 2) {
            System.out.print("¿Es estudiante? (s/n): ");
            estudiante = scanner.nextLine().toLowerCase();
            if (estudiante.equals("s") || estudiante.equals("n")) {
                break;
            } else {
                System.out.println("Dato inválido. Ingrese 's' para sí o 'n' para no.");
                intentos++;
            }
        }
        if (!estudiante.equals("s") && !estudiante.equals("n")) {
            System.out.println("Número máximo de intentos alcanzado. Cancelando la compra...");
            return indice;
        }

        double precio = base;
        switch (zona) {
            case "A" -> precio += 2000;
            case "B" -> precio += 0;
            case "C" -> precio -= 2000;
            default -> {
                System.out.println("Zona inválida");
                return indice;
            }
        }

        double descuento = (edad >= 60) ? 0.15 : (estudiante.equals("s") ? 0.10 : 0);
        double finalPrecio = precio * (1 - descuento);

        System.out.println("===== RESUMEN RESERVA PREVIA =====");
        System.out.println("Zona: " + zona + " | Asiento: " + asiento + " | Base: $" + precio + " | Desc: " + (descuento * 100) + "% | Total: $" + finalPrecio);
        System.out.print("¿Confirmar reserva? (s/n): ");
        if (!scanner.nextLine().equalsIgnoreCase("s")) return indice;

        reservas[indice][0] = zona;
        reservas[indice][1] = String.valueOf(precio);
        reservas[indice][2] = descuento * 100 + "%";
        reservas[indice][3] = String.valueOf(finalPrecio);
        reservas[indice][4] = String.valueOf(asiento);

        marcarAsiento(zona, asiento);
        totalReservas++;
        System.out.println("Reserva realizada con éxito.");
        return indice + 1;
    }

    
   
    public static int seleccionarAsiento(Scanner scanner, String zona) {
        boolean[] asientos = switch (zona) {
            case "A" -> asientosA;
            case "B" -> asientosB;
            case "C" -> asientosC;
            default -> null;
        };

        if (asientos == null) return -1;

        System.out.print("Asientos disponibles en zona " + zona + ": ");
        for (int i = 0; i < asientos.length; i++) {
            if (!asientos[i]) System.out.print((i + 1) + " ");
        }
       
       int intentos = 0;
       while (intentos < 2) {
            System.out.print("Seleccione número de asiento: ");
        int num = scanner.nextInt();
        scanner.nextLine();

            if (num >= 1 && num <= asientos.length && !asientos[num - 1]) {
                return num;
            } else {
                System.out.println("Asiento inválido o ya ocupado.");
                intentos++;
            }
        }
       System.out.println("Número máximo de intentos.Cancelando la selección de asiento");
       return -1;
        }


    public static void marcarAsiento(String zona, int asiento) {
        switch (zona) {
            case "A" -> asientosA[asiento - 1] = true;
            case "B" -> asientosB[asiento - 1] = true;
            case "C" -> asientosC[asiento - 1] = true;
        }
    }

    public static void imprimirBoleta(int indiceCarrito) {
        if (indiceCarrito == 0) {
            System.out.println("No hay entradas compradas.");
            return;
        }

        double total = 0;
        System.out.println("===== BOLETA TEATRO MORO =====");
        for (int i = 0; i < indiceCarrito; i++) {
            System.out.println("Entrada " + (i + 1) + ": Zona: " + carrito[i][0] + " | Asiento: " + carrito[i][4] + " | Base: $" + carrito[i][1] + " | Desc: " + carrito[i][2] + " | Final: $" + carrito[i][3]);
            total += Double.parseDouble(carrito[i][3]);
        }
        System.out.println("TOTAL A PAGAR: $" + total);
        System.out.println("Entradas totales: " + indiceCarrito);
    }
public static int confirmarReservaComoCompra(Scanner scanner, int indiceCarrito, int indiceReserva) {
    if (indiceReserva == 0) {
        System.out.println("No hay reservas para confirmar.");
        return indiceCarrito;
    }

    System.out.println("\n===== RESERVAS ACTIVAS =====");
    for (int i = 0; i < reservas.length; i++) {
        if (reservas[i][0] != null) {
            System.out.println((i + 1) + ". Zona: " + reservas[i][0]
                + " | Asiento: " + reservas[i][4]
                + " | Precio base: $" + reservas[i][1]
                + " | Descuento: " + reservas[i][2]
                + " | Precio final: $" + reservas[i][3]);
        }
    }

    System.out.print("Ingrese el número de reserva que desea confirmar como compra: ");
    int seleccion = scanner.nextInt();
    scanner.nextLine();

    if (seleccion < 1 || seleccion > reservas.length || reservas[seleccion - 1][0] == null) {
        System.out.println("Selección inválida.");
        return indiceCarrito;
    }

    String[] reservaSeleccionada = reservas[seleccion - 1];
    System.out.println("\n===== RESUMEN DE RESERVA =====");
    System.out.println("Zona: " + reservaSeleccionada[0]
            + " | Asiento: " + reservaSeleccionada[4]
            + " | Precio base: $" + reservaSeleccionada[1]
            + " | Descuento: " + reservaSeleccionada[2]
            + " | Precio final: $" + reservaSeleccionada[3]);

    carrito[indiceCarrito] = reservaSeleccionada.clone();
    reservas[seleccion - 1] = new String[5];
    totalEntradasVendidas++;
    System.out.println("Reserva confirmada y agregada como compra.");

    return indiceCarrito + 1;
}

    public static void modificarEntrada(Scanner scanner, int indiceCarrito) {
    if (indiceCarrito == 0) {
        System.out.println("No hay entradas para modificar.");
        return;
    }

    System.out.print("Ingrese número de entrada a modificar (1 a " + indiceCarrito + "): ");
    int num = scanner.nextInt();
    scanner.nextLine();

    if (num < 1 || num > indiceCarrito) {
        System.out.println("Número inválido.");
        return;
    }

    System.out.println("Entrada actual: Zona: " + carrito[num - 1][0] +
            " | Asiento: " + carrito[num - 1][4] +
            " | Base: $" + carrito[num - 1][1] +
            " | Desc: " + carrito[num - 1][2] +
            " | Final: $" + carrito[num - 1][3]);

    System.out.print("Nueva zona (A/B/C): ");
    String zona = scanner.nextLine().toUpperCase();
    int asiento = seleccionarAsiento(scanner, zona);
    if (asiento == -1) return;

    System.out.print("Nueva edad: ");
    int edad = scanner.nextInt();
    scanner.nextLine();
    System.out.print("¿Es estudiante? (s/n): ");
    String estudiante = scanner.nextLine().toLowerCase();

    double precio = 10000;
    switch (zona) {
        case "A" -> precio += 2000;
        case "B" -> {}
        case "C" -> precio -= 2000;
        default -> {
            System.out.println("Zona inválida");
            return;
        }
    }

    double descuento = (edad >= 60) ? 0.15 : (estudiante.equals("s") ? 0.10 : 0);
    double finalPrecio = precio * (1 - descuento);

    System.out.println("\n===== NUEVO RESUMEN =====");
    System.out.println("Zona: " + zona + " | Asiento: " + asiento +
            " | Base: $" + precio + " | Desc: " + (descuento * 100) + "% | Total: $" + finalPrecio);
    System.out.print("¿Confirmar modificación? (s/n): ");
    if (!scanner.nextLine().equalsIgnoreCase("s")) return;

    carrito[num - 1][0] = zona;
    carrito[num - 1][1] = String.valueOf(precio);
    carrito[num - 1][2] = descuento * 100 + "%";
    carrito[num - 1][3] = String.valueOf(finalPrecio);
    carrito[num - 1][4] = String.valueOf(asiento);
    marcarAsiento(zona, asiento);

    System.out.println("Entrada modificada con éxito.");
}

}
