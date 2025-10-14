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
        if (usuario == null) {
            return Optional.empty();
        }
        String idUpper = java.text.Normalizer
                .normalize(usuario, java.text.Normalizer.Form.NFKC)
                .trim().toUpperCase(java.util.Locale.ROOT);

        EntityManager em = em();
        try {
            var q = em.createQuery(
                    "SELECT a FROM Administrador a WHERE UPPER(a.id) = :id",
                    Administrador.class
            );
            q.setParameter("id", idUpper);
            return q.getResultStream().findFirst();
        } finally {
            em.close();
        }
    }
}
