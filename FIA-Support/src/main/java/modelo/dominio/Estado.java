/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dominio;

/**
 *
 * @author Méndez
 */
public enum Estado {
    CREADO("Creado"),
    PENDIENTE("Pendiente de Asignación"),
    ASIGNADO("Asignado"),
    EN_PROCESO("En Proceso"),
    CERRADO("Cerrado");

    private final String rotulo;

    Estado(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }

    @Override
    public String toString() {
        return rotulo;
    }
}
