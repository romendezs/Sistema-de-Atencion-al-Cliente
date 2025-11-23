package dao;

import bd.Conexion;
import modelo.Usuario;
import java.sql.*;

public class UsuarioDAO {

    public boolean esAdmin(String carnet) {
        String sql = "SELECT 1 FROM administrador WHERE id_administrador = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, carnet);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario validarLogin(String carnet, String password) {
    String sql = "SELECT id_usuario, nombres, apellidos " +
                 "FROM usuario WHERE id_usuario=? AND password=?";

    try (Connection cn = Conexion.getConexion();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        ps.setString(1, carnet);
        ps.setString(2, password);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setCarnet(rs.getString("id_usuario"));
                u.setNombres(rs.getString("nombres"));
                u.setApellidos(rs.getString("apellidos"));
                return u;
            }
        }
        } 
    catch (Exception e) {
        e.printStackTrace();
        }
    return null;
    }
}
