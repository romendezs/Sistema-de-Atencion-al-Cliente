package modelo.repo.IRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import modelo.dominio.Categoria;
import modelo.dominio.Empleado;
import modelo.dominio.Estado;
import modelo.dominio.Historial;
import modelo.dominio.Ticket;
import modelo.dominio.TicketAsignacionHistorial;

public interface ITicketRepository {

    // Lecturas principales
    List<Ticket> findAll();
    Optional<Ticket> findById(int id);
    List<Ticket> findByUsuarioId(String usuarioId);
    List<Ticket> findByEstadoId(int estadoId);
    List<Ticket> findByRangoFechas(LocalDateTime desde, LocalDateTime hasta);

    // CRUD
    Ticket save(Ticket t);
    void deleteById(int id); // si querés este en interfaz (tu clase lo tiene)

    // Historial / estados
    List<Historial> findHistorialByTicket(int ticketId);
    Optional<Historial> findUltimoHistorial(int ticketId);
    void addHistorial(int ticketId, Estado estado, String comentario);
    List<Estado> findAllEstados();

    // Asignaciones
    List<TicketAsignacionHistorial> findAsignacionesByTicket(int ticketId);
    List<Empleado> findAllEmpleados();
    void assignToEmpleado(int ticketId, int empleadoId);

    // Categorías
    Categoria findCategoriaById(int categoriaId);
    List<Categoria> findAllCategorias();
}
