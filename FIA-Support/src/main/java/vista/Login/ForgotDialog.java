package Vista.Login;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controlador.LoginController;

public class ForgotDialog extends JDialog {

    private JTextField txtCorreo;
    private JTextField txtCarnet;
<<<<<<< Updated upstream
    private JCheckBox chkRobot;
    private JButton btnEnviar;
    private JButton btnCerrar;

    public ForgotDialog(Frame owner) {
=======
    private JLabel lblCaptchaImg;
    private JTextField txtCaptchaInput;
    private JButton btnRefreshCaptcha;
    private JButton btnEnviar;
    private JButton btnCerrar;
    private String captchaActual;

    private LoginController controller;  // <-- referencia al controlador

    // Placeholders
    private static final String PLACEHOLDER_CORREO = "Digite su correo electrónico personal";
    private static final String PLACEHOLDER_CARNET = "Digite su Carnet. Ejemplo ZG06055";

    // ✅ Constructor nuevo (EL BUENO) que recibe controller
    public ForgotDialog(Frame owner, LoginController controller) {
>>>>>>> Stashed changes
        super(owner, true); // modal
        this.controller = controller; // <-- guardamos el controller
        buildUI(owner);
    }

    // ✅ Constructor viejo para no romper si aún llamas new ForgotDialog(LoginUI.this)
    public ForgotDialog(Frame owner) {
        super(owner, true);
        this.controller = null;
        buildUI(owner);
    }

    // ---------------- UI (lo que ya tenías) ----------------
    private void buildUI(Frame owner) {
        setTitle("Problemas con el carnet o la contraseña");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        // Colores base
        Color rojo = new Color(180, 40, 25);
        Color fondo = Color.WHITE;
        Color grisTxt = new Color(80, 80, 80);

        // Panel raíz
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(fondo);
        content.setBorder(new EmptyBorder(12, 12, 12, 12));
        setContentPane(content);

        // Cabecera (título + botón cerrar)
        JLabel lblTitulo = new JLabel("Problemas con el carnet o la contraseña");
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 18f));
        lblTitulo.setForeground(grisTxt);

        btnCerrar = new JButton("✕");
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnCerrar.setBackground(fondo);
        btnCerrar.setForeground(new Color(120,120,120));
        btnCerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCerrar.addActionListener((e) -> dispose());

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(lblTitulo, BorderLayout.WEST);
        header.add(btnCerrar, BorderLayout.EAST);
        content.add(header, BorderLayout.NORTH);

        // Centro (formulario)
        JPanel center = new JPanel();
        center.setOpaque(false);
        GroupLayout gl = new GroupLayout(center);
        center.setLayout(gl);

        JLabel lblOlvidoCarnet = new JLabel("¿Olvidó su carnet o aun no lo tienes?");
        lblOlvidoCarnet.setForeground(grisTxt);
        lblOlvidoCarnet.setFont(lblOlvidoCarnet.getFont().deriveFont(Font.BOLD));

        txtCorreo = new JTextField();
        txtCorreo.setToolTipText("Digite su correo electrónico personal");

        JLabel lblOlvidoPass = new JLabel("¿Olvidó la contraseña?");
        lblOlvidoPass.setForeground(grisTxt);
        lblOlvidoPass.setFont(lblOlvidoPass.getFont().deriveFont(Font.BOLD));

        txtCarnet = new JTextField();
        txtCarnet.setToolTipText("Digite su carnet");

        chkRobot = new JCheckBox("I'm not a robot");
        chkRobot.setOpaque(false);

        btnEnviar = new JButton("Enviar");
        btnEnviar.setBackground(rojo);
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setFocusPainted(false);
        btnEnviar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnEnviar.addActionListener(this::onEnviar);

        // Layout del formulario
        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);
        gl.setHorizontalGroup(
            gl.createParallelGroup(GroupLayout.Alignment.LEADING)
              .addComponent(lblOlvidoCarnet)
              .addComponent(txtCorreo)
              .addComponent(lblOlvidoPass)
              .addComponent(txtCarnet)
              .addGroup(gl.createSequentialGroup()
                   .addComponent(chkRobot)
                   .addGap(0, 0, Short.MAX_VALUE)
                   .addComponent(btnEnviar, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
        );
        gl.setVerticalGroup(
            gl.createSequentialGroup()
              .addComponent(lblOlvidoCarnet)
              .addComponent(txtCorreo, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
              .addGap(10)
              .addComponent(lblOlvidoPass)
              .addComponent(txtCarnet, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
              .addGap(10)
              .addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                   .addComponent(chkRobot)
                   .addComponent(btnEnviar, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
        );

        content.add(center, BorderLayout.CENTER);

        // Tamaño y ubicación
        setSize(520, 260);
        setLocationRelativeTo(owner);
    }

<<<<<<< Updated upstream
=======
    // ===================== PLACEHOLDER =====================
    private void addPlaceholder(JTextField field, String placeholder) {
        Color placeholderColor = new Color(120, 120, 120); // gris visible
        Color normalColor = Color.BLACK;

        field.setText(placeholder);
        field.setForeground(placeholderColor);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(normalColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().trim().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(placeholderColor);
                }
            }
        });
    }

    // ===================== CAPTCHA =====================
    private void generarCaptcha() {
        captchaActual = crearTextoCaptcha(6);
        ImageIcon icon = crearImagenCaptcha(captchaActual);
        lblCaptchaImg.setIcon(icon);
    }

    private String crearTextoCaptcha(int length) {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"; // sin I,O,0,1
        StringBuilder sb = new StringBuilder();
        java.util.Random r = new java.util.Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(r.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private ImageIcon crearImagenCaptcha(String text) {
        int w = 160, h = 50;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, w, h);

        java.util.Random r = new java.util.Random();

        for (int i = 0; i < 8; i++) {
            g2.setColor(new Color(r.nextInt(150), r.nextInt(150), r.nextInt(150)));
            int x1 = r.nextInt(w), y1 = r.nextInt(h);
            int x2 = r.nextInt(w), y2 = r.nextInt(h);
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setFont(new Font("Arial", Font.BOLD, 28));
        int x = 15;
        for (int i = 0; i < text.length(); i++) {
            g2.setColor(new Color(r.nextInt(120), r.nextInt(120), r.nextInt(120)));
            double angle = (r.nextDouble() - 0.5) * 0.5;
            g2.rotate(angle, x, 30);
            g2.drawString(String.valueOf(text.charAt(i)), x, 35);
            g2.rotate(-angle, x, 30);
            x += 22;
        }

        g2.dispose();
        return new ImageIcon(img);
    }

    // ===================== ENVIAR =====================
>>>>>>> Stashed changes
    private void onEnviar(ActionEvent e) {
        // Validaciones básicas (ajusta según tu backend)
        if (!chkRobot.isSelected()) {
            JOptionPane.showMessageDialog(this, "Confirme el reCAPTCHA.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String correo = txtCorreo.getText().trim();
        String carnet = txtCarnet.getText().trim();

        if (correo.isEmpty() && carnet.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese correo o carnet para continuar.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

<<<<<<< Updated upstream
        // Aquí iría tu lógica real (enviar correo, generar token, etc.)
=======
        if (correoVacio && carnetVacio) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar correo personal O carnet para continuar.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 4) Crear ticket + mensaje según la opción usada
        if (!correoVacio) {

            // ✅ CREA TICKET EN BD (PENDIENTE)
            if (controller != null) {
                controller.crearTicketRecuperacion(
                        correo,
                        "Solicitud de carnet / corrección de acceso"
                );
            }

            JOptionPane.showMessageDialog(this,
                    "Se ha enviado un correo con las instrucciones para tramitar el carnet a:\n"
                            + correo,
                    "FIA Support",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
            return;
        }

        // Si llegó aquí, entonces carnet NO está vacío

        // ✅ CREA TICKET EN BD (PENDIENTE)
        if (controller != null) {
            controller.crearTicketRecuperacion(
                    carnet,
                    "Solicitud de restablecimiento de contraseña"
            );
        }

>>>>>>> Stashed changes
        JOptionPane.showMessageDialog(this,
            "Solicitud enviada.\nSi el correo/carnet existe, recibirá instrucciones.",
            "FIA Support",
            JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
