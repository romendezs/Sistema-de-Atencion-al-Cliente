/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.repo;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import modelo.dominio.TicketMetricasDiarias;
import modelo.repo.IRepository.IEstadisticasAdminRepository;

/**
 *
 * @author Méndez
 */
// db/repo/EstadisticasAdminRepository.java
public class EstadisticasAdminRepository extends BaseJpaRepository implements IEstadisticasAdminRepository {

    @Override
    public List<Object[]> conteoPorEstadoGlobal() {
        var em = em();
        try {
            var q = em.createQuery(
                    "SELECT t.estadoActual.tipo, COUNT(t) "
                    + "FROM Ticket t "
                    + "GROUP BY t.estadoActual.tipo "
                    + "ORDER BY 2 DESC", Object[].class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Object[]> conteoPorTecnico() {
        var em = em();
        try {
            var q = em.createQuery(
                    "SELECT CONCAT(e.apellidos, ' ', e.nombres), COUNT(t) "
                    + "FROM Ticket t "
                    + "JOIN t.tecnicoAsignado e "
                    + "GROUP BY e.apellidos, e.nombres "
                    + "ORDER BY 2 DESC", Object[].class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Object[]> conteoPorCategoria() {
        var em = em();
        try {
            // Si tienes una tabla 'categoria' referenciada en Ticket, reemplaza por JOIN a esa entidad.
            // Aquí usamos el histórico, asumiendo que guardó el nombre en la fila más reciente por ticket.
            var q = em.createQuery(
                    "SELECT h.categoria, COUNT(DISTINCT h.ticket.id) "
                    + "FROM TicketCategoriaHistorial h "
                    + "GROUP BY h.categoria "
                    + "ORDER BY 2 DESC", Object[].class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<TicketMetricasDiarias> metricasDiarias(LocalDate desde, LocalDate hasta) {
        var em = em();
        try {
            var q = em.createQuery(
                    "SELECT m FROM TicketMetricasDiarias m "
                    + "WHERE m.id.dia BETWEEN :d AND :h "
                    + // <-- usa la clave compuesta
                    "ORDER BY m.id.dia", TicketMetricasDiarias.class);
            q.setParameter("d", desde);
            q.setParameter("h", hasta);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Double promedioResolucionMinGlobal(LocalDate desde, LocalDate hasta) {
        var em = em();
        try {
            var q = em.createQuery(
                    "SELECT AVG(EXTRACT(EPOCH FROM (t.actualizadoEn - t.creadoEn)))/60.0 "
                    + "FROM Ticket t "
                    + "WHERE t.estadoActual.tipo = 'Cerrado' "
                    + "AND t.creadoEn BETWEEN :d AND :h", Double.class);
            q.setParameter("d", desde.atStartOfDay());
            q.setParameter("h", hasta.plusDays(1).atStartOfDay().minusSeconds(1));
            return q.getSingleResult();
        } finally {
            em.close();
        }
    }
}
