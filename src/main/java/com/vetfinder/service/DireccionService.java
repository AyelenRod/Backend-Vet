package com.vetfinder.service;

import com.vetfinder.model.Direccion;
import com.vetfinder.repository.DireccionRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Servicio para la gestión de direcciones
 * Contiene la lógica de negocio para operaciones con direcciones
 * No requiere modificaciones ya que solo maneja validaciones de calle
 */
public class DireccionService {
    private final DireccionRepository direccionRepository;

    public DireccionService(DireccionRepository direccionRepository) {
        this.direccionRepository = direccionRepository;
    }

    /**
     * Obtiene todas las direcciones
     * @return Lista de todas las direcciones
     * @throws SQLException Error en la consulta
     */
    public List<Direccion> getAllDirecciones() throws SQLException {
        return direccionRepository.findAll();
    }

    /**
     * Obtiene una dirección por su ID
     * @param id ID de la dirección
     * @return Dirección encontrada o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Direccion getDireccionById(int id) throws SQLException {
        return direccionRepository.findById(id);
    }

    /**
     * Crea una nueva dirección
     * @param direccion Dirección a crear
     * @return ID de la dirección creada
     * @throws SQLException Error en la inserción
     */
    public int createDireccion(Direccion direccion) throws SQLException {
        // Validación básica (sin cambios)
        if (direccion == null || direccion.getCalle() == null || direccion.getCalle().trim().isEmpty()) {
            throw new IllegalArgumentException("La calle es requerida");
        }
        return direccionRepository.save(direccion);
    }

    /**
     * Actualiza una dirección existente
     * @param direccion Dirección con los datos actualizados
     * @return true si se actualizó correctamente
     * @throws SQLException Error en la actualización
     */
    public boolean updateDireccion(Direccion direccion) throws SQLException {
        // Validación básica (sin cambios)
        if (direccion == null || direccion.getCalle() == null || direccion.getCalle().trim().isEmpty()) {
            throw new IllegalArgumentException("La calle es requerida");
        }
        return direccionRepository.update(direccion);
    }

    /**
     * Elimina una dirección por su ID
     * @param id ID de la dirección a eliminar
     * @return true si se eliminó correctamente
     * @throws SQLException Error en la eliminación
     */
    public boolean deleteDireccion(int id) throws SQLException {
        return direccionRepository.delete(id);
    }
}
