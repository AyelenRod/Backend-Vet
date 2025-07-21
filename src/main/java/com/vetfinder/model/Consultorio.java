package com.vetfinder.model;

import java.time.LocalTime;

/**
 * Modelo de datos para la tabla CONSULTORIO
 * Representa los consultorios veterinarios
 */
public class Consultorio {
    private int idConsultorio;
    private LocalTime horario;
    private String nombreConsultorio;
    private int idDatoVeterinario;

    // Constructor vacío
    public Consultorio() {}

    // Constructor completo
    public Consultorio(int idConsultorio, LocalTime horario, String nombreConsultorio, int idDatoVeterinario) {
        this.idConsultorio = idConsultorio;
        this.horario = horario;
        this.nombreConsultorio = nombreConsultorio;
        this.idDatoVeterinario = idDatoVeterinario;
    }

    // Constructor sin ID (para creación)
    public Consultorio(LocalTime horario, String nombreConsultorio, int idDatoVeterinario) {
        this.horario = horario;
        this.nombreConsultorio = nombreConsultorio;
        this.idDatoVeterinario = idDatoVeterinario;
    }

    // Getters y Setters
    public int getIdConsultorio() {
        return idConsultorio;
    }

    public void setIdConsultorio(int idConsultorio) {
        this.idConsultorio = idConsultorio;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public String getNombreConsultorio() {
        return nombreConsultorio;
    }

    public void setNombreConsultorio(String nombreConsultorio) {
        this.nombreConsultorio = nombreConsultorio;
    }

    public int getIdDatoVeterinario() {
        return idDatoVeterinario;
    }

    public void setIdDatoVeterinario(int idDatoVeterinario) {
        this.idDatoVeterinario = idDatoVeterinario;
    }

    @Override
    public String toString() {
        return "Consultorio{" +
                "idConsultorio=" + idConsultorio +
                ", horario=" + horario +
                ", nombreConsultorio='" + nombreConsultorio + '\'' +
                ", idDatoVeterinario=" + idDatoVeterinario +
                '}';
    }
}