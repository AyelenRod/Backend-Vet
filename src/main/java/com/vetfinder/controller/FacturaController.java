package com.vetfinder.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.vetfinder.service.FacturaService;
import com.vetfinder.util.ApiResponse;
import com.vetfinder.model.Factura;

/**
 * Controlador para manejar las peticiones HTTP relacionadas con facturas
 */
public class FacturaController {
    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    /**
     * GET /facturas - Obtiene todas las facturas
     */
    public void getAll(Context ctx) {
        try {
            var facturas = facturaService.getAllFacturas();
            ctx.json(ApiResponse.success("Facturas obtenidas correctamente", facturas));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener facturas: " + e.getMessage()));
        }
    }

    /**
     * GET /facturas/{id} - Obtiene una factura por ID
     */
    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var factura = facturaService.getFacturaById(id);

            if (factura != null) {
                ctx.json(ApiResponse.success("Factura encontrada", factura));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Factura"));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("ID inválido"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener factura: " + e.getMessage()));
        }
    }

    /**
     * GET /usuarios/{usuarioId}/facturas - Obtiene facturas por usuario
     */
    public void getByUsuario(Context ctx) {
        try {
            int idUsuario = Integer.parseInt(ctx.pathParam("usuarioId"));
            var facturas = facturaService.getFacturasByUsuario(idUsuario);
            ctx.json(ApiResponse.success("Facturas del usuario obtenidas", facturas));
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("ID de usuario inválido"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener facturas: " + e.getMessage()));
        }
    }

    /**
     * POST /facturas - Crea una nueva factura
     */
    public void create(Context ctx) {
        try {
            var factura = ctx.bodyAsClass(Factura.class);
            int id = facturaService.createFactura(factura);
            factura.setIdFactura(id);

            ctx.status(HttpStatus.CREATED)
                    .json(ApiResponse.success("Factura creada correctamente", factura));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al crear factura: " + e.getMessage()));
        }
    }

    /**
     * PUT /facturas/{id} - Actualiza una factura existente
     */
    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var factura = ctx.bodyAsClass(Factura.class);
            factura.setIdFactura(id);

            boolean updated = facturaService.updateFactura(factura);
            if (updated) {
                ctx.json(ApiResponse.success("Factura actualizada correctamente", factura));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Factura"));
            }
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al actualizar factura: " + e.getMessage()));
        }
    }

    /**
     * DELETE /facturas/{id} - Elimina una factura
     */
    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = facturaService.deleteFactura(id);

            if (deleted) {
                ctx.json(ApiResponse.success("Factura eliminada correctamente"));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Factura"));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("ID inválido"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al eliminar factura: " + e.getMessage()));
        }
    }
}