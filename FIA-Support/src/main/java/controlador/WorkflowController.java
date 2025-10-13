package controlador;

import java.util.List;
import modelo.dominio.Empleado;
import modelo.dominio.Estado;
import modelo.dominio.Historial;
import modelo.servicios.WorkflowService;

/**
 * Controller for ticket workflow tracking.
 */
public class WorkflowController {

    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    public void advanceStatus(int ticketId, Estado nuevoEstado, Empleado tecnico, String comentario) {
        workflowService.advanceStatus(ticketId, nuevoEstado, tecnico, comentario);
    }

    public List<Historial> historial(int ticketId) {
        return workflowService.obtenerHistorial(ticketId);
    }

    public List<Estado> estadosDisponibles() {
        return workflowService.listarEstadosDisponibles();
    }
}
