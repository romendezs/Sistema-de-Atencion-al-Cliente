/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dominio;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Objects;

/**
 *
 * @author Méndez
 */


@Entity
@Table(name = "historial")
public class Historial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ticket", nullable = false)   // FK -> ticket.id_ticket
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_estado", nullable = false)   // FK -> estado.id_estado
    private Estado estado;

    @Column(name = "seguimiento", length = 500)         // ajusta length si tu DDL dice otro
    private String seguimiento;

    // timestamp with time zone -> OffsetDateTime (Hibernate 6 / JPA 3)
    @Column(name = "fecha", nullable = false)
    private OffsetDateTime fecha;

    public Historial() {
    }

    public Historial(Ticket ticket, Estado estado, String seguimiento, OffsetDateTime fecha) {
        this.ticket = ticket;
        this.estado = estado;
        this.seguimiento = seguimiento;
        this.fecha = fecha;
    }

    // getters/setters
    public Integer getId() {
        return id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(String seguimiento) {
        this.seguimiento = seguimiento;
    }

    public OffsetDateTime getFecha() {
        return fecha;
    }

    public void setFecha(OffsetDateTime fecha) {
        this.fecha = fecha;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Historial)) {
            return false;
        }
        Historial that = (Historial) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
