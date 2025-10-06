/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Modelo.repo;

import Modelo.dominio.Facultad;
import java.util.*;

/**
 *
 * @author Méndez
 */
public interface FacultadRepository {

    List<Facultad> findAll();

    Optional<Facultad> findById(int id);
}
