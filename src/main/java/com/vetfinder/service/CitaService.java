package com.vetfinder.service;

import com.vetfinder.model.Cita;
import com.vetfinder.repository.CitaRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Servicio para la gestión de citas
 * Contiene la lógica de negocio para operaciones con citas
 */
public class CitaService {
    private final CitaRepository citaRepository;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    /**
     * Obtiene todas las citas
     * @return Lista de todas las citas
     * @throws SQLException Error en la consulta
     */
    public List<Cita> getAllCitas() throws SQLException {
        return citaRepository.findAll();
    }

    /**
     * Obtiene una cita por su ID
     * @param id ID de la cita
     * @return Cita encontrada o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Cita getCitaById(int id) throws SQLException {
        return citaRepository.findById(id);
    }

    /**
     * Obtiene todas las citas de una mascota
     * @param idMascota ID de la mascota
     * @return Lista de citas de la mascota
     * @throws SQLException Error en la consulta
     */
    public List<Cita> getCitasByMascota(int idMascota) throws SQLException {
        return citaRepository.findByMascota(idMascota);
    }

    /**
     * Obtiene todas las citas de un veterinario
     * @param idDatoVeterinario ID del dato veterinario
     * @return Lista de citas del veterinario
     * @throws SQLException Error en la consulta
     */
    public List<Cita> getCitasByVeterinario(int idDatoVeterinario) throws SQLException {
        return citaRepository.findByVeterinario(idDatoVeterinario);
    }

    /**
     * Crea una nueva cita
     * @param cita Cita a crear
     * @return ID de la cita creada
     * @throws SQLException Error en la inserción
     */
    public int createCita(Cita cita) throws SQLException {
        // Validaciones básicas
        validateCita(cita);

        // Establecer estado por defecto si no se especifica
        if (cita.getEstado() == null || cita.getEstado().trim().isEmpty()) {
            cita.setEstado("Pendiente");
        }

        return citaRepository.save(cita);
    }

    /**
     * Actualiza una cita existente
     * @param cita Cita con los datos actualizados
     * @return true si se actualizó correctamente
     * @throws SQLException Error en la actualización
     */
    public boolean updateCita(Cita cita) throws SQLException {
        // Validaciones básicas
        validateCita(cita);
        return citaRepository.update(cita);
    }

    /**
     * Elimina una cita por su ID
     * @param id ID de la cita a eliminar
     * @return true si se eliminó correctamente
     * @throws SQLException Error en la eliminación
     */
    public boolean deleteCita(int id) throws SQLException {
        return citaRepository.delete(id);
    }

    /**
     * Actualiza el estado de una cita
     * @param idCita ID de la cita
     * @param nuevoEstado Nuevo estado (Pendiente, Aceptada, Rechazada)
     * @return true si se actualizó correctamente
     * @throws SQLException Error en la actualización
     */
    public boolean updateEstadoCita(int idCita, String nuevoEstado) throws SQLException {
        // Validar estados permitidos
        if (!isEstadoValido(nuevoEstado)) {
            throw new IllegalArgumentException("Estado no válido. Estados permitidos: Pendiente, Aceptada, Rechazada");
        }

        Cita cita = citaRepository.findById(idCita);
        if (cita == null) {
            return false;
        }

        cita.setEstado(nuevoEstado);
        return citaRepository.update(cita);
    }

    /**
     * Valida los datos básicos de una cita
     * @param cita Cita a validar
     */
    private void validateCita(Cita cita) {
        if (cita == null) {
            throw new IllegalArgumentException("La cita no puede ser nula");
        }
        if (cita.getFecha() == null) {
            throw new IllegalArgumentException("La fecha es requerida");
        }
        if (cita.getFecha().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no puede ser en el pasado");
        }
        if (cita.getHora() == null) {
            throw new IllegalArgumentException("La hora es requerida");
        }
        if (cita.getIdServicio() <= 0) {
            throw new IllegalArgumentException("El ID del servicio es requerido");
        }
        if (cita.getIdMascota() <= 0) {
            throw new IllegalArgumentException("El ID de la mascota es requerido");
        }
        if (cita.getIdDatoVeterinario() <= 0) {
            throw new IllegalArgumentException("El ID del dato veterinario es requerido");
        }
        if (cita.getEstado() != null && !isEstadoValido(cita.getEstado())) {
            throw new IllegalArgumentException("Estado no válido. Estados permitidos: Pendiente, Aceptada, Rechazada");
        }
    }

    /**
     * Verifica si un estado es válido
     * @param estado Estado a verificar
     * @return true si es válido
     */
    private boolean isEstadoValido(String estado) {
        return estado != null && (
                estado.equals("Pendiente") ||
                        estado.equals("Aceptada") ||
                        estado.equals("Rechazada")
        );
    }
}