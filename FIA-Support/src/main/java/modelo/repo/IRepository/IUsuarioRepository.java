/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.repo.IRepository;

import modelo.dominio.UsuarioFinal;
import java.util.*;

/**
 *
 * @author Méndez
 */
    public interface IUsuarioRepository {

    List<UsuarioFinal> findAll();

    Optional<UsuarioFinal> findById(String id);

    UsuarioFinal save(UsuarioFinal u);     

    void deleteById(String id);
}
