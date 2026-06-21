package com.beloqui.controlador;

import com.beloqui.modelo.Agenda;
import com.beloqui.modelo.Especialidad;
import com.beloqui.modelo.Paciente;
import com.beloqui.modelo.Profesional;
import com.beloqui.modelo.Turno;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controlador de aplicacion que concentra las operaciones del sistema de turnos.
 */
public class SistemaTurnos {
    private static final String CARPETA_DATOS = "01_Proyecto/beloqui_gonzalo/04_datos/";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

    private final GestorPacientesTexto gestorPacientes =
            new GestorPacientesTexto(CARPETA_DATOS + "pacientes.txt");
    private final GestorProfesionalesTexto gestorProfesionales =
            new GestorProfesionalesTexto(CARPETA_DATOS + "profesionales.txt");
    private final GestorEspecialidadesTexto gestorEspecialidades =
            new GestorEspecialidadesTexto(CARPETA_DATOS + "especialidades.txt");
    private final GestorAgendasXML gestorAgendas =
            new GestorAgendasXML(CARPETA_DATOS + "agendas.xml");
    private final GestorTurnosXML gestorTurnos =
            new GestorTurnosXML(CARPETA_DATOS + "turnos.xml");

    private List<Paciente> pacientes;
    private List<Profesional> profesionales;
    private List<Especialidad> especialidades;
    private List<Agenda> agendas;
    private List<Turno> turnos;

    public SistemaTurnos() {
        recargarDatos();
    }

    public final void recargarDatos() {
        this.pacientes = gestorPacientes.leerPacientes();
        this.profesionales = gestorProfesionales.leerProfesionales();
        this.especialidades = gestorEspecialidades.leerEspecialidades();
        this.agendas = gestorAgendas.leerAgendas(this.profesionales);
        this.turnos = gestorTurnos.leerTurnos(this.pacientes, this.profesionales, this.agendas);

        if (this.especialidades.isEmpty()) {
            this.especialidades = crearEspecialidadesIniciales();
            gestorEspecialidades.guardarEspecialidades(this.especialidades);
        }
    }

    public void guardarDatos() {
        gestorPacientes.guardarPacientes(this.pacientes);
        gestorProfesionales.guardarProfesionales(this.profesionales);
        gestorEspecialidades.guardarEspecialidades(this.especialidades);
        gestorAgendas.guardarAgendas(this.agendas);
        gestorTurnos.guardarTurnos(this.turnos);
    }

    public List<Paciente> getPacientes() {
        return new ArrayList<>(this.pacientes);
    }

    public List<Profesional> getProfesionales() {
        return new ArrayList<>(this.profesionales);
    }

    public List<Especialidad> getEspecialidades() {
        return new ArrayList<>(this.especialidades);
    }

    public List<Agenda> getAgendas() {
        return new ArrayList<>(this.agendas);
    }

    public List<Turno> getTurnos() {
        return new ArrayList<>(this.turnos);
    }

    public Paciente agregarPaciente(
            String nombre,
            String apellido,
            String dni,
            String telefono,
            int historiaClinica,
            String obraSocial,
            String email,
            String fechaNacimiento,
            String sexo) throws OperacionInvalidaException {
        if (buscarPacientePorDni(dni) != null) {
            throw new OperacionInvalidaException("Ya existe un paciente con ese DNI.");
        }

        Paciente paciente = new Paciente(
                siguienteIdPaciente(),
                nombre,
                apellido,
                dni,
                telefono,
                historiaClinica,
                obraSocial,
                email,
                fechaNacimiento,
                sexo);
        if (!paciente.validarDatos()) {
            throw new OperacionInvalidaException(
                    "Los datos del paciente no son validos. Revise DNI, telefono, email, fecha y sexo.");
        }

        this.pacientes.add(paciente);
        gestorPacientes.guardarPacientes(this.pacientes);
        return paciente;
    }

    public Profesional agregarProfesional(
            String nombre,
            String apellido,
            String dni,
            String telefono,
            String matricula,
            String especialidad,
            String emailInstitucional) throws OperacionInvalidaException {
        if (buscarProfesionalPorDni(dni) != null) {
            throw new OperacionInvalidaException("Ya existe un profesional con ese DNI.");
        }
        if (buscarProfesionalPorMatricula(matricula) != null) {
            throw new OperacionInvalidaException("Ya existe un profesional con esa matricula.");
        }
        if (buscarProfesionalPorEmail(emailInstitucional) != null) {
            throw new OperacionInvalidaException("Ya existe un profesional con ese email institucional.");
        }
        if (buscarEspecialidadPorNombre(especialidad) == null) {
            throw new OperacionInvalidaException("La especialidad seleccionada no existe.");
        }

        Profesional profesional = new Profesional(
                siguienteIdProfesional(),
                nombre,
                apellido,
                dni,
                telefono,
                matricula,
                especialidad,
                emailInstitucional);
        if (!profesional.validarDatos()) {
            throw new OperacionInvalidaException(
                    "Los datos del profesional no son validos. La matricula debe ser numerica y el email debe usar @centrosalud.com.");
        }

        this.profesionales.add(profesional);
        gestorProfesionales.guardarProfesionales(this.profesionales);
        return profesional;
    }

    public Especialidad agregarEspecialidad(String nombre) throws OperacionInvalidaException {
        String valor = nombre == null ? "" : nombre.trim();
        if (valor.isEmpty()) {
            throw new OperacionInvalidaException("Ingrese el nombre de la especialidad.");
        }
        if (buscarEspecialidadPorNombre(valor) != null) {
            throw new OperacionInvalidaException("La especialidad ya existe.");
        }

        Especialidad especialidad = new Especialidad(siguienteIdEspecialidad(), valor);
        this.especialidades.add(especialidad);
        gestorEspecialidades.guardarEspecialidades(this.especialidades);
        return especialidad;
    }

    public Agenda agregarAgenda(
            int idProfesional,
            String diaSemana,
            String horaInicio,
            String horaFin,
            String fechaDesde,
            String fechaHasta) throws OperacionInvalidaException {
        Profesional profesional = buscarProfesionalPorId(idProfesional);
        if (profesional == null) {
            throw new OperacionInvalidaException("Seleccione un profesional valido.");
        }

        Agenda agenda = new Agenda(
                siguienteIdAgenda(),
                profesional,
                diaSemana,
                horaInicio,
                horaFin,
                fechaDesde,
                fechaHasta,
                "Activa");
        if (!agenda.validarDisponibilidad(this.agendas)) {
            throw new OperacionInvalidaException(
                    "La agenda no es valida, no respeta la vigencia, el horario o se superpone con otra.");
        }

        this.agendas.add(agenda);
        gestorAgendas.guardarAgendas(this.agendas);
        return agenda;
    }

    public Turno asignarTurno(int idPaciente, int idAgenda, String fecha, String hora)
            throws OperacionInvalidaException {
        Paciente paciente = buscarPacientePorId(idPaciente);
        Agenda agenda = buscarAgendaPorId(idAgenda);
        if (paciente == null) {
            throw new OperacionInvalidaException("Seleccione un paciente valido.");
        }
        if (agenda == null || agenda.getProfesional() == null) {
            throw new OperacionInvalidaException("Seleccione una agenda valida.");
        }
        if (!agenda.estaActiva()) {
            throw new OperacionInvalidaException("La agenda seleccionada no esta activa.");
        }
        if (!esFechaFuturaOVigente(fecha)) {
            throw new OperacionInvalidaException("La fecha del turno debe ser hoy o futura.");
        }
        if (!agenda.contieneFecha(fecha)) {
            throw new OperacionInvalidaException(
                    "La fecha no coincide con el dia o la vigencia de la agenda.");
        }
        if (!agenda.contieneHorario(hora)) {
            throw new OperacionInvalidaException("El horario no pertenece a la agenda.");
        }
        if (existeTurnoEnHorario(idAgenda, fecha, hora)) {
            throw new OperacionInvalidaException("El horario seleccionado ya esta ocupado.");
        }
        if (existeTurnoDeEspecialidadParaPaciente(paciente, agenda.getProfesional().getEspecialidad())) {
            throw new OperacionInvalidaException(
                    "El paciente ya tiene un turno vigente para esa especialidad.");
        }

        Turno turno = new Turno(
                siguienteIdTurno(),
                paciente,
                agenda.getProfesional(),
                agenda,
                fecha,
                hora,
                "Asignado");
        if (!turno.puedeAsignarse()) {
            throw new OperacionInvalidaException(
                    "El turno no cumple las reglas de la especialidad, la agenda o los datos del paciente.");
        }

        this.turnos.add(turno);
        gestorTurnos.guardarTurnos(this.turnos);
        return turno;
    }

    public void anularTurno(int idTurno) throws OperacionInvalidaException {
        Turno turno = buscarTurnoPorId(idTurno);
        if (turno == null) {
            throw new OperacionInvalidaException("Seleccione un turno valido.");
        }
        if (!esTurnoCancelable(turno)) {
            throw new OperacionInvalidaException("El turno ya esta anulado o no se puede cancelar.");
        }

        turno.anularTurno();
        gestorTurnos.guardarTurnos(this.turnos);
    }

    public Paciente buscarPacientePorDni(String dni) {
        for (Paciente paciente : this.pacientes) {
            if (paciente.getDni().equalsIgnoreCase(normalizar(dni))) {
                return paciente;
            }
        }
        return null;
    }

    public Profesional buscarProfesionalPorDni(String dni) {
        for (Profesional profesional : this.profesionales) {
            if (profesional.getDni().equalsIgnoreCase(normalizar(dni))) {
                return profesional;
            }
        }
        return null;
    }

    public Profesional buscarProfesionalPorId(int idProfesional) {
        for (Profesional profesional : this.profesionales) {
            if (profesional.getIdProfesional() == idProfesional) {
                return profesional;
            }
        }
        return null;
    }

    public Agenda buscarAgendaPorId(int idAgenda) {
        for (Agenda agenda : this.agendas) {
            if (agenda.getIdAgenda() == idAgenda) {
                return agenda;
            }
        }
        return null;
    }

    public List<Agenda> buscarAgendas(String diaSemana, String especialidad) {
        List<Agenda> resultado = new ArrayList<>();
        for (Agenda agenda : this.agendas) {
            Profesional profesional = agenda.getProfesional();
            boolean coincideDia = diaSemana == null
                    || diaSemana.trim().isEmpty()
                    || agenda.getDiaSemana().equalsIgnoreCase(diaSemana);
            boolean coincideEspecialidad = especialidad == null
                    || especialidad.trim().isEmpty()
                    || (profesional != null
                    && profesional.getEspecialidad().equalsIgnoreCase(especialidad));
            if (coincideDia && coincideEspecialidad) {
                resultado.add(agenda);
            }
        }
        return resultado;
    }

    public List<String> obtenerEspecialidadesPermitidas(int idPaciente) {
        List<String> resultado = new ArrayList<>();
        Paciente paciente = buscarPacientePorId(idPaciente);
        if (paciente == null) {
            return resultado;
        }

        for (Especialidad especialidad : this.especialidades) {
            String nombre = especialidad.getNombre();
            if (esEspecialidadPermitidaParaPaciente(paciente, nombre)
                    && !existeTurnoDeEspecialidadParaPaciente(paciente, nombre)) {
                resultado.add(nombre);
            }
        }
        return resultado;
    }

    public List<Profesional> obtenerProfesionalesParaTurno(int idPaciente, String especialidad) {
        List<Profesional> resultado = new ArrayList<>();
        Paciente paciente = buscarPacientePorId(idPaciente);
        if (paciente == null || !esEspecialidadPermitidaParaPaciente(paciente, especialidad)) {
            return resultado;
        }

        for (Profesional profesional : this.profesionales) {
            if (profesional.getEspecialidad().equalsIgnoreCase(normalizar(especialidad))
                    && tieneAgendaActiva(profesional.getIdProfesional())) {
                resultado.add(profesional);
            }
        }
        return resultado;
    }

    public List<Agenda> obtenerAgendasParaTurno(int idProfesional, String especialidad) {
        List<Agenda> resultado = new ArrayList<>();
        for (Agenda agenda : this.agendas) {
            Profesional profesional = agenda.getProfesional();
            if (profesional != null
                    && profesional.getIdProfesional() == idProfesional
                    && profesional.getEspecialidad().equalsIgnoreCase(normalizar(especialidad))
                    && agenda.estaActiva()
                    && tieneFechasDisponibles(agenda)) {
                resultado.add(agenda);
            }
        }
        return resultado;
    }

    public List<String> obtenerFechasDisponibles(int idAgenda) {
        List<String> resultado = new ArrayList<>();
        Agenda agenda = buscarAgendaPorId(idAgenda);
        if (agenda == null || !agenda.estaActiva()) {
            return resultado;
        }

        LocalDate desde = parsearFecha(agenda.getFechaDesde());
        LocalDate hasta = parsearFecha(agenda.getFechaHasta());
        if (desde == null || hasta == null) {
            return resultado;
        }

        LocalDate fecha = desde.isBefore(LocalDate.now()) ? LocalDate.now() : desde;
        while (!fecha.isAfter(hasta)) {
            String textoFecha = fecha.format(FORMATO_FECHA);
            if (agenda.contieneFecha(textoFecha)
                    && !obtenerHorariosDisponibles(idAgenda, textoFecha).isEmpty()) {
                resultado.add(textoFecha);
            }
            fecha = fecha.plusDays(1);
        }
        return resultado;
    }

    public List<Turno> buscarTurnosPorPaciente(String dni) {
        List<Turno> resultado = new ArrayList<>();
        Paciente paciente = buscarPacientePorDni(dni);
        if (paciente == null) {
            return resultado;
        }
        for (Turno turno : this.turnos) {
            if (turno.getPaciente() != null
                    && turno.getPaciente().getIdPaciente() == paciente.getIdPaciente()) {
                resultado.add(turno);
            }
        }
        return resultado;
    }

    public List<String> obtenerHorariosDisponibles(int idAgenda, String fecha) {
        List<String> horarios = new ArrayList<>();
        Agenda agenda = buscarAgendaPorId(idAgenda);
        if (agenda == null || !agenda.contieneFecha(fecha)) {
            return horarios;
        }

        Set<String> ocupados = obtenerHorariosOcupados(idAgenda, fecha);
        LocalTime actual = LocalTime.parse(agenda.getHoraInicio(), FORMATO_HORA);
        LocalTime fin = LocalTime.parse(agenda.getHoraFin(), FORMATO_HORA);
        while (actual.isBefore(fin)) {
            String hora = actual.format(FORMATO_HORA);
            if (!ocupados.contains(hora)) {
                horarios.add(hora);
            }
            actual = actual.plusMinutes(15);
        }
        return horarios;
    }

    private boolean tieneAgendaActiva(int idProfesional) {
        for (Agenda agenda : this.agendas) {
            Profesional profesional = agenda.getProfesional();
            if (profesional != null
                    && profesional.getIdProfesional() == idProfesional
                    && agenda.estaActiva()
                    && tieneFechasDisponibles(agenda)) {
                return true;
            }
        }
        return false;
    }

    private boolean tieneFechasDisponibles(Agenda agenda) {
        return agenda != null && !obtenerFechasDisponibles(agenda.getIdAgenda()).isEmpty();
    }

    private boolean esEspecialidadPermitidaParaPaciente(Paciente paciente, String especialidad) {
        if (paciente == null) {
            return false;
        }
        String nombre = normalizar(especialidad);
        if (nombre.equalsIgnoreCase("Pediatria")) {
            return paciente.esPediatricoActual();
        }
        if (nombre.equalsIgnoreCase("Ginecologia")) {
            return paciente.esSexoFemenino();
        }
        return !nombre.isEmpty();
    }

    private Profesional buscarProfesionalPorMatricula(String matricula) {
        String valor = normalizar(matricula);
        if (!valor.toUpperCase().startsWith("MP")) {
            valor = "MP" + valor;
        }
        for (Profesional profesional : this.profesionales) {
            if (profesional.getMatricula().equalsIgnoreCase(valor)) {
                return profesional;
            }
        }
        return null;
    }

    private Profesional buscarProfesionalPorEmail(String email) {
        for (Profesional profesional : this.profesionales) {
            if (profesional.getEmailInstitucional().equalsIgnoreCase(normalizar(email))) {
                return profesional;
            }
        }
        return null;
    }

    private Especialidad buscarEspecialidadPorNombre(String nombre) {
        for (Especialidad especialidad : this.especialidades) {
            if (especialidad.getNombre().equalsIgnoreCase(normalizar(nombre))) {
                return especialidad;
            }
        }
        return null;
    }

    private Paciente buscarPacientePorId(int idPaciente) {
        for (Paciente paciente : this.pacientes) {
            if (paciente.getIdPaciente() == idPaciente) {
                return paciente;
            }
        }
        return null;
    }

    private Turno buscarTurnoPorId(int idTurno) {
        for (Turno turno : this.turnos) {
            if (turno.getIdTurno() == idTurno) {
                return turno;
            }
        }
        return null;
    }

    private boolean existeTurnoEnHorario(int idAgenda, String fecha, String hora) {
        return obtenerHorariosOcupados(idAgenda, fecha).contains(hora);
    }

    private Set<String> obtenerHorariosOcupados(int idAgenda, String fecha) {
        Set<String> horarios = new HashSet<>();
        for (Turno turno : this.turnos) {
            if (turno.getAgenda() != null
                    && turno.getAgenda().getIdAgenda() == idAgenda
                    && turno.getFecha().equalsIgnoreCase(fecha)
                    && !turno.getEstado().equalsIgnoreCase("Anulado")) {
                horarios.add(turno.getHora());
            }
        }
        return horarios;
    }

    private boolean existeTurnoDeEspecialidadParaPaciente(Paciente paciente, String especialidad) {
        for (Turno turno : this.turnos) {
            if (turno.getPaciente() != null
                    && turno.getPaciente().getIdPaciente() == paciente.getIdPaciente()
                    && turno.getProfesional() != null
                    && turno.getProfesional().getEspecialidad().equalsIgnoreCase(especialidad)
                    && !turno.getEstado().equalsIgnoreCase("Anulado")
                    && esTurnoFuturoOVigente(turno)) {
                return true;
            }
        }
        return false;
    }

    private boolean esTurnoCancelable(Turno turno) {
        return turno != null
                && !turno.getEstado().equalsIgnoreCase("Anulado")
                && esTurnoFuturoOVigente(turno);
    }

    private boolean esTurnoFuturoOVigente(Turno turno) {
        LocalDate fecha = parsearFecha(turno.getFecha());
        LocalTime hora = parsearHora(turno.getHora());
        if (fecha == null || hora == null) {
            return false;
        }
        return fecha.isAfter(LocalDate.now())
                || (fecha.equals(LocalDate.now()) && hora.isAfter(LocalTime.now()));
    }

    private boolean esFechaFuturaOVigente(String fecha) {
        LocalDate valor = parsearFecha(fecha);
        return valor != null && !valor.isBefore(LocalDate.now());
    }

    private LocalDate parsearFecha(String fecha) {
        try {
            return LocalDate.parse(normalizar(fecha), FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private LocalTime parsearHora(String hora) {
        try {
            return LocalTime.parse(normalizar(hora), FORMATO_HORA);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private int siguienteIdPaciente() {
        int mayor = 0;
        for (Paciente paciente : this.pacientes) {
            mayor = Math.max(mayor, paciente.getIdPaciente());
        }
        return mayor + 1;
    }

    private int siguienteIdProfesional() {
        int mayor = 0;
        for (Profesional profesional : this.profesionales) {
            mayor = Math.max(mayor, profesional.getIdProfesional());
        }
        return mayor + 1;
    }

    private int siguienteIdEspecialidad() {
        int mayor = 0;
        for (Especialidad especialidad : this.especialidades) {
            mayor = Math.max(mayor, especialidad.getIdEspecialidad());
        }
        return mayor + 1;
    }

    private int siguienteIdAgenda() {
        int mayor = 0;
        for (Agenda agenda : this.agendas) {
            mayor = Math.max(mayor, agenda.getIdAgenda());
        }
        return mayor + 1;
    }

    private int siguienteIdTurno() {
        int mayor = 0;
        for (Turno turno : this.turnos) {
            mayor = Math.max(mayor, turno.getIdTurno());
        }
        return mayor + 1;
    }

    private String normalizar(String valor) {
        return valor == null ? "" : valor.trim();
    }

    private List<Especialidad> crearEspecialidadesIniciales() {
        List<Especialidad> resultado = new ArrayList<>();
        resultado.add(new Especialidad("Clinica medica"));
        resultado.add(new Especialidad("Pediatria"));
        resultado.add(new Especialidad("Cardiologia"));
        resultado.add(new Especialidad("Ginecologia"));
        resultado.add(new Especialidad("Gastroenterologia"));
        return resultado;
    }
}
