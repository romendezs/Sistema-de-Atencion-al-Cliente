package Modelo.servicios;

import Modelo.dominio.Estado;
import Modelo.dominio.Facultad;
import Modelo.dominio.Seguimiento;
import Modelo.dominio.Ticket;
import Modelo.repo.ITicketRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Servicio para el caso de uso "Reportar Estadísticas".
 */
public class ReportingService {
    private final ITicketRepository tickets;

    public ReportingService(ITicketRepository tickets) {
        this.tickets = Objects.requireNonNull(tickets, "tickets");
    }

    public List<Ticket> ticketsByPeriod(LocalDate desde, LocalDate hasta) {
        if (desde == null || hasta == null) {
            throw new IllegalArgumentException("Debe indicar el rango de fechas.");
        }
        if (hasta.isBefore(desde)) {
            throw new IllegalArgumentException("La fecha final no puede ser anterior a la inicial.");
        }
        return tickets.findAll().stream()
                .filter(t -> t.getHistorial().stream()
                        .map(Seguimiento::getFecha)
                        .filter(Objects::nonNull)
                        .anyMatch(f -> !f.isBefore(desde) && !f.isAfter(hasta)))
                .collect(Collectors.toList());
    }

    public double avgResolutionTime() {
        List<Long> duraciones = tickets.findAll().stream()
                .map(this::diasResolucion)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (duraciones.isEmpty()) {
            return 0.0;
        }
        return duraciones.stream().mapToLong(Long::longValue).average().orElse(0.0);
    }

    public Map<String, Long> byArea() {
        return tickets.findAll().stream()
                .collect(Collectors.groupingBy(t -> obtenerArea(t), Collectors.counting()));
    }

    public List<String> exportaciones() {
        List<String> resultado = new ArrayList<>();
        for (Ticket t : tickets.findAll()) {
            resultado.add(String.join(";",
                    String.valueOf(t.getId()),
                    t.getTitulo(),
                    obtenerArea(t),
                    t.getEstadoActual().getRotulo()));
        }
        return resultado;
    }

    private Long diasResolucion(Ticket ticket) {
        List<Seguimiento> historial = ticket.getHistorial();
        if (historial.isEmpty()) {
            return null;
        }
        LocalDate inicio = historial.get(0).getFecha();
        LocalDate fin = null;
        for (int i = historial.size() - 1; i >= 0; i--) {
            Seguimiento seg = historial.get(i);
            if (seg.getEstado() == Estado.CERRADO) {
                fin = seg.getFecha();
                break;
            }
        }
        if (inicio == null || fin == null) {
            return null;
        }
        return ChronoUnit.DAYS.between(inicio, fin);
    }

    private String obtenerArea(Ticket ticket) {
        Facultad facultad = ticket.getSolicitante().getFacultad();
        return facultad != null ? facultad.getNombre() : "Externo";
    }
}
