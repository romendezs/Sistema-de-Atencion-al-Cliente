/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.servicios;

import modelo.repo.IRepository.ICarreraRepository;
import modelo.repo.IRepository.IUsuarioRepository;
import modelo.repo.IRepository.IFacultadRepository;
import modelo.dominio.*;
import modelo.utils.PasswordEncoder;
import modelo.utils.Sha256PasswordEncoder;
import modelo.utils.Validacion;

import java.util.*;

/**
 *
 * @author Méndez
 */

public class UsuarioService {
    private final IUsuarioRepository usuarios;
    private final IFacultadRepository facultades;
    private final ICarreraRepository carreras;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(IUsuarioRepository usuarios,
                          IFacultadRepository facultades,
                          ICarreraRepository carreras) {
        this(usuarios, facultades, carreras, new Sha256PasswordEncoder());
    }

    public UsuarioService(IUsuarioRepository usuarios,
                          IFacultadRepository facultades,
                          ICarreraRepository carreras,
                          PasswordEncoder passwordEncoder) {
        this.usuarios        = usuarios;
        this.facultades      = facultades;
        this.carreras        = carreras;
        this.passwordEncoder = passwordEncoder;
    }

    // ----------------- Lecturas básicas -----------------
    public List<UsuarioFinal> listar() {
        return usuarios.findAll();
    }

    public List<Facultad> listarFacultades() {
        return facultades.findAll();
    }

    public List<Carrera> listarCarrerasPorFacultad(int idFac) {
        return carreras.findByFacultadId(idFac);
    }

    public Optional<UsuarioFinal> buscar(String carnet) {
        String id = Validacion.normalizarCarnet(carnet);
        return usuarios.findById(id); // Repositorio ya debe ser case-insensitive con UPPER(...)
    }

    // ----------------- Crear -----------------
    public UsuarioFinal crear(String carnet, String nombres, String apellidos,
                              boolean esEstudiante, Integer idFacultad, Integer idCarrera) {

        carnet = Validacion.normalizarCarnet(carnet);

        // 1) Campos vacíos
        if (carnet.isEmpty() || nombres == null || apellidos == null)
            throw new IllegalArgumentException("Ningún campo puede estar vacío.");

        // 2) Formatos
        if (!Validacion.esCarnetValido(carnet))
            throw new IllegalArgumentException("Carnet inválido. Formato: LLNNNNN.");
        if (!Validacion.esTextoLetras(nombres))
            throw new IllegalArgumentException("Nombres solo letras y espacios.");
        if (!Validacion.esTextoLetras(apellidos))
            throw new IllegalArgumentException("Apellidos solo letras y espacios.");

        // 3) Unicidad (en subtipo UsuarioFinal)
        if (usuarios.findById(carnet).isPresent())
            throw new IllegalArgumentException("Ya existe un usuario final con ese carnet.");

        // 4) Reglas estudiante
        Facultad fac = null;
        Carrera car  = null;
        if (esEstudiante) {
            if (idFacultad == null || idCarrera == null)
                throw new IllegalArgumentException("Seleccione Facultad y Carrera.");
            fac = facultades.findById(idFacultad).orElseThrow(() ->
                    new IllegalArgumentException("Facultad no válida."));
            car = carreras.findById(idCarrera).orElseThrow(() ->
                    new IllegalArgumentException("Carrera no válida."));
            if (!Objects.equals(car.getFacultad().getId(), fac.getId()))
                throw new IllegalArgumentException("La carrera no pertenece a la facultad.");
        }

        // 5) Contraseña por defecto = hash(carnet)
        String passHash = passwordEncoder.encode(carnet.toCharArray());

        // 6) Crear directamente el subtipo (JOINED persistirá en usuario + usuariofinal)
        UsuarioFinal u = new UsuarioFinal(
                carnet,
                nombres.trim(),
                apellidos.trim(),
                passHash,
                esEstudiante,
                fac,
                car
        );

        usuarios.save(u);
        return u;
    }

    // ----------------- Actualizar -----------------
    public UsuarioFinal actualizar(UsuarioFinal u, String nuevosNombres, String nuevosApellidos,
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
            if (!Objects.equals(c.getFacultad().getId(), f.getId()))
                throw new IllegalArgumentException("La carrera no pertenece a la facultad.");

            u.setFacultad(f);
            u.setCarrera(c);
        } else {
            u.setFacultad(null);
            u.setCarrera(null);
        }

        usuarios.save(u); // upsert
        return u;
    }

    // ----------------- Eliminar -----------------
    public void eliminar(String carnet) {
        String id = Validacion.normalizarCarnet(carnet);
        usuarios.deleteById(id);
    }

    // ----------------- Reset Password -----------------
    public UsuarioFinal restablecerPassword(String carnet) {
        String id = Validacion.normalizarCarnet(carnet);
        UsuarioFinal usuario = usuarios.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
        usuario.setPasswordHash(passwordEncoder.encode(id.toCharArray()));
        usuarios.save(usuario);
        return usuario;
    }
}
