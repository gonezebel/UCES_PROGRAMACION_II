package com.beloqui.modelo;

public class Especialidad {
    private static final String SEPARADOR_ARCHIVO = ";";
    private static int contadorEspecialidades = 1;

    private int idEspecialidad;
    private String nombre;

    public Especialidad() {
        this.idEspecialidad = contadorEspecialidades++;
        setNombre("");
    }

    public Especialidad(String nombre) {
        this.idEspecialidad = contadorEspecialidades++;
        setNombre(nombre);
    }

    public Especialidad(int idEspecialidad, String nombre) {
        setIdEspecialidad(idEspecialidad);
        setNombre(nombre);
    }

    public int getIdEspecialidad() {
        return this.idEspecialidad;
    }

    public void setIdEspecialidad(int idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
        if (idEspecialidad >= contadorEspecialidades) {
            contadorEspecialidades = idEspecialidad + 1;
        }
    }

    public String getNombre() {
        return this.nombre.trim();
    }

    public void setNombre(String nombre) {
        this.nombre = normalizarTexto(nombre);
    }

    public boolean validarNombre() {
        return !getNombre().isEmpty();
    }

    @Override
    public String toString() {
        return this.idEspecialidad + SEPARADOR_ARCHIVO + getNombre();
    }

    public static Especialidad fromString(String linea) {
        String[] datos = linea.split(SEPARADOR_ARCHIVO, -1);
        if (datos.length == 1) {
            return new Especialidad(datos[0]);
        }
        if (datos.length != 2) {
            throw new IllegalArgumentException("La linea no representa una especialidad valida.");
        }
        return new Especialidad(Integer.parseInt(datos[0]), datos[1]);
    }

    private String normalizarTexto(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.replace(SEPARADOR_ARCHIVO, ",").trim();
    }
}
