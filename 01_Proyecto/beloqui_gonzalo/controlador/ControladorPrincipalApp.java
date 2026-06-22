package com.beloqui.controlador;

import com.beloqui.modelo.Agenda;
import com.beloqui.modelo.Especialidad;
import com.beloqui.modelo.Paciente;
import com.beloqui.modelo.Profesional;
import com.beloqui.modelo.Turno;
import com.beloqui.vista.PrincipalApp;
import com.beloqui.vista.PrincipalApp.ElementoCombo;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ControladorPrincipalApp {
    private final PrincipalApp vista;
    private final SistemaTurnos sistema = new SistemaTurnos();
    private Paciente turnoPacienteActual;
    private final JTextField pacienteNombre;
    private final JTextField pacienteApellido;
    private final JTextField pacienteDni;
    private final JTextField pacienteTelefono;
    private final JTextField pacienteHistoria;
    private final JTextField pacienteObraSocial;
    private final JTextField pacienteEmail;
    private final JTextField pacienteNacimiento;
    private final JRadioButton pacienteFemenino;
    private final JRadioButton pacienteMasculino;
    private final ButtonGroup grupoSexoPaciente;
    private final JTextField pacienteBuscarDni;
    private final DefaultTableModel modeloPacientes;
    private final JTable tablaPacientes;
    private final JTextField profesionalNombre;
    private final JTextField profesionalApellido;
    private final JTextField profesionalDni;
    private final JTextField profesionalTelefono;
    private final JTextField profesionalMatricula;
    private final JTextField profesionalEmail;
    private final JComboBox<String> profesionalEspecialidad;
    private final JTextField profesionalBuscarDni;
    private final DefaultTableModel modeloProfesionales;
    private final JTable tablaProfesionales;
    private final JTextField especialidadNombre;
    private final DefaultTableModel modeloEspecialidades;
    private final JTable tablaEspecialidades;
    private final JComboBox<ElementoCombo<Profesional>> agendaProfesional;
    private final JComboBox<String> agendaDia;
    private final JComboBox<String> agendaHoraInicio;
    private final JComboBox<String> agendaHoraFin;
    private final JTextField agendaFechaDesde;
    private final JTextField agendaFechaHasta;
    private final JComboBox<String> agendaBuscarDia;
    private final JComboBox<String> agendaBuscarEspecialidad;
    private final JCheckBox agendaSoloActivas;
    private final DefaultTableModel modeloAgendas;
    private final JTable tablaAgendas;
    private final JTextField turnoPacienteDniAsignacion;
    private final JLabel turnoPacienteSeleccionado;
    private final JComboBox<String> turnoEspecialidad;
    private final JComboBox<ElementoCombo<Profesional>> turnoProfesional;
    private final JComboBox<ElementoCombo<Agenda>> turnoAgenda;
    private final JComboBox<String> turnoFecha;
    private final JList<String> turnoHorarios;
    private final JTextField turnoBuscarDni;
    private final DefaultTableModel modeloTurnos;
    private final JTable tablaTurnos;
    private final JLabel estado;

    public ControladorPrincipalApp(PrincipalApp vista) {
        this.vista = vista;
        this.pacienteNombre = vista.pacienteNombre;
        this.pacienteApellido = vista.pacienteApellido;
        this.pacienteDni = vista.pacienteDni;
        this.pacienteTelefono = vista.pacienteTelefono;
        this.pacienteHistoria = vista.pacienteHistoria;
        this.pacienteObraSocial = vista.pacienteObraSocial;
        this.pacienteEmail = vista.pacienteEmail;
        this.pacienteNacimiento = vista.pacienteNacimiento;
        this.pacienteFemenino = vista.pacienteFemenino;
        this.pacienteMasculino = vista.pacienteMasculino;
        this.grupoSexoPaciente = vista.grupoSexoPaciente;
        this.pacienteBuscarDni = vista.pacienteBuscarDni;
        this.modeloPacientes = vista.modeloPacientes;
        this.tablaPacientes = vista.tablaPacientes;
        this.profesionalNombre = vista.profesionalNombre;
        this.profesionalApellido = vista.profesionalApellido;
        this.profesionalDni = vista.profesionalDni;
        this.profesionalTelefono = vista.profesionalTelefono;
        this.profesionalMatricula = vista.profesionalMatricula;
        this.profesionalEmail = vista.profesionalEmail;
        this.profesionalEspecialidad = vista.profesionalEspecialidad;
        this.profesionalBuscarDni = vista.profesionalBuscarDni;
        this.modeloProfesionales = vista.modeloProfesionales;
        this.tablaProfesionales = vista.tablaProfesionales;
        this.especialidadNombre = vista.especialidadNombre;
        this.modeloEspecialidades = vista.modeloEspecialidades;
        this.tablaEspecialidades = vista.tablaEspecialidades;
        this.agendaProfesional = vista.agendaProfesional;
        this.agendaDia = vista.agendaDia;
        this.agendaHoraInicio = vista.agendaHoraInicio;
        this.agendaHoraFin = vista.agendaHoraFin;
        this.agendaFechaDesde = vista.agendaFechaDesde;
        this.agendaFechaHasta = vista.agendaFechaHasta;
        this.agendaBuscarDia = vista.agendaBuscarDia;
        this.agendaBuscarEspecialidad = vista.agendaBuscarEspecialidad;
        this.agendaSoloActivas = vista.agendaSoloActivas;
        this.modeloAgendas = vista.modeloAgendas;
        this.tablaAgendas = vista.tablaAgendas;
        this.turnoPacienteDniAsignacion = vista.turnoPacienteDniAsignacion;
        this.turnoPacienteSeleccionado = vista.turnoPacienteSeleccionado;
        this.turnoEspecialidad = vista.turnoEspecialidad;
        this.turnoProfesional = vista.turnoProfesional;
        this.turnoAgenda = vista.turnoAgenda;
        this.turnoFecha = vista.turnoFecha;
        this.turnoHorarios = vista.turnoHorarios;
        this.turnoBuscarDni = vista.turnoBuscarDni;
        this.modeloTurnos = vista.modeloTurnos;
        this.tablaTurnos = vista.tablaTurnos;
        this.estado = vista.estado;
    }

    public void registrarRecargar(AbstractButton boton) {
        boton.addActionListener(evento -> recargarDatos());
    }

    public void registrarGuardar(AbstractButton boton) {
        boton.addActionListener(evento -> guardarDatos());
    }

    public void registrarSalir(AbstractButton boton) {
        boton.addActionListener(evento -> salirConControlDeFormularios());
    }

    public void registrarMostrarPanel(AbstractButton boton, int indice) {
        boton.addActionListener(evento -> mostrarPanel(indice));
    }

    public void registrarAltaPaciente(AbstractButton boton) {
        boton.addActionListener(evento -> registrarPaciente());
    }

    public void registrarLimpiarPaciente(AbstractButton boton) {
        boton.addActionListener(evento -> limpiarPaciente());
    }

    public void registrarBuscarPaciente(AbstractButton boton) {
        boton.addActionListener(evento -> buscarPaciente());
    }

    public void registrarMostrarTodosPacientes(AbstractButton boton) {
        boton.addActionListener(evento -> mostrarTodosPacientes());
    }

    public void registrarAltaProfesional(AbstractButton boton) {
        boton.addActionListener(evento -> registrarProfesional());
    }

    public void registrarLimpiarProfesional(AbstractButton boton) {
        boton.addActionListener(evento -> limpiarProfesional());
    }

    public void registrarBuscarProfesional(AbstractButton boton) {
        boton.addActionListener(evento -> buscarProfesional());
    }

    public void registrarMostrarTodosProfesionales(AbstractButton boton) {
        boton.addActionListener(evento -> mostrarTodosProfesionales());
    }

    public void registrarAltaEspecialidad(AbstractButton boton) {
        boton.addActionListener(evento -> registrarEspecialidad());
    }

    public void registrarAltaAgenda(AbstractButton boton) {
        boton.addActionListener(evento -> registrarAgenda());
    }

    public void registrarLimpiarAgenda(AbstractButton boton) {
        boton.addActionListener(evento -> limpiarAgenda());
    }

    public void registrarBuscarAgendas(AbstractButton boton) {
        boton.addActionListener(evento -> buscarAgendas());
    }

    public void registrarBuscarPacienteParaTurno(AbstractButton boton) {
        boton.addActionListener(evento -> buscarPacienteParaTurno());
    }

    public void registrarCambioEspecialidadTurno(JComboBox<?> combo) {
        combo.addActionListener(evento -> cargarProfesionalesParaTurno());
    }

    public void registrarCambioProfesionalTurno(JComboBox<?> combo) {
        combo.addActionListener(evento -> cargarAgendasParaTurno());
    }

    public void registrarCambioAgendaTurno(JComboBox<?> combo) {
        combo.addActionListener(evento -> cargarFechasParaTurno());
    }

    public void registrarCambioFechaTurno(JComboBox<?> combo) {
        combo.addActionListener(evento -> cargarHorariosParaTurno());
    }

    public void registrarConsultarHorarios(AbstractButton boton) {
        boton.addActionListener(evento -> cargarHorariosParaTurno());
    }

    public void registrarAsignarTurno(AbstractButton boton) {
        boton.addActionListener(evento -> asignarTurno());
    }

    public void registrarBuscarTurnos(AbstractButton boton) {
        boton.addActionListener(evento -> buscarTurnos());
    }

    public void registrarAnularTurno(AbstractButton boton) {
        boton.addActionListener(evento -> anularTurno());
    }

    public void registrarCierreVentana() {
        vista.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evento) {
                salirConControlDeFormularios();
            }
        });
    }

    public void mostrarPanel(int indice) {
        vista.mostrarPanel(indice);
    }

    public void mostrarTodosPacientes() {
        actualizarTablaPacientes(sistema.getPacientes());
    }

    public void mostrarTodosProfesionales() {
        actualizarTablaProfesionales(sistema.getProfesionales());
    }

    public void registrarPaciente() {
        try {
            int historia = Integer.parseInt(pacienteHistoria.getText().trim());
            Paciente paciente = sistema.agregarPaciente(
                    pacienteNombre.getText(),
                    pacienteApellido.getText(),
                    pacienteDni.getText(),
                    pacienteTelefono.getText(),
                    historia,
                    pacienteObraSocial.getText(),
                    pacienteEmail.getText(),
                    pacienteNacimiento.getText(),
                    obtenerSexoPaciente());
            mostrarExito("Paciente registrado: " + paciente.getNombreCompleto());
            limpiarPaciente();
            recargarInterfaz();
        } catch (NumberFormatException e) {
            mostrarError("La historia clínica debe ser un número entero.");
        } catch (OperacionInvalidaException e) {
            mostrarError(e.getMessage());
        }
    }

    public void buscarPaciente() {
        Paciente paciente = sistema.buscarPacientePorDni(pacienteBuscarDni.getText());
        if (paciente == null) {
            mostrarError("No se encontró un paciente con ese DNI.");
            return;
        }
        actualizarTablaPacientes(java.util.Arrays.asList(paciente));
        informar("Paciente encontrado.");
    }

    public void registrarProfesional() {
        try {
            Profesional profesional = sistema.agregarProfesional(
                    profesionalNombre.getText(),
                    profesionalApellido.getText(),
                    profesionalDni.getText(),
                    profesionalTelefono.getText(),
                    profesionalMatricula.getText(),
                    (String) profesionalEspecialidad.getSelectedItem(),
                    profesionalEmail.getText());
            mostrarExito("Profesional registrado: " + profesional.getNombreCompleto());
            limpiarProfesional();
            recargarInterfaz();
        } catch (OperacionInvalidaException e) {
            mostrarError(e.getMessage());
        }
    }

    public void buscarProfesional() {
        Profesional profesional = sistema.buscarProfesionalPorDni(profesionalBuscarDni.getText());
        if (profesional == null) {
            mostrarError("No se encontró un profesional con ese DNI.");
            return;
        }
        actualizarTablaProfesionales(java.util.Arrays.asList(profesional));
        informar("Profesional encontrado.");
    }

    public void registrarEspecialidad() {
        try {
            Especialidad especialidad = sistema.agregarEspecialidad(especialidadNombre.getText());
            mostrarExito("Especialidad registrada: " + especialidad.getNombre());
            especialidadNombre.setText("");
            recargarInterfaz();
        } catch (OperacionInvalidaException e) {
            mostrarError(e.getMessage());
        }
    }

    public void registrarAgenda() {
        ElementoCombo<Profesional> opcion = obtenerSeleccion(agendaProfesional);
        if (opcion == null) {
            mostrarError("Seleccione un profesional.");
            return;
        }
        try {
            Agenda agenda = sistema.agregarAgenda(
                    opcion.getValor().getIdProfesional(),
                    (String) agendaDia.getSelectedItem(),
                    (String) agendaHoraInicio.getSelectedItem(),
                    (String) agendaHoraFin.getSelectedItem(),
                    agendaFechaDesde.getText(),
                    agendaFechaHasta.getText());
            mostrarExito("Agenda registrada con ID " + agenda.getIdAgenda() + ".");
            limpiarAgenda();
            recargarInterfaz();
        } catch (OperacionInvalidaException e) {
            mostrarError(e.getMessage());
        }
    }

    public void buscarAgendas() {
        String dia = (String) agendaBuscarDia.getSelectedItem();
        String especialidad = (String) agendaBuscarEspecialidad.getSelectedItem();
        List<Agenda> agendas = sistema.buscarAgendas(
                "Todos".equals(dia) ? "" : dia,
                "Todas".equals(especialidad) ? "" : especialidad);
        actualizarTablaAgendas(agendas);
        informar("Consulta de agendas actualizada.");
    }

    public void buscarPacienteParaTurno() {
        Paciente paciente = sistema.buscarPacientePorDni(turnoPacienteDniAsignacion.getText());
        if (paciente == null) {
            limpiarAsignacionTurno();
            mostrarError("No se encontro un paciente con ese DNI.");
            return;
        }

        this.turnoPacienteActual = paciente;
        mostrarPacienteTurno(paciente);
        turnoBuscarDni.setText(paciente.getDni());
        cargarEspecialidadesParaTurno();
        buscarTurnos();
        informar("Paciente seleccionado. Elija una especialidad habilitada.");
    }

    private void cargarEspecialidadesParaTurno() {
        turnoEspecialidad.removeAllItems();
        limpiarProfesionalesAgendasFechasYHorarios();
        if (turnoPacienteActual == null) {
            return;
        }

        List<String> especialidades =
                sistema.obtenerEspecialidadesPermitidas(turnoPacienteActual.getIdPaciente());
        for (String especialidad : especialidades) {
            turnoEspecialidad.addItem(especialidad);
        }
        if (especialidades.isEmpty()) {
            informar("El paciente no tiene especialidades disponibles según sus restricciones o turnos vigentes.");
        }
    }

    public void cargarProfesionalesParaTurno() {
        turnoProfesional.removeAllItems();
        limpiarAgendasFechasYHorarios();
        if (turnoPacienteActual == null || turnoEspecialidad.getSelectedItem() == null) {
            return;
        }

        String especialidad = turnoEspecialidad.getSelectedItem().toString();
        List<Profesional> profesionales = sistema.obtenerProfesionalesParaTurno(
                turnoPacienteActual.getIdPaciente(), especialidad);
        for (Profesional profesional : profesionales) {
            turnoProfesional.addItem(new ElementoCombo<>(
                    profesional,
                    profesional.getNombreCompleto() + " - " + profesional.getMatricula()));
        }
        if (profesionales.isEmpty()) {
            informar("No hay profesionales con agenda disponible para esa especialidad.");
        }
    }

    public void cargarAgendasParaTurno() {
        turnoAgenda.removeAllItems();
        limpiarFechasYHorarios();
        ElementoCombo<Profesional> profesional = obtenerSeleccion(turnoProfesional);
        if (profesional == null || turnoEspecialidad.getSelectedItem() == null) {
            return;
        }

        String especialidad = turnoEspecialidad.getSelectedItem().toString();
        List<Agenda> agendas = sistema.obtenerAgendasParaTurno(
                profesional.getValor().getIdProfesional(), especialidad);
        for (Agenda agenda : agendas) {
            turnoAgenda.addItem(new ElementoCombo<>(
                    agenda,
                    agenda.getDiaSemana() + " "
                            + agenda.getHoraInicio() + " a " + agenda.getHoraFin()
                            + " - Vigencia " + agenda.getFechaDesde()
                            + " a " + agenda.getFechaHasta()));
        }
        if (agendas.isEmpty()) {
            informar("El profesional no tiene agendas activas con fechas disponibles.");
        }
    }

    public void cargarFechasParaTurno() {
        turnoFecha.removeAllItems();
        turnoHorarios.setListData(new String[0]);
        ElementoCombo<Agenda> agenda = obtenerSeleccion(turnoAgenda);
        if (agenda == null) {
            return;
        }

        List<String> fechas = sistema.obtenerFechasDisponibles(agenda.getValor().getIdAgenda());
        for (String fecha : fechas) {
            turnoFecha.addItem(fecha);
        }
        if (fechas.isEmpty()) {
            informar("No hay fechas disponibles para la agenda seleccionada.");
        }
    }

    public void cargarHorariosParaTurno() {
        turnoHorarios.setListData(new String[0]);
        ElementoCombo<Agenda> agenda = obtenerSeleccion(turnoAgenda);
        Object fecha = turnoFecha.getSelectedItem();
        if (agenda == null || fecha == null) {
            return;
        }

        List<String> horarios = sistema.obtenerHorariosDisponibles(
                agenda.getValor().getIdAgenda(), fecha.toString());
        turnoHorarios.setListData(horarios.toArray(new String[0]));
        if (horarios.isEmpty()) {
            informar("No hay horarios disponibles para la fecha seleccionada.");
        } else {
            informar("Seleccione un horario y confirme la asignación.");
        }
    }

    public void asignarTurno() {
        ElementoCombo<Agenda> agenda = obtenerSeleccion(turnoAgenda);
        Object fecha = turnoFecha.getSelectedItem();
        String hora = turnoHorarios.getSelectedValue();
        if (turnoPacienteActual == null || agenda == null || fecha == null || hora == null) {
            mostrarError("Seleccione paciente, especialidad, profesional, agenda, fecha y horario.");
            return;
        }
        try {
            Turno turno = sistema.asignarTurno(
                    turnoPacienteActual.getIdPaciente(),
                    agenda.getValor().getIdAgenda(),
                    fecha.toString(),
                    hora);
            mostrarExito("Turno asignado con ID " + turno.getIdTurno() + ".");
            turnoBuscarDni.setText(turnoPacienteActual.getDni());
            recargarInterfaz();
            buscarTurnos();
        } catch (OperacionInvalidaException e) {
            mostrarError(e.getMessage());
        }
    }

    public void buscarTurnos() {
        List<Turno> turnos = sistema.buscarTurnosPorPaciente(turnoBuscarDni.getText());
        actualizarTablaTurnos(turnos);
        if (turnos.isEmpty()) {
            mostrarError("No se encontraron turnos para ese DNI.");
        } else {
            informar("Turnos del paciente cargados.");
        }
    }

    public void anularTurno() {
        int fila = tablaTurnos.getSelectedRow();
        if (fila < 0) {
            mostrarError("Seleccione un turno de la tabla.");
            return;
        }
        int idTurno = Integer.parseInt(tablaTurnos.getValueAt(fila, 0).toString());
        int respuesta = JOptionPane.showConfirmDialog(
                vista,
                "¿Desea anular el turno " + idTurno + "?",
                "Confirmar anulación",
                JOptionPane.YES_NO_OPTION);
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            sistema.anularTurno(idTurno);
            mostrarExito("Turno anulado.");
            recargarInterfaz();
            buscarTurnos();
        } catch (OperacionInvalidaException e) {
            mostrarError(e.getMessage());
        }
    }

    public void recargarDatos() {
        if (hayFormulariosConDatos() && !confirmarDescarteFormularios("recargar los datos desde archivos")) {
            return;
        }
        sistema.recargarDatos();
        recargarInterfaz();
        informar("Datos recargados desde los archivos.");
    }

    public void guardarDatos() {
        sistema.guardarDatos();
        informar("Datos guardados en los archivos del proyecto.");
        JOptionPane.showMessageDialog(
                vista,
                "Los datos actuales fueron guardados correctamente.",
                "Guardado de datos",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void recargarInterfaz() {
        actualizarEspecialidades();
        actualizarCombosEntidades();
        actualizarTablaPacientes(sistema.getPacientes());
        actualizarTablaProfesionales(sistema.getProfesionales());
        actualizarTablaEspecialidades(sistema.getEspecialidades());
        actualizarTablaAgendas(sistema.getAgendas());
        actualizarTablaTurnos(sistema.getTurnos());
    }

    private void actualizarEspecialidades() {
        String seleccionProfesional = (String) profesionalEspecialidad.getSelectedItem();
        String seleccionFiltro = (String) agendaBuscarEspecialidad.getSelectedItem();
        profesionalEspecialidad.removeAllItems();
        agendaBuscarEspecialidad.removeAllItems();
        agendaBuscarEspecialidad.addItem("Todas");
        for (Especialidad especialidad : sistema.getEspecialidades()) {
            profesionalEspecialidad.addItem(especialidad.getNombre());
            agendaBuscarEspecialidad.addItem(especialidad.getNombre());
        }
        profesionalEspecialidad.setSelectedItem(seleccionProfesional);
        agendaBuscarEspecialidad.setSelectedItem(seleccionFiltro == null ? "Todas" : seleccionFiltro);

        String seleccionDia = (String) agendaBuscarDia.getSelectedItem();
        agendaBuscarDia.removeAllItems();
        agendaBuscarDia.addItem("Todos");
        for (String dia : PrincipalApp.DIAS) {
            agendaBuscarDia.addItem(dia);
        }
        agendaBuscarDia.setSelectedItem(seleccionDia == null ? "Todos" : seleccionDia);
    }

    private void actualizarCombosEntidades() {
        agendaProfesional.removeAllItems();
        for (Profesional profesional : sistema.getProfesionales()) {
            agendaProfesional.addItem(new ElementoCombo<>(
                    profesional,
                    profesional.getNombreCompleto() + " - " + profesional.getEspecialidad()));
        }

        if (turnoPacienteActual != null) {
            Paciente pacienteActualizado = sistema.buscarPacientePorDni(turnoPacienteActual.getDni());
            if (pacienteActualizado == null) {
                limpiarAsignacionTurno();
            } else {
                turnoPacienteActual = pacienteActualizado;
                mostrarPacienteTurno(pacienteActualizado);
                cargarEspecialidadesParaTurno();
            }
        }
    }

    private void actualizarTablaPacientes(List<Paciente> pacientes) {
        limpiarModelo(modeloPacientes);
        for (Paciente paciente : pacientes) {
            modeloPacientes.addRow(new Object[] {
                paciente.getIdPaciente(),
                paciente.getNombreCompleto(),
                paciente.getDni(),
                paciente.getTelefono(),
                paciente.getNumeroHistoriaClinica(),
                paciente.getObraSocial()
            });
        }
    }

    private void actualizarTablaProfesionales(List<Profesional> profesionales) {
        limpiarModelo(modeloProfesionales);
        for (Profesional profesional : profesionales) {
            modeloProfesionales.addRow(new Object[] {
                profesional.getIdProfesional(),
                profesional.getNombreCompleto(),
                profesional.getDni(),
                profesional.getMatricula(),
                profesional.getEspecialidad(),
                profesional.getEmailInstitucional()
            });
        }
    }

    private void actualizarTablaEspecialidades(List<Especialidad> especialidades) {
        limpiarModelo(modeloEspecialidades);
        for (Especialidad especialidad : especialidades) {
            modeloEspecialidades.addRow(new Object[] {
                especialidad.getIdEspecialidad(), especialidad.getNombre()
            });
        }
    }

    private void actualizarTablaAgendas(List<Agenda> agendas) {
        limpiarModelo(modeloAgendas);
        for (Agenda agenda : agendas) {
            if (agendaSoloActivas.isSelected() && !agenda.estaActiva()) {
                continue;
            }
            Profesional profesional = agenda.getProfesional();
            modeloAgendas.addRow(new Object[] {
                agenda.getIdAgenda(),
                profesional == null ? "" : profesional.getNombreCompleto(),
                profesional == null ? "" : profesional.getEspecialidad(),
                agenda.getDiaSemana(),
                agenda.getHoraInicio() + " a " + agenda.getHoraFin(),
                agenda.getFechaDesde() + " a " + agenda.getFechaHasta(),
                agenda.getEstado()
            });
        }
    }

    private void actualizarTablaTurnos(List<Turno> turnos) {
        limpiarModelo(modeloTurnos);
        for (Turno turno : turnos) {
            modeloTurnos.addRow(new Object[] {
                turno.getIdTurno(),
                turno.getPaciente() == null ? "" : turno.getPaciente().getNombreCompleto(),
                turno.getProfesional() == null ? "" : turno.getProfesional().getNombreCompleto(),
                turno.getProfesional() == null ? "" : turno.getProfesional().getEspecialidad(),
                turno.getFecha(),
                turno.getHora(),
                turno.getEstado()
            });
        }
    }

    private void limpiarPaciente() {
        pacienteNombre.setText("");
        pacienteApellido.setText("");
        pacienteDni.setText("");
        pacienteTelefono.setText("");
        pacienteHistoria.setText("");
        pacienteObraSocial.setText("");
        pacienteEmail.setText("");
        pacienteNacimiento.setText("");
        grupoSexoPaciente.clearSelection();
    }

    private void limpiarProfesional() {
        profesionalNombre.setText("");
        profesionalApellido.setText("");
        profesionalDni.setText("");
        profesionalTelefono.setText("");
        profesionalMatricula.setText("");
        profesionalEmail.setText("");
    }

    private void limpiarAgenda() {
        agendaFechaDesde.setText("");
        agendaFechaHasta.setText("");
        agendaDia.setSelectedIndex(0);
        agendaHoraInicio.setSelectedIndex(0);
        agendaHoraFin.setSelectedIndex(0);
    }

    private void limpiarAsignacionTurno() {
        turnoPacienteActual = null;
        turnoPacienteSeleccionado.setText("Sin paciente seleccionado");
        turnoEspecialidad.removeAllItems();
        limpiarProfesionalesAgendasFechasYHorarios();
    }

    private void limpiarProfesionalesAgendasFechasYHorarios() {
        turnoProfesional.removeAllItems();
        limpiarAgendasFechasYHorarios();
    }

    private void limpiarAgendasFechasYHorarios() {
        turnoAgenda.removeAllItems();
        limpiarFechasYHorarios();
    }

    private void limpiarFechasYHorarios() {
        turnoFecha.removeAllItems();
        turnoHorarios.setListData(new String[0]);
    }

    private void mostrarPacienteTurno(Paciente paciente) {
        turnoPacienteSeleccionado.setText(
                paciente.getNombreCompleto() + " - DNI " + paciente.getDni()
                        + " - " + paciente.getSexo()
                        + " - Nac. " + paciente.getFechaNacimiento());
    }

    private String obtenerSexoPaciente() {
        if (pacienteFemenino.isSelected()) {
            return "Femenino";
        }
        if (pacienteMasculino.isSelected()) {
            return "Masculino";
        }
        return "";
    }

    public void salirConControlDeFormularios() {
        if (!hayFormulariosConDatos()) {
            confirmarSalida();
            return;
        }

        if (confirmarDescarteFormularios("salir del sistema")) {
            vista.dispose();
        }
    }

    private void confirmarSalida() {
        int respuesta = JOptionPane.showConfirmDialog(
                vista,
                "¿Desea salir del sistema?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            vista.dispose();
        }
    }

    private boolean confirmarDescarteFormularios(String accion) {
        int respuesta = JOptionPane.showConfirmDialog(
                vista,
                "Hay datos cargados en formularios que todavía no fueron registrados. "
                        + "Si continúa, se perderán al " + accion + ". ¿Desea continuar?",
                "Formularios sin registrar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }

    private boolean hayFormulariosConDatos() {
        return hayTexto(pacienteNombre, pacienteApellido, pacienteDni, pacienteTelefono,
                pacienteHistoria, pacienteObraSocial, pacienteEmail, pacienteNacimiento)
                || pacienteFemenino.isSelected()
                || pacienteMasculino.isSelected()
                || hayTexto(profesionalNombre, profesionalApellido, profesionalDni,
                profesionalTelefono, profesionalMatricula, profesionalEmail)
                || hayTexto(especialidadNombre, agendaFechaDesde, agendaFechaHasta);
    }

    private boolean hayTexto(JTextField... campos) {
        for (JTextField campo : campos) {
            if (campo != null && !campo.getText().trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void mostrarExito(String mensaje) {
        informar(mensaje);
        JOptionPane.showMessageDialog(vista, mensaje, "Operación completada", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        informar("Revise la operación.");
        JOptionPane.showMessageDialog(vista, mensaje, "Operación no válida", JOptionPane.ERROR_MESSAGE);
    }

    private void informar(String mensaje) {
        estado.setText(mensaje);
    }


    private static void limpiarModelo(DefaultTableModel modelo) {
        modelo.setRowCount(0);
    }

    @SuppressWarnings("unchecked")
    private static <T> ElementoCombo<T> obtenerSeleccion(JComboBox<ElementoCombo<T>> combo) {
        return (ElementoCombo<T>) combo.getSelectedItem();
    }
}
