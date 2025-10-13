/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Modelo.dominio.*;
import Modelo.servicios.AssignmentService;
import Modelo.servicios.TicketService;
import Modelo.servicios.WorkflowService;

import java.util.List;


/**
 *
 * @author Méndez
 */

public class GestionTicketsController {

    private final TicketService ticketService;
    private final AssignmentService assignmentService;
    private final WorkflowService workflowService;

    public GestionTicketsController(TicketService ticketService,
                                    AssignmentService assignmentService,
                                    WorkflowService workflowService){
        this.ticketService = ticketService;
        this.assignmentService = assignmentService;
        this.workflowService = workflowService;
    }

    // consultas
    public List<Ticket> getTickets(){ return ticketService.listTickets(null); }
    public List<Empleado> getEmpleados(){ return assignmentService.listEmpleados(); }

    // acciones
    public void eliminar(int ticketId){ ticketService.deleteTicket(ticketId); }
    public void asignar(int ticketId, int empleadoId){ assignmentService.assign(ticketId, empleadoId); }
    public void actualizarEstado(int ticketId, Estado estado, String comentario){
        workflowService.advanceStatus(ticketId, estado, null, comentario);
    }

    // filtro por solicitante/carnet
    public List<Ticket> filtrarPorSolicitante(String query){
        return ticketService.listTickets(query);
    }
}
