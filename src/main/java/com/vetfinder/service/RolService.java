package com.vetfinder.service;

import com.vetfinder.model.Rol;
import com.vetfinder.repository.RolRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Servicio para la gestión de roles
 * Contiene la lógica de negocio para operaciones con roles
 */
public class RolService {
    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    /**
     * Obtiene todos los roles
     * @return Lista de todos los roles
     * @throws SQLException Error en la consulta
     */
    public List<Rol> getAllRoles() throws SQLException {
        return rolRepository.findAll();
    }

    /**
     * Obtiene un rol por su ID
     * @param id ID del rol
     * @return Rol encontrado o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Rol getRolById(int id) throws SQLException {
        return rolRepository.findById(id);
    }

    /**
     * Crea un nuevo rol
     * @param rol Rol a crear
     * @return ID del rol creado
     * @throws SQLException Error en la inserción
     */
    public int createRol(Rol rol) throws SQLException {
        // Validación básica
        if (rol == null || rol.getNombre() == null || rol.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del rol es requerido");
        }
        return rolRepository.save(rol);
    }

    /**
     * Actualiza un rol existente
     * @param rol Rol con los datos actualizados
     * @return true si se actualizó correctamente
     * @throws SQLException Error en la actualización
     */
    public boolean updateRol(Rol rol) throws SQLException {
        // Validación básica
        if (rol == null || rol.getNombre() == null || rol.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del rol es requerido");
        }
        return rolRepository.update(rol);
    }

    /**
     * Elimina un rol por su ID
     * @param id ID del rol a eliminar
     * @return true si se eliminó correctamente
     * @throws SQLException Error en la eliminación
     */
    public boolean deleteRol(int id) throws SQLException {
        return rolRepository.delete(id);
    }
}