package controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import modelo.dominio.Estado;
import modelo.dominio.Historial;
import modelo.dominio.Ticket;
import modelo.servicios.TicketService;

/**
 * Controller for the "Gestionar Ticket" use case. Keeps UI components unaware
 * of repository details.
 */
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // ------------------- Métodos existentes -------------------

    public List<Ticket> listTickets() {
        return ticketService.listTickets(null);
    }

    public List<Ticket> filterBySolicitante(String query) {
        List<Ticket> tickets = listTickets();
        if (query == null || query.trim().isEmpty()) {
            return tickets;
        }
        final String normalized = query.trim().toLowerCase();
        return tickets.stream()
                .filter(t -> {
                    if (t.getSolicitante() == null) return false;
                    String carnet = t.getSolicitante().getId();
                    String nombre = t.getSolicitante().toString();
                    boolean matchCarnet = carnet != null && carnet.toLowerCase().contains(normalized);
                    boolean matchNombre = nombre != null && nombre.toLowerCase().contains(normalized);
                    return matchCarnet || matchNombre;
                })
                .collect(Collectors.toList());
    }

    public Ticket openTicket(String titulo, String descripcion, String carnetSolicitante, Estado estadoInicial) {
        return ticketService.openTicket(titulo, descripcion, carnetSolicitante, estadoInicial);
    }

    public Ticket updateTicket(int ticketId, String nuevoTitulo, String nuevaDescripcion) {
        return ticketService.updateTicket(ticketId, nuevoTitulo, nuevaDescripcion);
    }

    public void deleteTicket(int ticketId) {
        ticketService.deleteTicket(ticketId);
    }

    public Ticket reload(int ticketId) {
        return ticketService.getTicket(ticketId).orElse(null);
    }

    public List<Historial> historial(int ticketId) {
        return ticketService.getHistorial(ticketId);
    }

    public boolean hasHistorial(int ticketId) {
        List<Historial> h = historial(ticketId);
        return h != null && !h.isEmpty();
    }

    // ------------------- NUEVO: estado actual desde Historial -------------------

    /**
     * Devuelve el nombre del estado actual del ticket, derivado del último Historial.
     * Prioriza usar un método optimizado del Service si existe (findUltimoHistorial).
     */
    public String estadoActualNombre(int ticketId) {
        // Camino 1 (recomendado): si expusiste esto en el Service
        try {
            var m = ticketService.getClass().getMethod("findUltimoHistorial", int.class);
            @SuppressWarnings("unchecked")
            Optional<Historial> opt = (Optional<Historial>) m.invoke(ticketService, ticketId);
            return opt.map(this::nombreEstadoDe)
                      .orElse("—");
        } catch (ReflectiveOperationException ignored) {
            // Camino 2: caer a getHistorial(...) y tomar el último de forma segura
            List<Historial> hist = historial(ticketId);
            if (hist == null || hist.isEmpty()) return "—";
            Historial ultimo = elegirUltimo(hist);
            return nombreEstadoDe(ultimo);
        }
    }

    /**
     * Devuelve el id del estado actual del ticket (o null si no hay historial/estado).
     */
    public Integer estadoActualId(int ticketId) {
        try {
            var m = ticketService.getClass().getMethod("findUltimoHistorial", int.class);
            @SuppressWarnings("unchecked")
            Optional<Historial> opt = (Optional<Historial>) m.invoke(ticketService, ticketId);
            return opt.map(h -> h.getEstado() != null ? h.getEstado().getId() : null)
                      .orElse(null);
        } catch (ReflectiveOperationException ignored) {
            List<Historial> hist = historial(ticketId);
            if (hist == null || hist.isEmpty()) return null;
            Historial ultimo = elegirUltimo(hist);
            return ultimo.getEstado() != null ? ultimo.getEstado().getId() : null;
        }
    }

    /**
     * Versión bulk para tablas: devuelve un mapa ticketId -> nombre de estado actual.
     * Evita repetir consultas desde la UI (reduce N+1).
     */
    public Map<Integer, String> estadosActualesPorTicket(List<Integer> ticketIds) {
        Map<Integer, String> out = new HashMap<>();
        if (ticketIds == null || ticketIds.isEmpty()) return out;
        for (int id : ticketIds) {
            out.put(id, estadoActualNombre(id));
        }
        return out;
    }

    // ------------------- Helpers privados -------------------

    private String nombreEstadoDe(Historial h) {
        if (h == null) return "—";
        Estado e = h.getEstado();
        if (e == null) return "—";
        // Usa el nombre si existe; si no, toString()
        try {
            var m = e.getClass().getMethod("getNombre");
            Object v = m.invoke(e);
            if (v instanceof String s && !s.isBlank()) return s;
        } catch (Exception ignored) {}
        return String.valueOf(e);
    }

    /**
     * Elige el “último” historial de una lista:
     * 1) Si tienen getFechaCambio()/getCambioEn(): ordena por fecha.
     * 2) Si no, intenta por getId() numérico/comparable.
     * 3) Fallback: último elemento de la lista.
     */
    private Historial elegirUltimo(List<Historial> hist) {
        if (hist.size() == 1) return hist.get(0);

        // Copia para no mutar la lista original
        List<Historial> copia = new ArrayList<>(hist);
        copia.sort((a, b) -> {
            // 1) fechaCambio/cambioEn si existe
            var fa = invocarNoArg(a, "getFechaCambio");
            var fb = invocarNoArg(b, "getFechaCambio");
            if (fa instanceof Comparable && fb instanceof Comparable) {
                @SuppressWarnings({"rawtypes","unchecked"})
                int cmp = ((Comparable) fa).compareTo(fb);
                if (cmp != 0) return cmp;
            } else {
                var ca = invocarNoArg(a, "getCambioEn");
                var cb = invocarNoArg(b, "getCambioEn");
                if (ca instanceof Comparable && cb instanceof Comparable) {
                    @SuppressWarnings({"rawtypes","unchecked"})
                    int cmp = ((Comparable) ca).compareTo(cb);
                    if (cmp != 0) return cmp;
                }
            }
            // 2) id comparable/numérico
            Object ia = invocarNoArg(a, "getId");
            Object ib = invocarNoArg(b, "getId");
            if (ia instanceof Number && ib instanceof Number) {
                long la = ((Number) ia).longValue();
                long lb = ((Number) ib).longValue();
                return Long.compare(la, lb);
            }
            if (ia instanceof Comparable && ib instanceof Comparable) {
                @SuppressWarnings({"rawtypes","unchecked"})
                int cmp = ((Comparable) ia).compareTo(ib);
                if (cmp != 0) return cmp;
            }
            return 0;
        });
        // último de la ordenación ascendente = más reciente
        return copia.get(copia.size() - 1);
    }

    private Object invocarNoArg(Object target, String method) {
        try {
            var m = target.getClass().getMethod(method);
            return m.invoke(target);
        } catch (Exception e) {
            return null;
        }
    }
}