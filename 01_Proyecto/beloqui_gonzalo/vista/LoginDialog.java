package com.beloqui.vista;

import com.beloqui.controlador.ControladorLogin;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private final ControladorLogin controlador = new ControladorLogin(this);
    public final JTextField usuario = new JTextField(16);
    public final JPasswordField clave = new JPasswordField(16);
    public final JLabel mensaje = new JLabel("Ingrese sus credenciales.");
    private boolean autenticado;

    private LoginDialog(Frame owner) {
        super(owner, "Ingreso al sistema", true);
        configurar();
        agregarComponentes();
    }

    public static boolean mostrar(Frame owner) {
        LoginDialog dialogo = new LoginDialog(owner);
        dialogo.setVisible(true);
        return dialogo.autenticado;
    }

    private void configurar() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setIconImage(IconoHospital.crearImagen(64));
        setSize(410, 280);
        setLocationRelativeTo(getOwner());
        setResizable(false);
    }

    private void agregarComponentes() {
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(BorderFactory.createEmptyBorder(10, 18, 8, 18));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        formulario.add(new JLabel("Usuario:"), c);
        c.gridx = 1;
        formulario.add(usuario, c);

        c.gridx = 0;
        c.gridy = 1;
        formulario.add(new JLabel("Clave:"), c);
        c.gridx = 1;
        formulario.add(clave, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        formulario.add(mensaje, c);

        JButton ingresar = new JButton("Ingresar");
        JButton cancelar = new JButton("Cancelar");
        controlador.registrarEventos(ingresar, cancelar);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.add(cancelar);
        botones.add(ingresar);

        add(crearEncabezado(), BorderLayout.NORTH);
        add(formulario, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    private JPanel crearEncabezado() {
        JPanel encabezado = new JPanel(new BorderLayout(12, 0));
        encabezado.setBorder(BorderFactory.createEmptyBorder(14, 18, 10, 18));
        encabezado.setBackground(new Color(32, 89, 138));

        JLabel logo = new JLabel(IconoHospital.crearIcono(58));
        encabezado.add(logo, BorderLayout.WEST);

        JPanel textos = new JPanel(new GridBagLayout());
        textos.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.anchor = GridBagConstraints.WEST;

        JLabel titulo = new JLabel("Centro de Salud");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        textos.add(titulo, c);

        c.gridy = 1;
        JLabel subtitulo = new JLabel("Ingreso al sistema de turnos");
        subtitulo.setForeground(new Color(225, 237, 247));
        textos.add(subtitulo, c);

        encabezado.add(textos, BorderLayout.CENTER);
        return encabezado;
    }

    public void marcarAutenticado() {
        this.autenticado = true;
    }

    public void mostrarCredencialesInvalidas() {
        mensaje.setText("Usuario o clave incorrectos.");
        clave.setText("");
        clave.requestFocusInWindow();
    }
}
