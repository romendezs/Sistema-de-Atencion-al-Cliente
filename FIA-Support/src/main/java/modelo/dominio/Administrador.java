package modelo.dominio;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Representa a un usuario administrador de la plataforma. Los administradores
 * tienen credenciales independientes de los usuarios finales y pueden acceder
 * a las pantallas de gestión.
 */
@Entity
@Table(name = "administrador")
public class Administrador {

    @Id
    @Column(name = "id_administrador", length = 32)
    private String id;

    @Column(name = "usuario", nullable = false, unique = true, length = 64)
    private String usuario;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "nombres", nullable = false, length = 120)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 120)
    private String apellidos;

    public Administrador() {
    }

    public Administrador(String id, String usuario, String passwordHash,
                         String nombres, String apellidos) {
        this.id = id;
        this.usuario = usuario;
        this.passwordHash = passwordHash;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Administrador)) {
            return false;
        }
        Administrador that = (Administrador) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return usuario;
    }
}
