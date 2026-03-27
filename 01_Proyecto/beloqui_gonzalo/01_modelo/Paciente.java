package com.beloqui.modelo;

public class Paciente extends Persona implements Notificable {
    // Atributos
    private int numeroHistoriaClinica;
    private String obraSocial;
    private String email;

    // Constructores
    public Paciente() {
        super();
        this.numeroHistoriaClinica = 0;
        this.obraSocial = "";
        this.email = "";
    }

    public Paciente(String nombre, String apellido, String dni, String telefono,
            int numeroHistoriaClinica, String obraSocial, String email) {
        super(nombre, apellido, dni, telefono);
        this.numeroHistoriaClinica = numeroHistoriaClinica;
        this.obraSocial = obraSocial;
        this.email = email;
    }

    // Getters y setters
    public int getNumeroHistoriaClinica() {
        return this.numeroHistoriaClinica;
    }

    public void setNumeroHistoriaClinica(int numeroHistoriaClinica) {
        this.numeroHistoriaClinica = numeroHistoriaClinica;
    }

    public String getObraSocial() {
        return this.obraSocial;
    }

    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Metodos
    public boolean validarEmail() {
        return this.email != null && this.email.contains("@") && this.email.contains(".");
    }

    @Override
    public String obtenerDestinoNotificacion() {
        return this.email;
    }

    @Override
    public String enviarNotificacion(String mensaje) {
        return "Notificacion enviada al paciente " + getNombreCompleto() + " a " + this.email
                + ": " + mensaje;
    }

    @Override
    public String mostrarDatos() {
        return "Paciente: " + getNombreCompleto() + " - HC: " + this.numeroHistoriaClinica
                + " - Obra social: " + this.obraSocial;
    }

    @Override
    public String toString() {
        return this.mostrarDatos();
    }
}
