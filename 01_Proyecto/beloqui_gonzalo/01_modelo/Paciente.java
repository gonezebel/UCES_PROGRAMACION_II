package com.beloqui.modelo;

public class Paciente extends Persona implements Notificable {
    private static final long serialVersionUID = 1L;
    private static int contadorPacientes = 1;

    // Atributos
    private int idPaciente;
    private int numeroHistoriaClinica;
    private String obraSocial;
    private String email;

    // Constructores
    public Paciente() {
        super();
        this.idPaciente = contadorPacientes++;
        this.numeroHistoriaClinica = 0;
        setObraSocial("");
        setEmail("");
    }

    public Paciente(String nombre, String apellido, String dni, String telefono,
            int numeroHistoriaClinica, String obraSocial, String email) {
        super(nombre, apellido, dni, telefono);
        this.idPaciente = contadorPacientes++;
        this.numeroHistoriaClinica = numeroHistoriaClinica;
        setObraSocial(obraSocial);
        setEmail(email);
    }

    public Paciente(int idPaciente, String nombre, String apellido, String dni, String telefono,
            int numeroHistoriaClinica, String obraSocial, String email) {
        super(nombre, apellido, dni, telefono);
        setIdPaciente(idPaciente);
        this.numeroHistoriaClinica = numeroHistoriaClinica;
        setObraSocial(obraSocial);
        setEmail(email);
    }

    // Getters y setters
    public int getIdPaciente() {
        return this.idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
        if (idPaciente >= contadorPacientes) {
            contadorPacientes = idPaciente + 1;
        }
    }

    public int getNumeroHistoriaClinica() {
        return this.numeroHistoriaClinica;
    }

    public void setNumeroHistoriaClinica(int numeroHistoriaClinica) {
        this.numeroHistoriaClinica = numeroHistoriaClinica;
    }

    public String getObraSocial() {
        return this.obraSocial.trim();
    }

    public void setObraSocial(String obraSocial) {
        this.obraSocial = normalizarTexto(obraSocial);
    }

    public String getEmail() {
        return this.email.trim();
    }

    public void setEmail(String email) {
        this.email = normalizarTexto(email);
    }

    // Metodos
    public boolean validarEmail() {
        return getEmail().contains("@") && getEmail().contains(".");
    }

    @Override
    public String obtenerDestinoNotificacion() {
        return getEmail();
    }

    @Override
    public String enviarNotificacion(String mensaje) {
        return "Notificacion enviada al paciente " + getNombreCompleto() + " a " + getEmail()
                + ": " + mensaje;
    }

    @Override
    public String mostrarDatos() {
        return "Paciente ID " + this.idPaciente + ": " + getNombreCompleto()
                + " - HC: " + this.numeroHistoriaClinica
                + " - Obra social: " + getObraSocial();
    }

    @Override
    public String toString() {
        return this.idPaciente + SEPARADOR_ARCHIVO
                + getNombre() + SEPARADOR_ARCHIVO
                + getApellido() + SEPARADOR_ARCHIVO
                + getDni() + SEPARADOR_ARCHIVO
                + getTelefono() + SEPARADOR_ARCHIVO
                + this.numeroHistoriaClinica + SEPARADOR_ARCHIVO
                + getObraSocial() + SEPARADOR_ARCHIVO
                + getEmail();
    }

    public static Paciente fromString(String linea) {
        String[] datos = linea.split(SEPARADOR_ARCHIVO, -1);
        if (datos.length == 7) {
            return new Paciente(
                    datos[0],
                    datos[1],
                    datos[2],
                    datos[3],
                    Integer.parseInt(datos[4]),
                    datos[5],
                    datos[6]);
        }

        if (datos.length != 8) {
            throw new IllegalArgumentException("La linea no representa un paciente valido.");
        }

        return new Paciente(
                Integer.parseInt(datos[0]),
                datos[1],
                datos[2],
                datos[3],
                datos[4],
                Integer.parseInt(datos[5]),
                datos[6],
                datos[7]);
    }
}
