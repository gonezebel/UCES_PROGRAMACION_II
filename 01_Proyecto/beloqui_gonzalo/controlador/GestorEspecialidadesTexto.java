package com.beloqui.controlador;

import com.beloqui.modelo.Especialidad;
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

public class GestorEspecialidadesTexto {
    private final String nombreArchivo;

    public GestorEspecialidadesTexto(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void guardarEspecialidades(List<Especialidad> especialidades) {
        crearCarpetaDatos();
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(this.nombreArchivo), StandardCharsets.UTF_8))) {
            for (Especialidad especialidad : especialidades) {
                bw.write(especialidad.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar especialidades: " + e.getMessage());
        }
    }

    public void agregarEspecialidad(Especialidad especialidad) {
        crearCarpetaDatos();
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(this.nombreArchivo, true), StandardCharsets.UTF_8))) {
            bw.write(especialidad.toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al agregar especialidad: " + e.getMessage());
        }
    }

    public List<Especialidad> leerEspecialidades() {
        List<Especialidad> especialidades = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(this.nombreArchivo), StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                especialidades.add(Especialidad.fromString(linea));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de especialidades no encontrado.");
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error al leer especialidades: " + e.getMessage());
        }

        return especialidades;
    }

    private void crearCarpetaDatos() {
        File archivo = new File(this.nombreArchivo);
        File carpeta = archivo.getParentFile();
        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }
    }
}
