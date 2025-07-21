package com.vetfinder.repository;

import com.vetfinder.config.DatabaseConfig;
import com.vetfinder.model.Mascota;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de la tabla MASCOTA
 * Maneja todas las interacciones con la base de datos para mascotas
 */
public class MascotaRepository {

    /**
     * Obtiene todas las mascotas de la base de datos
     * @return Lista de todas las mascotas
     * @throws SQLException Error en la consulta
     */
    public List<Mascota> findAll() throws SQLException {
        List<Mascota> mascotas = new ArrayList<>();
        String query = "SELECT id_mascota, nombre, raza, fecha_nacimiento, id_sexo, id_usuario FROM MASCOTA ORDER BY nombre";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Mascota mascota = new Mascota();
                mascota.setIdMascota(rs.getInt("id_mascota"));
                mascota.setNombre(rs.getString("nombre"));
                mascota.setRaza(rs.getString("raza"));
                mascota.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                mascota.setIdSexo(rs.getInt("id_sexo"));
                mascota.setIdUsuario(rs.getInt("id_usuario"));
                mascotas.add(mascota);
            }
        }
        return mascotas;
    }

    /**
     * Busca una mascota por su ID
     * @param idMascota ID de la mascota a buscar
     * @return Mascota encontrada o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Mascota findById(int idMascota) throws SQLException {
        Mascota mascota = null;
        String query = "SELECT id_mascota, nombre, raza, fecha_nacimiento, id_sexo, id_usuario FROM MASCOTA WHERE id_mascota = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idMascota);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    mascota = new Mascota();
                    mascota.setIdMascota(rs.getInt("id_mascota"));
                    mascota.setNombre(rs.getString("nombre"));
                    mascota.setRaza(rs.getString("raza"));
                    mascota.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                    mascota.setIdSexo(rs.getInt("id_sexo"));
                    mascota.setIdUsuario(rs.getInt("id_usuario"));
                }
            }
        }
        return mascota;
    }

    /**
     * Obtiene todas las mascotas de un usuario específico
     * @param idUsuario ID del usuario
     * @return Lista de mascotas del usuario
     * @throws SQLException Error en la consulta
     */
    public List<Mascota> findByUsuario(int idUsuario) throws SQLException {
        List<Mascota> mascotas = new ArrayList<>();
        String query = "SELECT id_mascota, nombre, raza, fecha_nacimiento, id_sexo, id_usuario FROM MASCOTA WHERE id_usuario = ? ORDER BY nombre";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Mascota mascota = new Mascota();
                    mascota.setIdMascota(rs.getInt("id_mascota"));
                    mascota.setNombre(rs.getString("nombre"));
                    mascota.setRaza(rs.getString("raza"));
                    mascota.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                    mascota.setIdSexo(rs.getInt("id_sexo"));
                    mascota.setIdUsuario(rs.getInt("id_usuario"));
                    mascotas.add(mascota);
                }
            }
        }
        return mascotas;
    }

    /**
     * Guarda una nueva mascota en la base de datos
     * @param mascota Mascota a guardar
     * @return ID de la mascota creada
     * @throws SQLException Error en la inserción
     */
    public int save(Mascota mascota) throws SQLException {
        String query = "INSERT INTO MASCOTA (nombre, raza, fecha_nacimiento, id_sexo, id_usuario) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, mascota.getNombre());
            stmt.setString(2, mascota.getRaza());
            stmt.setDate(3, Date.valueOf(mascota.getFechaNacimiento()));
            stmt.setInt(4, mascota.getIdSexo());
            stmt.setInt(5, mascota.getIdUsuario());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear mascota, no se insertaron filas");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al crear mascota, no se obtuvo el ID");
                }
            }
        }
    }

    /**
     * Actualiza una mascota existente
     * @param mascota Mascota con los datos actualizados
     * @return true si se actualizó correctamente, false si no se encontró la mascota
     * @throws SQLException Error en la actualización
     */
    public boolean update(Mascota mascota) throws SQLException {
        String query = "UPDATE MASCOTA SET nombre = ?, raza = ?, fecha_nacimiento = ?, id_sexo = ?, id_usuario = ? WHERE id_mascota = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, mascota.getNombre());
            stmt.setString(2, mascota.getRaza());
            stmt.setDate(3, Date.valueOf(mascota.getFechaNacimiento()));
            stmt.setInt(4, mascota.getIdSexo());
            stmt.setInt(5, mascota.getIdUsuario());
            stmt.setInt(6, mascota.getIdMascota());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Elimina una mascota por su ID
     * @param idMascota ID de la mascota a eliminar
     * @return true si se eliminó correctamente, false si no se encontró la mascota
     * @throws SQLException Error en la eliminación
     */
    public boolean delete(int idMascota) throws SQLException {
        String query = "DELETE FROM MASCOTA WHERE id_mascota = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idMascota);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}