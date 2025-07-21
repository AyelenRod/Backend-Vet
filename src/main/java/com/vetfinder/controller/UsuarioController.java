package com.vetfinder.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.vetfinder.service.UsuarioService;
import com.vetfinder.util.ApiResponse;
import com.vetfinder.model.Usuario;

/**
 * Controlador para manejar las peticiones HTTP relacionadas con usuarios
 * COMPLETO: Incluye todos los métodos existentes + nuevos específicos por rol + login
 */
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // ========== MÉTODOS BÁSICOS CRUD (YA EXISTÍAN) ==========

    /**
     * GET /usuarios - Obtiene todos los usuarios
     */
    public void getAll(Context ctx) {
        try {
            var usuarios = usuarioService.getAllUsuarios();
            ctx.json(ApiResponse.success("Usuarios obtenidos correctamente", usuarios));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener usuarios: " + e.getMessage()));
        }
    }

    /**
     * GET /usuarios/{id} - Obtiene un usuario por ID
     */
    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var usuario = usuarioService.getUsuarioById(id);

            if (usuario != null) {
                ctx.json(ApiResponse.success("Usuario encontrado", usuario));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Usuario"));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("ID inválido"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener usuario: " + e.getMessage()));
        }
    }

    /**
     * POST /usuarios - Crea un nuevo usuario genérico
     */
    public void create(Context ctx) {
        try {
            var usuario = ctx.bodyAsClass(Usuario.class);
            String id = String.valueOf(usuarioService.createUsuario(usuario));
            usuario.setIdUsuario(Integer.parseInt(id));

            ctx.status(HttpStatus.CREATED)
                    .json(ApiResponse.success("Usuario creado correctamente", usuario));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al crear usuario: " + e.getMessage()));
        }
    }

    /**
     * PUT /usuarios/{id} - Actualiza un usuario existente
     */
    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var usuario = ctx.bodyAsClass(Usuario.class);
            usuario.setIdUsuario(Integer.parseInt(String.valueOf(id)));

            boolean updated = usuarioService.updateUsuario(usuario);
            if (updated) {
                ctx.json(ApiResponse.success("Usuario actualizado correctamente", usuario));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Usuario"));
            }
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al actualizar usuario: " + e.getMessage()));
        }
    }

    /**
     * DELETE /usuarios/{id} - Elimina un usuario
     */
    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = usuarioService.deleteUsuario(id);

            if (deleted) {
                ctx.json(ApiResponse.success("Usuario eliminado correctamente"));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Usuario"));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("ID inválido"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al eliminar usuario: " + e.getMessage()));
        }
    }

    // ========== MÉTODOS NUEVOS ESPECÍFICOS POR ROL ==========

    /**
     * GET /usuarios/veterinarios - Obtiene todos los veterinarios
     */
    public void getVeterinarios(Context ctx) {
        try {
            var veterinarios = usuarioService.getVeterinarios();
            ctx.json(ApiResponse.success("Veterinarios obtenidos correctamente", veterinarios));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener veterinarios: " + e.getMessage()));
        }
    }

    /**
     * GET /usuarios/tutores - Obtiene todos los tutores de mascotas
     */
    public void getTutores(Context ctx) {
        try {
            var tutores = usuarioService.getTutores();
            ctx.json(ApiResponse.success("Tutores obtenidos correctamente", tutores));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener tutores: " + e.getMessage()));
        }
    }

    /**
     * POST /usuarios/veterinario - Crea un nuevo veterinario
     */
    public void createVeterinario(Context ctx) {
        try {
            var usuario = ctx.bodyAsClass(Usuario.class);
            // Forzar rol de veterinario
            usuario.setIdRol(1);

            String id = String.valueOf(usuarioService.createUsuario(usuario));
            usuario.setIdUsuario(Integer.parseInt(id));

            ctx.status(HttpStatus.CREATED)
                    .json(ApiResponse.success("Veterinario creado correctamente", usuario));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al crear veterinario: " + e.getMessage()));
        }
    }

    /**
     * POST /usuarios/tutor - Crea un nuevo tutor de mascota
     */
    public void createTutor(Context ctx) {
        try {
            var usuario = ctx.bodyAsClass(Usuario.class);
            // Forzar rol de tutor
            usuario.setIdRol(2);

            String id = String.valueOf(usuarioService.createUsuario(usuario));
            usuario.setIdUsuario(Integer.parseInt(id));

            ctx.status(HttpStatus.CREATED)
                    .json(ApiResponse.success("Tutor creado correctamente", usuario));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al crear tutor: " + e.getMessage()));
        }
    }

    /**
     * GET /usuarios/{id}/tipo - Obtiene el tipo de usuario (para frontend)
     */
    public void getTipoUsuario(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var usuario = usuarioService.getUsuarioById(id);

            if (usuario != null) {
                String tipoUsuario = usuario.getIdRol() == 1 ? "veterinario" : "tutor";
                ctx.json(ApiResponse.success("Tipo de usuario obtenido",
                        new TipoUsuarioResponse(usuario.getIdRol(), tipoUsuario)));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Usuario"));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("ID inválido"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener tipo de usuario: " + e.getMessage()));
        }
    }

    // ========== MÉTODO PARA LOGIN ==========

    /**
     * POST /usuarios/login - Autenticación de usuarios
     */
    public void login(Context ctx) {
        try {
            var loginRequest = ctx.bodyAsClass(LoginRequest.class);

            // Buscar usuario por correo
            var usuario = usuarioService.getUsuarioByCorreo(loginRequest.getCorreo());

            if (usuario != null && usuario.getContrasena().equals(loginRequest.getContrasena())) {
                // Login exitoso
                String tipoUsuario = usuario.getIdRol() == 1 ? "veterinario" : "tutor";
                var loginResponse = new LoginResponse(
                        usuario.getIdUsuario(),
                        usuario.getNombre(),
                        usuario.getApellidos(),
                        usuario.getCorreo(),
                        usuario.getIdRol(),
                        tipoUsuario
                );
                ctx.json(ApiResponse.success("Login exitoso", loginResponse));
            } else {
                ctx.status(HttpStatus.UNAUTHORIZED)
                        .json(ApiResponse.error("Credenciales incorrectas"));
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error en el login: " + e.getMessage()));
        }
    }

    // ========== CLASES AUXILIARES ==========

    /**
     * Clase para respuesta de tipo de usuario
     */
    public static class TipoUsuarioResponse {
        private int idRol;
        private String tipoUsuario;

        public TipoUsuarioResponse(int idRol, String tipoUsuario) {
            this.idRol = idRol;
            this.tipoUsuario = tipoUsuario;
        }

        public int getIdRol() {
            return idRol;
        }

        public void setIdRol(int idRol) {
            this.idRol = idRol;
        }

        public String getTipoUsuario() {
            return tipoUsuario;
        }

        public void setTipoUsuario(String tipoUsuario) {
            this.tipoUsuario = tipoUsuario;
        }
    }

    /**
     * Clase para petición de login
     */
    public static class LoginRequest {
        private String correo;
        private String contrasena;

        public LoginRequest() {}

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public String getContrasena() {
            return contrasena;
        }

        public void setContrasena(String contrasena) {
            this.contrasena = contrasena;
        }
    }

    /**
     * Clase para respuesta de login
     */
    public static class LoginResponse {
        private int idUsuario;
        private String nombre;
        private String apellidos;
        private String correo;
        private int idRol;
        private String tipoUsuario;

        public LoginResponse(int idUsuario, String nombre, String apellidos, String correo, int idRol, String tipoUsuario) {
            this.idUsuario = idUsuario;
            this.nombre = nombre;
            this.apellidos = apellidos;
            this.correo = correo;
            this.idRol = idRol;
            this.tipoUsuario = tipoUsuario;
        }

        // Getters y setters
        public int getIdUsuario() { return idUsuario; }
        public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getApellidos() { return apellidos; }
        public void setApellidos(String apellidos) { this.apellidos = apellidos; }

        public String getCorreo() { return correo; }
        public void setCorreo(String correo) { this.correo = correo; }

        public int getIdRol() { return idRol; }
        public void setIdRol(int idRol) { this.idRol = idRol; }

        public String getTipoUsuario() { return tipoUsuario; }
        public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }
    }
}