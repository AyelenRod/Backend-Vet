package com.vetfinder.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;

import javax.sql.DataSource;

/**
 * Configuración de la conexión a la base de datos MySQL
 * MODIFICADO: Inicialización EAGER para evitar timeout en primera petición
 */
public class DatabaseConfig {
    private static HikariDataSource dataSource;
    private static boolean initialized = false;

    /**
     * NUEVO: Inicializa el pool de conexiones de forma EAGER
     * Debe llamarse al inicio de la aplicación
     */
    public static void initialize() {
        if (!initialized) {
            System.out.println("Inicializando pool de conexiones...");

            // Cargar variables de entorno desde archivo .env (MANTENER)
            Dotenv dotenv = Dotenv.load();

            // Obtener configuración de base de datos
            String host = dotenv.get("DB_HOST");
            String dbName = dotenv.get("DB_SCHEMA");
            String jdbcUrl = String.format("jdbc:mysql://%s:3306/%s", host, dbName);

            // Configurar HikariCP con timeouts optimizados
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(dotenv.get("DB_USER"));
            config.setPassword(dotenv.get("DB_PASS"));
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            // MODIFICADO: Timeouts más cortos para evitar colgamientos
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setConnectionTimeout(10000);     // 10 segundos (era 30)
            config.setIdleTimeout(300000);          // 5 minutos
            config.setMaxLifetime(600000);          // 10 minutos
            config.setValidationTimeout(5000);      // 5 segundos validación
            config.setInitializationFailTimeout(15000); // 15 segundos init

            dataSource = new HikariDataSource(config);
            initialized = true;

            System.out.println("Conexión a base de datos configurada: " + dbName);
        }
    }

    /**
     * MODIFICADO: Ahora requiere inicialización previa
     */
    public static DataSource getDataSource() {
        if (!initialized) {
            // Si no se inicializó, forzar inicialización (fallback)
            initialize();
        }
        return dataSource;
    }

    /**
     * Cierra el pool de conexiones
     */
    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            initialized = false;
            System.out.println("Pool de conexiones cerrado");
        }
    }

    /**
     * Verifica si la conexión a la base de datos funciona
     */
    public static boolean testConnection() {
        try (var connection = getDataSource().getConnection()) {
            return connection.isValid(5);
        } catch (Exception e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            return false;
        }
    }
}