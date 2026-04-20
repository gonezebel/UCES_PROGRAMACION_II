package com.beloqui.main;

import com.beloqui.controlador.*;
import com.beloqui.modelo.*;
import java.util.Arrays;
import java.util.List;

public class Principal {
    public static void main(String[] args) {
        // Objetos principales
        Paciente paciente = new Paciente("Gonzalo", "Beloqui", "35426789", "1155551234",
                1025, "Swiss Medical", "gonzalo.beloqui@mail.com");

        Profesional profesional = new Profesional("Juan", "Perez", "28765432", "1144449876",
                "MN12345", "Cardiologia", "jperez@hospital.com");

        Paciente paciente2 = new Paciente("Ana", "Gomez", "40111222", "1166667788",
                1026, "OSDE", "ana.gomez@mail.com");

        Profesional profesional2 = new Profesional("Laura", "Martinez", "30999888", "1133332222",
                "MN54321", "Pediatria", "lmartinez@hospital.com");

        Agenda agenda = new Agenda(1, profesional, "Lunes", "08:00", "12:00", "Activa");

        Turno turno = new Turno(5001, paciente, profesional, agenda, "15/04/2026", "09:30",
                "Pendiente");

        Notificacion notificacion = new Notificacion(1, "Recordatorio",
                "Recuerde su turno del 15/04/2026 a las 09:30.", "14/04/2026", false);

        // Ejecucion del sistema
        System.out.println(paciente.mostrarDatos());
        System.out.println(profesional.mostrarDatos());
        System.out.println(agenda.toString());

        if (turno.asignarTurno()) {
            System.out.println(turno.mostrarResumenTurno());
        } else {
            System.out.println("No fue posible asignar el turno.");
        }

        System.out.println(notificacion.enviarA(paciente));
        System.out.println(notificacion.toString());

        // Persistencia en archivos de texto
        List<Paciente> pacientes = Arrays.asList(paciente, paciente2);
        List<Profesional> profesionales = Arrays.asList(profesional, profesional2);

        GestorPacientesTexto gestorPacientesTexto =
                new GestorPacientesTexto("pacientes.txt");
        GestorProfesionalesTexto gestorProfesionalesTexto =
                new GestorProfesionalesTexto("profesionales.txt");

        gestorPacientesTexto.guardarPacientes(pacientes);
        gestorProfesionalesTexto.guardarProfesionales(profesionales);

        System.out.println("\nPacientes recuperados desde texto:");
        for (Paciente pacienteRecuperado : gestorPacientesTexto.leerPacientes()) {
            System.out.println(pacienteRecuperado.mostrarDatos());
        }

        System.out.println("\nProfesionales recuperados desde texto:");
        for (Profesional profesionalRecuperado : gestorProfesionalesTexto.leerProfesionales()) {
            System.out.println(profesionalRecuperado.mostrarDatos());
        }

        // Persistencia XML de la clase Paciente
        GestorPacientesXML gestorPacientesXML = new GestorPacientesXML("pacientes.xml");
        gestorPacientesXML.guardarPacientes(pacientes);

        System.out.println("\nPacientes recuperados desde XML:");
        for (Paciente pacienteRecuperado : gestorPacientesXML.leerPacientes()) {
            System.out.println(pacienteRecuperado.mostrarDatos());
        }
    }
}
