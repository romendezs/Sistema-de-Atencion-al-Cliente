package Vista.Admin;

import controlador.ReportingController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import modelo.dominio.EstadisticaConteo;
import modelo.dominio.TicketMetricasDiarias;

/**
 * Ventana dedicada a visualizar las estadísticas globales disponibles para los
 * administradores.
 */
public class EstadisticasAdminUI extends JDialog {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final Color ROJO = new Color(180, 40, 25);

    private final ReportingController reportingController;

    private final DefaultTableModel estadosModel = buildTableModel("Estado", "Total");
    private final DefaultTableModel tecnicosModel = buildTableModel("Técnico", "Tickets");
    private final DefaultTableModel categoriasModel = buildTableModel("Categoría", "Tickets");
    private final DefaultTableModel metricasModel = buildTableModel(
            "Fecha", "Abiertos", "Cerrados", "En proceso", "Prom. resolución (min)");

    private final JTextField txtDesde = new JTextField(10);
    private final JTextField txtHasta = new JTextField(10);
    private final JLabel lblPromedio = new JLabel("Promedio resolución: -");

    public EstadisticasAdminUI(Window owner, ReportingController reportingController) {
        super(owner, "Reportes y estadísticas", ModalityType.APPLICATION_MODAL);
        this.reportingController = reportingController;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(880, 620));
        setLocationRelativeTo(owner instanceof JFrame ? (JFrame) owner : null);
        buildUi();
        LocalDate hoy = LocalDate.now();
        LocalDate inicio = hoy.minusDays(14);
        txtDesde.setText(DATE_FORMAT.format(inicio));
        txtHasta.setText(DATE_FORMAT.format(hoy));
        cargarResumenes();
        actualizarMetricas();
    }

    private void buildUi() {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        setContentPane(root);

        JLabel titulo = new JLabel("Resumen de estadísticas");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 24f));
        titulo.setForeground(ROJO);
        JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        north.setOpaque(false);
        north.add(titulo);
        root.add(north, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);
        center.add(buildResumenPanel());
        center.add(Box.createVerticalStrut(16));
        center.add(buildMetricasPanel());

        JScrollPane scroll = new JScrollPane(center,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        root.add(scroll, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(btnCerrar);
        root.add(south, BorderLayout.SOUTH);

        pack();
    }

    private JPanel buildResumenPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lbl = new JLabel("Conteos globales");
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 18f));
        lbl.setForeground(ROJO);
        panel.add(lbl);
        panel.add(Box.createVerticalStrut(8));

        JPanel tables = new JPanel(new java.awt.GridLayout(1, 3, 12, 0));
        tables.setOpaque(false);
        tables.add(card("Tickets por estado", estadosModel));
        tables.add(card("Tickets por técnico", tecnicosModel));
        tables.add(card("Tickets por categoría", categoriasModel));
        panel.add(tables);
        return panel;
    }

    private JPanel buildMetricasPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lbl = new JLabel("Tendencia de tickets");
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 18f));
        lbl.setForeground(ROJO);
        panel.add(lbl);
        panel.add(Box.createVerticalStrut(8));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        filtros.add(new JLabel("Desde (yyyy-MM-dd):"));
        filtros.add(txtDesde);
        filtros.add(new JLabel("Hasta:"));
        filtros.add(txtHasta);
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> actualizarMetricas());
        filtros.add(btnActualizar);
        JButton btnExportar = new JButton("Exportar CSV");
        btnExportar.addActionListener(e -> exportarCsv());
        filtros.add(btnExportar);
        panel.add(filtros);

        JTable tablaMetricas = new JTable(metricasModel) {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return true;
            }
        };
        JScrollPane scroll = new JScrollPane(tablaMetricas);
        scroll.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        panel.add(scroll);

        lblPromedio.setHorizontalAlignment(SwingConstants.LEFT);
        lblPromedio.setBorder(BorderFactory.createEmptyBorder(8, 4, 0, 4));
        panel.add(lblPromedio);
        return panel;
    }

    private JPanel card(String titulo, DefaultTableModel model) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        card.setBackground(Color.WHITE);

        JLabel lbl = new JLabel(titulo);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 14f));
        card.add(lbl, BorderLayout.NORTH);

        JTable table = new JTable(model) {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return true;
            }
        };
        card.add(new JScrollPane(table), BorderLayout.CENTER);
        return card;
    }

    private DefaultTableModel buildTableModel(String... columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void cargarResumenes() {
        cargarTabla(estadosModel, reportingController.obtenerConteoPorEstadoGlobal());
        cargarTabla(tecnicosModel, reportingController.obtenerConteoPorTecnico());
        cargarTabla(categoriasModel, reportingController.obtenerConteoPorCategoria());
    }

    private void cargarTabla(DefaultTableModel model, List<EstadisticaConteo> datos) {
        model.setRowCount(0);
        if (datos == null || datos.isEmpty()) {
            return;
        }
        for (EstadisticaConteo dato : datos) {
            model.addRow(new Object[]{dato.getEtiqueta(), dato.getTotal()});
        }
    }

    private void actualizarMetricas() {
        LocalDate desde;
        LocalDate hasta;
        try {
            desde = parseFecha(txtDesde.getText().trim());
            hasta = parseFecha(txtHasta.getText().trim());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Fechas", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<TicketMetricasDiarias> metricas = reportingController.metricasPorPeriodo(desde, hasta);
            metricasModel.setRowCount(0);
            for (TicketMetricasDiarias m : metricas) {
                metricasModel.addRow(new Object[]{
                        m.getId().getDia(),
                        safeNumber(m.getAbiertos()),
                        safeNumber(m.getCerrados()),
                        safeNumber(m.getReabiertos()),
                        safeNumber(m.getPromedioResolucionMin())
                });
            }
            Double promedio = reportingController.promedioResolucionGlobal(desde, hasta);
            if (promedio == null) {
                lblPromedio.setText("Promedio resolución: N/D");
            } else {
                lblPromedio.setText(String.format("Promedio resolución: %.2f minutos", promedio));
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Reportes", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "No fue posible cargar las métricas.", "Reportes", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportarCsv() {
        LocalDate desde;
        LocalDate hasta;
        try {
            desde = parseFecha(txtDesde.getText().trim());
            hasta = parseFecha(txtHasta.getText().trim());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Fechas", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String csv = reportingController.exportMetricasCsv(desde, hasta);
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Guardar métricas");
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                if (!file.getName().toLowerCase().endsWith(".csv")) {
                    file = new File(file.getParentFile(), file.getName() + ".csv");
                }
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(csv);
                }
                JOptionPane.showMessageDialog(this, "Archivo exportado correctamente.", "Reportes", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No fue posible guardar el archivo.", "Reportes", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "No fue posible generar el archivo.", "Reportes", JOptionPane.ERROR_MESSAGE);
        }
    }

    private LocalDate parseFecha(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("Debe ingresar ambas fechas en formato yyyy-MM-dd.");
        }
        try {
            return LocalDate.parse(texto, DATE_FORMAT);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use yyyy-MM-dd.");
        }
    }

    private Object safeNumber(Number numero) {
        return numero == null ? "" : numero;
    }
}
