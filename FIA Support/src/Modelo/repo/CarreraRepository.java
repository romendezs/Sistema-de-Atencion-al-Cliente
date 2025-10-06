/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Modelo.repo;

import Modelo.dominio.Carrera;
import java.util.*;

/**
 *
 * @author Méndez
 */
public interface CarreraRepository {

    List<Carrera> findAll();

    List<Carrera> findByFacultadId(int idFacultad);

    Optional<Carrera> findById(int id);
}
