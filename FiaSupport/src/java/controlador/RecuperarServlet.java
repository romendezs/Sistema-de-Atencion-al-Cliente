package controlador;

import dao.TicketDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/recuperar")
public class RecuperarServlet extends HttpServlet {

    private final TicketDAO dao = new TicketDAO();
    private static final int ID_CATEGORIA_RECUP = 7;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json;charset=UTF-8");

        // OJO: el front manda el carnet dentro del parámetro "correo"
        String carnet = req.getParameter("correo");

        try (PrintWriter out = resp.getWriter()) {

            if (carnet == null || carnet.trim().isEmpty()) {
                out.print("{\"ok\":false,\"msg\":\"Carnet vacío.\"}");
                return;
            }

            boolean ok = dao.crearTicketRecuperacion(carnet.trim(), ID_CATEGORIA_RECUP);

            if (ok) {
                out.print("{\"ok\":true}");
            } else {
                out.print("{\"ok\":false,\"msg\":\"Carnet no registrado. Verifica e inténtalo de nuevo.\"}");
            }
        }
    }
}
