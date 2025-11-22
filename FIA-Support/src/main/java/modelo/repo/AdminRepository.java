package modelo.repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import modelo.dominio.Administrador;
import modelo.repo.IRepository.IAdminRepository;

public class AdminRepository extends BaseJpaRepository implements IAdminRepository {

    @Override
    public Optional<Administrador> findByUsuario(String usuario) {
        if (usuario == null) {
            return Optional.empty();
        }

        String idUpper = java.text.Normalizer
                .normalize(usuario, java.text.Normalizer.Form.NFKC)
                .trim().toUpperCase(java.util.Locale.ROOT);

        EntityManager em = em();
        try {
            TypedQuery<Administrador> q = em.createQuery(
                "SELECT a FROM Administrador a WHERE UPPER(a.id) = :id",
                Administrador.class
            );
            q.setParameter("id", idUpper);
            return q.getResultStream().findFirst();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Administrador> findByCredenciales(String usuario, String passwordHash) {
        if (usuario == null || passwordHash == null) {
            return Optional.empty();
        }

        String idUpper = java.text.Normalizer
                .normalize(usuario, java.text.Normalizer.Form.NFKC)
                .trim().toUpperCase(java.util.Locale.ROOT);

        EntityManager em = em();
        try {
            TypedQuery<Administrador> q = em.createQuery(
                "SELECT a FROM Administrador a "
              + "WHERE UPPER(a.id) = :id AND a.passwordHash = :pass",
                Administrador.class
            );
            q.setParameter("id", idUpper);
            q.setParameter("pass", passwordHash);

            return q.getResultStream().findFirst();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Administrador> findAll() {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT a FROM Administrador a", Administrador.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void save(Administrador admin) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.persist(admin);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Administrador admin) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            em.merge(admin);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(String usuario) {
        if (usuario == null) return;

        EntityManager em = em();
        try {
            em.getTransaction().begin();
            Administrador a = em.find(Administrador.class, usuario);
            if (a != null) {
                em.remove(a);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
