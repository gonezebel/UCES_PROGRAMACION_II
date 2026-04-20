package com.beloqui.controlador;

import com.beloqui.modelo.Paciente;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestorPacientesTexto {
    private final String nombreArchivo;

    public GestorPacientesTexto(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void guardarPacientes(List<Paciente> pacientes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.nombreArchivo))) {
            for (Paciente paciente : pacientes) {
                bw.write(paciente.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar pacientes: " + e.getMessage());
        }
    }

    public void agregarPaciente(Paciente paciente) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.nombreArchivo, true))) {
            bw.write(paciente.toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al agregar paciente: " + e.getMessage());
        }
    }

    public List<Paciente> leerPacientes() {
        List<Paciente> pacientes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(this.nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                pacientes.add(Paciente.fromString(linea));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de pacientes no encontrado.");
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error al leer pacientes: " + e.getMessage());
        }

        return pacientes;
    }
}
