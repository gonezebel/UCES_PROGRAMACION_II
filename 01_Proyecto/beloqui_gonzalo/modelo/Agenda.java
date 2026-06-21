package com.beloqui.modelo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Agenda {
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static int contadorAgendas = 1;

    // Atributos
    private int idAgenda;
    private Profesional profesional;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private String fechaDesde;
    private String fechaHasta;
    private String estado;

    // Constructores
    public Agenda() {
        this.idAgenda = contadorAgendas++;
        this.profesional = null;
        setDiaSemana("");
        setHoraInicio("");
        setHoraFin("");
        setFechaDesde("");
        setFechaHasta("");
        setEstado("");
    }

    public Agenda(int idAgenda, Profesional profesional, String diaSemana, String horaInicio,
            String horaFin, String fechaDesde, String fechaHasta, String estado) {
        setIdAgenda(idAgenda);
        this.profesional = profesional;
        setDiaSemana(diaSemana);
        setHoraInicio(horaInicio);
        setHoraFin(horaFin);
        setFechaDesde(fechaDesde);
        setFechaHasta(fechaHasta);
        setEstado(estado);
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
        return this.diaSemana == null ? "" : this.diaSemana.trim();
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = normalizarTexto(diaSemana);
    }

    public String getHoraInicio() {
        return this.horaInicio == null ? "" : this.horaInicio.trim();
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = normalizarTexto(horaInicio);
    }

    public String getHoraFin() {
        return this.horaFin == null ? "" : this.horaFin.trim();
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = normalizarTexto(horaFin);
    }

    public String getEstado() {
        return this.estado == null ? "" : this.estado.trim();
    }

    public String getFechaDesde() {
        return this.fechaDesde == null ? "" : this.fechaDesde.trim();
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = normalizarTexto(fechaDesde);
    }

    public String getFechaHasta() {
        return this.fechaHasta == null ? "" : this.fechaHasta.trim();
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = normalizarTexto(fechaHasta);
    }

    public void setEstado(String estado) {
        this.estado = normalizarTexto(estado);
    }

    // Metodos
    public void suspenderAgenda() {
        this.estado = "Suspendida";
    }

    public void activarAgenda() {
        this.estado = "Activa";
    }

    public boolean estaActiva() {
        return getEstado().equalsIgnoreCase("Activa");
    }

    public boolean validarDiaSemana() {
        String dia = getDiaSemana();
        return dia.equalsIgnoreCase("Lunes")
                || dia.equalsIgnoreCase("Martes")
                || dia.equalsIgnoreCase("Miercoles")
                || dia.equalsIgnoreCase("Jueves")
                || dia.equalsIgnoreCase("Viernes")
                || dia.equalsIgnoreCase("Sabado");
    }

    public boolean validarHorario() {
        String inicio = getHoraInicio();
        String fin = getHoraFin();
        return esHorarioEnCuartos(inicio)
                && esHorarioEnCuartos(fin)
                && inicio.compareTo(fin) < 0
                && inicio.compareTo("09:00") >= 0
                && inicio.compareTo("17:45") <= 0
                && fin.compareTo("09:15") >= 0
                && fin.compareTo("18:00") <= 0;
    }

    public boolean validarVigencia() {
        LocalDate desde = parsearFecha(getFechaDesde());
        LocalDate hasta = parsearFecha(getFechaHasta());
        return desde != null && hasta != null && !desde.isAfter(hasta);
    }

    public boolean contieneHorario(String hora) {
        String valorHora = normalizarTexto(hora);
        return esHorarioEnCuartos(valorHora)
                && validarHorario()
                && valorHora.compareTo(getHoraInicio()) >= 0
                && valorHora.compareTo(getHoraFin()) < 0;
    }

    public boolean contieneFecha(String fecha) {
        LocalDate fechaTurno = parsearFecha(fecha);
        LocalDate desde = parsearFecha(getFechaDesde());
        LocalDate hasta = parsearFecha(getFechaHasta());
        return fechaTurno != null
                && desde != null
                && hasta != null
                && !fechaTurno.isBefore(desde)
                && !fechaTurno.isAfter(hasta)
                && coincideDiaSemana(fechaTurno);
    }

    public boolean seSuperponeCon(Agenda otraAgenda) {
        if (otraAgenda == null || this.profesional == null || otraAgenda.getProfesional() == null) {
            return false;
        }

        return this.profesional.getIdProfesional() == otraAgenda.getProfesional().getIdProfesional()
                && getDiaSemana().equalsIgnoreCase(otraAgenda.getDiaSemana())
                && getHoraInicio().compareTo(otraAgenda.getHoraFin()) < 0
                && getHoraFin().compareTo(otraAgenda.getHoraInicio()) > 0;
    }

    public boolean validarDisponibilidad(List<Agenda> agendasExistentes) {
        if (!validarDiaSemana() || !validarHorario() || !validarVigencia()) {
            return false;
        }

        for (Agenda agendaExistente : agendasExistentes) {
            if (agendaExistente != null
                    && agendaExistente.getIdAgenda() != this.idAgenda
                    && seSuperponeCon(agendaExistente)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        String profesionalTexto = this.profesional == null
                ? "sin profesional asociado"
                : "Profesional ID " + this.profesional.getIdProfesional();
        return "Agenda " + this.idAgenda + " - " + profesionalTexto + " - " + getDiaSemana() + " de " + getHoraInicio()
                + " a " + getHoraFin() + " - Vigencia: " + getFechaDesde() + " a "
                + getFechaHasta() + " - Estado: " + getEstado();
    }

    private String normalizarTexto(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.trim();
    }

    private boolean esHorarioEnCuartos(String hora) {
        if (!hora.matches("\\d{2}:\\d{2}")) {
            return false;
        }

        int minutos = Integer.parseInt(hora.substring(3, 5));
        return minutos == 0 || minutos == 15 || minutos == 30 || minutos == 45;
    }

    private LocalDate parsearFecha(String fecha) {
        try {
            return LocalDate.parse(fecha, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private boolean coincideDiaSemana(LocalDate fecha) {
        DayOfWeek dia = fecha.getDayOfWeek();
        return (dia == DayOfWeek.MONDAY && getDiaSemana().equalsIgnoreCase("Lunes"))
                || (dia == DayOfWeek.TUESDAY && getDiaSemana().equalsIgnoreCase("Martes"))
                || (dia == DayOfWeek.WEDNESDAY && getDiaSemana().equalsIgnoreCase("Miercoles"))
                || (dia == DayOfWeek.THURSDAY && getDiaSemana().equalsIgnoreCase("Jueves"))
                || (dia == DayOfWeek.FRIDAY && getDiaSemana().equalsIgnoreCase("Viernes"))
                || (dia == DayOfWeek.SATURDAY && getDiaSemana().equalsIgnoreCase("Sabado"));
    }
}
