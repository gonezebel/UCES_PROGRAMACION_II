package com.beloqui.vista;

import com.beloqui.controlador.ControladorPrincipalApp;
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
    public static final String[] DIAS = {
        "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
    };

    private final ControladorPrincipalApp controlador;
    public final JPanel panelContenido = new JPanel(new BorderLayout());
    public final JLabel estado = new JLabel("Sistema listo.");
    private JPanel panelPacientes;
    private JPanel panelProfesionales;
    private JPanel panelEspecialidades;
    private JPanel panelAgendas;
    private JPanel panelTurnos;

    public final JTextField pacienteNombre = new JTextField(18);
    public final JTextField pacienteApellido = new JTextField(18);
    public final JTextField pacienteDni = new JTextField(12);
    public final JTextField pacienteTelefono = new JTextField(14);
    public final JTextField pacienteHistoria = new JTextField(10);
    public final JTextField pacienteObraSocial = new JTextField(18);
    public final JTextField pacienteEmail = new JTextField(22);
    public final JTextField pacienteNacimiento = new JTextField(12);
    public final JRadioButton pacienteFemenino = new JRadioButton("Femenino");
    public final JRadioButton pacienteMasculino = new JRadioButton("Masculino");
    public final ButtonGroup grupoSexoPaciente = new ButtonGroup();
    public final JTextField pacienteBuscarDni = new JTextField(12);
    public final DefaultTableModel modeloPacientes =
            crearModelo("ID", "Nombre", "DNI", "Teléfono", "Historia clínica", "Obra social");
    public final JTable tablaPacientes = new JTable(modeloPacientes);

    public final JTextField profesionalNombre = new JTextField(18);
    public final JTextField profesionalApellido = new JTextField(18);
    public final JTextField profesionalDni = new JTextField(12);
    public final JTextField profesionalTelefono = new JTextField(14);
    public final JTextField profesionalMatricula = new JTextField(12);
    public final JTextField profesionalEmail = new JTextField(22);
    public final JComboBox<String> profesionalEspecialidad = new JComboBox<>();
    public final JTextField profesionalBuscarDni = new JTextField(12);
    public final DefaultTableModel modeloProfesionales =
            crearModelo("ID", "Nombre", "DNI", "Matrícula", "Especialidad", "Email");
    public final JTable tablaProfesionales = new JTable(modeloProfesionales);

    public final JTextField especialidadNombre = new JTextField(22);
    public final DefaultTableModel modeloEspecialidades = crearModelo("ID", "Especialidad");
    public final JTable tablaEspecialidades = new JTable(modeloEspecialidades);

    public final JComboBox<ElementoCombo<Profesional>> agendaProfesional = new JComboBox<>();
    public final JComboBox<String> agendaDia = new JComboBox<>(DIAS);
    public final JComboBox<String> agendaHoraInicio = new JComboBox<>(crearHorarios("09:00", "17:45"));
    public final JComboBox<String> agendaHoraFin = new JComboBox<>(crearHorarios("09:15", "18:00"));
    public final JTextField agendaFechaDesde = new JTextField(12);
    public final JTextField agendaFechaHasta = new JTextField(12);
    public final JComboBox<String> agendaBuscarDia = new JComboBox<>();
    public final JComboBox<String> agendaBuscarEspecialidad = new JComboBox<>();
    public final JCheckBox agendaSoloActivas = new JCheckBox("Mostrar solo agendas activas", true);
    public final DefaultTableModel modeloAgendas =
            crearModelo("ID", "Profesional", "Especialidad", "Día", "Horario", "Vigencia", "Estado");
    public final JTable tablaAgendas = new JTable(modeloAgendas);

    public final JTextField turnoPacienteDniAsignacion = new JTextField(12);
    public final JLabel turnoPacienteSeleccionado = new JLabel("Sin paciente seleccionado");
    public final JComboBox<String> turnoEspecialidad = new JComboBox<>();
    public final JComboBox<ElementoCombo<Profesional>> turnoProfesional = new JComboBox<>();
    public final JComboBox<ElementoCombo<Agenda>> turnoAgenda = new JComboBox<>();
    public final JComboBox<String> turnoFecha = new JComboBox<>();
    public final JList<String> turnoHorarios = new JList<>();
    public final JTextField turnoBuscarDni = new JTextField(12);
    public final DefaultTableModel modeloTurnos =
            crearModelo("ID", "Paciente", "Profesional", "Especialidad", "Fecha", "Hora", "Estado");
    public final JTable tablaTurnos = new JTable(modeloTurnos);

    public PrincipalApp() {
        this.controlador = new ControladorPrincipalApp(this);
        configurarVentana();
        agregarComponentes();
        controlador.recargarInterfaz();
    }

    private void configurarVentana() {
        setTitle("Centro de Salud - Sistema interno de turnos");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(980, 650));
        setSize(1120, 760);
        setLocationRelativeTo(null);
        setIconImage(IconoHospital.crearImagen(64));
        getContentPane().setBackground(COLOR_FONDO);
        controlador.registrarCierreVentana();
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
        controlador.registrarRecargar(recargar);
        archivo.add(recargar);

        JMenuItem guardar = new JMenuItem("Guardar datos");
        guardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK));
        controlador.registrarGuardar(guardar);
        archivo.add(guardar);
        archivo.addSeparator();

        JMenuItem salir = new JMenuItem("Salir");
        salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
        controlador.registrarSalir(salir);
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
        controlador.registrarMostrarPanel(opcion, indice);
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
        controlador.registrarRecargar(recargar);
        barra.add(recargar);
        JButton guardar = new JButton("Guardar");
        controlador.registrarGuardar(guardar);
        barra.add(guardar);
        return barra;
    }

    private JButton crearBotonPestana(String texto, int indice) {
        JButton boton = new JButton(texto);
        controlador.registrarMostrarPanel(boton, indice);
        return boton;
    }

    public void mostrarPanel(int indice) {
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
        controlador.registrarAltaPaciente(guardar);
        JButton limpiar = new JButton("Limpiar");
        controlador.registrarLimpiarPaciente(limpiar);
        agregarBotones(formulario, c, 9, guardar, limpiar);

        JPanel listado = crearPanelListado("Pacientes registrados");
        JPanel busqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        busqueda.add(new JLabel("Buscar por DNI:"));
        busqueda.add(pacienteBuscarDni);
        JButton buscar = new JButton("Buscar");
        controlador.registrarBuscarPaciente(buscar);
        busqueda.add(buscar);
        JButton todos = new JButton("Mostrar todos");
        controlador.registrarMostrarTodosPacientes(todos);
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
        controlador.registrarAltaProfesional(guardar);
        JButton limpiar = new JButton("Limpiar");
        controlador.registrarLimpiarProfesional(limpiar);
        agregarBotones(formulario, c, 7, guardar, limpiar);

        JPanel listado = crearPanelListado("Profesionales registrados");
        JPanel busqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        busqueda.add(new JLabel("Buscar por DNI:"));
        busqueda.add(profesionalBuscarDni);
        JButton buscar = new JButton("Buscar");
        controlador.registrarBuscarProfesional(buscar);
        busqueda.add(buscar);
        JButton todos = new JButton("Mostrar todos");
        controlador.registrarMostrarTodosProfesionales(todos);
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
        controlador.registrarAltaEspecialidad(guardar);
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
        controlador.registrarAltaAgenda(guardar);
        JButton limpiar = new JButton("Limpiar");
        controlador.registrarLimpiarAgenda(limpiar);
        agregarBotones(formulario, c, 6, guardar, limpiar);

        JPanel listado = crearPanelListado("Consulta de agendas");
        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtros.add(new JLabel("Día:"));
        filtros.add(agendaBuscarDia);
        filtros.add(new JLabel("Especialidad:"));
        filtros.add(agendaBuscarEspecialidad);
        filtros.add(agendaSoloActivas);
        JButton buscar = new JButton("Buscar");
        controlador.registrarBuscarAgendas(buscar);
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
        controlador.registrarBuscarPacienteParaTurno(botonBuscarPaciente);
        buscarPaciente.add(turnoPacienteDniAsignacion, BorderLayout.CENTER);
        buscarPaciente.add(botonBuscarPaciente, BorderLayout.EAST);
        agregarFila(formulario, c, 0, "DNI paciente:", buscarPaciente);
        agregarFila(formulario, c, 1, "Paciente:", turnoPacienteSeleccionado);

        controlador.registrarCambioEspecialidadTurno(turnoEspecialidad);
        controlador.registrarCambioProfesionalTurno(turnoProfesional);
        controlador.registrarCambioAgendaTurno(turnoAgenda);
        controlador.registrarCambioFechaTurno(turnoFecha);

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
        controlador.registrarConsultarHorarios(consultar);
        JButton asignar = new JButton("Asignar turno");
        controlador.registrarAsignarTurno(asignar);
        agregarBotones(formulario, c, 7, consultar, asignar);

        JPanel listado = crearPanelListado("Búsqueda y anulación de turnos");
        JPanel busqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        busqueda.add(new JLabel("DNI del paciente:"));
        busqueda.add(turnoBuscarDni);
        JButton buscar = new JButton("Buscar turnos");
        controlador.registrarBuscarTurnos(buscar);
        busqueda.add(buscar);
        JButton anular = new JButton("Anular turno seleccionado");
        controlador.registrarAnularTurno(anular);
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

    public static class ElementoCombo<T> {
        private final T valor;
        private final String etiqueta;

        public ElementoCombo(T valor, String etiqueta) {
            this.valor = valor;
            this.etiqueta = etiqueta;
        }

        public T getValor() {
            return this.valor;
        }

        @Override
        public String toString() {
            return this.etiqueta;
        }
    }
}
