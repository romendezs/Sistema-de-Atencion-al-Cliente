package com.tuapp.seguridad.captcha;

import java.awt.image.BufferedImage;
import java.time.Instant;

/** Contiene los datos del reto que mostraremos en pantalla. */
public class CaptchaChallenge {
    private final String token;          // ID del reto
    private final BufferedImage image;   // imagen a mostrar (para modo local)
    private final Instant expiresAt;     // fecha/hora de expiración

    public CaptchaChallenge(String token, BufferedImage image, Instant expiresAt) {
        this.token = token;
        this.image = image;
        this.expiresAt = expiresAt;
    }

    public String getToken() { return token; }
    public BufferedImage getImage() { return image; }
    public Instant getExpiresAt() { return expiresAt; }
}
