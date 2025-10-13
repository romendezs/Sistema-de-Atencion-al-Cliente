package modelo.servicios;

import java.util.List;
import modelo.dominio.Empleado;
import modelo.dominio.TicketAsignacionHistorial;
import modelo.repo.IRepository.ITicketRepository;

/**
 * Servicio para el caso de uso "Asignar Solicititud". Se encarga de validar
 * los parámetros y delegar en el repositorio la asignación o reasignación de
 * técnicos, así como la consulta del historial.
 */
public class AssignmentService {

    private final ITicketRepository ticketRepository;

    public AssignmentService(ITicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Empleado> listTechnicians() {
        return ticketRepository.findAllEmpleados();
    }

    public void assign(int ticketId, int empleadoId) {
        validateIds(ticketId, empleadoId);
        ticketRepository.assignToEmpleado(ticketId, empleadoId);
    }

    public void reassign(int ticketId, int empleadoId) {
        assign(ticketId, empleadoId);
    }

    public List<TicketAsignacionHistorial> history(int ticketId) {
        if (ticketId <= 0) {
            throw new IllegalArgumentException("Ticket inválido.");
        }
        return ticketRepository.findAsignacionesByTicket(ticketId);
    }

    private void validateIds(int ticketId, int empleadoId) {
        if (ticketId <= 0) {
            throw new IllegalArgumentException("Ticket inválido.");
        }
        if (empleadoId <= 0) {
            throw new IllegalArgumentException("Empleado inválido.");
        }
    }
}
