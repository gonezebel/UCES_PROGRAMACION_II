package com.beloqui.vista;

import com.beloqui.controlador.OperacionInvalidaException;
import com.beloqui.controlador.SistemaTurnos;
import com.beloqui.modelo.Agenda;
import com.beloqui.modelo.Especialidad;
import com.beloqui.modelo.Paciente;
import com.beloqui.modelo.Profesional;
import com.beloqui.modelo.Turno;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 * Aplicación interna para que los empleados administren el sistema de turnos.
 */
public class PrincipalApp extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Color COLOR_PRINCIPAL = new Color(32, 89, 138);
    private static final Color COLOR_FONDO = new Color(245, 248, 250);
    private static final String[] DIAS = {
        "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
    };

    private final SistemaTurnos sistema = new SistemaTurnos();
    private final JPanel panelContenido = new JPanel(new BorderLayout());
    private final JLabel estado = new JLabel("Sistema listo.");
    private JPanel panelPacientes;
    private JPanel panelProfesionales;
    private JPanel panelEspecialidades;
    private JPanel panelAgendas;
    private JPanel panelTurnos;

    private final JTextField pacienteNombre = new JTextField(18);
    private final JTextField pacienteApellido = new JTextField(18);
    private final JTextField pacienteDni = new JTextField(12);
    private final JTextField pacienteTelefono = new JTextField(14);
    private final JTextField pacienteHistoria = new JTextField(10);
    private final JTextField pacienteObraSocial = new JTextField(18);
    private final JTextField pacienteEmail = new JTextField(22);
    private final JTextField pacienteNacimiento = new JTextField(12);
    private final JRadioButton pacienteFemenino = new JRadioButton("Femenino");
    private final JRadioButton pacienteMasculino = new JRadioButton("Masculino");
    private final ButtonGroup grupoSexoPaciente = new ButtonGroup();
    private final JTextField pacienteBuscarDni = new JTextField(12);
    private final DefaultTableModel modeloPacientes =
            crearModelo("ID", "Nombre", "DNI", "Teléfono", "Historia clínica", "Obra social");
    private final JTable tablaPacientes = new JTable(modeloPacientes);

    private final JTextField profesionalNombre = new JTextField(18);
    private final JTextField profesionalApellido = new JTextField(18);
    private final JTextField profesionalDni = new JTextField(12);
    private final JTextField profesionalTelefono = new JTextField(14);
    private final JTextField profesionalMatricula = new JTextField(12);
    private final JTextField profesionalEmail = new JTextField(22);
    private final JComboBox<String> profesionalEspecialidad = new JComboBox<>();
    private final JTextField profesionalBuscarDni = new JTextField(12);
    private final DefaultTableModel modeloProfesionales =
            crearModelo("ID", "Nombre", "DNI", "Matrícula", "Especialidad", "Email");
    private final JTable tablaProfesionales = new JTable(modeloProfesionales);

    private final JTextField especialidadNombre = new JTextField(22);
    private final DefaultTableModel modeloEspecialidades = crearModelo("ID", "Especialidad");
    private final JTable tablaEspecialidades = new JTable(modeloEspecialidades);

    private final JComboBox<ElementoCombo<Profesional>> agendaProfesional = new JComboBox<>();
    private final JComboBox<String> agendaDia = new JComboBox<>(DIAS);
    private final JComboBox<String> agendaHoraInicio = new JComboBox<>(crearHorarios("09:00", "17:45"));
    private final JComboBox<String> agendaHoraFin = new JComboBox<>(crearHorarios("09:15", "18:00"));
    private final JTextField agendaFechaDesde = new JTextField(12);
    private final JTextField agendaFechaHasta = new JTextField(12);
    private final JComboBox<String> agendaBuscarDia = new JComboBox<>();
    private final JComboBox<String> agendaBuscarEspecialidad = new JComboBox<>();
    private final JCheckBox agendaSoloActivas = new JCheckBox("Mostrar solo agendas activas", true);
    private final DefaultTableModel modeloAgendas =
            crearModelo("ID", "Profesional", "Especialidad", "Día", "Horario", "Vigencia", "Estado");
    private final JTable tablaAgendas = new JTable(modeloAgendas);

    private final JTextField turnoPacienteDniAsignacion = new JTextField(12);
    private final JLabel turnoPacienteSeleccionado = new JLabel("Sin paciente seleccionado");
    private final JComboBox<String> turnoEspecialidad = new JComboBox<>();
    private final JComboBox<ElementoCombo<Profesional>> turnoProfesional = new JComboBox<>();
    private final JComboBox<ElementoCombo<Agenda>> turnoAgenda = new JComboBox<>();
    private final JComboBox<String> turnoFecha = new JComboBox<>();
    private final JList<String> turnoHorarios = new JList<>();
    private final JTextField turnoBuscarDni = new JTextField(12);
    private final DefaultTableModel modeloTurnos =
            crearModelo("ID", "Paciente", "Profesional", "Especialidad", "Fecha", "Hora", "Estado");
    private final JTable tablaTurnos = new JTable(modeloTurnos);
    private Paciente turnoPacienteActual;

    public PrincipalApp() {
        configurarVentana();
        agregarComponentes();
        recargarInterfaz();
    }

    private void configurarVentana() {
        setTitle("Centro de Salud - Sistema interno de turnos");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(980, 650));
        setSize(1120, 760);
        setLocationRelativeTo(null);
        setIconImage(IconoHospital.crearImagen(64));
        getContentPane().setBackground(COLOR_FONDO);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evento) {
                salirConControlDeFormularios();
            }
        });
    }

    private void agregarComponentes() {
        setJMenuBar(crearBarraMenu());
        add(crearBarraHerramientas(), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.setBackground(COLOR_FONDO);
        centro.setBorder(BorderFactory.createEmptyBorder(12, 14, 8, 14));
        centro.add(crearEncabezado(), BorderLayout.NORTH);

        panelPacientes = crearPestanaPacientes();
        panelProfesionales = crearPestanaProfesionales();
        panelEspecialidades = crearPestanaEspecialidades();
        panelAgendas = crearPestanaAgendas();
        panelTurnos = crearPestanaTurnos();
        panelContenido.setBackground(COLOR_FONDO);
        mostrarPanel(panelPacientes);
        centro.add(panelContenido, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        JPanel pie = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pie.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        pie.add(estado);
        add(pie, BorderLayout.SOUTH);
    }

    private JMenuBar crearBarraMenu() {
        JMenuBar barra = new JMenuBar();
        JMenu archivo = new JMenu("Archivo");
        archivo.setMnemonic(KeyEvent.VK_A);

        JMenuItem recargar = new JMenuItem("Recargar datos");
        recargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
        recargar.addActionListener(evento -> recargarDatos());
        archivo.add(recargar);

        JMenuItem guardar = new JMenuItem("Guardar datos");
        guardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK));
        guardar.addActionListener(evento -> guardarDatos());
        archivo.add(guardar);
        archivo.addSeparator();

        JMenuItem salir = new JMenuItem("Salir");
        salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
        salir.addActionListener(evento -> salirConControlDeFormularios());
        archivo.add(salir);

        JMenu navegar = new JMenu("Navegar");
        navegar.setMnemonic(KeyEvent.VK_N);
        agregarOpcionPestana(navegar, "Pacientes", 0);
        agregarOpcionPestana(navegar, "Profesionales", 1);
        agregarOpcionPestana(navegar, "Especialidades", 2);
        agregarOpcionPestana(navegar, "Agendas", 3);
        agregarOpcionPestana(navegar, "Turnos", 4);

        barra.add(archivo);
        barra.add(navegar);
        return barra;
    }

    private void agregarOpcionPestana(JMenu menu, String nombre, int indice) {
        JMenuItem opcion = new JMenuItem(nombre);
        opcion.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_1 + indice, KeyEvent.CTRL_DOWN_MASK));
        opcion.addActionListener(evento -> mostrarPanel(indice));
        menu.add(opcion);
    }

    private JToolBar crearBarraHerramientas() {
        JToolBar barra = new JToolBar();
        barra.setFloatable(false);
        barra.add(crearBotonPestana("Pacientes", 0));
        barra.add(crearBotonPestana("Profesionales", 1));
        barra.add(crearBotonPestana("Especialidades", 2));
        barra.add(crearBotonPestana("Agendas", 3));
        barra.add(crearBotonPestana("Turnos", 4));
        barra.addSeparator();
        JButton recargar = new JButton("Recargar");
        recargar.addActionListener(evento -> recargarDatos());
        barra.add(recargar);
        JButton guardar = new JButton("Guardar");
        guardar.addActionListener(evento -> guardarDatos());
        barra.add(guardar);
        return barra;
    }

    private JButton crearBotonPestana(String texto, int indice) {
        JButton boton = new JButton(texto);
        boton.addActionListener(evento -> mostrarPanel(indice));
        return boton;
    }

    private void mostrarPanel(int indice) {
        switch (indice) {
            case 0:
                mostrarPanel(panelPacientes);
                break;
            case 1:
                mostrarPanel(panelProfesionales);
                break;
            case 2:
                mostrarPanel(panelEspecialidades);
                break;
            case 3:
                mostrarPanel(panelAgendas);
                break;
            case 4:
                mostrarPanel(panelTurnos);
                break;
            default:
                mostrarPanel(panelPacientes);
        }
    }

    private void mostrarPanel(JPanel panel) {
        panelContenido.removeAll();
        panelContenido.add(panel, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    private JPanel crearEncabezado() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_PRINCIPAL);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        JLabel titulo = new JLabel("Sistema interno de gestión de turnos");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 22f));
        JLabel subtitulo = new JLabel(
                "Administración de pacientes, profesionales, especialidades, agendas y turnos.");
        subtitulo.setForeground(new Color(225, 237, 247));

        panel.add(titulo);
        panel.add(Box.createVerticalStrut(4));
        panel.add(subtitulo);
        return panel;
    }

    private JPanel crearPestanaPacientes() {
        JPanel panel = crearPanelPestana();
        JPanel formulario = crearPanelFormulario("Alta de paciente");
        GridBagConstraints c = restricciones();
        agregarFila(formulario, c, 0, "Nombre:", pacienteNombre);
        agregarFila(formulario, c, 1, "Apellido:", pacienteApellido);
        agregarFila(formulario, c, 2, "DNI:", pacienteDni);
        agregarFila(formulario, c, 3, "Teléfono:", pacienteTelefono);
        agregarFila(formulario, c, 4, "Historia clínica:", pacienteHistoria);
        agregarFila(formulario, c, 5, "Obra social:", pacienteObraSocial);
        agregarFila(formulario, c, 6, "Email:", pacienteEmail);
        agregarFila(formulario, c, 7, "Fecha nacimiento:", pacienteNacimiento);

        grupoSexoPaciente.add(pacienteFemenino);
        grupoSexoPaciente.add(pacienteMasculino);
        JPanel sexo = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        sexo.setOpaque(false);
        sexo.add(pacienteFemenino);
        sexo.add(pacienteMasculino);
        agregarFila(formulario, c, 8, "Sexo:", sexo);

        JButton guardar = new JButton("Registrar paciente");
        guardar.addActionListener(evento -> registrarPaciente());
        JButton limpiar = new JButton("Limpiar");
        limpiar.addActionListener(evento -> limpiarPaciente());
        agregarBotones(formulario, c, 9, guardar, limpiar);

        JPanel listado = crearPanelListado("Pacientes registrados");
        JPanel busqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        busqueda.add(new JLabel("Buscar por DNI:"));
        busqueda.add(pacienteBuscarDni);
        JButton buscar = new JButton("Buscar");
        buscar.addActionListener(evento -> buscarPaciente());
        busqueda.add(buscar);
        JButton todos = new JButton("Mostrar todos");
        todos.addActionListener(evento -> actualizarTablaPacientes(sistema.getPacientes()));
        busqueda.add(todos);
        listado.add(busqueda, BorderLayout.NORTH);
        listado.add(new JScrollPane(tablaPacientes), BorderLayout.CENTER);

        panel.add(formulario, BorderLayout.WEST);
        panel.add(listado, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPestanaProfesionales() {
        JPanel panel = crearPanelPestana();
        JPanel formulario = crearPanelFormulario("Alta de profesional");
        GridBagConstraints c = restricciones();
        agregarFila(formulario, c, 0, "Nombre:", profesionalNombre);
        agregarFila(formulario, c, 1, "Apellido:", profesionalApellido);
        agregarFila(formulario, c, 2, "DNI:", profesionalDni);
        agregarFila(formulario, c, 3, "Teléfono:", profesionalTelefono);
        agregarFila(formulario, c, 4, "Matrícula:", profesionalMatricula);
        agregarFila(formulario, c, 5, "Especialidad:", profesionalEspecialidad);
        agregarFila(formulario, c, 6, "Email institucional:", profesionalEmail);

        JButton guardar = new JButton("Registrar profesional");
        guardar.addActionListener(evento -> registrarProfesional());
        JButton limpiar = new JButton("Limpiar");
        limpiar.addActionListener(evento -> limpiarProfesional());
        agregarBotones(formulario, c, 7, guardar, limpiar);

        JPanel listado = crearPanelListado("Profesionales registrados");
        JPanel busqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        busqueda.add(new JLabel("Buscar por DNI:"));
        busqueda.add(profesionalBuscarDni);
        JButton buscar = new JButton("Buscar");
        buscar.addActionListener(evento -> buscarProfesional());
        busqueda.add(buscar);
        JButton todos = new JButton("Mostrar todos");
        todos.addActionListener(evento -> actualizarTablaProfesionales(sistema.getProfesionales()));
        busqueda.add(todos);
        listado.add(busqueda, BorderLayout.NORTH);
        listado.add(new JScrollPane(tablaProfesionales), BorderLayout.CENTER);

        panel.add(formulario, BorderLayout.WEST);
        panel.add(listado, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPestanaEspecialidades() {
        JPanel panel = crearPanelPestana();
        JPanel formulario = crearPanelFormulario("Alta de especialidad");
        GridBagConstraints c = restricciones();
        agregarFila(formulario, c, 0, "Nombre:", especialidadNombre);
        JButton guardar = new JButton("Registrar especialidad");
        guardar.addActionListener(evento -> registrarEspecialidad());
        agregarBotones(formulario, c, 1, guardar);

        JPanel listado = crearPanelListado("Especialidades disponibles");
        listado.add(new JScrollPane(tablaEspecialidades), BorderLayout.CENTER);
        panel.add(formulario, BorderLayout.WEST);
        panel.add(listado, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPestanaAgendas() {
        JPanel panel = crearPanelPestana();
        JPanel formulario = crearPanelFormulario("Alta de agenda");
        GridBagConstraints c = restricciones();
        agregarFila(formulario, c, 0, "Profesional:", agendaProfesional);
        agregarFila(formulario, c, 1, "Día:", agendaDia);
        agregarFila(formulario, c, 2, "Hora inicio:", agendaHoraInicio);
        agregarFila(formulario, c, 3, "Hora fin:", agendaHoraFin);
        agregarFila(formulario, c, 4, "Fecha desde:", agendaFechaDesde);
        agregarFila(formulario, c, 5, "Fecha hasta:", agendaFechaHasta);
        JButton guardar = new JButton("Registrar agenda");
        guardar.addActionListener(evento -> registrarAgenda());
        JButton limpiar = new JButton("Limpiar");
        limpiar.addActionListener(evento -> limpiarAgenda());
        agregarBotones(formulario, c, 6, guardar, limpiar);

        JPanel listado = crearPanelListado("Consulta de agendas");
        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtros.add(new JLabel("Día:"));
        filtros.add(agendaBuscarDia);
        filtros.add(new JLabel("Especialidad:"));
        filtros.add(agendaBuscarEspecialidad);
        filtros.add(agendaSoloActivas);
        JButton buscar = new JButton("Buscar");
        buscar.addActionListener(evento -> buscarAgendas());
        filtros.add(buscar);
        listado.add(filtros, BorderLayout.NORTH);
        listado.add(new JScrollPane(tablaAgendas), BorderLayout.CENTER);

        panel.add(formulario, BorderLayout.WEST);
        panel.add(listado, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPestanaTurnos() {
        JPanel panel = crearPanelPestana();
        JPanel formulario = crearPanelFormulario("Asignación de turno");
        GridBagConstraints c = restricciones();
        JPanel buscarPaciente = new JPanel(new BorderLayout(5, 0));
        buscarPaciente.setOpaque(false);
        JButton botonBuscarPaciente = new JButton("Buscar");
        botonBuscarPaciente.addActionListener(evento -> buscarPacienteParaTurno());
        buscarPaciente.add(turnoPacienteDniAsignacion, BorderLayout.CENTER);
        buscarPaciente.add(botonBuscarPaciente, BorderLayout.EAST);
        agregarFila(formulario, c, 0, "DNI paciente:", buscarPaciente);
        agregarFila(formulario, c, 1, "Paciente:", turnoPacienteSeleccionado);

        turnoEspecialidad.addActionListener(evento -> cargarProfesionalesParaTurno());
        turnoProfesional.addActionListener(evento -> cargarAgendasParaTurno());
        turnoAgenda.addActionListener(evento -> cargarFechasParaTurno());
        turnoFecha.addActionListener(evento -> cargarHorariosParaTurno());

        agregarFila(formulario, c, 2, "Especialidad:", turnoEspecialidad);
        agregarFila(formulario, c, 3, "Profesional:", turnoProfesional);
        agregarFila(formulario, c, 4, "Agenda:", turnoAgenda);
        agregarFila(formulario, c, 5, "Fecha disponible:", turnoFecha);

        turnoHorarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        turnoHorarios.setVisibleRowCount(7);
        JScrollPane horarios = new JScrollPane(turnoHorarios);
        horarios.setPreferredSize(new Dimension(170, 125));
        agregarFila(formulario, c, 6, "Horarios:", horarios);

        JButton consultar = new JButton("Actualizar horarios");
        consultar.addActionListener(evento -> cargarHorariosParaTurno());
        JButton asignar = new JButton("Asignar turno");
        asignar.addActionListener(evento -> asignarTurno());
        agregarBotones(formulario, c, 7, consultar, asignar);

        JPanel listado = crearPanelListado("Búsqueda y anulación de turnos");
        JPanel busqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        busqueda.add(new JLabel("DNI del paciente:"));
        busqueda.add(turnoBuscarDni);
        JButton buscar = new JButton("Buscar turnos");
        buscar.addActionListener(evento -> buscarTurnos());
        busqueda.add(buscar);
        JButton anular = new JButton("Anular turno seleccionado");
        anular.addActionListener(evento -> anularTurno());
        busqueda.add(anular);
        listado.add(busqueda, BorderLayout.NORTH);
        listado.add(new JScrollPane(tablaTurnos), BorderLayout.CENTER);

        panel.add(formulario, BorderLayout.WEST);
        panel.add(listado, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelPestana() {
        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }

    private JPanel crearPanelFormulario(String titulo) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        return panel;
    }

    private JPanel crearPanelListado(String titulo) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        return panel;
    }

    private GridBagConstraints restricciones() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 5, 4, 5);
        c.anchor = GridBagConstraints.LINE_START;
        return c;
    }

    private void agregarFila(
            JPanel panel, GridBagConstraints c, int fila, String texto, java.awt.Component componente) {
        c.gridx = 0;
        c.gridy = fila;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_END;
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setLabelFor(componente);
        panel.add(etiqueta, c);

        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(componente, c);
    }

    private void agregarBotones(JPanel panel, GridBagConstraints c, int fila, JButton... botones) {
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        acciones.setOpaque(false);
        for (JButton boton : botones) {
            acciones.add(boton);
        }
        c.gridx = 0;
        c.gridy = fila;
        c.gridwidth = 2;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(acciones, c);
        c.gridwidth = 1;
    }

    private void registrarPaciente() {
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

    private void buscarPaciente() {
        Paciente paciente = sistema.buscarPacientePorDni(pacienteBuscarDni.getText());
        if (paciente == null) {
            mostrarError("No se encontró un paciente con ese DNI.");
            return;
        }
        actualizarTablaPacientes(java.util.Arrays.asList(paciente));
        informar("Paciente encontrado.");
    }

    private void registrarProfesional() {
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

    private void buscarProfesional() {
        Profesional profesional = sistema.buscarProfesionalPorDni(profesionalBuscarDni.getText());
        if (profesional == null) {
            mostrarError("No se encontró un profesional con ese DNI.");
            return;
        }
        actualizarTablaProfesionales(java.util.Arrays.asList(profesional));
        informar("Profesional encontrado.");
    }

    private void registrarEspecialidad() {
        try {
            Especialidad especialidad = sistema.agregarEspecialidad(especialidadNombre.getText());
            mostrarExito("Especialidad registrada: " + especialidad.getNombre());
            especialidadNombre.setText("");
            recargarInterfaz();
        } catch (OperacionInvalidaException e) {
            mostrarError(e.getMessage());
        }
    }

    private void registrarAgenda() {
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

    private void buscarAgendas() {
        String dia = (String) agendaBuscarDia.getSelectedItem();
        String especialidad = (String) agendaBuscarEspecialidad.getSelectedItem();
        List<Agenda> agendas = sistema.buscarAgendas(
                "Todos".equals(dia) ? "" : dia,
                "Todas".equals(especialidad) ? "" : especialidad);
        actualizarTablaAgendas(agendas);
        informar("Consulta de agendas actualizada.");
    }

    private void buscarPacienteParaTurno() {
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

    private void cargarProfesionalesParaTurno() {
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

    private void cargarAgendasParaTurno() {
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

    private void cargarFechasParaTurno() {
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

    private void cargarHorariosParaTurno() {
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

    private void asignarTurno() {
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

    private void buscarTurnos() {
        List<Turno> turnos = sistema.buscarTurnosPorPaciente(turnoBuscarDni.getText());
        actualizarTablaTurnos(turnos);
        if (turnos.isEmpty()) {
            mostrarError("No se encontraron turnos para ese DNI.");
        } else {
            informar("Turnos del paciente cargados.");
        }
    }

    private void anularTurno() {
        int fila = tablaTurnos.getSelectedRow();
        if (fila < 0) {
            mostrarError("Seleccione un turno de la tabla.");
            return;
        }
        int idTurno = Integer.parseInt(tablaTurnos.getValueAt(fila, 0).toString());
        int respuesta = JOptionPane.showConfirmDialog(
                this,
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

    private void recargarDatos() {
        if (hayFormulariosConDatos() && !confirmarDescarteFormularios("recargar los datos desde archivos")) {
            return;
        }
        sistema.recargarDatos();
        recargarInterfaz();
        informar("Datos recargados desde los archivos.");
    }

    private void guardarDatos() {
        sistema.guardarDatos();
        informar("Datos guardados en los archivos del proyecto.");
        JOptionPane.showMessageDialog(
                this,
                "Los datos actuales fueron guardados correctamente.",
                "Guardado de datos",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void recargarInterfaz() {
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
        for (String dia : DIAS) {
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

    private void salirConControlDeFormularios() {
        if (!hayFormulariosConDatos()) {
            confirmarSalida();
            return;
        }

        if (confirmarDescarteFormularios("salir del sistema")) {
            dispose();
        }
    }

    private void confirmarSalida() {
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Desea salir del sistema?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    private boolean confirmarDescarteFormularios(String accion) {
        int respuesta = JOptionPane.showConfirmDialog(
                this,
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
        JOptionPane.showMessageDialog(this, mensaje, "Operación completada", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        informar("Revise la operación.");
        JOptionPane.showMessageDialog(this, mensaje, "Operación no válida", JOptionPane.ERROR_MESSAGE);
    }

    private void informar(String mensaje) {
        estado.setText(mensaje);
    }

    private static DefaultTableModel crearModelo(String... columnas) {
        return new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
    }

    private static void limpiarModelo(DefaultTableModel modelo) {
        modelo.setRowCount(0);
    }

    private static String[] crearHorarios(String inicio, String fin) {
        java.time.LocalTime actual = java.time.LocalTime.parse(inicio);
        java.time.LocalTime limite = java.time.LocalTime.parse(fin);
        java.util.ArrayList<String> horarios = new java.util.ArrayList<>();
        while (!actual.isAfter(limite)) {
            horarios.add(actual.toString());
            actual = actual.plusMinutes(15);
        }
        return horarios.toArray(new String[0]);
    }

    @SuppressWarnings("unchecked")
    private static <T> ElementoCombo<T> obtenerSeleccion(JComboBox<ElementoCombo<T>> combo) {
        return (ElementoCombo<T>) combo.getSelectedItem();
    }

    private static void configurarApariencia() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException e) {
            // Se conserva la apariencia predeterminada de Swing.
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            configurarApariencia();
            if (!LoginDialog.mostrar(null)) {
                return;
            }
            PrincipalApp app = new PrincipalApp();
            if (args.length > 0) {
                app.mostrarPanel(obtenerIndiceInicial(args[0]));
            }
            app.setVisible(true);
        });
    }

    private static int obtenerIndiceInicial(String seccion) {
        if ("profesionales".equalsIgnoreCase(seccion)) {
            return 1;
        }
        if ("especialidades".equalsIgnoreCase(seccion)) {
            return 2;
        }
        if ("agendas".equalsIgnoreCase(seccion)) {
            return 3;
        }
        if ("turnos".equalsIgnoreCase(seccion)) {
            return 4;
        }
        return 0;
    }

    private static class ElementoCombo<T> {
        private final T valor;
        private final String etiqueta;

        ElementoCombo(T valor, String etiqueta) {
            this.valor = valor;
            this.etiqueta = etiqueta;
        }

        T getValor() {
            return this.valor;
        }

        @Override
        public String toString() {
            return this.etiqueta;
        }
    }
}
