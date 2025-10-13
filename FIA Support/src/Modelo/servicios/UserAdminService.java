package Modelo.servicios;

import Modelo.dominio.Carrera;
import Modelo.dominio.Facultad;
import Modelo.dominio.UsuarioFinal;
import Modelo.repo.CarreraRepository;
import Modelo.repo.FacultadRepository;
import Modelo.repo.UsuarioRepository;

import java.util.List;

/**
 * Servicio para el caso de uso "Gestionar Usuario".
 */
public class UserAdminService extends UsuarioService {

    public UserAdminService(UsuarioRepository usuarios,
                            FacultadRepository facultades,
                            CarreraRepository carreras) {
        super(usuarios, facultades, carreras);
    }

    public List<UsuarioFinal> listUsers(String filtro) {
        return buscarUsuariosPorCarnet(filtro);
    }

    public List<UsuarioFinal> listUsers() {
        return listarUsuarios();
    }

    public UsuarioFinal createUser(String carnet, String nombres, String apellidos,
                                   boolean esEstudiante, Integer idFacultad, Integer idCarrera) {
        return crearUsuario(carnet, nombres, apellidos, esEstudiante, idFacultad, idCarrera);
    }

    public UsuarioFinal updateUser(UsuarioFinal usuario, String nombres, String apellidos,
                                   boolean esEstudiante, Integer idFacultad, Integer idCarrera) {
        return actualizarUsuario(usuario, nombres, apellidos, esEstudiante, idFacultad, idCarrera);
    }

    public void deleteUser(String carnet) {
        eliminarUsuario(carnet);
    }

    public UsuarioFinal getUser(String carnet) {
        return buscarUsuario(carnet)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
    }

    public List<Facultad> listFacultades() {
        return super.listarFacultades();
    }

    public List<Carrera> listCarrerasPorFacultad(int idFacultad) {
        return super.listarCarrerasPorFacultad(idFacultad);
    }
}
