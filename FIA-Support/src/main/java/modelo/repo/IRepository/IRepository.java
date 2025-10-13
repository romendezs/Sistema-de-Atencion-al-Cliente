/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.repo.IRepository;

import java.util.List;
import java.util.Optional;
import modelo.dominio.*;

/**
 *
 * @author Méndez
 */
public interface IRepository {
    
    //Métodos para Facultad
    List<Facultad> findAllFacultad();
    Optional<Facultad> findFacultadById(int id);
    
    //Métodos para Carrera
    List<Carrera> findAll();
    List<Carrera> findByFacultadId(int idFacultad);

    Optional<Carrera> findById(int id);
    
    
    //Metodos para Ticket
    
    
    //Metodos para Usuario
    
    //Metodos para Uusario Final
    
    //Metodos para Admin
    
    
}
