package com.tuapp.seguridad.captcha;

/** Servicio simple que delega en el proveedor que elijas (local/remote). */
public class CaptchaVerifier {
    private final CaptchaProvider provider;

    public CaptchaVerifier(CaptchaProvider provider) {
        this.provider = provider;
    }

    /** Pide un nuevo reto al proveedor. */
    public CaptchaChallenge newChallenge() {
        return provider.newChallenge();
    }

    /** Verifica la respuesta del usuario contra el reto indicado. */
    public boolean verify(String userAnswer, String token) {
        return provider.verify(userAnswer, token);
    }
}
