package com.vetfinder.service;

import com.vetfinder.model.DatoVeterinario;
import com.vetfinder.repository.DatoVeterinarioRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Servicio para la gestión de datos veterinarios
 * Contiene la lógica de negocio para operaciones con datos veterinarios
 */
public class DatoVeterinarioService {
    private final DatoVeterinarioRepository datoVeterinarioRepository;

    public DatoVeterinarioService(DatoVeterinarioRepository datoVeterinarioRepository) {
        this.datoVeterinarioRepository = datoVeterinarioRepository;
    }

    /**
     * Obtiene todos los datos veterinarios
     * @return Lista de todos los datos veterinarios
     * @throws SQLException Error en la consulta
     */
    public List<DatoVeterinario> getAllDatosVeterinarios() throws SQLException {
        return datoVeterinarioRepository.findAll();
    }

    /**
     * Obtiene un dato veterinario por su ID
     * @param id ID del dato veterinario
     * @return Dato veterinario encontrado o null si no existe
     * @throws SQLException Error en la consulta
     */
    public DatoVeterinario getDatoVeterinarioById(int id) throws SQLException {
        return datoVeterinarioRepository.findById(id);
    }

    /**
     * Obtiene todos los datos veterinarios de un usuario
     * @param idUsuario ID del usuario veterinario
     * @return Lista de datos veterinarios del usuario
     * @throws SQLException Error en la consulta
     */
    public List<DatoVeterinario> getDatosVeterinariosByUsuario(int idUsuario) throws SQLException {
        return datoVeterinarioRepository.findByUsuario(idUsuario);
    }

    /**
     * Crea un nuevo dato veterinario
     * @param datoVeterinario Dato veterinario a crear
     * @return ID del dato veterinario creado
     * @throws SQLException Error en la inserción
     */
    public int createDatoVeterinario(DatoVeterinario datoVeterinario) throws SQLException {
        // Validaciones básicas
        validateDatoVeterinario(datoVeterinario);
        return datoVeterinarioRepository.save(datoVeterinario);
    }

    /**
     * Actualiza un dato veterinario existente
     * @param datoVeterinario Dato veterinario con los datos actualizados
     * @return true si se actualizó correctamente
     * @throws SQLException Error en la actualización
     */
    public boolean updateDatoVeterinario(DatoVeterinario datoVeterinario) throws SQLException {
        // Validaciones básicas
        validateDatoVeterinario(datoVeterinario);
        return datoVeterinarioRepository.update(datoVeterinario);
    }

    /**
     * Elimina un dato veterinario por su ID
     * @param id ID del dato veterinario a eliminar
     * @return true si se eliminó correctamente
     * @throws SQLException Error en la eliminación
     */
    public boolean deleteDatoVeterinario(int id) throws SQLException {
        return datoVeterinarioRepository.delete(id);
    }

    /**
     * Valida los datos básicos de un dato veterinario
     * @param datoVeterinario Dato veterinario a validar
     */
    private void validateDatoVeterinario(DatoVeterinario datoVeterinario) {
        if (datoVeterinario == null) {
            throw new IllegalArgumentException("El dato veterinario no puede ser nulo");
        }
        if (datoVeterinario.getIdUsuario() <= 0) {
            throw new IllegalArgumentException("El ID del usuario es requerido");
        }
        if (datoVeterinario.getIdEspecialidad() <= 0) {
            throw new IllegalArgumentException("El ID de la especialidad es requerido");
        }
    }
}