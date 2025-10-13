package Modelo.servicios;

import Modelo.dominio.UsuarioFinal;
import Modelo.repo.UsuarioRepository;
import Modelo.utils.Validacion;

import java.util.Map;
import java.util.Objects;

/**
 * Servicio para el caso de uso "Iniciar Sesión".
 */
public class AuthService {
    private final UsuarioRepository usuarios;
    private final Map<String, String> credenciales;

    public AuthService(UsuarioRepository usuarios, Map<String, String> credenciales) {
        this.usuarios = Objects.requireNonNull(usuarios, "usuarios");
        this.credenciales = Objects.requireNonNull(credenciales, "credenciales");
    }

    public UsuarioFinal login(String carnet, String password) {
        String normalizado = Validacion.normalizarCarnet(carnet);
        if (!Validacion.esCarnetValido(normalizado)) {
            throw new IllegalArgumentException("Carnet inválido. Formato: LLNNNNN.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }

        UsuarioFinal usuario = usuarios.findById(normalizado)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
        String esperado = credenciales.get(normalizado);
        if (esperado == null || !esperado.equals(password)) {
            throw new IllegalArgumentException("Credenciales incorrectas.");
        }
        return usuario;
    }
}
