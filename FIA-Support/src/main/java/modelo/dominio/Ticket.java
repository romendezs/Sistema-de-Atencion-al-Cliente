/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dominio;

import java.util.List;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static modelo.db.JPAUtil.em;

/**
 *
 * @author Méndez
 */
@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    private Integer id;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "text")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false) // FK -> usuario_final.id_usuario
    private UsuarioFinal solicitante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado") // técnico asignado, puede ser null
    private Empleado tecnicoAsignado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_estado", nullable = false) // FK -> estado.id_estado
    private Estado estadoActual;

    @Column(name = "creado_en", nullable = false)
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("fecha ASC, id ASC")
    private List<Historial> historial = new ArrayList<>();

    public Ticket() {
    }

    // getters/setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public UsuarioFinal getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(UsuarioFinal solicitante) {
        this.solicitante = solicitante;
    }

    public Empleado getTecnicoAsignado() {
        return tecnicoAsignado;
    }

    public void setTecnicoAsignado(Empleado tecnicoAsignado) {
        this.tecnicoAsignado = tecnicoAsignado;
    }

    public Estado getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(Estado estadoActual) {
        this.estadoActual = estadoActual;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public void setActualizadoEn(LocalDateTime actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }

    public List<Historial> getHistorial() {
        return historial;
    }

    public void setHistorial(List<Historial> historial) {
        this.historial = historial;
    }

    public void addHistorial(Historial h) {
        h.setTicket(this);                 // dueño de la relación es Historial (FK)
        this.historial.add(h);
        this.setEstadoActual(h.getEstado());
        this.setActualizadoEn(java.time.LocalDateTime.now());
    }

    public void addHistorial(int ticketId, int estadoId, String texto) {
        var em = em();
        try {
            em.getTransaction().begin();
            var t = em.find(Ticket.class, ticketId);
            if (t == null) {
                throw new IllegalArgumentException("Ticket no encontrado: " + ticketId);
            }

            var estado = em.getReference(Estado.class, estadoId);

            var h = new Historial(t, estado, texto, java.time.OffsetDateTime.now());
            em.persist(h);

            t.setEstadoActual(estado);
            t.setActualizadoEn(java.time.LocalDateTime.now());

            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

}
