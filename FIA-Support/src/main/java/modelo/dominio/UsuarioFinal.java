/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dominio;

import jakarta.persistence.*;
import java.util.Objects;

/**
 *
 * @author Méndez
 */
@Entity
@Table(name = "usuario_final")
public class UsuarioFinal {

    @Id
    @Column(name = "id_usuario", length = 32)
    private String id;

    @Column(name = "nombres", nullable = false, length = 120)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 120)
    private String apellidos;

    @Column(name = "es_estudiante", nullable = false)
    private boolean esEstudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_facultad")
    private Facultad facultad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrera")
    private Carrera carrera;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    public UsuarioFinal() {
    }

    public UsuarioFinal(String id, String nombres, String apellidos,
                        boolean esEstudiante, Facultad facultad, Carrera carrera) {
        this(id, nombres, apellidos, esEstudiante, facultad, carrera, null);
    }

    public UsuarioFinal(String id, String nombres, String apellidos,
                        boolean esEstudiante, Facultad facultad, Carrera carrera,
                        String passwordHash) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.esEstudiante = esEstudiante;
        this.facultad = facultad;
        this.carrera = carrera;
        this.passwordHash = passwordHash;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsuarioFinal)) {
            return false;
        }
        return Objects.equals(id, ((UsuarioFinal) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return nombres + " " + apellidos;
    }
}
