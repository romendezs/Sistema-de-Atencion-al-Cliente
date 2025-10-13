package modelo.servicios;

import modelo.dominio.UsuarioFinal;
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

    private final IUsuarioRepository usuarios;
    private final PasswordEncoder passwordEncoder;

    public AuthService(IUsuarioRepository usuarios) {
        this(usuarios, new Sha256PasswordEncoder());
    }

    public AuthService(IUsuarioRepository usuarios, PasswordEncoder passwordEncoder) {
        this.usuarios = usuarios;
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
                .orElseThrow(() -> new IllegalArgumentException("Usuario o contraseña incorrectos."));

        if (usuario.getPasswordHash() == null
                || !passwordEncoder.matches(password, usuario.getPasswordHash())) {
            throw new IllegalArgumentException("Usuario o contraseña incorrectos.");
        }

        return usuario;
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
}
