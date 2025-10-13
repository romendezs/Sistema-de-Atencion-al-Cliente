/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.db;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author Méndez
 */
public class TestConexion {
    public static void main(String[] args) {
        System.out.println("🔍 Iniciando prueba de conexión a la base de datos...");

        try {
            // 1️⃣ Crear la fábrica de EntityManagers usando el nombre del persistence.xml
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("fiasupportPU");

            // 2️⃣ Obtener un EntityManager (esto fuerza la conexión)
            EntityManager em = emf.createEntityManager();

            // 3️⃣ Si llega aquí, la conexión es exitosa
            System.out.println("✅ Conexión exitosa a la base de datos mediante Hibernate!");

            // 4️⃣ Cierra los recursos
            em.close();
            emf.close();

        } catch (Exception e) {
            System.out.println("❌ Error al conectar con la base de datos:");
            e.printStackTrace();
        }
    }
}
