/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.db.pgConexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Méndez
 */
public class DbSmoke {
    
    public static void main(String[] args) {
        // Adjust class name if needed (PgConexion vs pgConexion)
        pgConexion db = new pgConexion();
        try (Connection cn = db.conectar()) {
            System.out.println("Connected? " + (cn != null && !cn.isClosed()));

            try (Statement st = cn.createStatement();
                 ResultSet rs = st.executeQuery("select version(), current_database()")) {
                while (rs.next()) {
                    System.out.println("Server: " + rs.getString(1));
                    System.out.println("DB: " + rs.getString(2));
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // shows exact error (driver missing, auth, host, etc.)
        }
    }
    
}
