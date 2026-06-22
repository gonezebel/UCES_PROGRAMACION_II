package com.beloqui.modelo;

import java.text.Normalizer;

public class Turno {
    private static int contadorTurnos = 1;

    // Atributos
    private int idTurno;
    private Paciente paciente;
    private Profesional profesional;
    private Agenda agenda;
    private String fecha;
    private String hora;
    private String estado;

    // Constructores
    public Turno() {
        this.idTurno = contadorTurnos++;
        this.paciente = null;
        this.profesional = null;
        this.agenda = null;
        setFecha("");
        setHora("");
        setEstado("");
    }

    public Turno(int idTurno, Paciente paciente, Profesional profesional, Agenda agenda,
            String fecha, String hora, String estado) {
        setIdTurno(idTurno);
        this.paciente = paciente;
        this.profesional = profesional;
        this.agenda = agenda;
        setFecha(fecha);
        setHora(hora);
        setEstado(estado);
    }

    // Getters y setters
    public int getIdTurno() {
        return this.idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
        if (idTurno >= contadorTurnos) {
            contadorTurnos = idTurno + 1;
        }
    }

    public Paciente getPaciente() {
        return this.paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Profesional getProfesional() {
        return this.profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    public Agenda getAgenda() {
        return this.agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public String getFecha() {
        return this.fecha == null ? "" : this.fecha.trim();
    }

    public void setFecha(String fecha) {
        this.fecha = normalizarTexto(fecha);
    }

    public String getHora() {
        return this.hora == null ? "" : this.hora.trim();
    }

    public void setHora(String hora) {
        this.hora = normalizarTexto(hora);
    }

    public String getEstado() {
        return this.estado == null ? "" : this.estado.trim();
    }

    public void setEstado(String estado) {
        this.estado = normalizarTexto(estado);
    }

    // Metodos
    public boolean validarDatosTurno() {
        return this.paciente != null
                && this.profesional != null
                && this.agenda != null
                && !getFecha().isEmpty()
                && esHorarioEnCuartos(getHora())
                && getHora().compareTo("09:00") >= 0
                && getHora().compareTo("17:45") <= 0
                && this.paciente.validarDatos()
                && this.profesional.validarDatos()
                && this.agenda.getProfesional() != null
                && this.profesional.getIdProfesional() == this.agenda.getProfesional().getIdProfesional()
                && validarEspecialidadSegunEdad()
                && this.agenda.contieneFecha(getFecha())
                && this.agenda.contieneHorario(getHora());
    }

    public boolean puedeAsignarse() {
        return validarDatosTurno() && this.agenda.estaActiva();
    }

    public boolean asignarTurno() {
        if (puedeAsignarse()) {
            this.estado = "Asignado";
            return true;
        }
        return false;
    }

    public void anularTurno() {
        this.estado = "Anulado";
    }

    public String mostrarResumenTurno() {
        return "Turno " + this.idTurno + " - Paciente: " + this.paciente.getNombreCompleto()
                + " - Profesional: " + this.profesional.getNombreCompleto() + " - Fecha: "
                + this.fecha + " - Hora: " + this.hora + " - Estado: " + this.estado;
    }

    @Override
    public String toString() {
        return this.mostrarResumenTurno();
    }

    private String normalizarTexto(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.trim();
    }

    private boolean validarEspecialidadSegunEdad() {
        String especialidad = normalizarClave(this.profesional.getEspecialidad());
        if (especialidad.equals("pediatria")) {
            return this.paciente.esPediatricoEnFecha(getFecha());
        }
        if (especialidad.equals("ginecologia")) {
            return this.paciente.esSexoFemenino();
        }
        return true;
    }

    private String normalizarClave(String valor) {
        String texto = normalizarTexto(valor).toLowerCase();
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    }

    private boolean esHorarioEnCuartos(String hora) {
        if (!hora.matches("\\d{2}:\\d{2}")) {
            return false;
        }

        int minutos = Integer.parseInt(hora.substring(3, 5));
        return minutos == 0 || minutos == 15 || minutos == 30 || minutos == 45;
    }
}
