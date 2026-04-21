package com.beloqui.modelo;

public class Agenda {
    private static int contadorAgendas = 1;

    // Atributos
    private int idAgenda;
    private Profesional profesional;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private String estado;

    // Constructores
    public Agenda() {
        this.idAgenda = contadorAgendas++;
        this.profesional = null;
        this.diaSemana = "";
        this.horaInicio = "";
        this.horaFin = "";
        this.estado = "";
    }

    public Agenda(int idAgenda, Profesional profesional, String diaSemana, String horaInicio,
            String horaFin, String estado) {
        setIdAgenda(idAgenda);
        this.profesional = profesional;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
    }

    // Getters y setters
    public int getIdAgenda() {
        return this.idAgenda;
    }

    public void setIdAgenda(int idAgenda) {
        this.idAgenda = idAgenda;
        if (idAgenda >= contadorAgendas) {
            contadorAgendas = idAgenda + 1;
        }
    }

    public Profesional getProfesional() {
        return this.profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    public String getDiaSemana() {
        return this.diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getHoraInicio() {
        return this.horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return this.horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Metodos
    public void suspenderAgenda() {
        this.estado = "Suspendida";
    }

    public void activarAgenda() {
        this.estado = "Activa";
    }

    public boolean estaActiva() {
        return this.estado != null && this.estado.equalsIgnoreCase("Activa");
    }

    @Override
    public String toString() {
        String profesionalTexto = this.profesional == null
                ? "sin profesional asociado"
                : "Profesional ID " + this.profesional.getIdProfesional();
        return "Agenda " + this.idAgenda + " - " + profesionalTexto + " - " + this.diaSemana + " de " + this.horaInicio
                + " a " + this.horaFin + " - Estado: " + this.estado;
    }
}
