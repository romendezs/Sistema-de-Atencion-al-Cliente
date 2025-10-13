/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dominio.pk;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Méndez
 */
// modelo/dominio/pk/TicketMetricasDiariasId.java
@Embeddable
public class TicketMetricasDiariasId implements Serializable {

    @Column(name = "dia", nullable = false)
    private LocalDate dia;

    @Column(name = "id_categoria", nullable = false)
    private Integer idCategoria;

    @Column(name = "id_empleado", nullable = false)
    private Integer idEmpleado;

    @Column(name = "canal", nullable = false, length = 50)
    private String canal;

    public TicketMetricasDiariasId() {
    }

    public TicketMetricasDiariasId(LocalDate dia, Integer idCategoria, Integer idEmpleado, String canal) {
        this.dia = dia;
        this.idCategoria = idCategoria;
        this.idEmpleado = idEmpleado;
        this.canal = canal;
    }

    public LocalDate getDia() {
        return dia;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public String getCanal() {
        return canal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketMetricasDiariasId)) {
            return false;
        }
        TicketMetricasDiariasId x = (TicketMetricasDiariasId) o;
        return Objects.equals(dia, x.dia)
                && Objects.equals(idCategoria, x.idCategoria)
                && Objects.equals(idEmpleado, x.idEmpleado)
                && Objects.equals(canal, x.canal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dia, idCategoria, idEmpleado, canal);
    }
}
