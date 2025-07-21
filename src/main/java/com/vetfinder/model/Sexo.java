package com.vetfinder.model;

/**
 * Modelo de datos para la tabla SEXO
 * Representa el sexo de las mascotas (Macho, Hembra)
 */
public class Sexo {
    private int idSexo;
    private String nombre;

    // Constructor vacío
    public Sexo() {}

    // Constructor completo
    public Sexo(int idSexo, String nombre) {
        this.idSexo = idSexo;
        this.nombre = nombre;
    }

    // Constructor sin ID (para creación)
    public Sexo(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(int idSexo) {
        this.idSexo = idSexo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Sexo{" +
                "idSexo=" + idSexo +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
