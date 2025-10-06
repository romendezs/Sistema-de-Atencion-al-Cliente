/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.repo.mem;

import Modelo.dominio.Facultad;
import Modelo.repo.FacultadRepository;
import java.util.*;

/**
 *
 * @author Méndez
 */
public class InMemoryFacultadRepository implements FacultadRepository {

    private final Map<Integer, Facultad> store = new LinkedHashMap<>();

    public InMemoryFacultadRepository() {
        store.put(1, new Facultad(1, "Ingeniería y Arquitectura"));
        store.put(2, new Facultad(2, "Ciencias y Humanidades"));
        store.put(3, new Facultad(3, "Ciencias Económicas"));
    }

    @Override
    public List<Facultad> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Facultad> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }
}
