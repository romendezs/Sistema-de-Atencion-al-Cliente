/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dominio;

/**
 *
 * @author Méndez
 */

public class Empleado {
    private final int id;
    private String nombres;
    private String apellidos;

    public Empleado(int id, String nombres, String apellidos) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public int getId(){ return id; }
    public String getNombres(){ return nombres; }
    public String getApellidos(){ return apellidos; }

    @Override public String toString(){ return nombres + " " + apellidos; }
}

