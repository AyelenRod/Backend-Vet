package com.vetfinder.repository;

import com.vetfinder.config.DatabaseConfig;
import com.vetfinder.model.Factura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de la tabla FACTURA
 * Maneja todas las interacciones con la base de datos para facturas
 */
public class FacturaRepository {

    /**
     * Obtiene todas las facturas de la base de datos
     * @return Lista de todas las facturas
     * @throws SQLException Error en la consulta
     */
    public List<Factura> findAll() throws SQLException {
        List<Factura> facturas = new ArrayList<>();
        String query = "SELECT id_factura, id_usuario, id_servicio, id_cita, total, fecha_factura FROM FACTURA ORDER BY fecha_factura DESC";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Factura factura = new Factura();
                factura.setIdFactura(rs.getInt("id_factura"));
                factura.setIdUsuario(rs.getInt("id_usuario"));
                factura.setIdServicio(rs.getInt("id_servicio"));
                factura.setIdCita(rs.getInt("id_cita"));
                factura.setTotal(rs.getFloat("total"));
                factura.setFechaFactura(rs.getDate("fecha_factura").toLocalDate());
                facturas.add(factura);
            }
        }
        return facturas;
    }

    /**
     * Busca una factura por su ID
     * @param idFactura ID de la factura a buscar
     * @return Factura encontrada o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Factura findById(int idFactura) throws SQLException {
        Factura factura = null;
        String query = "SELECT id_factura, id_usuario, id_servicio, id_cita, total, fecha_factura FROM FACTURA WHERE id_factura = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idFactura);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    factura = new Factura();
                    factura.setIdFactura(rs.getInt("id_factura"));
                    factura.setIdUsuario(rs.getInt("id_usuario"));
                    factura.setIdServicio(rs.getInt("id_servicio"));
                    factura.setIdCita(rs.getInt("id_cita"));
                    factura.setTotal(rs.getFloat("total"));
                    factura.setFechaFactura(rs.getDate("fecha_factura").toLocalDate());
                }
            }
        }
        return factura;
    }

    /**
     * Obtiene todas las facturas de un usuario específico
     * @param idUsuario ID del usuario
     * @return Lista de facturas del usuario
     * @throws SQLException Error en la consulta
     */
    public List<Factura> findByUsuario(int idUsuario) throws SQLException {
        List<Factura> facturas = new ArrayList<>();
        String query = "SELECT id_factura, id_usuario, id_servicio, id_cita, total, fecha_factura FROM FACTURA WHERE id_usuario = ? ORDER BY fecha_factura DESC";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Factura factura = new Factura();
                    factura.setIdFactura(rs.getInt("id_factura"));
                    factura.setIdUsuario(rs.getInt("id_usuario"));
                    factura.setIdServicio(rs.getInt("id_servicio"));
                    factura.setIdCita(rs.getInt("id_cita"));
                    factura.setTotal(rs.getFloat("total"));
                    factura.setFechaFactura(rs.getDate("fecha_factura").toLocalDate());
                    facturas.add(factura);
                }
            }
        }
        return facturas;
    }

    /**
     * Guarda una nueva factura en la base de datos
     * @param factura Factura a guardar
     * @return ID de la factura creada
     * @throws SQLException Error en la inserción
     */
    public int save(Factura factura) throws SQLException {
        String query = "INSERT INTO FACTURA (id_usuario, id_servicio, id_cita, total, fecha_factura) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, factura.getIdUsuario());
            stmt.setInt(2, factura.getIdServicio());
            stmt.setInt(3, factura.getIdCita());
            stmt.setFloat(4, factura.getTotal());
            stmt.setDate(5, Date.valueOf(factura.getFechaFactura()));

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear factura, no se insertaron filas");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al crear factura, no se obtuvo el ID");
                }
            }
        }
    }

    /**
     * Actualiza una factura existente
     * @param factura Factura con los datos actualizados
     * @return true si se actualizó correctamente, false si no se encontró la factura
     * @throws SQLException Error en la actualización
     */
    public boolean update(Factura factura) throws SQLException {
        String query = "UPDATE FACTURA SET id_usuario = ?, id_servicio = ?, id_cita = ?, total = ?, fecha_factura = ? WHERE id_factura = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, factura.getIdUsuario());
            stmt.setInt(2, factura.getIdServicio());
            stmt.setInt(3, factura.getIdCita());
            stmt.setFloat(4, factura.getTotal());
            stmt.setDate(5, Date.valueOf(factura.getFechaFactura()));
            stmt.setInt(6, factura.getIdFactura());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Elimina una factura por su ID
     * @param idFactura ID de la factura a eliminar
     * @return true si se eliminó correctamente, false si no se encontró la factura
     * @throws SQLException Error en la eliminación
     */
    public boolean delete(int idFactura) throws SQLException {
        String query = "DELETE FROM FACTURA WHERE id_factura = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idFactura);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}