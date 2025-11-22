package dao;

import bd.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TicketDAO {

    private static final String SQL_INSERT_RECUP =
        "INSERT INTO ticket " +
        "(id_solicitante, id_empleado, id_categoria, titulo, descripcion, fecha_creacion, fecha_cierre) " +
        "VALUES (?, 1, ?, ?, ?, NOW(), NULL)";

    // Ahora recibe CARNET, no correo
    public boolean crearTicketRecuperacion(String carnet, int idCategoria) {

        // Validación extra por si acaso (7 chars)
        if (carnet == null || carnet.trim().length() != 7) {
            return false;
        }

        String titulo = "Solicitud de reseteo de contraseña";
        String descripcion =
            "El usuario con carnet " + carnet +
            " solicitó recuperación de contraseña desde login.";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERT_RECUP)) {

            ps.setString(1, carnet.trim());
            ps.setInt(2, idCategoria);
            ps.setString(3, titulo);
            ps.setString(4, descripcion);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
