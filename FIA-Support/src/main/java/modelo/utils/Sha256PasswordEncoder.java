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
        return Base64.getEncoder().encodeToString(hash(rawPassword));
    }

    @Override
    public boolean matches(char[] rawPassword, String encodedPassword) {
        if (encodedPassword == null) {
            return false;
        }

        byte[] expected = hash(rawPassword);

        try {
            byte[] provided = Base64.getDecoder().decode(encodedPassword.trim());
            if (MessageDigest.isEqual(expected, provided)) {
                return true;
            }
        } catch (IllegalArgumentException ex) {
            // No es Base64 válido, probaremos con representación hexadecimal.
        }

        String candidate = encodedPassword.trim();

        if ((candidate.startsWith("\\x") || candidate.startsWith("0x") || candidate.startsWith("0X"))
                && candidate.length() > 2) {
            candidate = candidate.substring(2);
        }

        if (isHex(candidate)) {
            byte[] provided = hexToBytes(candidate);
            return MessageDigest.isEqual(expected, provided);
        }

        return false;
    }

    private byte[] hash(char[] rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(new String(rawPassword).getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Algoritmo SHA-256 no disponible", ex);
        }
    }

    private boolean isHex(String value) {
        if (value.isEmpty() || (value.length() % 2) != 0) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            boolean hexDigit = (c >= '0' && c <= '9')
                    || (c >= 'a' && c <= 'f')
                    || (c >= 'A' && c <= 'F');
            if (!hexDigit) {
                return false;
            }
        }
        return true;
    }

    private byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            int high = Character.digit(hex.charAt(i), 16);
            int low = Character.digit(hex.charAt(i + 1), 16);
            bytes[i / 2] = (byte) ((high << 4) + low);
        }
        return bytes;
    }
}
