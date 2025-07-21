package com.vetfinder.routes;

import io.javalin.Javalin;
import com.vetfinder.controller.EstadisticasController;

/**
 * Configuración de rutas para estadísticas
 * Define los endpoints para obtener datos de gráficas y reportes
 */
public class EstadisticasRoutes {
    private final EstadisticasController estadisticasController;

    public EstadisticasRoutes(EstadisticasController estadisticasController) {
        this.estadisticasController = estadisticasController;
    }

    /**
     * Registra todas las rutas relacionadas con estadísticas
     * @param app Instancia de Javalin
     */
    public void register(Javalin app) {
        // ========== ENDPOINTS PARA GRÁFICAS ==========

        // GET /api/estadisticas/horarios-concurridos - Datos para gráfica de horarios
        app.get("/api/estadisticas/horarios-concurridos", estadisticasController::getHorariosConcurridos);

        // GET /api/estadisticas/servicios-solicitados - Datos para gráfica de servicios
        app.get("/api/estadisticas/servicios-solicitados", estadisticasController::getServiciosSolicitados);

        // GET /api/estadisticas/resumen - Datos generales para dashboard
        app.get("/api/estadisticas/resumen", estadisticasController::getResumenEstadisticas);
    }
}
