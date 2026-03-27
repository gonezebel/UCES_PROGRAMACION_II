package com.beloqui.modelo;

public class Profesional extends Persona implements Notificable {
    // Atributos
    private String matricula;
    private String especialidad;
    private String emailInstitucional;

    // Constructores
    public Profesional() {
        super();
        this.matricula = "";
        this.especialidad = "";
        this.emailInstitucional = "";
    }

    public Profesional(String nombre, String apellido, String dni, String telefono,
            String matricula, String especialidad, String emailInstitucional) {
        super(nombre, apellido, dni, telefono);
        this.matricula = matricula;
        this.especialidad = especialidad;
        this.emailInstitucional = emailInstitucional;
    }

    // Getters y setters
    public String getMatricula() {
        return this.matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEspecialidad() {
        return this.especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEmailInstitucional() {
        return this.emailInstitucional;
    }

    public void setEmailInstitucional(String emailInstitucional) {
        this.emailInstitucional = emailInstitucional;
    }

    // Metodos
    public boolean estaDisponible(String estadoAgenda) {
        return estadoAgenda != null && estadoAgenda.equalsIgnoreCase("Activa");
    }

    @Override
    public String obtenerDestinoNotificacion() {
        return this.emailInstitucional;
    }

    @Override
    public String enviarNotificacion(String mensaje) {
        return "Notificacion enviada al profesional " + getNombreCompleto() + " a "
                + this.emailInstitucional + ": " + mensaje;
    }

    @Override
    public String mostrarDatos() {
        return "Profesional: " + getNombreCompleto() + " - Especialidad: " + this.especialidad
                + " - Matricula: " + this.matricula;
    }

    @Override
    public String toString() {
        return this.mostrarDatos();
    }
}
