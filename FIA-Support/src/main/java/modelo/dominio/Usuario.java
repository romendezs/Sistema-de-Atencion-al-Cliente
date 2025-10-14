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
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    
    @Id
    @Column(name = "id_usuario", length = 7)
    private String id;

    @Column(name = "nombres", nullable = false, length = 120)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 120)
    private String apellidos;
    
    @Column(name = "password", nullable = false, length = 255)
    private String passwordHash;
    
    public Usuario(){
    
    }
    
    public Usuario(String id, String nombres, String apellidos, String passwordHash) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
}
