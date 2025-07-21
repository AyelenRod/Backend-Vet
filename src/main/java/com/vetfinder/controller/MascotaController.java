package com.vetfinder.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.vetfinder.service.MascotaService;
import com.vetfinder.util.ApiResponse;
import com.vetfinder.model.Mascota;

public class MascotaController {
    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    public void getAll(Context ctx) {
        try {
            ctx.json(ApiResponse.success("Mascotas obtenidas", mascotaService.getAllMascotas()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var mascota = mascotaService.getMascotaById(id);
            ctx.json(mascota != null ? ApiResponse.success("Mascota encontrada", mascota) : ApiResponse.notFound("Mascota"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    public void getByUsuario(Context ctx) {
        try {
            int idUsuario = Integer.parseInt(ctx.pathParam("usuarioId"));
            var mascotas = mascotaService.getMascotasByUsuario(idUsuario);
            ctx.json(ApiResponse.success("Mascotas del usuario obtenidas", mascotas));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    public void create(Context ctx) {
        try {
            var mascota = ctx.bodyAsClass(Mascota.class);
            int id = mascotaService.createMascota(mascota);
            mascota.setIdMascota(id);
            ctx.status(HttpStatus.CREATED).json(ApiResponse.success("Mascota creada", mascota));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var mascota = ctx.bodyAsClass(Mascota.class);
            mascota.setIdMascota(id);
            ctx.json(mascotaService.updateMascota(mascota) ?
                    ApiResponse.success("Mascota actualizada", mascota) : ApiResponse.notFound("Mascota"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(mascotaService.deleteMascota(id) ?
                    ApiResponse.success("Mascota eliminada") : ApiResponse.notFound("Mascota"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }
}