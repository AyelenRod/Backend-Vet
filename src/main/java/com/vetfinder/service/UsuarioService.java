package com.vetfinder.service;

import com.vetfinder.model.Usuario;
import com.vetfinder.repository.UsuarioRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Servicio para la gestión de usuarios
 * Contiene la lógica de negocio para operaciones con usuarios
 * COMPLETO: Incluye métodos getVeterinarios() y getTutores() que faltaban
 */
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    // Constantes para roles
    private static final int ROL_VETERINARIO = 1;
    private static final int ROL_TUTOR = 2;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Obtiene todos los usuarios
     * @return Lista de todos los usuarios
     * @throws SQLException Error en la consulta
     */
    public List<Usuario> getAllUsuarios() throws SQLException {
        return usuarioRepository.findAll();
    }

    /**
     * Obtiene un usuario por su ID
     * @param id ID del usuario
     * @return Usuario encontrado o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Usuario getUsuarioById(int id) throws SQLException {
        return usuarioRepository.findById(id);
    }

    /**
     * Obtiene un usuario por su correo electrónico - MÉTODO QUE FALTABA
     * @param correo Correo del usuario
     * @return Usuario encontrado o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Usuario getUsuarioByCorreo(String correo) throws SQLException {
        return usuarioRepository.findByCorreo(correo);
    }

    /**
     * Obtiene todos los veterinarios - MÉTODO QUE FALTABA
     * @return Lista de usuarios con rol veterinario
     * @throws SQLException Error en la consulta
     */
    public List<Usuario> getVeterinarios() throws SQLException {
        return usuarioRepository.findByRol(ROL_VETERINARIO);
    }

    /**
     * Obtiene todos los tutores de mascotas - MÉTODO QUE FALTABA
     * @return Lista de usuarios con rol tutor
     * @throws SQLException Error en la consulta
     */
    public List<Usuario> getTutores() throws SQLException {
        return usuarioRepository.findByRol(ROL_TUTOR);
    }

    /**
     * Crea un nuevo usuario
     * @param usuario Usuario a crear
     * @return ID del usuario creado
     * @throws SQLException Error en la inserción
     */
    public int createUsuario(Usuario usuario) throws SQLException {
        // Validaciones básicas
        validateUsuario(usuario);

        // Validaciones específicas por rol
        validateUsuarioByRol(usuario);

        return usuarioRepository.save(usuario);
    }

    /**
     * Actualiza un usuario existente
     * @param usuario Usuario con los datos actualizados
     * @return true si se actualizó correctamente
     * @throws SQLException Error en la actualización
     */
    public boolean updateUsuario(Usuario usuario) throws SQLException {
        // Validaciones básicas
        validateUsuario(usuario);

        // Validaciones específicas por rol
        validateUsuarioByRol(usuario);

        return usuarioRepository.update(usuario);
    }

    /**
     * Elimina un usuario por su ID
     * @param id ID del usuario a eliminar
     * @return true si se eliminó correctamente
     * @throws SQLException Error en la eliminación
     */
    public boolean deleteUsuario(int id) throws SQLException {
        return usuarioRepository.delete(id);
    }

    /**
     * Valida los datos básicos de un usuario
     * @param usuario Usuario a validar
     */
    private void validateUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es requerido");
        }
        if (usuario.getApellidos() == null || usuario.getApellidos().trim().isEmpty()) {
            throw new IllegalArgumentException("Los apellidos son requeridos");
        }
        if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es requerido");
        }
        if (usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida");
        }
        if (usuario.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es requerida");
        }

        // Validar que el rol sea válido (solo 1 o 2)
        if (usuario.getIdRol() != ROL_VETERINARIO && usuario.getIdRol() != ROL_TUTOR) {
            throw new IllegalArgumentException("El rol debe ser 1 (Veterinario) o 2 (Tutor de Mascota)");
        }
    }

    /**
     * Valida los datos específicos según el rol del usuario
     * @param usuario Usuario a validar
     */
    private void validateUsuarioByRol(Usuario usuario) {
        if (usuario.getIdRol() == ROL_VETERINARIO) {
            // Validaciones para veterinarios
            if (usuario.getDescripcion() == null || usuario.getDescripcion().trim().isEmpty()) {
                throw new IllegalArgumentException("La descripción es requerida para veterinarios");
            }
            if (usuario.getCedula() <= 0) {
                throw new IllegalArgumentException("La cédula es requerida para veterinarios");
            }
        } else if (usuario.getIdRol() == ROL_TUTOR) {
            // Para tutores, establecer descripción y cédula como null
            usuario.setDescripcion(null);
            usuario.setCedula(0);
        }
    }

    /**
     * Verifica si un usuario es veterinario
     * @param usuario Usuario a verificar
     * @return true si es veterinario
     */
    public boolean isVeterinario(Usuario usuario) {
        return usuario != null && usuario.getIdRol() == ROL_VETERINARIO;
    }

    /**
     * Verifica si un usuario es tutor de mascota
     * @param usuario Usuario a verificar
     * @return true si es tutor
     */
    public boolean isTutor(Usuario usuario) {
        return usuario != null && usuario.getIdRol() == ROL_TUTOR;
    }
}