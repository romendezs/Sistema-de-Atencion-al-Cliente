/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import modelo.dominio.Facultad;

import modelo.repo.IRepository.IFacultadRepository;

/**
 *
 * @author Méndez
 */
// db/repo/FacultadRepository.java

public class FacultadRepository extends BaseJpaRepository implements IFacultadRepository {

    @Override
    public List<Facultad> findAll() {
        EntityManager em = em();
        try {
            TypedQuery<Facultad> q = em.createQuery(
                    "SELECT f FROM Facultad f ORDER BY f.nombre", Facultad.class);
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

    // CRUD opcional
    public Facultad save(Facultad f) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            if (f.getId() == null) {
                em.persist(f);
            } else {
                f = em.merge(f);
            }
            em.getTransaction().commit();
            return f;
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public void deleteById(int id) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            Facultad ref = em.find(Facultad.class, id);
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
