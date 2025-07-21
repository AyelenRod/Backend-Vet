package com.vetfinder.model;

/**
 * Modelo de datos para la tabla SERVICIO
 * Representa los servicios veterinarios disponibles
 */
public class Servicio {
    private int idServicio;
    private String nombre;
    private float precio;

    // Constructor vacío
    public Servicio() {}

    // Constructor completo
    public Servicio(int idServicio, String nombre, float precio) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.precio = precio;
    }

    // Constructor sin ID (para creación)
    public Servicio(String nombre, float precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    // Getters y Setters
    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "idServicio=" + idServicio +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }
}