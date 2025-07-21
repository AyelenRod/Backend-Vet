package com.vetfinder.util;

/**
 * Clase para estandarizar las respuestas de la API
 * Proporciona un formato consistente para respuestas exitosas y de error
 */
public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;
    private long timestamp;

    // Constructor vacío
    public ApiResponse() {
        this.timestamp = System.currentTimeMillis();
    }

    // Constructor completo
    public ApiResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Crea una respuesta exitosa con datos
     * @param message Mensaje descriptivo
     * @param data Datos a retornar
     * @return ApiResponse exitosa
     */
    public static ApiResponse success(String message, Object data) {
        return new ApiResponse(true, message, data);
    }

    /**
     * Crea una respuesta exitosa sin datos
     * @param message Mensaje descriptivo
     * @return ApiResponse exitosa
     */
    public static ApiResponse success(String message) {
        return new ApiResponse(true, message, null);
    }

    /**
     * Crea una respuesta de error
     * @param message Mensaje de error
     * @return ApiResponse de error
     */
    public static ApiResponse error(String message) {
        return new ApiResponse(false, message, null);
    }

    /**
     * Crea una respuesta de no encontrado (404)
     * @param resource Recurso que no se encontró
     * @return ApiResponse de recurso no encontrado
     */
    public static ApiResponse notFound(String resource) {
        return new ApiResponse(false, resource + " no encontrado", null);
    }

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}