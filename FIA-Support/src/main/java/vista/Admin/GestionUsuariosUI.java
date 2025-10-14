package Vista.Admin;

import controlador.ReportingController;
import controlador.UserAdminController;
import modelo.dominio.Carrera;
import modelo.dominio.Facultad;
import modelo.dominio.UsuarioFinal;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;

import javax.swing.text.*;

public class GestionUsuariosUI extends JFrame {

    // ======== Controller inyectado ========
    private UserAdminController controller;
    private ReportingController reportingController;

    //para las opciones del menu
    private controlador.TicketController ticketsController;
    private controlador.AssignmentController assignmentController;
    private controlador.WorkflowController workflowController;

    public void setController(UserAdminController c) {
        this.controller = c;
    }

    public void setReportingController(ReportingController reportingController) {
        this.reportingController = reportingController;
    }

    public void setTicketsController(controlador.TicketController c) {
        this.ticketsController = c;
    }

    public void setAssignmentController(controlador.AssignmentController c) {
        this.assignmentController = c;
    }

    public void setWorkflowController(controlador.WorkflowController c) {
        this.workflowController = c;
    }

    // Colores
    private static final Color ROJO = new Color(180, 40, 25);
    private static final Color ROJO_CLARO = new Color(244, 196, 192);
    private static final Color GRIS_TXT = new Color(90, 90, 90);
    private static final int ACTION_BTN = 28;
    private static final int ACTION_GAP = 8;
    private static final int CELL_PAD_RIGHT = 8;

    // Form crear
    private JTextField txtCarnet, txtNombres, txtApellidos;
    private JRadioButton rbEstSi, rbEstNo;
    private JComboBox<Facultad> cbFacultad;
    private JComboBox<Carrera> cbCarrera;
    private JButton btnCrear;

    // Tabla
    private JTable table;
    private UsuariosTableModel model;
    private TableRowSorter<UsuariosTableModel> sorter;
    private JTextField txtBuscar;

    // Hover acciones
    private int hoveredRow = -1;
    private float actionsAlpha = 0f;
    private javax.swing.Timer hoverTimer = new javax.swing.Timer(20, e -> {
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
    private final int actionsColumnWidth = 140;

    private static DefaultListCellRenderer nombreFacultadRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                String txt = (value instanceof Facultad f) ? f.getNombre() : "";
                return super.getListCellRendererComponent(list, txt, index, isSelected, cellHasFocus);
            }
        };
    }

    private static DefaultListCellRenderer nombreCarreraRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                String txt = (value instanceof Carrera c) ? c.getNombre() : "";
                return super.getListCellRendererComponent(list, txt, index, isSelected, cellHasFocus);
            }
        };
    }

    public GestionUsuariosUI() {
        setTitle("FIA Support");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(980, 680));
        setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buildTopMenu(), BorderLayout.NORTH);
        JComponent main = buildMain();                       // tu panel central actual
        JScrollPane scroller = new JScrollPane(
                main,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        scroller.setBorder(BorderFactory.createEmptyBorder()); // sin borde
        scroller.getViewport().setBackground(Color.WHITE);     // fondo blanco
        scroller.getVerticalScrollBar().setUnitIncrement(18);  // scroll suave
        getContentPane().add(scroller, BorderLayout.CENTER);
        ImageIcon windowIcon = loadIconSafely("/Vista/img/icon.png");
        if (windowIcon != null) {
            setIconImage(windowIcon.getImage());
        }

    }

    // Mostrar nombre legible en los combos
    /* ----------------- Métodos puente usados por el Controller ----------------- */
    public void setFacultades(List<Facultad> facs) {
        cbFacultad.removeAllItems();
        for (Facultad f : facs) {
            cbFacultad.addItem(f);
        }
        if (cbFacultad.getItemCount() > 0) {
            cbFacultad.setSelectedIndex(0);
        }
    }

    public void setCarreras(List<Carrera> cars) {
        cbCarrera.removeAllItems();
        for (Carrera c : cars) {
            cbCarrera.addItem(c);
        }
        if (cbCarrera.getItemCount() > 0) {
            cbCarrera.setSelectedIndex(0);
        }
    }

    public void setUsuarios(List<UsuarioFinal> usuarios) {
        model.setData(usuarios);
    }

    public void showError(String m) {
        JOptionPane.showMessageDialog(this, m, "Validación", JOptionPane.WARNING_MESSAGE);
    }

    public void showInfo(String m) {
        JOptionPane.showMessageDialog(this, m, "FIA Support", JOptionPane.INFORMATION_MESSAGE);
    }

    public void clearForm() {
        txtCarnet.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        rbEstSi.setSelected(true);
        cbCarrera.setEnabled(true);
        if (cbFacultad.getItemCount() > 0) {
            cbFacultad.setSelectedIndex(0);
        }
        cbFacultadAction(); // recarga carreras
    }

    /* ---------------------------------- UI ---------------------------------- */
    //icono del menu
    private ImageIcon loadScaledIcon(String resourcePath, int targetPx) {
        java.net.URL url = getClass().getResource(resourcePath);
        if (url == null) {
            System.err.println("No se encontró el recurso: " + resourcePath);
            return null;
        }
        ImageIcon ii = new ImageIcon(url);
        int w = ii.getIconWidth(), h = ii.getIconHeight();
        if (w <= 0 || h <= 0) {
            return ii;
        }

        double scale = (double) targetPx / Math.max(w, h);
        int nw = (int) Math.round(w * scale);
        int nh = (int) Math.round(h * scale);

        Image scaled = ii.getImage().getScaledInstance(nw, nh, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    private ImageIcon loadIconSafely(String resourcePath) {
        java.net.URL url = getClass().getResource(resourcePath);
        if (url == null) {
            System.err.println("No se encontró el recurso: " + resourcePath);
            return null;
        }
        return new ImageIcon(url);
    }

    private JPanel buildTopMenu() {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(ROJO);
        JPanel brand = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        brand.setOpaque(false);
        JLabel logo = new JLabel(" ");
        logo.setPreferredSize(new Dimension(28, 28));
        brand.setOpaque(false);
        logo.setPreferredSize(new Dimension(28, 28));
        // usa el archivo que tengas en /Vista/img
        ImageIcon menuIcon = loadScaledIcon("/Vista/img/icon.png", 24); // o "/Vista/img/Logo.png"
        if (menuIcon != null) {
            logo.setIcon(menuIcon);
        }

        JLabel title = new JLabel("FIA Support");
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));

        brand.add(logo);
        brand.add(title);
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        brand.add(logo);
        brand.add(title);

        JPanel mid = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 8));
        mid.setOpaque(false);
        JButton btnTickets = btnHeader("Gestionar Tickets");
        btnTickets.addActionListener(e -> openGestionTickets());
        JButton btnReportes = btnHeader("Ver Reportes y Estadísticas");
        btnReportes.addActionListener(e -> openReportes());
        mid.add(btnTickets);
        mid.add(btnReportes);

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
        b.setBorder(new CompoundBorder(new LineBorder(new Color(230, 150, 140)), new EmptyBorder(6, 12, 6, 12)));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JPanel buildMain() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);
        root.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel h1 = new JLabel("Gestionar Usuarios");
        h1.setFont(h1.getFont().deriveFont(Font.BOLD, 28f));
        h1.setForeground(ROJO);
        JPanel pTitle = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 18));
        pTitle.setOpaque(false);
        pTitle.add(h1);
        root.add(pTitle, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.add(buildNuevoUsuarioPanel());
        center.add(Box.createVerticalStrut(16));
        center.add(buildListadoPanel());
        root.add(center, BorderLayout.CENTER);
        return root;
    }

    private JPanel buildNuevoUsuarioPanel() {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(new LineBorder(ROJO, 2), new EmptyBorder(16, 24, 16, 24)));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(900, Integer.MAX_VALUE)); // ancho máximo del card

        JLabel title = new JLabel("+  Nuevo Usuario");
        title.setForeground(ROJO);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));

        JLabel lCarnet = new JLabel("Carnet:");
        JLabel lNombres = new JLabel("Nombres:");
        JLabel lApellidos = new JLabel("Apellidos:");
        JLabel lEst = new JLabel("Estudiante:");
        JLabel lFac = new JLabel("Facultad:");
        JLabel lCar = new JLabel("Carrera:");
        lCarnet.setForeground(GRIS_TXT);
        lNombres.setForeground(GRIS_TXT);
        lApellidos.setForeground(GRIS_TXT);
        lEst.setForeground(GRIS_TXT);
        lFac.setForeground(GRIS_TXT);
        lCar.setForeground(GRIS_TXT);

        txtCarnet = new JTextField();
        txtNombres = new JTextField();
        txtApellidos = new JTextField();
        onlyLetters(txtNombres);
        onlyLetters(txtApellidos);
        carnetFilter(txtCarnet);

        rbEstSi = new JRadioButton("Sí");
        rbEstNo = new JRadioButton("No");
        rbEstSi.setOpaque(false);
        rbEstNo.setOpaque(false);
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbEstSi);
        bg.add(rbEstNo);

        cbFacultad = new JComboBox<>();
        cbCarrera = new JComboBox<>();
        cbFacultad.setRenderer(nombreFacultadRenderer());
        cbCarrera.setRenderer(nombreCarreraRenderer());
        cbCarrera.setEnabled(false);
        cbFacultad.addActionListener(e -> cbFacultadAction());
        rbEstSi.addActionListener(e -> actualizarDisponibilidadCarrera());
        rbEstNo.addActionListener(e -> actualizarDisponibilidadCarrera());

        btnCrear = new JButton("Crear");
        btnCrear.setBackground(ROJO);
        btnCrear.setForeground(Color.WHITE);
        btnCrear.setFocusPainted(false);
        btnCrear.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCrear.addActionListener(e -> onCrearUsuario());

        GroupLayout gl = new GroupLayout(card);
        card.setLayout(gl);
        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);

        gl.setHorizontalGroup(
                gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(title)
                        .addGroup(gl.createSequentialGroup()
                                .addGroup(gl.createParallelGroup()
                                        .addComponent(lCarnet).addComponent(txtCarnet, 260, 260, 260)
                                        .addComponent(lNombres).addComponent(txtNombres, 260, 260, 260)
                                        .addComponent(lApellidos).addComponent(txtApellidos, 260, 260, 260))
                                .addGap(24)
                                .addGroup(gl.createParallelGroup()
                                        .addComponent(lEst)
                                        .addGroup(gl.createSequentialGroup().addComponent(rbEstSi).addGap(10).addComponent(rbEstNo))
                                        .addComponent(lFac).addComponent(cbFacultad, 280, 280, 280)
                                        .addComponent(lCar).addComponent(cbCarrera, 280, 280, 280)))
                        .addGroup(GroupLayout.Alignment.CENTER,
                                gl.createSequentialGroup().addComponent(btnCrear, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
        );
        gl.setVerticalGroup(
                gl.createSequentialGroup()
                        .addComponent(title).addGap(8)
                        .addGroup(gl.createParallelGroup()
                                .addGroup(gl.createSequentialGroup()
                                        .addComponent(lCarnet).addComponent(txtCarnet, 28, 28, 28).addGap(6)
                                        .addComponent(lNombres).addComponent(txtNombres, 28, 28, 28).addGap(6)
                                        .addComponent(lApellidos).addComponent(txtApellidos, 28, 28, 28))
                                .addGroup(gl.createSequentialGroup()
                                        .addComponent(lEst)
                                        .addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(rbEstSi).addComponent(rbEstNo)).addGap(6)
                                        .addComponent(lFac).addComponent(cbFacultad, 28, 28, 28).addGap(6)
                                        .addComponent(lCar).addComponent(cbCarrera, 28, 28, 28)))
                        .addGap(8).addComponent(btnCrear, 32, 32, 32)
        );

        return card;
    }

    private void cbFacultadAction() {
        Facultad f = (Facultad) cbFacultad.getSelectedItem();
        if (controller != null) {
            controller.onSeleccionFacultad(f);
        }
    }

    private JPanel buildListadoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(8, 0, 0, 0));

        // Título
        JLabel h2 = new JLabel("Listado");
        h2.setFont(h2.getFont().deriveFont(Font.BOLD, 22f));
        h2.setForeground(ROJO);
        JPanel pTitle = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 8));
        pTitle.setOpaque(false);
        pTitle.add(h2);

        // Buscar
        JPanel search = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        search.setOpaque(false);
        txtBuscar = new JTextField(24);
        txtBuscar.setToolTipText("Buscar por carnet");
        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filtrar();
            }

            public void removeUpdate(DocumentEvent e) {
                filtrar();
            }

            public void changedUpdate(DocumentEvent e) {
                filtrar();
            }
        });
        search.add(txtBuscar);

        // Cabeceras “rosadas”
        JPanel headers = new JPanel(new GridLayout(1, 3, 12, 0));
        headers.setOpaque(false);
        headers.setBorder(new EmptyBorder(12, 0, 8, 0));
        headers.add(headerBox("Id (Carnet)"));
        headers.add(headerBox("Nombres"));
        headers.add(headerBox("Apellidos"));

        // Zona norte unificada
        JPanel north = new JPanel();
        north.setOpaque(false);
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        north.add(pTitle);
        north.add(search);
        north.add(headers);
        panel.add(north, BorderLayout.NORTH);

        // --- Tabla ---
        model = new UsuariosTableModel();
        table = new JTable(model) {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return true;
            }
        };
        table.setRowHeight(36);
        table.setFillsViewportHeight(true);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 4));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        DefaultTableCellRenderer cell = new DefaultTableCellRenderer();
        cell.setBackground(new Color(242, 242, 242));
        cell.setBorder(new EmptyBorder(0, 12, 0, 12));
        table.getColumnModel().getColumn(1).setCellRenderer(cell);
        table.getColumnModel().getColumn(2).setCellRenderer(cell);

// Columna 0: Id con acciones
        TableColumn c0 = table.getColumnModel().getColumn(0);
        c0.setPreferredWidth(actionsColumnWidth);
        c0.setCellRenderer(new RowActionRenderer());

        JTableHeader th = table.getTableHeader();
        if (th != null) {
            th.setReorderingAllowed(false);   // <- no permitir arrastrar columnas
            th.setResizingAllowed(false);     // <- opcional: bloquear redimensionar
        }

// Hover + clicks (igual que ya tenías)
        table.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                hoveredRow = row;
                updateCursorForActions(e);
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
                handleTableClick(e);
            }
        });

// Filtro
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

// Scrollpane
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new EmptyBorder(6, 0, 0, 0));
        sp.setPreferredSize(new Dimension(1, 260));

// *** Ocultamos el header nativo del JTable (para que no duplique tus cajas rosadas) ***
        sp.setColumnHeaderView(null);    // <- clave

        panel.add(sp, BorderLayout.CENTER);

        return panel;
    }

    private JPanel headerBox(String text) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(ROJO_CLARO);
        p.setBorder(new EmptyBorder(8, 12, 8, 12));
        JLabel l = new JLabel(text);
        l.setForeground(GRIS_TXT);
        p.add(l, BorderLayout.WEST);
        return p;
    }

    private void filtrar() {
        String text = txtBuscar.getText().trim();
        sorter.setRowFilter(text.isEmpty() ? null : RowFilter.regexFilter("(?i)" + Pattern.quote(text), 0));
    }

    /* -------------------- Lógica UI (llama al Controller) ------------------- */
    private void openGestionTickets() {
        if (ticketsController == null || assignmentController == null || workflowController == null || reportingController == null) {
            JOptionPane.showMessageDialog(this,
                    "Faltan controladores para abrir Gestión de Tickets.",
                    "Navegación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        GestionTicketsUI ui = new GestionTicketsUI(
                ticketsController,
                assignmentController,
                workflowController,
                reportingController
        );
        ui.setLocationRelativeTo(this);
        ui.setVisible(true);
    }

    private void openReportes() {
        if (reportingController == null) {
            JOptionPane.showMessageDialog(this,
                    "No se configuró el servicio de reportes.",
                    "Reportes",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        EstadisticasAdminUI dialog = new EstadisticasAdminUI(this, reportingController);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void actualizarDisponibilidadCarrera() {
        boolean estudiante = rbEstSi.isSelected();
        cbCarrera.setEnabled(estudiante);
        if (!estudiante) {
            cbCarrera.setSelectedItem(null);
        }
    }

    private void onCrearUsuario() {
        boolean esEst = rbEstSi.isSelected();
        Facultad f = esEst ? (Facultad) cbFacultad.getSelectedItem() : null;
        Carrera c = esEst ? (Carrera) cbCarrera.getSelectedItem() : null;

        if (controller != null) {
            controller.onCrear(
                    normalizeCarnet(txtCarnet.getText()),
                    normalizeText(txtNombres.getText()),
                    normalizeText(txtApellidos.getText()),
                    esEst, f, c
            );
        }
    }

    private void handleTableClick(MouseEvent e) {
        int row = table.rowAtPoint(e.getPoint());
        int col = table.columnAtPoint(e.getPoint());
        if (row < 0 || col != 0) {
            return;
        }

        int modelRow = table.convertRowIndexToModel(row);
        Rectangle cell = table.getCellRect(row, col, false);

        int xIn = e.getX() - cell.x;
        int yIn = e.getY() - cell.y;

        int cellW = cell.width;
        int startX = cellW - CELL_PAD_RIGHT - (ACTION_BTN * 2 + ACTION_GAP);
        int yBtn = (cell.height - ACTION_BTN) / 2;

        Rectangle rEdit = new Rectangle(startX, yBtn, ACTION_BTN, ACTION_BTN);
        Rectangle rDel = new Rectangle(startX + ACTION_BTN + ACTION_GAP, yBtn, ACTION_BTN, ACTION_BTN);

        if (rEdit.contains(xIn, yIn)) {
            onEditar(model.get(modelRow));
        } else if (rDel.contains(xIn, yIn)) {
            onEliminar(model.get(modelRow));
        }
    }

    private void onEditar(UsuarioFinal user) {
        EditDialog dlg = new EditDialog(this, controller, user);
        dlg.setVisible(true);
    }

    private void onEliminar(UsuarioFinal user) {
        DeleteDialog dlg = new DeleteDialog(this, user.getId(), controller);
        dlg.setVisible(true);
    }

    private void updateCursorForActions(MouseEvent e) {
        int row = table.rowAtPoint(e.getPoint());
        int col = table.columnAtPoint(e.getPoint());
        if (row < 0 || col != 0) {
            table.setCursor(Cursor.getDefaultCursor());
            return;
        }

        Rectangle cell = table.getCellRect(row, col, false);
        int xIn = e.getX() - cell.x;
        int yIn = e.getY() - cell.y;

        int cellW = cell.width;
        int startX = cellW - CELL_PAD_RIGHT - (ACTION_BTN * 2 + ACTION_GAP);
        int yBtn = (cell.height - ACTION_BTN) / 2;

        Rectangle rEdit = new Rectangle(startX, yBtn, ACTION_BTN, ACTION_BTN);
        Rectangle rDel = new Rectangle(startX + ACTION_BTN + ACTION_GAP, yBtn, ACTION_BTN, ACTION_BTN);

        boolean over = rEdit.contains(xIn, yIn) || rDel.contains(xIn, yIn);
        table.setCursor(over ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getDefaultCursor());
    }


    /* ----------------------------- Utils ---------------------------------- */
    private String normalizeText(String s) {
        return (s == null) ? "" : s.trim();
    }

    private String normalizeCarnet(String s) {
        s = normalizeText(s);
        if (s.length() >= 2) {
            String letras = s.substring(0, 2).toUpperCase(Locale.ROOT);
            String resto = (s.length() > 2) ? s.substring(2) : "";
            return letras + resto;
        }
        return s.toUpperCase(Locale.ROOT);
    }

    private ImageIcon drawCircleLogo(int r) {
        int d = r * 2;
        BufferedImage img = new BufferedImage(d, d, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.fillOval(0, 0, d, d);
        g.setColor(ROJO);
        g.setStroke(new BasicStroke(3f));
        g.drawOval(1, 1, d - 3, d - 3);
        g.dispose();
        return new ImageIcon(img);
    }

    // ====== Input filters
    private static final java.util.regex.Pattern LETTERS_PATTERN = java.util.regex.Pattern.compile("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+");

    private static void onlyLetters(JTextField field) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int off, String str, AttributeSet a) throws BadLocationException {
                if (str == null || str.isEmpty() || LETTERS_PATTERN.matcher(str).matches()) {
                    super.insertString(fb, off, str, a);
                }
            }

            @Override
            public void replace(FilterBypass fb, int off, int len, String str, AttributeSet a) throws BadLocationException {
                if (str == null || str.isEmpty() || LETTERS_PATTERN.matcher(str).matches()) {
                    super.replace(fb, off, len, str, a);
                }
            }
        });
    }

    private static void carnetFilter(JTextField field) {
        final java.util.regex.Pattern p = java.util.regex.Pattern.compile("[A-Za-z0-9]+");
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int off, String s, AttributeSet a) throws BadLocationException {
                if (s == null) {
                    return;
                }
                String next = fb.getDocument().getText(0, fb.getDocument().getLength()) + s;
                if (p.matcher(s).matches() && next.length() <= 7) {
                    super.insertString(fb, off, s, a);
                }
            }

            @Override
            public void replace(FilterBypass fb, int off, int len, String s, AttributeSet a) throws BadLocationException {
                String base = fb.getDocument().getText(0, fb.getDocument().getLength());
                String next = (s == null) ? base : base.substring(0, off) + s + base.substring(off + len);
                if ((s == null || p.matcher(s).matches()) && next.length() <= 7) {
                    super.replace(fb, off, len, s, a);
                }
            }
        });
    }

    // ====== Renderers (header/row actions)
    private static class HeaderRenderer implements TableCellRenderer {

        private final TableCellRenderer delegate;

        HeaderRenderer(TableCellRenderer d) {
            this.delegate = d;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = delegate.getTableCellRendererComponent(table, value, false, false, row, column);
            c.setBackground(new Color(250, 250, 250));
            c.setForeground(new Color(80, 80, 80));
            return c;
        }
    }

    private class RowActionRenderer extends DefaultTableCellRenderer {

        RowActionRenderer() {
            setOpaque(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable t, Object value, boolean isSel, boolean hasFocus, int row, int col) {
            JPanel p = new JPanel(new BorderLayout());
            p.setOpaque(false);
            JLabel lbl = new JLabel("  " + String.valueOf(value));
            lbl.setOpaque(true);
            lbl.setBackground(new Color(242, 242, 242));
            lbl.setBorder(new EmptyBorder(0, 12, 0, 12));
            p.add(lbl, BorderLayout.CENTER);
            if (row == hoveredRow && actionsAlpha > 0f) {
                JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4)) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setComposite(AlphaComposite.SrcOver.derive(actionsAlpha));
                        super.paintComponent(g2);
                        g2.dispose();
                    }
                };
                btns.setOpaque(false);
                btns.add(iconBtn("✎"));
                btns.add(iconBtn("🗑"));
                p.add(btns, BorderLayout.EAST);
            }
            return p;
        }

        private Component iconBtn(String glyph) {
            JLabel b = new JLabel(glyph, SwingConstants.CENTER);
            b.setOpaque(true);
            b.setBackground(Color.WHITE);
            b.setBorder(new LineBorder(new Color(220, 220, 220)));
            b.setPreferredSize(new Dimension(28, 28));
            return b;
        }
    }

    /* =========================== Modelo de Tabla =========================== */
    private static class UsuariosTableModel extends AbstractTableModel {

        private final String[] cols = {"Id (Carnet)", "Nombres", "Apellidos"};
        private final List<UsuarioFinal> data = new ArrayList<>();

        public void setData(List<UsuarioFinal> d) {
            data.clear();
            data.addAll(d);
            fireTableDataChanged();
        }

        public UsuarioFinal get(int row) {
            return data.get(row);
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
            UsuarioFinal u = data.get(r);
            switch (c) {
                case 0:
                    return u.getId();
                case 1:
                    return u.getNombres();
                case 2:
                    return u.getApellidos();
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    }

    /* =============================== Diálogos ============================== */
    // Editar
    /**
     * Diálogo de edición con mismas validaciones que crear (rehecho)
     */
    private static class EditDialog extends JDialog {

        private final UserAdminController controller;
        private final UsuarioFinal user;

        private JTextField txtNombres, txtApellidos;
        private JRadioButton rbSi, rbNo;
        private JComboBox<Facultad> cbFac;
        private JComboBox<Carrera> cbCar;

        // Mostrar nombre legible en los combos
        private static DefaultListCellRenderer nombreFacultadRenderer() {
            return new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(
                        JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    String txt = (value instanceof Facultad f) ? f.getNombre() : "";
                    return super.getListCellRendererComponent(list, txt, index, isSelected, cellHasFocus);
                }
            };
        }

        private static DefaultListCellRenderer nombreCarreraRenderer() {
            return new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(
                        JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    String txt = (value instanceof Carrera c) ? c.getNombre() : "";
                    return super.getListCellRendererComponent(list, txt, index, isSelected, cellHasFocus);
                }
            };
        }

        EditDialog(Frame owner, UserAdminController controller, UsuarioFinal user) {
            super(owner, "Editar Usuario", true);
            this.controller = controller;
            this.user = user;

            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            JPanel root = new JPanel(new BorderLayout());
            root.setBorder(new EmptyBorder(12, 12, 12, 12));
            setContentPane(root);

            // ---------- Header ----------
            JLabel title = new JLabel("  Editar Usuario");
            title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
            title.setIcon(UIManager.getIcon("OptionPane.informationIcon"));

            JButton btnX = new JButton("✕");
            btnX.setFocusPainted(false);
            btnX.setContentAreaFilled(false);
            btnX.setBorder(new EmptyBorder(4, 8, 4, 8));
            btnX.addActionListener(e -> dispose());

            JPanel head = new JPanel(new BorderLayout());
            head.add(title, BorderLayout.WEST);
            head.add(btnX, BorderLayout.EAST);
            root.add(head, BorderLayout.NORTH);

            // ---------- Formulario (GridBag) ----------
            JPanel form = new JPanel(new GridBagLayout());
            GridBagConstraints gc = new GridBagConstraints();
            gc.gridx = 0;
            gc.gridy = 0;
            gc.fill = GridBagConstraints.HORIZONTAL;
            gc.anchor = GridBagConstraints.WEST;
            gc.weightx = 1.0;
            gc.insets = new Insets(6, 0, 2, 0);

            JLabel lEdit = new JLabel("Editando " + user.getId());
            lEdit.setFont(lEdit.getFont().deriveFont(Font.BOLD, 16f));
            gc.gridwidth = 2;
            form.add(lEdit, gc);

            // Nombres
            gc.gridy++;
            gc.insets = new Insets(10, 0, 2, 0);
            form.add(new JLabel("Nombres"), gc);
            txtNombres = new JTextField(user.getNombres());
            onlyLetters(txtNombres);
            gc.gridy++;
            gc.insets = new Insets(0, 0, 0, 0);
            form.add(txtNombres, gc);

            // Apellidos
            gc.gridy++;
            gc.insets = new Insets(10, 0, 2, 0);
            form.add(new JLabel("Apellidos"), gc);
            txtApellidos = new JTextField(user.getApellidos());
            onlyLetters(txtApellidos);
            gc.gridy++;
            gc.insets = new Insets(0, 0, 0, 0);
            form.add(txtApellidos, gc);

            // Estudiante
            gc.gridy++;
            gc.insets = new Insets(10, 0, 2, 0);
            form.add(new JLabel("Estudiante:"), gc);

            JPanel est = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
            rbSi = new JRadioButton("Sí");
            rbNo = new JRadioButton("No");
            ButtonGroup bg = new ButtonGroup();
            bg.add(rbSi);
            bg.add(rbNo);
            rbSi.setSelected(user.isEsEstudiante());
            rbNo.setSelected(!user.isEsEstudiante());
            rbSi.setOpaque(false);
            rbNo.setOpaque(false);
            est.add(rbSi);
            est.add(rbNo);

            gc.gridy++;
            gc.insets = new Insets(0, 0, 0, 0);
            form.add(est, gc);

            // Facultad
            gc.gridy++;
            gc.insets = new Insets(10, 0, 2, 0);
            form.add(new JLabel("Facultad:"), gc);
            cbFac = new JComboBox<>();
            for (Facultad f : controller.getFacultades()) {
                cbFac.addItem(f);
            }
            gc.gridy++;
            gc.insets = new Insets(0, 0, 0, 0);
            form.add(cbFac, gc);
            cbFac.setRenderer(nombreFacultadRenderer());

            // Carrera
            gc.gridy++;
            gc.insets = new Insets(10, 0, 2, 0);
            form.add(new JLabel("Carrera:"), gc);
            cbCar = new JComboBox<>();
            gc.gridy++;
            gc.insets = new Insets(0, 0, 0, 0);
            form.add(cbCar, gc);
            cbCar.setRenderer(nombreCarreraRenderer());

            // Carga carreras según facultad
            ActionListener loadCars = e -> {
                cbCar.removeAllItems();
                Facultad fac = (Facultad) cbFac.getSelectedItem();
                if (fac != null) {
                    for (Carrera c : controller.getCarrerasByFacultad(fac.getId())) {
                        cbCar.addItem(c);
                    }
                }
                if (user.getCarrera() != null) {
                    cbCar.setSelectedItem(user.getCarrera());
                }
            };
            cbFac.addActionListener(loadCars);

            // Preselección
            if (user.isEsEstudiante() && user.getFacultad() != null) {
                cbFac.setSelectedItem(user.getFacultad());
            } else if (cbFac.getItemCount() > 0) {
                cbFac.setSelectedIndex(0);
            }
            loadCars.actionPerformed(null);

            // Habilitar/deshabilitar carrera
            Runnable toggleCarrera = () -> cbCar.setEnabled(rbSi.isSelected());
            rbSi.addActionListener(e -> toggleCarrera.run());
            rbNo.addActionListener(e -> toggleCarrera.run());
            toggleCarrera.run();

            root.add(form, BorderLayout.CENTER);

            // ---------- Botón (siempre visible) ----------
            JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton btn = new JButton("Enviar");
            btn.setBackground(ROJO);
            btn.setForeground(Color.WHITE);
            btn.setPreferredSize(new Dimension(120, 32));
            btn.addActionListener(e -> {
                boolean ok = controller.onEditar(
                        user,
                        txtNombres.getText().trim(),
                        txtApellidos.getText().trim(),
                        rbSi.isSelected(),
                        rbSi.isSelected() ? (Facultad) cbFac.getSelectedItem() : null,
                        rbSi.isSelected() ? (Carrera) cbCar.getSelectedItem() : null
                );
                if (ok) {
                    dispose();
                }
            });
            south.add(btn);
            south.setBorder(new EmptyBorder(10, 0, 0, 0));
            root.add(south, BorderLayout.SOUTH);

            // Tamaño / ubicación
            pack();                                    // calcula tamaño justo
            setMinimumSize(new Dimension(560, 420));   // asegura altura para botón
            setLocationRelativeTo(owner);
        }
    }

    // Eliminar
    private static class DeleteDialog extends JDialog {

        public DeleteDialog(Frame owner, String carnet, UserAdminController controller) {
            super(owner, true);
            setTitle("Confirmación de Eliminar Usuario");

            setResizable(false);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            JPanel root = new JPanel(new BorderLayout());
            root.setBorder(new EmptyBorder(12, 12, 12, 12));
            setContentPane(root);

            JLabel title = new JLabel(" Confirmación de Eliminar Usuario");
            title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
            title.setIcon(UIManager.getIcon("OptionPane.warningIcon"));
            JButton btnX = new JButton("✕");
            btnX.addActionListener(e -> dispose());
            btnX.setBorder(new EmptyBorder(4, 8, 4, 8));
            btnX.setFocusPainted(false);
            btnX.setContentAreaFilled(false);
            JPanel head = new JPanel(new BorderLayout());
            head.add(title, BorderLayout.WEST);
            head.add(btnX, BorderLayout.EAST);
            root.add(head, BorderLayout.NORTH);

            JLabel msg = new JLabel(
                    "<html><div style='text-align:center;color:#C00;'>Estás a punto de eliminar todos los datos que pertenecen<br>"
                    + "al usuario <b>" + carnet + "</b>. Confirma la acción escribiendo <b>Eliminar</b>.</div></html>", SwingConstants.CENTER);

            JTextField input = new JTextField();
            JButton btnEliminar = new JButton("Eliminar");
            btnEliminar.setBackground(ROJO);
            btnEliminar.setForeground(Color.WHITE);
            btnEliminar.setEnabled(false);
            btnEliminar.addActionListener(e -> {
                controller.onEliminar(carnet);
                dispose();
            });

            input.getDocument().addDocumentListener(new DocumentListener() {
                void check() {
                    btnEliminar.setEnabled(input.getText().trim().equalsIgnoreCase("eliminar"));
                }

                public void insertUpdate(DocumentEvent e) {
                    check();
                }

                public void removeUpdate(DocumentEvent e) {
                    check();
                }

                public void changedUpdate(DocumentEvent e) {
                    check();
                }
            });

            JPanel center = new JPanel();
            center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
            center.add(Box.createVerticalStrut(8));
            center.add(msg);
            center.add(Box.createVerticalStrut(12));
            center.add(input);
            center.add(Box.createVerticalStrut(12));
            JPanel pbtn = new JPanel();
            pbtn.add(btnEliminar);
            center.add(pbtn);
            root.add(center, BorderLayout.CENTER);

            setSize(520, 240);
            setLocationRelativeTo(owner);
        }
    }

    /* =============================== MAIN ================================= */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()
                -> JOptionPane.showMessageDialog(null,
                        "Inicialice los repositorios reales para ejecutar la pantalla de usuarios.",
                        "FIA Support",
                        JOptionPane.INFORMATION_MESSAGE));
    }
}
