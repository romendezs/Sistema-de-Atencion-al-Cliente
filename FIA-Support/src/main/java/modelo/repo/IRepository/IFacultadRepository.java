package modelo.repo.IRepository;

import java.util.List;
import java.util.Optional;
import modelo.dominio.Facultad;

public interface IFacultadRepository {

    List<Facultad> findAll();

    Optional<Facultad> findById(int id);

    void save(Facultad facultad);

    void update(Facultad facultad);

    void delete(int id);
}
