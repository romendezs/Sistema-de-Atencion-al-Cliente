package Modelo.servicios;

import Modelo.dominio.Empleado;
import Modelo.dominio.Ticket;
import Modelo.repo.ITicketRepository;

import java.util.List;
import java.util.Objects;

/**
 * Servicio para el caso de uso "Asignar Solicitud".
 */
public class AssignmentService {
    private final ITicketRepository tickets;

    public AssignmentService(ITicketRepository tickets) {
        this.tickets = Objects.requireNonNull(tickets, "tickets");
    }

    public void assign(int ticketId, int empleadoId) {
        asegurarTicket(ticketId);
        Empleado empleado = asegurarEmpleado(empleadoId);
        tickets.assignToEmpleado(ticketId, empleado.getId());
    }

    public void reassign(int ticketId, int empleadoId) {
        assign(ticketId, empleadoId);
    }

    public List<Empleado> listEmpleados() {
        return tickets.findAllEmpleados();
    }

    private Ticket asegurarTicket(int ticketId) {
        Ticket t = tickets.findById(ticketId);
        if (t == null) {
            throw new IllegalArgumentException("Ticket no encontrado.");
        }
        return t;
    }

    private Empleado asegurarEmpleado(int empleadoId) {
        if (empleadoId <= 0) {
            throw new IllegalArgumentException("Empleado inválido.");
        }
        return tickets.findAllEmpleados().stream()
                .filter(e -> e.getId() == empleadoId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado."));
    }
}
