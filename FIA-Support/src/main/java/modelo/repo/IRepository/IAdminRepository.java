package modelo.repo.IRepository;

import java.util.List;
import java.util.Optional;
import modelo.dominio.Administrador;

public interface IAdminRepository {

    // ya lo tenés
    Optional<Administrador> findByUsuario(String usuario);

    // para login web
    Optional<Administrador> findByCredenciales(String usuario, String passwordHash);

    // para panel admin web
    List<Administrador> findAll();
    void save(Administrador admin);
    void update(Administrador admin);
    void delete(String usuario);
}
