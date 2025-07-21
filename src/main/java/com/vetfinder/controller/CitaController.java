package com.vetfinder.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.vetfinder.service.CitaService;
import com.vetfinder.util.ApiResponse;
import com.vetfinder.model.Cita;

public class CitaController {
    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    public void getAll(Context ctx) {
        try {
            ctx.json(ApiResponse.success("Citas obtenidas", citaService.getAllCitas()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var cita = citaService.getCitaById(id);
            ctx.json(cita != null ? ApiResponse.success("Cita encontrada", cita) : ApiResponse.notFound("Cita"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    public void getByMascota(Context ctx) {
        try {
            int idMascota = Integer.parseInt(ctx.pathParam("mascotaId"));
            var citas = citaService.getCitasByMascota(idMascota);
            ctx.json(ApiResponse.success("Citas de la mascota obtenidas", citas));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    public void getByVeterinario(Context ctx) {
        try {
            int idDatoVeterinario = Integer.parseInt(ctx.pathParam("veterinarioId"));
            var citas = citaService.getCitasByVeterinario(idDatoVeterinario);
            ctx.json(ApiResponse.success("Citas del veterinario obtenidas", citas));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    public void create(Context ctx) {
        try {
            var cita = ctx.bodyAsClass(Cita.class);
            int id = citaService.createCita(cita);
            cita.setIdCita(id);
            ctx.status(HttpStatus.CREATED).json(ApiResponse.success("Cita creada", cita));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var cita = ctx.bodyAsClass(Cita.class);
            cita.setIdCita(id);
            ctx.json(citaService.updateCita(cita) ?
                    ApiResponse.success("Cita actualizada", cita) : ApiResponse.notFound("Cita"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(citaService.deleteCita(id) ?
                    ApiResponse.success("Cita eliminada") : ApiResponse.notFound("Cita"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    public void updateEstado(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            String nuevoEstado = ctx.queryParam("estado");

            if (nuevoEstado == null) {
                ctx.status(HttpStatus.BAD_REQUEST).json(ApiResponse.error("El parámetro 'estado' es requerido"));
                return;
            }

            boolean updated = citaService.updateEstadoCita(id, nuevoEstado);
            ctx.json(updated ? ApiResponse.success("Estado de cita actualizado") : ApiResponse.notFound("Cita"));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error: " + e.getMessage()));
        }
    }
}