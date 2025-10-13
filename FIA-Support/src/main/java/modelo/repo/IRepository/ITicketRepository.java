/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.repo.IRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import modelo.dominio.*;

/**
 *
 * @author Méndez
 */
public interface ITicketRepository {

    // Lecturas
    List<Ticket> findAll();

    Optional<Ticket> findById(int id);

    List<Ticket> findByUsuarioId(String usuarioId);

    List<Ticket> findByEstadoId(int estadoId);

    List<Ticket> findByRangoFechas(LocalDateTime desde, LocalDateTime hasta);

    List<Empleado> findAllEmpleados();

    Ticket save(Ticket ticket);

    List<Historial> findHistorialByTicket(int ticketId);

    List<TicketAsignacionHistorial> findAsignacionesByTicket(int ticketId);

    // Escrituras
    void deleteById(int ticketId);

    void assignToEmpleado(int ticketId, int empleadoId);

    void addHistorial(int ticketId, Estado estado, String comentario);
}
