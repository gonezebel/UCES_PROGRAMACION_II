package com.apellido.main;

import com.apellido.modelo.Agenda;
import com.apellido.modelo.Notificacion;
import com.apellido.modelo.Paciente;
import com.apellido.modelo.Profesional;
import com.apellido.modelo.Turno;

public class Principal {
    public static void main(String[] args) {
        Paciente paciente = new Paciente("Maria", "Garcia", "35426789", "1155551234",
                1025, "Swiss Medical", "maria@mail.com");

        Profesional profesional = new Profesional("Juan", "Perez", "28765432", "1144449876",
                "MN12345", "Cardiologia", "jperez@hospital.com");

        Agenda agenda = new Agenda(1, profesional, "Lunes", "08:00", "12:00", "Activa");

        Turno turno = new Turno(5001, paciente, profesional, agenda, "15/04/2026", "09:30",
                "Pendiente");

        Notificacion notificacion = new Notificacion(1, "Recordatorio",
                "Recuerde su turno del 15/04/2026 a las 09:30.", "14/04/2026", false);

        System.out.println(paciente.mostrarDatos());
        System.out.println(profesional.mostrarDatos());
        System.out.println(agenda);

        if (turno.asignarTurno()) {
            System.out.println(turno.mostrarResumenTurno());
        } else {
            System.out.println("No fue posible asignar el turno.");
        }

        System.out.println(notificacion.enviarA(paciente));
        System.out.println(notificacion);
    }
}
