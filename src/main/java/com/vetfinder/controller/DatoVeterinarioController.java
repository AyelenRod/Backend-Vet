package com.vetfinder.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.vetfinder.service.DatoVeterinarioService;
import com.vetfinder.util.ApiResponse;
import com.vetfinder.model.DatoVeterinario;

/**
 * Controlador para manejar las peticiones HTTP relacionadas con datos veterinarios
 */
public class DatoVeterinarioController {
    private final DatoVeterinarioService datoVeterinarioService;

    public DatoVeterinarioController(DatoVeterinarioService datoVeterinarioService) {
        this.datoVeterinarioService = datoVeterinarioService;
    }

    /**
     * GET /datos-veterinarios - Obtiene todos los datos veterinarios
     */
    public void getAll(Context ctx) {
        try {
            var datosVeterinarios = datoVeterinarioService.getAllDatosVeterinarios();
            ctx.json(ApiResponse.success("Datos veterinarios obtenidos correctamente", datosVeterinarios));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener datos veterinarios: " + e.getMessage()));
        }
    }

    /**
     * GET /datos-veterinarios/{id} - Obtiene un dato veterinario por ID
     */
    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var datoVeterinario = datoVeterinarioService.getDatoVeterinarioById(id);

            if (datoVeterinario != null) {
                ctx.json(ApiResponse.success("Dato veterinario encontrado", datoVeterinario));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Dato veterinario"));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("ID inválido"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener dato veterinario: " + e.getMessage()));
        }
    }

    /**
     * GET /usuarios/{usuarioId}/datos-veterinarios - Obtiene datos veterinarios por usuario
     */
    public void getByUsuario(Context ctx) {
        try {
            int idUsuario = Integer.parseInt(ctx.pathParam("usuarioId"));
            var datosVeterinarios = datoVeterinarioService.getDatosVeterinariosByUsuario(idUsuario);
            ctx.json(ApiResponse.success("Datos veterinarios del usuario obtenidos", datosVeterinarios));
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("ID de usuario inválido"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener datos veterinarios: " + e.getMessage()));
        }
    }

    /**
     * POST /datos-veterinarios - Crea un nuevo dato veterinario
     * CON DEBUG PARA IDENTIFICAR PROBLEMA DE JSON
     */
    public void create(Context ctx) {
        try {
            // ========== CÓDIGO DE DEBUG TEMPORAL ==========
            System.out.println("=== DEBUG PETICIÓN POST /datos-veterinarios ===");
            System.out.println("Method: " + ctx.method());
            System.out.println("Path: " + ctx.path());
            System.out.println("Content-Type header: " + ctx.header("Content-Type"));
            System.out.println("Content-Length header: " + ctx.header("Content-Length"));
            System.out.println("All headers: " + ctx.headerMap());

            // Verificar el contenido crudo del body
            String bodyContent = ctx.body();
            System.out.println("Body length: " + (bodyContent != null ? bodyContent.length() : "null"));
            System.out.println("Body content: '" + bodyContent + "'");
            System.out.println("Body isEmpty: " + (bodyContent == null || bodyContent.trim().isEmpty()));
            System.out.println("Body bytes length: " + (ctx.bodyAsBytes() != null ? ctx.bodyAsBytes().length : "null"));

            // Verificar si el body está vacío antes de intentar parsearlo
            if (bodyContent == null || bodyContent.trim().isEmpty()) {
                System.err.println("ERROR: Body de la petición está vacío!");
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(ApiResponse.error("Body de la petición está vacío. Se requiere JSON válido con los datos del veterinario."));
                return;
            }

            // Intentar validar que sea JSON válido
            try {
                System.out.println("Intentando parsear JSON...");
                var datoVeterinario = ctx.bodyAsClass(DatoVeterinario.class);
                System.out.println("JSON parseado exitosamente!");
                System.out.println("Objeto creado: " + datoVeterinario);

                // Lógica normal de creación
                int id = datoVeterinarioService.createDatoVeterinario(datoVeterinario);
                datoVeterinario.setIdDatoVeterinario(id);

                ctx.status(HttpStatus.CREATED)
                        .json(ApiResponse.success("Dato veterinario creado correctamente", datoVeterinario));

            } catch (Exception parseException) {
                System.err.println("ERROR AL PARSEAR JSON:");
                parseException.printStackTrace();
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(ApiResponse.error("Error al parsear JSON: " + parseException.getMessage()));
                return;
            }
            // ========== FIN DEBUG ==========

        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            System.err.println("Error general en create:");
            e.printStackTrace();
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al crear dato veterinario: " + e.getMessage()));
        }
    }

    /**
     * PUT /datos-veterinarios/{id} - Actualiza un dato veterinario existente
     * CON DEBUG PARA IDENTIFICAR PROBLEMA DE JSON
     */
    public void update(Context ctx) {
        try {
            // ========== CÓDIGO DE DEBUG TEMPORAL ==========
            System.out.println("=== DEBUG PETICIÓN PUT /datos-veterinarios/{id} ===");
            System.out.println("Method: " + ctx.method());
            System.out.println("Path: " + ctx.path());
            System.out.println("Path param 'id': " + ctx.pathParam("id"));
            System.out.println("Content-Type header: " + ctx.header("Content-Type"));
            System.out.println("Content-Length header: " + ctx.header("Content-Length"));

            // Verificar el contenido crudo del body
            String bodyContent = ctx.body();
            System.out.println("Body length: " + (bodyContent != null ? bodyContent.length() : "null"));
            System.out.println("Body content: '" + bodyContent + "'");
            System.out.println("Body isEmpty: " + (bodyContent == null || bodyContent.trim().isEmpty()));

            // Verificar si el body está vacío antes de intentar parsearlo
            if (bodyContent == null || bodyContent.trim().isEmpty()) {
                System.err.println("ERROR: Body de la petición PUT está vacío!");
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(ApiResponse.error("Body de la petición está vacío. Se requiere JSON válido con los datos actualizados."));
                return;
            }
            // ========== FIN DEBUG ==========

            int id = Integer.parseInt(ctx.pathParam("id"));
            var datoVeterinario = ctx.bodyAsClass(DatoVeterinario.class);
            datoVeterinario.setIdDatoVeterinario(id);

            boolean updated = datoVeterinarioService.updateDatoVeterinario(datoVeterinario);
            if (updated) {
                ctx.json(ApiResponse.success("Dato veterinario actualizado correctamente", datoVeterinario));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Dato veterinario"));
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación en update: " + e.getMessage());
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            System.err.println("Error general en update:");
            e.printStackTrace();
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al actualizar dato veterinario: " + e.getMessage()));
        }
    }

    /**
     * DELETE /datos-veterinarios/{id} - Elimina un dato veterinario
     */
    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = datoVeterinarioService.deleteDatoVeterinario(id);

            if (deleted) {
                ctx.json(ApiResponse.success("Dato veterinario eliminado correctamente"));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Dato veterinario"));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("ID inválido"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al eliminar dato veterinario: " + e.getMessage()));
        }
    }
}