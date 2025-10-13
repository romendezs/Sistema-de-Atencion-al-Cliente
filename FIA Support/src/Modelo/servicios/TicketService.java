package Modelo.servicios;

import Modelo.dominio.Ticket;
import Modelo.dominio.UsuarioFinal;
import Modelo.repo.ITicketRepository;
import Modelo.repo.UsuarioRepository;
import Modelo.utils.Validacion;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Servicio para el caso de uso "Gestionar Ticket".
 */
public class TicketService {
    private final ITicketRepository tickets;
    private final UsuarioRepository usuarios;

    public TicketService(ITicketRepository tickets, UsuarioRepository usuarios) {
        this.tickets = Objects.requireNonNull(tickets, "tickets");
        this.usuarios = Objects.requireNonNull(usuarios, "usuarios");
    }

    public Ticket createTicket(String carnetSolicitante, String titulo, String descripcion) {
        UsuarioFinal solicitante = obtenerSolicitante(carnetSolicitante);
        String tituloLimpio = validarTextoLibre(titulo, "El título es obligatorio.");
        String descripcionLimpia = validarTextoLibre(descripcion, "La descripción es obligatoria.");
        return tickets.create(solicitante, tituloLimpio, descripcionLimpia);
    }

    public Ticket updateTicket(int ticketId, String titulo, String descripcion) {
        Ticket existente = asegurarTicket(ticketId);
        String tituloLimpio = titulo == null ? existente.getTitulo() : validarTextoLibre(titulo, "El título es obligatorio.");
        String descripcionLimpia = descripcion == null ? existente.getDescripcion() : validarTextoLibre(descripcion, "La descripción es obligatoria.");
        tickets.update(ticketId, tituloLimpio, descripcionLimpia);
        return asegurarTicket(ticketId);
    }

    public void cancelTicket(int ticketId, String comentario) {
        asegurarTicket(ticketId);
        tickets.cancel(ticketId, comentario);
    }

    public void deleteTicket(int ticketId) {
        asegurarTicket(ticketId);
        tickets.deleteById(ticketId);
    }

    public List<Ticket> listTickets(String filtroSolicitante) {
        List<Ticket> todos = tickets.findAll();
        if (filtroSolicitante == null || filtroSolicitante.trim().isEmpty()) {
            return todos;
        }
        final String query = filtroSolicitante.trim().toLowerCase();
        return todos.stream().filter(t ->
                t.getSolicitante().getId().toLowerCase().contains(query) ||
                        (t.getSolicitante().getNombres() + " " + t.getSolicitante().getApellidos()).toLowerCase().contains(query)
        ).collect(Collectors.toList());
    }

    public Ticket getTicket(int ticketId) {
        return asegurarTicket(ticketId);
    }

    private Ticket asegurarTicket(int ticketId) {
        Ticket ticket = tickets.findById(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket no encontrado.");
        }
        return ticket;
    }

    private UsuarioFinal obtenerSolicitante(String carnetSolicitante) {
        String normalizado = Validacion.normalizarCarnet(carnetSolicitante);
        if (!Validacion.esCarnetValido(normalizado)) {
            throw new IllegalArgumentException("Carnet inválido. Formato: LLNNNNN.");
        }
        return usuarios.findById(normalizado)
                .orElseThrow(() -> new IllegalArgumentException("Usuario solicitante no encontrado."));
    }

    private String validarTextoLibre(String texto, String mensajeError) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(mensajeError);
        }
        return texto.trim();
    }
}
