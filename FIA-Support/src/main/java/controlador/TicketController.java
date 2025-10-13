package controlador;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import modelo.dominio.Estado;
import modelo.dominio.Historial;
import modelo.dominio.Ticket;
import modelo.servicios.TicketService;

/**
 * Controller for the "Gestionar Ticket" use case. Keeps UI components unaware
 * of repository details.
 */
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public List<Ticket> listTickets() {
        return ticketService.listTickets(null);
    }

    public List<Ticket> filterBySolicitante(String query) {
        List<Ticket> tickets = listTickets();
        if (query == null || query.trim().isEmpty()) {
            return tickets;
        }
        final String normalized = query.trim().toLowerCase();
        return tickets.stream()
                .filter(t -> {
                    if (t.getSolicitante() == null) {
                        return false;
                    }
                    String carnet = t.getSolicitante().getId();
                    String nombre = t.getSolicitante().toString();
                    boolean matchCarnet = carnet != null && carnet.toLowerCase().contains(normalized);
                    boolean matchNombre = nombre != null && nombre.toLowerCase().contains(normalized);
                    return matchCarnet || matchNombre;
                })
                .collect(Collectors.toList());
    }

    public Ticket openTicket(String titulo, String descripcion, String carnetSolicitante, Estado estadoInicial) {
        return ticketService.openTicket(titulo, descripcion, carnetSolicitante, estadoInicial);
    }

    public Ticket updateTicket(int ticketId, String nuevoTitulo, String nuevaDescripcion) {
        return ticketService.updateTicket(ticketId, nuevoTitulo, nuevaDescripcion);
    }

    public void deleteTicket(int ticketId) {
        ticketService.deleteTicket(ticketId);
    }

    public Ticket reload(int ticketId) {
        return ticketService.getTicket(ticketId).orElse(null);
    }

    public List<Historial> historial(int ticketId) {
        return ticketService.getHistorial(ticketId);
    }

    public boolean hasHistorial(int ticketId) {
        return !Objects.requireNonNullElse(historial(ticketId), List.of()).isEmpty();
    }
}
