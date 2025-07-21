package com.vetfinder.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.vetfinder.service.ConsultorioService;
import com.vetfinder.util.ApiResponse;
import com.vetfinder.model.Consultorio;

/**
 * Controlador para manejar las peticiones HTTP relacionadas con consultorios
 */
public class ConsultorioController {
    private final ConsultorioService consultorioService;

    public ConsultorioController(ConsultorioService consultorioService) {
        this.consultorioService = consultorioService;
    }

    /**
     * GET /consultorios - Obtiene todos los consultorios
     */
    public void getAll(Context ctx) {
        try {
            var consultorios = consultorioService.getAllConsultorios();
            ctx.json(ApiResponse.success("Consultorios obtenidos correctamente", consultorios));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener consultorios: " + e.getMessage()));
        }
    }

    /**
     * GET /consultorios/{id} - Obtiene un consultorio por ID
     */
    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var consultorio = consultorioService.getConsultorioById(id);

            if (consultorio != null) {
                ctx.json(ApiResponse.success("Consultorio encontrado", consultorio));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Consultorio"));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("ID inválido"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener consultorio: " + e.getMessage()));
        }
    }

    /**
     * POST /consultorios - Crea un nuevo consultorio
     */
    public void create(Context ctx) {
        try {
            var consultorio = ctx.bodyAsClass(Consultorio.class);
            int id = consultorioService.createConsultorio(consultorio);
            consultorio.setIdConsultorio(id);

            ctx.status(HttpStatus.CREATED)
                    .json(ApiResponse.success("Consultorio creado correctamente", consultorio));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al crear consultorio: " + e.getMessage()));
        }
    }

    /**
     * PUT /consultorios/{id} - Actualiza un consultorio existente
     */
    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var consultorio = ctx.bodyAsClass(Consultorio.class);
            consultorio.setIdConsultorio(id);

            boolean updated = consultorioService.updateConsultorio(consultorio);
            if (updated) {
                ctx.json(ApiResponse.success("Consultorio actualizado correctamente", consultorio));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Consultorio"));
            }
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al actualizar consultorio: " + e.getMessage()));
        }
    }

    /**
     * DELETE /consultorios/{id} - Elimina un consultorio
     */
    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = consultorioService.deleteConsultorio(id);

            if (deleted) {
                ctx.json(ApiResponse.success("Consultorio eliminado correctamente"));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Consultorio"));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("ID inválido"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al eliminar consultorio: " + e.getMessage()));
        }
    }
}