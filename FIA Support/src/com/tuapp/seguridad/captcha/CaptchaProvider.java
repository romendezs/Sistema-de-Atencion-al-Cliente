package com.tuapp.seguridad.captcha;

/** Contrato para cualquier proveedor de CAPTCHA (local u online). */
public interface CaptchaProvider {

    /** Crea un nuevo reto y devuelve el objeto con imagen/token/expiración. */
    CaptchaChallenge newChallenge();

    /**
     * Verifica la respuesta del usuario contra el reto actual.
     * @param userAnswer  Texto que escribió el usuario.
     * @param tokenOrId   Token/ID del reto que queremos validar.
     * @return true si es correcto, false en caso contrario.
     */
    boolean verify(String userAnswer, String tokenOrId);
}
