/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Modelo.dominio.*;
import Modelo.repo.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 *
 * @author Méndez
 */

public class GestionTicketsController {

    private final ITicketRepository repo;

    public GestionTicketsController(ITicketRepository repo){
        this.repo = repo;
    }

    // consultas
    public List<Ticket> getTickets(){ return repo.findAll(); }
    public List<Empleado> getEmpleados(){ return repo.findAllEmpleados(); }

    // acciones
    public void eliminar(int ticketId){ repo.deleteById(ticketId); }
    public void asignar(int ticketId, int empleadoId){ repo.assignToEmpleado(ticketId, empleadoId); }
    public void actualizarEstado(int ticketId, Estado estado, String comentario){
        repo.addSeguimiento(ticketId, estado, comentario);
    }

    // filtro por solicitante/carnet
    public List<Ticket> filtrarPorSolicitante(String query){
        if (query==null || query.trim().isEmpty()) return getTickets();
        final String q = query.trim().toLowerCase();
        return getTickets().stream().filter(t ->
            t.getSolicitante().getId().toLowerCase().contains(q) ||
            t.getSolicitante().toString().toLowerCase().contains(q)
        ).collect(Collectors.toList());
    }
}
