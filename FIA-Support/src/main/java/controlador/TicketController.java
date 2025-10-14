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
import modelo.dominio.UsuarioFinal;
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

    public List<Ticket> listTickets() {
        return ticketService.listTickets(null);
    }

    private List<Ticket> listTicketsEagerIfAvailable() {
        // Intenta varios nombres posibles para un método eager en el service sin romper compatibilidad
        String[] candidatos = {
            "listTicketsEager",           // recomendado
            "listTicketsWithSolicitante", // alternativo
            "listTicketsJoinFetch"        // alternativo
        };
        for (String nombre : candidatos) {
            try {
                var m = ticketService.getClass().getMethod(nombre);
                @SuppressWarnings("unchecked")
                List<Ticket> res = (List<Ticket>) m.invoke(ticketService);
                if (res != null) return res;
            } catch (ReflectiveOperationException ignored) {
                // probamos el siguiente nombre
            }
        }
        // Fallback: el método original (puede traer solicitante LAZY)
        return listTickets();
    }

    public List<Ticket> filterBySolicitante(String query) {
        List<Ticket> tickets = listTicketsEagerIfAvailable();
        if (query == null || query.trim().isEmpty()) {
            return tickets;
        }
        final String normalized = query.trim().toLowerCase();

        return tickets.stream()
            .filter(t -> {
                UsuarioFinal s = t.getSolicitante();
                if (s == null) return false;

                // 1) Coincidencia por carnet (ID): SAFE incluso si es proxy lazy (el id no inicializa)
                String carnet = s.getId();
                boolean matchCarnet = carnet != null && carnet.toLowerCase().contains(normalized);

                // 2) Coincidencia por nombre: solo si está inicializado (si es lazy sin sesión, lo capturamos)
                boolean matchNombre = false;
                try {
                    // Evita usar toString(). Arma nombre completo si es posible.
                    String nombres = s.getNombres(); // puede disparar inicialización
                    String apellidos = s.getApellidos();
                    if (nombres != null || apellidos != null) {
                        String nombreCompleto = ((nombres != null ? nombres : "") + " " +
                                                 (apellidos != null ? apellidos : "")).trim();
                        if (!nombreCompleto.isEmpty()) {
                            matchNombre = nombreCompleto.toLowerCase().contains(normalized);
                        }
                    }
                } catch (org.hibernate.LazyInitializationException ex) {
                    // No hay sesión; ignoramos nombre y nos quedamos solo con el filtro por carnet.
                    matchNombre = false;
                }

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

    public String estadoActualNombre(int ticketId) {
        try {
            var m = ticketService.getClass().getMethod("findUltimoHistorial", int.class);
            @SuppressWarnings("unchecked")
            Optional<Historial> opt = (Optional<Historial>) m.invoke(ticketService, ticketId);
            return opt.map(this::nombreEstadoDe)
                      .orElse("—");
        } catch (ReflectiveOperationException ignored) {
            List<Historial> hist = historial(ticketId);
            if (hist == null || hist.isEmpty()) return "—";
            Historial ultimo = elegirUltimo(hist);
            return nombreEstadoDe(ultimo);
        }
    }

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
        try {
            var m = e.getClass().getMethod("getNombre");
            Object v = m.invoke(e);
            if (v instanceof String s && !s.isBlank()) return s;
        } catch (Exception ignored) {}
        return String.valueOf(e);
    }

    private Historial elegirUltimo(List<Historial> hist) {
        if (hist.size() == 1) return hist.get(0);

        List<Historial> copia = new ArrayList<>(hist);
        copia.sort((a, b) -> {
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
