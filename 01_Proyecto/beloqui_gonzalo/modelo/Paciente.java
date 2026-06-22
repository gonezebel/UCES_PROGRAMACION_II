package com.beloqui.modelo;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Paciente extends Persona implements Notificable {
    private static final long serialVersionUID = 1L;
    private static int contadorPacientes = 1;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Atributos
    private int idPaciente;
    private int numeroHistoriaClinica;
    private String obraSocial;
    private String email;
    private String fechaNacimiento;
    private String sexo;

    // Constructores
    public Paciente() {
        super();
        this.idPaciente = contadorPacientes++;
        this.numeroHistoriaClinica = 0;
        setObraSocial("");
        setEmail("");
        setFechaNacimiento("");
        setSexo("");
    }

    public Paciente(String nombre, String apellido, String dni, String telefono,
            int numeroHistoriaClinica, String obraSocial, String email, String fechaNacimiento,
            String sexo) {
        super(nombre, apellido, dni, telefono);
        this.idPaciente = contadorPacientes++;
        this.numeroHistoriaClinica = numeroHistoriaClinica;
        setObraSocial(obraSocial);
        setEmail(email);
        setFechaNacimiento(fechaNacimiento);
        setSexo(sexo);
    }

    public Paciente(int idPaciente, String nombre, String apellido, String dni, String telefono,
            int numeroHistoriaClinica, String obraSocial, String email, String fechaNacimiento,
            String sexo) {
        super(nombre, apellido, dni, telefono);
        setIdPaciente(idPaciente);
        this.numeroHistoriaClinica = numeroHistoriaClinica;
        setObraSocial(obraSocial);
        setEmail(email);
        setFechaNacimiento(fechaNacimiento);
        setSexo(sexo);
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

    public String getFechaNacimiento() {
        return this.fechaNacimiento.trim();
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = normalizarTexto(fechaNacimiento);
    }

    public String getSexo() {
        return this.sexo.trim();
    }

    public void setSexo(String sexo) {
        this.sexo = normalizarTexto(sexo);
    }

    // Metodos
    public boolean validarEmail() {
        return getEmail().contains("@") && getEmail().contains(".");
    }

    public boolean validarFechaNacimiento() {
        return parsearFecha(getFechaNacimiento()) != null;
    }

    public boolean validarSexo() {
        return getSexo().equalsIgnoreCase("Femenino")
                || getSexo().equalsIgnoreCase("Masculino");
    }

    public boolean validarDatos() {
        return validarNombreApellido() && validarDni() && validarTelefono() && validarEmail()
                && validarFechaNacimiento() && validarSexo();
    }

    public boolean esPediatricoEnFecha(String fechaTurno) {
        LocalDate nacimiento = parsearFecha(getFechaNacimiento());
        LocalDate fecha = parsearFecha(fechaTurno);
        if (nacimiento == null || fecha == null || fecha.isBefore(nacimiento)) {
            return false;
        }
        return Period.between(nacimiento, fecha).getYears() < 18;
    }

    public boolean esPediatricoActual() {
        LocalDate nacimiento = parsearFecha(getFechaNacimiento());
        if (nacimiento == null) {
            return false;
        }
        return Period.between(nacimiento, LocalDate.now()).getYears() < 18;
    }

    public boolean esSexoFemenino() {
        return getSexo().equalsIgnoreCase("Femenino");
    }

    @Override
    public String obtenerDestinoNotificacion() {
        return getEmail();
    }

    @Override
    public String enviarNotificacion(String mensaje) {
        return "Notificación enviada al paciente " + getNombreCompleto() + " a " + getEmail()
                + ": " + mensaje;
    }

    @Override
    public String mostrarDatos() {
        return "Paciente ID " + this.idPaciente + ": " + getNombreCompleto()
                + " - HC: " + this.numeroHistoriaClinica
                + " - Obra social: " + getObraSocial()
                + " - Fecha de nacimiento: " + getFechaNacimiento()
                + " - Sexo: " + getSexo();
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
                + getEmail() + SEPARADOR_ARCHIVO
                + getFechaNacimiento() + SEPARADOR_ARCHIVO
                + getSexo();
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
                    datos[6],
                    "",
                    "");
        }

        if (datos.length != 8) {
            if (datos.length == 9) {
                return new Paciente(
                        Integer.parseInt(datos[0]),
                        datos[1],
                        datos[2],
                        datos[3],
                        datos[4],
                        Integer.parseInt(datos[5]),
                        datos[6],
                        datos[7],
                        datos[8],
                        "");
            }
            if (datos.length != 10) {
                throw new IllegalArgumentException("La línea no representa un paciente válido.");
            }

            return new Paciente(
                    Integer.parseInt(datos[0]),
                    datos[1],
                    datos[2],
                    datos[3],
                    datos[4],
                    Integer.parseInt(datos[5]),
                    datos[6],
                    datos[7],
                    datos[8],
                    datos[9]);
        }

        return new Paciente(
                Integer.parseInt(datos[0]),
                datos[1],
                datos[2],
                datos[3],
                datos[4],
                Integer.parseInt(datos[5]),
                datos[6],
                datos[7],
                "",
                "");
    }

    private LocalDate parsearFecha(String fecha) {
        try {
            return LocalDate.parse(fecha, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
