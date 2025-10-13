package modelo.servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import modelo.dominio.Estado;
import modelo.dominio.Historial;
import modelo.dominio.Ticket;
import modelo.dominio.UsuarioFinal;
import modelo.repo.IRepository.ITicketRepository;
import modelo.repo.IRepository.IUsuarioRepository;
import modelo.utils.Validacion;

/**
 * Capa de servicios para el caso de uso "Gestionar Ticket". Se encarga de
 * validar los datos y orquestar las operaciones con el repositorio.
 */
public class TicketService {

    private final ITicketRepository ticketRepository;
    private final IUsuarioRepository usuarioRepository;

    public TicketService(ITicketRepository ticketRepository, IUsuarioRepository usuarioRepository) {
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Ticket openTicket(String titulo, String descripcion, String carnetSolicitante, Estado estadoInicial) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título es obligatorio.");
        }
        if (estadoInicial == null || estadoInicial.getId() == null) {
            throw new IllegalArgumentException("Debe seleccionar un estado inicial válido.");
        }

        String carnet = Validacion.normalizarCarnet(carnetSolicitante);
        if (!Validacion.esCarnetValido(carnet)) {
            throw new IllegalArgumentException("Carnet inválido.");
        }

        UsuarioFinal solicitante = usuarioRepository.findById(carnet)
                .orElseThrow(() -> new IllegalArgumentException("El solicitante no existe."));

        Ticket ticket = new Ticket();
        ticket.setTitulo(titulo.trim());
        ticket.setDescripcion(descripcion == null ? null : descripcion.trim());
        ticket.setSolicitante(solicitante);
        ticket.setEstadoActual(estadoInicial);
        ticket.setCreadoEn(LocalDateTime.now());
        ticket.setActualizadoEn(LocalDateTime.now());

        Ticket creado = ticketRepository.save(ticket);
        if (descripcion != null && !descripcion.isBlank()) {
            ticketRepository.addHistorial(creado.getId(), estadoInicial, descripcion.trim());
        }
        return creado;
    }

    public Ticket updateTicket(int ticketId, String nuevoTitulo, String nuevaDescripcion) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket no encontrado."));

        if (nuevoTitulo == null || nuevoTitulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título es obligatorio.");
        }

        ticket.setTitulo(nuevoTitulo.trim());
        ticket.setDescripcion(nuevaDescripcion == null ? null : nuevaDescripcion.trim());
        ticket.setActualizadoEn(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    public void cancelTicket(int ticketId, Estado estadoCancelado, String comentario) {
        changeStatus(ticketId, estadoCancelado, comentario);
    }

    public void closeTicket(int ticketId, Estado estadoCerrado, String comentario) {
        changeStatus(ticketId, estadoCerrado, comentario);
    }

    public void deleteTicket(int ticketId) {
        if (ticketId <= 0) {
            throw new IllegalArgumentException("Ticket inválido.");
        }
        ticketRepository.deleteById(ticketId);
    }

    private void changeStatus(int ticketId, Estado nuevoEstado, String comentario) {
        if (nuevoEstado == null || nuevoEstado.getId() == null) {
            throw new IllegalArgumentException("Estado inválido.");
        }
        if (comentario == null || comentario.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe agregar un comentario.");
        }

        ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket no encontrado."));

        ticketRepository.addHistorial(ticketId, nuevoEstado, comentario.trim());
    }

    public Optional<Ticket> getTicket(int ticketId) {
        return ticketRepository.findById(ticketId);
    }

    public List<Ticket> listTickets(TicketFilter filter) {
        List<Ticket> base = new ArrayList<>(ticketRepository.findAll());
        if (filter == null) {
            return base;
        }

        return base.stream()
                .filter(t -> filter.matchesSolicitante(t.getSolicitante() == null ? null : t.getSolicitante().getId()))
                .filter(t -> filter.matchesEstado(t.getEstadoActual() == null ? null : t.getEstadoActual().getId()))
                .filter(t -> filter.matchesFecha(t.getCreadoEn()))
                .collect(Collectors.toList());
    }

    public List<Historial> getHistorial(int ticketId) {
        return ticketRepository.findHistorialByTicket(ticketId);
    }

    public static class TicketFilter {

        private final String solicitanteCarnet;
        private final Integer estadoId;
        private final LocalDate desde;
        private final LocalDate hasta;

        public TicketFilter(String solicitanteCarnet, Integer estadoId, LocalDate desde, LocalDate hasta) {
            if (desde != null && hasta != null && desde.isAfter(hasta)) {
                throw new IllegalArgumentException("El rango de fechas es inválido.");
            }
            this.solicitanteCarnet = solicitanteCarnet;
            this.estadoId = estadoId;
            this.desde = desde;
            this.hasta = hasta;
        }

        public String getSolicitanteCarnet() {
            return solicitanteCarnet;
        }

        public Integer getEstadoId() {
            return estadoId;
        }

        public LocalDate getDesde() {
            return desde;
        }

        public LocalDate getHasta() {
            return hasta;
        }

        private boolean matchesSolicitante(String carnet) {
            if (solicitanteCarnet == null || solicitanteCarnet.isBlank()) {
                return true;
            }
            if (carnet == null) {
                return false;
            }
            return carnet.equalsIgnoreCase(Validacion.normalizarCarnet(solicitanteCarnet));
        }

        private boolean matchesEstado(Integer estado) {
            if (estadoId == null) {
                return true;
            }
            return Objects.equals(estadoId, estado);
        }

        private boolean matchesFecha(LocalDateTime fecha) {
            if (fecha == null) {
                return true;
            }
            if (desde != null && fecha.toLocalDate().isBefore(desde)) {
                return false;
            }
            if (hasta != null && fecha.toLocalDate().isAfter(hasta)) {
                return false;
            }
            return true;
        }
    }
}
