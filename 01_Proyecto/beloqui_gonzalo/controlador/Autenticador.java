package com.beloqui.controlador;

public class Autenticador {
    private static final String USUARIO_ADMIN = "admin";
    private static final String CLAVE_ADMIN = "admin123";

    public boolean validarCredenciales(String usuario, char[] clave) {
        String usuarioNormalizado = usuario == null ? "" : usuario.trim();
        String claveTexto = clave == null ? "" : new String(clave);
        return USUARIO_ADMIN.equals(usuarioNormalizado) && CLAVE_ADMIN.equals(claveTexto);
    }
}
