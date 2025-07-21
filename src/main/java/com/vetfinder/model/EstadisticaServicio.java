package com.vetfinder.model;

/**
 * Modelo de datos para estadísticas de servicios más solicitados
 * Representa la cantidad de citas por tipo de servicio para gráficas
 */
public class EstadisticaServicio {
    private int idServicio;         // ID del servicio
    private String nombreServicio;  // Nombre del servicio (ej: "Vacunación", "Desparasitación")
    private int cantidadSolicitudes; // Número de veces que se ha solicitado
    private float precio;           // Precio del servicio
    private double porcentaje;      // Porcentaje respecto al total

    // Constructor vacío
    public EstadisticaServicio() {}

    // Constructor completo
    public EstadisticaServicio(int idServicio, String nombreServicio, int cantidadSolicitudes,
                               float precio, double porcentaje) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.cantidadSolicitudes = cantidadSolicitudes;
        this.precio = precio;
        this.porcentaje = porcentaje;
    }

    // Constructor sin porcentaje (se calcula después)
    public EstadisticaServicio(int idServicio, String nombreServicio, int cantidadSolicitudes, float precio) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.cantidadSolicitudes = cantidadSolicitudes;
        this.precio = precio;
        this.porcentaje = 0.0; // Se calculará externamente
    }

    /**
     * Calcula el porcentaje basado en el total de solicitudes
     * @param totalSolicitudes Total de solicitudes de todos los servicios
     */
    public void calcularPorcentaje(int totalSolicitudes) {
        if (totalSolicitudes > 0) {
            this.porcentaje = ((double) this.cantidadSolicitudes / totalSolicitudes) * 100;
        } else {
            this.porcentaje = 0.0;
        }
    }

    /**
     * Obtiene un nombre corto para mostrar en gráficas
     * @return Nombre truncado si es muy largo
     */
    public String getNombreCorto() {
        if (nombreServicio == null) return "";
        if (nombreServicio.length() <= 12) return nombreServicio;
        return nombreServicio.substring(0, 12) + "...";
    }

    // Getters y Setters
    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public int getCantidadSolicitudes() {
        return cantidadSolicitudes;
    }

    public void setCantidadSolicitudes(int cantidadSolicitudes) {
        this.cantidadSolicitudes = cantidadSolicitudes;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    @Override
    public String toString() {
        return "EstadisticaServicio{" +
                "idServicio=" + idServicio +
                ", nombreServicio='" + nombreServicio + '\'' +
                ", cantidadSolicitudes=" + cantidadSolicitudes +
                ", precio=" + precio +
                ", porcentaje=" + porcentaje +
                '}';
    }
}