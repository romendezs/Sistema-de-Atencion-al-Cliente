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
    private final UsuarioRepository usuarios;
    private final FacultadRepository facultades;
    private final CarreraRepository carreras;

    public UsuarioService(UsuarioRepository usuarios,
                          FacultadRepository facultades,
                          CarreraRepository carreras) {
        this.usuarios   = usuarios;
        this.facultades = facultades;
        this.carreras   = carreras;
    }

    public List<UsuarioFinal> listar() { return usuarios.findAll(); }
    public List<Facultad> listarFacultades() { return facultades.findAll(); }
    public List<Carrera> listarCarrerasPorFacultad(int idFac) { return carreras.findByFacultadId(idFac); }

    public Optional<UsuarioFinal> buscar(String carnet) { return usuarios.findById(carnet); }

    public void crear(String carnet, String nombres, String apellidos,
                      boolean esEstudiante, Integer idFacultad, Integer idCarrera) {

        carnet = Validacion.normalizarCarnet(carnet);

        // 1. campos vacíos
        if (carnet.isEmpty() || nombres == null || apellidos == null)
            throw new IllegalArgumentException("Ningún campo puede estar vacío.");

        // 2. formatos
        if (!Validacion.esCarnetValido(carnet))
            throw new IllegalArgumentException("Carnet inválido. Formato: LLNNNNN.");
        if (!Validacion.esTextoLetras(nombres))
            throw new IllegalArgumentException("Nombres solo letras y espacios.");
        if (!Validacion.esTextoLetras(apellidos))
            throw new IllegalArgumentException("Apellidos solo letras y espacios.");

        // 3. unicidad
        if (usuarios.findById(carnet).isPresent())
            throw new IllegalArgumentException("Ya existe un usuario con ese carnet.");

        // 4. Reglas estudiante
        Facultad fac = null;
        Carrera car  = null;
        if (esEstudiante) {
            if (idFacultad == null || idCarrera == null)
                throw new IllegalArgumentException("Seleccione Facultad y Carrera.");
            fac = facultades.findById(idFacultad).orElseThrow(() ->
                    new IllegalArgumentException("Facultad no válida."));
            car = carreras.findById(idCarrera).orElseThrow(() ->
                    new IllegalArgumentException("Carrera no válida."));
            if (car.getIdFacultad() != fac.getId())
                throw new IllegalArgumentException("La carrera no pertenece a la facultad.");
        }

        UsuarioFinal u = new UsuarioFinal(carnet, nombres.trim(), apellidos.trim(), esEstudiante, fac, car);
        usuarios.save(u);
    }

    public void actualizar(UsuarioFinal u, String nuevosNombres, String nuevosApellidos,
                           boolean esEstudiante, Integer idFacultad, Integer idCarrera) {

        if (!Validacion.esTextoLetras(nuevosNombres))
            throw new IllegalArgumentException("Nombres solo letras y espacios.");
        if (!Validacion.esTextoLetras(nuevosApellidos))
            throw new IllegalArgumentException("Apellidos solo letras y espacios.");

        u.setNombres(nuevosNombres.trim());
        u.setApellidos(nuevosApellidos.trim());
        u.setEsEstudiante(esEstudiante);

        if (esEstudiante) {
            if (idFacultad == null || idCarrera == null)
                throw new IllegalArgumentException("Seleccione Facultad y Carrera.");
            Facultad f = facultades.findById(idFacultad).orElseThrow(() ->
                    new IllegalArgumentException("Facultad no válida."));
            Carrera c = carreras.findById(idCarrera).orElseThrow(() ->
                    new IllegalArgumentException("Carrera no válida."));
            if (c.getIdFacultad() != f.getId())
                throw new IllegalArgumentException("La carrera no pertenece a la facultad.");

            u.setFacultad(f);
            u.setCarrera(c);
        } else {
            u.setFacultad(null);
            u.setCarrera(null);
        }

        usuarios.save(u); // upsert
    }

    public void eliminar(String carnet) { usuarios.deleteById(carnet); }
}
