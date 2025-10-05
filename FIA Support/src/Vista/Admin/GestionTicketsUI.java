package Vista.Admin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class GestionTicketsUI extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private int hoveredRow = -1;

    public GestionTicketsUI() {
        setTitle("FIA Support - Gestionar Tickets");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🔴 Top navigation bar
        JPanel barraSuperior = new JPanel(new BorderLayout());
        barraSuperior.setBackground(new Color(153, 0, 0));
        barraSuperior.setPreferredSize(new Dimension(1000, 60));

        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        menuPanel.setOpaque(false);

        // 🚀 Create navigation buttons
        JButton btnUsuarios = crearBotonMenu("Gestionar Usuarios");
        JButton btnReportes = crearBotonMenu("Ver Reportes y Estadísticas");
        JButton btnCerrar = crearBotonMenu("Cerrar Sesión");

        // 🔗 Navigation actions
        btnUsuarios.addActionListener(e -> {
            new GestionUsuariosUI().setVisible(true); // open other frame
            dispose(); // close current
        });

        btnReportes.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Próximamente: módulo de reportes");
        });

        btnCerrar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Deseas cerrar sesión?", 
                    "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose(); // close current window
                // Optionally: redirect to login JFrame if you have one
            }
        });

        menuPanel.add(btnUsuarios);
        menuPanel.add(btnReportes);
        barraSuperior.add(menuPanel, BorderLayout.WEST);
        barraSuperior.add(btnCerrar, BorderLayout.EAST);
        add(barraSuperior, BorderLayout.NORTH);

        // 🧱 Main content
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));

        JLabel lblTitulo = new JLabel("Gestionar Tickets");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(153, 0, 0));

        JLabel lblListado = new JLabel("Listado");
        lblListado.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblListado.setForeground(new Color(153, 0, 0));

        JTextField txtBuscar = new JTextField("Buscar por carnet de solicitante");
        txtBuscar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        mainPanel.add(lblTitulo);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(lblListado);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(txtBuscar);
        mainPanel.add(Box.createVerticalStrut(20));

        // 🗂 Tabla con datos
        String[] columnas = {"Solicitante", "Título", "Asignación", "Estado", "Acciones"};
        Object[][] datos = {
            {"Héctor Alejandro", "Internet Edificio C", "Sin asignar", "Sin Asignar: 1 día"},
            {"José Roberto", "Televisor B41", "Sin asignar", "Sin Asignar: 2 horas"},
            {"Alex Ezequiel", "Expediente en línea", "Santiago Hernández", "En Proceso: 3 días"},
            {"Christopher Enrique", "Cuentas de Google", "Juan Pérez", "En Proceso: 1 día"},
            {"Arnoldo Inocencio", "Aires B21", "Carlos Rodríguez", "En Proceso: 2 horas"},
        };

        modelo = new DefaultTableModel(datos, columnas);
        tabla = new JTable(modelo) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // solo la columna "Acciones" puede tener botones
            }
        };

        tabla.setRowHeight(40);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(new Color(255, 204, 204));

        // 🖌 Personalizar celdas de "Acciones"
        tabla.getColumn("Acciones").setCellRenderer(new AccionRenderer());
        tabla.getColumn("Acciones").setCellEditor(new AccionEditor());

        JScrollPane scroll = new JScrollPane(tabla);
        mainPanel.add(scroll);

        add(mainPanel, BorderLayout.CENTER);

        // 🧭 Detectar fila bajo el mouse
        tabla.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = tabla.rowAtPoint(e.getPoint());
                if (row != hoveredRow) {
                    hoveredRow = row;
                    tabla.repaint();
                }
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoveredRow = -1;
                tabla.repaint();
            }
        });
    }

    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(153, 0, 0));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // 🔧 Renderizador para mostrar los íconos de acción
    private class AccionRenderer extends DefaultTableCellRenderer {
        private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        private final JButton btnEditar = new JButton("✏");
        private final JButton btnEliminar = new JButton("🗑");

        public AccionRenderer() {
            panel.setOpaque(true);
            btnEditar.setFocusable(false);
            btnEliminar.setFocusable(false);
            btnEditar.setBorderPainted(false);
            btnEliminar.setBorderPainted(false);
            btnEditar.setContentAreaFilled(false);
            btnEliminar.setContentAreaFilled(false);
            panel.add(btnEliminar);
            panel.add(btnEditar);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                      boolean isSelected, boolean hasFocus,
                                                      int row, int column) {
            panel.setBackground(isSelected ? new Color(255, 220, 220) : Color.WHITE);
            btnEditar.setVisible(row == hoveredRow);
            btnEliminar.setVisible(row == hoveredRow);
            return panel;
        }
    }

    // 🧩 Editor que responde a clics de los botones
    private class AccionEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        private final JButton btnEditar = new JButton("✏");
        private final JButton btnEliminar = new JButton("🗑");

        public AccionEditor() {
            panel.add(btnEliminar);
            panel.add(btnEditar);

            for (JButton b : new JButton[]{btnEditar, btnEliminar}) {
                b.setFocusable(false);
                b.setBorderPainted(false);
                b.setContentAreaFilled(false);
                b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            btnEditar.addActionListener(e -> {
                JOptionPane.showMessageDialog(null, "¡Estoy funcionando! (Editar)");
                fireEditingStopped();
            });

            btnEliminar.addActionListener(e -> {
                JOptionPane.showMessageDialog(null, "¡Estoy funcionando! (Eliminar)");
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }

    // 🚀 Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GestionTicketsUI().setVisible(true);
        });
    }
}
