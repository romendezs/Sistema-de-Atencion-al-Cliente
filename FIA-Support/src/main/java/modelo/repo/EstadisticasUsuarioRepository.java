/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.repo;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import modelo.repo.IRepository.IEstadisticasUsuarioRepository;
/**
 *
 * @author Méndez
 */

public class EstadisticasUsuarioRepository extends BaseJpaRepository implements IEstadisticasUsuarioRepository {

    @Override
    public List<Object[]> conteoPorEstadoUsuario(String usuarioId) {
        var em = em();
        try {
            // cuenta tickets por estado del solicitante
            var q = em.createQuery(
                    "SELECT t.estadoActual.tipo, COUNT(t) "
                    + "FROM Ticket t "
                    + "WHERE t.solicitante.id = :uid "
                    + "GROUP BY t.estadoActual.tipo "
                    + "ORDER BY 2 DESC", Object[].class);
            q.setParameter("uid", usuarioId);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Object[]> conteoPorDiaUsuario(String usuarioId, LocalDate desde, LocalDate hasta) {
        var em = em();
        try {
            // agrupa por fecha de creación (ajusta si quieres cierre)
            var q = em.createQuery(
                    "SELECT DATE(t.creadoEn), COUNT(t) "
                    + "FROM Ticket t "
                    + "WHERE t.solicitante.id = :uid "
                    + "AND t.creadoEn BETWEEN :d AND :h "
                    + "GROUP BY DATE(t.creadoEn) "
                    + "ORDER BY DATE(t.creadoEn)", Object[].class);
            q.setParameter("uid", usuarioId);
            q.setParameter("d", desde.atStartOfDay());
            q.setParameter("h", hasta.plusDays(1).atStartOfDay().minusSeconds(1));
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Double promedioResolucionMinUsuario(String usuarioId, LocalDate desde, LocalDate hasta) {
        var em = em();
        try {
            // ejemplo: diferencia entre creadoEn y actualizadoEn cuando estado=CERRADO
            var q = em.createQuery(
                    "SELECT AVG(EXTRACT(EPOCH FROM (t.actualizadoEn - t.creadoEn)))/60.0 "
                    + "FROM Ticket t "
                    + "WHERE t.solicitante.id = :uid "
                    + "AND t.estadoActual.tipo = 'Cerrado' "
                    + "AND t.creadoEn BETWEEN :d AND :h", Double.class);
            q.setParameter("uid", usuarioId);
            q.setParameter("d", desde.atStartOfDay());
            q.setParameter("h", hasta.plusDays(1).atStartOfDay().minusSeconds(1));
            return q.getSingleResult();
        } finally {
            em.close();
        }
    }
}
