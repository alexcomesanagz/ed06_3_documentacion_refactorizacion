package org.ed06.model;

public class Cliente {
    public static final String formatoEmail = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
    public static final String formatoDni = "[0-9]{8}[A-Z]";
    public static final int maxCaracteresNombre = 3;
    public int id;
    public String nombre;
    public String dni;
    public String email;
    public boolean esVip;

    public Cliente(int id, String nombre, String dni, String email, boolean esVip) {
        this.id = id;
        if(validarNombre(nombre)) {
            this.nombre = nombre;
        }
        if(validarDni(dni)) {
            this.dni = dni;
        }
        if(validarEmail(email)) {
            this.email = email;
        }
        this.esVip = esVip;
    }

    public static boolean validarNombre(String nombre) {
        if (nombre == null || nombre.trim().length() < maxCaracteresNombre) {
            throw new IllegalArgumentException("El nombre no es válido");
        }
        return true;
    }

    public static boolean validarEmail(String email) {
        if (!email.matches(formatoEmail)) {
            throw new IllegalArgumentException("El email no es válido");
        }
        return true;
    }

    public static boolean validarDni(String dni) {
        if (!dni.matches(formatoDni)) {
            throw new IllegalArgumentException("El DNI no es válido");
        }
        return true;
    }

}