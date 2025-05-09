package org.ed06.model;

import java.time.LocalDate;
import java.util.*;

public class Hotel {
    public static final int maxNumReservas = 3;
    private String nombre;
    private String direccion;
    private String telefono;

    private final Map<Integer,Cliente> clientes = new HashMap<>();
    private final List<Habitacion> habitaciones = new ArrayList<>();
    private final Map<Integer,List<Reserva>> reservasPorHabitacion = new HashMap<>();

    public Hotel(String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }


    public void registrarHabitacion(String tipo, double precioBase) {
        Habitacion habitacion = new Habitacion(habitaciones.size() + 1, tipo, precioBase);
        habitaciones.add(habitacion);
        reservasPorHabitacion.put(habitacion.getNumero(), new ArrayList<>());
    }

    public void listarHabitacionesDisponibles() {
        for(Habitacion habitacion : habitaciones) {
            if(habitacion.isDisponible()) {
                System.out.println("Habitación #" + habitacion.getNumero() + " - Tipo: " + habitacion.getTipo() + " - Precio base: " + habitacion.getPrecioBase());
            }
        }
    }

    public Habitacion getHabitacion(int numero) {
        for(Habitacion habitacion : habitaciones) {
            if(habitacion.getNumero() == numero) {
                return habitacion;
            }
        }
        return null;
    }

    /**
     * realiza comprobaciones para
     * habitaciones suficientes en el hotel
     * existencia del id cliente
     * fechas correctas
     *
     * recorre habitaciones en busca de la que quiere el cliente
     * comprueba la posibilidad de que el cliente se convierta en VIP
     * reserva la habitacion y le dice el numero de esta al cliente
     *
     * @param clienteId
     * @param tipo tipo de habitacion a reservar
     * @param fechaEntrada fecha de comienzo de reserva
     * @param fechaSalida fecha de final de reserva
     * @return numero de las habitacion reservada
     */

    public int reservarHabitacion(int clienteId, String tipo, LocalDate fechaEntrada, LocalDate fechaSalida) {

        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones en el hotel");
            return -4;
        }

        if (this.clientes.get(clienteId) == null) {
            System.out.println("No existe el cliente con id " + clienteId);
            return -3;
        }
        Cliente cliente = this.clientes.get(clienteId);

        if (!fechaEntrada.isBefore(fechaSalida)) {
            System.out.println("La fecha de entrada es posterior a la fecha de salida");
            return -2;
        }

        for(Habitacion habitacion : habitaciones) {
            if(habitacion.getTipo().equals(tipo.toUpperCase()) && habitacion.isDisponible()) {
                int numReservas = (int) reservasPorHabitacion.values().stream().flatMap(Collection::stream)
                        .filter(reservaCliente -> reservaCliente.getCliente().equals(cliente))
                        .filter(reservaCliente -> reservaCliente.getFechaInicio()
                                .isAfter(LocalDate.now().minusYears(1))).count();

                if(numReservas > maxNumReservas && !cliente.esVip) {
                    cliente.esVip = true;
                    System.out.println("El cliente " + cliente.nombre + " ha pasado a ser VIP");
                }


                Reserva reserva = new Reserva(reservasPorHabitacion.size() + 1, habitacion, cliente, fechaEntrada, fechaSalida);
                reservasPorHabitacion.get(habitacion.getNumero()).add(reserva);

                habitacion.reservar();

                System.out.println("Reserva realizada con éxito");
                return habitacion.getNumero();
            }
        }

        System.out.println("No hay habitaciones disponibles del tipo " + tipo);
        return -1;

    }

    public void listarReservas() {
        reservasPorHabitacion.forEach((key, value) -> {
            System.out.println("Habitación #" + key);
            value.forEach(reserva -> System.out.println(
                    "Reserva #" + reserva.getId() + " - Cliente: " + reserva.getCliente().nombre
                            + " - Fecha de entrada: " + reserva.getFechaInicio()
                            + " - Fecha de salida: " + reserva.getFechaFin()));
        });
    }

    public void listarClientes() {
        for(Cliente cliente : clientes.values()) {
            System.out.println("Cliente #" + cliente.id + " - Nombre: " + cliente.nombre + " - DNI: " + cliente.dni + " - VIP: " + cliente.esVip);
        }
    }

    public void registrarCliente(String nombre, String email, String dni, boolean esVip) {
        Cliente cliente = new Cliente(clientes.size() + 1, nombre, dni, email, esVip);
        clientes.put(cliente.id, cliente);
    }

}
