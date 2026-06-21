package com.beloqui.controlador;

public class OperacionInvalidaException extends Exception {
    private static final long serialVersionUID = 1L;

    public OperacionInvalidaException(String mensaje) {
        super(mensaje);
    }
}
