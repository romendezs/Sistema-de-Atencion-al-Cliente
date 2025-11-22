package dao;

import bd.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TicketDAO {

    // ✅ Query para validar que el carnet exista en usuario
    private static final String SQL_EXISTE_USUARIO =
        "SELECT 1 FROM usuario WHERE id_usuario = ?";

    private static final String SQL_INSERT_RECUP =
        "INSERT INTO ticket " +
        "(id_solicitante, id_empleado, id_categoria, titulo, descripcion, fecha_creacion, fecha_cierre) " +
        "VALUES (?, 1, ?, ?, ?, NOW(), NULL)";

    // Ahora recibe CARNET, no correo
    public boolean crearTicketRecuperacion(String carnet, int idCategoria) {

        // Validación de formato básica (7 chars)
        if (carnet == null || carnet.trim().length() != 7) {
            return false;
        }

        String titulo = "Solicitud de reseteo de contraseña";
        String descripcion =
            "El usuario con carnet " + carnet +
            " solicitó recuperación de contraseña desde login.";

        try (Connection con = Conexion.getConexion()) {

            // ✅ 1) Verificar que el carnet exista en la tabla usuario
            try (PreparedStatement ps0 = con.prepareStatement(SQL_EXISTE_USUARIO)) {
                ps0.setString(1, carnet.trim());
                try (ResultSet rs0 = ps0.executeQuery()) {
                    if (!rs0.next()) {
                        // no existe carnet
                        return false;
                    }
                }
            }

            // ✅ 2) Si existe, entonces insertamos el ticket
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
}
