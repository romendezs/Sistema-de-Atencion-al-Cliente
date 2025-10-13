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
@Table(name = "ticket_categoria_historial")
@Immutable
public class TicketCategoriaHistorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria_historial")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ticket", nullable = false)
    private Ticket ticket;

    // Si tu tabla guarda FK numérica a una tabla categoria:
    @Column(name = "id_categoria")
    private Integer idCategoria;

    // y si además guarda el nombre (común en historiales):
    @Column(name = "categoria", length = 150)
    private String categoria;

    @Column(name = "comentario", length = 500)
    private String comentario;

    @Column(name = "fecha", nullable = false)
    private OffsetDateTime fecha;

    public Integer getId() {
        return id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getComentario() {
        return comentario;
    }

    public OffsetDateTime getFecha() {
        return fecha;
    }
}
