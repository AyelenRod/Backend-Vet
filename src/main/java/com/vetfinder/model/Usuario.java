package com.vetfinder.model;

import java.time.LocalDate;

/**
 * Modelo de datos para la tabla USUARIO
 * Representa tanto tutores de mascotas como veterinarios
 * CORREGIDO: telefono como long para soportar BIGINT
 */
public class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String correo;
    private String contrasena;
    private long telefono; // CAMBIADO: de double a long para BIGINT
    private String descripcion;
    private int cedula;
    private int idDireccion;
    private int idRol;

    // Constructor vacío
    public Usuario() {}

    // Constructor completo - CORREGIDO: parametro telefono como long
    public Usuario(int idUsuario, String nombre, String apellidos, LocalDate fechaNacimiento,
                   String correo, String contrasena, long telefono, String descripcion,
                   int cedula, int idDireccion, int idRol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.descripcion = descripcion;
        this.cedula = cedula;
        this.idDireccion = idDireccion;
        this.idRol = idRol;
    }

    // Constructor sin ID (para creación) - CORREGIDO: parametro telefono como long
    public Usuario(String nombre, String apellidos, LocalDate fechaNacimiento,
                   String correo, String contrasena, long telefono, String descripcion,
                   int cedula, int idDireccion, int idRol) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.descripcion = descripcion;
        this.cedula = cedula;
        this.idDireccion = idDireccion;
        this.idRol = idRol;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    // CORREGIDO: Getter y Setter para long
    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", correo='" + correo + '\'' +
                ", telefono=" + telefono +
                ", descripcion='" + descripcion + '\'' +
                ", cedula=" + cedula +
                ", idDireccion=" + idDireccion +
                ", idRol=" + idRol +
                '}';
    }
}