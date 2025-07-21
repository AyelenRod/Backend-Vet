package com.vetfinder.routes;

import io.javalin.Javalin;
import com.vetfinder.controller.UsuarioController;

/**
 * Configuración de rutas para la entidad Usuario
 * ORDEN CORREGIDO: Las rutas específicas VAN ANTES que las rutas con parámetros
 */
public class UsuarioRoutes {
    private final UsuarioController usuarioController;

    public UsuarioRoutes(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    /**
     * Registra todas las rutas relacionadas con usuarios
     * IMPORTANTE: El orden importa - rutas específicas primero
     * @param app Instancia de Javalin
     */
    public void register(Javalin app) {
        // ========== RUTAS ESPECÍFICAS PRIMERO (SIN PARÁMETROS) ==========
        app.get("/api/usuarios/veterinarios", usuarioController::getVeterinarios);     // DEBE IR ANTES que /{id}
        app.get("/api/usuarios/tutores", usuarioController::getTutores);               // DEBE IR ANTES que /{id}
        app.post("/api/usuarios/login", usuarioController::login);                     // DEBE IR ANTES que /{id}
        app.post("/api/usuarios/veterinario", usuarioController::createVeterinario);   // DEBE IR ANTES que /{id}
        app.post("/api/usuarios/tutor", usuarioController::createTutor);               // DEBE IR ANTES que /{id}

        // ========== RUTAS BÁSICAS CRUD (CON PARÁMETROS) ==========
        app.get("/api/usuarios", usuarioController::getAll);                    // Obtener todos los usuarios
        app.get("/api/usuarios/{id}", usuarioController::getById);              // Obtener usuario por ID - DESPUÉS de las específicas
        app.post("/api/usuarios", usuarioController::create);                   // Crear usuario genérico
        app.put("/api/usuarios/{id}", usuarioController::update);               // Actualizar usuario
        app.delete("/api/usuarios/{id}", usuarioController::delete);            // Eliminar usuario

        // ========== RUTAS CON PARÁMETROS ESPECÍFICOS AL FINAL ==========
        app.get("/api/usuarios/{id}/tipo", usuarioController::getTipoUsuario);         // Obtener tipo de usuario
    }
}