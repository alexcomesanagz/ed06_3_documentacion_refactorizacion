package org.ed06.app;

import org.ed06.model.Cliente;
import org.ed06.model.Habitacion;
import org.ed06.model.Hotel;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    /**
     * funciones del menú con el número asignado
     */
    private static final int REGISTRAR_HABITACION = 1;
    private static final int LISTAR_HABITACIONES_DISPONIBLES = 2;
    private static final int RESERVAR_HABITACION = 3;
    private static final int LISTAR_RESERVAS = 4;
    private static final int LISTAR_CLIENTES = 5;
    private static final int REGISTRAR_CLIENTE = 6;
    private static final int SALIR = 0;

    /**
     * crea objeto hotel y le registra habitaciones y clientes
     * muestra el menu con las opciones a emplear
     * @param args
     */

    public static void main(String[] args) {

        Hotel hotel = new Hotel("El mirador", "Calle Entornos de Desarrollo 6", "123456789");

        hotel.registrarHabitacion("SIMPLE", 50);
        hotel.registrarHabitacion("DOBLE", 80);
        hotel.registrarHabitacion("SUITE", 120);
        hotel.registrarHabitacion("LITERAS", 200);
        hotel.registrarHabitacion("SIMPLE", 65);
        hotel.registrarHabitacion("DOBLE", 100);
        hotel.registrarHabitacion("SUITE", 150);
        hotel.registrarHabitacion("LITERAS", 250);

        hotel.registrarCliente("Daniel", "daniel@daniel.com", "12345678A", true);
        hotel.registrarCliente("Adrián", "adrian@adrian.es", "87654321B", false);

        // Mostramos el menú
        while (true) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case REGISTRAR_HABITACION:
                    registrarHabitacion(hotel);
                    break;
                case LISTAR_HABITACIONES_DISPONIBLES:
                    hotel.listarHabitacionesDisponibles();
                    break;
                case RESERVAR_HABITACION:
                    reservarHabitacion(hotel);
                    break;
                case LISTAR_RESERVAS:
                    hotel.listarReservas();
                    break;
                case LISTAR_CLIENTES:
                    hotel.listarClientes();
                    break;
                case REGISTRAR_CLIENTE:
                    registrarCliente(hotel);
                    break;
                case SALIR:
                    System.out.println("Saliendo del programa...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }

    private static void registrarCliente(Hotel hotel) {
        String nombre;
        String email;
        String dni;

        nombre = getNombre();
        email = getEmail();
        dni = getDni();
        System.out.println("¿Es VIP? (true/false): ");
        boolean esVip = scanner.nextBoolean();
        hotel.registrarCliente(nombre, email, dni, esVip);
    }

    private static String getDni() {
        String dni;
        while (true) {
            try {
                System.out.println("Introduce el DNI del cliente: ");
                dni = scanner.next();
                Cliente.validarDni(dni);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("DNI no válido. Inténtalo de nuevo.");
            }
        }
        return dni;
    }

    private static String getEmail() {
        String email;
        while (true) {
            try {
                System.out.println("Introduce el email del cliente: ");
                email = scanner.next();
                Cliente.validarEmail(email);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Email no válido. Inténtalo de nuevo.");
            }
        }
        return email;
    }

    private static String getNombre() {
        String nombre;
        while(true) {
            try {
                System.out.println("Introduce el nombre del cliente: ");
                nombre = scanner.next();
                Cliente.validarNombre(nombre);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Nombre no válido. Inténtalo de nuevo.");
            }
        }
        return nombre;
    }

    /**
     * emplea metodos de la propia clase
     * para reservar una habitacion en el objeto hotel
     * @param hotel
     */

    private static void reservarHabitacion(Hotel hotel) {
        int clienteId = getClienteId();
        String tipo;
        System.out.println("Introduce el tipo de habitación (SIMPLE, DOBLE, SUITE): ");
        tipo = scanner.next();
        LocalDate fechaEntrada = getLocalDate("Introduce la fecha de entrada (año): ", "Introduce la fecha de entrada (mes): ", "Introduce la fecha de entrada (día): ");
        LocalDate fechaSalida = getLocalDate("Introduce la fecha de salida (año): ", "Introduce la fecha de salida (mes): ", "Introduce la fecha de salida (día): ");
        int numeroHabitacion = hotel.reservarHabitacion(clienteId, tipo, fechaEntrada,
                fechaSalida);
        System.out.println("Datos de la habitacion");
        Habitacion habitacion = hotel.getHabitacion(numeroHabitacion);
        System.out.println(
                "Habitación #" + habitacion.getNumero() + " - Tipo: " + habitacion.getTipo()
                        + " - Precio base: " + habitacion.getPrecioBase());
        System.out.println("Número de habitación reservada: " + numeroHabitacion);
    }

    private static LocalDate getLocalDate(String x, String x1, String x2) {
        System.out.println(x);
        int anioEntrada = scanner.nextInt();
        scanner.nextLine();
        System.out.println(x1);
        int mesEntrada = scanner.nextInt();
        scanner.nextLine();
        System.out.println(x2);
        int diaEntrada = scanner.nextInt();
        scanner.nextLine();
        LocalDate fechaEntrada = LocalDate.of(anioEntrada, mesEntrada, diaEntrada);
        return fechaEntrada;
    }

    private static int getClienteId() {
        System.out.println("Introduce el id del cliente: ");
        int clienteId = scanner.nextInt();
        return clienteId;
    }

    /**
     * registra una habitacion en el objeto hotel
     * @param hotel
     */

    private static void registrarHabitacion(Hotel hotel) {
        String tipo;
        System.out.println("Introduce el tipo de habitación (SIMPLE, DOBLE, SUITE): ");
        tipo = scanner.nextLine();
        System.out.println("Introduce el precio base de la habitación: ");
        double precioBase = scanner.nextDouble();
        scanner.nextLine();
        hotel.registrarHabitacion(tipo, precioBase);
        System.out.println("Habitación registrada: " + tipo + " - Precio base: " + precioBase);
    }

    private static void mostrarMenu() {
        System.out.println("Menú:");
        System.out.println("1. Registrar habitación");
        System.out.println("2. Listar habitaciones disponibles");
        System.out.println("3. Reservar habitación");
        System.out.println("4. Listar reservas");
        System.out.println("5. Listar clientes");
        System.out.println("6. Registrar cliente");
        System.out.println("0. Salir");
    }
}