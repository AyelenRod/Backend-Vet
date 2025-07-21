package com.vetfinder.service;

import com.vetfinder.model.Sexo;
import com.vetfinder.repository.SexoRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Servicio para la gestión de sexos
 * Contiene la lógica de negocio para operaciones con sexos
 */
public class SexoService {
    private final SexoRepository sexoRepository;

    public SexoService(SexoRepository sexoRepository) {
        this.sexoRepository = sexoRepository;
    }

    /**
     * Obtiene todos los sexos
     * @return Lista de todos los sexos
     * @throws SQLException Error en la consulta
     */
    public List<Sexo> getAllSexos() throws SQLException {
        return sexoRepository.findAll();
    }

    /**
     * Obtiene un sexo por su ID
     * @param id ID del sexo
     * @return Sexo encontrado o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Sexo getSexoById(int id) throws SQLException {
        return sexoRepository.findById(id);
    }

    /**
     * Crea un nuevo sexo
     * @param sexo Sexo a crear
     * @return ID del sexo creado
     * @throws SQLException Error en la inserción
     */
    public int createSexo(Sexo sexo) throws SQLException {
        // Validación básica
        if (sexo == null || sexo.getNombre() == null || sexo.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del sexo es requerido");
        }
        return sexoRepository.save(sexo);
    }

    /**
     * Actualiza un sexo existente
     * @param sexo Sexo con los datos actualizados
     * @return true si se actualizó correctamente
     * @throws SQLException Error en la actualización
     */
    public boolean updateSexo(Sexo sexo) throws SQLException {
        // Validación básica
        if (sexo == null || sexo.getNombre() == null || sexo.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del sexo es requerido");
        }
        return sexoRepository.update(sexo);
    }

    /**
     * Elimina un sexo por su ID
     * @param id ID del sexo a eliminar
     * @return true si se eliminó correctamente
     * @throws SQLException Error en la eliminación
     */
    public boolean deleteSexo(int id) throws SQLException {
        return sexoRepository.delete(id);
    }
}