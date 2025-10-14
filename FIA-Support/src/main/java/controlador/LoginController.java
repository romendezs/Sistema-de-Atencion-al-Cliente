package controlador;

import Vista.Admin.GestionUsuariosUI;
import Vista.Login.LoginUI;
import com.mycompany.soporte.SoporteFrame;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import javax.swing.JOptionPane;
import modelo.dominio.Administrador;
import modelo.dominio.Estado;
import modelo.dominio.UsuarioFinal;
import modelo.servicios.AuthService;
import modelo.servicios.WorkflowService;

/**
 * Controller específico para la vista de inicio de sesión. Amplía las
 * funcionalidades básicas de {@link AuthController} conectando la vista con
 * el servicio de autenticación.
 */
public class LoginController extends AuthController {

    private final LoginUI view;
    private final TicketController ticketController;
    private final WorkflowService workflowService;
    private final Supplier<SoporteFrame> soporteFrameSupplier;
    private final UserAdminController userAdminController;
    private final GestionUsuariosUI adminView;
    private final ReportingController reportingController;

    public LoginController(AuthService authService,
                           LoginUI view,
                           TicketController ticketController,
                           WorkflowService workflowService,
                           Supplier<SoporteFrame> soporteFrameSupplier,
                           UserAdminController userAdminController,
                           GestionUsuariosUI adminView,
                           ReportingController reportingController) {
        super(authService);
        this.view = Objects.requireNonNull(view, "view");
        this.ticketController = Objects.requireNonNull(ticketController, "ticketController");
        this.workflowService = Objects.requireNonNull(workflowService, "workflowService");
        this.soporteFrameSupplier = Objects.requireNonNull(soporteFrameSupplier, "soporteFrameSupplier");
        this.userAdminController = Objects.requireNonNull(userAdminController, "userAdminController");
        this.adminView = Objects.requireNonNull(adminView, "adminView");
        this.reportingController = Objects.requireNonNull(reportingController, "reportingController");
        this.view.setController(this);
    }

    public void showLogin() {
        view.setVisible(true);
    }

    public void handleLogin(String usuario, char[] password) {
        AuthService.AuthenticationResult result = authenticate(usuario, password);
        switch (result.getTipo()) {
            case USUARIO_FINAL -> openSoporteFrame(Objects.requireNonNull(result.getUsuarioFinal()));
            case ADMINISTRADOR -> openAdminFrame(Objects.requireNonNull(result.getAdministrador()));
            default -> throw new IllegalStateException("Tipo de usuario no soportado.");
        }
    }

    private void openSoporteFrame(UsuarioFinal usuario) {
        Estado estadoInicial = resolveEstadoInicial();
        SoporteFrame soporteFrame = soporteFrameSupplier.get();
        soporteFrame.configurar(ticketController, reportingController, usuario, estadoInicial);
        JOptionPane.showMessageDialog(view,
                "¡Bienvenido " + usuario.getNombres() + "!",
                "FIA Support",
                JOptionPane.INFORMATION_MESSAGE);
        view.dispose();
        soporteFrame.setLocationRelativeTo(null);
        soporteFrame.setVisible(true);
    }

    private void openAdminFrame(Administrador admin) {
        userAdminController.init();
        adminView.setTitle("FIA Support - Administración");
        JOptionPane.showMessageDialog(view,
                "¡Bienvenido " + admin.getNombres() + "!",
                "FIA Support",
                JOptionPane.INFORMATION_MESSAGE);
        view.dispose();
        adminView.setLocationRelativeTo(null);
        adminView.setVisible(true);
    }

    private Estado resolveEstadoInicial() {
        List<Estado> estados = workflowService.listarEstadosDisponibles();
        if (estados == null || estados.isEmpty()) {
            throw new IllegalStateException("No hay estados configurados para crear tickets.");
        }
        return estados.stream()
                .filter(e -> e.getTipo() != null)
                .filter(e -> {
                    String tipo = e.getTipo().trim().toLowerCase();
                    return "nuevo".equals(tipo) || "abierto".equals(tipo);
                })
                .findFirst()
                .orElse(estados.get(0));
    }
}
