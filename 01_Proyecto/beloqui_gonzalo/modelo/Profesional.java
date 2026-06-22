package com.beloqui.modelo;

public class Profesional extends Persona implements Notificable {
    private static final long serialVersionUID = 1L;
    private static int contadorProfesionales = 1;
    private static final String DOMINIO_INSTITUCIONAL = "@centrosalud.com";

    // Atributos
    private int idProfesional;
    private String matricula;
    private String especialidad;
    private String emailInstitucional;

    // Constructores
    public Profesional() {
        super();
        this.idProfesional = contadorProfesionales++;
        setMatricula("");
        setEspecialidad("");
        setEmailInstitucional("");
    }

    public Profesional(String nombre, String apellido, String dni, String telefono,
            String matricula, String especialidad, String emailInstitucional) {
        super(nombre, apellido, dni, telefono);
        this.idProfesional = contadorProfesionales++;
        setMatricula(matricula);
        setEspecialidad(especialidad);
        setEmailInstitucional(emailInstitucional);
    }

    public Profesional(int idProfesional, String nombre, String apellido, String dni, String telefono,
            String matricula, String especialidad, String emailInstitucional) {
        super(nombre, apellido, dni, telefono);
        setIdProfesional(idProfesional);
        setMatricula(matricula);
        setEspecialidad(especialidad);
        setEmailInstitucional(emailInstitucional);
    }

    // Getters y setters
    public int getIdProfesional() {
        return this.idProfesional;
    }

    public void setIdProfesional(int idProfesional) {
        this.idProfesional = idProfesional;
        if (idProfesional >= contadorProfesionales) {
            contadorProfesionales = idProfesional + 1;
        }
    }

    public String getMatricula() {
        return this.matricula.trim();
    }

    public void setMatricula(String matricula) {
        this.matricula = normalizarMatricula(matricula);
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

    public boolean validarEmailInstitucional() {
        return getEmailInstitucional().matches("[a-zA-Z0-9._%+-]+@centrosalud\\.com");
    }

    public boolean validarMatricula() {
        return getMatricula().matches("MP\\d+");
    }

    public boolean validarDatos() {
        return validarNombreApellido() && validarDni() && validarTelefono() && validarMatricula()
                && validarEmailInstitucional();
    }

    @Override
    public String obtenerDestinoNotificacion() {
        return getEmailInstitucional();
    }

    @Override
    public String enviarNotificacion(String mensaje) {
        return "Notificación enviada al profesional " + getNombreCompleto() + " a "
                + getEmailInstitucional() + ": " + mensaje;
    }

    @Override
    public String mostrarDatos() {
        return "Profesional ID " + this.idProfesional + ": " + getNombreCompleto()
                + " - Especialidad: " + getEspecialidad()
                + " - Matrícula: " + getMatricula();
    }

    @Override
    public String toString() {
        return this.idProfesional + SEPARADOR_ARCHIVO
                + getNombre() + SEPARADOR_ARCHIVO
                + getApellido() + SEPARADOR_ARCHIVO
                + getDni() + SEPARADOR_ARCHIVO
                + getTelefono() + SEPARADOR_ARCHIVO
                + getMatricula() + SEPARADOR_ARCHIVO
                + getEspecialidad() + SEPARADOR_ARCHIVO
                + getEmailInstitucional();
    }

    public static Profesional fromString(String linea) {
        String[] datos = linea.split(SEPARADOR_ARCHIVO, -1);
        if (datos.length == 7) {
            return new Profesional(
                    datos[0],
                    datos[1],
                    datos[2],
                    datos[3],
                    datos[4],
                    datos[5],
                    datos[6]);
        }

        if (datos.length != 8) {
            throw new IllegalArgumentException("La línea no representa un profesional válido.");
        }

        return new Profesional(
                Integer.parseInt(datos[0]),
                datos[1],
                datos[2],
                datos[3],
                datos[4],
                datos[5],
                datos[6],
                datos[7]);
    }

    private String normalizarMatricula(String matricula) {
        String valor = normalizarTexto(matricula).replace(" ", "");
        if (valor.isEmpty()) {
            return "";
        }
        if (valor.toUpperCase().startsWith("MP")) {
            return "MP" + valor.substring(2);
        }
        return "MP" + valor;
    }

    public static String getDominioInstitucional() {
        return DOMINIO_INSTITUCIONAL;
    }
}
