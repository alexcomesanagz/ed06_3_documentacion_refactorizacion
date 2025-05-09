package org.ed06.model;

import java.time.LocalDate;
import java.util.Date;

public class Reserva {
    public static final double aplicarDescuentoVip = 0.9;
    public static final int intervaloFechas = 7;
    public static final double aplicarDescuentoAdicional = 0.95;
    private int id;
    private Habitacion habitacion;
    private Cliente cliente;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double precioTotal;

    public Reserva(int id, Habitacion habitacion, Cliente cliente, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.habitacion = habitacion;
        this.cliente = cliente;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioTotal = calcularPrecioFinal();
    }

    public int getId() {
        return id;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    /**
     * calcula el precio con las fechas de entrada y salida
     * con descuentos incluidos
     * existe descuento Vio y descuento por pasar x dias
     * @return el precio con descuentos posibles aplicados
     */

    public double calcularPrecioFinal() {

        int diasDeEstancia = fechaFin.getDayOfYear() - fechaInicio.getDayOfYear();

        double precioBase = habitacion.getPrecioBase() * diasDeEstancia;

        double precioFinal = precioBase;

        if (cliente.esVip) {
            precioFinal *= aplicarDescuentoVip;
        }

        if (diasDeEstancia > intervaloFechas) {
            precioFinal *= aplicarDescuentoAdicional;
        }

        return precioFinal;
    }
}
