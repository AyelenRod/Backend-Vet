package com.vetfinder.repository;

import com.vetfinder.config.DatabaseConfig;
import com.vetfinder.model.Consultorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de la tabla CONSULTORIO
 * Maneja todas las interacciones con la base de datos para consultorios
 */
public class ConsultorioRepository {

    /**
     * Obtiene todos los consultorios de la base de datos
     * @return Lista de todos los consultorios
     * @throws SQLException Error en la consulta
     */
    public List<Consultorio> findAll() throws SQLException {
        List<Consultorio> consultorios = new ArrayList<>();
        String query = "SELECT id_consultorio, horario, nombre_consultorio, id_datoveterinario FROM CONSULTORIO ORDER BY nombre_consultorio";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Consultorio consultorio = new Consultorio();
                consultorio.setIdConsultorio(rs.getInt("id_consultorio"));
                consultorio.setHorario(rs.getTime("horario").toLocalTime());
                consultorio.setNombreConsultorio(rs.getString("nombre_consultorio"));
                consultorio.setIdDatoVeterinario(rs.getInt("id_datoveterinario"));
                consultorios.add(consultorio);
            }
        }
        return consultorios;
    }

    /**
     * Busca un consultorio por su ID
     * @param idConsultorio ID del consultorio a buscar
     * @return Consultorio encontrado o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Consultorio findById(int idConsultorio) throws SQLException {
        Consultorio consultorio = null;
        String query = "SELECT id_consultorio, horario, nombre_consultorio, id_datoveterinario FROM CONSULTORIO WHERE id_consultorio = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idConsultorio);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    consultorio = new Consultorio();
                    consultorio.setIdConsultorio(rs.getInt("id_consultorio"));
                    consultorio.setHorario(rs.getTime("horario").toLocalTime());
                    consultorio.setNombreConsultorio(rs.getString("nombre_consultorio"));
                    consultorio.setIdDatoVeterinario(rs.getInt("id_datoveterinario"));
                }
            }
        }
        return consultorio;
    }

    /**
     * Guarda un nuevo consultorio en la base de datos
     * @param consultorio Consultorio a guardar
     * @return ID del consultorio creado
     * @throws SQLException Error en la inserción
     */
    public int save(Consultorio consultorio) throws SQLException {
        String query = "INSERT INTO CONSULTORIO (horario, nombre_consultorio, id_datoveterinario) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTime(1, Time.valueOf(consultorio.getHorario()));
            stmt.setString(2, consultorio.getNombreConsultorio());
            stmt.setInt(3, consultorio.getIdDatoVeterinario());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear consultorio, no se insertaron filas");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al crear consultorio, no se obtuvo el ID");
                }
            }
        }
    }

    /**
     * Actualiza un consultorio existente
     * @param consultorio Consultorio con los datos actualizados
     * @return true si se actualizó correctamente, false si no se encontró el consultorio
     * @throws SQLException Error en la actualización
     */
    public boolean update(Consultorio consultorio) throws SQLException {
        String query = "UPDATE CONSULTORIO SET horario = ?, nombre_consultorio = ?, id_datoveterinario = ? WHERE id_consultorio = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setTime(1, Time.valueOf(consultorio.getHorario()));
            stmt.setString(2, consultorio.getNombreConsultorio());
            stmt.setInt(3, consultorio.getIdDatoVeterinario());
            stmt.setInt(4, consultorio.getIdConsultorio());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Elimina un consultorio por su ID
     * @param idConsultorio ID del consultorio a eliminar
     * @return true si se eliminó correctamente, false si no se encontró el consultorio
     * @throws SQLException Error en la eliminación
     */
    public boolean delete(int idConsultorio) throws SQLException {
        String query = "DELETE FROM CONSULTORIO WHERE id_consultorio = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idConsultorio);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}