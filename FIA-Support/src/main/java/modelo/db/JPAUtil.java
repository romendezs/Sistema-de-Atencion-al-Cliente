/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Méndez
 */
public class JPAUtil {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/fiasupport";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "admin";

    private static final EntityManagerFactory EMF;

    static {
        ensureAdministradorIdColumn();
        EMF = Persistence.createEntityManagerFactory("fiasupportPU");
    }

    private JPAUtil() {
    }

    public static EntityManager em() {
        return EMF.createEntityManager();
    }

    public static void close() {
        EMF.close();
    }

    private static void ensureAdministradorIdColumn() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            boolean hasIdAdmin = columnExists(connection, "administrador", "id_admin");
            if (hasIdAdmin) {
                return;
            }
            boolean hasLegacyId = columnExists(connection, "administrador", "id");
            if (hasLegacyId) {
                renameColumn(connection, "administrador", "id", "id_admin");
                return;
            }
            throw new IllegalStateException("La tabla administrador no tiene columnas id_admin ni id");
        } catch (SQLException ex) {
            throw new ExceptionInInitializerError(
                    new IllegalStateException("Error verificando la columna id_admin de administrador", ex));
        }
    }

    private static boolean columnExists(Connection connection, String table, String column) throws SQLException {
        String sql = "SELECT 1 FROM information_schema.columns "
                + "WHERE table_schema = current_schema() AND table_name = ? AND column_name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, table);
            ps.setString(2, column);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private static void renameColumn(Connection connection, String table, String from, String to) throws SQLException {
        String sql = String.format("ALTER TABLE %s RENAME COLUMN %s TO %s", table, from, to);
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }
}
