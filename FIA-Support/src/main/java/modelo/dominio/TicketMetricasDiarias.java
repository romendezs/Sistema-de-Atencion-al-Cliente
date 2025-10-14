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
@Entity
@Table(
        name = "ticket_metricas_diarias",
        indexes = {
            @Index(name = "idx_tmd_canal_dia", columnList = "canal, dia"),
            @Index(name = "idx_tmd_categoria_dia", columnList = "id_categoria, dia"),
            @Index(name = "idx_tmd_dia", columnList = "dia"),
            @Index(name = "idx_tmd_empleado_dia", columnList = "id_empleado, dia")
        }
)
@Immutable
public class TicketMetricasDiarias {

    @EmbeddedId
    private TicketMetricasDiariasId id;

    @Column(name = "abiertos")
    private Integer abiertos;

    @Column(name = "cerrados")
    private Integer cerrados;

    @Column(name = "reabiertos")
    private Integer reabiertos;

    @Column(name = "frt_segundos_promedio")
    private Long frtSegundosPromedio;

    @Column(name = "rt_segundos_promedio")
    private Long rtSegundosPromedio;

    @Column(name = "rt_p50_segundos")
    private Long rtP50Segundos;

    @Column(name = "rt_p90_segundos")
    private Long rtP90Segundos;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idCategoria")
    @JoinColumn(name = "id_categoria", insertable = false, updatable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idEmpleado")
    @JoinColumn(name = "id_empleado", insertable = false, updatable = false)
    private Empleado empleado;

    protected TicketMetricasDiarias() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final TicketMetricasDiariasId id = new TicketMetricasDiariasId();
        private final TicketMetricasDiarias entity = new TicketMetricasDiarias();

        public Builder dia(LocalDate dia) {
            id.setDia(dia);
            return this;
        }

        public Builder idCategoria(Integer idCategoria) {
            id.setIdCategoria(idCategoria);
            return this;
        }

        public Builder idEmpleado(Integer idEmpleado) {
            id.setIdEmpleado(idEmpleado);
            return this;
        }

        public Builder canal(String canal) {
            id.setCanal(canal);
            return this;
        }

        public Builder abiertos(Integer abiertos) {
            entity.abiertos = abiertos;
            return this;
        }

        public Builder cerrados(Integer cerrados) {
            entity.cerrados = cerrados;
            return this;
        }

        public Builder reabiertos(Integer reabiertos) {
            entity.reabiertos = reabiertos;
            return this;
        }

        public Builder frtSegundosPromedio(Long v) {
            entity.frtSegundosPromedio = v;
            return this;
        }

        public Builder rtSegundosPromedio(Long v) {
            entity.rtSegundosPromedio = v;
            return this;
        }

        public Builder rtP50Segundos(Long v) {
            entity.rtP50Segundos = v;
            return this;
        }

        public Builder rtP90Segundos(Long v) {
            entity.rtP90Segundos = v;
            return this;
        }

        public TicketMetricasDiarias build() {
            entity.id = this.id;
            return entity;
        }
    }

    // 🔍 Getters
    public TicketMetricasDiariasId getId() {
        return id;
    }

    public Integer getAbiertos() {
        return abiertos;
    }

    public Integer getCerrados() {
        return cerrados;
    }

    public Integer getReabiertos() {
        return reabiertos;
    }

    public Long getFrtSegundosPromedio() {
        return frtSegundosPromedio;
    }

    public Long getRtSegundosPromedio() {
        return rtSegundosPromedio;
    }

    public Long getRtP50Segundos() {
        return rtP50Segundos;
    }

    public Long getRtP90Segundos() {
        return rtP90Segundos;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    // En TicketMetricasDiarias.java
    @Transient
    public Double getPromedioResolucionMin() {
        if (rtSegundosPromedio == null) {
            return null;
        }
        return rtSegundosPromedio / 60.0;
    }

}
