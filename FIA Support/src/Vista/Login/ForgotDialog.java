package Vista.Login;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ForgotDialog extends JDialog {

    private JTextField txtCorreo;
    private JTextField txtCarnet;
    private JCheckBox chkRobot;
    private JButton btnEnviar;
    private JButton btnCerrar;

    public ForgotDialog(Frame owner) {
        super(owner, true); // modal
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

        // Aquí iría tu lógica real (enviar correo, generar token, etc.)
        JOptionPane.showMessageDialog(this,
            "Solicitud enviada.\nSi el correo/carnet existe, recibirá instrucciones.",
            "FIA Support",
            JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
