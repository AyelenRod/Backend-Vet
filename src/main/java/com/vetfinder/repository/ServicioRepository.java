package com.vetfinder.repository;

import com.vetfinder.config.DatabaseConfig;
import com.vetfinder.model.Servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de la tabla SERVICIO
 * Maneja todas las interacciones con la base de datos para servicios
 */
public class ServicioRepository {

    /**
     * Obtiene todos los servicios de la base de datos
     * @return Lista de todos los servicios
     * @throws SQLException Error en la consulta
     */
    public List<Servicio> findAll() throws SQLException {
        List<Servicio> servicios = new ArrayList<>();
        String query = "SELECT id_servicio, nombre, precio FROM SERVICIO ORDER BY nombre";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Servicio servicio = new Servicio();
                servicio.setIdServicio(rs.getInt("id_servicio"));
                servicio.setNombre(rs.getString("nombre"));
                servicio.setPrecio(rs.getFloat("precio"));
                servicios.add(servicio);
            }
        }
        return servicios;
    }

    /**
     * Busca un servicio por su ID
     * @param idServicio ID del servicio a buscar
     * @return Servicio encontrado o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Servicio findById(int idServicio) throws SQLException {
        Servicio servicio = null;
        String query = "SELECT id_servicio, nombre, precio FROM SERVICIO WHERE id_servicio = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idServicio);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    servicio = new Servicio();
                    servicio.setIdServicio(rs.getInt("id_servicio"));
                    servicio.setNombre(rs.getString("nombre"));
                    servicio.setPrecio(rs.getFloat("precio"));
                }
            }
        }
        return servicio;
    }

    /**
     * Guarda un nuevo servicio en la base de datos
     * @param servicio Servicio a guardar
     * @return ID del servicio creado
     * @throws SQLException Error en la inserción
     */
    public int save(Servicio servicio) throws SQLException {
        String query = "INSERT INTO SERVICIO (nombre, precio) VALUES (?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, servicio.getNombre());
            stmt.setFloat(2, servicio.getPrecio());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear servicio, no se insertaron filas");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al crear servicio, no se obtuvo el ID");
                }
            }
        }
    }

    /**
     * Actualiza un servicio existente
     * @param servicio Servicio con los datos actualizados
     * @return true si se actualizó correctamente, false si no se encontró el servicio
     * @throws SQLException Error en la actualización
     */
    public boolean update(Servicio servicio) throws SQLException {
        String query = "UPDATE SERVICIO SET nombre = ?, precio = ? WHERE id_servicio = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, servicio.getNombre());
            stmt.setFloat(2, servicio.getPrecio());
            stmt.setInt(3, servicio.getIdServicio());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Elimina un servicio por su ID
     * @param idServicio ID del servicio a eliminar
     * @return true si se eliminó correctamente, false si no se encontró el servicio
     * @throws SQLException Error en la eliminación
     */
    public boolean delete(int idServicio) throws SQLException {
        String query = "DELETE FROM SERVICIO WHERE id_servicio = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idServicio);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}
