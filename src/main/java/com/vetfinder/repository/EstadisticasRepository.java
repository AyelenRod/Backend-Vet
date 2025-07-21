package com.vetfinder.repository;

import com.vetfinder.config.DatabaseConfig;
import com.vetfinder.model.EstadisticaHorario;
import com.vetfinder.model.EstadisticaServicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones de consulta de estadísticas
 * Maneja todas las consultas SQL para generar reportes y gráficas
 * CORREGIDO: Compatible con Java 8+ (sin text blocks)
 */
public class EstadisticasRepository {

    /**
     * Obtiene estadísticas de horarios más concurridos
     * Agrupa las citas por franja horaria y cuenta la cantidad
     * @return Lista de estadísticas por horario ordenadas por cantidad
     * @throws SQLException Error en la consulta
     */
    public List<EstadisticaHorario> findHorariosConcurridos() throws SQLException {
        List<EstadisticaHorario> estadisticas = new ArrayList<>();

        // Query que agrupa por franja horaria y cuenta las citas
        String query = "SELECT " +
                "CASE " +
                "WHEN HOUR(hora) BETWEEN 8 AND 11 THEN '09:00:00' " +
                "WHEN HOUR(hora) BETWEEN 12 AND 15 THEN '12:00:00' " +
                "WHEN HOUR(hora) BETWEEN 16 AND 19 THEN '15:00:00' " +
                "ELSE '18:00:00' " +
                "END as franja_horaria, " +
                "COUNT(*) as cantidad_citas " +
                "FROM CITA " +
                "WHERE fecha >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
                "GROUP BY franja_horaria " +
                "ORDER BY cantidad_citas DESC";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String franjaHoraria = rs.getString("franja_horaria");
                int cantidadCitas = rs.getInt("cantidad_citas");

                EstadisticaHorario estadistica = new EstadisticaHorario(franjaHoraria, cantidadCitas);
                estadisticas.add(estadistica);
            }
        }

        return estadisticas;
    }

    /**
     * Obtiene estadísticas de servicios más solicitados
     * Agrupa las citas por tipo de servicio y cuenta la cantidad
     * @return Lista de estadísticas por servicio ordenadas por cantidad
     * @throws SQLException Error en la consulta
     */
    public List<EstadisticaServicio> findServiciosSolicitados() throws SQLException {
        List<EstadisticaServicio> estadisticas = new ArrayList<>();

        // Query que une citas con servicios para obtener estadísticas
        String query = "SELECT " +
                "s.id_servicio, " +
                "s.nombre as nombre_servicio, " +
                "s.precio, " +
                "COUNT(c.id_cita) as cantidad_solicitudes " +
                "FROM SERVICIO s " +
                "LEFT JOIN CITA c ON s.id_servicio = c.id_servicio " +
                "WHERE c.fecha >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) OR c.fecha IS NULL " +
                "GROUP BY s.id_servicio, s.nombre, s.precio " +
                "HAVING cantidad_solicitudes > 0 " +
                "ORDER BY cantidad_solicitudes DESC " +
                "LIMIT 10";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Primero obtener el total para calcular porcentajes
            int totalSolicitudes = getTotalSolicitudesServicios();

            while (rs.next()) {
                int idServicio = rs.getInt("id_servicio");
                String nombreServicio = rs.getString("nombre_servicio");
                float precio = rs.getFloat("precio");
                int cantidadSolicitudes = rs.getInt("cantidad_solicitudes");

                EstadisticaServicio estadistica = new EstadisticaServicio(
                        idServicio, nombreServicio, cantidadSolicitudes, precio
                );

                // Calcular porcentaje respecto al total
                estadistica.calcularPorcentaje(totalSolicitudes);

                estadisticas.add(estadistica);
            }
        }

        return estadisticas;
    }

    /**
     * Obtiene el total de citas en el sistema
     * @return Número total de citas
     * @throws SQLException Error en la consulta
     */
    public int countTotalCitas() throws SQLException {
        String query = "SELECT COUNT(*) as total FROM CITA";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    /**
     * Obtiene el total de mascotas en el sistema
     * @return Número total de mascotas
     * @throws SQLException Error en la consulta
     */
    public int countTotalMascotas() throws SQLException {
        String query = "SELECT COUNT(*) as total FROM MASCOTA";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    /**
     * Obtiene el total de veterinarios en el sistema
     * @return Número total de veterinarios
     * @throws SQLException Error en la consulta
     */
    public int countTotalVeterinarios() throws SQLException {
        String query = "SELECT COUNT(*) as total FROM USUARIO WHERE id_rol = 2"; // Rol veterinario

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    /**
     * Obtiene el total de tutores en el sistema
     * @return Número total de tutores
     * @throws SQLException Error en la consulta
     */
    public int countTotalTutores() throws SQLException {
        String query = "SELECT COUNT(*) as total FROM USUARIO WHERE id_rol = 1"; // Rol tutor

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    /**
     * Obtiene el total de citas por estado específico
     * @param estado Estado de la cita ("Pendiente", "Aceptada", "Rechazada")
     * @return Número de citas con ese estado
     * @throws SQLException Error en la consulta
     */
    public int countCitasByEstado(String estado) throws SQLException {
        String query = "SELECT COUNT(*) as total FROM CITA WHERE estado = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, estado);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0;
    }

    /**
     * Obtiene el total de solicitudes de servicios para calcular porcentajes
     * @return Total de solicitudes de servicios
     * @throws SQLException Error en la consulta
     */
    private int getTotalSolicitudesServicios() throws SQLException {
        String query = "SELECT COUNT(*) as total " +
                "FROM CITA " +
                "WHERE fecha >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
}