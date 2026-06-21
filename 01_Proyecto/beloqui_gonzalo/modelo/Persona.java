package com.beloqui.modelo;

import java.io.Serializable;

public abstract class Persona implements Serializable {
    private static final long serialVersionUID = 1L;

    protected static final String SEPARADOR_ARCHIVO = ";";

    // Atributos
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;

    // Constructores
    public Persona() {
        setNombre("");
        setApellido("");
        setDni("");
        setTelefono("");
    }

    public Persona(String nombre, String apellido, String dni, String telefono) {
        setNombre(nombre);
        setApellido(apellido);
        setDni(dni);
        setTelefono(telefono);
    }

    // Getters y setters
    public String getNombre() {
        return this.nombre.trim();
    }

    public void setNombre(String nombre) {
        this.nombre = normalizarTexto(nombre);
    }

    public String getApellido() {
        return this.apellido.trim();
    }

    public void setApellido(String apellido) {
        this.apellido = normalizarTexto(apellido);
    }

    public String getDni() {
        return this.dni.trim();
    }

    public void setDni(String dni) {
        this.dni = normalizarTexto(dni);
    }

    public String getTelefono() {
        return this.telefono.trim();
    }

    public void setTelefono(String telefono) {
        this.telefono = normalizarTexto(telefono);
    }

    // Metodos
    public String getNombreCompleto() {
        return getNombre() + " " + getApellido();
    }

    public boolean validarNombreApellido() {
        return !getNombre().isEmpty() && !getApellido().isEmpty();
    }

    public boolean validarDni() {
        return getDni().matches("\\d{7,8}");
    }

    public boolean validarTelefono() {
        return getTelefono().matches("\\d{8,15}");
    }

    public abstract String mostrarDatos();

    protected static String normalizarTexto(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.replace(SEPARADOR_ARCHIVO, ",").trim();
    }
}
