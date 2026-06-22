package com.beloqui.controlador;

import com.beloqui.vista.LoginDialog;
import java.util.Arrays;
import javax.swing.JButton;

public class ControladorLogin {
    private final LoginDialog vista;
    private final Autenticador autenticador = new Autenticador();

    public ControladorLogin(LoginDialog vista) {
        this.vista = vista;
    }

    public void registrarEventos(JButton ingresar, JButton cancelar) {
        ingresar.addActionListener(evento -> ingresar());
        cancelar.addActionListener(evento -> vista.dispose());
        vista.clave.addActionListener(evento -> ingresar());
    }

    private void ingresar() {
        char[] claveIngresada = vista.clave.getPassword();
        try {
            if (autenticador.validarCredenciales(vista.usuario.getText(), claveIngresada)) {
                vista.marcarAutenticado();
                vista.dispose();
                return;
            }
            vista.mostrarCredencialesInvalidas();
        } finally {
            Arrays.fill(claveIngresada, '\0');
        }
    }
}
