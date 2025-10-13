/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.db.pgConexion;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Méndez
 */
public class PgConexionTest {
    
    @Test
    void canConnectAndSelectOne() throws Exception {
        // Adjust class name if yours is PgConexion (capital P) instead of pgConexion
        pgConexion db = new pgConexion();

        try (Connection cn = db.conectar()) {
            assertNotNull(cn, "Connection should not be null");
            assertFalse(cn.isClosed(), "Connection should be open");

            try (PreparedStatement ps = cn.prepareStatement("SELECT 1");
                 ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next(), "Should return one row");
                assertEquals(1, rs.getInt(1), "SELECT 1 should return 1");
            }
        }
    }
    
}
