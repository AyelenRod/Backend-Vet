package com.vetfinder.model;

/**
 * Modelo de datos para estadísticas de horarios concurridos
 * Representa la cantidad de citas por franja horaria para gráficas
 */
public class EstadisticaHorario {
    private String franjaHoraria;  // "9:00 AM", "12:00 PM", etc.
    private int cantidadCitas;     // Número de citas en esa franja
    private String label;          // Label para mostrar en la gráfica

    // Constructor vacío
    public EstadisticaHorario() {}

    // Constructor completo
    public EstadisticaHorario(String franjaHoraria, int cantidadCitas, String label) {
        this.franjaHoraria = franjaHoraria;
        this.cantidadCitas = cantidadCitas;
        this.label = label;
    }

    // Constructor sin label (se genera automáticamente)
    public EstadisticaHorario(String franjaHoraria, int cantidadCitas) {
        this.franjaHoraria = franjaHoraria;
        this.cantidadCitas = cantidadCitas;
        this.label = generarLabel(franjaHoraria);
    }

    /**
     * Genera un label amigable para mostrar en la gráfica
     * @param franja Franja horaria en formato HH:mm:ss
     * @return Label formateado (ej: "9 a.m.", "12 p.m.")
     */
    private String generarLabel(String franja) {
        if (franja == null) return "";

        try {
            String[] partes = franja.split(":");
            int hora = Integer.parseInt(partes[0]);

            if (hora == 0) return "12 a.m.";
            else if (hora < 12) return hora + " a.m.";
            else if (hora == 12) return "12 p.m.";
            else return (hora - 12) + " p.m.";
        } catch (Exception e) {
            return franja; // Retornar original si hay error
        }
    }

    // Getters y Setters
    public String getFranjaHoraria() {
        return franjaHoraria;
    }

    public void setFranjaHoraria(String franjaHoraria) {
        this.franjaHoraria = franjaHoraria;
        this.label = generarLabel(franjaHoraria); // Actualizar label automáticamente
    }

    public int getCantidadCitas() {
        return cantidadCitas;
    }

    public void setCantidadCitas(int cantidadCitas) {
        this.cantidadCitas = cantidadCitas;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "EstadisticaHorario{" +
                "franjaHoraria='" + franjaHoraria + '\'' +
                ", cantidadCitas=" + cantidadCitas +
                ", label='" + label + '\'' +
                '}';
    }
}