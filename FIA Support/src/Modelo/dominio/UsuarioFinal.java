/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.dominio;

import java.util.Objects;

/**
 *
 * @author Méndez
 */
public class UsuarioFinal {

    private String id;          // carnet (varchar)  ej. MS24030
    private String nombres;
    private String apellidos;
    private boolean esEstudiante;
    private Facultad facultad;  // null si no es estudiante
    private Carrera carrera;    // null si no es estudiante

    public UsuarioFinal(String id, String nombres, String apellidos,
            boolean esEstudiante, Facultad facultad, Carrera carrera) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.esEstudiante = esEstudiante;
        this.facultad = facultad;
        this.carrera = carrera;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isEsEstudiante() {
        return esEstudiante;
    }

    public void setEsEstudiante(boolean esEstudiante) {
        this.esEstudiante = esEstudiante;
    }

    public Facultad getFacultad() {
        return facultad;
    }

    public void setFacultad(Facultad facultad) {
        this.facultad = facultad;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsuarioFinal)) {
            return false;
        }
        UsuarioFinal that = (UsuarioFinal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
