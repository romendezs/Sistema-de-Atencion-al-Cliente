package modelo.repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import modelo.dominio.Carrera;
import modelo.repo.IRepository.ICarreraRepository;

public class CarreraRepository extends BaseJpaRepository implements ICarreraRepository {

    @Override
    public List<Carrera> findAll() {
        EntityManager em = em();
        try {
            TypedQuery<Carrera> q = em.createQuery(
                    "SELECT c FROM Carrera c ORDER BY c.id", Carrera.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Carrera> findByFacultadId(int idFacultad) {
        EntityManager em = em();
        try {
            TypedQuery<Carrera> q = em.createQuery(
                    "SELECT c FROM Carrera c WHERE c.facultad.id = :id ORDER BY c.nombre",
                    Carrera.class);
            q.setParameter("id", idFacultad);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Carrera> findById(int id) {
        EntityManager em = em();
        try {
            return Optional.ofNullable(em.find(Carrera.class, id));
        } finally {
            em.close();
        }
    }

    // ===== CRUD para entorno web (implementa interfaz) =====

    @Override
    public void save(Carrera c) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.persist(c);              // solo crear
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Carrera c) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.merge(c);               // solo actualizar
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
            Carrera ref = em.find(Carrera.class, id);
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
