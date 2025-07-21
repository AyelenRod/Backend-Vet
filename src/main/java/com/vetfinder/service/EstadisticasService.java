package com.vetfinder.service;

import com.vetfinder.repository.EstadisticasRepository;
import com.vetfinder.model.EstadisticaHorario;
import com.vetfinder.model.EstadisticaServicio;
import com.vetfinder.model.ResumenEstadisticas;

import java.sql.SQLException;
import java.util.List;

/**
 * Servicio para la gestión de estadísticas
 * Contiene la lógica de negocio para generar reportes y datos para gráficas
 */
public class EstadisticasService {
    private final EstadisticasRepository estadisticasRepository;

    public EstadisticasService(EstadisticasRepository estadisticasRepository) {
        this.estadisticasRepository = estadisticasRepository;
    }

    /**
     * Obtiene estadísticas de horarios más concurridos
     * Agrupa las citas por franja horaria y cuenta la cantidad
     * @return Lista de estadísticas por horario
     * @throws SQLException Error en la consulta
     */
    public List<EstadisticaHorario> getHorariosConcurridos() throws SQLException {
        return estadisticasRepository.findHorariosConcurridos();
    }

    /**
     * Obtiene estadísticas de servicios más solicitados
     * Agrupa las citas por tipo de servicio y cuenta la cantidad
     * @return Lista de estadísticas por servicio
     * @throws SQLException Error en la consulta
     */
    public List<EstadisticaServicio> getServiciosSolicitados() throws SQLException {
        return estadisticasRepository.findServiciosSolicitados();
    }

    /**
     * Obtiene un resumen general de estadísticas
     * Incluye totales y métricas principales
     * @return Resumen de estadísticas generales
     * @throws SQLException Error en la consulta
     */
    public ResumenEstadisticas getResumenEstadisticas() throws SQLException {
        ResumenEstadisticas resumen = new ResumenEstadisticas();

        // Obtener totales
        resumen.setTotalCitas(estadisticasRepository.countTotalCitas());
        resumen.setTotalMascotas(estadisticasRepository.countTotalMascotas());
        resumen.setTotalVeterinarios(estadisticasRepository.countTotalVeterinarios());
        resumen.setTotalTutores(estadisticasRepository.countTotalTutores());

        // Obtener citas por estado
        resumen.setCitasPendientes(estadisticasRepository.countCitasByEstado("Pendiente"));
        resumen.setCitasAceptadas(estadisticasRepository.countCitasByEstado("Aceptada"));
        resumen.setCitasRechazadas(estadisticasRepository.countCitasByEstado("Rechazada"));

        return resumen;
    }
}
