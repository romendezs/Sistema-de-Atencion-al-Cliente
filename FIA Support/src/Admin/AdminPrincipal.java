
package Admin;

/**
 *
 * @author Alex
 */
public class AdminPrincipal extends javax.swing.JFrame
{

    public AdminPrincipal() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        jToolBar1 = new javax.swing.JToolBar();
        lblFiaSupport = new javax.swing.JLabel();
        btnReportesYEstadisticas = new javax.swing.JButton();
        btnAsignarSolicitud = new javax.swing.JButton();
        btnGestionarTicket = new javax.swing.JButton();
        btnGestionarUsuario = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        lblLogo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        desktopPane.setBackground(new java.awt.Color(204, 204, 204));
        desktopPane.setForeground(new java.awt.Color(0, 0, 0));

        jToolBar1.setBackground(new java.awt.Color(153, 153, 153));
        jToolBar1.setRollover(true);

        lblFiaSupport.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        lblFiaSupport.setForeground(new java.awt.Color(0, 0, 0));
        lblFiaSupport.setText("FIA Support");
        jToolBar1.add(lblFiaSupport);

        desktopPane.add(jToolBar1);
        jToolBar1.setBounds(0, 0, 270, 110);

        btnReportesYEstadisticas.setBackground(new java.awt.Color(102, 102, 102));
        btnReportesYEstadisticas.setText("Reportes y estadisticas ");
        desktopPane.add(btnReportesYEstadisticas);
        btnReportesYEstadisticas.setBounds(90, 140, 160, 25);

        btnAsignarSolicitud.setBackground(new java.awt.Color(102, 102, 102));
        btnAsignarSolicitud.setText("Asignar solicitud");
        desktopPane.add(btnAsignarSolicitud);
        btnAsignarSolicitud.setBounds(90, 210, 160, 25);

        btnGestionarTicket.setBackground(new java.awt.Color(102, 102, 102));
        btnGestionarTicket.setText("Gestionar ticket");
        desktopPane.add(btnGestionarTicket);
        btnGestionarTicket.setBounds(90, 280, 160, 25);

        btnGestionarUsuario.setBackground(new java.awt.Color(102, 102, 102));
        btnGestionarUsuario.setText("Gestionar usuario");
        desktopPane.add(btnGestionarUsuario);
        btnGestionarUsuario.setBounds(90, 350, 160, 25);

        jButton1.setBackground(new java.awt.Color(102, 102, 102));
        jButton1.setText("Cerrar sesión");
        desktopPane.add(jButton1);
        jButton1.setBounds(90, 420, 160, 25);

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Fia_Support_Logo-convertido-a-100x100 (2).png"))); // NOI18N
        lblLogo.setText("jLabel1");
        desktopPane.add(lblLogo);
        lblLogo.setBounds(270, 0, 100, 110);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    public static void main(String args[])
    {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
      
        java.awt.EventQueue.invokeLater(() -> {
            new AdminPrincipal().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsignarSolicitud;
    private javax.swing.JButton btnGestionarTicket;
    private javax.swing.JButton btnGestionarUsuario;
    private javax.swing.JButton btnReportesYEstadisticas;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblFiaSupport;
    private javax.swing.JLabel lblLogo;
    // End of variables declaration//GEN-END:variables

}
