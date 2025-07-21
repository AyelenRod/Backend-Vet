package com.vetfinder.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.vetfinder.service.SexoService;
import com.vetfinder.util.ApiResponse;
import com.vetfinder.model.Sexo;

/**
 * Controlador para manejar las peticiones HTTP relacionadas con sexos
 */
public class SexoController {
    private final SexoService sexoService;

    public SexoController(SexoService sexoService) {
        this.sexoService = sexoService;
    }

    /**
     * GET /sexos - Obtiene todos los sexos
     */
    public void getAll(Context ctx) {
        try {
            var sexos = sexoService.getAllSexos();
            ctx.json(ApiResponse.success("Sexos obtenidos correctamente", sexos));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener sexos: " + e.getMessage()));
        }
    }

    /**
     * GET /sexos/{id} - Obtiene un sexo por ID
     */
    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var sexo = sexoService.getSexoById(id);

            if (sexo != null) {
                ctx.json(ApiResponse.success("Sexo encontrado", sexo));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Sexo"));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("ID inválido"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener sexo: " + e.getMessage()));
        }
    }

    /**
     * POST /sexos - Crea un nuevo sexo
     */
    public void create(Context ctx) {
        try {
            var sexo = ctx.bodyAsClass(Sexo.class);
            int id = sexoService.createSexo(sexo);
            sexo.setIdSexo(id);

            ctx.status(HttpStatus.CREATED)
                    .json(ApiResponse.success("Sexo creado correctamente", sexo));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al crear sexo: " + e.getMessage()));
        }
    }

    /**
     * PUT /sexos/{id} - Actualiza un sexo existente
     */
    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var sexo = ctx.bodyAsClass(Sexo.class);
            sexo.setIdSexo(id);

            boolean updated = sexoService.updateSexo(sexo);
            if (updated) {
                ctx.json(ApiResponse.success("Sexo actualizado correctamente", sexo));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Sexo"));
            }
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al actualizar sexo: " + e.getMessage()));
        }
    }

    /**
     * DELETE /sexos/{id} - Elimina un sexo
     */
    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = sexoService.deleteSexo(id);

            if (deleted) {
                ctx.json(ApiResponse.success("Sexo eliminado correctamente"));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Sexo"));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("ID inválido"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al eliminar sexo: " + e.getMessage()));
        }
    }
}