package modelo.servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
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

        // 1) Creamos el Ticket SIN estado
        Ticket ticket = new Ticket();
        ticket.setTitulo(titulo.trim());
        ticket.setDescripcion(descripcion == null ? null : descripcion.trim());
        ticket.setSolicitante(solicitante);
        ticket.setCreadoEn(LocalDateTime.now());
        ticket.setActualizadoEn(LocalDateTime.now());

        Ticket creado = ticketRepository.save(ticket);

        // 2) Insertamos el primer Historial con el estado inicial
        String comentarioInicial = (descripcion == null || descripcion.isBlank())
                ? "Ticket creado"
                : descripcion.trim();
        ticketRepository.addHistorial(creado.getId(), estadoInicial, comentarioInicial);

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

        // Verificamos existencia del ticket
        ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket no encontrado."));

        // Solo agregamos un nuevo historial; el estado actual se deriva del último historial
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

        // Para evitar N+1, cacheamos por si se filtra por estado
        final Map<Integer, Integer> estadoActualCache = new HashMap<>();

        return base.stream()
                .filter(t -> filter.matchesSolicitante(t.getSolicitante() == null ? null : t.getSolicitante().getId()))
                .filter(t -> {
                    if (filter.getEstadoId() == null) {
                        return true;
                    }
                    Integer estadoActual = resolveEstadoActualId(t.getId(), estadoActualCache);
                    return filter.matchesEstado(estadoActual);
                })
                .filter(t -> filter.matchesFecha(t.getCreadoEn()))
                .collect(Collectors.toList());
    }

    public List<Historial> getHistorial(int ticketId) {
        return ticketRepository.findHistorialByTicket(ticketId);
    }

    /**
     * Resuelve el id de estado actual del ticket a partir de su último
     * historial. Usa caché local para evitar múltiples accesos por el mismo
     * ticket.
     */
    private Integer resolveEstadoActualId(int ticketId, Map<Integer, Integer> cache) {
        return cache.computeIfAbsent(ticketId, id
                -> ticketRepository.findUltimoHistorial(id)
                        .map(h -> h.getEstado() != null ? h.getEstado().getId() : null)
                        .orElse(null)
        );
    }

    // ------------------------------ Filtro ------------------------------
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

        private boolean matchesEstado(Integer estadoActualId) {
            if (estadoId == null) {
                return true;
            }
            return Objects.equals(estadoId, estadoActualId);
        }

        private boolean matchesFecha(LocalDateTime fechaCreacionTicket) {
            if (fechaCreacionTicket == null) {
                return true;
            }
            LocalDate f = fechaCreacionTicket.toLocalDate();
            if (desde != null && f.isBefore(desde)) {
                return false;
            }
            if (hasta != null && f.isAfter(hasta)) {
                return false;
            }
            return true;
        }
    }
}
