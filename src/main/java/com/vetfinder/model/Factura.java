package com.vetfinder.model;

import java.time.LocalDate;

/**
 * Modelo de datos para la tabla FACTURA
 * Representa las facturas por servicios veterinarios
 */
public class Factura {
    private int idFactura;
    private int idUsuario;
    private int idServicio;
    private int idCita;
    private float total;
    private LocalDate fechaFactura;

    // Constructor vacío
    public Factura() {}

    // Constructor completo
    public Factura(int idFactura, int idUsuario, int idServicio, int idCita,
                   float total, LocalDate fechaFactura) {
        this.idFactura = idFactura;
        this.idUsuario = idUsuario;
        this.idServicio = idServicio;
        this.idCita = idCita;
        this.total = total;
        this.fechaFactura = fechaFactura;
    }

    // Constructor sin ID (para creación)
    public Factura(int idUsuario, int idServicio, int idCita, float total, LocalDate fechaFactura) {
        this.idUsuario = idUsuario;
        this.idServicio = idServicio;
        this.idCita = idCita;
        this.total = total;
        this.fechaFactura = fechaFactura;
    }

    // Getters y Setters
    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public LocalDate getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(LocalDate fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "idFactura=" + idFactura +
                ", idUsuario=" + idUsuario +
                ", idServicio=" + idServicio +
                ", idCita=" + idCita +
                ", total=" + total +
                ", fechaFactura=" + fechaFactura +
                '}';
    }
}
