package com.vetfinder.repository;

import com.vetfinder.config.DatabaseConfig;
import com.vetfinder.model.Cita;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de la tabla CITA
 * Maneja todas las interacciones con la base de datos para citas
 */
public class CitaRepository {

    /**
     * Obtiene todas las citas de la base de datos
     * @return Lista de todas las citas
     * @throws SQLException Error en la consulta
     */
    public List<Cita> findAll() throws SQLException {
        List<Cita> citas = new ArrayList<>();
        String query = "SELECT id_cita, fecha, hora, id_servicio, id_mascota, id_datoveterinario, estado FROM CITA ORDER BY fecha, hora";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cita cita = new Cita();
                cita.setIdCita(rs.getInt("id_cita"));
                cita.setFecha(rs.getDate("fecha").toLocalDate());
                cita.setHora(rs.getTime("hora").toLocalTime());
                cita.setIdServicio(rs.getInt("id_servicio"));
                cita.setIdMascota(rs.getInt("id_mascota"));
                cita.setIdDatoVeterinario(rs.getInt("id_datoveterinario"));
                cita.setEstado(rs.getString("estado"));
                citas.add(cita);
            }
        }
        return citas;
    }

    /**
     * Busca una cita por su ID
     * @param idCita ID de la cita a buscar
     * @return Cita encontrada o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Cita findById(int idCita) throws SQLException {
        Cita cita = null;
        String query = "SELECT id_cita, fecha, hora, id_servicio, id_mascota, id_datoveterinario, estado FROM CITA WHERE id_cita = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idCita);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cita = new Cita();
                    cita.setIdCita(rs.getInt("id_cita"));
                    cita.setFecha(rs.getDate("fecha").toLocalDate());
                    cita.setHora(rs.getTime("hora").toLocalTime());
                    cita.setIdServicio(rs.getInt("id_servicio"));
                    cita.setIdMascota(rs.getInt("id_mascota"));
                    cita.setIdDatoVeterinario(rs.getInt("id_datoveterinario"));
                    cita.setEstado(rs.getString("estado"));
                }
            }
        }
        return cita;
    }

    /**
     * Obtiene todas las citas de una mascota específica
     * @param idMascota ID de la mascota
     * @return Lista de citas de la mascota
     * @throws SQLException Error en la consulta
     */
    public List<Cita> findByMascota(int idMascota) throws SQLException {
        List<Cita> citas = new ArrayList<>();
        String query = "SELECT id_cita, fecha, hora, id_servicio, id_mascota, id_datoveterinario, estado FROM CITA WHERE id_mascota = ? ORDER BY fecha, hora";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idMascota);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cita cita = new Cita();
                    cita.setIdCita(rs.getInt("id_cita"));
                    cita.setFecha(rs.getDate("fecha").toLocalDate());
                    cita.setHora(rs.getTime("hora").toLocalTime());
                    cita.setIdServicio(rs.getInt("id_servicio"));
                    cita.setIdMascota(rs.getInt("id_mascota"));
                    cita.setIdDatoVeterinario(rs.getInt("id_datoveterinario"));
                    cita.setEstado(rs.getString("estado"));
                    citas.add(cita);
                }
            }
        }
        return citas;
    }

    /**
     * Obtiene todas las citas de un veterinario específico
     * @param idDatoVeterinario ID del dato veterinario
     * @return Lista de citas del veterinario
     * @throws SQLException Error en la consulta
     */
    public List<Cita> findByVeterinario(int idDatoVeterinario) throws SQLException {
        List<Cita> citas = new ArrayList<>();
        String query = "SELECT id_cita, fecha, hora, id_servicio, id_mascota, id_datoveterinario, estado FROM CITA WHERE id_datoveterinario = ? ORDER BY fecha, hora";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idDatoVeterinario);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cita cita = new Cita();
                    cita.setIdCita(rs.getInt("id_cita"));
                    cita.setFecha(rs.getDate("fecha").toLocalDate());
                    cita.setHora(rs.getTime("hora").toLocalTime());
                    cita.setIdServicio(rs.getInt("id_servicio"));
                    cita.setIdMascota(rs.getInt("id_mascota"));
                    cita.setIdDatoVeterinario(rs.getInt("id_datoveterinario"));
                    cita.setEstado(rs.getString("estado"));
                    citas.add(cita);
                }
            }
        }
        return citas;
    }

    /**
     * Guarda una nueva cita en la base de datos
     * @param cita Cita a guardar
     * @return ID de la cita creada
     * @throws SQLException Error en la inserción
     */
    public int save(Cita cita) throws SQLException {
        String query = "INSERT INTO CITA (fecha, hora, id_servicio, id_mascota, id_datoveterinario, estado) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, Date.valueOf(cita.getFecha()));
            stmt.setTime(2, Time.valueOf(cita.getHora()));
            stmt.setInt(3, cita.getIdServicio());
            stmt.setInt(4, cita.getIdMascota());
            stmt.setInt(5, cita.getIdDatoVeterinario());
            stmt.setString(6, cita.getEstado());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear cita, no se insertaron filas");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al crear cita, no se obtuvo el ID");
                }
            }
        }
    }

    /**
     * Actualiza una cita existente
     * @param cita Cita con los datos actualizados
     * @return true si se actualizó correctamente, false si no se encontró la cita
     * @throws SQLException Error en la actualización
     */
    public boolean update(Cita cita) throws SQLException {
        String query = "UPDATE CITA SET fecha = ?, hora = ?, id_servicio = ?, id_mascota = ?, id_datoveterinario = ?, estado = ? WHERE id_cita = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, Date.valueOf(cita.getFecha()));
            stmt.setTime(2, Time.valueOf(cita.getHora()));
            stmt.setInt(3, cita.getIdServicio());
            stmt.setInt(4, cita.getIdMascota());
            stmt.setInt(5, cita.getIdDatoVeterinario());
            stmt.setString(6, cita.getEstado());
            stmt.setInt(7, cita.getIdCita());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Elimina una cita por su ID
     * @param idCita ID de la cita a eliminar
     * @return true si se eliminó correctamente, false si no se encontró la cita
     * @throws SQLException Error en la eliminación
     */
    public boolean delete(int idCita) throws SQLException {
        String query = "DELETE FROM CITA WHERE id_cita = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idCita);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}