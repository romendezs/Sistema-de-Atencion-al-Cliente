/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.db.pgConexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Méndez
 */
public class pgConexion {
    
    private static Connection conn = null;
    private static final String URL = "jdbc:postgresql://localhost:5432/fiasupport";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    public static Connection conectar() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Conexión exitosa a PostgreSQL");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar con la base de datos");
            e.printStackTrace();
        }
        return conn;
    }
    
}
