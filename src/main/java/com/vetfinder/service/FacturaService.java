package com.vetfinder.service;

import com.vetfinder.model.Factura;
import com.vetfinder.repository.FacturaRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Servicio para la gestión de facturas
 * Contiene la lógica de negocio para operaciones con facturas
 */
public class FacturaService {
    private final FacturaRepository facturaRepository;

    public FacturaService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    /**
     * Obtiene todas las facturas
     * @return Lista de todas las facturas
     * @throws SQLException Error en la consulta
     */
    public List<Factura> getAllFacturas() throws SQLException {
        return facturaRepository.findAll();
    }

    /**
     * Obtiene una factura por su ID
     * @param id ID de la factura
     * @return Factura encontrada o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Factura getFacturaById(int id) throws SQLException {
        return facturaRepository.findById(id);
    }

    /**
     * Obtiene todas las facturas de un usuario
     * @param idUsuario ID del usuario
     * @return Lista de facturas del usuario
     * @throws SQLException Error en la consulta
     */
    public List<Factura> getFacturasByUsuario(int idUsuario) throws SQLException {
        return facturaRepository.findByUsuario(idUsuario);
    }

    /**
     * Crea una nueva factura
     * @param factura Factura a crear
     * @return ID de la factura creada
     * @throws SQLException Error en la inserción
     */
    public int createFactura(Factura factura) throws SQLException {
        // Validaciones básicas
        validateFactura(factura);

        // Establecer fecha actual si no se especifica
        if (factura.getFechaFactura() == null) {
            factura.setFechaFactura(LocalDate.now());
        }

        return facturaRepository.save(factura);
    }

    /**
     * Actualiza una factura existente
     * @param factura Factura con los datos actualizados
     * @return true si se actualizó correctamente
     * @throws SQLException Error en la actualización
     */
    public boolean updateFactura(Factura factura) throws SQLException {
        // Validaciones básicas
        validateFactura(factura);
        return facturaRepository.update(factura);
    }

    /**
     * Elimina una factura por su ID
     * @param id ID de la factura a eliminar
     * @return true si se eliminó correctamente
     * @throws SQLException Error en la eliminación
     */
    public boolean deleteFactura(int id) throws SQLException {
        return facturaRepository.delete(id);
    }

    /**
     * Valida los datos básicos de una factura
     * @param factura Factura a validar
     */
    private void validateFactura(Factura factura) {
        if (factura == null) {
            throw new IllegalArgumentException("La factura no puede ser nula");
        }
        if (factura.getIdUsuario() <= 0) {
            throw new IllegalArgumentException("El ID del usuario es requerido");
        }
        if (factura.getIdServicio() <= 0) {
            throw new IllegalArgumentException("El ID del servicio es requerido");
        }
        if (factura.getIdCita() <= 0) {
            throw new IllegalArgumentException("El ID de la cita es requerido");
        }
        if (factura.getTotal() <= 0) {
            throw new IllegalArgumentException("El total debe ser mayor a 0");
        }
    }
}