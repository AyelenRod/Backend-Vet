package com.vetfinder.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Modelo de datos para la tabla CITA
 * Representa las citas médicas veterinarias
 */
public class Cita {
    private int idCita;
    private LocalDate fecha;
    private LocalTime hora;
    private int idServicio;
    private int idMascota;
    private int idDatoVeterinario;
    private String estado;

    // Constructor vacío
    public Cita() {}

    // Constructor completo
    public Cita(int idCita, LocalDate fecha, LocalTime hora, int idServicio,
                int idMascota, int idDatoVeterinario, String estado) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.hora = hora;
        this.idServicio = idServicio;
        this.idMascota = idMascota;
        this.idDatoVeterinario = idDatoVeterinario;
        this.estado = estado;
    }

    // Constructor sin ID (para creación)
    public Cita(LocalDate fecha, LocalTime hora, int idServicio,
                int idMascota, int idDatoVeterinario, String estado) {
        this.fecha = fecha;
        this.hora = hora;
        this.idServicio = idServicio;
        this.idMascota = idMascota;
        this.idDatoVeterinario = idDatoVeterinario;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public int getIdDatoVeterinario() {
        return idDatoVeterinario;
    }

    public void setIdDatoVeterinario(int idDatoVeterinario) {
        this.idDatoVeterinario = idDatoVeterinario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "idCita=" + idCita +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", idServicio=" + idServicio +
                ", idMascota=" + idMascota +
                ", idDatoVeterinario=" + idDatoVeterinario +
                ", estado='" + estado + '\'' +
                '}';
    }
}