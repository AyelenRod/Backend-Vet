package com.vetfinder.model;

import java.time.LocalDate;

/**
 * Modelo de datos para la tabla MASCOTA
 * Representa las mascotas de los usuarios
 */
public class Mascota {
    private int idMascota;
    private String nombre;
    private String raza;
    private LocalDate fechaNacimiento;
    private int idSexo;
    private int idUsuario;

    // Constructor vacío
    public Mascota() {}

    // Constructor completo
    public Mascota(int idMascota, String nombre, String raza, LocalDate fechaNacimiento,
                   int idSexo, int idUsuario) {
        this.idMascota = idMascota;
        this.nombre = nombre;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.idSexo = idSexo;
        this.idUsuario = idUsuario;
    }

    // Constructor sin ID (para creación)
    public Mascota(String nombre, String raza, LocalDate fechaNacimiento,
                   int idSexo, int idUsuario) {
        this.nombre = nombre;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.idSexo = idSexo;
        this.idUsuario = idUsuario;
    }

    // Getters y Setters
    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(int idSexo) {
        this.idSexo = idSexo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "idMascota=" + idMascota +
                ", nombre='" + nombre + '\'' +
                ", raza='" + raza + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", idSexo=" + idSexo +
                ", idUsuario=" + idUsuario +
                '}';
    }
}