/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dominio;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;
import java.time.OffsetDateTime;

/**
 *
 * @author Méndez
 */
@Entity
@Table(name = "ticket_asignacion_historial")
@Immutable
public class TicketAsignacionHistorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignacion_historial")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ticket", nullable = false)
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado; // técnico asignado

    @Column(name = "comentario", length = 500)
    private String comentario;

    @Column(name = "fecha", nullable = false) // timestamp with time zone
    private OffsetDateTime fecha;

    public Integer getId() {
        return id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public String getComentario() {
        return comentario;
    }

    public OffsetDateTime getFecha() {
        return fecha;
    }
}
