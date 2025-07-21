package com.vetfinder.routes;

import io.javalin.Javalin;
import com.vetfinder.controller.RolController;

/**
 * Configuración de rutas para la entidad Rol
 * COMPLETO: Todos los endpoints para gestión de roles
 */
public class RolRoutes {
    private final RolController rolController;

    public RolRoutes(RolController rolController) {
        this.rolController = rolController;
    }

    /**
     * Registra todas las rutas relacionadas con roles
     * @param app Instancia de Javalin
     */
    public void register(Javalin app) {
        // ========== ENDPOINTS BÁSICOS CRUD ==========
        app.get("/api/roles", rolController::getAll);                    // Obtener todos los roles
        app.get("/api/roles/{id}", rolController::getById);              // Obtener rol por ID
        app.post("/api/roles", rolController::create);                   // Crear rol
        app.put("/api/roles/{id}", rolController::update);               // Actualizar rol
        app.delete("/api/roles/{id}", rolController::delete);            // Eliminar rol
    }
}