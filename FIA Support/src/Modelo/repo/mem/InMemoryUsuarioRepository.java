/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.repo.mem;

import Modelo.dominio.UsuarioFinal;
import Modelo.repo.UsuarioRepository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 *
 * @author Méndez
 */

public class InMemoryUsuarioRepository implements UsuarioRepository {
    private final Map<String, UsuarioFinal> store = new ConcurrentHashMap<>();

    @Override public List<UsuarioFinal> findAll() { return new ArrayList<>(store.values()); }
    @Override public Optional<UsuarioFinal> findById(String id) { return Optional.ofNullable(store.get(id)); }
    @Override public void save(UsuarioFinal u) { store.put(u.getId(), u); }
    @Override public void deleteById(String id) { store.remove(id); }

    @Override public List<UsuarioFinal> searchByCarnet(String texto) {
        String t = texto.toLowerCase(Locale.ROOT);
        return store.values().stream()
                .filter(u -> u.getId().toLowerCase(Locale.ROOT).contains(t))
                .collect(Collectors.toList());
    }
}

