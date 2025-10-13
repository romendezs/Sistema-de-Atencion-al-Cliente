/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Modelo.repo;

import java.util.List;
import Modelo.dominio.*;

/**
 *
 * @author Méndez
 */
public interface ITicketRepository {

    // Lecturas
    List<Ticket> findAll();

    Ticket findById(int id);

    List<Empleado> findAllEmpleados();

    Ticket create(UsuarioFinal solicitante, String titulo, String descripcion);

    void update(int ticketId, String titulo, String descripcion);

    // Escrituras
    void deleteById(int ticketId);

    void cancel(int ticketId, String comentario);

    void assignToEmpleado(int ticketId, int empleadoId);

    void addSeguimiento(int ticketId, Estado estado, String comentario);

    // Solo para demo (semilla)
    default void seed() {
    }
}
