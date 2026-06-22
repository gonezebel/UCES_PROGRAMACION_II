package com.beloqui.controlador;

import com.beloqui.modelo.Paciente;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GestorPacientesTexto {
    private final String nombreArchivo;

    public GestorPacientesTexto(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void guardarPacientes(List<Paciente> pacientes) {
        crearCarpetaDatos();
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(this.nombreArchivo), StandardCharsets.UTF_8))) {
            for (Paciente paciente : pacientes) {
                bw.write(paciente.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar pacientes: " + e.getMessage());
        }
    }

    public void agregarPaciente(Paciente paciente) {
        crearCarpetaDatos();
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(this.nombreArchivo, true), StandardCharsets.UTF_8))) {
            bw.write(paciente.toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al agregar paciente: " + e.getMessage());
        }
    }

    public List<Paciente> leerPacientes() {
        List<Paciente> pacientes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(this.nombreArchivo), StandardCharsets.UTF_8))) {
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

    private void crearCarpetaDatos() {
        File archivo = new File(this.nombreArchivo);
        File carpeta = archivo.getParentFile();
        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }
    }
}
