package modelo.servicios;

import java.util.List;
import modelo.dominio.Empleado;
import modelo.dominio.Estado;
import modelo.dominio.Historial;
import modelo.repo.IRepository.ITicketRepository;

/**
 * Servicio que encapsula el flujo de trabajo de un ticket (seguimientos y
 * cambios de estado).
 */
public class WorkflowService {

    private final ITicketRepository ticketRepository;

    public WorkflowService(ITicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public void advanceStatus(int ticketId, Estado nuevoEstado, Empleado tecnico, String comentario) {
        if (ticketId <= 0) {
            throw new IllegalArgumentException("Ticket inválido.");
        }
        if (nuevoEstado == null || nuevoEstado.getId() == null) {
            throw new IllegalArgumentException("Debe seleccionar un estado válido.");
        }
        if (comentario == null || comentario.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe registrar un comentario de seguimiento.");
        }

        var ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket no encontrado."));

        if (tecnico != null && ticket.getTecnicoAsignado() != null
                && !ticket.getTecnicoAsignado().getId().equals(tecnico.getId())) {
            throw new IllegalArgumentException("El seguimiento debe registrarlo el técnico asignado.");
        }

        ticketRepository.addHistorial(ticketId, nuevoEstado, comentario.trim());
    }

    public List<Historial> obtenerHistorial(int ticketId) {
        if (ticketId <= 0) {
            throw new IllegalArgumentException("Ticket inválido.");
        }
        return ticketRepository.findHistorialByTicket(ticketId);
    }
}
