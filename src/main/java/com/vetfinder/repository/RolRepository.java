package com.vetfinder.repository;

import com.vetfinder.config.DatabaseConfig;
import com.vetfinder.model.Rol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de la tabla ROL
 * Maneja todas las interacciones con la base de datos para roles
 */
public class RolRepository {

    /**
     * Obtiene todos los roles de la base de datos
     * @return Lista de todos los roles
     * @throws SQLException Error en la consulta
     */
    public List<Rol> findAll() throws SQLException {
        List<Rol> roles = new ArrayList<>();
        String query = "SELECT id_rol, nombre FROM ROL ORDER BY nombre";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("id_rol"));
                rol.setNombre(rs.getString("nombre"));
                roles.add(rol);
            }
        }
        return roles;
    }

    /**
     * Busca un rol por su ID
     * @param idRol ID del rol a buscar
     * @return Rol encontrado o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Rol findById(int idRol) throws SQLException {
        Rol rol = null;
        String query = "SELECT id_rol, nombre FROM ROL WHERE id_rol = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRol);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    rol = new Rol();
                    rol.setIdRol(rs.getInt("id_rol"));
                    rol.setNombre(rs.getString("nombre"));
                }
            }
        }
        return rol;
    }

    /**
     * Guarda un nuevo rol en la base de datos
     * @param rol Rol a guardar
     * @return ID del rol creado
     * @throws SQLException Error en la inserción
     */
    public int save(Rol rol) throws SQLException {
        String query = "INSERT INTO ROL (nombre) VALUES (?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, rol.getNombre());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear rol, no se insertaron filas");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al crear rol, no se obtuvo el ID");
                }
            }
        }
    }

    /**
     * Actualiza un rol existente
     * @param rol Rol con los datos actualizados
     * @return true si se actualizó correctamente, false si no se encontró el rol
     * @throws SQLException Error en la actualización
     */
    public boolean update(Rol rol) throws SQLException {
        String query = "UPDATE ROL SET nombre = ? WHERE id_rol = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, rol.getNombre());
            stmt.setInt(2, rol.getIdRol());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Elimina un rol por su ID
     * @param idRol ID del rol a eliminar
     * @return true si se eliminó correctamente, false si no se encontró el rol
     * @throws SQLException Error en la eliminación
     */
    public boolean delete(int idRol) throws SQLException {
        String query = "DELETE FROM ROL WHERE id_rol = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRol);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}