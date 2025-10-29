package com.tuapp.seguridad.captcha;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Proveedor OFFLINE: genera una imagen y guarda la solución en memoria. */
public class LocalCaptchaProvider implements CaptchaProvider {
    private static final String ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"; // sin I/O/1/0
    private static final int LEN = 6;
    private static final int W = 180, H = 60;
    private static final Duration TTL = Duration.ofMinutes(2);

    private final SecureRandom rnd = new SecureRandom();

    /** Almacén temporal: token -> (respuesta, expiración) */
    private final Map<String, Entry> store = new ConcurrentHashMap<>();

    private static class Entry {
        final String answerLower;
        final Instant expiresAt;
        Entry(String answerLower, Instant expiresAt) {
            this.answerLower = answerLower;
            this.expiresAt = expiresAt;
        }
    }

    @Override
    public CaptchaChallenge newChallenge() {
        String text = randomText(LEN);
        String token = generateToken();
        Instant exp = Instant.now().plus(TTL);

        store.put(token, new Entry(text.toLowerCase(), exp));

        BufferedImage img = render(text);

        // Limpia expirados de forma simple
        store.entrySet().removeIf(e -> Instant.now().isAfter(e.getValue().expiresAt));

        return new CaptchaChallenge(token, img, exp);
    }

    @Override
    public boolean verify(String userAnswer, String tokenOrId) {
        if (userAnswer == null || tokenOrId == null) return false;
        Entry e = store.get(tokenOrId);
        if (e == null || Instant.now().isAfter(e.expiresAt)) {
            store.remove(tokenOrId);
            return false;
        }
        boolean ok = userAnswer.trim().toLowerCase().equals(e.answerLower);
        if (ok) store.remove(tokenOrId); // un solo uso
        return ok;
    }

    // ---- Helpers ----

    private String randomText(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(ALPHABET.charAt(rnd.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    private String generateToken() {
        byte[] buf = new byte[16]; // 128 bits
        rnd.nextBytes(buf);
        StringBuilder sb = new StringBuilder(32);
        for (byte b : buf) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    private BufferedImage render(String text) {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fondo
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, W, H);

            // Ruido de fondo
            g.setColor(new Color(220, 220, 220));
            for (int i = 0; i < 12; i++) {
                int x1 = rnd.nextInt(W), y1 = rnd.nextInt(H), x2 = rnd.nextInt(W), y2 = rnd.nextInt(H);
                g.drawLine(x1, y1, x2, y2);
            }

            // Texto con pequeñas rotaciones
            Font base = new Font(Font.SANS_SERIF, Font.BOLD, 34);
            int x = 15;
            for (char c : text.toCharArray()) {
                double angle = Math.toRadians(rnd.nextInt(21) - 10);
                AffineTransform old = g.getTransform();
                g.rotate(angle, x, H / 2.0);
                g.setColor(new Color(30, 30, 30));
                g.setFont(base);
                g.drawString(String.valueOf(c), x, 40 + rnd.nextInt(5) - 2);
                g.setTransform(old);
                x += 24 + rnd.nextInt(3);
            }

            // Más ruido
            g.setColor(new Color(180, 180, 180));
            for (int i = 0; i < 3; i++) {
                int w = 30 + rnd.nextInt(60);
                int h = 8 + rnd.nextInt(14);
                int x0 = rnd.nextInt(W - w);
                int y0 = rnd.nextInt(H - h);
                g.drawOval(x0, y0, w, h);
            }
        } finally {
            g.dispose();
        }
        return img;
    }
}
