package controlador;

import dao.TicketDAO;
import modelo.Ticket;
import modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "TicketServlet", urlPatterns = {"/TicketServlet"})
public class TicketServlet extends HttpServlet {

    private final TicketDAO tdao = new TicketDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        if ("list".equals(action)) {
            listarTicketsUsuario(req, resp);
        } else {
            listarTicketsUsuario(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "create";

        if ("create".equals(action)) {
            crearTicketUsuario(req, resp);
        } else {
            crearTicketUsuario(req, resp);
        }
    }

    private void listarTicketsUsuario(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession sesion = req.getSession(false);
        Usuario u = (sesion != null) ? (Usuario) sesion.getAttribute("usuario") : null;

        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/vistas/vistasUsuario/login.jsp");
            return;
        }

        List<Ticket> lista = tdao.listarPorSolicitante(u.getCarnet());
        req.setAttribute("listTickets", lista);

        req.getRequestDispatcher("/vistas/vistasUsuario/interfazUsuario.jsp")
                .forward(req, resp);
    }

    private void crearTicketUsuario(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession sesion = req.getSession(false);
        Usuario u = (sesion != null) ? (Usuario) sesion.getAttribute("usuario") : null;

        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/vistas/vistasUsuario/login.jsp");
            return;
        }

        String categoriaStr = req.getParameter("categoria");
        String fechaSucesoStr = req.getParameter("fechaSuceso");
        String descripcion = req.getParameter("descripcion");

        if (categoriaStr == null || categoriaStr.isEmpty()
                || fechaSucesoStr == null || fechaSucesoStr.isEmpty()
                || descripcion == null || descripcion.trim().isEmpty()) {

            sesion.setAttribute("error", "Debes llenar todos los campos.");
            resp.sendRedirect(req.getContextPath() + "/TicketServlet?action=list");
            return;
        }

        int idCategoria;
        Date fechaSuceso;

        try {
            idCategoria = Integer.parseInt(categoriaStr);
            fechaSuceso = Date.valueOf(fechaSucesoStr);
        } catch (Exception ex) {
            sesion.setAttribute("error", "Datos inválidos.");
            resp.sendRedirect(req.getContextPath() + "/TicketServlet?action=list");
            return;
        }

        Ticket t = new Ticket();
        t.setSolicitante(u.getCarnet());
        t.setIdCategoria(idCategoria);
        t.setDescripcion(descripcion.trim());
        t.setFechaSuceso(fechaSuceso);

        boolean ok = tdao.crearTicketUsuario(t);

        sesion.setAttribute(ok ? "msg" : "error",
                ok ? "Ticket creado correctamente." : "No se pudo crear el ticket.");

        resp.sendRedirect(req.getContextPath() + "/TicketServlet?action=list");
    }
}
