package controlador;

import java.util.List;
import modelo.dominio.Empleado;
import modelo.servicios.AssignmentService;

/**
 * Controller for technician assignment workflows.
 */
public class AssignmentController {

    private final AssignmentService service;

    public AssignmentController(AssignmentService service) {
        this.service = service;
    }

    public List<Empleado> listTechnicians() {
        return service.listTechnicians();
    }

    public void assign(int ticketId, int empleadoId) {
        service.assign(ticketId, empleadoId);
    }

    public void reassign(int ticketId, int empleadoId) {
        service.reassign(ticketId, empleadoId);
    }
}
