/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dominio;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;
import java.time.LocalDate;
import modelo.dominio.pk.TicketMetricasDiariasId;

/**
 *
 * @author Méndez
 */
// modelo/dominio/TicketMetricasDiarias.java
@Entity
@Table(name = "ticket_metricas_diarias")
@Immutable
public class TicketMetricasDiarias {

    @EmbeddedId
    private TicketMetricasDiariasId id;

    // métricas (ajusta nombres/typos exactos de columnas)
    @Column(name = "abiertos")
    private Integer abiertos;

    @Column(name = "cerrados")
    private Integer cerrados;

    @Column(name = "en_proceso")
    private Integer enProceso;

    @Column(name = "promedio_resolucion_min")
    private Double promedioResolucionMin;

    public TicketMetricasDiariasId getId() {
        return id;
    }

    // helpers de lectura
    public LocalDate getDia() {
        return id != null ? id.getDia() : null;
    }

    public Integer getAbiertos() {
        return abiertos;
    }

    public Integer getCerrados() {
        return cerrados;
    }

    public Integer getEnProceso() {
        return enProceso;
    }

    public Double getPromedioResolucionMin() {
        return promedioResolucionMin;
    }
}
