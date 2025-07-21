package com.vetfinder.routes;

import io.javalin.Javalin;
import com.vetfinder.controller.EspecialidadController;

/**
 * Configuración de rutas para la entidad Especialidad
 * COMPLETO: Todos los endpoints para gestión de especialidades
 */
public class EspecialidadRoutes {
    private final EspecialidadController especialidadController;

    public EspecialidadRoutes(EspecialidadController especialidadController) {
        this.especialidadController = especialidadController;
    }

    /**
     * Registra todas las rutas relacionadas con especialidades
     * @param app Instancia de Javalin
     */
    public void register(Javalin app) {
        // ========== ENDPOINTS BÁSICOS CRUD ==========
        app.get("/api/especialidades", especialidadController::getAll);                    // Obtener todas las especialidades
        app.get("/api/especialidades/{id}", especialidadController::getById);              // Obtener especialidad por ID
        app.post("/api/especialidades", especialidadController::create);                   // Crear especialidad
        app.put("/api/especialidades/{id}", especialidadController::update);               // Actualizar especialidad
        app.delete("/api/especialidades/{id}", especialidadController::delete);            // Eliminar especialidad
    }
}