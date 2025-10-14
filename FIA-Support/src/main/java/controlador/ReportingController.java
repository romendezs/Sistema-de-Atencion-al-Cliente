package controlador;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import modelo.dominio.EstadisticaConteo;
import modelo.dominio.EstadisticaDiaria;
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

    public List<EstadisticaConteo> obtenerConteoPorEstadoGlobal() {
        return mapConteos(resumenPorEstadoGlobal());
    }

    public List<EstadisticaConteo> obtenerConteoPorTecnico() {
        return mapConteos(resumenPorTecnico());
    }

    public List<EstadisticaConteo> obtenerConteoPorCategoria() {
        return mapConteos(resumenPorCategoria());
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

    public List<EstadisticaConteo> obtenerConteoPorEstadoUsuario(String carnet) {
        return mapConteos(resumenUsuarioPorEstado(carnet));
    }

    public List<EstadisticaDiaria> obtenerConteoDiarioUsuario(String carnet, LocalDate desde, LocalDate hasta) {
        List<Object[]> datos = resumenUsuarioPorDia(carnet, desde, hasta);
        if (datos == null || datos.isEmpty()) {
            return Collections.emptyList();
        }
        List<EstadisticaDiaria> resultado = new ArrayList<>();
        for (Object[] fila : datos) {
            if (fila == null || fila.length < 2) {
                continue;
            }
            LocalDate fecha = convertToLocalDate(fila[0]);
            long total = extractLong(fila[1]);
            if (fecha != null) {
                resultado.add(new EstadisticaDiaria(fecha, total));
            }
        }
        return resultado;
    }

    private List<EstadisticaConteo> mapConteos(List<Object[]> datos) {
        if (datos == null || datos.isEmpty()) {
            return Collections.emptyList();
        }
        List<EstadisticaConteo> resultado = new ArrayList<>();
        for (Object[] fila : datos) {
            if (fila == null || fila.length < 2) {
                continue;
            }
            String etiqueta = fila[0] == null ? "(Sin etiqueta)" : fila[0].toString();
            long total = extractLong(fila[1]);
            resultado.add(new EstadisticaConteo(etiqueta, total));
        }
        return resultado;
    }

    private long extractLong(Object numero) {
        if (numero instanceof Number) {
            return ((Number) numero).longValue();
        }
        if (numero != null) {
            try {
                return Long.parseLong(numero.toString());
            } catch (NumberFormatException ignored) {
            }
        }
        return 0L;
    }

    private LocalDate convertToLocalDate(Object valor) {
        if (valor instanceof LocalDate) {
            return (LocalDate) valor;
        }
        if (valor instanceof java.sql.Date sqlDate) {
            return sqlDate.toLocalDate();
        }
        if (valor instanceof java.util.Date date) {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if (valor instanceof java.time.LocalDateTime ldt) {
            return ldt.toLocalDate();
        }
        return null;
    }
}
