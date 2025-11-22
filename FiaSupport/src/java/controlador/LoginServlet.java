package controlador;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String carnet = req.getParameter("carnet");
        String pass   = req.getParameter("password");

        if (carnet == null || carnet.isBlank() || pass == null || pass.isBlank()) {
            req.setAttribute("error", "Debe llenar carnet y contraseña.");
            req.getRequestDispatcher("/vistas/login.jsp").forward(req, resp);
            return;
        }

        Usuario u = usuarioDAO.validarLogin(carnet.trim(), pass.trim());

        if (u == null) {
            req.setAttribute("error", "Carnet o contraseña incorrectos.");
            req.getRequestDispatcher("/vistas/login.jsp").forward(req, resp);
            return;
        }

        HttpSession ses = req.getSession(true);
        ses.setAttribute("usuario", u);
        ses.setAttribute("rol", u.getRol());

        // Ajustá rutas según tus carpetas reales
        if ("ADMIN".equalsIgnoreCase(u.getRol())) {
            resp.sendRedirect(req.getContextPath() + "/vistas/vistasAdministrador/gestionarTickets.jsp");
        } else {
            resp.sendRedirect(req.getContextPath() + "/vistas/vistasUsuario/interfazUsuario.jsp");
        }
    }
}
