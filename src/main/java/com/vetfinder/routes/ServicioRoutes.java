package com.vetfinder.routes;

import io.javalin.Javalin;
import com.vetfinder.controller.ServicioController;

/**
 * Configuración de rutas para la entidad Servicio
 * COMPLETO: Todos los endpoints para gestión de servicios veterinarios
 */
public class ServicioRoutes {
    private final ServicioController servicioController;

    public ServicioRoutes(ServicioController servicioController) {
        this.servicioController = servicioController;
    }

    /**
     * Registra todas las rutas relacionadas con servicios
     * @param app Instancia de Javalin
     */
    public void register(Javalin app) {
        // ========== ENDPOINTS BÁSICOS CRUD ==========
        app.get("/api/servicios", servicioController::getAll);                    // Obtener todos los servicios
        app.get("/api/servicios/{id}", servicioController::getById);              // Obtener servicio por ID
        app.post("/api/servicios", servicioController::create);                   // Crear servicio
        app.put("/api/servicios/{id}", servicioController::update);               // Actualizar servicio
        app.delete("/api/servicios/{id}", servicioController::delete);            // Eliminar servicio
    }
}