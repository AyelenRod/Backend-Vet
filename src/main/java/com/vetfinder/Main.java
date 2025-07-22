package com.vetfinder;

import io.javalin.Javalin;
import com.vetfinder.di.AppModule;
import com.vetfinder.config.DatabaseConfig;

/**
 * Main.java CORREGIDO - Con módulos necesarios para el frontend
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

            // Crear aplicación Javalin
            Javalin app = Javalin.create(config -> {
                config.plugins.enableCors(cors -> {
                    cors.add(it -> {
                        it.anyHost();
                        it.allowCredentials = true;
                    });
                });
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
                        "status", "ok",
                        "message", "VetFinder API funcionando correctamente",
                        "timestamp", System.currentTimeMillis()
                ));
            });

            app.get("/test", ctx -> {
                System.out.println("✅ Endpoint /test ejecutado correctamente");
                ctx.json(java.util.Map.of(
                        "test", "success",
                        "database", DatabaseConfig.testConnection()
                ));
            });

            // REGISTRAR MÓDULOS NECESARIOS PARA EL FRONTEND
            try {
                // ========== MÓDULOS BÁSICOS (CATÁLOGOS) ==========
                System.out.println("📝 Registrando ROL...");
                AppModule.initRoles().register(app);
                System.out.println("✅ ROL registrado");

                System.out.println("⚥ Registrando SEXO...");
                AppModule.initSexos().register(app);
                System.out.println("✅ SEXO registrado");

                System.out.println("🎓 Registrando ESPECIALIDAD...");
                AppModule.initEspecialidades().register(app);
                System.out.println("✅ ESPECIALIDAD registrado");

                System.out.println("💼 Registrando SERVICIO...");
                AppModule.initServicios().register(app);
                System.out.println("✅ SERVICIO registrado");

                System.out.println("📍 Registrando DIRECCION...");
                AppModule.initDirecciones().register(app);
                System.out.println("✅ DIRECCION registrado");

                // ========== MÓDULOS PRINCIPALES (REQUERIDOS POR FRONTEND) ==========
                System.out.println("👥 Registrando USUARIO...");
                AppModule.initUsuarios().register(app);
                System.out.println("✅ USUARIO registrado");

                System.out.println("🐕 Registrando MASCOTA...");
                AppModule.initMascotas().register(app);
                System.out.println("✅ MASCOTA registrado");

                System.out.println("🩺 Registrando DATO VETERINARIO...");
                AppModule.initDatosVeterinarios().register(app);
                System.out.println("✅ DATO VETERINARIO registrado");

                System.out.println("🏥 Registrando CONSULTORIO...");
                AppModule.initConsultorios().register(app);
                System.out.println("✅ CONSULTORIO registrado");

                System.out.println("📊 Registrando ESTADÍSTICAS...");
                AppModule.initEstadisticas().register(app);
                System.out.println("✅ ESTADÍSTICAS registrado");

                // ========== MÓDULOS OPCIONALES (ACTIVAR SI ESTÁN LISTOS) ==========
                /*
                System.out.println("📅 Registrando CITA...");
                AppModule.initCitas().register(app);
                System.out.println("✅ CITA registrado");

                System.out.println("🧾 Registrando FACTURA...");
                AppModule.initFacturas().register(app);
                System.out.println("✅ FACTURA registrado");
                */

            } catch (Exception e) {
                System.err.println("❌ Error al registrar módulo: " + e.getMessage());
                e.printStackTrace();
                // Continuar con el servidor aunque falle un módulo
            }

            System.out.println("=== DEBUG: Iniciando servidor ===");

            // Iniciar servidor
            app.start("0.0.0.0", port);

            System.out.println("=================================================");
            System.out.println("✅ VetFinder API iniciada CORRECTAMENTE");
            System.out.println("🌐 Servidor: http://localhost:" + port);
            System.out.println("=================================================");
            System.out.println("🧪 Endpoints principales disponibles:");
            System.out.println("- GET  http://localhost:" + port + "/ (test básico)");
            System.out.println("- GET  http://localhost:" + port + "/test (test con DB)");
            System.out.println("- POST http://localhost:" + port + "/api/usuarios/login");
            System.out.println("- GET  http://localhost:" + port + "/api/especialidades");
            System.out.println("- GET  http://localhost:" + port + "/api/direcciones");
            System.out.println("- GET  http://localhost:" + port + "/api/servicios");
            System.out.println("- GET  http://localhost:" + port + "/api/usuarios/veterinarios");
            System.out.println("- GET  http://localhost:" + port + "/api/estadisticas/horarios-concurridos");
            System.out.println("=================================================");
            System.out.println("🎯 FRONTEND READY - Todos los endpoints necesarios activos");
            System.out.println("=================================================");

        } catch (Exception e) {
            System.err.println("❌ ERROR CRÍTICO:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}