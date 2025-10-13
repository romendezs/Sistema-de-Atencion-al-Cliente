package modelo.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Implementación sencilla basada en SHA-256. No pretende reemplazar a una
 * librería especializada como BCrypt, pero ofrece un punto de partida sin
 * dependencias externas. Para entornos productivos se recomienda cambiarla
 * por un algoritmo más robusto con salt aleatorio.
 */
public class Sha256PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(char[] rawPassword) {
        if (rawPassword == null || rawPassword.length == 0) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(new String(rawPassword).getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Algoritmo SHA-256 no disponible", ex);
        }
    }

    @Override
    public boolean matches(char[] rawPassword, String encodedPassword) {
        if (encodedPassword == null) {
            return false;
        }
        return encodedPassword.equals(encode(rawPassword));
    }
}
