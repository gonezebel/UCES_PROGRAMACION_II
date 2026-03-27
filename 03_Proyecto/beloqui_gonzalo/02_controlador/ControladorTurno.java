package controlador;

import modelo.Notificacion;
import modelo.Paciente;
import modelo.Turno;

public class ControladorTurno {
    // Metodos
    public boolean asignarTurno(Turno turno) {
        return turno.asignarTurno();
    }

    public void anularTurno(Turno turno) {
        turno.anularTurno();
    }

    public String enviarRecordatorio(Notificacion notificacion, Paciente paciente) {
        return notificacion.enviarA(paciente);
    }
}
