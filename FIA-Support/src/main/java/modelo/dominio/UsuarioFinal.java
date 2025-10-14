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
@Table(name = "usuariofinal")
@PrimaryKeyJoinColumn(name = "id_usuariofinal", referencedColumnName = "id_usuario")
public class UsuarioFinal extends Usuario {

    @Column(name = "esestudiante", nullable = false)
    private boolean esEstudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_facultad")
    private Facultad facultad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrera")
    private Carrera carrera;

    public UsuarioFinal() {
    }

    public UsuarioFinal(boolean esEstudiante, Facultad facultad, Carrera carrera, Usuario usuario) {
        super(usuario.getId(), usuario.getNombres(), usuario.getApellidos(), usuario.getPasswordHash());
        this.esEstudiante = esEstudiante;
        this.facultad = facultad;
        this.carrera = carrera;
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
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId());
    }
}
