package com.beloqui.main;

import com.beloqui.controlador.GestorAgendasXML;
import com.beloqui.controlador.GestorPacientesTexto;
import com.beloqui.controlador.GestorProfesionalesTexto;
import com.beloqui.modelo.Agenda;
import com.beloqui.modelo.Paciente;
import com.beloqui.modelo.Profesional;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private static final String CARPETA_DATOS = "04_datos/";
    private static final String COMANDO_CANCELAR = "cancelar";
    private static final GestorPacientesTexto gestorPacientesTexto =
            new GestorPacientesTexto(CARPETA_DATOS + "pacientes.txt");
    private static final GestorProfesionalesTexto gestorProfesionalesTexto =
            new GestorProfesionalesTexto(CARPETA_DATOS + "profesionales.txt");
    private static final GestorAgendasXML gestorAgendasXML =
            new GestorAgendasXML(CARPETA_DATOS + "agendas.xml");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Profesional> profesionales = crearProfesionalesIniciales();

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero(scanner, "Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    agregarPacienteDesdeTeclado(scanner);
                    break;
                case 2:
                    agregarProfesionalDesdeTeclado(scanner);
                    break;
                case 3:
                    agregarAgendaDesdeTeclado(scanner, profesionales);
                    break;
                case 4:
                    buscarPaciente(scanner);
                    break;
                case 5:
                    buscarProfesional(scanner);
                    break;
                case 6:
                    buscarAgenda(scanner, profesionales);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema.");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Sistema de turnos ---");
        System.out.println("1. Agregar paciente");
        System.out.println("2. Agregar profesional");
        System.out.println("3. Agregar agenda");
        System.out.println("4. Buscar paciente");
        System.out.println("5. Buscar profesional");
        System.out.println("6. Buscar agenda por profesional y dia");
        System.out.println("0. Salir");
        System.out.println("Escriba \"" + COMANDO_CANCELAR + "\" dentro de una opcion para volver al menu.");
    }

    private static List<Paciente> crearPacientesIniciales() {
        List<Paciente> pacientes = new ArrayList<>();
        pacientes.add(new Paciente("Gonzalo", "Beloqui", "35426789", "1155551234",
                1025, "Swiss Medical", "gonzalo.beloqui@mail.com"));
        pacientes.add(new Paciente("Ana", "Gomez", "40111222", "1166667788",
                1026, "OSDE", "ana.gomez@mail.com"));
        return pacientes;
    }

    private static List<Profesional> crearProfesionalesIniciales() {
        List<Profesional> profesionales = new ArrayList<>();
        profesionales.add(new Profesional("Juan", "Perez", "28765432", "1144449876",
                "MN12345", "Cardiologia", "jperez@hospital.com"));
        profesionales.add(new Profesional("Laura", "Martinez", "30999888", "1133332222",
                "MN54321", "Pediatria", "lmartinez@hospital.com"));
        return profesionales;
    }

    private static List<Agenda> crearAgendasIniciales(List<Profesional> profesionales) {
        List<Agenda> agendas = new ArrayList<>();
        agendas.add(new Agenda(1, profesionales.get(0), "Lunes", "08:00", "12:00", "Activa"));
        agendas.add(new Agenda(2, profesionales.get(1), "Miercoles", "14:00", "18:00", "Activa"));
        return agendas;
    }

    private static void agregarPacienteDesdeTeclado(Scanner scanner) {
        System.out.println("\nCarga de paciente");
        String nombre = leerTexto(scanner, "Nombre: ");
        if (esCancelacion(nombre)) {
            cancelarOperacion();
            return;
        }
        String apellido = leerTexto(scanner, "Apellido: ");
        if (esCancelacion(apellido)) {
            cancelarOperacion();
            return;
        }
        String dni = leerTexto(scanner, "DNI: ");
        if (esCancelacion(dni)) {
            cancelarOperacion();
            return;
        }
        String telefono = leerTexto(scanner, "Telefono: ");
        if (esCancelacion(telefono)) {
            cancelarOperacion();
            return;
        }
        Integer historiaClinica = leerEnteroCancelable(scanner, "Numero de historia clinica: ");
        if (historiaClinica == null) {
            cancelarOperacion();
            return;
        }
        String obraSocial = leerTexto(scanner, "Obra social: ");
        if (esCancelacion(obraSocial)) {
            cancelarOperacion();
            return;
        }
        String email = leerTexto(scanner, "Email: ");
        if (esCancelacion(email)) {
            cancelarOperacion();
            return;
        }

        Paciente paciente = new Paciente(nombre, apellido, dni, telefono, historiaClinica,
                obraSocial, email);
        gestorPacientesTexto.agregarPaciente(paciente);
        System.out.println("Paciente agregado en pacientes.txt.");
    }

    private static void agregarProfesionalDesdeTeclado(Scanner scanner) {
        System.out.println("\nCarga de profesional");
        String nombre = leerTexto(scanner, "Nombre: ");
        if (esCancelacion(nombre)) {
            cancelarOperacion();
            return;
        }
        String apellido = leerTexto(scanner, "Apellido: ");
        if (esCancelacion(apellido)) {
            cancelarOperacion();
            return;
        }
        String dni = leerTexto(scanner, "DNI: ");
        if (esCancelacion(dni)) {
            cancelarOperacion();
            return;
        }
        String telefono = leerTexto(scanner, "Telefono: ");
        if (esCancelacion(telefono)) {
            cancelarOperacion();
            return;
        }
        String matricula = leerTexto(scanner, "Matricula: ");
        if (esCancelacion(matricula)) {
            cancelarOperacion();
            return;
        }
        String especialidad = leerTexto(scanner, "Especialidad: ");
        if (esCancelacion(especialidad)) {
            cancelarOperacion();
            return;
        }
        String emailInstitucional = leerTexto(scanner, "Email institucional: ");
        if (esCancelacion(emailInstitucional)) {
            cancelarOperacion();
            return;
        }

        Profesional profesional = new Profesional(nombre, apellido, dni, telefono, matricula,
                especialidad, emailInstitucional);
        gestorProfesionalesTexto.agregarProfesional(profesional);
        System.out.println("Profesional agregado en profesionales.txt.");
    }

    private static void agregarAgendaDesdeTeclado(Scanner scanner, List<Profesional> profesionalesIniciales) {
        List<Profesional> profesionales = gestorProfesionalesTexto.leerProfesionales();
        if (profesionales.isEmpty()) {
            profesionales = profesionalesIniciales;
        }

        if (profesionales.isEmpty()) {
            System.out.println("No hay profesionales cargados para asociar a la agenda.");
            return;
        }

        System.out.println("\nProfesionales disponibles:");
        for (Profesional profesional : profesionales) {
            System.out.println("ID " + profesional.getIdProfesional() + " - "
                    + profesional.getNombreCompleto() + " - " + profesional.getEspecialidad());
        }

        Integer idProfesional = leerEnteroCancelable(scanner, "ID del profesional: ");
        if (idProfesional == null) {
            cancelarOperacion();
            return;
        }
        Profesional profesionalSeleccionado = buscarProfesionalPorId(profesionales, idProfesional);
        if (profesionalSeleccionado == null) {
            System.out.println("No existe un profesional con ese ID.");
            return;
        }

        List<Agenda> agendas = gestorAgendasXML.leerAgendas(profesionales);
        if (agendas.isEmpty()) {
            agendas = crearAgendasIniciales(profesionalesIniciales);
        }

        int idAgenda = obtenerSiguienteIdAgenda(agendas);
        String diaSemana = leerTexto(scanner, "Dia de la semana: ");
        if (esCancelacion(diaSemana)) {
            cancelarOperacion();
            return;
        }
        String horaInicio = leerTexto(scanner, "Hora de inicio: ");
        if (esCancelacion(horaInicio)) {
            cancelarOperacion();
            return;
        }
        String horaFin = leerTexto(scanner, "Hora de fin: ");
        if (esCancelacion(horaFin)) {
            cancelarOperacion();
            return;
        }
        String estado = leerTexto(scanner, "Estado: ");
        if (esCancelacion(estado)) {
            cancelarOperacion();
            return;
        }

        Agenda agenda = new Agenda(idAgenda, profesionalSeleccionado, diaSemana, horaInicio,
                horaFin, estado);
        agendas.add(agenda);
        gestorAgendasXML.guardarAgendas(agendas);
        System.out.println("Agenda agregada en agendas.xml.");
    }

    private static void buscarPaciente(Scanner scanner) {
        String dni = leerTexto(scanner, "\nDNI del paciente: ");
        if (esCancelacion(dni)) {
            cancelarOperacion();
            return;
        }
        List<Paciente> pacientes = gestorPacientesTexto.leerPacientes();

        for (Paciente paciente : pacientes) {
            if (paciente.getDni().equals(dni)) {
                System.out.println(paciente.mostrarDatos());
                return;
            }
        }

        System.out.println("No se encontro un paciente con ese DNI.");
    }

    private static void buscarProfesional(Scanner scanner) {
        String dni = leerTexto(scanner, "\nDNI del profesional: ");
        if (esCancelacion(dni)) {
            cancelarOperacion();
            return;
        }
        List<Profesional> profesionales = gestorProfesionalesTexto.leerProfesionales();

        for (Profesional profesional : profesionales) {
            if (profesional.getDni().equals(dni)) {
                System.out.println(profesional.mostrarDatos());
                return;
            }
        }

        System.out.println("No se encontro un profesional con ese DNI.");
    }

    private static void buscarAgenda(Scanner scanner, List<Profesional> profesionalesIniciales) {
        List<Profesional> profesionales = gestorProfesionalesTexto.leerProfesionales();
        if (profesionales.isEmpty()) {
            profesionales = profesionalesIniciales;
        }

        String dniProfesional = leerTexto(scanner, "\nDNI del profesional: ");
        if (esCancelacion(dniProfesional)) {
            cancelarOperacion();
            return;
        }
        String diaSemana = leerTexto(scanner, "Dia de la semana: ");
        if (esCancelacion(diaSemana)) {
            cancelarOperacion();
            return;
        }
        List<Agenda> agendas = gestorAgendasXML.leerAgendas(profesionales);
        for (Agenda agenda : agendas) {
            Profesional profesional = agenda.getProfesional();
            if (profesional != null
                    && profesional.getDni().equals(dniProfesional)
                    && agenda.getDiaSemana().equalsIgnoreCase(diaSemana)) {
                System.out.println(agenda.toString());
                return;
            }
        }

        System.out.println("No se encontro una agenda para ese profesional en ese dia.");
    }

    private static Profesional buscarProfesionalPorId(List<Profesional> profesionales, int idProfesional) {
        for (Profesional profesional : profesionales) {
            if (profesional.getIdProfesional() == idProfesional) {
                return profesional;
            }
        }
        return null;
    }

    private static int obtenerSiguienteIdAgenda(List<Agenda> agendas) {
        int mayorId = 0;
        for (Agenda agenda : agendas) {
            if (agenda.getIdAgenda() > mayorId) {
                mayorId = agenda.getIdAgenda();
            }
        }
        return mayorId + 1;
    }

    private static boolean esCancelacion(String valor) {
        return valor != null && valor.equalsIgnoreCase(COMANDO_CANCELAR);
    }

    private static void cancelarOperacion() {
        System.out.println("Operacion cancelada.");
    }

    private static String leerTexto(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private static Integer leerEnteroCancelable(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine();
            if (esCancelacion(valor)) {
                return null;
            }
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido o escriba \"" + COMANDO_CANCELAR + "\".");
            }
        }
    }

    private static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine();
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }
        }
    }
}
