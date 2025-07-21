package com.vetfinder.model;

/**
 * Modelo de datos para la tabla ESPECIALIDAD
 * Representa las especialidades veterinarias disponibles
 */
public class Especialidad {
    private int idEspecialidad;
    private String nombre;

    // Constructor vacío
    public Especialidad() {}

    // Constructor completo
    public Especialidad(int idEspecialidad, String nombre) {
        this.idEspecialidad = idEspecialidad;
        this.nombre = nombre;
    }

    // Constructor sin ID (para creación)
    public Especialidad(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(int idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Especialidad{" +
                "idEspecialidad=" + idEspecialidad +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
