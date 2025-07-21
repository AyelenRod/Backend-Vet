package com.vetfinder.routes;

import io.javalin.Javalin;
import com.vetfinder.controller.DireccionController;

/**
 * Configuración de rutas para la entidad Direccion
 * COMPLETO: Todos los endpoints para gestión de direcciones
 */
public class DireccionRoutes {
    private final DireccionController direccionController;

    public DireccionRoutes(DireccionController direccionController) {
        this.direccionController = direccionController;
    }

    /**
     * Registra todas las rutas relacionadas con direcciones
     * @param app Instancia de Javalin
     */
    public void register(Javalin app) {
        // ========== ENDPOINTS BÁSICOS CRUD ==========
        app.get("/api/direcciones", direccionController::getAll);                    // Obtener todas las direcciones
        app.get("/api/direcciones/{id}", direccionController::getById);              // Obtener dirección por ID
        app.post("/api/direcciones", direccionController::create);                   // Crear dirección
        app.put("/api/direcciones/{id}", direccionController::update);               // Actualizar dirección
        app.delete("/api/direcciones/{id}", direccionController::delete);            // Eliminar dirección
    }
}
