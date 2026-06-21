package com.beloqui.principal;

import com.beloqui.controlador.GestorAgendasXML;
import com.beloqui.controlador.GestorEspecialidadesTexto;
import com.beloqui.controlador.GestorPacientesTexto;
import com.beloqui.controlador.GestorProfesionalesTexto;
import com.beloqui.controlador.GestorTurnosXML;
import com.beloqui.modelo.Agenda;
import com.beloqui.modelo.Especialidad;
import com.beloqui.modelo.Notificacion;
import com.beloqui.modelo.Paciente;
import com.beloqui.modelo.Profesional;
import com.beloqui.modelo.Turno;
import com.beloqui.vista.VistaConsola;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Principal {
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
    private static final String CARPETA_DATOS = "01_Proyecto/beloqui_gonzalo/04_datos/";
    private static final String COMANDO_CANCELAR = "cancelar";
    private static final String[] DIAS_SEMANA_DISPONIBLES = {
        "Lunes",
        "Martes",
        "Miercoles",
        "Jueves",
        "Viernes",
        "Sabado"
    };
    private static final String[] SEXOS_DISPONIBLES = {
        "Femenino",
        "Masculino"
    };
    private static final GestorPacientesTexto gestorPacientesTexto =
            new GestorPacientesTexto(CARPETA_DATOS + "pacientes.txt");
    private static final GestorProfesionalesTexto gestorProfesionalesTexto =
            new GestorProfesionalesTexto(CARPETA_DATOS + "profesionales.txt");
    private static final GestorEspecialidadesTexto gestorEspecialidadesTexto =
            new GestorEspecialidadesTexto(CARPETA_DATOS + "especialidades.txt");
    private static final GestorAgendasXML gestorAgendasXML =
            new GestorAgendasXML(CARPETA_DATOS + "agendas.xml");
    private static final GestorTurnosXML gestorTurnosXML =
            new GestorTurnosXML(CARPETA_DATOS + "turnos.xml");

    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        VistaConsola vista = new VistaConsola(scanner);

        int opcion;
        do {
            vista.mostrarMenu();
            opcion = leerEntero(vista, "Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    agregarPacienteDesdeTeclado(vista);
                    break;
                case 2:
                    agregarProfesionalDesdeTeclado(vista);
                    break;
                case 3:
                    agregarAgendaDesdeTeclado(vista);
                    break;
                case 4:
                    buscarPaciente(vista);
                    break;
                case 5:
                    buscarProfesional(vista);
                    break;
                case 6:
                    buscarAgenda(vista);
                    break;
                case 7:
                    asignarTurnoDesdeTeclado(vista);
                    break;
                case 8:
                    buscarTurnosPorPaciente(vista);
                    break;
                case 9:
                    agregarEspecialidadDesdeTeclado(vista);
                    break;
                case 0:
                    vista.mostrarMensaje("Saliendo del sistema.");
                    break;
                default:
                    vista.mostrarMensaje("Opcion invalida.");
            }
        } while (opcion != 0);

        scanner.close();
    }

    private static List<Paciente> crearPacientesIniciales() {
        List<Paciente> pacientes = new ArrayList<>();
        pacientes.add(new Paciente("Gonzalo", "Beloqui", "35426789", "1155551234",
                1025, "Swiss Medical", "gonzalo.beloqui@mail.com", "15/03/1990", "Masculino"));
        pacientes.add(new Paciente("Ana", "Gomez", "40111222", "1166667788",
                1026, "OSDE", "ana.gomez@mail.com", "20/08/2015", "Femenino"));
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

    private static List<Especialidad> crearEspecialidadesIniciales() {
        List<Especialidad> especialidades = new ArrayList<>();
        especialidades.add(new Especialidad("Clinica medica"));
        especialidades.add(new Especialidad("Pediatria"));
        especialidades.add(new Especialidad("Cardiologia"));
        especialidades.add(new Especialidad("Ginecologia"));
        especialidades.add(new Especialidad("Gastroenterologia"));
        return especialidades;
    }

    private static List<Agenda> crearAgendasIniciales(List<Profesional> profesionales) {
        List<Agenda> agendas = new ArrayList<>();
        agendas.add(new Agenda(1, profesionales.get(0), "Lunes", "09:00", "13:00",
                "01/01/2026", "31/12/2026", "Activa"));
        agendas.add(new Agenda(2, profesionales.get(1), "Miercoles", "14:00", "18:00",
                "01/01/2026", "31/12/2026", "Activa"));
        return agendas;
    }

    private static List<Paciente> cargarPacientes() {
        List<Paciente> pacientes = gestorPacientesTexto.leerPacientes();
        if (pacientes.isEmpty()) {
            pacientes = crearPacientesIniciales();
        }
        return pacientes;
    }

    private static List<Profesional> cargarProfesionales() {
        List<Profesional> profesionales = gestorProfesionalesTexto.leerProfesionales();
        if (profesionales.isEmpty()) {
            profesionales = crearProfesionalesIniciales();
        }
        return profesionales;
    }

    private static List<Agenda> cargarAgendas(List<Profesional> profesionales) {
        List<Agenda> agendas = gestorAgendasXML.leerAgendas(profesionales);
        if (agendas.isEmpty() && !profesionales.isEmpty()) {
            agendas = crearAgendasIniciales(profesionales);
        }
        return agendas;
    }

    private static List<Especialidad> cargarEspecialidades() {
        List<Especialidad> especialidades = gestorEspecialidadesTexto.leerEspecialidades();
        if (especialidades.isEmpty()) {
            especialidades = crearEspecialidadesIniciales();
            gestorEspecialidadesTexto.guardarEspecialidades(especialidades);
        }
        return especialidades;
    }

    private static List<Turno> cargarTurnos(List<Paciente> pacientes, List<Profesional> profesionales,
            List<Agenda> agendas) {
        return gestorTurnosXML.leerTurnos(pacientes, profesionales, agendas);
    }

    private static void agregarPacienteDesdeTeclado(VistaConsola vista) {
        List<Paciente> pacientes = cargarPacientes();
        vista.mostrarMensaje("\nCarga de paciente");
        String nombre = leerTextoObligatorio(vista, "Nombre: ", "El nombre no puede estar vacio.");
        if (nombre == null) {
            return;
        }
        String apellido = leerTextoObligatorio(vista, "Apellido: ", "El apellido no puede estar vacio.");
        if (apellido == null) {
            return;
        }
        String dni = leerTextoConFormato(vista, "DNI: ", "\\d{7,8}",
                "El DNI debe contener solo numeros y tener entre 7 y 8 digitos.");
        if (dni == null) {
            return;
        }
        String telefono = leerTextoConFormato(vista, "Telefono: ", "\\d{8,15}",
                "El telefono debe contener solo numeros y tener entre 8 y 15 digitos.");
        if (telefono == null) {
            return;
        }
        Integer historiaClinica = leerEnteroCancelable(vista, "Numero de historia clinica: ");
        if (historiaClinica == null) {
            cancelarOperacion(vista);
            return;
        }
        String obraSocial = leerTextoCancelable(vista, "Obra social: ");
        if (obraSocial == null) {
            return;
        }
        String email = leerEmailValido(vista, "Email: ");
        if (email == null) {
            return;
        }
        String fechaNacimiento = leerFechaValida(vista, "Fecha de nacimiento (dd/mm/aaaa): ");
        if (fechaNacimiento == null) {
            return;
        }
        String sexo = leerSexoPaciente(vista);
        if (sexo == null) {
            return;
        }

        if (buscarPacientePorDni(pacientes, dni) != null) {
            vista.mostrarMensaje("Ya existe un paciente con ese DNI.");
            return;
        }

        Paciente paciente = new Paciente(obtenerSiguienteIdPaciente(pacientes), nombre, apellido,
                dni, telefono, historiaClinica, obraSocial, email, fechaNacimiento, sexo);
        if (!paciente.validarDatos()) {
            vista.mostrarMensaje("Los datos del paciente no son validos.");
            return;
        }

        gestorPacientesTexto.agregarPaciente(paciente);
        vista.mostrarMensaje("Paciente agregado en pacientes.txt.");
    }

    private static void agregarProfesionalDesdeTeclado(VistaConsola vista) {
        List<Profesional> profesionales = cargarProfesionales();
        vista.mostrarMensaje("\nCarga de profesional");
        String nombre = leerTextoObligatorio(vista, "Nombre: ", "El nombre no puede estar vacio.");
        if (nombre == null) {
            return;
        }
        String apellido = leerTextoObligatorio(vista, "Apellido: ", "El apellido no puede estar vacio.");
        if (apellido == null) {
            return;
        }
        String dni = leerTextoConFormato(vista, "DNI: ", "\\d{7,8}",
                "El DNI debe contener solo numeros y tener entre 7 y 8 digitos.");
        if (dni == null) {
            return;
        }
        String telefono = leerTextoConFormato(vista, "Telefono: ", "\\d{8,15}",
                "El telefono debe contener solo numeros y tener entre 8 y 15 digitos.");
        if (telefono == null) {
            return;
        }
        String matricula = leerMatriculaProfesional(vista, "Matricula (numero, se guardara como MP): ");
        if (matricula == null) {
            return;
        }
        String especialidad = leerEspecialidadProfesional(vista, cargarEspecialidades());
        if (especialidad == null) {
            return;
        }
        String emailInstitucional = leerAliasEmailInstitucional(vista);
        if (emailInstitucional == null) {
            return;
        }

        if (buscarProfesionalPorDni(profesionales, dni) != null) {
            vista.mostrarMensaje("Ya existe un profesional con ese DNI.");
            return;
        }
        if (buscarProfesionalPorMatricula(profesionales, matricula) != null) {
            vista.mostrarMensaje("Ya existe un profesional con esa matricula.");
            return;
        }
        if (buscarProfesionalPorEmailInstitucional(profesionales, emailInstitucional) != null) {
            vista.mostrarMensaje("Ya existe un profesional con ese email institucional.");
            return;
        }

        Profesional profesional = new Profesional(obtenerSiguienteIdProfesional(profesionales),
                nombre, apellido, dni, telefono, matricula, especialidad, emailInstitucional);
        if (!profesional.validarDatos()) {
            vista.mostrarMensaje("Los datos del profesional no son validos.");
            return;
        }

        gestorProfesionalesTexto.agregarProfesional(profesional);
        vista.mostrarMensaje("Profesional agregado en profesionales.txt.");
    }

    private static void agregarAgendaDesdeTeclado(VistaConsola vista) {
        List<Profesional> profesionales = cargarProfesionales();
        if (profesionales.isEmpty()) {
            vista.mostrarMensaje("No hay profesionales cargados para asociar a la agenda.");
            return;
        }

        vista.mostrarMensaje("\nProfesionales disponibles:");
        for (Profesional profesional : profesionales) {
            vista.mostrarMensaje("ID " + profesional.getIdProfesional() + " - "
                    + profesional.getNombreCompleto() + " - " + profesional.getEspecialidad());
        }

        Integer idProfesional = leerEnteroCancelable(vista, "ID del profesional: ");
        if (idProfesional == null) {
            cancelarOperacion(vista);
            return;
        }
        Profesional profesionalSeleccionado = buscarProfesionalPorId(profesionales, idProfesional);
        if (profesionalSeleccionado == null) {
            vista.mostrarMensaje("No existe un profesional con ese ID.");
            return;
        }

        List<Agenda> agendas = cargarAgendas(profesionales);
        String diaSemana = leerDiaSemanaAgenda(vista);
        if (diaSemana == null) {
            return;
        }
        String horaInicio = leerHoraAgenda(vista,
                "Hora de inicio (09:00 a 17:45, cada 15 minutos): ", false);
        if (horaInicio == null) {
            return;
        }
        String horaFin = leerHoraAgenda(vista,
                "Hora de fin (09:15 a 18:00, cada 15 minutos): ", true);
        if (horaFin == null) {
            return;
        }
        String fechaDesde = leerFechaValida(vista, "Fecha desde de la agenda (dd/mm/aaaa): ");
        if (fechaDesde == null) {
            return;
        }
        String fechaHasta = leerFechaValida(vista, "Fecha hasta de la agenda (dd/mm/aaaa): ");
        if (fechaHasta == null) {
            return;
        }

        Agenda agenda = new Agenda(obtenerSiguienteIdAgenda(agendas), profesionalSeleccionado,
                diaSemana, horaInicio, horaFin, fechaDesde, fechaHasta, "Activa");
        if (!agenda.validarDisponibilidad(agendas)) {
            vista.mostrarMensaje("La agenda ingresada no es valida, no respeta la vigencia o se superpone con otra existente.");
            return;
        }

        agendas.add(agenda);
        gestorAgendasXML.guardarAgendas(agendas);
        vista.mostrarMensaje("Agenda agregada en agendas.xml.");
    }

    private static void buscarPaciente(VistaConsola vista) {
        String dni = leerTextoCancelable(vista, "\nDNI del paciente: ");
        if (dni == null) {
            return;
        }

        Paciente paciente = buscarPacientePorDni(cargarPacientes(), dni);
        if (paciente == null) {
            vista.mostrarMensaje("No se encontro un paciente con ese DNI.");
            return;
        }

        vista.mostrarMensaje(paciente.mostrarDatos());
    }

    private static void buscarProfesional(VistaConsola vista) {
        String dni = leerTextoCancelable(vista, "\nDNI del profesional: ");
        if (dni == null) {
            return;
        }

        Profesional profesional = buscarProfesionalPorDni(cargarProfesionales(), dni);
        if (profesional == null) {
            vista.mostrarMensaje("No se encontro un profesional con ese DNI.");
            return;
        }

        vista.mostrarMensaje(profesional.mostrarDatos());
    }

    private static void buscarAgenda(VistaConsola vista) {
        List<Profesional> profesionales = cargarProfesionales();
        List<Agenda> agendas = cargarAgendas(profesionales);
        String diaSemana = leerDiaSemanaAgenda(vista);
        if (diaSemana == null) {
            return;
        }
        String especialidad = leerEspecialidadProfesional(vista, cargarEspecialidades());
        if (especialidad == null) {
            return;
        }

        List<Agenda> agendasDisponibles = filtrarAgendasPorDiaYEspecialidad(agendas, diaSemana, especialidad);
        if (agendasDisponibles.isEmpty()) {
            vista.mostrarMensaje("No se encontraron agendas para esa especialidad en ese dia.");
            return;
        }

        vista.mostrarMensaje("\nAgendas disponibles:");
        for (Agenda agendaDisponible : agendasDisponibles) {
            Profesional profesional = agendaDisponible.getProfesional();
            vista.mostrarMensaje("Profesional: " + profesional.getNombreCompleto()
                    + " - Horario: " + agendaDisponible.getHoraInicio() + " a "
                    + agendaDisponible.getHoraFin() + " - Estado: " + agendaDisponible.getEstado());
        }
    }

    private static void asignarTurnoDesdeTeclado(VistaConsola vista) {
        List<Paciente> pacientes = cargarPacientes();
        List<Profesional> profesionales = cargarProfesionales();
        List<Agenda> agendas = cargarAgendas(profesionales);
        List<Turno> turnos = cargarTurnos(pacientes, profesionales, agendas);

        if (agendas.isEmpty()) {
            vista.mostrarMensaje("No hay agendas disponibles para asignar turnos.");
            return;
        }

        String dniPaciente = leerTextoCancelable(vista, "\nDNI del paciente: ");
        if (dniPaciente == null) {
            return;
        }
        Paciente paciente = buscarPacientePorDni(pacientes, dniPaciente);
        if (paciente == null) {
            vista.mostrarMensaje("No existe un paciente con ese DNI.");
            return;
        }

        String especialidad = leerEspecialidadParaPaciente(vista, paciente);
        if (especialidad == null) {
            return;
        }
        if (existeTurnoDeEspecialidadParaPaciente(turnos, paciente, especialidad)) {
            vista.mostrarMensaje("El paciente ya tiene un turno asignado para esa especialidad.");
            return;
        }

        List<Agenda> agendasPorEspecialidad = filtrarAgendasPorEspecialidad(agendas, especialidad);
        if (agendasPorEspecialidad.isEmpty()) {
            vista.mostrarMensaje("No hay agendas disponibles para esa especialidad.");
            return;
        }

        vista.mostrarMensaje("\nAgendas disponibles para " + especialidad + ":");
        for (Agenda agenda : agendasPorEspecialidad) {
            Profesional profesional = agenda.getProfesional();
            vista.mostrarMensaje("Agenda " + agenda.getIdAgenda() + " - Profesional: "
                    + profesional.getNombreCompleto() + " - " + agenda.getDiaSemana() + " de "
                    + agenda.getHoraInicio() + " a " + agenda.getHoraFin() + " - Vigencia hasta "
                    + agenda.getFechaHasta());
        }

        Integer idAgenda = leerEnteroCancelable(vista, "ID de la agenda: ");
        if (idAgenda == null) {
            cancelarOperacion(vista);
            return;
        }
        Agenda agenda = buscarAgendaPorId(agendasPorEspecialidad, idAgenda);
        if (agenda == null) {
            vista.mostrarMensaje("No existe una agenda con ese ID.");
            return;
        }

        String fecha = leerFechaTurnoSegunAgenda(vista, agenda);
        if (fecha == null) {
            return;
        }
        if (agenda.getProfesional().getEspecialidad().equalsIgnoreCase("Pediatria")
                && !paciente.esPediatricoEnFecha(fecha)) {
            vista.mostrarMensaje("El paciente no cumple con la edad requerida para turnos de Pediatria.");
            return;
        }
        if (agenda.getProfesional().getEspecialidad().equalsIgnoreCase("Ginecologia")
                && !paciente.esSexoFemenino()) {
            vista.mostrarMensaje("Los turnos de Ginecologia solo pueden asignarse a pacientes femeninos.");
            return;
        }
        String hora = leerHorarioDisponibleTurno(vista, agenda, turnos, fecha);
        if (hora == null) {
            return;
        }

        Turno turno = new Turno(obtenerSiguienteIdTurno(turnos), paciente, agenda.getProfesional(),
                agenda, fecha, hora, "Pendiente");
        if (!turno.asignarTurno()) {
            vista.mostrarMensaje("No fue posible asignar el turno con los datos ingresados.");
            return;
        }

        turnos.add(turno);
        gestorTurnosXML.guardarTurnos(turnos);
        vista.mostrarMensaje("Turno agregado en turnos.xml.");
        enviarNotificacionesTurno(vista, turno);
    }

    private static void buscarTurnosPorPaciente(VistaConsola vista) {
        List<Paciente> pacientes = cargarPacientes();
        List<Profesional> profesionales = cargarProfesionales();
        List<Agenda> agendas = cargarAgendas(profesionales);
        List<Turno> turnos = cargarTurnos(pacientes, profesionales, agendas);

        String dniPaciente = leerTextoCancelable(vista, "\nDNI del paciente: ");
        if (dniPaciente == null) {
            return;
        }

        Paciente paciente = buscarPacientePorDni(pacientes, dniPaciente);
        if (paciente == null) {
            vista.mostrarMensaje("No existe un paciente con ese DNI.");
            return;
        }

        List<Turno> turnosPaciente = new ArrayList<>();
        List<Turno> turnosCancelables = new ArrayList<>();
        for (Turno turno : turnos) {
            if (turno.getPaciente() != null
                    && turno.getPaciente().getIdPaciente() == paciente.getIdPaciente()) {
                turnosPaciente.add(turno);
                vista.mostrarMensaje(turno.toString());
                if (esTurnoCancelable(turno)) {
                    turnosCancelables.add(turno);
                }
            }
        }

        if (turnosPaciente.isEmpty()) {
            vista.mostrarMensaje("No se encontraron turnos para ese paciente.");
            return;
        }

        if (turnosCancelables.isEmpty()) {
            vista.mostrarMensaje("El paciente no tiene turnos futuros para cancelar.");
            return;
        }

        vista.mostrarMensaje("\nTurnos cancelables:");
        for (Turno turnoCancelable : turnosCancelables) {
            vista.mostrarMensaje(turnoCancelable.toString());
        }

        Integer idTurno = leerEnteroCancelable(vista, "ID del turno a cancelar: ");
        if (idTurno == null) {
            cancelarOperacion(vista);
            return;
        }

        Turno turnoSeleccionado = buscarTurnoPorId(turnosCancelables, idTurno);
        if (turnoSeleccionado == null) {
            vista.mostrarMensaje("No existe un turno cancelable con ese ID.");
            return;
        }

        turnoSeleccionado.anularTurno();
        gestorTurnosXML.guardarTurnos(turnos);
        vista.mostrarMensaje("Turno cancelado correctamente.");
        enviarNotificacionesAnulacion(vista, turnoSeleccionado);
    }

    private static void agregarEspecialidadDesdeTeclado(VistaConsola vista) {
        List<Especialidad> especialidades = cargarEspecialidades();
        String nombre = leerTextoObligatorio(vista, "Nombre de la especialidad: ",
                "La especialidad no puede estar vacia.");
        if (nombre == null) {
            return;
        }

        if (existeEspecialidad(especialidades, nombre)) {
            vista.mostrarMensaje("La especialidad ya existe.");
            return;
        }

        Especialidad especialidad = new Especialidad(obtenerSiguienteIdEspecialidad(especialidades), nombre);
        if (!especialidad.validarNombre()) {
            vista.mostrarMensaje("La especialidad ingresada no es valida.");
            return;
        }

        gestorEspecialidadesTexto.agregarEspecialidad(especialidad);
        vista.mostrarMensaje("Especialidad agregada en especialidades.txt.");
    }

    private static void enviarNotificacionesTurno(VistaConsola vista, Turno turno) {
        Notificacion notificacion = new Notificacion();
        notificacion.prepararConfirmacionTurno(turno);
        vista.mostrarMensaje(notificacion.enviarA(turno.getPaciente()));
        vista.mostrarMensaje(notificacion.enviarA(turno.getProfesional()));
    }

    private static void enviarNotificacionesAnulacion(VistaConsola vista, Turno turno) {
        Notificacion notificacion = new Notificacion();
        notificacion.prepararAnulacionTurno(turno);
        vista.mostrarMensaje(notificacion.enviarA(turno.getPaciente()));
        vista.mostrarMensaje(notificacion.enviarA(turno.getProfesional()));
    }

    private static Paciente buscarPacientePorDni(List<Paciente> pacientes, String dni) {
        return indexarPacientesPorDni(pacientes).get(normalizarClave(dni));
    }

    private static Profesional buscarProfesionalPorDni(List<Profesional> profesionales, String dni) {
        return indexarProfesionalesPorDni(profesionales).get(normalizarClave(dni));
    }

    private static Profesional buscarProfesionalPorId(List<Profesional> profesionales, int idProfesional) {
        return indexarProfesionalesPorId(profesionales).get(idProfesional);
    }

    private static Profesional buscarProfesionalPorMatricula(List<Profesional> profesionales,
            String matricula) {
        return indexarProfesionalesPorMatricula(profesionales).get(normalizarClave(matricula));
    }

    private static Profesional buscarProfesionalPorEmailInstitucional(
            List<Profesional> profesionales, String emailInstitucional) {
        return indexarProfesionalesPorEmail(profesionales).get(normalizarClave(emailInstitucional));
    }

    private static Agenda buscarAgendaPorId(List<Agenda> agendas, int idAgenda) {
        return indexarAgendasPorId(agendas).get(idAgenda);
    }

    private static Turno buscarTurnoPorId(List<Turno> turnos, int idTurno) {
        return indexarTurnosPorId(turnos).get(idTurno);
    }

    private static Agenda buscarAgendaPorProfesionalYDia(List<Agenda> agendas, String dniProfesional,
            String diaSemana) {
        for (Agenda agenda : agendas) {
            Profesional profesional = agenda.getProfesional();
            if (profesional != null
                    && profesional.getDni().equals(dniProfesional)
                    && agenda.getDiaSemana().equalsIgnoreCase(diaSemana)) {
                return agenda;
            }
        }
        return null;
    }

    private static List<Agenda> filtrarAgendasPorDiaYEspecialidad(List<Agenda> agendas, String diaSemana,
            String especialidad) {
        List<Agenda> agendasFiltradas = new ArrayList<>();
        for (Agenda agenda : agendas) {
            Profesional profesional = agenda.getProfesional();
            if (profesional != null
                    && agenda.getDiaSemana().equalsIgnoreCase(diaSemana)
                    && profesional.getEspecialidad().equalsIgnoreCase(especialidad)) {
                agendasFiltradas.add(agenda);
            }
        }
        return agendasFiltradas;
    }

    private static List<Agenda> filtrarAgendasPorEspecialidad(List<Agenda> agendas, String especialidad) {
        List<Agenda> agendasFiltradas = new ArrayList<>();
        for (Agenda agenda : agendas) {
            Profesional profesional = agenda.getProfesional();
            if (profesional != null
                    && profesional.getEspecialidad().equalsIgnoreCase(especialidad)
                    && agenda.estaActiva()) {
                agendasFiltradas.add(agenda);
            }
        }
        return agendasFiltradas;
    }

    private static List<Especialidad> filtrarEspecialidadesParaPaciente(
            List<Especialidad> especialidades, Paciente paciente) {
        List<Especialidad> especialidadesFiltradas = new ArrayList<>();
        for (Especialidad especialidad : especialidades) {
            String nombre = especialidad.getNombre();
            if (nombre.equalsIgnoreCase("Pediatria") && !paciente.esPediatricoActual()) {
                continue;
            }
            if (nombre.equalsIgnoreCase("Ginecologia") && !paciente.esSexoFemenino()) {
                continue;
            }
            especialidadesFiltradas.add(especialidad);
        }
        return especialidadesFiltradas;
    }

    private static Especialidad buscarEspecialidadPorNombre(List<Especialidad> especialidades,
            String nombre) {
        return indexarEspecialidadesPorNombre(especialidades).get(normalizarClave(nombre));
    }

    private static boolean existeTurnoEnHorario(List<Turno> turnos, int idAgenda, String fecha, String hora) {
        return obtenerHorariosOcupados(turnos, idAgenda, fecha).contains(hora);
    }

    private static boolean existeTurnoDeEspecialidadParaPaciente(List<Turno> turnos, Paciente paciente,
            String especialidad) {
        for (Turno turno : turnos) {
            if (turno.getPaciente() == null
                    || turno.getPaciente().getIdPaciente() != paciente.getIdPaciente()
                    || turno.getEstado().equalsIgnoreCase("Anulado")
                    || !esTurnoFuturoOVigente(turno)) {
                continue;
            }

            Profesional profesional = turno.getProfesional();
            if (profesional != null
                    && profesional.getEspecialidad().equalsIgnoreCase(especialidad)) {
                return true;
            }
        }
        return false;
    }

    private static boolean esTurnoCancelable(Turno turno) {
        return turno != null
                && !turno.getEstado().equalsIgnoreCase("Anulado")
                && esTurnoFuturoOVigente(turno);
    }

    private static boolean esTurnoFuturoOVigente(Turno turno) {
        LocalDate fechaTurno = parsearFecha(turno.getFecha());
        LocalTime horaTurno = parsearHora(turno.getHora());
        if (fechaTurno == null || horaTurno == null) {
            return false;
        }

        LocalDate hoy = LocalDate.now();
        if (fechaTurno.isAfter(hoy)) {
            return true;
        }

        return fechaTurno.equals(hoy) && horaTurno.isAfter(LocalTime.now());
    }

    private static List<String> obtenerHorariosDisponibles(Agenda agenda, List<Turno> turnos, String fecha) {
        List<String> horariosDisponibles = new ArrayList<>();
        Set<String> horariosOcupados = obtenerHorariosOcupados(turnos, agenda.getIdAgenda(), fecha);
        LocalTime horaActual = LocalTime.parse(agenda.getHoraInicio(), FORMATO_HORA);
        LocalTime horaFin = LocalTime.parse(agenda.getHoraFin(), FORMATO_HORA);

        while (horaActual.isBefore(horaFin)) {
            String hora = horaActual.format(FORMATO_HORA);
            if (!horariosOcupados.contains(hora)) {
                horariosDisponibles.add(hora);
            }
            horaActual = horaActual.plusMinutes(15);
        }

        return horariosDisponibles;
    }

    private static Map<String, Paciente> indexarPacientesPorDni(List<Paciente> pacientes) {
        Map<String, Paciente> indicePacientes = new HashMap<>();
        for (Paciente paciente : pacientes) {
            indicePacientes.put(normalizarClave(paciente.getDni()), paciente);
        }
        return indicePacientes;
    }

    private static Map<String, Profesional> indexarProfesionalesPorDni(List<Profesional> profesionales) {
        Map<String, Profesional> indiceProfesionales = new HashMap<>();
        for (Profesional profesional : profesionales) {
            indiceProfesionales.put(normalizarClave(profesional.getDni()), profesional);
        }
        return indiceProfesionales;
    }

    private static Map<Integer, Profesional> indexarProfesionalesPorId(List<Profesional> profesionales) {
        Map<Integer, Profesional> indiceProfesionales = new HashMap<>();
        for (Profesional profesional : profesionales) {
            indiceProfesionales.put(profesional.getIdProfesional(), profesional);
        }
        return indiceProfesionales;
    }

    private static Map<String, Profesional> indexarProfesionalesPorMatricula(
            List<Profesional> profesionales) {
        Map<String, Profesional> indiceProfesionales = new HashMap<>();
        for (Profesional profesional : profesionales) {
            indiceProfesionales.put(normalizarClave(profesional.getMatricula()), profesional);
        }
        return indiceProfesionales;
    }

    private static Map<String, Profesional> indexarProfesionalesPorEmail(List<Profesional> profesionales) {
        Map<String, Profesional> indiceProfesionales = new HashMap<>();
        for (Profesional profesional : profesionales) {
            indiceProfesionales.put(normalizarClave(profesional.getEmailInstitucional()), profesional);
        }
        return indiceProfesionales;
    }

    private static Map<Integer, Agenda> indexarAgendasPorId(List<Agenda> agendas) {
        Map<Integer, Agenda> indiceAgendas = new HashMap<>();
        for (Agenda agenda : agendas) {
            indiceAgendas.put(agenda.getIdAgenda(), agenda);
        }
        return indiceAgendas;
    }

    private static Map<Integer, Turno> indexarTurnosPorId(List<Turno> turnos) {
        Map<Integer, Turno> indiceTurnos = new HashMap<>();
        for (Turno turno : turnos) {
            indiceTurnos.put(turno.getIdTurno(), turno);
        }
        return indiceTurnos;
    }

    private static Map<String, Especialidad> indexarEspecialidadesPorNombre(
            List<Especialidad> especialidades) {
        Map<String, Especialidad> indiceEspecialidades = new HashMap<>();
        for (Especialidad especialidad : especialidades) {
            indiceEspecialidades.put(normalizarClave(especialidad.getNombre()), especialidad);
        }
        return indiceEspecialidades;
    }

    private static Set<String> obtenerHorariosOcupados(List<Turno> turnos, int idAgenda, String fecha) {
        Set<String> horariosOcupados = new HashSet<>();
        for (Turno turno : turnos) {
            if (turno.getAgenda() != null
                    && turno.getAgenda().getIdAgenda() == idAgenda
                    && turno.getFecha().equalsIgnoreCase(fecha)
                    && !turno.getEstado().equalsIgnoreCase("Anulado")) {
                horariosOcupados.add(turno.getHora());
            }
        }
        return horariosOcupados;
    }

    private static boolean existeEspecialidad(List<Especialidad> especialidades, String nombre) {
        Set<String> nombresEspecialidades = new HashSet<>();
        for (Especialidad especialidad : especialidades) {
            nombresEspecialidades.add(normalizarClave(especialidad.getNombre()));
        }
        return nombresEspecialidades.contains(normalizarClave(nombre));
    }

    private static String normalizarClave(String valor) {
        return valor == null ? "" : valor.trim().toLowerCase();
    }

    private static int obtenerSiguienteIdAgenda(List<Agenda> agendas) {
        int mayorId = 0;
        for (Agenda agenda : agendas) {
            if (agenda.getIdAgenda() > mayorId) {
                mayorId = agenda.getIdAgenda();
            }
        }
        return obtenerSiguienteId(mayorId);
    }

    private static int obtenerSiguienteIdPaciente(List<Paciente> pacientes) {
        int mayorId = 0;
        for (Paciente paciente : pacientes) {
            if (paciente.getIdPaciente() > mayorId) {
                mayorId = paciente.getIdPaciente();
            }
        }
        return obtenerSiguienteId(mayorId);
    }

    private static int obtenerSiguienteIdProfesional(List<Profesional> profesionales) {
        int mayorId = 0;
        for (Profesional profesional : profesionales) {
            if (profesional.getIdProfesional() > mayorId) {
                mayorId = profesional.getIdProfesional();
            }
        }
        return obtenerSiguienteId(mayorId);
    }

    private static int obtenerSiguienteIdTurno(List<Turno> turnos) {
        int mayorId = 0;
        for (Turno turno : turnos) {
            if (turno.getIdTurno() > mayorId) {
                mayorId = turno.getIdTurno();
            }
        }
        return obtenerSiguienteId(mayorId);
    }

    private static int obtenerSiguienteIdEspecialidad(List<Especialidad> especialidades) {
        int mayorId = 0;
        for (Especialidad especialidad : especialidades) {
            if (especialidad.getIdEspecialidad() > mayorId) {
                mayorId = especialidad.getIdEspecialidad();
            }
        }
        return obtenerSiguienteId(mayorId);
    }

    private static int obtenerSiguienteId(int mayorId) {
        return mayorId + 1;
    }

    private static String leerTextoCancelable(VistaConsola vista, String mensaje) {
        String valor = vista.leerTexto(mensaje);
        if (esCancelacion(valor)) {
            cancelarOperacion(vista);
            return null;
        }
        return valor;
    }

    private static String leerTextoObligatorio(VistaConsola vista, String mensaje,
            String mensajeError) {
        while (true) {
            String valor = leerTextoCancelable(vista, mensaje);
            if (valor == null) {
                return null;
            }
            if (!valor.trim().isEmpty()) {
                return valor;
            }
            vista.mostrarMensaje(mensajeError);
        }
    }

    private static String leerTextoConFormato(VistaConsola vista, String mensaje, String regex,
            String mensajeError) {
        while (true) {
            String valor = leerTextoCancelable(vista, mensaje);
            if (valor == null) {
                return null;
            }
            if (valor.matches(regex)) {
                return valor;
            }
            vista.mostrarMensaje(mensajeError);
        }
    }

    private static String leerEmailValido(VistaConsola vista, String mensaje) {
        while (true) {
            String valor = leerTextoCancelable(vista, mensaje);
            if (valor == null) {
                return null;
            }
            if (valor.contains("@") && valor.contains(".")) {
                return valor;
            }
            vista.mostrarMensaje("Ingrese un email valido.");
        }
    }

    private static String leerAliasEmailInstitucional(VistaConsola vista) {
        while (true) {
            String alias = leerTextoCancelable(vista,
                    "Alias de email institucional (sin dominio): ");
            if (alias == null) {
                return null;
            }
            if (alias.matches("[a-zA-Z0-9._%+-]+")) {
                return alias + Profesional.getDominioInstitucional();
            }
            vista.mostrarMensaje("El alias solo puede contener letras, numeros y . _ % + -");
        }
    }

    private static String leerMatriculaProfesional(VistaConsola vista, String mensaje) {
        while (true) {
            String valor = leerTextoCancelable(vista, mensaje);
            if (valor == null) {
                return null;
            }
            String soloNumero = valor.trim().toUpperCase();
            if (soloNumero.startsWith("MP")) {
                soloNumero = soloNumero.substring(2);
            }
            if (soloNumero.matches("\\d+")) {
                return "MP" + soloNumero;
            }
            vista.mostrarMensaje("La matricula debe ser numerica. Se guardara con el prefijo MP.");
        }
    }

    private static String leerEspecialidadProfesional(VistaConsola vista,
            List<Especialidad> especialidades) {
        while (true) {
            vista.mostrarMensaje("Especialidades disponibles:");
            for (int i = 0; i < especialidades.size(); i++) {
                vista.mostrarMensaje((i + 1) + ". " + especialidades.get(i).getNombre());
            }

            Integer opcion = leerEnteroCancelable(vista, "Seleccione una especialidad: ");
            if (opcion == null) {
                cancelarOperacion(vista);
                return null;
            }

            if (opcion >= 1 && opcion <= especialidades.size()) {
                return especialidades.get(opcion - 1).getNombre();
            }

            vista.mostrarMensaje("Seleccione una opcion valida de especialidad.");
        }
    }

    private static String leerEspecialidadParaPaciente(VistaConsola vista, Paciente paciente) {
        List<Especialidad> especialidades = filtrarEspecialidadesParaPaciente(
                cargarEspecialidades(), paciente);
        if (especialidades.isEmpty()) {
            vista.mostrarMensaje("No hay especialidades habilitadas para este paciente.");
            return null;
        }
        return leerEspecialidadProfesional(vista, especialidades);
    }

    private static String leerDiaSemanaAgenda(VistaConsola vista) {
        while (true) {
            vista.mostrarMensaje("Dias disponibles para agenda:");
            for (int i = 0; i < DIAS_SEMANA_DISPONIBLES.length; i++) {
                vista.mostrarMensaje((i + 1) + ". " + DIAS_SEMANA_DISPONIBLES[i]);
            }

            Integer opcion = leerEnteroCancelable(vista, "Seleccione un dia: ");
            if (opcion == null) {
                cancelarOperacion(vista);
                return null;
            }

            if (opcion >= 1 && opcion <= DIAS_SEMANA_DISPONIBLES.length) {
                return DIAS_SEMANA_DISPONIBLES[opcion - 1];
            }

            vista.mostrarMensaje("Seleccione una opcion valida de dia.");
        }
    }

    private static String leerSexoPaciente(VistaConsola vista) {
        while (true) {
            vista.mostrarMensaje("Sexos disponibles:");
            for (int i = 0; i < SEXOS_DISPONIBLES.length; i++) {
                vista.mostrarMensaje((i + 1) + ". " + SEXOS_DISPONIBLES[i]);
            }

            Integer opcion = leerEnteroCancelable(vista, "Seleccione el sexo del paciente: ");
            if (opcion == null) {
                cancelarOperacion(vista);
                return null;
            }

            if (opcion >= 1 && opcion <= SEXOS_DISPONIBLES.length) {
                return SEXOS_DISPONIBLES[opcion - 1];
            }

            vista.mostrarMensaje("Seleccione una opcion valida de sexo.");
        }
    }

    private static String leerHoraAgenda(VistaConsola vista, String mensaje, boolean permiteCierre) {
        while (true) {
            String hora = leerTextoCancelable(vista, mensaje);
            if (hora == null) {
                return null;
            }
            if (esHorarioEnCuartos(hora)
                    && hora.compareTo("09:00") >= 0
                    && ((permiteCierre && hora.compareTo("18:00") <= 0)
                    || (!permiteCierre && hora.compareTo("17:45") <= 0))) {
                return hora;
            }
            vista.mostrarMensaje(
                    "La hora debe tener formato hh:mm, caer en bloques de 15 minutos y respetar el horario comercial.");
        }
    }

    private static String leerHorarioDisponibleTurno(VistaConsola vista, Agenda agenda,
            List<Turno> turnos, String fecha) {
        List<String> horariosDisponibles = obtenerHorariosDisponibles(agenda, turnos, fecha);
        if (horariosDisponibles.isEmpty()) {
            vista.mostrarMensaje("No hay horarios disponibles para esa agenda en la fecha seleccionada.");
            return null;
        }

        while (true) {
            vista.mostrarMensaje("Horarios disponibles para " + fecha + ":");
            for (int i = 0; i < horariosDisponibles.size(); i++) {
                vista.mostrarMensaje((i + 1) + ". " + horariosDisponibles.get(i));
            }

            Integer opcion = leerEnteroCancelable(vista, "Seleccione un horario disponible: ");
            if (opcion == null) {
                cancelarOperacion(vista);
                return null;
            }

            if (opcion >= 1 && opcion <= horariosDisponibles.size()) {
                return horariosDisponibles.get(opcion - 1);
            }

            vista.mostrarMensaje("Seleccione una opcion valida de la lista.");
        }
    }

    private static String leerFechaValida(VistaConsola vista, String mensaje) {
        while (true) {
            String fecha = leerTextoCancelable(vista, mensaje);
            if (fecha == null) {
                return null;
            }
            if (fecha.matches("\\d{2}/\\d{2}/\\d{4}") && esFechaCalendarioValida(fecha)) {
                return fecha;
            }
            vista.mostrarMensaje("La fecha debe tener formato dd/mm/aaaa y ser una fecha valida.");
        }
    }

    private static String leerFechaTurnoSegunAgenda(VistaConsola vista, Agenda agenda) {
        while (true) {
            String fecha = leerFechaValida(vista, "Fecha del turno (dd/mm/aaaa): ");
            if (fecha == null) {
                return null;
            }
            if (!esFechaFuturaOVigente(fecha)) {
                vista.mostrarMensaje("La fecha del turno debe ser hoy o futura.");
                continue;
            }
            if (agenda.contieneFecha(fecha)) {
                return fecha;
            }
            vista.mostrarMensaje("La fecha debe coincidir con el dia "
                    + agenda.getDiaSemana() + " y estar dentro de la vigencia de la agenda ("
                    + agenda.getFechaDesde() + " a " + agenda.getFechaHasta() + ").");
        }
    }

    private static boolean esFechaCalendarioValida(String fecha) {
        try {
            return parsearFecha(fecha) != null;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean esFechaFuturaOVigente(String fecha) {
        LocalDate fechaTurno = parsearFecha(fecha);
        return fechaTurno != null && !fechaTurno.isBefore(LocalDate.now());
    }

    private static LocalDate parsearFecha(String fecha) {
        try {
            return LocalDate.parse(fecha, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static LocalTime parsearHora(String hora) {
        try {
            return LocalTime.parse(hora, FORMATO_HORA);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static boolean esHorarioEnCuartos(String hora) {
        if (!hora.matches("\\d{2}:\\d{2}")) {
            return false;
        }

        String minutos = hora.substring(3, 5);
        return minutos.equals("00") || minutos.equals("15")
                || minutos.equals("30") || minutos.equals("45");
    }

    private static Integer leerEnteroCancelable(VistaConsola vista, String mensaje) {
        while (true) {
            String valor = vista.leerTexto(mensaje);
            if (esCancelacion(valor)) {
                return null;
            }
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                vista.mostrarMensaje("Ingrese un numero valido o escriba \"" + COMANDO_CANCELAR + "\".");
            }
        }
    }

    private static int leerEntero(VistaConsola vista, String mensaje) {
        while (true) {
            String valor = vista.leerTexto(mensaje);
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                vista.mostrarMensaje("Ingrese un numero valido.");
            }
        }
    }

    private static boolean esCancelacion(String valor) {
        return valor != null && valor.equalsIgnoreCase(COMANDO_CANCELAR);
    }

    private static void cancelarOperacion(VistaConsola vista) {
        vista.mostrarMensaje("Operacion cancelada.");
    }
}
