package com.vetfinder.repository;

import com.vetfinder.config.DatabaseConfig;
import com.vetfinder.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de la tabla USUARIO
 * MODIFICADO: Solo el mapeo de telefono que causaba colgamientos
 */
public class UsuarioRepository {

    /**
     * Obtiene todos los usuarios de la base de datos - SIN MODIFICACIONES
     */
    public List<Usuario> findAll() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT id_usuario, nombre, apellidos, fecha_nacimiento, correo, contrasena, " +
                "telefono, descripcion, cedula, id_direccion, id_rol FROM USUARIO ORDER BY nombre";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = mapResultSetToUsuario(rs);
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    /**
     * Busca un usuario por su ID - SIN MODIFICACIONES
     */
    public Usuario findById(int idUsuario) throws SQLException {
        Usuario usuario = null;
        String query = "SELECT id_usuario, nombre, apellidos, fecha_nacimiento, correo, contrasena, " +
                "telefono, descripcion, cedula, id_direccion, id_rol FROM USUARIO WHERE id_usuario = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = mapResultSetToUsuario(rs);
                }
            }
        }
        return usuario;
    }

    /**
     * Busca usuarios por rol - SIN MODIFICACIONES
     */
    public List<Usuario> findByRol(int idRol) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT id_usuario, nombre, apellidos, fecha_nacimiento, correo, contrasena, " +
                "telefono, descripcion, cedula, id_direccion, id_rol FROM USUARIO WHERE id_rol = ? ORDER BY nombre";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRol);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = mapResultSetToUsuario(rs);
                    usuarios.add(usuario);
                }
            }
        }
        return usuarios;
    }

    /**
     * Busca un usuario por correo electrónico - SIN MODIFICACIONES
     */
    public Usuario findByCorreo(String correo) throws SQLException {
        Usuario usuario = null;
        String query = "SELECT id_usuario, nombre, apellidos, fecha_nacimiento, correo, contrasena, " +
                "telefono, descripcion, cedula, id_direccion, id_rol FROM USUARIO WHERE correo = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, correo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = mapResultSetToUsuario(rs);
                }
            }
        }
        return usuario;
    }

    // MANTENER todos los otros métodos (save, update, delete) SIN MODIFICACIONES
    public int save(Usuario usuario) throws SQLException {
        String query = "INSERT INTO USUARIO (nombre, apellidos, fecha_nacimiento, correo, contrasena, " +
                "telefono, descripcion, cedula, id_direccion, id_rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellidos());
            stmt.setDate(3, Date.valueOf(usuario.getFechaNacimiento()));
            stmt.setString(4, usuario.getCorreo());
            stmt.setString(5, usuario.getContrasena());

            // CORREGIDO: Manejo mejorado de telefono BIGINT
            if (usuario.getTelefono() > 0) {
                stmt.setLong(6, usuario.getTelefono());
            } else {
                stmt.setNull(6, java.sql.Types.BIGINT);
            }

            // Manejar descripcion null (solo veterinarios)
            if (usuario.getDescripcion() != null && !usuario.getDescripcion().trim().isEmpty()) {
                stmt.setString(7, usuario.getDescripcion());
            } else {
                stmt.setNull(7, java.sql.Types.VARCHAR);
            }

            // Manejar cedula null (solo veterinarios)
            if (usuario.getCedula() > 0) {
                stmt.setInt(8, usuario.getCedula());
            } else {
                stmt.setNull(8, java.sql.Types.INTEGER);
            }

            // Manejar id_direccion null
            if (usuario.getIdDireccion() > 0) {
                stmt.setInt(9, usuario.getIdDireccion());
            } else {
                stmt.setNull(9, java.sql.Types.INTEGER);
            }

            stmt.setInt(10, usuario.getIdRol());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear usuario, no se insertaron filas");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al crear usuario, no se obtuvo el ID");
                }
            }
        }
    }

    public boolean update(Usuario usuario) throws SQLException {
        String query = "UPDATE USUARIO SET nombre = ?, apellidos = ?, fecha_nacimiento = ?, " +
                "correo = ?, contrasena = ?, telefono = ?, descripcion = ?, cedula = ?, " +
                "id_direccion = ?, id_rol = ? WHERE id_usuario = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellidos());
            stmt.setDate(3, Date.valueOf(usuario.getFechaNacimiento()));
            stmt.setString(4, usuario.getCorreo());
            stmt.setString(5, usuario.getContrasena());

            // CORREGIDO: Manejo mejorado de telefono BIGINT
            if (usuario.getTelefono() > 0) {
                stmt.setLong(6, usuario.getTelefono());
            } else {
                stmt.setNull(6, java.sql.Types.BIGINT);
            }

            // Manejar descripcion null (solo veterinarios)
            if (usuario.getDescripcion() != null && !usuario.getDescripcion().trim().isEmpty()) {
                stmt.setString(7, usuario.getDescripcion());
            } else {
                stmt.setNull(7, java.sql.Types.VARCHAR);
            }

            // Manejar cedula null (solo veterinarios)
            if (usuario.getCedula() > 0) {
                stmt.setInt(8, usuario.getCedula());
            } else {
                stmt.setNull(8, java.sql.Types.INTEGER);
            }

            // Manejar id_direccion null
            if (usuario.getIdDireccion() > 0) {
                stmt.setInt(9, usuario.getIdDireccion());
            } else {
                stmt.setNull(9, java.sql.Types.INTEGER);
            }

            stmt.setInt(10, usuario.getIdRol());
            stmt.setInt(11, usuario.getIdUsuario());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean delete(int idUsuario) throws SQLException {
        String query = "DELETE FROM USUARIO WHERE id_usuario = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUsuario);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * MODIFICADO: Mapeo corregido para evitar colgamientos con telefono BIGINT
     */
    private Usuario mapResultSetToUsuario(ResultSet rs) throws SQLException {
        try {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("id_usuario"));
            usuario.setNombre(rs.getString("nombre"));
            usuario.setApellidos(rs.getString("apellidos"));
            usuario.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
            usuario.setCorreo(rs.getString("correo"));
            usuario.setContrasena(rs.getString("contrasena"));

            // CORREGIDO: Manejo seguro de telefono BIGINT para evitar colgamientos
            try {
                long telefono = rs.getLong("telefono");
                if (rs.wasNull()) {
                    usuario.setTelefono(0);
                } else {
                    usuario.setTelefono(telefono);
                }
            } catch (SQLException e) {
                System.err.println("Error al mapear telefono: " + e.getMessage());
                usuario.setTelefono(0); // Valor por defecto seguro
            }

            // Manejar campos nullable de forma segura
            usuario.setDescripcion(rs.getString("descripcion")); // Puede ser null para tutores

            int cedula = rs.getInt("cedula");
            usuario.setCedula(rs.wasNull() ? 0 : cedula);

            int idDireccion = rs.getInt("id_direccion");
            usuario.setIdDireccion(rs.wasNull() ? 0 : idDireccion);

            usuario.setIdRol(rs.getInt("id_rol"));

            return usuario;

        } catch (Exception e) {
            System.err.println("Error al mapear usuario: " + e.getMessage());
            e.printStackTrace();
            throw new SQLException("Error al mapear datos de usuario", e);
        }
    }
}