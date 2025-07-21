package com.vetfinder.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.vetfinder.service.ServicioService;
import com.vetfinder.util.ApiResponse;
import com.vetfinder.model.Servicio;

public class ServicioController {
    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    public void getAll(Context ctx) {
        try {
            ctx.json(ApiResponse.success("Servicios obtenidos", servicioService.getAllServicios()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var servicio = servicioService.getServicioById(id);
            ctx.json(servicio != null ? ApiResponse.success("Servicio encontrado", servicio) : ApiResponse.notFound("Servicio"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    public void create(Context ctx) {
        try {
            var servicio = ctx.bodyAsClass(Servicio.class);
            int id = servicioService.createServicio(servicio);
            servicio.setIdServicio(id);
            ctx.status(HttpStatus.CREATED).json(ApiResponse.success("Servicio creado", servicio));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var servicio = ctx.bodyAsClass(Servicio.class);
            servicio.setIdServicio(id);
            ctx.json(servicioService.updateServicio(servicio) ?
                    ApiResponse.success("Servicio actualizado", servicio) : ApiResponse.notFound("Servicio"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(servicioService.deleteServicio(id) ?
                    ApiResponse.success("Servicio eliminado") : ApiResponse.notFound("Servicio"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }
}