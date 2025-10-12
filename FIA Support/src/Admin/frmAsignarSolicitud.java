package Admin;

public class frmAsignarSolicitud extends javax.swing.JFrame {

    public frmAsignarSolicitud() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        jToolBar1 = new javax.swing.JToolBar();
        lblFiaSupport = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtSolicitudesSinAsignacion = new javax.swing.JTextField();
        lblTecnicoDisponible = new javax.swing.JLabel();
        txtTecnicoDisponible = new javax.swing.JTextField();
        btnAsignar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        desktopPane.setBackground(new java.awt.Color(204, 204, 204));
        desktopPane.setForeground(new java.awt.Color(204, 204, 204));

        jToolBar1.setBackground(new java.awt.Color(153, 153, 153));
        jToolBar1.setRollover(true);

        lblFiaSupport.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        lblFiaSupport.setForeground(new java.awt.Color(0, 0, 0));
        lblFiaSupport.setText("FIA Support");
        jToolBar1.add(lblFiaSupport);

        desktopPane.add(jToolBar1);
        jToolBar1.setBounds(0, 0, 420, 110);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Fia_Support_Logo-convertido-a-100x100 (2).png"))); // NOI18N
        desktopPane.add(jLabel1);
        jLabel1.setBounds(440, 0, 100, 110);

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Solicitudes:");
        desktopPane.add(jLabel2);
        jLabel2.setBounds(30, 135, 60, 20);

        jTable1.setBackground(new java.awt.Color(204, 204, 204));
        jTable1.setForeground(new java.awt.Color(0, 0, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Solicitud", "Usuario", "Tecnico asignado"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        desktopPane.add(jScrollPane1);
        jScrollPane1.setBounds(23, 170, 500, 70);

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Solicitud sin tecnico asignado:");
        desktopPane.add(jLabel3);
        jLabel3.setBounds(30, 270, 160, 16);

        txtSolicitudesSinAsignacion.setBackground(new java.awt.Color(204, 204, 204));
        txtSolicitudesSinAsignacion.setForeground(new java.awt.Color(0, 0, 0));
        txtSolicitudesSinAsignacion.setEnabled(false);
        desktopPane.add(txtSolicitudesSinAsignacion);
        txtSolicitudesSinAsignacion.setBounds(30, 300, 310, 19);

        lblTecnicoDisponible.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblTecnicoDisponible.setForeground(new java.awt.Color(0, 0, 0));
        lblTecnicoDisponible.setText("Tecnico disponible:");
        desktopPane.add(lblTecnicoDisponible);
        lblTecnicoDisponible.setBounds(30, 350, 160, 16);

        txtTecnicoDisponible.setBackground(new java.awt.Color(204, 204, 204));
        txtTecnicoDisponible.setForeground(new java.awt.Color(0, 0, 0));
        txtTecnicoDisponible.setEnabled(false);
        desktopPane.add(txtTecnicoDisponible);
        txtTecnicoDisponible.setBounds(30, 390, 310, 19);

        btnAsignar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Asignar.png"))); // NOI18N
        btnAsignar.setText("Asignar solicitud");
        desktopPane.add(btnAsignar);
        btnAsignar.setBounds(310, 440, 170, 42);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/volver.png"))); // NOI18N
        jButton1.setText("Atras");
        desktopPane.add(jButton1);
        jButton1.setBounds(30, 440, 110, 42);

        jButton2.setText("jButton2");
        desktopPane.add(jButton2);
        jButton2.setBounds(380, 340, 76, 25);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
  /* public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(frmAsignarSolicitud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmAsignarSolicitud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmAsignarSolicitud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmAsignarSolicitud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        //java.awt.EventQueue.invokeLater(() -> {
          //  new frmAsignarSolicitud().setVisible(true);
        //});
   // }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsignar;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblFiaSupport;
    private javax.swing.JLabel lblTecnicoDisponible;
    private javax.swing.JTextField txtSolicitudesSinAsignacion;
    private javax.swing.JTextField txtTecnicoDisponible;
    // End of variables declaration//GEN-END:variables

}
