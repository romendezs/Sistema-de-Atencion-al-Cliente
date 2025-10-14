/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dominio;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

/**
 *
 * @author Méndez
 */

@Entity
@Table(name = "categoria")
@Immutable // <- Hibernate: prohíbe UPDATE/DELETE
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 255, updatable = false)
    private String nombre;

    @Column(name = "tipo", nullable = false, length = 255, updatable = false)
    private String tipo;

    @Column(name = "nivel_gravedad", nullable = false, length = 255, updatable = false)
    private String nivelGravedad;

    // JPA necesita un ctor por defecto (puede ser protected)
    protected Categoria() {}

    // Si necesitas construir objetos para lectura (p.ej. en tests)
    public Categoria(Integer id, String nombre, String tipo, String nivelGravedad) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.nivelGravedad = nivelGravedad;
    }

    // Solo getters (sin setters)
    public Integer getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public String getNivelGravedad() { return nivelGravedad; }

    // equals/hashCode basados en id (opcional)
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categoria)) return false;
        Categoria c = (Categoria) o;
        return id != null && id.equals(c.id);
    }
    @Override public int hashCode() { return 31; }
}
