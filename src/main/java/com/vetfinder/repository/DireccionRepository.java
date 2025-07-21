package com.vetfinder.repository;

import com.vetfinder.config.DatabaseConfig;
import com.vetfinder.model.Direccion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de la tabla DIRECCION
 * Maneja todas las interacciones con la base de datos para direcciones
 * Modificado: Se removieron las referencias a maps_link
 */
public class DireccionRepository {

    /**
     * Obtiene todas las direcciones de la base de datos
     * @return Lista de todas las direcciones
     * @throws SQLException Error en la consulta
     */
    public List<Direccion> findAll() throws SQLException {
        List<Direccion> direcciones = new ArrayList<>();
        // Query modificada: removida la columna maps_link
        String query = "SELECT id_direccion, calle FROM DIRECCION ORDER BY calle";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Direccion direccion = new Direccion();
                direccion.setIdDireccion(rs.getInt("id_direccion"));
                direccion.setCalle(rs.getString("calle"));
                // Se removió la línea: direccion.setMapsLink(rs.getString("maps_link"));
                direcciones.add(direccion);
            }
        }
        return direcciones;
    }

    /**
     * Busca una dirección por su ID
     * @param idDireccion ID de la dirección a buscar
     * @return Dirección encontrada o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Direccion findById(int idDireccion) throws SQLException {
        Direccion direccion = null;
        // Query modificada: removida la columna maps_link
        String query = "SELECT id_direccion, calle FROM DIRECCION WHERE id_direccion = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idDireccion);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    direccion = new Direccion();
                    direccion.setIdDireccion(rs.getInt("id_direccion"));
                    direccion.setCalle(rs.getString("calle"));
                    // Se removió la línea: direccion.setMapsLink(rs.getString("maps_link"));
                }
            }
        }
        return direccion;
    }

    /**
     * Guarda una nueva dirección en la base de datos
     * @param direccion Dirección a guardar
     * @return ID de la dirección creada
     * @throws SQLException Error en la inserción
     */
    public int save(Direccion direccion) throws SQLException {
        // Query modificada: removida la columna maps_link
        String query = "INSERT INTO DIRECCION (calle) VALUES (?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, direccion.getCalle());
            // Se removió la línea: stmt.setString(2, direccion.getMapsLink());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear dirección, no se insertaron filas");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al crear dirección, no se obtuvo el ID");
                }
            }
        }
    }

    /**
     * Actualiza una dirección existente
     * @param direccion Dirección con los datos actualizados
     * @return true si se actualizó correctamente, false si no se encontró la dirección
     * @throws SQLException Error en la actualización
     */
    public boolean update(Direccion direccion) throws SQLException {
        // Query modificada: removida la columna maps_link
        String query = "UPDATE DIRECCION SET calle = ? WHERE id_direccion = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, direccion.getCalle());
            stmt.setInt(2, direccion.getIdDireccion());
            // Se removió la línea: stmt.setString(2, direccion.getMapsLink());
            // Se reubicó el ID al parámetro 2

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Elimina una dirección por su ID
     * @param idDireccion ID de la dirección a eliminar
     * @return true si se eliminó correctamente, false si no se encontró la dirección
     * @throws SQLException Error en la eliminación
     */
    public boolean delete(int idDireccion) throws SQLException {
        String query = "DELETE FROM DIRECCION WHERE id_direccion = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idDireccion);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}