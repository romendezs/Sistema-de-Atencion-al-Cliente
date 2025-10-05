package Admin;

public class frmGestionarTicket extends javax.swing.JFrame {

    
    public frmGestionarTicket() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        jToolBar1 = new javax.swing.JToolBar();
        lblFiaSupport = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        btnBuscarTicket = new javax.swing.JButton();
        btnAsignarTicket = new javax.swing.JButton();
        btnVolverInicio = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        desktopPane.setBackground(new java.awt.Color(204, 204, 204));

        jToolBar1.setBackground(new java.awt.Color(153, 153, 153));
        jToolBar1.setRollover(true);

        lblFiaSupport.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        lblFiaSupport.setForeground(new java.awt.Color(0, 0, 0));
        lblFiaSupport.setText("FIA Support");
        jToolBar1.add(lblFiaSupport);

        desktopPane.add(jToolBar1);
        jToolBar1.setBounds(0, 0, 430, 100);

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Fia_Support_Logo-convertido-a-100x100 (2).png"))); // NOI18N
        lblLogo.setText("jLabel2");
        desktopPane.add(lblLogo);
        lblLogo.setBounds(430, 0, 110, 100);

        btnBuscarTicket.setBackground(new java.awt.Color(102, 102, 102));
        btnBuscarTicket.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarTicket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar ticket.png"))); // NOI18N
        btnBuscarTicket.setText("Buscar ticket");
        desktopPane.add(btnBuscarTicket);
        btnBuscarTicket.setBounds(200, 160, 150, 42);

        btnAsignarTicket.setBackground(new java.awt.Color(102, 102, 102));
        btnAsignarTicket.setForeground(new java.awt.Color(255, 255, 255));
        btnAsignarTicket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Asignar.png"))); // NOI18N
        btnAsignarTicket.setText("Asignar ticket");
        desktopPane.add(btnAsignarTicket);
        btnAsignarTicket.setBounds(200, 260, 150, 42);

        btnVolverInicio.setBackground(new java.awt.Color(102, 102, 102));
        btnVolverInicio.setForeground(new java.awt.Color(255, 255, 255));
        btnVolverInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/volver inicio.png"))); // NOI18N
        btnVolverInicio.setText("Volver a inicio");
        desktopPane.add(btnVolverInicio);
        btnVolverInicio.setBounds(200, 360, 150, 42);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
 /*   public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
      /* try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmGestionarTicket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmGestionarTicket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmGestionarTicket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmGestionarTicket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
     /*   java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmGestionarTicket().setVisible(true);
            }
        });*/
    //}*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsignarTicket;
    private javax.swing.JButton btnBuscarTicket;
    private javax.swing.JButton btnVolverInicio;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblFiaSupport;
    private javax.swing.JLabel lblLogo;
    // End of variables declaration//GEN-END:variables

}
