package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
private static final String URL = "jdbc:postgresql://localhost:5432/fiasupport";
private static final String USER = "postgres";
private static final String PASS = "admin";


    public static Connection getConexion() throws SQLException {
    try {
        Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
        throw new SQLException("PostgreSQL Driver not found", e);
    }

    System.out.println("Intentando conectar a: " + URL + " con usuario: " + USER);
    Connection cn = DriverManager.getConnection(URL, USER, PASS);
    System.out.println("✓ Conexión OK!");
    return cn;
}
}
