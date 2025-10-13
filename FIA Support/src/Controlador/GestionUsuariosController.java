/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.dominio.*;
import Modelo.servicios.UserAdminService;
import java.util.List;

/**
 *
 * @author Méndez
 */
public class GestionUsuariosController {

    private final UserAdminService service;
    private final Vista.Admin.GestionUsuariosUI view;

    public GestionUsuariosController(UserAdminService service, Vista.Admin.GestionUsuariosUI view) {
        this.service = service;
        this.view = view;
        this.view.setController(this);
    }

    // Inicializa combos y tabla
    public void init() {
        view.setFacultades(service.listFacultades());
        view.setUsuarios(service.listUsers());
    }

    // Accesores para la vista / diálogos
    public List<Facultad> getFacultades() {
        return service.listFacultades();
    }

    public List<Carrera> getCarrerasByFacultad(int idFac) {
        return service.listCarrerasPorFacultad(idFac);
    }

    public void onSeleccionFacultad(Facultad f) {
        if (f != null) {
            view.setCarreras(service.listCarrerasPorFacultad(f.getId()));
        } else {
            view.setCarreras(java.util.Collections.emptyList());
        }
    }

    public boolean onCrear(String carnet, String nombres, String apellidos,
            boolean esEstudiante, Facultad fac, Carrera car) {
        try {
            Integer idFac = (fac == null ? null : fac.getId());
            Integer idCar = (car == null ? null : car.getId());
            service.createUser(carnet, nombres, apellidos, esEstudiante, idFac, idCar);
            view.clearForm();
            view.setUsuarios(service.listUsers());
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
            service.updateUser(u, nombres, apellidos, esEstudiante, idFac, idCar);
            view.setUsuarios(service.listUsers());
            view.showInfo("Usuario actualizado.");
            return true;
        } catch (IllegalArgumentException ex) {
            view.showError(ex.getMessage());
            return false;
        }
    }

    public void onEliminar(String carnet) {
        try {
            service.deleteUser(carnet);
            view.setUsuarios(service.listUsers());
            view.showInfo("Usuario eliminado.");
        } catch (IllegalArgumentException ex) {
            view.showError(ex.getMessage());
        }
    }
}
