package controlador;

import java.util.Collections;
import java.util.List;
import modelo.dominio.Carrera;
import modelo.dominio.Facultad;
import modelo.dominio.UsuarioFinal;
import modelo.servicios.UserAdminService;
import Vista.Admin.GestionUsuariosUI;

/**
 * Controller for the admin user management screen.
 */
public class UserAdminController {

    private final UserAdminService userAdminService;
    private final GestionUsuariosUI view;

    public UserAdminController(UserAdminService userAdminService, GestionUsuariosUI view) {
        this.userAdminService = userAdminService;
        this.view = view;
        this.view.setController(this);
    }

    public void init() {
        view.setFacultades(userAdminService.listFacultades());
        view.setUsuarios(userAdminService.listUsers(null));
    }

    public List<Facultad> getFacultades() {
        return userAdminService.listFacultades();
    }

    public List<Carrera> getCarrerasByFacultad(int idFacultad) {
        return userAdminService.listCarrerasPorFacultad(idFacultad);
    }

    public void onSeleccionFacultad(Facultad facultad) {
        if (facultad == null) {
            view.setCarreras(Collections.emptyList());
        } else {
            view.setCarreras(userAdminService.listCarrerasPorFacultad(facultad.getId()));
        }
    }

    public boolean onCrear(String carnet, String nombres, String apellidos,
                           boolean esEstudiante, Facultad facultad, Carrera carrera) {
        try {
            Integer idFac = facultad == null ? null : facultad.getId();
            Integer idCar = carrera == null ? null : carrera.getId();
            userAdminService.createUser(carnet, nombres, apellidos, esEstudiante, idFac, idCar);
            view.clearForm();
            view.setUsuarios(userAdminService.listUsers(null));
            view.showInfo("Usuario creado con éxito.");
            return true;
        } catch (IllegalArgumentException ex) {
            view.showError(ex.getMessage());
            return false;
        }
    }
    
    public List<UsuarioFinal> listUsuarios() {
        // tu repo ya tiene findAll() ordenado por apellidos/nombres según lo que definiste
        return userAdminService.listUsers(null);
    }

    public boolean onEditar(UsuarioFinal usuario, String nombres, String apellidos,
                            boolean esEstudiante, Facultad facultad, Carrera carrera) {
        try {
            Integer idFac = facultad == null ? null : facultad.getId();
            Integer idCar = carrera == null ? null : carrera.getId();
            userAdminService.updateUser(usuario, nombres, apellidos, esEstudiante, idFac, idCar);
            view.setUsuarios(userAdminService.listUsers(null));
            view.showInfo("Usuario actualizado.");
            return true;
        } catch (IllegalArgumentException ex) {
            view.showError(ex.getMessage());
            return false;
        }
    }

    public void onEliminar(String carnet) {
        userAdminService.deleteUser(carnet);
        view.setUsuarios(userAdminService.listUsers(null));
        view.showInfo("Usuario eliminado.");
    }

    public void onBuscar(String filtro) {
        view.setUsuarios(userAdminService.listUsers(filtro));
    }

    public void onResetPassword(String carnet) {
        try {
            UsuarioFinal usuario = userAdminService.resetPassword(carnet);
            view.showInfo("Contraseña restablecida para " + usuario.getNombres());
        } catch (IllegalArgumentException ex) {
            view.showError(ex.getMessage());
        }
    }
}
