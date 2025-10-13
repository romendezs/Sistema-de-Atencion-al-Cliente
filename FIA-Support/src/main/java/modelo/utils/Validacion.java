/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.utils;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 *
 * @author Méndez
 */

public final class Validacion {
    private Validacion(){}

    private static final Pattern CARNET = Pattern.compile("^[A-Za-z]{2}\\d{5}$");
    private static final Pattern LETRAS = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$");

    /** fuerza MAYÚSCULA en las 2 primeras letras y retorna el valor normalizado. */
    public static String normalizarCarnet(String carnet) {
        if (carnet == null) return "";
        carnet = carnet.trim();
        if (carnet.length() < 2) return carnet.toUpperCase(Locale.ROOT);
        String letras = carnet.substring(0, 2).toUpperCase(Locale.ROOT);
        String resto  = carnet.substring(2);
        return letras + resto;
    }
    public static boolean esCarnetValido(String carnet) {
        return carnet != null && CARNET.matcher(carnet).matches();
    }
    public static boolean esTextoLetras(String s) {
        return s != null && !s.trim().isEmpty() && LETRAS.matcher(s.trim()).matches();
    }
}

