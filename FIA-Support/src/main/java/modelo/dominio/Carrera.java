/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dominio;

import java.util.Objects;

/**
 *
 * @author Méndez
 */
public class Carrera {

    private int id;
    private String nombre;
    private int idFacultad;  // FK para validaciones

    public Carrera(int id, String nombre, int idFacultad) {
        this.id = id;
        this.nombre = nombre;
        this.idFacultad = idFacultad;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdFacultad() {
        return idFacultad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Carrera)) {
            return false;
        }
        Carrera that = (Carrera) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
