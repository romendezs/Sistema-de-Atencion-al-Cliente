package modelo.utils;

/**
 * API mínima para codificar y validar contraseñas sin acoplar la capa de
 * servicio a una implementación concreta. Permite inyectar diferentes
 * estrategias (BCrypt, PBKDF2, etc.) según las necesidades del proyecto.
 */
public interface PasswordEncoder {

    /**
     * Genera una representación segura de la contraseña proporcionada.
     * Implementaciones deben tratar el arreglo como sensible y limpiarlo
     * cuando sea posible.
     */
    String encode(char[] rawPassword);

    /**
     * Verifica si la contraseña proporcionada coincide con la representación
     * almacenada.
     */
    boolean matches(char[] rawPassword, String encodedPassword);
}
