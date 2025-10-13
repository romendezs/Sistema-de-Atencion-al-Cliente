package Modelo.servicios;

import Modelo.dominio.Empleado;
import Modelo.dominio.Estado;
import Modelo.dominio.Ticket;
import Modelo.repo.ITicketRepository;

import java.util.Objects;

/**
 * Servicio para el caso de uso "Seguir Estado".
 */
public class WorkflowService {
    private final ITicketRepository tickets;

    public WorkflowService(ITicketRepository tickets) {
        this.tickets = Objects.requireNonNull(tickets, "tickets");
    }

    public void advanceStatus(int ticketId, Estado nuevoEstado, Integer tecnicoId, String comentario) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El estado es obligatorio.");
        }
        asegurarTicket(ticketId);

        if (nuevoEstado == Estado.ASIGNADO) {
            if (tecnicoId == null) {
                throw new IllegalArgumentException("Debe indicar el técnico para asignar.");
            }
            asegurarEmpleado(tecnicoId);
            tickets.assignToEmpleado(ticketId, tecnicoId);
        } else {
            if (tecnicoId != null) {
                asegurarEmpleado(tecnicoId);
            }
            String nota = (comentario == null || comentario.trim().isEmpty())
                    ? "Estado actualizado a " + nuevoEstado.getRotulo()
                    : comentario.trim();
            tickets.addSeguimiento(ticketId, nuevoEstado, nota);
        }
    }

    private Ticket asegurarTicket(int ticketId) {
        Ticket ticket = tickets.findById(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket no encontrado.");
        }
        return ticket;
    }

    private Empleado asegurarEmpleado(int empleadoId) {
        return tickets.findAllEmpleados().stream()
                .filter(e -> e.getId() == empleadoId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado."));
    }
}
