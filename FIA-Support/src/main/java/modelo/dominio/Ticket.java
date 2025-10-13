/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dominio;

import java.util.List;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
    private List<Seguimiento> historial = new ArrayList<>();

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

    public List<Seguimiento> getHistorial() {
        return historial;
    }

    public void setHistorial(List<Seguimiento> historial) {
        this.historial = historial;
    }

    // helper de dominio
    public void agregarSeguimiento(Seguimiento s) {
        s.setTicket(this);
        this.historial.add(s);
        this.estadoActual = s.getEstado(); // si así manejas el flujo
        this.actualizadoEn = LocalDateTime.now();
    }
}
