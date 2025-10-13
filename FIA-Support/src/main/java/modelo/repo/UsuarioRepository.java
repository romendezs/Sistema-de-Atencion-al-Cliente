/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.repo;

import jakarta.persistence.EntityManager;
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
    public List<UsuarioFinal> findAll() {
        EntityManager em = em();
        try {
            TypedQuery<UsuarioFinal> q = em.createQuery(
                    "SELECT u FROM UsuarioFinal u ORDER BY u.apellidos, u.nombres", UsuarioFinal.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<UsuarioFinal> findById(String id) {
        EntityManager em = em();
        try {
            return Optional.ofNullable(em.find(UsuarioFinal.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<UsuarioFinal> searchByCarnet(String carnet) {
        var em = em();
        try {
            return Optional.ofNullable(em.find(UsuarioFinal.class, carnet));
        } finally {
            em.close();
        }
    }

    // CRUD opcional
    public UsuarioFinal save(UsuarioFinal u) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            if (em.find(UsuarioFinal.class, u.getId()) == null) {
                em.persist(u);          // PK String: decide si usas persist o merge
            } else {
                u = em.merge(u);
            }
            em.getTransaction().commit();
            return u;
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public void deleteById(String id) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            UsuarioFinal ref = em.find(UsuarioFinal.class, id);
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
}
