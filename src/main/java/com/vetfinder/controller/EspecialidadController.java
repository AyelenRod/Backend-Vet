package com.vetfinder.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.vetfinder.service.EspecialidadService;
import com.vetfinder.util.ApiResponse;
import com.vetfinder.model.Especialidad;

public class EspecialidadController {
    private final EspecialidadService especialidadService;

    public EspecialidadController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    public void getAll(Context ctx) {
        try {
            ctx.json(ApiResponse.success("Especialidades obtenidas", especialidadService.getAllEspecialidades()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var especialidad = especialidadService.getEspecialidadById(id);
            ctx.json(especialidad != null ? ApiResponse.success("Especialidad encontrada", especialidad) : ApiResponse.notFound("Especialidad"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    public void create(Context ctx) {
        try {
            var especialidad = ctx.bodyAsClass(Especialidad.class);
            int id = especialidadService.createEspecialidad(especialidad);
            especialidad.setIdEspecialidad(id);
            ctx.status(HttpStatus.CREATED).json(ApiResponse.success("Especialidad creada", especialidad));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var especialidad = ctx.bodyAsClass(Especialidad.class);
            especialidad.setIdEspecialidad(id);
            ctx.json(especialidadService.updateEspecialidad(especialidad) ?
                    ApiResponse.success("Especialidad actualizada", especialidad) : ApiResponse.notFound("Especialidad"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(especialidadService.deleteEspecialidad(id) ?
                    ApiResponse.success("Especialidad eliminada") : ApiResponse.notFound("Especialidad"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }
}