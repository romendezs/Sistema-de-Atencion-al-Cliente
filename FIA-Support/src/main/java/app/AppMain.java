package app;

import Vista.Admin.GestionUsuariosUI;
import Vista.Login.LoginUI;
import com.mycompany.soporte.SoporteFrame;
import controlador.LoginController;
import controlador.ReportingController;
import controlador.TicketController;
import controlador.UserAdminController;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.db.JPAUtil;
import modelo.repo.AdminRepository;
import modelo.repo.CarreraRepository;
import modelo.repo.EstadisticasAdminRepository;
import modelo.repo.EstadisticasUsuarioRepository;
import modelo.repo.FacultadRepository;
import modelo.repo.TicketRepository;
import modelo.repo.UsuarioRepository;
import modelo.servicios.AuthService;
import modelo.servicios.ReportingService;
import modelo.servicios.TicketService;
import modelo.servicios.UserAdminService;
import modelo.servicios.UsuarioService;
import modelo.servicios.WorkflowService;

/**
 * Punto de entrada de la aplicación. Configura las dependencias necesarias y
 * muestra la pantalla de inicio de sesión.
 */
public final class AppMain {

    private AppMain() {
        // Evita la instanciación
    }

    public static void main(String[] args) {
        configurarLookAndFeel();
        Runtime.getRuntime().addShutdownHook(new Thread(JPAUtil::close));

        SwingUtilities.invokeLater(() -> {
            UsuarioRepository usuarioRepository = new UsuarioRepository();
            AdminRepository adminRepository = new AdminRepository();
            AuthService authService = new AuthService(usuarioRepository, adminRepository);

            TicketRepository ticketRepository = new TicketRepository();
            TicketService ticketService = new TicketService(ticketRepository, usuarioRepository);
            TicketController ticketController = new TicketController(ticketService);
            WorkflowService workflowService = new WorkflowService(ticketRepository);

            ReportingService reportingService = new ReportingService(
                    new EstadisticasAdminRepository(),
                    new EstadisticasUsuarioRepository());
            ReportingController reportingController = new ReportingController(reportingService);

            FacultadRepository facultadRepository = new FacultadRepository();
            CarreraRepository carreraRepository = new CarreraRepository();
            UsuarioService usuarioService = new UsuarioService(usuarioRepository, facultadRepository, carreraRepository);
            UserAdminService userAdminService = new UserAdminService(usuarioService);
            GestionUsuariosUI adminView = new GestionUsuariosUI();
            adminView.setReportingController(reportingController);
            UserAdminController userAdminController = new UserAdminController(userAdminService, adminView);

            LoginUI loginUI = new LoginUI();
            LoginController loginController = new LoginController(
                    authService,
                    loginUI,
                    ticketController,
                    workflowService,
                    SoporteFrame::new,
                    userAdminController,
                    adminView,
                    reportingController
            );
            loginController.showLogin();
        });
    }

    private static void configurarLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
        {
            System.err.println("No se pudo establecer Nimbus L&F: " + ex.getMessage());
        }
    }
}
