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
public class Administrador extends Usuario{
    
    
        public Administrador() {
    }

    public Administrador(String id, Usuario usuario) {
        super(usuario.getId(),usuario.getNombres(),usuario.getApellidos(), usuario.getPasswordHash());
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
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId());
    }
}
