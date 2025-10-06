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

    // Escrituras
    void deleteById(int ticketId);

    void assignToEmpleado(int ticketId, int empleadoId);

    void addSeguimiento(int ticketId, Estado estado, String comentario);

    // Solo para demo (semilla)
    default void seed() {
    }
}
