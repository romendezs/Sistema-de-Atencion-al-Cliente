/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dominio;

import java.time.LocalDate;

/**
 *
 * @author Méndez
 */
public class Seguimiento {

    private Estado estado;
    private String comentario;
    private LocalDate fecha;

    public Seguimiento(Estado estado, String comentario, LocalDate fecha) {
        this.estado = estado;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public String getComentario() {
        return comentario;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}
