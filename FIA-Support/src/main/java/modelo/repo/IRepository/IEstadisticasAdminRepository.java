/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.repo.IRepository;

import java.time.LocalDate;
import java.util.List;
import modelo.dominio.TicketMetricasDiarias;

/**
 *
 * @author Méndez
 */
public interface IEstadisticasAdminRepository {
    // Global por estado

    List<Object[]> conteoPorEstadoGlobal();

    // Por técnico
    List<Object[]> conteoPorTecnico();

    // Por categoría
    List<Object[]> conteoPorCategoria();

    // Métricas diarias en rango
    List<TicketMetricasDiarias> metricasDiarias(LocalDate desde, LocalDate hasta);

    // Promedio de resolución global (minutos) en rango
    Double promedioResolucionMinGlobal(LocalDate desde, LocalDate hasta);
}
