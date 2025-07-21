package com.vetfinder.model;

/**
 * Modelo de datos para la tabla ROL
 * Representa los roles del sistema (Tutor de Mascota, Veterinario, Administrador)
 */
public class Rol {
    private int idRol;
    private String nombre;

    // Constructor vacío
    public Rol() {}

    // Constructor completo
    public Rol(int idRol, String nombre) {
        this.idRol = idRol;
        this.nombre = nombre;
    }

    // Constructor sin ID (para creación)
    public Rol(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "idRol=" + idRol +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}