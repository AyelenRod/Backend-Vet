package com.vetfinder.repository;

import com.vetfinder.config.DatabaseConfig;
import com.vetfinder.model.Sexo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de la tabla SEXO
 * Maneja todas las interacciones con la base de datos para sexos
 */
public class SexoRepository {

    /**
     * Obtiene todos los sexos de la base de datos
     * @return Lista de todos los sexos
     * @throws SQLException Error en la consulta
     */
    public List<Sexo> findAll() throws SQLException {
        List<Sexo> sexos = new ArrayList<>();
        String query = "SELECT id_sexo, nombre FROM SEXO ORDER BY nombre";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Sexo sexo = new Sexo();
                sexo.setIdSexo(rs.getInt("id_sexo"));
                sexo.setNombre(rs.getString("nombre"));
                sexos.add(sexo);
            }
        }
        return sexos;
    }

    /**
     * Busca un sexo por su ID
     * @param idSexo ID del sexo a buscar
     * @return Sexo encontrado o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Sexo findById(int idSexo) throws SQLException {
        Sexo sexo = null;
        String query = "SELECT id_sexo, nombre FROM SEXO WHERE id_sexo = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idSexo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    sexo = new Sexo();
                    sexo.setIdSexo(rs.getInt("id_sexo"));
                    sexo.setNombre(rs.getString("nombre"));
                }
            }
        }
        return sexo;
    }

    /**
     * Guarda un nuevo sexo en la base de datos
     * @param sexo Sexo a guardar
     * @return ID del sexo creado
     * @throws SQLException Error en la inserción
     */
    public int save(Sexo sexo) throws SQLException {
        String query = "INSERT INTO SEXO (nombre) VALUES (?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, sexo.getNombre());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear sexo, no se insertaron filas");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al crear sexo, no se obtuvo el ID");
                }
            }
        }
    }

    /**
     * Actualiza un sexo existente
     * @param sexo Sexo con los datos actualizados
     * @return true si se actualizó correctamente, false si no se encontró el sexo
     * @throws SQLException Error en la actualización
     */
    public boolean update(Sexo sexo) throws SQLException {
        String query = "UPDATE SEXO SET nombre = ? WHERE id_sexo = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, sexo.getNombre());
            stmt.setInt(2, sexo.getIdSexo());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Elimina un sexo por su ID
     * @param idSexo ID del sexo a eliminar
     * @return true si se eliminó correctamente, false si no se encontró el sexo
     * @throws SQLException Error en la eliminación
     */
    public boolean delete(int idSexo) throws SQLException {
        String query = "DELETE FROM SEXO WHERE id_sexo = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idSexo);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}
