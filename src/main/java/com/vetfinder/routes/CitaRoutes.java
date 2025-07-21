package com.vetfinder.routes;

import io.javalin.Javalin;
import com.vetfinder.controller.CitaController;

/**
 * Configuración de rutas para la entidad Cita
 * COMPLETO: Todos los endpoints para gestión de citas
 */
public class CitaRoutes {
    private final CitaController citaController;

    public CitaRoutes(CitaController citaController) {
        this.citaController = citaController;
    }

    /**
     * Registra todas las rutas relacionadas con citas
     * @param app Instancia de Javalin
     */
    public void register(Javalin app) {
        // ========== ENDPOINTS BÁSICOS CRUD ==========
        app.get("/api/citas", citaController::getAll);                    // Obtener todas las citas
        app.get("/api/citas/{id}", citaController::getById);              // Obtener cita por ID
        app.post("/api/citas", citaController::create);                   // Crear cita
        app.put("/api/citas/{id}", citaController::update);               // Actualizar cita
        app.delete("/api/citas/{id}", citaController::delete);            // Eliminar cita

        // ========== ENDPOINTS ADICIONALES ESPECÍFICOS ==========
        app.get("/api/mascotas/{mascotaId}/citas", citaController::getByMascota);           // Citas de una mascota específica
        app.get("/api/veterinarios/{veterinarioId}/citas", citaController::getByVeterinario);   // Citas de un veterinario específico
        app.patch("/api/citas/{id}/estado", citaController::updateEstado);                 // Actualizar solo el estado de una cita
    }
}