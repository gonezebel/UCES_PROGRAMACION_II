package com.beloqui.principal;

import com.beloqui.vista.LoginDialog;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class PrincipalApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            configurarApariencia();
            if (!LoginDialog.mostrar(null)) {
                return;
            }
            com.beloqui.vista.PrincipalApp app = new com.beloqui.vista.PrincipalApp();
            if (args.length > 0) {
                app.mostrarPanel(obtenerIndiceInicial(args[0]));
            }
            app.setVisible(true);
        });
    }

    private static void configurarApariencia() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException e) {
            // Se conserva la apariencia predeterminada de Swing.
        }
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
}
