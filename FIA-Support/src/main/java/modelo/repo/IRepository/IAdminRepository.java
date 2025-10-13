package modelo.repo.IRepository;

import java.util.Optional;
import modelo.dominio.Administrador;

/**
 * Contrato mínimo para consultar credenciales de administradores.
 */
public interface IAdminRepository {

    Optional<Administrador> findByUsuario(String usuario);
}
