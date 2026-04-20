package com.beloqui.modelo;

public class Profesional extends Persona implements Notificable {
    private static final long serialVersionUID = 1L;

    // Atributos
    private String matricula;
    private String especialidad;
    private String emailInstitucional;

    // Constructores
    public Profesional() {
        super();
        setMatricula("");
        setEspecialidad("");
        setEmailInstitucional("");
    }

    public Profesional(String nombre, String apellido, String dni, String telefono,
            String matricula, String especialidad, String emailInstitucional) {
        super(nombre, apellido, dni, telefono);
        setMatricula(matricula);
        setEspecialidad(especialidad);
        setEmailInstitucional(emailInstitucional);
    }

    // Getters y setters
    public String getMatricula() {
        return this.matricula.trim();
    }

    public void setMatricula(String matricula) {
        this.matricula = normalizarTexto(matricula);
    }

    public String getEspecialidad() {
        return this.especialidad.trim();
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = normalizarTexto(especialidad);
    }

    public String getEmailInstitucional() {
        return this.emailInstitucional.trim();
    }

    public void setEmailInstitucional(String emailInstitucional) {
        this.emailInstitucional = normalizarTexto(emailInstitucional);
    }

    // Metodos
    public boolean estaDisponible(String estadoAgenda) {
        return estadoAgenda != null && estadoAgenda.equalsIgnoreCase("Activa");
    }

    @Override
    public String obtenerDestinoNotificacion() {
        return getEmailInstitucional();
    }

    @Override
    public String enviarNotificacion(String mensaje) {
        return "Notificacion enviada al profesional " + getNombreCompleto() + " a "
                + getEmailInstitucional() + ": " + mensaje;
    }

    @Override
    public String mostrarDatos() {
        return "Profesional: " + getNombreCompleto() + " - Especialidad: " + getEspecialidad()
                + " - Matricula: " + getMatricula();
    }

    @Override
    public String toString() {
        return getNombre() + SEPARADOR_ARCHIVO
                + getApellido() + SEPARADOR_ARCHIVO
                + getDni() + SEPARADOR_ARCHIVO
                + getTelefono() + SEPARADOR_ARCHIVO
                + getMatricula() + SEPARADOR_ARCHIVO
                + getEspecialidad() + SEPARADOR_ARCHIVO
                + getEmailInstitucional();
    }

    public static Profesional fromString(String linea) {
        String[] datos = linea.split(SEPARADOR_ARCHIVO, -1);
        if (datos.length != 7) {
            throw new IllegalArgumentException("La linea no representa un profesional valido.");
        }

        return new Profesional(
                datos[0],
                datos[1],
                datos[2],
                datos[3],
                datos[4],
                datos[5],
                datos[6]);
    }
}
