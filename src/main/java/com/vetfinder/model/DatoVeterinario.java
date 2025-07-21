package com.vetfinder.model;

/**
 * Modelo de datos para la tabla DATOVETERINARIO
 * Representa los datos específicos de un veterinario
 */
public class DatoVeterinario {
    private int idDatoVeterinario;
    private int idConsultorio;
    private int idUsuario;
    private int idEspecialidad;

    // Constructor vacío
    public DatoVeterinario() {}

    // Constructor completo
    public DatoVeterinario(int idDatoVeterinario, int idConsultorio, int idUsuario, int idEspecialidad) {
        this.idDatoVeterinario = idDatoVeterinario;
        this.idConsultorio = idConsultorio;
        this.idUsuario = idUsuario;
        this.idEspecialidad = idEspecialidad;
    }

    // Constructor sin ID (para creación)
    public DatoVeterinario(int idConsultorio, int idUsuario, int idEspecialidad) {
        this.idConsultorio = idConsultorio;
        this.idUsuario = idUsuario;
        this.idEspecialidad = idEspecialidad;
    }

    // Getters y Setters
    public int getIdDatoVeterinario() {
        return idDatoVeterinario;
    }

    public void setIdDatoVeterinario(int idDatoVeterinario) {
        this.idDatoVeterinario = idDatoVeterinario;
    }

    public int getIdConsultorio() {
        return idConsultorio;
    }

    public void setIdConsultorio(int idConsultorio) {
        this.idConsultorio = idConsultorio;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(int idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    @Override
    public String toString() {
        return "DatoVeterinario{" +
                "idDatoVeterinario=" + idDatoVeterinario +
                ", idConsultorio=" + idConsultorio +
                ", idUsuario=" + idUsuario +
                ", idEspecialidad=" + idEspecialidad +
                '}';
    }
}
