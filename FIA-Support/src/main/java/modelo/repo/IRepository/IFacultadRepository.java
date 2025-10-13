/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.repo.IRepository;

import modelo.dominio.Facultad;
import java.util.*;

/**
 *
 * @author Méndez
 */
public interface IFacultadRepository {

    List<Facultad> findAll();

    Optional<Facultad> findById(int id);
}
