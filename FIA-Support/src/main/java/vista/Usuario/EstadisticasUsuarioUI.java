package com.mycompany.soporte;

import controlador.ReportingController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
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
import modelo.dominio.EstadisticaDiaria;
import modelo.dominio.UsuarioFinal;

/**
 * Dialogo de reportes específicos para un usuario final.
 */
public class EstadisticasUsuarioUI extends JDialog {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    private final ReportingController reportingController;
    private final UsuarioFinal usuario;

    private final DefaultTableModel estadosModel = buildModel("Estado", "Total");
    private final DefaultTableModel diasModel = buildModel("Fecha", "Tickets");

    private final JTextField txtDesde = new JTextField(10);
    private final JTextField txtHasta = new JTextField(10);
    private final JLabel lblPromedio = new JLabel("Promedio resolución: -");

    public EstadisticasUsuarioUI(Window owner, ReportingController reportingController, UsuarioFinal usuario) {
        super(owner, "Mis estadísticas", ModalityType.APPLICATION_MODAL);
        this.reportingController = reportingController;
        this.usuario = usuario;
        setPreferredSize(new Dimension(720, 520));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(owner);
        buildUi();
        LocalDate hoy = LocalDate.now();
        LocalDate inicio = hoy.minusDays(14);
        txtDesde.setText(DATE_FORMAT.format(inicio));
        txtHasta.setText(DATE_FORMAT.format(hoy));
        cargarDatos();
    }

    private void buildUi() {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        setContentPane(root);

        JLabel titulo = new JLabel("Estadísticas personales");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 22f));
        titulo.setForeground(new Color(180, 40, 25));
        JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        north.setOpaque(false);
        north.add(titulo);
        if (usuario != null) {
            JLabel nombre = new JLabel(" - " + usuario.getNombres() + " " + usuario.getApellidos());
            nombre.setFont(nombre.getFont().deriveFont(Font.PLAIN, 18f));
            north.add(nombre);
        }
        root.add(north, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);
        center.add(buildResumen());
        center.add(Box.createVerticalStrut(16));
        center.add(buildDiario());

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

    private JPanel buildResumen() {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createTitledBorder("Tickets por estado"));
        JTable tabla = new JTable(estadosModel) {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return true;
            }
        };
        card.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return card;
    }

    private JPanel buildDiario() {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createTitledBorder("Actividad en el periodo"));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        filtros.add(new JLabel("Desde:"));
        filtros.add(txtDesde);
        filtros.add(new JLabel("Hasta:"));
        filtros.add(txtHasta);
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarDatos());
        filtros.add(btnActualizar);
        card.add(filtros, BorderLayout.NORTH);

        JTable tabla = new JTable(diasModel) {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return true;
            }
        };
        card.add(new JScrollPane(tabla), BorderLayout.CENTER);

        lblPromedio.setHorizontalAlignment(SwingConstants.LEFT);
        lblPromedio.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        card.add(lblPromedio, BorderLayout.SOUTH);
        return card;
    }

    private DefaultTableModel buildModel(String... columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void cargarDatos() {
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "No se encontró la información del usuario.", "Reportes", JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }

        LocalDate desde;
        LocalDate hasta;
        try {
            desde = parse(txtDesde.getText());
            hasta = parse(txtHasta.getText());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Fechas", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<EstadisticaConteo> conteos = reportingController.obtenerConteoPorEstadoUsuario(usuario.getId());
            estadosModel.setRowCount(0);
            for (EstadisticaConteo dato : conteos) {
                estadosModel.addRow(new Object[]{dato.getEtiqueta(), dato.getTotal()});
            }

            List<EstadisticaDiaria> diarios = reportingController.obtenerConteoDiarioUsuario(usuario.getId(), desde, hasta);
            diasModel.setRowCount(0);
            for (EstadisticaDiaria dia : diarios) {
                diasModel.addRow(new Object[]{dia.getFecha(), dia.getTotal()});
            }

            Double promedio = reportingController.promedioResolucionUsuario(usuario.getId(), desde, hasta);
            if (promedio == null) {
                lblPromedio.setText("Promedio resolución: N/D");
            } else {
                lblPromedio.setText(String.format("Promedio resolución: %.2f minutos", promedio));
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Reportes", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "No fue posible consultar las estadísticas.", "Reportes", JOptionPane.ERROR_MESSAGE);
        }
    }

    private LocalDate parse(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("Debe ingresar ambas fechas en formato yyyy-MM-dd.");
        }
        try {
            return LocalDate.parse(texto.trim(), DATE_FORMAT);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use yyyy-MM-dd.");
        }
    }
}
