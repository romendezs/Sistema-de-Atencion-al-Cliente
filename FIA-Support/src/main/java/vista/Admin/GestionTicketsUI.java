package Vista.Admin;

import controlador.GestionTicketsController;
import modelo.dominio.*;
import modelo.repo.*;
import modelo.servicios.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * UI: Gestionar Tickets
 */
public class GestionTicketsUI extends JFrame {

    private static final Color ROJO = new Color(180, 40, 25);
    private static final Color ROJO_CLARO = new Color(244, 196, 192);
    private static final Color GRIS_TXT = new Color(90, 90, 90);

    private final GestionTicketsController controller;

    // tabla
    private JTable table;
    private TicketTableModel model;
    private JTextField txtBuscar;
    private int hoveredRow = -1;
    private float actionsAlpha = 0f;
    private javax.swing.Timer hoverTimer;

    private static final int COL_DEL = 0;
    private static final int COL_SOL = 1;
    private static final int COL_TIT = 2;
    private static final int COL_ASIG = 3;
    private static final int COL_EST = 4;

    private static final int ICON_BTN = 20;
    private static final int ICON_GAP = 6;

    public GestionTicketsUI(GestionTicketsController controller) {
        this.controller = controller;

        setTitle("FIA Support");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(980, 680));
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        add(buildTopMenu(), BorderLayout.NORTH);
        add(buildMain(), BorderLayout.CENTER);

        hoverTimer = new javax.swing.Timer(20, e -> {
            float target = (hoveredRow >= 0) ? 1f : 0f;
            float step = 0.12f;
            if (Math.abs(actionsAlpha - target) < step) {
                actionsAlpha = target;
                table.repaint();
                ((javax.swing.Timer) e.getSource()).stop();
            } else {
                actionsAlpha += (target > actionsAlpha ? step : -step);
                table.repaint();
            }
        });
    }

    private JComponent buildTopMenu() {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(ROJO);

        // brand
        JPanel brand = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        brand.setOpaque(false);
        JLabel logo = new JLabel();
        logo.setPreferredSize(new Dimension(28, 28));
        ImageIcon icon = loadScaledIcon("/Vista/img/icon.png", 24);
        if (icon != null) {
            logo.setIcon(icon);
        }
        JLabel title = new JLabel("FIA Support");
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        brand.add(logo);
        brand.add(title);

        // mid
        JPanel mid = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 8));
        mid.setOpaque(false);
        JButton btnUsuarios = btnHeader("Gestionar Usuarios");
        btnUsuarios.addActionListener(e -> openUsuarios());
        JButton btnReportes = btnHeader("Ver Reportes y Estadísticas");
        mid.add(btnUsuarios);
        mid.add(btnReportes);

        // right
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 8));
        right.setOpaque(false);
        JButton btnSalir = btnHeader("Cerrar Sesión");
        btnSalir.addActionListener(e -> dispose());
        right.add(btnSalir);

        top.add(brand, BorderLayout.WEST);
        top.add(mid, BorderLayout.CENTER);
        top.add(right, BorderLayout.EAST);
        return top;
    }

    private JButton btnHeader(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(190, 60, 45));
        b.setBorder(new CompoundBorder(
                new LineBorder(new Color(230, 150, 140)),
                new EmptyBorder(6, 12, 6, 12)));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JComponent buildMain() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);
        root.setBorder(new EmptyBorder(16, 16, 48, 16));

        JLabel h1 = new JLabel("Gestionar Tickets");
        h1.setFont(h1.getFont().deriveFont(Font.BOLD, 28f));
        h1.setForeground(ROJO);
        JPanel pTitle = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 18));
        pTitle.setOpaque(false);
        pTitle.add(h1);
        root.add(pTitle, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.add(buildListado());
        root.add(new JScrollPane(center,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        return root;
    }

    private JComponent buildListado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // Buscar
        JPanel search = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        search.setOpaque(false);
        txtBuscar = new JTextField(24);
        txtBuscar.setToolTipText("Buscar por carnet de solicitante");
        txtBuscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                refiltrar();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                refiltrar();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                refiltrar();
            }
        });
        search.add(txtBuscar);

        JPanel north = new JPanel();
        north.setOpaque(false);
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        north.add(search);
        panel.add(north, BorderLayout.NORTH);

        // Tabla
        model = new TicketTableModel(controller.getTickets());
        table = new JTable(model) {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return true;
            }

            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        table.setRowHeight(36);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 4));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

        // renderers
        table.getColumnModel().getColumn(COL_DEL).setPreferredWidth(30);
        table.getColumnModel().getColumn(COL_DEL).setCellRenderer(new DeleteRenderer());
        table.getColumnModel().getColumn(COL_SOL).setCellRenderer(cell());
        table.getColumnModel().getColumn(COL_TIT).setCellRenderer(cell());
        table.getColumnModel().getColumn(COL_ASIG).setCellRenderer(new PencilRenderer());
        table.getColumnModel().getColumn(COL_EST).setCellRenderer(new StatusRenderer());

        table.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                hoveredRow = table.rowAtPoint(e.getPoint());
                updateCursor(e);
                if (!hoverTimer.isRunning()) {
                    hoverTimer.start();
                }
            }
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoveredRow = -1;
                table.setCursor(Cursor.getDefaultCursor());
                if (!hoverTimer.isRunning()) {
                    hoverTimer.start();
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e);
            }
        });

        // ---- header styling ----
        JTableHeader th = table.getTableHeader();
        th.setPreferredSize(new Dimension(th.getPreferredSize().width, 36));
        th.setReorderingAllowed(false);
        th.setResizingAllowed(false);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                l.setHorizontalAlignment(SwingConstants.LEFT);
                l.setOpaque(true);
                l.setBackground(ROJO_CLARO);      // mismo color que usabas
                l.setForeground(GRIS_TXT);
                l.setBorder(new EmptyBorder(8, 12, 8, 12));
                l.setFont(l.getFont().deriveFont(Font.BOLD));
                return l;
            }
        };
        TableColumnModel cm = table.getColumnModel();
        for (int c = 0; c < cm.getColumnCount(); c++) {
            cm.getColumn(c).setHeaderRenderer(headerRenderer);
        }

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new EmptyBorder(6, 0, 0, 0));
        panel.add(sp, BorderLayout.CENTER);

        return panel;
    }


    private DefaultTableCellRenderer cell() {
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setBackground(new Color(242, 242, 242));
        r.setBorder(new EmptyBorder(0, 12, 0, 12));
        return r;
    }

    // ---- Renderers ----
    private class DeleteRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, (table.getRowHeight() - ICON_BTN) / 2)) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    if (row == hoveredRow && actionsAlpha > 0f) {
                        g2.setComposite(AlphaComposite.SrcOver.derive(actionsAlpha));
                    } else {
                        g2.setComposite(AlphaComposite.SrcOver.derive(0f));
                    }
                    super.paintComponent(g2);
                    g2.dispose();
                }
            };
            p.setOpaque(false);
            JLabel x = iconLabel("✕");
            p.add(x);
            return p;
        }
    }

    private class PencilRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel p = new JPanel(new BorderLayout());
            JLabel text = new JLabel(String.valueOf(value));
            text.setOpaque(true);
            text.setBackground(new Color(242, 242, 242));
            text.setBorder(new EmptyBorder(0, 12, 0, 12));
            p.add(text, BorderLayout.CENTER);

            if (row == hoveredRow && actionsAlpha > 0f) {
                JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, ICON_GAP, (table.getRowHeight() - ICON_BTN) / 2)) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setComposite(AlphaComposite.SrcOver.derive(actionsAlpha));
                        super.paintComponent(g2);
                        g2.dispose();
                    }
                };
                right.setOpaque(false);
                right.add(iconLabel("✎"));
                p.add(right, BorderLayout.EAST);
            }
            return p;
        }
    }

    private class StatusRenderer extends PencilRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (c instanceof JPanel) {
                JPanel p = (JPanel) c;
                if (p.getComponent(0) instanceof JLabel) {
                    JLabel lbl = (JLabel) p.getComponent(0);
                    lbl.setHorizontalAlignment(SwingConstants.LEFT);
                    lbl.setBorder(new EmptyBorder(0, 12, 0, 12));
                    // simple color tag (optional)
                }
            }
            return c;
        }
    }

    private JLabel iconLabel(String glyph) {
        JLabel b = new JLabel(glyph, SwingConstants.CENTER);
        b.setOpaque(true);
        b.setBackground(Color.WHITE);
        b.setBorder(new LineBorder(new Color(220, 220, 220)));
        b.setPreferredSize(new Dimension(ICON_BTN, ICON_BTN));
        return b;
    }

    // ---- interactions ----
    private void updateCursor(MouseEvent e) {
        int row = table.rowAtPoint(e.getPoint());
        int col = table.columnAtPoint(e.getPoint());
        if (row < 0) {
            table.setCursor(Cursor.getDefaultCursor());
            return;
        }

        Rectangle cell = table.getCellRect(row, col, false);
        int xIn = e.getX() - cell.x;
        int yIn = e.getY() - cell.y;

        if (col == COL_DEL) {
            Rectangle rX = new Rectangle(8, (cell.height - ICON_BTN) / 2, ICON_BTN, ICON_BTN);
            table.setCursor(rX.contains(xIn, yIn) ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getDefaultCursor());
        } else if (col == COL_ASIG || col == COL_EST) {
            Rectangle rPen = new Rectangle(cell.width - ICON_BTN - 8, (cell.height - ICON_BTN) / 2, ICON_BTN, ICON_BTN);
            table.setCursor(rPen.contains(xIn, yIn) ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getDefaultCursor());
        } else {
            table.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void handleClick(MouseEvent e) {
        int row = table.rowAtPoint(e.getPoint());
        int col = table.columnAtPoint(e.getPoint());
        if (row < 0) {
            return;
        }

        int modelRow = table.convertRowIndexToModel(row);
        Ticket t = model.get(modelRow);

        Rectangle cell = table.getCellRect(row, col, false);
        int xIn = e.getX() - cell.x;
        int yIn = e.getY() - cell.y;

        if (col == COL_DEL) {
            Rectangle rX = new Rectangle(8, (cell.height - ICON_BTN) / 2, ICON_BTN, ICON_BTN);
            if (rX.contains(xIn, yIn)) {
                onEliminar(t);
            }
        } else if (col == COL_SOL || col == COL_TIT) {
            onInfo(t);
        } else if (col == COL_ASIG) {
            Rectangle rPen = new Rectangle(cell.width - ICON_BTN - 8, (cell.height - ICON_BTN) / 2, ICON_BTN, ICON_BTN);
            if (rPen.contains(xIn, yIn)) {
                onAsignar(t);
            }
        } else if (col == COL_EST) {
            Rectangle rPen = new Rectangle(cell.width - ICON_BTN - 8, (cell.height - ICON_BTN) / 2, ICON_BTN, ICON_BTN);
            if (rPen.contains(xIn, yIn)) {
                onSeguimiento(t);
            }
        }
    }

    private void refiltrar() {
        List<Ticket> lst = controller.filtrarPorSolicitante(txtBuscar.getText());
        model.setData(lst);
    }

    // ---- actions ----
    private void onEliminar(Ticket t) {
        DeleteTicketDialog dlg = new DeleteTicketDialog(this, t);
        dlg.setVisible(true);
        if (dlg.isConfirmed()) {
            controller.eliminar(t.getId());
            model.setData(controller.getTickets());
        }
    }

    private void onInfo(Ticket t) {
        TicketInfoDialog dlg = new TicketInfoDialog(this, t);
        dlg.setVisible(true);
    }

    private void onAsignar(Ticket t) {
        AssignDialog dlg = new AssignDialog(this, t, controller.getEmpleados());
        dlg.setVisible(true);
        if (dlg.getEmpleadoSeleccionado() != null) {
            controller.asignar(t.getId(), dlg.getEmpleadoSeleccionado().getId());
            model.setData(controller.getTickets());
        }
    }

    private void onSeguimiento(Ticket t) {
        StatusDialog dlg = new StatusDialog(this, t);
        dlg.setVisible(true);
        if (dlg.isUpdated()) {
            controller.actualizarEstado(t.getId(), dlg.getEstadoSeleccionado(), dlg.getComentario());
            model.setData(controller.getTickets());
        }
    }

    // ---- dialogs ----
    private static class DeleteTicketDialog extends JDialog {

        private boolean confirmed = false;

        DeleteTicketDialog(Frame owner, Ticket t) {
            super(owner, "Confirmación de Eliminar Ticket", true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            JPanel root = new JPanel(new BorderLayout());
            root.setBorder(new EmptyBorder(12, 12, 12, 12));
            setContentPane(root);

            JLabel title = new JLabel(" Confirmación de Eliminar Ticket");
            title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
            title.setIcon(UIManager.getIcon("OptionPane.warningIcon"));
            JButton bx = new JButton("✕");
            bx.setFocusPainted(false);
            bx.setContentAreaFilled(false);
            bx.addActionListener(e -> dispose());
            JPanel head = new JPanel(new BorderLayout());
            head.add(title, BorderLayout.WEST);
            head.add(bx, BorderLayout.EAST);
            root.add(head, BorderLayout.NORTH);

            String ms = "<html><div style='text-align:center;color:#C00;'>Estás a punto de eliminar todos los datos que pertenecen<br>"
                    + "al ticket <b>“" + t.getTitulo() + "”</b> de <b>" + t.getSolicitante() + "</b>.<br>"
                    + "Confirma la acción escribiendo <b>Eliminar</b>.</div></html>";
            JLabel msg = new JLabel(ms, SwingConstants.CENTER);
            JTextField input = new JTextField();
            JButton btn = new JButton("Eliminar");
            btn.setEnabled(false);
            input.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                void check() {
                    btn.setEnabled(input.getText().trim().equalsIgnoreCase("eliminar"));
                }

                public void insertUpdate(javax.swing.event.DocumentEvent e) {
                    check();
                }

                public void removeUpdate(javax.swing.event.DocumentEvent e) {
                    check();
                }

                public void changedUpdate(javax.swing.event.DocumentEvent e) {
                    check();
                }
            });
            btn.addActionListener(e -> {
                confirmed = true;
                dispose();
            });

            JPanel center = new JPanel();
            center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
            center.add(Box.createVerticalStrut(8));
            center.add(msg);
            center.add(Box.createVerticalStrut(12));
            center.add(input);
            center.add(Box.createVerticalStrut(12));
            JPanel pb = new JPanel();
            pb.add(btn);
            center.add(pb);
            root.add(center, BorderLayout.CENTER);

            pack();
            setMinimumSize(new Dimension(520, 240));
            setLocationRelativeTo(owner);
        }

        boolean isConfirmed() {
            return confirmed;
        }
    }

    private static class TicketInfoDialog extends JDialog {

        TicketInfoDialog(Frame owner, Ticket t) {
            super(owner, "Información de Ticket", true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            JPanel root = new JPanel(new BorderLayout());
            root.setBorder(new EmptyBorder(12, 12, 12, 12));
            setContentPane(root);

            JLabel title = new JLabel(" Información de Ticket");
            title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
            title.setIcon(UIManager.getIcon("OptionPane.informationIcon"));
            JButton bx = new JButton("✕");
            bx.setFocusPainted(false);
            bx.setContentAreaFilled(false);
            bx.addActionListener(e -> dispose());
            JPanel head = new JPanel(new BorderLayout());
            head.add(title, BorderLayout.WEST);
            head.add(bx, BorderLayout.EAST);
            root.add(head, BorderLayout.NORTH);

            String html = "<html><div style='width:460px'>"
                    + "<b>Solicitante:</b> " + t.getSolicitante() + "<br><br>"
                    + "<b>Título:</b> " + t.getTitulo() + "<br><br>"
                    + "<b>Descripción:</b> " + t.getDescripcion()
                    + "</div></html>";
            JLabel content = new JLabel(html);

            JButton ok = new JButton("Ok");
            ok.addActionListener(e -> dispose());
            JPanel south = new JPanel();
            south.add(ok);

            root.add(content, BorderLayout.CENTER);
            root.add(south, BorderLayout.SOUTH);
            pack();
            setMinimumSize(new Dimension(560, 280));
            setLocationRelativeTo(owner);
        }
    }

    private static class AssignDialog extends JDialog {

        private Empleado sel;

        AssignDialog(Frame owner, Ticket t, List<Empleado> empleados) {
            super(owner, "Asignar un Ticket", true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            JPanel root = new JPanel(new BorderLayout());
            root.setBorder(new EmptyBorder(12, 12, 12, 12));
            setContentPane(root);

            JLabel title = new JLabel(" Asignar un Ticket");
            title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
            title.setIcon(UIManager.getIcon("OptionPane.informationIcon"));
            JButton bx = new JButton("✕");
            bx.setFocusPainted(false);
            bx.setContentAreaFilled(false);
            bx.addActionListener(e -> dispose());
            JPanel head = new JPanel(new BorderLayout());
            head.add(title, BorderLayout.WEST);
            head.add(bx, BorderLayout.EAST);
            root.add(head, BorderLayout.NORTH);

            String html = "<html><div style='width:520px'>"
                    + "<b>Título:</b> " + t.getTitulo() + "<br><br>"
                    + "<b>Descripción:</b> " + t.getDescripcion() + "</div></html>";
            JLabel info = new JLabel(html);

            JComboBox<Empleado> cb = new JComboBox<>(empleados.toArray(new Empleado[0]));
            cb.setSelectedItem(null);

            JPanel center = new JPanel();
            center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
            center.add(info);
            center.add(Box.createVerticalStrut(16));
            JPanel row = new JPanel(new BorderLayout());
            row.add(new JLabel("Asignar a: "), BorderLayout.WEST);
            row.add(cb, BorderLayout.CENTER);
            center.add(row);
            root.add(center, BorderLayout.CENTER);

            JButton btn = new JButton("Asignar");
            btn.setBackground(ROJO);
            btn.setForeground(Color.WHITE);
            btn.addActionListener(e -> {
                if (cb.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(this, "Seleccione un empleado.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                sel = (Empleado) cb.getSelectedItem();
                dispose();
            });
            JPanel south = new JPanel();
            south.add(btn);
            root.add(south, BorderLayout.SOUTH);

            pack();
            setMinimumSize(new Dimension(640, 360));
            setLocationRelativeTo(owner);
        }

        Empleado getEmpleadoSeleccionado() {
            return sel;
        }
    }

    private static class StatusDialog extends JDialog {

        private boolean updated = false;
        private Estado nextEstado;
        private String comentario;

        StatusDialog(Frame owner, Ticket t) {
            super(owner, "Seguimiento Ticket", true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            JPanel root = new JPanel(new BorderLayout());
            root.setBorder(new EmptyBorder(12, 12, 12, 12));
            setContentPane(root);

            JLabel title = new JLabel(" Seguimiento Ticket");
            title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
            title.setIcon(UIManager.getIcon("OptionPane.informationIcon"));
            JButton bx = new JButton("✕");
            bx.setFocusPainted(false);
            bx.setContentAreaFilled(false);
            bx.addActionListener(e -> dispose());
            JPanel head = new JPanel(new BorderLayout());
            head.add(title, BorderLayout.WEST);
            head.add(bx, BorderLayout.EAST);
            root.add(head, BorderLayout.NORTH);

            // top info
            String top = "<html><b>Título:</b> " + t.getTitulo() + "<br><br>"
                    + "<b>Descripción:</b> " + t.getDescripcion() + "</html>";
            JLabel info = new JLabel(top);
            info.setBorder(new EmptyBorder(6, 0, 12, 0));

            // table historial
            String[] cols = {"Actualización", "Seguimiento", "Fecha"};
            Object[][] rows = new Object[t.getHistorial().size() + 1][3];
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d/M/yyyy");
            int i = 0;
            for (Seguimiento s : t.getHistorial()) {
                rows[i][0] = s.getEstado().getRotulo();
                rows[i][1] = s.getComentario();
                rows[i][2] = s.getFecha().format(fmt);
                i++;
            }
            rows[i][0] = "▼ Seleccionar nuevo Estado";
            rows[i][1] = "Añadir Seguimiento";
            rows[i][2] = ""; // auto-fecha

            JTable grid = new JTable(new DefaultTableModel(rows, cols) {
                @Override
                public boolean isCellEditable(int r, int c) {
                    return r == getRowCount() - 1 && (c == 0 || c == 1);
                }
            });
            grid.setRowHeight(32);
            grid.setShowGrid(false);
            grid.setIntercellSpacing(new Dimension(0, 4));

            // editors for last row
            JComboBox<Estado> cbEstado = new JComboBox<>(Estado.values());
            grid.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(cbEstado));
            JTextField txt = new JTextField();
            grid.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(txt));

            JPanel center = new JPanel(new BorderLayout());
            center.add(info, BorderLayout.NORTH);
            center.add(grid, BorderLayout.CENTER);
            root.add(center, BorderLayout.CENTER);

            JButton btn = new JButton("Actualizar");
            btn.setBackground(ROJO);
            btn.setForeground(Color.WHITE);
            btn.addActionListener(e -> {
                Object vEstado = grid.getValueAt(grid.getRowCount() - 1, 0);
                Object vCom = grid.getValueAt(grid.getRowCount() - 1, 1);
                if (!(vEstado instanceof Estado)) {
                    JOptionPane.showMessageDialog(this, "Seleccione el nuevo estado.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String com = vCom == null ? "" : vCom.toString().trim();
                if (com.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Escriba un comentario de seguimiento.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                nextEstado = (Estado) vEstado;
                comentario = com;
                updated = true;
                dispose();
            });
            JPanel south = new JPanel();
            south.add(btn);
            root.add(south, BorderLayout.SOUTH);

            pack();
            setMinimumSize(new Dimension(720, 520));
            setLocationRelativeTo(owner);
        }

        boolean isUpdated() {
            return updated;
        }

        Estado getEstadoSeleccionado() {
            return nextEstado;
        }

        String getComentario() {
            return comentario;
        }
    }

    // ---- model ----
    private static class TicketTableModel extends AbstractTableModel {

        private final String[] cols = {"", "Solicitante", "Título", "Asignación", "Estado"};
        private List<Ticket> data;

        TicketTableModel(List<Ticket> data) {
            this.data = new ArrayList<>(data);
        }

        void setData(List<Ticket> lst) {
            this.data = new ArrayList<>(lst);
            fireTableDataChanged();
        }

        Ticket get(int r) {
            return data.get(r);
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return cols.length;
        }

        @Override
        public String getColumnName(int c) {
            return cols[c];
        }

        @Override
        public Object getValueAt(int r, int c) {
            Ticket t = data.get(r);
            if (c == COL_DEL) {
                return "";
            }
            if (c == COL_SOL) {
                return t.getSolicitante().toString();
            }
            if (c == COL_TIT) {
                return t.getTitulo();
            }
            if (c == COL_ASIG) {
                return t.getAsignado() == null ? "Sin asignar" : t.getAsignado().toString();
            }
            if (c == COL_EST) {
                return t.getEstadoActual().getRotulo();
            }
            return "";
        }
    }

    // ---- helpers ----
    private ImageIcon loadScaledIcon(String path, int px) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            return null;
        }
        ImageIcon ii = new ImageIcon(url);
        int w = ii.getIconWidth(), h = ii.getIconHeight();
        double s = (double) px / Math.max(w, h);
        Image scaled = ii.getImage().getScaledInstance((int) (w * s), (int) (h * s), Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    private void openUsuarios() {
        try {
            Class<?> c = Class.forName("Vista.Admin.GestionUsuariosUI");
            java.awt.Window next = (java.awt.Window) c.getDeclaredConstructor().newInstance();
            next.setVisible(true);
            dispose();
        } catch (Throwable ex) {
            JOptionPane.showMessageDialog(this, "No se pudo abrir Gestionar Usuarios.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        modelo.repo.mem.TicketRepositoryMem repo = new modelo.repo.mem.TicketRepositoryMem();
        repo.seed();
        GestionTicketsController ctrl = new GestionTicketsController(repo);
        SwingUtilities.invokeLater(() -> new GestionTicketsUI(ctrl).setVisible(true));
    }

}
