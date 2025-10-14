package modelo.servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import modelo.dominio.TicketMetricasDiarias;
import modelo.repo.IRepository.IEstadisticasAdminRepository;
import modelo.repo.IRepository.IEstadisticasUsuarioRepository;
import modelo.utils.Validacion;

/**
 * Servicio para el caso de uso "Reportar Estadísticas". Expone métodos de
 * consulta agregada y utilidades de exportación.
 */
public class ReportingService {

    private final IEstadisticasAdminRepository adminRepository;
    private final IEstadisticasUsuarioRepository usuarioRepository;

    public ReportingService(IEstadisticasAdminRepository adminRepository,
                            IEstadisticasUsuarioRepository usuarioRepository) {
        this.adminRepository = adminRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Object[]> resumenPorEstadoGlobal() {
        return adminRepository.conteoPorEstadoGlobal();
    }

    public List<Object[]> resumenPorTecnico() {
        return adminRepository.conteoPorTecnico();
    }

    public List<Object[]> resumenPorCategoria() {
        return adminRepository.conteoPorCategoria();
    }

    public List<TicketMetricasDiarias> metricasPorPeriodo(LocalDate desde, LocalDate hasta) {
        validarRango(desde, hasta);
        return adminRepository.metricasDiarias(desde, hasta);
    }

    public Double promedioResolucionGlobal(LocalDate desde, LocalDate hasta) {
        validarRango(desde, hasta);
        return adminRepository.promedioResolucionMinGlobal(desde, hasta);
    }

    public List<Object[]> resumenUsuarioPorEstado(String carnet) {
        String id = Validacion.normalizarCarnet(carnet);
        if (!Validacion.esCarnetValido(id)) {
            throw new IllegalArgumentException("Carnet inválido.");
        }
        return usuarioRepository.conteoPorEstadoUsuario(id);
    }

    public List<Object[]> resumenUsuarioPorDia(String carnet, LocalDate desde, LocalDate hasta) {
        String id = validarCarnet(carnet);
        validarRango(desde, hasta);
        return usuarioRepository.conteoPorDiaUsuario(id, desde, hasta);
    }

    public Double promedioResolucionUsuario(String carnet, LocalDate desde, LocalDate hasta) {
        String id = validarCarnet(carnet);
        validarRango(desde, hasta);
        return usuarioRepository.promedioResolucionMinUsuario(id, desde, hasta);
    }

    public String exportMetricasCsv(LocalDate desde, LocalDate hasta) {
        List<TicketMetricasDiarias> metricas = metricasPorPeriodo(desde, hasta);
        StringBuilder sb = new StringBuilder();
        sb.append("fecha,abiertos,cerrados,en_proceso,promedio_resolucion_min\n");
        for (TicketMetricasDiarias m : metricas) {
            sb.append(m.getId().getDia()).append(',')
                    .append(valor(m.getAbiertos())).append(',')
                    .append(valor(m.getCerrados())).append(',')
                    .append(valor(m.getReabiertos())).append(',')
                    .append(valor(m.getPromedioResolucionMin()))
                    .append('\n');
        }
        return sb.toString();
    }

    private void validarRango(LocalDate desde, LocalDate hasta) {
        Objects.requireNonNull(desde, "Debe indicar la fecha inicial.");
        Objects.requireNonNull(hasta, "Debe indicar la fecha final.");
        if (desde.isAfter(hasta)) {
            throw new IllegalArgumentException("El rango de fechas es inválido.");
        }
    }

    private String validarCarnet(String carnet) {
        String id = Validacion.normalizarCarnet(carnet);
        if (!Validacion.esCarnetValido(id)) {
            throw new IllegalArgumentException("Carnet inválido.");
        }
        return id;
    }

    private static String valor(Number numero) {
        return numero == null ? "" : numero.toString();
    }
}
