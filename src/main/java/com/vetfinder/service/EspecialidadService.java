package com.vetfinder.service;

import com.vetfinder.model.Especialidad;
import com.vetfinder.repository.EspecialidadRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Servicio para la gestión de especialidades
 * Contiene la lógica de negocio para operaciones con especialidades
 */
public class EspecialidadService {
    private final EspecialidadRepository especialidadRepository;

    public EspecialidadService(EspecialidadRepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }

    /**
     * Obtiene todas las especialidades
     * @return Lista de todas las especialidades
     * @throws SQLException Error en la consulta
     */
    public List<Especialidad> getAllEspecialidades() throws SQLException {
        return especialidadRepository.findAll();
    }

    /**
     * Obtiene una especialidad por su ID
     * @param id ID de la especialidad
     * @return Especialidad encontrada o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Especialidad getEspecialidadById(int id) throws SQLException {
        return especialidadRepository.findById(id);
    }

    /**
     * Crea una nueva especialidad
     * @param especialidad Especialidad a crear
     * @return ID de la especialidad creada
     * @throws SQLException Error en la inserción
     */
    public int createEspecialidad(Especialidad especialidad) throws SQLException {
        // Validación básica
        if (especialidad == null || especialidad.getNombre() == null || especialidad.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la especialidad es requerido");
        }
        return especialidadRepository.save(especialidad);
    }

    /**
     * Actualiza una especialidad existente
     * @param especialidad Especialidad con los datos actualizados
     * @return true si se actualizó correctamente
     * @throws SQLException Error en la actualización
     */
    public boolean updateEspecialidad(Especialidad especialidad) throws SQLException {
        // Validación básica
        if (especialidad == null || especialidad.getNombre() == null || especialidad.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la especialidad es requerido");
        }
        return especialidadRepository.update(especialidad);
    }

    /**
     * Elimina una especialidad por su ID
     * @param id ID de la especialidad a eliminar
     * @return true si se eliminó correctamente
     * @throws SQLException Error en la eliminación
     */
    public boolean deleteEspecialidad(int id) throws SQLException {
        return especialidadRepository.delete(id);
    }
}