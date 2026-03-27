package modelo;

public class Turno {
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
        this.idTurno = 0;
        this.paciente = null;
        this.profesional = null;
        this.agenda = null;
        this.fecha = "";
        this.hora = "";
        this.estado = "";
    }

    public Turno(int idTurno, Paciente paciente, Profesional profesional, Agenda agenda,
            String fecha, String hora, String estado) {
        this.idTurno = idTurno;
        this.paciente = paciente;
        this.profesional = profesional;
        this.agenda = agenda;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    // Getters y setters
    public int getIdTurno() {
        return this.idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
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
        return this.fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return this.hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Metodos
    public boolean asignarTurno() {
        if (this.agenda != null && this.agenda.estaActiva()) {
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
}
