package com.vetfinder.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.vetfinder.service.DireccionService;
import com.vetfinder.util.ApiResponse;
import com.vetfinder.model.Direccion;

/**
 * Controlador para manejar las peticiones HTTP relacionadas con direcciones
 * No requiere modificaciones ya que la lógica se mantiene igual
 */
public class DireccionController {
    private final DireccionService direccionService;

    public DireccionController(DireccionService direccionService) {
        this.direccionService = direccionService;
    }

    /**
     * GET /direcciones - Obtiene todas las direcciones
     */
    public void getAll(Context ctx) {
        try {
            ctx.json(ApiResponse.success("Direcciones obtenidas", direccionService.getAllDirecciones()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    /**
     * GET /direcciones/{id} - Obtiene una dirección por ID
     */
    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var direccion = direccionService.getDireccionById(id);
            ctx.json(direccion != null ? ApiResponse.success("Dirección encontrada", direccion) : ApiResponse.notFound("Dirección"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    /**
     * POST /direcciones - Crea una nueva dirección
     */
    public void create(Context ctx) {
        try {
            var direccion = ctx.bodyAsClass(Direccion.class);
            int id = direccionService.createDireccion(direccion);
            direccion.setIdDireccion(id);
            ctx.status(HttpStatus.CREATED).json(ApiResponse.success("Dirección creada", direccion));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    /**
     * PUT /direcciones/{id} - Actualiza una dirección existente
     */
    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var direccion = ctx.bodyAsClass(Direccion.class);
            direccion.setIdDireccion(id);
            ctx.json(direccionService.updateDireccion(direccion) ?
                    ApiResponse.success("Dirección actualizada", direccion) : ApiResponse.notFound("Dirección"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    /**
     * DELETE /direcciones/{id} - Elimina una dirección
     */
    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(direccionService.deleteDireccion(id) ?
                    ApiResponse.success("Dirección eliminada") : ApiResponse.notFound("Dirección"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }
}
