package com.tuapp.seguridad.captcha;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Panel Swing listo para mostrar el CAPTCHA (layout vertical):
 *  Imagen
 *  Botón "Actualizar"
 *  Texto "Expira: ..."
 *  "Escribe lo que ves" + campo de texto
 */
public class CaptchaPanel extends JPanel {

    private final CaptchaVerifier verifier;
    private CaptchaChallenge current;

    private final JLabel lblImagen = new JLabel();
    private final JTextField txtRespuesta = new JTextField();
    private final JButton btnActualizar = new JButton("Actualizar");
    private final JLabel lblExpira = new JLabel();

    public CaptchaPanel(CaptchaVerifier verifier) {
        this.verifier = verifier;

        // Tamaño general del panel
        setOpaque(false);
        setLayout(new BorderLayout(6, 6));
        setPreferredSize(new Dimension(320, 200));

        // ====== IMAGEN (arriba, ocupa todo el ancho) ======
        JPanel pImagen = new JPanel(new BorderLayout());
        pImagen.setOpaque(false);

        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setOpaque(true);
        lblImagen.setBackground(Color.WHITE);
        lblImagen.setBorder(BorderFactory.createLineBorder(new Color(120,120,120)));
        lblImagen.setPreferredSize(new Dimension(260, 80));
        pImagen.add(lblImagen, BorderLayout.CENTER);

        add(pImagen, BorderLayout.NORTH);

        // ====== CONTROLES (debajo de la imagen, en columna) ======
        JPanel pControles = new JPanel();
        pControles.setOpaque(false);
        pControles.setLayout(new BoxLayout(pControles, BoxLayout.Y_AXIS));

        btnActualizar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnActualizar.setFocusable(false);
        pControles.add(btnActualizar);

        pControles.add(Box.createVerticalStrut(4));

        lblExpira.setFont(lblExpira.getFont().deriveFont(Font.PLAIN, 11f));
        lblExpira.setAlignmentX(Component.CENTER_ALIGNMENT);
        pControles.add(lblExpira);

        add(pControles, BorderLayout.CENTER);

        // ====== RESPUESTA (abajo, fila label + textfield) ======
        JPanel bottom = new JPanel(new BorderLayout(6, 6));
        bottom.setOpaque(false);
        bottom.add(new JLabel("Escribe lo que ves:"), BorderLayout.WEST);
        bottom.add(txtRespuesta, BorderLayout.CENTER);

        add(bottom, BorderLayout.SOUTH);

        // Eventos
        btnActualizar.addActionListener(e -> reload());

        // Primer reto
        reload();
    }

    /** Regenera el reto y limpia el campo. */
    public final void reload() {
        current = verifier.newChallenge();
        BufferedImage img = current.getImage();

        // Fallback por si en algún caso extremo viene null
        if (img == null) {
            lblImagen.setText("Generando CAPTCHA...");
            lblImagen.setIcon(null);
        } else {
            lblImagen.setText(null);
            lblImagen.setIcon(new ImageIcon(img));
        }

        txtRespuesta.setText("");
        if (current.getExpiresAt() != null) {
            lblExpira.setText("Expira: " + current.getExpiresAt());
        } else {
            lblExpira.setText("");
        }

        revalidate();
        repaint();
        txtRespuesta.requestFocusInWindow();
    }

    /**
     * Verifica la respuesta actual.
     * @return true si es correcta; si falla, regenera automáticamente un reto nuevo.
     */
    public boolean verifyNow() {
        if (current == null) return false;
        String ans = txtRespuesta.getText();
        boolean ok = verifier.verify(ans, current.getToken());
        if (!ok) {
            // si falla, renovamos para evitar reintentos con el mismo reto
            reload();
        }
        return ok;
    }

    // (Opcional) Exponer el JTextField por si quieres agregar validadores externos
    public JTextField getTxtRespuesta() { return txtRespuesta; }
    public JLabel getLblImagen() { return lblImagen; }
}
