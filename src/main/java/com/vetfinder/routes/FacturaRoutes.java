package com.vetfinder.routes;

import io.javalin.Javalin;
import com.vetfinder.controller.FacturaController;

/**
 * Configuración de rutas para la entidad Factura
 * COMPLETO: Todos los endpoints para gestión de facturas
 */
public class FacturaRoutes {
    private final FacturaController facturaController;

    public FacturaRoutes(FacturaController facturaController) {
        this.facturaController = facturaController;
    }

    /**
     * Registra todas las rutas relacionadas con facturas
     * @param app Instancia de Javalin
     */
    public void register(Javalin app) {
        // ========== ENDPOINTS BÁSICOS CRUD ==========
        app.get("/api/facturas", facturaController::getAll);                    // Obtener todas las facturas
        app.get("/api/facturas/{id}", facturaController::getById);              // Obtener factura por ID
        app.post("/api/facturas", facturaController::create);                   // Crear factura
        app.put("/api/facturas/{id}", facturaController::update);               // Actualizar factura
        app.delete("/api/facturas/{id}", facturaController::delete);            // Eliminar factura

        // ========== ENDPOINTS ADICIONALES ESPECÍFICOS ==========
        app.get("/api/usuarios/{usuarioId}/facturas", facturaController::getByUsuario);    // Facturas de un usuario específico
    }
}