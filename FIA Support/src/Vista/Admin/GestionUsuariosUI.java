/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * @author Méndez
 */
public class GestionUsuariosUI extends javax.swing.JFrame {

    public GestionUsuariosUI() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        // ======= COLORES =======
        Color rojoFIA = new Color(180, 30, 25);
        Color grisFondo = new Color(245, 245, 245);

        // ======= PANEL PRINCIPAL =======
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(grisFondo);
        mainPanel.setLayout(new BorderLayout());

        // ======= BARRA SUPERIOR =======
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(rojoFIA);
        topBar.setPreferredSize(new Dimension(900, 50));

        // Izquierda
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        leftPanel.setBackground(rojoFIA);
        JButton btnGestionar = new JButton("Gestionar Tickets");
        JButton btnReportes = new JButton("Ver Reportes y Estadísticas");
        styleTopButton(btnGestionar, rojoFIA);
        styleTopButton(btnReportes, rojoFIA);
        leftPanel.add(btnGestionar);
        leftPanel.add(btnReportes);

        // Derecha
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rightPanel.setBackground(rojoFIA);
        JButton btnCerrar = new JButton("Cerrar Sesión");
        styleTopButton(btnCerrar, rojoFIA);
        rightPanel.add(btnCerrar);

        topBar.add(leftPanel, BorderLayout.WEST);
        topBar.add(rightPanel, BorderLayout.EAST);

        // ======= PANEL CONTENIDO =======
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(grisFondo);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));

        JLabel lblGestion = new JLabel("Gestionar Usuarios");
        lblGestion.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblGestion.setForeground(rojoFIA);
        lblGestion.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(lblGestion);
        contentPanel.add(Box.createVerticalStrut(20));

        // ======= NUEVO USUARIO =======
        JPanel nuevoPanel = new JPanel(new GridBagLayout());
        nuevoPanel.setBackground(Color.WHITE);
        nuevoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(rojoFIA, 2),
                "+ Nuevo Usuario",
                0, 0,
                new Font("SansSerif", Font.BOLD, 16),
                rojoFIA
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblCarnet = new JLabel("Carnet:");
        JTextField txtCarnet = new JTextField(15);
        JLabel lblEstudiante = new JLabel("Estudiante:");
        JRadioButton rbSi = new JRadioButton("Sí");
        JRadioButton rbNo = new JRadioButton("No");
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbSi);
        grupo.add(rbNo);
        JPanel panelRadio = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelRadio.setBackground(Color.WHITE);
        panelRadio.add(rbSi);
        panelRadio.add(rbNo);

        JLabel lblNombres = new JLabel("Nombres:");
        JTextField txtNombres = new JTextField(15);
        JLabel lblFacultad = new JLabel("Facultad:");
        JComboBox<String> cbFacultad = new JComboBox<>(new String[]{"Seleccione...", "FIA", "FMOcc", "FMLN"});

        JLabel lblApellidos = new JLabel("Apellidos:");
        JTextField txtApellidos = new JTextField(15);
        JLabel lblCarrera = new JLabel("Carrera:");
        JComboBox<String> cbCarrera = new JComboBox<>(new String[]{"Seleccione...", "Ing. Sistemas", "Ing. Civil"});

        JButton btnCrear = new JButton("Crear");
        btnCrear.setBackground(rojoFIA);
        btnCrear.setForeground(Color.WHITE);

        // Fila 1
        gbc.gridx = 0; gbc.gridy = 0; nuevoPanel.add(lblCarnet, gbc);
        gbc.gridx = 1; nuevoPanel.add(txtCarnet, gbc);
        gbc.gridx = 2; nuevoPanel.add(lblEstudiante, gbc);
        gbc.gridx = 3; nuevoPanel.add(panelRadio, gbc);

        // Fila 2
        gbc.gridx = 0; gbc.gridy = 1; nuevoPanel.add(lblNombres, gbc);
        gbc.gridx = 1; nuevoPanel.add(txtNombres, gbc);
        gbc.gridx = 2; nuevoPanel.add(lblFacultad, gbc);
        gbc.gridx = 3; nuevoPanel.add(cbFacultad, gbc);

        // Fila 3
        gbc.gridx = 0; gbc.gridy = 2; nuevoPanel.add(lblApellidos, gbc);
        gbc.gridx = 1; nuevoPanel.add(txtApellidos, gbc);
        gbc.gridx = 2; nuevoPanel.add(lblCarrera, gbc);
        gbc.gridx = 3; nuevoPanel.add(cbCarrera, gbc);

        // Fila 4
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 2; nuevoPanel.add(btnCrear, gbc);

        contentPanel.add(nuevoPanel);
        contentPanel.add(Box.createVerticalStrut(30));

        // ======= LISTADO =======
        JLabel lblListado = new JLabel("Listado");
        lblListado.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblListado.setForeground(rojoFIA);
        lblListado.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(lblListado);
        contentPanel.add(Box.createVerticalStrut(10));

        JTextField txtBuscar = new JTextField("Buscar por carnet");
        txtBuscar.setMaximumSize(new Dimension(200, 30));
        contentPanel.add(txtBuscar);
        contentPanel.add(Box.createVerticalStrut(15));

        String[] columnas = {"Id (Carnet)", "Nombres", "Apellidos"};
        Object[][] filas = {
                {"MS24030", "José Roberto", "Méndez Sosa"},
                {"GC22090", "Alex Ezequiel", "García Córdova"},
                {"OL24002", "Christopher Enrique", "Orellana Linares"}
        };
        JTable tabla = new JTable(new DefaultTableModel(filas, columnas));
        JScrollPane scroll = new JScrollPane(tabla);
        contentPanel.add(scroll);

        // ======= AGREGAR TODO =======
        mainPanel.add(topBar, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // FRAME SETTINGS
        getContentPane().add(mainPanel);
        setSize(950, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("FIA Support");
    }// </editor-fold>

    private void styleTopButton(JButton b, Color color) {
        b.setFocusPainted(false);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new GestionUsuariosUI().setVisible(true));
    }
}
