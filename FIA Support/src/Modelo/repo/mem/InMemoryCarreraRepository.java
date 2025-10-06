/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.repo.mem;

import Modelo.dominio.Carrera;
import Modelo.repo.CarreraRepository;
import java.util.*;

/**
 *
 * @author Méndez
 */
public class InMemoryCarreraRepository implements CarreraRepository {

    private final Map<Integer, Carrera> store = new LinkedHashMap<>();

    public InMemoryCarreraRepository() {
        // fac 1
        store.put(1, new Carrera(1, "Ingeniería de Sistemas Informáticos", 1));
        store.put(2, new Carrera(2, "Ingeniería Industrial", 1));
        store.put(3, new Carrera(3, "Arquitectura", 1));
        // fac 2
        store.put(4, new Carrera(4, "Psicología", 2));
        store.put(5, new Carrera(5, "Trabajo Social", 2));
        // fac 3
        store.put(6, new Carrera(6, "Contaduría Pública", 3));
        store.put(7, new Carrera(7, "Administración de Empresas", 3));
    }

    @Override
    public List<Carrera> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Carrera> findByFacultadId(int idFacultad) {
        List<Carrera> out = new ArrayList<>();
        for (Carrera c : store.values()) {
            if (c.getIdFacultad() == idFacultad) {
                out.add(c);
            }
        }
        return out;
    }

    @Override
    public Optional<Carrera> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }
}
