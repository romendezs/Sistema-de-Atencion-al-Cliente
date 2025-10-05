package ui;

public class LoginFrame2 extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(LoginFrame2.class.getName());

    // ==== Placeholders & colores ====
    private static final String PLACEHOLDER_CARNET = "Digite su carnet. Ejemplo: ÑZ03021";
    private static final String PLACEHOLDER_PASS   = "Digite su contraseña";

    private static final java.awt.Color COLOR_PLACEHOLDER = new java.awt.Color(102,102,102);
    private static final java.awt.Color COLOR_TEXTO       = java.awt.Color.BLACK;

    // echo char por defecto
    private char echoDefault = '\u2022';

    public LoginFrame2() {
        initComponents();

        // --- centrar jPanel3 dentro del frame ---
        javax.swing.JPanel centerRoot = new javax.swing.JPanel(new java.awt.GridBagLayout());
        centerRoot.setBackground(getContentPane().getBackground());
        centerRoot.add(jPanel3, new java.awt.GridBagConstraints());
        setContentPane(centerRoot);
        pack();
        setLocationRelativeTo(null);

        // --- al abrir la ventana, aplicar imagen y placeholders ---
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowOpened(java.awt.event.WindowEvent e) {
                applyLogoFit();
                initPlaceholders();
            }
        });
    }

    // === Métodos propios (EDITABLES) ===
    // Ajusta el logo al cuadro del JLabel sin deformar y lo centra (letterboxing)
    private void applyLogoFit() {
        try {
            Label_Image.setText(""); // por si quedó texto
            javax.swing.ImageIcon raw = new javax.swing.ImageIcon(
                getClass().getResource("/img/Fia_Support_Logo.png")
            );
            int boxW = Label_Image.getWidth();
            int boxH = Label_Image.getHeight();
            int imgW = raw.getIconWidth();
            int imgH = raw.getIconHeight();

            double scale = Math.min(boxW / (double) imgW, boxH / (double) imgH);
            int drawW = Math.max(1, (int)Math.round(imgW * scale));
            int drawH = Math.max(1, (int)Math.round(imgH * scale));

            java.awt.Image scaled = raw.getImage()
                .getScaledInstance(drawW, drawH, java.awt.Image.SCALE_SMOOTH);
            Label_Image.setIcon(new javax.swing.ImageIcon(scaled));

            int padX = Math.max(0, (boxW - drawW) / 2);
            int padY = Math.max(0, (boxH - drawH) / 2);
            Label_Image.setBorder(javax.swing.BorderFactory.createEmptyBorder(padY, padX, padY, padX));
            Label_Image.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            Label_Image.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        } catch (Exception ex) {
            // opcional: log
        }
    }

    private void initPlaceholders() {
        // carnet
        txtField_Carnet.setForeground(COLOR_PLACEHOLDER);

        // contraseña
        char ec = PswrdField_Digite_su_contraseña.getEchoChar();
        if (ec != 0) { echoDefault = ec; }
        PswrdField_Digite_su_contraseña.setText(PLACEHOLDER_PASS);
        PswrdField_Digite_su_contraseña.setForeground(COLOR_PLACEHOLDER);
        PswrdField_Digite_su_contraseña.setEchoChar((char)0);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        grayPanel1st = new javax.swing.JPanel();
        Text_FIA_Support = new javax.swing.JLabel();
        redPanel2nd = new javax.swing.JPanel();
        Text_Bienvenido = new javax.swing.JLabel();
        redPanel3rd = new javax.swing.JPanel();
        Text_Ingresa_tu_usuario = new javax.swing.JLabel();
        txtField_Carnet = new javax.swing.JTextField();
        Text_Carnet = new javax.swing.JLabel();
        Text_Contrasena = new javax.swing.JLabel();
        Btn_Iniciar_Sesion = new javax.swing.JButton();
        PswrdField_Digite_su_contraseña = new javax.swing.JPasswordField();
        Label_Image = new javax.swing.JLabel();
        TextLink_Problemas = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        grayPanel1st.setBackground(new java.awt.Color(204, 204, 204));

        Text_FIA_Support.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        Text_FIA_Support.setText("FIA Support");

        redPanel2nd.setBackground(new java.awt.Color(204, 0, 0));

        Text_Bienvenido.setFont(new java.awt.Font("Corbel", 1, 36)); // NOI18N
        Text_Bienvenido.setForeground(new java.awt.Color(255, 255, 255));
        Text_Bienvenido.setText("Bienvenido");

        javax.swing.GroupLayout redPanel2ndLayout = new javax.swing.GroupLayout(redPanel2nd);
        redPanel2nd.setLayout(redPanel2ndLayout);
        redPanel2ndLayout.setHorizontalGroup(
            redPanel2ndLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(redPanel2ndLayout.createSequentialGroup()
                .addGap(174, 174, 174)
                .addComponent(Text_Bienvenido)
                .addContainerGap(188, Short.MAX_VALUE))
        );
        redPanel2ndLayout.setVerticalGroup(
            redPanel2ndLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(redPanel2ndLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Text_Bienvenido))
        );

        javax.swing.GroupLayout grayPanel1stLayout = new javax.swing.GroupLayout(grayPanel1st);
        grayPanel1st.setLayout(grayPanel1stLayout);
        grayPanel1stLayout.setHorizontalGroup(
            grayPanel1stLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grayPanel1stLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(Text_FIA_Support, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, grayPanel1stLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(redPanel2nd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        grayPanel1stLayout.setVerticalGroup(
            grayPanel1stLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grayPanel1stLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Text_FIA_Support)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(redPanel2nd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        Text_FIA_Support.getAccessibleContext().setAccessibleDescription("Text_FIA_Support");

        redPanel3rd.setBackground(new java.awt.Color(204, 0, 0));

        Text_Ingresa_tu_usuario.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        Text_Ingresa_tu_usuario.setForeground(new java.awt.Color(255, 255, 255));
        Text_Ingresa_tu_usuario.setText("Ingresa tu usuario");

        txtField_Carnet.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtField_Carnet.setForeground(new java.awt.Color(102, 102, 102));
        txtField_Carnet.setText("Digite su carnet. Ejemplo: ÑZ03021");
        txtField_Carnet.setToolTipText("");
        txtField_Carnet.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtField_CarnetFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtField_CarnetFocusLost(evt);
            }
        });
        txtField_Carnet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtField_CarnetActionPerformed(evt);
            }
        });

        Text_Carnet.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        Text_Carnet.setForeground(new java.awt.Color(255, 255, 255));
        Text_Carnet.setText("Carnet");

        Text_Contrasena.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        Text_Contrasena.setForeground(new java.awt.Color(255, 255, 255));
        Text_Contrasena.setText("Contraseña");

        Btn_Iniciar_Sesion.setBackground(new java.awt.Color(0, 102, 204));
        Btn_Iniciar_Sesion.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        Btn_Iniciar_Sesion.setForeground(new java.awt.Color(255, 255, 255));
        Btn_Iniciar_Sesion.setText("Iniciar Sesion");
        Btn_Iniciar_Sesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Iniciar_SesionActionPerformed(evt);
            }
        });

        PswrdField_Digite_su_contraseña.setForeground(new java.awt.Color(102, 102, 102));
        PswrdField_Digite_su_contraseña.setText("Digite su contraseña");
        PswrdField_Digite_su_contraseña.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                PswrdField_Digite_su_contraseñaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                PswrdField_Digite_su_contraseñaFocusLost(evt);
            }
        });

        javax.swing.GroupLayout redPanel3rdLayout = new javax.swing.GroupLayout(redPanel3rd);
        redPanel3rd.setLayout(redPanel3rdLayout);
        redPanel3rdLayout.setHorizontalGroup(
            redPanel3rdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, redPanel3rdLayout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addGroup(redPanel3rdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Text_Contrasena)
                    .addComponent(Text_Carnet)
                    .addGroup(redPanel3rdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, redPanel3rdLayout.createSequentialGroup()
                            .addComponent(Text_Ingresa_tu_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(36, 36, 36))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, redPanel3rdLayout.createSequentialGroup()
                            .addComponent(Btn_Iniciar_Sesion, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(86, 86, 86)))
                    .addGroup(redPanel3rdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(PswrdField_Digite_su_contraseña, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtField_Carnet, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))))
        );
        redPanel3rdLayout.setVerticalGroup(
            redPanel3rdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(redPanel3rdLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(Text_Ingresa_tu_usuario)
                .addGap(27, 27, 27)
                .addComponent(Text_Carnet)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtField_Carnet, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Text_Contrasena)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PswrdField_Digite_su_contraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(Btn_Iniciar_Sesion, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        Label_Image.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Label_Image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Fia_Support_Logo.png"))); // NOI18N
        Label_Image.setMaximumSize(new java.awt.Dimension(210, 210));
        Label_Image.setMinimumSize(new java.awt.Dimension(190, 190));
        Label_Image.setPreferredSize(new java.awt.Dimension(220, 220));
        Label_Image.setRequestFocusEnabled(false);
        Label_Image.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(grayPanel1st, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(redPanel3rd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Label_Image, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(grayPanel1st, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(Label_Image, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(redPanel3rd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        TextLink_Problemas.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TextLink_Problemas.setForeground(new java.awt.Color(255, 0, 0));
        TextLink_Problemas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TextLink_Problemas.setText("<html><u>¿Problemas con el carnet o la contraseña?</u></html>");
        TextLink_Problemas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TextLink_Problemas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblProblemasMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(22, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(TextLink_Problemas)
                        .addGap(66, 66, 66))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TextLink_Problemas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtField_CarnetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtField_CarnetActionPerformed
        // TODO add your handling code here:
        Btn_Iniciar_SesionActionPerformed(null); // reutiliza la misma lógica
    }//GEN-LAST:event_txtField_CarnetActionPerformed

    private void Btn_Iniciar_SesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Iniciar_SesionActionPerformed
        // TODO add your handling code here:
        String carnet = txtField_Carnet.getText().trim();
String pass   = new String(PswrdField_Digite_su_contraseña.getPassword());

// Trata el placeholder como vacío
boolean carnetVacio = carnet.isEmpty() || PLACEHOLDER_CARNET.equals(carnet);
boolean passVacia   = pass.isEmpty()   || PLACEHOLDER_PASS.equals(pass);

// Validaciones mínimas
if (carnetVacio) {
    txtField_Carnet.requestFocus();
    txtField_Carnet.setToolTipText("Ingrese su carnet.");
    javax.swing.JOptionPane.showMessageDialog(this, "Ingrese su carnet.", "Validación", javax.swing.JOptionPane.WARNING_MESSAGE);
    return;
}
if (passVacia) {
    PswrdField_Digite_su_contraseña.requestFocus();
    PswrdField_Digite_su_contraseña.setToolTipText("Ingrese su contraseña.");
    javax.swing.JOptionPane.showMessageDialog(this, "Ingrese su contraseña.", "Validación", javax.swing.JOptionPane.WARNING_MESSAGE);
    return;
}

// (Opcional) Valida formato del carnet (solo letras/números)
if (!carnet.matches("^[A-Za-z0-9]+$")) {
    txtField_Carnet.requestFocus();
    javax.swing.JOptionPane.showMessageDialog(this, "Carnet inválido (solo letras o números).", "Validación", javax.swing.JOptionPane.WARNING_MESSAGE);
    return;
}

// DEMO de autenticación (cámbialo por tu lógica real)
boolean ok = "NZ03021".equalsIgnoreCase(carnet) && "123456".equals(pass);

if (ok) {
    javax.swing.JOptionPane.showMessageDialog(this, "Login OK (demo).", "FIA Support", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    // TODO: abrir tu ventana principal y dispose() del login
} else {
    javax.swing.JOptionPane.showMessageDialog(this, "Usuario o contraseña inválidos.", "FIA Support", javax.swing.JOptionPane.ERROR_MESSAGE);
}

    }//GEN-LAST:event_Btn_Iniciar_SesionActionPerformed

    private void lblProblemasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProblemasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblProblemasMouseClicked

    private void txtField_CarnetFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtField_CarnetFocusGained
        // TODO add your handling code here:
        String t = txtField_Carnet.getText();
    if (PLACEHOLDER_CARNET.equals(t)) {
        txtField_Carnet.setText("");
        txtField_Carnet.setForeground(COLOR_TEXTO);
    }
    }//GEN-LAST:event_txtField_CarnetFocusGained

    private void txtField_CarnetFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtField_CarnetFocusLost
        // TODO add your handling code here:
        String t = txtField_Carnet.getText().trim();
    if (t.isEmpty()) {
        txtField_Carnet.setText(PLACEHOLDER_CARNET);
        txtField_Carnet.setForeground(COLOR_PLACEHOLDER);
    }
    }//GEN-LAST:event_txtField_CarnetFocusLost

    private void PswrdField_Digite_su_contraseñaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PswrdField_Digite_su_contraseñaFocusGained
        // TODO add your handling code here:
         String t = new String(PswrdField_Digite_su_contraseña.getPassword());
    if (PLACEHOLDER_PASS.equals(t)) {
        PswrdField_Digite_su_contraseña.setText("");
        PswrdField_Digite_su_contraseña.setForeground(COLOR_TEXTO);
        PswrdField_Digite_su_contraseña.setEchoChar(echoDefault == 0 ? '\u2022' : echoDefault);
    }
    }//GEN-LAST:event_PswrdField_Digite_su_contraseñaFocusGained

    private void PswrdField_Digite_su_contraseñaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PswrdField_Digite_su_contraseñaFocusLost
        // TODO add your handling code here:
        String t = new String(PswrdField_Digite_su_contraseña.getPassword()).trim();
    if (t.isEmpty()) {
        PswrdField_Digite_su_contraseña.setText(PLACEHOLDER_PASS);
        PswrdField_Digite_su_contraseña.setForeground(COLOR_PLACEHOLDER);
        PswrdField_Digite_su_contraseña.setEchoChar((char)0); // sin bullets para placeholder
    }
    }//GEN-LAST:event_PswrdField_Digite_su_contraseñaFocusLost

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

javax.swing.SwingUtilities.invokeLater(() -> {
        // --- FIT: escalar manteniendo proporción DENTRO del tamaño que Matisse le dio al Label ---
        javax.swing.ImageIcon raw = new javax.swing.ImageIcon(
            getClass().getResource("/img/Fia_Support_Logo.png")
        );
        int boxW = Label_Image.getWidth();   
        int boxH = Label_Image.getHeight();  
        int imgW = raw.getIconWidth();       
        int imgH = raw.getIconHeight();      

        // factor de escala para que quepa COMPLETA sin deformar
        double scale = Math.min(boxW / (double) imgW, boxH / (double) imgH);
        int drawW = (int)Math.round(imgW * scale);
        int drawH = (int)Math.round(imgH * scale);

        java.awt.Image scaled = raw.getImage().getScaledInstance(drawW, drawH, java.awt.Image.SCALE_SMOOTH);
        Label_Image.setIcon(new javax.swing.ImageIcon(scaled));

        // centrar con “márgenes” (letterboxing) dentro del label
        int padX = Math.max(0, (boxW - drawW) / 2);
        int padY = Math.max(0, (boxH - drawH) / 2);
        Label_Image.setBorder(javax.swing.BorderFactory.createEmptyBorder(padY, padX, padY, padX));

        // ---- Placeholders (si los usas) ----
        txtField_Carnet.setForeground(COLOR_PLACEHOLDER);

        char ec = PswrdField_Digite_su_contraseña.getEchoChar();
        if (ec != 0) echoDefault = ec;
        PswrdField_Digite_su_contraseña.setText(PLACEHOLDER_PASS);
        PswrdField_Digite_su_contraseña.setForeground(COLOR_PLACEHOLDER);
        PswrdField_Digite_su_contraseña.setEchoChar((char)0); // sin bullets para placeholder

        // Centrar ventana y dejar foco en panel neutro
        setLocationRelativeTo(null);
        jPanel3.requestFocusInWindow();
    });
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new LoginFrame2().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Iniciar_Sesion;
    private javax.swing.JLabel Label_Image;
    private javax.swing.JPasswordField PswrdField_Digite_su_contraseña;
    private javax.swing.JLabel TextLink_Problemas;
    private javax.swing.JLabel Text_Bienvenido;
    private javax.swing.JLabel Text_Carnet;
    private javax.swing.JLabel Text_Contrasena;
    private javax.swing.JLabel Text_FIA_Support;
    private javax.swing.JLabel Text_Ingresa_tu_usuario;
    private javax.swing.JPanel grayPanel1st;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel redPanel2nd;
    private javax.swing.JPanel redPanel3rd;
    private javax.swing.JTextField txtField_Carnet;
    // End of variables declaration//GEN-END:variables
}
