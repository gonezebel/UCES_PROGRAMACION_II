package com.apellido.modelo;

public interface Notificable {
    String obtenerDestinoNotificacion();
    String enviarNotificacion(String mensaje);
}
