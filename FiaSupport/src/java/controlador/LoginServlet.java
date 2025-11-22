package controlador;

import dao.UsuarioDAO;
import modelo.Usuario;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UsuarioDAO udao = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String carnet = req.getParameter("carnet");
        String password = req.getParameter("password");

        if (carnet != null) carnet = carnet.trim().toUpperCase();
        if (password != null) password = password.trim();

        if (carnet == null || password == null || carnet.isEmpty() || password.isEmpty()) {
            req.setAttribute("error", "Debes ingresar carnet y contraseña.");
            req.getRequestDispatcher("/vistas/vistasUsuario/login.jsp").forward(req, resp);
            return;
        }

        // ✅ TU validarLogin devuelve Usuario
        Usuario u = udao.validarLogin(carnet, password);

        if (u == null) {
            req.setAttribute("error", "Carnet o contraseña incorrectos.");
            req.getRequestDispatcher("/vistas/vistasUsuario/login.jsp").forward(req, resp);
            return;
        }

        // ✅ sesión con el usuario completo
        HttpSession sesion = req.getSession(true);
        sesion.setAttribute("usuario", u);

        // ✅ redirección por rol admin
        if (udao.esAdmin(carnet)) {
            u.setRol("ADMIN"); // opcional, por si luego lo usas
            resp.sendRedirect(req.getContextPath() + "/vistas/vistasAdministrador/reportes.jsp");
        } else {
            u.setRol("USUARIO");
            resp.sendRedirect(req.getContextPath() + "/vistas/vistasUsuario/interfazUsuario.jsp");
        }
    }
}
