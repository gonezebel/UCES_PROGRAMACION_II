package com.beloqui.modelo;

public class Notificacion {
    private static int contadorNotificaciones = 1;

    // Atributos
    private int idNotificacion;
    private String tipo;
    private String mensaje;
    private String fechaEnvio;
    private boolean enviada;

    // Constructores
    public Notificacion() {
        this.idNotificacion = contadorNotificaciones++;
        this.tipo = "";
        this.mensaje = "";
        this.fechaEnvio = "";
        this.enviada = false;
    }

    public Notificacion(int idNotificacion, String tipo, String mensaje, String fechaEnvio,
            boolean enviada) {
        setIdNotificacion(idNotificacion);
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.fechaEnvio = fechaEnvio;
        this.enviada = enviada;
    }

    // Getters y setters
    public int getIdNotificacion() {
        return this.idNotificacion;
    }

    public void setIdNotificacion(int idNotificacion) {
        this.idNotificacion = idNotificacion;
        if (idNotificacion >= contadorNotificaciones) {
            contadorNotificaciones = idNotificacion + 1;
        }
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFechaEnvio() {
        return this.fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public boolean isEnviada() {
        return this.enviada;
    }

    public void setEnviada(boolean enviada) {
        this.enviada = enviada;
    }

    // Metodos
    public String enviarA(Notificable destinatario) {
        this.enviada = true;
        return destinatario.enviarNotificacion(this.mensaje);
    }

    public String mostrarDetalle() {
        return "Notificacion " + this.idNotificacion + " - Tipo: " + this.tipo
                + " - Fecha: " + this.fechaEnvio + " - Enviada: " + this.enviada;
    }

    @Override
    public String toString() {
        return this.mostrarDetalle();
    }
}
