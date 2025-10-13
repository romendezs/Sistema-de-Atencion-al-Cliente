/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dominio;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Méndez
 */
public class Ticket {

    private final int id;
    private final UsuarioFinal solicitante;
    private String titulo;
    private String descripcion;

    private Empleado asignado;       // null = sin asignar
    private Estado estadoActual;     // redundante, igual al último del historial
    private final List<Seguimiento> historial = new ArrayList<>();

    public Ticket(int id, UsuarioFinal solicitante, String titulo, String descripcion) {
        this.id = id;
        this.solicitante = solicitante;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estadoActual = Estado.PENDIENTE;
    }

    public int getId() {
        return id;
    }

    public UsuarioFinal getSolicitante() {
        return solicitante;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Empleado getAsignado() {
        return asignado;
    }

    public void setAsignado(Empleado e) {
        this.asignado = e;
    }

    public Estado getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(Estado e) {
        this.estadoActual = e;
    }

    public List<Seguimiento> getHistorial() {
        return historial;
    }
}
