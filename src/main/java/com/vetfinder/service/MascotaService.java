package com.vetfinder.service;

import com.vetfinder.model.Mascota;
import com.vetfinder.repository.MascotaRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Servicio para la gestión de mascotas
 * Contiene la lógica de negocio para operaciones con mascotas
 */
public class MascotaService {
    private final MascotaRepository mascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    /**
     * Obtiene todas las mascotas
     * @return Lista de todas las mascotas
     * @throws SQLException Error en la consulta
     */
    public List<Mascota> getAllMascotas() throws SQLException {
        return mascotaRepository.findAll();
    }

    /**
     * Obtiene una mascota por su ID
     * @param id ID de la mascota
     * @return Mascota encontrada o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Mascota getMascotaById(int id) throws SQLException {
        return mascotaRepository.findById(id);
    }

    /**
     * Obtiene todas las mascotas de un usuario
     * @param idUsuario ID del usuario
     * @return Lista de mascotas del usuario
     * @throws SQLException Error en la consulta
     */
    public List<Mascota> getMascotasByUsuario(int idUsuario) throws SQLException {
        return mascotaRepository.findByUsuario(idUsuario);
    }

    /**
     * Crea una nueva mascota
     * @param mascota Mascota a crear
     * @return ID de la mascota creada
     * @throws SQLException Error en la inserción
     */
    public int createMascota(Mascota mascota) throws SQLException {
        // Validaciones básicas
        validateMascota(mascota);
        return mascotaRepository.save(mascota);
    }

    /**
     * Actualiza una mascota existente
     * @param mascota Mascota con los datos actualizados
     * @return true si se actualizó correctamente
     * @throws SQLException Error en la actualización
     */
    public boolean updateMascota(Mascota mascota) throws SQLException {
        // Validaciones básicas
        validateMascota(mascota);
        return mascotaRepository.update(mascota);
    }

    /**
     * Elimina una mascota por su ID
     * @param id ID de la mascota a eliminar
     * @return true si se eliminó correctamente
     * @throws SQLException Error en la eliminación
     */
    public boolean deleteMascota(int id) throws SQLException {
        return mascotaRepository.delete(id);
    }

    /**
     * Valida los datos básicos de una mascota
     * @param mascota Mascota a validar
     */
    private void validateMascota(Mascota mascota) {
        if (mascota == null) {
            throw new IllegalArgumentException("La mascota no puede ser nula");
        }
        if (mascota.getNombre() == null || mascota.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la mascota es requerido");
        }
        if (mascota.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es requerida");
        }
        if (mascota.getIdUsuario() <= 0) {
            throw new IllegalArgumentException("El ID del usuario es requerido");
        }
    }
}