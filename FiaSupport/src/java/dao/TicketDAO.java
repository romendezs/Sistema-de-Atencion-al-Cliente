package dao;

import bd.Conexion;
import modelo.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {

    // ✅ validar carnet existente
    private static final String SQL_EXISTE_USUARIO =
        "SELECT 1 FROM usuario WHERE id_usuario = ?";

    private static final String SQL_INSERT_RECUP =
        "INSERT INTO ticket " +
        "(id_solicitante, id_empleado, id_categoria, titulo, descripcion, fecha_creacion, fecha_cierre) " +
        "VALUES (?, 1, ?, ?, ?, NOW(), NULL)";

    private static final String SQL_LISTAR_TODOS =
        "SELECT id_ticket, id_solicitante, id_empleado, id_categoria, titulo, descripcion, " +
        "fecha_creacion, fecha_cierre " +
        "FROM ticket ORDER BY fecha_creacion DESC";

    // =========================================================
    // 1) CREAR TICKET DE RECUPERACIÓN
    // =========================================================
    public boolean crearTicketRecuperacion(String carnet, int idCategoria) {

        if (carnet == null || carnet.trim().length() != 7) {
            return false;
        }

        String titulo = "Solicitud de reseteo de contraseña";
        String descripcion =
            "El usuario con carnet " + carnet +
            " solicitó recuperación de contraseña desde login.";

        try (Connection con = Conexion.getConexion()) {

            // 1) validar carnet exista
            try (PreparedStatement ps0 = con.prepareStatement(SQL_EXISTE_USUARIO)) {
                ps0.setString(1, carnet.trim());
                try (ResultSet rs0 = ps0.executeQuery()) {
                    if (!rs0.next()) return false;
                }
            }

            // 2) insertar ticket
            try (PreparedStatement ps = con.prepareStatement(SQL_INSERT_RECUP)) {
                ps.setString(1, carnet.trim());
                ps.setInt(2, idCategoria);
                ps.setString(3, titulo);
                ps.setString(4, descripcion);

                return ps.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================================================
    // 2) CREAR TICKET DESDE USUARIO
    // =========================================================
    public boolean crearTicketUsuario(Ticket t) {

        String sql =
            "INSERT INTO ticket " +
            "(id_solicitante, id_empleado, id_categoria, titulo, descripcion, fecha_creacion, fecha_cierre, fecha_suceso) " +
            "VALUES (?, 1, ?, ?, ?, NOW(), NULL, ?)";

        String titulo;
        switch (t.getIdCategoria()) {
            case 1: titulo = "Revisión de Notas en Parciales"; break;
            case 2: titulo = "Revisión Tareas/Proyectos"; break;
            case 3: titulo = "Consulta sobre Clases y Horarios"; break;
            case 4: titulo = "Consultas varias"; break;
            case 5: titulo = "Motivo de inasistencia"; break;
            case 6: titulo = "Cambio de grupo"; break;
            default: titulo = "Solicitud general";
        }

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, t.getSolicitante());
            ps.setInt(2, t.getIdCategoria());
            ps.setString(3, titulo);
            ps.setString(4, t.getDescripcion());
            ps.setDate(5, t.getFechaSuceso());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================================================
    // 3) LISTAR TICKETS POR SOLICITANTE
    // =========================================================
    public List<Ticket> listarPorSolicitante(String carnet) {
        List<Ticket> lista = new ArrayList<>();

        String sql =
            "SELECT id_ticket, titulo, descripcion, fecha_creacion, fecha_cierre, id_categoria, id_empleado " +
            "FROM ticket WHERE id_solicitante = ? ORDER BY fecha_creacion DESC";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, carnet);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ticket t = new Ticket();
                    t.setIdTicket(rs.getInt("id_ticket"));
                    t.setTitulo(rs.getString("titulo"));
                    t.setDescripcion(rs.getString("descripcion"));
                    t.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                    t.setFechaCierre(rs.getTimestamp("fecha_cierre"));
                    t.setIdCategoria(rs.getInt("id_categoria"));
                    t.setIdEmpleado(rs.getInt("id_empleado"));

                    // ✅ estado calculado
                    t.setEstado(t.getFechaCierre() == null ? "Pendiente" : "Cerrado");

                    lista.add(t);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // =========================================================
    // 4) LISTAR TODOS (ADMIN)
    // =========================================================
    public List<Ticket> listarTodos() {
        List<Ticket> lista = new ArrayList<>();

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_LISTAR_TODOS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ticket t = new Ticket();
                t.setIdTicket(rs.getInt("id_ticket"));
                t.setSolicitante(rs.getString("id_solicitante"));
                t.setIdEmpleado(rs.getInt("id_empleado"));
                t.setIdCategoria(rs.getInt("id_categoria"));
                t.setTitulo(rs.getString("titulo"));
                t.setDescripcion(rs.getString("descripcion"));
                t.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                t.setFechaCierre(rs.getTimestamp("fecha_cierre"));
                lista.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // =========================================================
    // 5) LISTAR CON FILTROS (REPORTES)
    // =========================================================
    public List<Ticket> listarFiltrados(Date fechaInicio, Date fechaFin, String estado, Integer idTecnico) {

        List<Ticket> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
            "SELECT id_ticket, id_solicitante, id_empleado, id_categoria, titulo, descripcion, " +
            "fecha_creacion, fecha_cierre " +
            "FROM ticket WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (fechaInicio != null) {
            sql.append(" AND fecha_creacion >= ? ");
            params.add(fechaInicio);
        }
        if (fechaFin != null) {
            sql.append(" AND fecha_creacion <= ? ");
            params.add(fechaFin);
        }

        if (estado != null && !estado.equalsIgnoreCase("Todos")) {
            if (estado.equalsIgnoreCase("En Proceso")) {
                sql.append(" AND fecha_cierre IS NULL ");
            } else if (estado.equalsIgnoreCase("Cerrado")) {
                sql.append(" AND fecha_cierre IS NOT NULL ");
            }
        }

        if (idTecnico != null && idTecnico > 0) {
            sql.append(" AND id_empleado = ? ");
            params.add(idTecnico);
        }

        sql.append(" ORDER BY fecha_creacion DESC ");

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ticket t = new Ticket();
                    t.setIdTicket(rs.getInt("id_ticket"));
                    t.setSolicitante(rs.getString("id_solicitante"));
                    t.setIdEmpleado(rs.getInt("id_empleado"));
                    t.setIdCategoria(rs.getInt("id_categoria"));
                    t.setTitulo(rs.getString("titulo"));
                    t.setDescripcion(rs.getString("descripcion"));
                    t.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                    t.setFechaCierre(rs.getTimestamp("fecha_cierre"));
                    lista.add(t);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // =========================================================
    // 6) REPORTES PARA GRÁFICOS
    // =========================================================
    public int contarEnProceso() {
        String sql = "SELECT COUNT(*) FROM ticket WHERE fecha_cierre IS NULL";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int contarCerrados() {
        String sql = "SELECT COUNT(*) FROM ticket WHERE fecha_cierre IS NOT NULL";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public List<Object[]> top5Categorias() {
        List<Object[]> lista = new ArrayList<>();
        String sql =
            "SELECT id_categoria, COUNT(*) as cantidad " +
            "FROM ticket GROUP BY id_categoria " +
            "ORDER BY cantidad DESC LIMIT 5";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("id_categoria"),
                    rs.getInt("cantidad")
                });
            }
        } catch (Exception e) { e.printStackTrace(); }

        return lista;
    }

    public List<Object[]> ticketsPorTecnico() {
        List<Object[]> lista = new ArrayList<>();
        String sql =
            "SELECT id_empleado, " +
            "COUNT(*) as asignados, " +
            "SUM(CASE WHEN fecha_cierre IS NOT NULL THEN 1 ELSE 0 END) as resueltos " +
            "FROM ticket GROUP BY id_empleado " +
            "ORDER BY asignados DESC";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("id_empleado"),
                    rs.getInt("asignados"),
                    rs.getInt("resueltos")
                });
            }
        } catch (Exception e) { e.printStackTrace(); }

        return lista;
    }
}
