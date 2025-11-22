package dao;

import bd.Conexion;
import modelo.Usuario;

import java.sql.*;

public class UsuarioDAO {

    public Usuario validarLogin(String carnet, String password) {
        String sql = "SELECT id_usuario, nombres, apellidos " +
                     "FROM usuario WHERE id_usuario=? AND password=?";

        System.out.println("DAO validarLogin() carnet=" + carnet + ", pass=" + password);

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, carnet);
            ps.setString(2, password);

            System.out.println("Ejecutando SQL: " + sql);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("✅ Usuario encontrado en BD");

                    Usuario u = new Usuario();
                    u.setCarnet(rs.getString("id_usuario"));
                    u.setNombre(rs.getString("nombres") + " " + rs.getString("apellidos"));
                    u.setRol("USUARIO");
                    return u;
                } else {
                    System.out.println("❌ No encontró fila con esos datos");
                }
            }

        } catch (SQLException e) {
            System.out.println("🔥 ERROR SQL: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
