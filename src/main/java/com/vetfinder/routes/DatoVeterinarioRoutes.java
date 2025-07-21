package com.vetfinder.routes;

import io.javalin.Javalin;
import com.vetfinder.controller.DatoVeterinarioController;

/**
 * Configuración de rutas para la entidad DatoVeterinario
 * COMPLETO: Todos los endpoints para gestión de datos veterinarios
 */
public class DatoVeterinarioRoutes {
    private final DatoVeterinarioController datoVeterinarioController;

    public DatoVeterinarioRoutes(DatoVeterinarioController datoVeterinarioController) {
        this.datoVeterinarioController = datoVeterinarioController;
    }

    /**
     * Registra todas las rutas relacionadas con datos veterinarios
     * @param app Instancia de Javalin
     */
    public void register(Javalin app) {
        // ========== ENDPOINTS BÁSICOS CRUD ==========
        app.get("/api/datos-veterinarios", datoVeterinarioController::getAll);                    // Obtener todos los datos veterinarios
        app.get("/api/datos-veterinarios/{id}", datoVeterinarioController::getById);              // Obtener dato veterinario por ID
        app.post("/api/datos-veterinarios", datoVeterinarioController::create);                   // Crear dato veterinario
        app.put("/api/datos-veterinarios/{id}", datoVeterinarioController::update);               // Actualizar dato veterinario
        app.delete("/api/datos-veterinarios/{id}", datoVeterinarioController::delete);            // Eliminar dato veterinario

        // ========== ENDPOINTS ADICIONALES ESPECÍFICOS ==========
        app.get("/api/usuarios/{usuarioId}/datos-veterinarios", datoVeterinarioController::getByUsuario);    // Datos veterinarios de un usuario específico
    }
}