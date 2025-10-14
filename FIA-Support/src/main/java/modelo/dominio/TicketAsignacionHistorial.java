/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dominio;

import jakarta.persistence.*;

/**
 *
 * @author Méndez
 */
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(
        name = "ticket_asignacion_historial",
        indexes = {
            @Index(name = "idx_tah_empleado_nuevo_fecha", columnList = "id_empleado_nuevo,fecha"),
            @Index(name = "idx_tah_ticket_fecha", columnList = "id_ticket,fecha")
        }
)
@Immutable // tabla de historial: solo lectura desde JPA
public class TicketAsignacionHistorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignacion")
    private Integer id;

    // DDL: timestamp without time zone -> usar LocalDateTime
    @CreationTimestamp
    @Column(name = "fecha", nullable = false,
            columnDefinition = "timestamp without time zone default now()")
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ticket", nullable = false)
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado_anterior")
    private Empleado empleadoAnterior;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado_nuevo")
    private Empleado empleadoNuevo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_cambio")
    private Usuario usuarioCambio;

    protected TicketAsignacionHistorial() {
    } // requerido por JPA

    public Integer getId() {
        return id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public Empleado getEmpleadoAnterior() {
        return empleadoAnterior;
    }

    public Empleado getEmpleadoNuevo() {
        return empleadoNuevo;
    }

    public Usuario getUsuarioCambio() {
        return usuarioCambio;
    }

    // equals/hashCode por id
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketAsignacionHistorial)) {
            return false;
        }
        TicketAsignacionHistorial that = (TicketAsignacionHistorial) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
