package controlador;

import java.time.LocalDate;
import java.util.List;
import modelo.dominio.TicketMetricasDiarias;
import modelo.servicios.ReportingService;

/**
 * Bridges {@link ReportingService} with Swing components.
 */
public class ReportingController {

    private final ReportingService reportingService;

    public ReportingController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    public List<Object[]> resumenPorEstadoGlobal() {
        return reportingService.resumenPorEstadoGlobal();
    }

    public List<Object[]> resumenPorTecnico() {
        return reportingService.resumenPorTecnico();
    }

    public List<Object[]> resumenPorCategoria() {
        return reportingService.resumenPorCategoria();
    }

    public List<TicketMetricasDiarias> metricasPorPeriodo(LocalDate desde, LocalDate hasta) {
        return reportingService.metricasPorPeriodo(desde, hasta);
    }

    public Double promedioResolucionGlobal(LocalDate desde, LocalDate hasta) {
        return reportingService.promedioResolucionGlobal(desde, hasta);
    }

    public String exportMetricasCsv(LocalDate desde, LocalDate hasta) {
        return reportingService.exportMetricasCsv(desde, hasta);
    }

    public List<Object[]> resumenUsuarioPorEstado(String carnet) {
        return reportingService.resumenUsuarioPorEstado(carnet);
    }

    public List<Object[]> resumenUsuarioPorDia(String carnet, LocalDate desde, LocalDate hasta) {
        return reportingService.resumenUsuarioPorDia(carnet, desde, hasta);
    }

    public Double promedioResolucionUsuario(String carnet, LocalDate desde, LocalDate hasta) {
        return reportingService.promedioResolucionUsuario(carnet, desde, hasta);
    }
}
