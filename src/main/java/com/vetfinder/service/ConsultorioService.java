package com.vetfinder.service;

import com.vetfinder.model.Consultorio;
import com.vetfinder.repository.ConsultorioRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Servicio para la gestión de consultorios
 * Contiene la lógica de negocio para operaciones con consultorios
 */
public class ConsultorioService {
    private final ConsultorioRepository consultorioRepository;

    public ConsultorioService(ConsultorioRepository consultorioRepository) {
        this.consultorioRepository = consultorioRepository;
    }

    /**
     * Obtiene todos los consultorios
     * @return Lista de todos los consultorios
     * @throws SQLException Error en la consulta
     */
    public List<Consultorio> getAllConsultorios() throws SQLException {
        return consultorioRepository.findAll();
    }

    /**
     * Obtiene un consultorio por su ID
     * @param id ID del consultorio
     * @return Consultorio encontrado o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Consultorio getConsultorioById(int id) throws SQLException {
        return consultorioRepository.findById(id);
    }

    /**
     * Crea un nuevo consultorio
     * @param consultorio Consultorio a crear
     * @return ID del consultorio creado
     * @throws SQLException Error en la inserción
     */
    public int createConsultorio(Consultorio consultorio) throws SQLException {
        // Validaciones básicas
        validateConsultorio(consultorio);
        return consultorioRepository.save(consultorio);
    }

    /**
     * Actualiza un consultorio existente
     * @param consultorio Consultorio con los datos actualizados
     * @return true si se actualizó correctamente
     * @throws SQLException Error en la actualización
     */
    public boolean updateConsultorio(Consultorio consultorio) throws SQLException {
        // Validaciones básicas
        validateConsultorio(consultorio);
        return consultorioRepository.update(consultorio);
    }

    /**
     * Elimina un consultorio por su ID
     * @param id ID del consultorio a eliminar
     * @return true si se eliminó correctamente
     * @throws SQLException Error en la eliminación
     */
    public boolean deleteConsultorio(int id) throws SQLException {
        return consultorioRepository.delete(id);
    }

    /**
     * Valida los datos básicos de un consultorio
     * @param consultorio Consultorio a validar
     */
    private void validateConsultorio(Consultorio consultorio) {
        if (consultorio == null) {
            throw new IllegalArgumentException("El consultorio no puede ser nulo");
        }
        if (consultorio.getNombreConsultorio() == null || consultorio.getNombreConsultorio().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del consultorio es requerido");
        }
        if (consultorio.getHorario() == null) {
            throw new IllegalArgumentException("El horario es requerido");
        }
        if (consultorio.getIdDatoVeterinario() <= 0) {
            throw new IllegalArgumentException("El ID del dato veterinario es requerido");
        }
    }
}
