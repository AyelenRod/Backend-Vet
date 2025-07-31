package com.vetfinder;

import io.javalin.Javalin;
import com.vetfinder.di.AppModule;
import com.vetfinder.config.DatabaseConfig;

/**
 * Main.java CON DEBUG AGREGADO - Para identificar problema de JSON vacío
 * ACCIÓN: REEMPLAZAR el archivo Main.java actual por este código
 */
public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("=== VetFinder API - Iniciando servidor ===");

            // Inicializar base de datos
            System.out.println("Inicializando base de datos...");
            DatabaseConfig.initialize();
            System.out.println("✅ Base de datos inicializada");

            // Obtener puerto del entorno o usar 7000 por defecto
            int port = Integer.parseInt(System.getenv().getOrDefault("SERVER_PORT", "7000"));

            // Crear aplicación Javalin CON CONFIGURACIÓN DE DEBUG
            Javalin app = Javalin.create(config -> {
                // Configuración de CORS
                config.plugins.enableCors(cors -> {
                    cors.add(it -> {
                        it.anyHost();
                        it.allowCredentials = true;
                    });
                });

                // ========== CONFIGURACIÓN ADICIONAL PARA DEBUG ==========
                config.plugins.enableDevLogging();

                config.http.maxRequestSize = 10_000_000L; // 10MB

                config.http.asyncTimeout = 10_000L; // 10 segundos

                System.out.println("✅ Javalin configurado con debug habilitado");
            });

            // ========== FILTRO DE DEBUG TEMPORAL ==========
            app.before("/api/*", ctx -> {
                System.out.println("\n" + "=".repeat(60));
                System.out.println("🔍 FILTRO DEBUG - PETICIÓN INTERCEPTADA");
                System.out.println("=".repeat(60));
                System.out.println("📍 Method: " + ctx.method());
                System.out.println("📍 Path: " + ctx.path());
                System.out.println("📍 Query string: " + ctx.queryString());
                System.out.println("📍 Content-Type header: " + ctx.header("Content-Type"));
                System.out.println("📍 Content-Length header: " + ctx.header("Content-Length"));
                System.out.println("📍 User-Agent: " + ctx.header("User-Agent"));

                // Mostrar TODOS los headers
                System.out.println("📍 Todos los headers:");
                ctx.headerMap().forEach((key, value) ->
                        System.out.println("   " + key + ": " + value)
                );

                // Información del body (CRÍTICO para debugging)
                String bodyContent = ctx.body();
                byte[] bodyBytes = ctx.bodyAsBytes();

                System.out.println("📍 Body info:");
                System.out.println("   Body string length: " + (bodyContent != null ? bodyContent.length() : "null"));
                System.out.println("   Body bytes length: " + (bodyBytes != null ? bodyBytes.length : "null"));
                System.out.println("   Body isEmpty (string): " + (bodyContent == null || bodyContent.trim().isEmpty()));
                System.out.println("   Body content: '" + bodyContent + "'");

                if (bodyBytes != null && bodyBytes.length > 0) {
                    System.out.println("   Body as bytes: " + java.util.Arrays.toString(bodyBytes));
                }

                System.out.println("=".repeat(60) + "\n");
            });

            // Shutdown hook
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Cerrando aplicación...");
                DatabaseConfig.closeDataSource();
                app.stop();
            }));

            System.out.println("=== Registrando endpoints de la API ===");

            // Rutas básicas de prueba
            app.get("/", ctx -> {
                System.out.println("✅ Endpoint / ejecutado correctamente");
                ctx.json(java.util.Map.of(
                        "message", "VetFinder API funcionando correctamente",
                        "version", "1.0.0",
                        "timestamp", System.currentTimeMillis()
                ));
            });

            app.get("/test", ctx -> {
                System.out.println("✅ Endpoint /test ejecutado correctamente");
                ctx.json(java.util.Map.of(
                        "message", "Conexión a base de datos OK",
                        "database", "conectada",
                        "timestamp", System.currentTimeMillis()
                ));
            });

            // ========== ENDPOINT DE PRUEBA ESPECÍFICO PARA JSON ==========
            app.post("/api/test-json", ctx -> {
                System.out.println("🧪 ENDPOINT TEST-JSON EJECUTADO");
                String body = ctx.body();
                System.out.println("Body recibido en test-json: '" + body + "'");

                try {
                    var json = ctx.bodyAsClass(java.util.Map.class);
                    ctx.json(java.util.Map.of(
                            "success", true,
                            "message", "JSON parseado correctamente",
                            "received", json
                    ));
                } catch (Exception e) {
                    ctx.json(java.util.Map.of(
                            "success", false,
                            "message", "Error al parsear JSON: " + e.getMessage(),
                            "bodyReceived", body
                    ));
                }
            });

            // Registrar todas las rutas de la aplicación
            AppModule.initRoles().register(app);
            AppModule.initUsuarios().register(app);
            AppModule.initSexos().register(app);
            AppModule.initDirecciones().register(app);
            AppModule.initEspecialidades().register(app);
            AppModule.initServicios().register(app);
            AppModule.initMascotas().register(app);
            AppModule.initDatosVeterinarios().register(app);
            AppModule.initConsultorios().register(app);
            AppModule.initCitas().register(app);
            AppModule.initFacturas().register(app);
            AppModule.initEstadisticas().register(app);

            System.out.println("=== DEBUG: Iniciando servidor ===");

            // Iniciar servidor
            app.start("0.0.0.0", port);

            System.out.println("=================================================");
            System.out.println("✅ VetFinder API iniciada CORRECTAMENTE CON DEBUG");
            System.out.println("🌐 Servidor: http://localhost:" + port);
            System.out.println("=================================================");
            System.out.println("🧪 Endpoints principales disponibles:");
            System.out.println("- GET  http://localhost:" + port + "/ (test básico)");
            System.out.println("- GET  http://localhost:" + port + "/test (test con DB)");
            System.out.println("- POST http://localhost:" + port + "/api/test-json (test JSON)");
            System.out.println("- POST http://localhost:" + port + "/api/usuarios/login");
            System.out.println("- GET  http://localhost:" + port + "/api/especialidades");
            System.out.println("- GET  http://localhost:" + port + "/api/direcciones");
            System.out.println("- GET  http://localhost:" + port + "/api/servicios");
            System.out.println("- GET  http://localhost:" + port + "/api/usuarios/veterinarios");
            System.out.println("- GET  http://localhost:" + port + "/api/estadisticas/horarios-concurridos");
            System.out.println("=================================================");
            System.out.println("🎯 FRONTEND READY - Todos los endpoints necesarios activos");
            System.out.println("🔍 DEBUG MODE ACTIVO - Se mostrarán detalles de todas las peticiones");
            System.out.println("=================================================");

        } catch (Exception e) {
            System.err.println("❌ ERROR CRÍTICO:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}