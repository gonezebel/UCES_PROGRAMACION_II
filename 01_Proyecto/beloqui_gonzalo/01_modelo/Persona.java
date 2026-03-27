package com.beloqui.modelo;

public abstract class Persona {
    // Atributos
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;

    // Constructores
    public Persona() {
        this.nombre = "";
        this.apellido = "";
        this.dni = "";
        this.telefono = "";
    }

    public Persona(String nombre, String apellido, String dni, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
    }

    // Getters y setters
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return this.dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // Metodos
    public String getNombreCompleto() {
        return this.nombre + " " + this.apellido;
    }

    public boolean validarDni() {
        return this.dni != null && this.dni.matches("\\d{7,8}");
    }

    public abstract String mostrarDatos();
}
