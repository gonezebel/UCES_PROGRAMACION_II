package com.beloqui.vista;

import java.util.Scanner;

public class VistaConsola {
    private final Scanner scanner;

    public VistaConsola(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        System.out.println("\n--- Sistema de turnos ---");
        System.out.println("1. Agregar paciente");
        System.out.println("2. Agregar profesional");
        System.out.println("3. Agregar agenda");
        System.out.println("4. Buscar paciente");
        System.out.println("5. Buscar profesional");
        System.out.println("6. Buscar agenda por dia, especialidad y profesional");
        System.out.println("7. Asignar turno");
        System.out.println("8. Buscar y cancelar turnos por paciente");
        System.out.println("9. Agregar especialidad");
        System.out.println("0. Salir");
        System.out.println("Escriba \"cancelar\" dentro de una opcion para volver al menu.");
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return this.scanner.nextLine();
    }
}
