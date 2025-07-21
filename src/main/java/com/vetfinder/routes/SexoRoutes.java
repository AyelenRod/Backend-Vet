package com.vetfinder.routes;

import io.javalin.Javalin;
import com.vetfinder.controller.SexoController;

/**
 * Configuración de rutas para la entidad Sexo
 * COMPLETO: Todos los endpoints para gestión de sexos
 */
public class SexoRoutes {
    private final SexoController sexoController;

    public SexoRoutes(SexoController sexoController) {
        this.sexoController = sexoController;
    }

    /**
     * Registra todas las rutas relacionadas con sexos
     * @param app Instancia de Javalin
     */
    public void register(Javalin app) {
        // ========== ENDPOINTS BÁSICOS CRUD ==========
        app.get("/api/sexos", sexoController::getAll);                    // Obtener todos los sexos
        app.get("/api/sexos/{id}", sexoController::getById);              // Obtener sexo por ID
        app.post("/api/sexos", sexoController::create);                   // Crear sexo
        app.put("/api/sexos/{id}", sexoController::update);               // Actualizar sexo
        app.delete("/api/sexos/{id}", sexoController::delete);            // Eliminar sexo
    }
}