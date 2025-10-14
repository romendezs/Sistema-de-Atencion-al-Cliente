/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import modelo.dominio.*;
import modelo.repo.IRepository.ITicketRepository;

/**
 *
 * @author Méndez
 */
public class TicketRepository extends BaseJpaRepository implements ITicketRepository {

    @Override
    public List<Ticket> findAll() {
        EntityManager em = em();
        try {
            TypedQuery<Ticket> q = em.createQuery(
                    "SELECT t FROM Ticket t ORDER BY t.creadoEn DESC", Ticket.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Ticket> findById(int id) {
        EntityManager em = em();
        try {
            return Optional.ofNullable(em.find(Ticket.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Ticket> findByUsuarioId(String usuarioId) {
        EntityManager em = em();
        try {
            TypedQuery<Ticket> q = em.createQuery(
                    "SELECT t FROM Ticket t WHERE t.solicitante.id = :uid ORDER BY t.creadoEn DESC", Ticket.class);
            q.setParameter("uid", usuarioId);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Ticket> findByEstadoId(int estadoId) {
        EntityManager em = em();
        try {
            TypedQuery<Ticket> q = em.createQuery(
                    "SELECT t FROM Ticket t WHERE t.estadoActual.id = :eid ORDER BY t.creadoEn DESC", Ticket.class);
            q.setParameter("eid", estadoId);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Ticket> findByRangoFechas(LocalDateTime desde, LocalDateTime hasta) {
        EntityManager em = em();
        try {
            TypedQuery<Ticket> q = em.createQuery(
                    "SELECT t FROM Ticket t WHERE t.creadoEn BETWEEN :d AND :h ORDER BY t.creadoEn DESC", Ticket.class);
            q.setParameter("d", desde);
            q.setParameter("h", hasta);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    // CRUD
    @Override
    public Ticket save(Ticket t) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            if (t.getId() == null) {
                em.persist(t);
            } else {
                t = em.merge(t);
            }
            em.getTransaction().commit();
            return t;
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Historial> findHistorialByTicket(int ticketId) {
        EntityManager em = em();
        try {
            TypedQuery<Historial> q = em.createQuery(
                    "SELECT h FROM Historial h WHERE h.ticket.id = :tid ORDER BY h.fecha ASC", Historial.class);
            q.setParameter("tid", ticketId);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Estado> findAllEstados() {
        EntityManager em = em();
        try {
            TypedQuery<Estado> q = em.createQuery(
                    "SELECT e FROM Estado e ORDER BY e.tipo", Estado.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<TicketAsignacionHistorial> findAsignacionesByTicket(int ticketId) {
        EntityManager em = em();
        try {
            TypedQuery<TicketAsignacionHistorial> q = em.createQuery(
                    "SELECT a FROM TicketAsignacionHistorial a WHERE a.ticket.id = :tid ORDER BY a.fecha DESC",
                    TicketAsignacionHistorial.class);
            q.setParameter("tid", ticketId);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public void deleteById(int id) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            Ticket ref = em.find(Ticket.class, id);
            if (ref != null) {
                em.remove(ref);
            }
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Empleado> findAllEmpleados() {
        EntityManager em = em();
        try {
            TypedQuery<Empleado> q = em.createQuery(
                    "SELECT e FROM Empleado e ORDER BY e.apellidos, e.nombres", Empleado.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void assignToEmpleado(int ticketId, int empleadoId) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();

            Ticket t = em.find(Ticket.class, ticketId);
            if (t == null) {
                em.getTransaction().rollback();
                throw new IllegalArgumentException("Ticket no encontrado: " + ticketId);
            }

            Empleado emp = em.find(Empleado.class, empleadoId);
            if (emp == null) {
                em.getTransaction().rollback();
                throw new IllegalArgumentException("Empleado no encontrado: " + empleadoId);
            }

            // asignar técnico
            t.setTecnicoAsignado(emp);
            t.setActualizadoEn(LocalDateTime.now());

            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public void addHistorial(int ticketId, Estado estado, String comentario) {
        var em = em();
        try {
            em.getTransaction().begin();

            // 1) Cargar el ticket
            var t = em.find(Ticket.class, ticketId);
            if (t == null) {
                em.getTransaction().rollback();
                throw new IllegalArgumentException("Ticket no encontrado: " + ticketId);
            }

            if (estado == null || estado.getId() == null) {
                em.getTransaction().rollback();
                throw new IllegalArgumentException("Estado inválido (null o sin ID).");
            }

            // 2) Asegurar que 'estado' esté administrado (por si viene detached/transient)
            var estadoRef = em.getReference(Estado.class, estado.getId());

            // 3) Crear y persistir registro en tabla 'historial'
            var h = new Historial();
            h.setTicket(t);
            h.setEstado(estadoRef);
            h.setSeguimiento(comentario);
            h.setFecha(java.time.OffsetDateTime.now()); // timestamp with time zone

            em.persist(h);

            // 4) Actualizar el estado/timestamp del ticket
            t.setActualizadoEn(java.time.LocalDateTime.now());

            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }
    
    @Override
    public Optional<Historial> findUltimoHistorial(int ticketId) {
        EntityManager em = em();
        try {
            Historial ultimo = em.createQuery("""
                SELECT h
                FROM Historial h
                WHERE h.ticket.id = :ticketId
                ORDER BY h.id DESC
            """, Historial.class)
            .setParameter("ticketId", ticketId)
            .setMaxResults(1)
            .getSingleResult();

            return Optional.of(ultimo);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

}
