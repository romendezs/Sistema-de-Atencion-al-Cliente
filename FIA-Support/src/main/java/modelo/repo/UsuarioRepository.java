/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import modelo.dominio.UsuarioFinal;
import modelo.repo.IRepository.IUsuarioRepository;

/**
 *
 * @author Méndez
 */
public class UsuarioRepository extends BaseJpaRepository implements IUsuarioRepository {

    @Override
    public Optional<UsuarioFinal> findById(String id) {
        if (id == null) return Optional.empty();
        String idUpper = java.text.Normalizer
                .normalize(id, java.text.Normalizer.Form.NFKC)
                .trim().toUpperCase(java.util.Locale.ROOT);

        EntityManager em = em();
        try {
            var q = em.createQuery(
                "SELECT uf FROM UsuarioFinal uf WHERE UPPER(uf.id) = :id",
                UsuarioFinal.class
            );
            q.setParameter("id", idUpper);
            // ¡no uses setMaxResults(1) por tu PG 9.3! (rompe con FETCH FIRST ?)
            return q.getResultStream().findFirst();
        } finally {
            em.close();
        }
    }

    @Override
    public List<UsuarioFinal> findAll() {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT uf FROM UsuarioFinal uf", UsuarioFinal.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public UsuarioFinal save(UsuarioFinal u) {
        EntityManager em = em();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            UsuarioFinal managed = (u.getId() == null || em.find(UsuarioFinal.class, u.getId()) == null)
                    ? persistCascade(em, u)
                    : em.merge(u);
            tx.commit();
            return managed;
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    private UsuarioFinal persistCascade(EntityManager em, UsuarioFinal u) {
        em.persist(u); // JOINED: inserta en usuario y luego en usuariofinal
        return u;
    }

    @Override
    public void deleteById(String id) {
        if (id == null) return;
        String idUpper = java.text.Normalizer
                .normalize(id, java.text.Normalizer.Form.NFKC)
                .trim().toUpperCase(java.util.Locale.ROOT);

        EntityManager em = em();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            UsuarioFinal uf = em.find(UsuarioFinal.class, idUpper);
            if (uf != null) {
                em.remove(uf); // borra fila en usuariofinal; OJO: en tu DDL NO borra usuario
            }
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}

