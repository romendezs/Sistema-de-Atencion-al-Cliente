/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.servicios;

import Modelo.dominio.*;
import Modelo.repo.*;
import Modelo.utils.Validacion;

import java.util.*;

/**
 *
 * @author Méndez
 */

public class UsuarioService {
    protected final UsuarioRepository usuarios;
    protected final FacultadRepository facultades;
    protected final CarreraRepository carreras;

    public UsuarioService(UsuarioRepository usuarios,
                          FacultadRepository facultades,
                          CarreraRepository carreras) {
        this.usuarios   = usuarios;
        this.facultades = facultades;
        this.carreras   = carreras;
    }

    protected List<UsuarioFinal> listarUsuarios() {
        return usuarios.findAll();
    }

    protected List<UsuarioFinal> buscarUsuariosPorCarnet(String filtro) {
        if (filtro == null || filtro.trim().isEmpty()) {
            return listarUsuarios();
        }
        return usuarios.searchByCarnet(filtro.trim());
    }

    protected List<Facultad> listarFacultades() {
        return facultades.findAll();
    }

    protected List<Carrera> listarCarrerasPorFacultad(int idFac) {
        return carreras.findByFacultadId(idFac);
    }

    protected Optional<UsuarioFinal> buscarUsuario(String carnet) {
        if (carnet == null) {
            return Optional.empty();
        }
        String normalizado = Validacion.normalizarCarnet(carnet);
        if (!Validacion.esCarnetValido(normalizado)) {
            return Optional.empty();
        }
        return usuarios.findById(normalizado);
    }

    protected UsuarioFinal crearUsuario(String carnet, String nombres, String apellidos,
                      boolean esEstudiante, Integer idFacultad, Integer idCarrera) {

        String normalizado = prepararCarnet(carnet);

        // 1. campos vacíos
        if (nombres == null || apellidos == null) {
            throw new IllegalArgumentException("Ningún campo puede estar vacío.");
        }

        // 2. formatos
        if (!Validacion.esTextoLetras(nombres)) {
            throw new IllegalArgumentException("Nombres solo letras y espacios.");
        }
        if (!Validacion.esTextoLetras(apellidos)) {
            throw new IllegalArgumentException("Apellidos solo letras y espacios.");
        }

        // 3. unicidad
        if (usuarios.findById(normalizado).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese carnet.");
        }

        // 4. Reglas estudiante
        Facultad fac = null;
        Carrera car  = null;
        if (esEstudiante) {
            fac = obtenerFacultad(idFacultad);
            car = obtenerCarrera(idCarrera);
            validarCarreraFacultad(fac, car);
        }

        UsuarioFinal u = new UsuarioFinal(normalizado, nombres.trim(), apellidos.trim(), esEstudiante, fac, car);
        usuarios.save(u);
        return u;
    }

    protected UsuarioFinal actualizarUsuario(UsuarioFinal u, String nuevosNombres, String nuevosApellidos,
                           boolean esEstudiante, Integer idFacultad, Integer idCarrera) {

        if (u == null) {
            throw new IllegalArgumentException("El usuario a actualizar es obligatorio.");
        }
        if (!Validacion.esTextoLetras(nuevosNombres)) {
            throw new IllegalArgumentException("Nombres solo letras y espacios.");
        }
        if (!Validacion.esTextoLetras(nuevosApellidos)) {
            throw new IllegalArgumentException("Apellidos solo letras y espacios.");
        }

        u.setNombres(nuevosNombres.trim());
        u.setApellidos(nuevosApellidos.trim());
        u.setEsEstudiante(esEstudiante);

        if (esEstudiante) {
            Facultad f = obtenerFacultad(idFacultad);
            Carrera c = obtenerCarrera(idCarrera);
            validarCarreraFacultad(f, c);
            u.setFacultad(f);
            u.setCarrera(c);
        } else {
            u.setFacultad(null);
            u.setCarrera(null);
        }

        usuarios.save(u); // upsert
        return u;
    }

    protected void eliminarUsuario(String carnet) {
        String normalizado = prepararCarnet(carnet);
        if (usuarios.findById(normalizado).isEmpty()) {
            throw new IllegalArgumentException("No existe un usuario con ese carnet.");
        }
        usuarios.deleteById(normalizado);
    }

    private String prepararCarnet(String carnet) {
        String normalizado = Validacion.normalizarCarnet(carnet);
        if (normalizado.isEmpty()) {
            throw new IllegalArgumentException("El carnet es obligatorio.");
        }
        if (!Validacion.esCarnetValido(normalizado)) {
            throw new IllegalArgumentException("Carnet inválido. Formato: LLNNNNN.");
        }
        return normalizado;
    }

    private Facultad obtenerFacultad(Integer idFacultad) {
        if (idFacultad == null) {
            throw new IllegalArgumentException("Seleccione Facultad.");
        }
        return facultades.findById(idFacultad).orElseThrow(() ->
                new IllegalArgumentException("Facultad no válida."));
    }

    private Carrera obtenerCarrera(Integer idCarrera) {
        if (idCarrera == null) {
            throw new IllegalArgumentException("Seleccione Carrera.");
        }
        return carreras.findById(idCarrera).orElseThrow(() ->
                new IllegalArgumentException("Carrera no válida."));
    }

    private void validarCarreraFacultad(Facultad fac, Carrera car) {
        if (car.getIdFacultad() != fac.getId()) {
            throw new IllegalArgumentException("La carrera no pertenece a la facultad.");
        }
    }
}
