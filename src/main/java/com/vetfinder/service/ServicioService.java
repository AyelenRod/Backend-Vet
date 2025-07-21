package com.vetfinder.service;

import com.vetfinder.model.Servicio;
import com.vetfinder.repository.ServicioRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Servicio para la gestión de servicios veterinarios
 * Contiene la lógica de negocio para operaciones con servicios
 */
public class ServicioService {
    private final ServicioRepository servicioRepository;

    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    /**
     * Obtiene todos los servicios
     * @return Lista de todos los servicios
     * @throws SQLException Error en la consulta
     */
    public List<Servicio> getAllServicios() throws SQLException {
        return servicioRepository.findAll();
    }

    /**
     * Obtiene un servicio por su ID
     * @param id ID del servicio
     * @return Servicio encontrado o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Servicio getServicioById(int id) throws SQLException {
        return servicioRepository.findById(id);
    }

    /**
     * Crea un nuevo servicio
     * @param servicio Servicio a crear
     * @return ID del servicio creado
     * @throws SQLException Error en la inserción
     */
    public int createServicio(Servicio servicio) throws SQLException {
        // Validaciones básicas
        validateServicio(servicio);
        return servicioRepository.save(servicio);
    }

    /**
     * Actualiza un servicio existente
     * @param servicio Servicio con los datos actualizados
     * @return true si se actualizó correctamente
     * @throws SQLException Error en la actualización
     */
    public boolean updateServicio(Servicio servicio) throws SQLException {
        // Validaciones básicas
        validateServicio(servicio);
        return servicioRepository.update(servicio);
    }

    /**
     * Elimina un servicio por su ID
     * @param id ID del servicio a eliminar
     * @return true si se eliminó correctamente
     * @throws SQLException Error en la eliminación
     */
    public boolean deleteServicio(int id) throws SQLException {
        return servicioRepository.delete(id);
    }

    /**
     * Valida los datos básicos de un servicio
     * @param servicio Servicio a validar
     */
    private void validateServicio(Servicio servicio) {
        if (servicio == null) {
            throw new IllegalArgumentException("El servicio no puede ser nulo");
        }
        if (servicio.getNombre() == null || servicio.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del servicio es requerido");
        }
        if (servicio.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
    }
}