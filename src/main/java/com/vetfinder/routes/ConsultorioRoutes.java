package com.vetfinder.routes;

import io.javalin.Javalin;
import com.vetfinder.controller.ConsultorioController;

/**
 * Configuración de rutas para la entidad Consultorio
 * COMPLETO: Todos los endpoints para gestión de consultorios
 */
public class ConsultorioRoutes {
    private final ConsultorioController consultorioController;

    public ConsultorioRoutes(ConsultorioController consultorioController) {
        this.consultorioController = consultorioController;
    }

    /**
     * Registra todas las rutas relacionadas con consultorios
     * @param app Instancia de Javalin
     */
    public void register(Javalin app) {
        // ========== ENDPOINTS BÁSICOS CRUD ==========
        app.get("/api/consultorios", consultorioController::getAll);                    // Obtener todos los consultorios
        app.get("/api/consultorios/{id}", consultorioController::getById);              // Obtener consultorio por ID
        app.post("/api/consultorios", consultorioController::create);                   // Crear consultorio
        app.put("/api/consultorios/{id}", consultorioController::update);               // Actualizar consultorio
        app.delete("/api/consultorios/{id}", consultorioController::delete);            // Eliminar consultorio
    }
}