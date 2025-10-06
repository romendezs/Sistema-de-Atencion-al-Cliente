/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.dominio.*;
import Modelo.servicios.UsuarioService;
import java.util.List;

/**
 *
 * @author Méndez
 */
public class GestionUsuariosController {

    private final UsuarioService service;
    private final Vista.Admin.GestionUsuariosUI view;

    public GestionUsuariosController(UsuarioService service, Vista.Admin.GestionUsuariosUI view) {
        this.service = service;
        this.view = view;
        this.view.setController(this);
    }

    // Inicializa combos y tabla
    public void init() {
        view.setFacultades(service.listarFacultades());
        view.setUsuarios(service.listar());
    }

    // Accesores para la vista / diálogos
    public List<Facultad> getFacultades() {
        return service.listarFacultades();
    }

    public List<Carrera> getCarrerasByFacultad(int idFac) {
        return service.listarCarrerasPorFacultad(idFac);
    }

    public void onSeleccionFacultad(Facultad f) {
        if (f != null) {
            view.setCarreras(service.listarCarrerasPorFacultad(f.getId()));
        } else {
            view.setCarreras(java.util.Collections.emptyList());
        }
    }

    public boolean onCrear(String carnet, String nombres, String apellidos,
            boolean esEstudiante, Facultad fac, Carrera car) {
        try {
            Integer idFac = (fac == null ? null : fac.getId());
            Integer idCar = (car == null ? null : car.getId());
            service.crear(carnet, nombres, apellidos, esEstudiante, idFac, idCar);
            view.clearForm();
            view.setUsuarios(service.listar());
            view.showInfo("Usuario creado con éxito.");
            return true;
        } catch (IllegalArgumentException ex) {
            view.showError(ex.getMessage());
            return false;
        }
    }

    public boolean onEditar(UsuarioFinal u, String nombres, String apellidos,
            boolean esEstudiante, Facultad fac, Carrera car) {
        try {
            Integer idFac = (fac == null ? null : fac.getId());
            Integer idCar = (car == null ? null : car.getId());
            service.actualizar(u, nombres, apellidos, esEstudiante, idFac, idCar);
            view.setUsuarios(service.listar());
            view.showInfo("Usuario actualizado.");
            return true;
        } catch (IllegalArgumentException ex) {
            view.showError(ex.getMessage());
            return false;
        }
    }

    public void onEliminar(String carnet) {
        service.eliminar(carnet);
        view.setUsuarios(service.listar());
        view.showInfo("Usuario eliminado.");
    }
}
