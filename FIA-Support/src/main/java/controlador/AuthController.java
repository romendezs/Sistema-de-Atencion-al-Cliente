package controlador;

import modelo.dominio.UsuarioFinal;
import modelo.servicios.AuthService;

/**
 * Controller that wires the authentication use case with the Swing layer.
 * It delegates every validation to {@link AuthService} so the views do not
 * access repositories directly.
 */
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    protected AuthService getAuthService() {
        return authService;
    }

    public UsuarioFinal login(String carnet, char[] password) {
        return authService.login(carnet, password);
    }

    public AuthService.AuthenticationResult authenticate(String usuario, char[] password) {
        return authService.authenticate(usuario, password);
    }

    public UsuarioFinal changePassword(String carnet, char[] currentPassword, char[] newPassword) {
        return authService.cambiarPassword(carnet, currentPassword, newPassword);
    }
}
