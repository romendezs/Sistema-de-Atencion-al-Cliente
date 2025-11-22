package modelo.repo;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import modelo.dominio.TicketMetricasDiarias;
import modelo.dto.ConteoPorCategoriaDTO;
import modelo.dto.ConteoPorEstadoDTO;
import modelo.dto.ConteoPorTecnicoDTO;
import modelo.repo.IRepository.IEstadisticasAdminRepository;

public class EstadisticasAdminRepository extends BaseJpaRepository implements IEstadisticasAdminRepository {

    @Override
    public List<ConteoPorEstadoDTO> conteoPorEstadoGlobal() {
        EntityManager em = em();
        try {
            List<Object[]> rows = em.createQuery(
                    "SELECT t.estadoActual.tipo, COUNT(t) " +
                    "FROM Ticket t " +
                    "GROUP BY t.estadoActual.tipo " +
                    "ORDER BY 2 DESC",
                    Object[].class
            ).getResultList();

            return rows.stream()
                    .map(r -> new ConteoPorEstadoDTO(
                            (String) r[0],
                            (Long) r[1]
                    ))
                    .toList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<ConteoPorTecnicoDTO> conteoPorTecnico() {
        EntityManager em = em();
        try {
            List<Object[]> rows = em.createQuery(
                    "SELECT CONCAT(e.apellidos, ' ', e.nombres), COUNT(t) " +
                    "FROM Ticket t " +
                    "JOIN t.tecnicoAsignado e " +
                    "GROUP BY e.apellidos, e.nombres " +
                    "ORDER BY 2 DESC",
                    Object[].class
            ).getResultList();

            return rows.stream()
                    .map(r -> new ConteoPorTecnicoDTO(
                            (String) r[0],
                            (Long) r[1]
                    ))
                    .toList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<ConteoPorCategoriaDTO> conteoPorCategoria() {
        EntityManager em = em();
        try {
            List<Object[]> rows = em.createQuery(
                    "SELECT h.categoria, COUNT(DISTINCT h.ticket.id) " +
                    "FROM TicketCategoriaHistorial h " +
                    "GROUP BY h.categoria " +
                    "ORDER BY 2 DESC",
                    Object[].class
            ).getResultList();

            return rows.stream()
                    .map(r -> new ConteoPorCategoriaDTO(
                            (String) r[0],
                            (Long) r[1]
                    ))
                    .toList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<TicketMetricasDiarias> metricasDiarias(LocalDate desde, LocalDate hasta) {
        EntityManager em = em();
        try {
            var q = em.createQuery(
                    "SELECT m FROM TicketMetricasDiarias m " +
                    "WHERE m.id.dia BETWEEN :d AND :h " +
                    "ORDER BY m.id.dia",
                    TicketMetricasDiarias.class
            );
            q.setParameter("d", desde);
            q.setParameter("h", hasta);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Double promedioResolucionMinGlobal(LocalDate desde, LocalDate hasta) {
        EntityManager em = em();
        try {
            var q = em.createQuery(
                    "SELECT AVG(EXTRACT(EPOCH FROM (t.actualizadoEn - t.creadoEn)))/60.0 " +
                    "FROM Ticket t " +
                    "WHERE t.estadoActual.tipo = 'Cerrado' " +
                    "AND t.creadoEn BETWEEN :d AND :h",
                    Double.class
            );
            q.setParameter("d", desde.atStartOfDay());
            q.setParameter("h", hasta.plusDays(1).atStartOfDay().minusSeconds(1));
            return q.getSingleResult();
        } finally {
            em.close();
        }
    }
}
