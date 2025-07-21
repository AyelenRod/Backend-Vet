package com.vetfinder.model;

/**
 * Modelo de datos para la tabla DIRECCION
 * Representa las direcciones de usuarios y consultorios
 * Modificado: Se removió el atributo mapsLink
 */
public class Direccion {
    private int idDireccion;
    private String calle;

    // Constructor vacío
    public Direccion() {}

    // Constructor completo
    public Direccion(int idDireccion, String calle) {
        this.idDireccion = idDireccion;
        this.calle = calle;
    }

    // Constructor sin ID (para creación)
    public Direccion(String calle) {
        this.calle = calle;
    }

    // Getters y Setters
    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "idDireccion=" + idDireccion +
                ", calle='" + calle + '\'' +
                '}';
    }
}