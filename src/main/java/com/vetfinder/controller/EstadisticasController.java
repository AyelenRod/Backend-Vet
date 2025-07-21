package com.vetfinder.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.vetfinder.service.EstadisticasService;
import com.vetfinder.util.ApiResponse;

/**
 * Controlador para manejar las peticiones HTTP relacionadas con estadísticas
 * Proporciona endpoints para obtener datos de gráficas y reportes
 */
public class EstadisticasController {
    private final EstadisticasService estadisticasService;

    public EstadisticasController(EstadisticasService estadisticasService) {
        this.estadisticasService = estadisticasService;
    }

    /**
     * GET /api/estadisticas/horarios-concurridos - Obtiene estadísticas de horarios más concurridos
     * Retorna datos para gráfica de barras con franjas horarias y cantidad de citas
     */
    public void getHorariosConcurridos(Context ctx) {
        try {
            var estadisticas = estadisticasService.getHorariosConcurridos();
            ctx.json(ApiResponse.success("Estadísticas de horarios obtenidas", estadisticas));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener estadísticas de horarios: " + e.getMessage()));
        }
    }

    /**
     * GET /api/estadisticas/servicios-solicitados - Obtiene estadísticas de servicios más solicitados
     * Retorna datos para gráfica de barras con tipos de servicio y cantidad de solicitudes
     */
    public void getServiciosSolicitados(Context ctx) {
        try {
            var estadisticas = estadisticasService.getServiciosSolicitados();
            ctx.json(ApiResponse.success("Estadísticas de servicios obtenidas", estadisticas));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener estadísticas de servicios: " + e.getMessage()));
        }
    }

    /**
     * GET /api/estadisticas/resumen - Obtiene un resumen general de estadísticas
     * Útil para dashboard principal
     */
    public void getResumenEstadisticas(Context ctx) {
        try {
            var resumen = estadisticasService.getResumenEstadisticas();
            ctx.json(ApiResponse.success("Resumen de estadísticas obtenido", resumen));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener resumen de estadísticas: " + e.getMessage()));
        }
    }
}