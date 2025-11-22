package modelo.repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import modelo.dominio.Facultad;
import modelo.repo.IRepository.IFacultadRepository;

public class FacultadRepository extends BaseJpaRepository implements IFacultadRepository {

    @Override
    public List<Facultad> findAll() {
        EntityManager em = em();
        try {
            TypedQuery<Facultad> q = em.createQuery(
                    "SELECT f FROM Facultad f ORDER BY f.nombre",
                    Facultad.class
            );
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Facultad> findById(int id) {
        EntityManager em = em();
        try {
            return Optional.ofNullable(em.find(Facultad.class, id));
        } finally {
            em.close();
        }
    }

    // ===== CRUD web =====

    @Override
    public void save(Facultad f) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.persist(f);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Facultad f) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.merge(f);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int id) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            Facultad ref = em.find(Facultad.class, id);
            if (ref != null) {
                em.remove(ref);
            }
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
