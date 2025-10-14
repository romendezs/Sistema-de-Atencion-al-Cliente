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
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_solicitante", nullable = false)
    private UsuarioFinal solicitante;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_empleado") // técnico asignado, puede ser null
    private Empleado tecnicoAsignado;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;
    
    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "text")
    private String descripcion;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime creadoEn;

    @Column(name = "fecha_cierre")
    private LocalDateTime actualizadoEn;

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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
}
