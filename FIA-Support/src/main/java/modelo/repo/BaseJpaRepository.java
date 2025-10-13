/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.repo;
import jakarta.persistence.EntityManager;
import modelo.db.JPAUtil;

/**
 *
 * @author Méndez
 */
public class BaseJpaRepository {
    protected EntityManager em() { return JPAUtil.em(); }
}
