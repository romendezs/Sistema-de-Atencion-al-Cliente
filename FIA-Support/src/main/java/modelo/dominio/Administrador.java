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
@PrimaryKeyJoinColumn(name = "id_administrador", referencedColumnName = "id_usuario")
public class Administrador extends Usuario {

    public Administrador() {}

    // Ctor de conveniencia, opcional
    public Administrador(String id, String nombres, String apellidos, String passwordHash) {
        super(id, nombres, apellidos, passwordHash);
    }

    @Override
    public int hashCode() { return Objects.hash(getId()); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Administrador)) return false;
        return Objects.equals(getId(), ((Administrador) o).getId());
    }
}

