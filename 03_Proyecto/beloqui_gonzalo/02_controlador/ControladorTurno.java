package com.beloqui.controlador;

import com.beloqui.modelo.Notificacion;
import com.beloqui.modelo.Paciente;
import com.beloqui.modelo.Turno;

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
