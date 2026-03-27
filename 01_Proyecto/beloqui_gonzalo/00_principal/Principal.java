package com.beloqui.main;

import com.beloqui.controlador.*;
import com.beloqui.modelo.*;
import com.beloqui.vista.*;

public class Principal {
    public static void main(String[] args) {
        // Objetos principales
        Paciente paciente = new Paciente("Gonzalo", "Beloqui", "35426789", "1155551234",
                1025, "Swiss Medical", "gonzalo.beloqui@mail.com");

        Profesional profesional = new Profesional("Juan", "Perez", "28765432", "1144449876",
                "MN12345", "Cardiologia", "jperez@hospital.com");

        Agenda agenda = new Agenda(1, profesional, "Lunes", "08:00", "12:00", "Activa");

        Turno turno = new Turno(5001, paciente, profesional, agenda, "15/04/2026", "09:30",
                "Pendiente");

        Notificacion notificacion = new Notificacion(1, "Recordatorio",
                "Recuerde su turno del 15/04/2026 a las 09:30.", "14/04/2026", false);

        ControladorTurno controladorTurno = new ControladorTurno();
        VistaTurno vistaTurno = new VistaTurno();

        // Ejecucion del sistema
        vistaTurno.mostrarMensaje(paciente.mostrarDatos());
        vistaTurno.mostrarMensaje(profesional.mostrarDatos());
        vistaTurno.mostrarMensaje(agenda.toString());

        if (controladorTurno.asignarTurno(turno)) {
            vistaTurno.mostrarMensaje(turno.mostrarResumenTurno());
        } else {
            vistaTurno.mostrarMensaje("No fue posible asignar el turno.");
        }

        vistaTurno.mostrarMensaje(controladorTurno.enviarRecordatorio(notificacion, paciente));
        vistaTurno.mostrarMensaje(notificacion.toString());
    }
}
