package com.vetfinder.di;

import com.vetfinder.controller.*;
import com.vetfinder.repository.*;
import com.vetfinder.routes.*;
import com.vetfinder.service.*;

/**
 * Módulo de configuración de dependencias de la aplicación.
 * Implementa un patrón de inyección de dependencias manual.
 * Crea e inicializa todas las capas de la aplicación para las 11 entidades + estadísticas.
 */
public class AppModule {

    public static RolRoutes initRoles() {
        RolRepository rolRepository = new RolRepository();
        RolService rolService = new RolService(rolRepository);
        RolController rolController = new RolController(rolService);
        return new RolRoutes(rolController);
    }

    public static UsuarioRoutes initUsuarios() {
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        UsuarioService usuarioService = new UsuarioService(usuarioRepository);
        UsuarioController usuarioController = new UsuarioController(usuarioService);
        return new UsuarioRoutes(usuarioController);
    }

    public static SexoRoutes initSexos() {
        SexoRepository sexoRepository = new SexoRepository();
        SexoService sexoService = new SexoService(sexoRepository);
        SexoController sexoController = new SexoController(sexoService);
        return new SexoRoutes(sexoController);
    }

    public static DireccionRoutes initDirecciones() {
        DireccionRepository direccionRepository = new DireccionRepository();
        DireccionService direccionService = new DireccionService(direccionRepository);
        DireccionController direccionController = new DireccionController(direccionService);
        return new DireccionRoutes(direccionController);
    }

    public static EspecialidadRoutes initEspecialidades() {
        EspecialidadRepository especialidadRepository = new EspecialidadRepository();
        EspecialidadService especialidadService = new EspecialidadService(especialidadRepository);
        EspecialidadController especialidadController = new EspecialidadController(especialidadService);
        return new EspecialidadRoutes(especialidadController);
    }

    public static ServicioRoutes initServicios() {
        ServicioRepository servicioRepository = new ServicioRepository();
        ServicioService servicioService = new ServicioService(servicioRepository);
        ServicioController servicioController = new ServicioController(servicioService);
        return new ServicioRoutes(servicioController);
    }

    public static MascotaRoutes initMascotas() {
        MascotaRepository mascotaRepository = new MascotaRepository();
        MascotaService mascotaService = new MascotaService(mascotaRepository);
        MascotaController mascotaController = new MascotaController(mascotaService);
        return new MascotaRoutes(mascotaController);
    }

    public static DatoVeterinarioRoutes initDatosVeterinarios() {
        DatoVeterinarioRepository datoVeterinarioRepository = new DatoVeterinarioRepository();
        DatoVeterinarioService datoVeterinarioService = new DatoVeterinarioService(datoVeterinarioRepository);
        DatoVeterinarioController datoVeterinarioController = new DatoVeterinarioController(datoVeterinarioService);
        return new DatoVeterinarioRoutes(datoVeterinarioController);
    }

    public static ConsultorioRoutes initConsultorios() {
        ConsultorioRepository consultorioRepository = new ConsultorioRepository();
        ConsultorioService consultorioService = new ConsultorioService(consultorioRepository);
        ConsultorioController consultorioController = new ConsultorioController(consultorioService);
        return new ConsultorioRoutes(consultorioController);
    }

    public static CitaRoutes initCitas() {
        CitaRepository citaRepository = new CitaRepository();
        CitaService citaService = new CitaService(citaRepository);
        CitaController citaController = new CitaController(citaService);
        return new CitaRoutes(citaController);
    }

    public static FacturaRoutes initFacturas() {
        FacturaRepository facturaRepository = new FacturaRepository();
        FacturaService facturaService = new FacturaService(facturaRepository);
        FacturaController facturaController = new FacturaController(facturaService);
        return new FacturaRoutes(facturaController);
    }

    /**
     * NUEVO: Inicializa el módulo de estadísticas
     * @return Rutas configuradas para estadísticas
     */
    public static EstadisticasRoutes initEstadisticas() {
        EstadisticasRepository estadisticasRepository = new EstadisticasRepository();
        EstadisticasService estadisticasService = new EstadisticasService(estadisticasRepository);
        EstadisticasController estadisticasController = new EstadisticasController(estadisticasService);
        return new EstadisticasRoutes(estadisticasController);
    }
}