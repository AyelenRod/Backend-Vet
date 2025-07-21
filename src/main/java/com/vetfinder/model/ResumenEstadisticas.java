package com.vetfinder.model;

/**
 * Modelo de datos para resumen general de estadísticas
 * Contiene métricas principales del sistema para dashboard
 */
public class ResumenEstadisticas {
    // Totales generales
    private int totalCitas;
    private int totalMascotas;
    private int totalVeterinarios;
    private int totalTutores;

    // Estados de citas
    private int citasPendientes;
    private int citasAceptadas;
    private int citasRechazadas;

    // Métricas calculadas
    private double tasaAceptacion;
    private double promedioCitasPorVeterinario;
    private double promedioCitasPorMascota;

    // Constructor vacío
    public ResumenEstadisticas() {}

    // Constructor completo
    public ResumenEstadisticas(int totalCitas, int totalMascotas, int totalVeterinarios,
                               int totalTutores, int citasPendientes, int citasAceptadas,
                               int citasRechazadas) {
        this.totalCitas = totalCitas;
        this.totalMascotas = totalMascotas;
        this.totalVeterinarios = totalVeterinarios;
        this.totalTutores = totalTutores;
        this.citasPendientes = citasPendientes;
        this.citasAceptadas = citasAceptadas;
        this.citasRechazadas = citasRechazadas;

        // Calcular métricas automáticamente
        calcularMetricas();
    }

    /**
     * Calcula las métricas derivadas basadas en los datos base
     */
    public void calcularMetricas() {
        // Tasa de aceptación (porcentaje de citas aceptadas)
        int totalCitasConEstado = citasAceptadas + citasRechazadas;
        if (totalCitasConEstado > 0) {
            this.tasaAceptacion = ((double) citasAceptadas / totalCitasConEstado) * 100;
        } else {
            this.tasaAceptacion = 0.0;
        }

        // Promedio de citas por veterinario
        if (totalVeterinarios > 0) {
            this.promedioCitasPorVeterinario = (double) totalCitas / totalVeterinarios;
        } else {
            this.promedioCitasPorVeterinario = 0.0;
        }

        // Promedio de citas por mascota
        if (totalMascotas > 0) {
            this.promedioCitasPorMascota = (double) totalCitas / totalMascotas;
        } else {
            this.promedioCitasPorMascota = 0.0;
        }
    }

    /**
     * Obtiene el total de usuarios (veterinarios + tutores)
     * @return Total de usuarios en el sistema
     */
    public int getTotalUsuarios() {
        return totalVeterinarios + totalTutores;
    }

    /**
     * Obtiene el porcentaje de citas pendientes
     * @return Porcentaje de citas pendientes respecto al total
     */
    public double getPorcentajeCitasPendientes() {
        if (totalCitas > 0) {
            return ((double) citasPendientes / totalCitas) * 100;
        }
        return 0.0;
    }

    // Getters y Setters
    public int getTotalCitas() {
        return totalCitas;
    }

    public void setTotalCitas(int totalCitas) {
        this.totalCitas = totalCitas;
        calcularMetricas(); // Recalcular cuando cambian los datos
    }

    public int getTotalMascotas() {
        return totalMascotas;
    }

    public void setTotalMascotas(int totalMascotas) {
        this.totalMascotas = totalMascotas;
        calcularMetricas();
    }

    public int getTotalVeterinarios() {
        return totalVeterinarios;
    }

    public void setTotalVeterinarios(int totalVeterinarios) {
        this.totalVeterinarios = totalVeterinarios;
        calcularMetricas();
    }

    public int getTotalTutores() {
        return totalTutores;
    }

    public void setTotalTutores(int totalTutores) {
        this.totalTutores = totalTutores;
        calcularMetricas();
    }

    public int getCitasPendientes() {
        return citasPendientes;
    }

    public void setCitasPendientes(int citasPendientes) {
        this.citasPendientes = citasPendientes;
        calcularMetricas();
    }

    public int getCitasAceptadas() {
        return citasAceptadas;
    }

    public void setCitasAceptadas(int citasAceptadas) {
        this.citasAceptadas = citasAceptadas;
        calcularMetricas();
    }

    public int getCitasRechazadas() {
        return citasRechazadas;
    }

    public void setCitasRechazadas(int citasRechazadas) {
        this.citasRechazadas = citasRechazadas;
        calcularMetricas();
    }

    public double getTasaAceptacion() {
        return tasaAceptacion;
    }

    public double getPromedioCitasPorVeterinario() {
        return promedioCitasPorVeterinario;
    }

    public double getPromedioCitasPorMascota() {
        return promedioCitasPorMascota;
    }

    @Override
    public String toString() {
        return "ResumenEstadisticas{" +
                "totalCitas=" + totalCitas +
                ", totalMascotas=" + totalMascotas +
                ", totalVeterinarios=" + totalVeterinarios +
                ", totalTutores=" + totalTutores +
                ", citasPendientes=" + citasPendientes +
                ", citasAceptadas=" + citasAceptadas +
                ", citasRechazadas=" + citasRechazadas +
                ", tasaAceptacion=" + tasaAceptacion +
                ", promedioCitasPorVeterinario=" + promedioCitasPorVeterinario +
                ", promedioCitasPorMascota=" + promedioCitasPorMascota +
                '}';
    }
}
