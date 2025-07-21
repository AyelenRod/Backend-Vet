package com.vetfinder.repository;

import com.vetfinder.config.DatabaseConfig;
import com.vetfinder.model.Especialidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de la tabla ESPECIALIDAD
 * Maneja todas las interacciones con la base de datos para especialidades
 */
public class EspecialidadRepository {

    /**
     * Obtiene todas las especialidades de la base de datos
     * @return Lista de todas las especialidades
     * @throws SQLException Error en la consulta
     */
    public List<Especialidad> findAll() throws SQLException {
        List<Especialidad> especialidades = new ArrayList<>();
        String query = "SELECT id_especialidad, nombre FROM ESPECIALIDAD ORDER BY nombre";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Especialidad especialidad = new Especialidad();
                especialidad.setIdEspecialidad(rs.getInt("id_especialidad"));
                especialidad.setNombre(rs.getString("nombre"));
                especialidades.add(especialidad);
            }
        }
        return especialidades;
    }

    /**
     * Busca una especialidad por su ID
     * @param idEspecialidad ID de la especialidad a buscar
     * @return Especialidad encontrada o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Especialidad findById(int idEspecialidad) throws SQLException {
        Especialidad especialidad = null;
        String query = "SELECT id_especialidad, nombre FROM ESPECIALIDAD WHERE id_especialidad = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idEspecialidad);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    especialidad = new Especialidad();
                    especialidad.setIdEspecialidad(rs.getInt("id_especialidad"));
                    especialidad.setNombre(rs.getString("nombre"));
                }
            }
        }
        return especialidad;
    }

    /**
     * Guarda una nueva especialidad en la base de datos
     * @param especialidad Especialidad a guardar
     * @return ID de la especialidad creada
     * @throws SQLException Error en la inserción
     */
    public int save(Especialidad especialidad) throws SQLException {
        String query = "INSERT INTO ESPECIALIDAD (nombre) VALUES (?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, especialidad.getNombre());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear especialidad, no se insertaron filas");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al crear especialidad, no se obtuvo el ID");
                }
            }
        }
    }

    /**
     * Actualiza una especialidad existente
     * @param especialidad Especialidad con los datos actualizados
     * @return true si se actualizó correctamente, false si no se encontró la especialidad
     * @throws SQLException Error en la actualización
     */
    public boolean update(Especialidad especialidad) throws SQLException {
        String query = "UPDATE ESPECIALIDAD SET nombre = ? WHERE id_especialidad = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, especialidad.getNombre());
            stmt.setInt(2, especialidad.getIdEspecialidad());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Elimina una especialidad por su ID
     * @param idEspecialidad ID de la especialidad a eliminar
     * @return true si se eliminó correctamente, false si no se encontró la especialidad
     * @throws SQLException Error en la eliminación
     */
    public boolean delete(int idEspecialidad) throws SQLException {
        String query = "DELETE FROM ESPECIALIDAD WHERE id_especialidad = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idEspecialidad);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}
