package com.vetfinder.repository;

import com.vetfinder.config.DatabaseConfig;
import com.vetfinder.model.DatoVeterinario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de la tabla DATOVETERINARIO
 * Maneja todas las interacciones con la base de datos para datos veterinarios
 */
public class DatoVeterinarioRepository {

    /**
     * Obtiene todos los datos veterinarios de la base de datos
     * @return Lista de todos los datos veterinarios
     * @throws SQLException Error en la consulta
     */
    public List<DatoVeterinario> findAll() throws SQLException {
        List<DatoVeterinario> datosVeterinarios = new ArrayList<>();
        String query = "SELECT id_datoveterinario, id_consultorio, id_usuario, id_especialidad FROM DATOVETERINARIO";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DatoVeterinario datoVeterinario = new DatoVeterinario();
                datoVeterinario.setIdDatoVeterinario(rs.getInt("id_datoveterinario"));
                datoVeterinario.setIdConsultorio(rs.getInt("id_consultorio"));
                datoVeterinario.setIdUsuario(rs.getInt("id_usuario"));
                datoVeterinario.setIdEspecialidad(rs.getInt("id_especialidad"));
                datosVeterinarios.add(datoVeterinario);
            }
        }
        return datosVeterinarios;
    }

    /**
     * Busca un dato veterinario por su ID
     * @param idDatoVeterinario ID del dato veterinario a buscar
     * @return Dato veterinario encontrado o null si no existe
     * @throws SQLException Error en la consulta
     */
    public DatoVeterinario findById(int idDatoVeterinario) throws SQLException {
        DatoVeterinario datoVeterinario = null;
        String query = "SELECT id_datoveterinario, id_consultorio, id_usuario, id_especialidad FROM DATOVETERINARIO WHERE id_datoveterinario = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idDatoVeterinario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    datoVeterinario = new DatoVeterinario();
                    datoVeterinario.setIdDatoVeterinario(rs.getInt("id_datoveterinario"));
                    datoVeterinario.setIdConsultorio(rs.getInt("id_consultorio"));
                    datoVeterinario.setIdUsuario(rs.getInt("id_usuario"));
                    datoVeterinario.setIdEspecialidad(rs.getInt("id_especialidad"));
                }
            }
        }
        return datoVeterinario;
    }

    /**
     * Busca datos veterinarios por usuario
     * @param idUsuario ID del usuario veterinario
     * @return Lista de datos veterinarios del usuario
     * @throws SQLException Error en la consulta
     */
    public List<DatoVeterinario> findByUsuario(int idUsuario) throws SQLException {
        List<DatoVeterinario> datosVeterinarios = new ArrayList<>();
        String query = "SELECT id_datoveterinario, id_consultorio, id_usuario, id_especialidad FROM DATOVETERINARIO WHERE id_usuario = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DatoVeterinario datoVeterinario = new DatoVeterinario();
                    datoVeterinario.setIdDatoVeterinario(rs.getInt("id_datoveterinario"));
                    datoVeterinario.setIdConsultorio(rs.getInt("id_consultorio"));
                    datoVeterinario.setIdUsuario(rs.getInt("id_usuario"));
                    datoVeterinario.setIdEspecialidad(rs.getInt("id_especialidad"));
                    datosVeterinarios.add(datoVeterinario);
                }
            }
        }
        return datosVeterinarios;
    }

    /**
     * Guarda un nuevo dato veterinario en la base de datos
     * @param datoVeterinario Dato veterinario a guardar
     * @return ID del dato veterinario creado
     * @throws SQLException Error en la inserción
     */
    public int save(DatoVeterinario datoVeterinario) throws SQLException {
        String query = "INSERT INTO DATOVETERINARIO (id_consultorio, id_usuario, id_especialidad) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, datoVeterinario.getIdConsultorio());
            stmt.setInt(2, datoVeterinario.getIdUsuario());
            stmt.setInt(3, datoVeterinario.getIdEspecialidad());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear dato veterinario, no se insertaron filas");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al crear dato veterinario, no se obtuvo el ID");
                }
            }
        }
    }

    /**
     * Actualiza un dato veterinario existente
     * @param datoVeterinario Dato veterinario con los datos actualizados
     * @return true si se actualizó correctamente, false si no se encontró el dato veterinario
     * @throws SQLException Error en la actualización
     */
    public boolean update(DatoVeterinario datoVeterinario) throws SQLException {
        String query = "UPDATE DATOVETERINARIO SET id_consultorio = ?, id_usuario = ?, id_especialidad = ? WHERE id_datoveterinario = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, datoVeterinario.getIdConsultorio());
            stmt.setInt(2, datoVeterinario.getIdUsuario());
            stmt.setInt(3, datoVeterinario.getIdEspecialidad());
            stmt.setInt(4, datoVeterinario.getIdDatoVeterinario());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Elimina un dato veterinario por su ID
     * @param idDatoVeterinario ID del dato veterinario a eliminar
     * @return true si se eliminó correctamente, false si no se encontró el dato veterinario
     * @throws SQLException Error en la eliminación
     */
    public boolean delete(int idDatoVeterinario) throws SQLException {
        String query = "DELETE FROM DATOVETERINARIO WHERE id_datoveterinario = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idDatoVeterinario);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}