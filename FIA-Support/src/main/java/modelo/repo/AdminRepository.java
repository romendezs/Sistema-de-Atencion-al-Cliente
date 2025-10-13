package modelo.repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.Optional;
import modelo.dominio.Administrador;
import modelo.repo.IRepository.IAdminRepository;

/**
 * Repositorio JPA para credenciales de administradores.
 */
public class AdminRepository extends BaseJpaRepository implements IAdminRepository {

    @Override
    public Optional<Administrador> findByUsuario(String usuario) {
        if (usuario == null || usuario.trim().isEmpty()) {
            return Optional.empty();
        }
        EntityManager em = em();
        try {
            TypedQuery<Administrador> query = em.createQuery(
                    "SELECT a FROM Administrador a WHERE UPPER(a.usuario) = :usuario",
                    Administrador.class);
            query.setParameter("usuario", usuario.trim().toUpperCase());
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }
}
