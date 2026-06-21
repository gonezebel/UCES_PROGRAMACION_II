package com.beloqui.controlador;

import com.beloqui.modelo.Profesional;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestorProfesionalesTexto {
    private final String nombreArchivo;

    public GestorProfesionalesTexto(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void guardarProfesionales(List<Profesional> profesionales) {
        crearCarpetaDatos();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.nombreArchivo))) {
            for (Profesional profesional : profesionales) {
                bw.write(profesional.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar profesionales: " + e.getMessage());
        }
    }

    public void agregarProfesional(Profesional profesional) {
        crearCarpetaDatos();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.nombreArchivo, true))) {
            bw.write(profesional.toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al agregar profesional: " + e.getMessage());
        }
    }

    public List<Profesional> leerProfesionales() {
        List<Profesional> profesionales = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(this.nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                profesionales.add(Profesional.fromString(linea));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de profesionales no encontrado.");
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error al leer profesionales: " + e.getMessage());
        }

        return profesionales;
    }

    private void crearCarpetaDatos() {
        File archivo = new File(this.nombreArchivo);
        File carpeta = archivo.getParentFile();
        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }
    }
}
