/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.repo.IRepository;

import modelo.dominio.Carrera;
import java.util.*;

/**
 *
 * @author Méndez
 */
public interface ICarreraRepository {

    List<Carrera> findAll();
    List<Carrera> findByFacultadId(int idFacultad);
    Optional<Carrera> findById(int id);
    
    void save(Carrera carrera);
    void update(Carrera carrera);
    void delete(int id);
}
