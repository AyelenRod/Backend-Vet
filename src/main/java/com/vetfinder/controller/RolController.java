package com.vetfinder.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.vetfinder.service.RolService;
import com.vetfinder.util.ApiResponse;
import com.vetfinder.model.Rol;

/**
 * Controlador para manejar las peticiones HTTP relacionadas con roles
 */
public class RolController {
    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    /**
     * GET /roles - Obtiene todos los roles
     */
    public void getAll(Context ctx) {
        try {
            var roles = rolService.getAllRoles();
            ctx.json(ApiResponse.success("Roles obtenidos correctamente", roles));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener roles: " + e.getMessage()));
        }
    }

    /**
     * GET /roles/{id} - Obtiene un rol por ID
     */
    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var rol = rolService.getRolById(id);

            if (rol != null) {
                ctx.json(ApiResponse.success("Rol encontrado", rol));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Rol"));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("ID inválido"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener rol: " + e.getMessage()));
        }
    }

    /**
     * POST /roles - Crea un nuevo rol
     */
    public void create(Context ctx) {
        try {
            var rol = ctx.bodyAsClass(Rol.class);
            int id = rolService.createRol(rol);
            rol.setIdRol(id);

            ctx.status(HttpStatus.CREATED)
                    .json(ApiResponse.success("Rol creado correctamente", rol));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al crear rol: " + e.getMessage()));
        }
    }

    /**
     * PUT /roles/{id} - Actualiza un rol existente
     */
    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var rol = ctx.bodyAsClass(Rol.class);
            rol.setIdRol(id);

            boolean updated = rolService.updateRol(rol);
            if (updated) {
                ctx.json(ApiResponse.success("Rol actualizado correctamente", rol));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Rol"));
            }
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al actualizar rol: " + e.getMessage()));
        }
    }

    /**
     * DELETE /roles/{id} - Elimina un rol
     */
    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = rolService.deleteRol(id);

            if (deleted) {
                ctx.json(ApiResponse.success("Rol eliminado correctamente"));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Rol"));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("ID inválido"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al eliminar rol: " + e.getMessage()));
        }
    }
}