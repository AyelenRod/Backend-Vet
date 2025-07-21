package com.vetfinder.routes;

import io.javalin.Javalin;
import com.vetfinder.controller.MascotaController;

/**
 * Configuración de rutas para la entidad Mascota
 * COMPLETO: Todos los endpoints para gestión de mascotas
 */
public class MascotaRoutes {
    private final MascotaController mascotaController;

    public MascotaRoutes(MascotaController mascotaController) {
        this.mascotaController = mascotaController;
    }

    /**
     * Registra todas las rutas relacionadas con mascotas
     * @param app Instancia de Javalin
     */
    public void register(Javalin app) {
        // ========== ENDPOINTS BÁSICOS CRUD ==========
        app.get("/api/mascotas", mascotaController::getAll);                    // Obtener todas las mascotas
        app.get("/api/mascotas/{id}", mascotaController::getById);              // Obtener mascota por ID
        app.post("/api/mascotas", mascotaController::create);                   // Crear mascota
        app.put("/api/mascotas/{id}", mascotaController::update);               // Actualizar mascota
        app.delete("/api/mascotas/{id}", mascotaController::delete);            // Eliminar mascota

        // ========== ENDPOINTS ADICIONALES ESPECÍFICOS ==========
        app.get("/api/usuarios/{usuarioId}/mascotas", mascotaController::getByUsuario);    // Mascotas de un usuario específico
    }
}