package modelo.dominio;

import java.util.Objects;

/**
 * Representa un valor agregado etiquetado (por ejemplo, conteos por estado o
 * por categoría) para ser mostrado en los reportes.
 */
public class EstadisticaConteo {

    private final String etiqueta;
    private final long total;

    public EstadisticaConteo(String etiqueta, long total) {
        this.etiqueta = etiqueta == null ? "" : etiqueta;
        this.total = total;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public long getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return etiqueta + ": " + total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstadisticaConteo)) {
            return false;
        }
        EstadisticaConteo that = (EstadisticaConteo) o;
        return total == that.total && Objects.equals(etiqueta, that.etiqueta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(etiqueta, total);
    }
}
