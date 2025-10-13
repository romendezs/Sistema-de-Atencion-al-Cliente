package modelo.servicios;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import modelo.dominio.Carrera;
import modelo.dominio.Facultad;
import modelo.dominio.UsuarioFinal;

/**
 * Servicio que agrupa el caso de uso "Gestionar Usuario" para la interfaz de
 * administración. Utiliza {@link UsuarioService} para centralizar las
 * validaciones de negocio.
 */
public class UserAdminService {

    private final UsuarioService usuarioService;

    public UserAdminService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public List<UsuarioFinal> listUsers(String filtro) {
        List<UsuarioFinal> usuarios = usuarioService.listar();
        if (filtro == null || filtro.trim().isEmpty()) {
            return usuarios;
        }
        final String query = filtro.trim().toLowerCase();
        return usuarios.stream()
                .filter(u -> u.getId().toLowerCase().contains(query)
                        || u.getNombres().toLowerCase().contains(query)
                        || u.getApellidos().toLowerCase().contains(query))
                .collect(Collectors.toList());
    }

    public List<Facultad> listFacultades() {
        return usuarioService.listarFacultades();
    }

    public List<Carrera> listCarrerasPorFacultad(int idFacultad) {
        return usuarioService.listarCarrerasPorFacultad(idFacultad);
    }

    public Optional<UsuarioFinal> findByCarnet(String carnet) {
        return usuarioService.buscar(carnet);
    }

    public UsuarioFinal createUser(String carnet, String nombres, String apellidos,
                                   boolean esEstudiante, Integer idFacultad, Integer idCarrera) {
        return usuarioService.crear(carnet, nombres, apellidos, esEstudiante, idFacultad, idCarrera);
    }

    public UsuarioFinal updateUser(UsuarioFinal usuario, String nombres, String apellidos,
                                   boolean esEstudiante, Integer idFacultad, Integer idCarrera) {
        return usuarioService.actualizar(usuario, nombres, apellidos, esEstudiante, idFacultad, idCarrera);
    }

    public void deleteUser(String carnet) {
        usuarioService.eliminar(carnet);
    }

    public UsuarioFinal resetPassword(String carnet) {
        return usuarioService.restablecerPassword(carnet);
    }
}
