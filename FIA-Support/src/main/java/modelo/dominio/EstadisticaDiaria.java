package modelo.dominio;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Representa un agregado diario de tickets asociado a una fecha específica.
 */
public class EstadisticaDiaria {

    private final LocalDate fecha;
    private final long total;

    public EstadisticaDiaria(LocalDate fecha, long total) {
        this.fecha = fecha;
        this.total = total;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public long getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return fecha + ": " + total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstadisticaDiaria)) {
            return false;
        }
        EstadisticaDiaria that = (EstadisticaDiaria) o;
        return total == that.total && Objects.equals(fecha, that.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fecha, total);
    }
}
