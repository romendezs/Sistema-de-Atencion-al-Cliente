/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dominio;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.Immutable;
import java.time.OffsetDateTime;
import org.hibernate.annotations.CreationTimestamp;

/**
 *
 * @author Méndez
 */
import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

@Entity
@Table(
        name = "ticket_categoria_historial",
        indexes = {
            @Index(name = "idx_tch_categoria_nueva_fecha", columnList = "id_categoria_nueva, fecha"),
            @Index(name = "idx_tch_ticket_fecha", columnList = "id_ticket, fecha")
        }
)
@Immutable // history rows are append-only; Hibernate won’t update them
public class TicketCategoriaHistorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cambio")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ticket", nullable = false)
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria_anterior") // nullable in DB
    private Categoria categoriaAnterior;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria_nueva") // nullable in DB
    private Categoria categoriaNueva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_cambio") // nullable in DB
    private Usuario usuarioCambio;

    @CreationTimestamp
    @Column(name = "fecha", nullable = false, updatable = false)
    private LocalDateTime fecha;

    // --- getters/setters ---
    public Integer getId() {
        return id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Categoria getCategoriaAnterior() {
        return categoriaAnterior;
    }

    public void setCategoriaAnterior(Categoria categoriaAnterior) {
        this.categoriaAnterior = categoriaAnterior;
    }

    public Categoria getCategoriaNueva() {
        return categoriaNueva;
    }

    public void setCategoriaNueva(Categoria categoriaNueva) {
        this.categoriaNueva = categoriaNueva;
    }

    public Usuario getUsuarioCambio() {
        return usuarioCambio;
    }

    public void setUsuarioCambio(Usuario usuarioCambio) {
        this.usuarioCambio = usuarioCambio;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    // equals/hashCode only by id (PK)
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketCategoriaHistorial)) {
            return false;
        }
        return id != null && id.equals(((TicketCategoriaHistorial) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
