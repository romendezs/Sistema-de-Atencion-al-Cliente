package modelo.servicios;

import modelo.dominio.Administrador;
import modelo.dominio.UsuarioFinal;
import modelo.repo.IRepository.IAdminRepository;
import modelo.repo.IRepository.IUsuarioRepository;
import modelo.utils.PasswordEncoder;
import modelo.utils.Sha256PasswordEncoder;
import modelo.utils.Validacion;

/**
 * Servicio centrado en el caso de uso "Iniciar Sesión". Contiene la lógica de
 * validación de credenciales y la actualización de contraseñas, evitando que
 * la capa de presentación interactúe directamente con los repositorios.
 */
public class AuthService {

    private static final String MSG_INVALID_CREDENTIALS = "Usuario o contraseña incorrectos.";

    private final IUsuarioRepository usuarios;
    private final IAdminRepository administradores;
    private final PasswordEncoder passwordEncoder;

    public AuthService(IUsuarioRepository usuarios) {
        this(usuarios, null, new Sha256PasswordEncoder());
    }

    public AuthService(IUsuarioRepository usuarios, IAdminRepository administradores) {
        this(usuarios, administradores, new Sha256PasswordEncoder());
    }

    public AuthService(IUsuarioRepository usuarios,
                       IAdminRepository administradores,
                       PasswordEncoder passwordEncoder) {
        this.usuarios = usuarios;
        this.administradores = administradores;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioFinal login(String carnet, char[] password) {
        if (password == null || password.length == 0) {
            throw new IllegalArgumentException("Debe proporcionar la contraseña.");
        }

        carnet = Validacion.normalizarCarnet(carnet);
        if (!Validacion.esCarnetValido(carnet)) {
            throw new IllegalArgumentException("Carnet inválido. Formato esperado LLNNNNN.");
        }

        UsuarioFinal usuario = usuarios.findById(carnet)
                .orElseThrow(() -> new IllegalArgumentException(MSG_INVALID_CREDENTIALS));

        if (usuario.getPasswordHash() == null
                || !passwordEncoder.matches(password, usuario.getPasswordHash())) {
            throw new IllegalArgumentException(MSG_INVALID_CREDENTIALS);
        }

        return usuario;
    }

    public AuthenticationResult authenticate(String identificador, char[] password) {
        if (password == null || password.length == 0) {
            throw new IllegalArgumentException("Debe proporcionar la contraseña.");
        }
        String input = identificador == null ? "" : identificador.trim();
        if (input.isEmpty()) {
            throw new IllegalArgumentException(MSG_INVALID_CREDENTIALS);
        }

        String carnet = Validacion.normalizarCarnet(input);
        if (Validacion.esCarnetValido(carnet)) {
            try {
                UsuarioFinal usuario = login(carnet, password);
                return AuthenticationResult.forUsuarioFinal(usuario);
            } catch (IllegalArgumentException ex) {
                if (!MSG_INVALID_CREDENTIALS.equals(ex.getMessage())) {
                    throw ex;
                }
                // si las credenciales no corresponden a un usuario final, intentamos con admin
            }
        }

        if (administradores == null) {
            throw new IllegalArgumentException(MSG_INVALID_CREDENTIALS);
        }

        Administrador admin = administradores.findByUsuario(input)
                .orElseThrow(() -> new IllegalArgumentException(MSG_INVALID_CREDENTIALS));

        if (admin.getPasswordHash() == null
                || !passwordEncoder.matches(password, admin.getPasswordHash())) {
            throw new IllegalArgumentException(MSG_INVALID_CREDENTIALS);
        }

        return AuthenticationResult.forAdministrador(admin);
    }

    public UsuarioFinal cambiarPassword(String carnet, char[] passwordActual, char[] nuevaPassword) {
        if (nuevaPassword == null || nuevaPassword.length < 6) {
            throw new IllegalArgumentException("La nueva contraseña debe tener al menos 6 caracteres.");
        }

        UsuarioFinal usuario = login(carnet, passwordActual);
        usuario.setPasswordHash(passwordEncoder.encode(nuevaPassword));
        usuarios.save(usuario);
        return usuario;
    }

    public static final class AuthenticationResult {

        public enum Tipo { USUARIO_FINAL, ADMINISTRADOR }

        private final Tipo tipo;
        private final UsuarioFinal usuarioFinal;
        private final Administrador administrador;

        private AuthenticationResult(Tipo tipo, UsuarioFinal usuarioFinal, Administrador administrador) {
            this.tipo = tipo;
            this.usuarioFinal = usuarioFinal;
            this.administrador = administrador;
        }

        public static AuthenticationResult forUsuarioFinal(UsuarioFinal usuario) {
            return new AuthenticationResult(Tipo.USUARIO_FINAL, usuario, null);
        }

        public static AuthenticationResult forAdministrador(Administrador administrador) {
            return new AuthenticationResult(Tipo.ADMINISTRADOR, null, administrador);
        }

        public Tipo getTipo() {
            return tipo;
        }

        public UsuarioFinal getUsuarioFinal() {
            return usuarioFinal;
        }

        public Administrador getAdministrador() {
            return administrador;
        }
    }
}
