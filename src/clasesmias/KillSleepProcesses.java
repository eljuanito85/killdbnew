/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasesmias;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author juani
 */
public class KillSleepProcesses {
    // Configuración de la conexión a la base de datos
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/tu_base_de_datos";
    private static final String JDBC_USER = "tu_usuario";
    private static final String JDBC_PASSWORD = "tu_contraseña";

    public static void main(String[] args) {
        try {
            // Conexión a la base de datos
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            
            Statement stmt = conn.createStatement();

            // Consulta para obtener los procesos SLEEP que llevan más de 5400 segundos
            String query = "SELECT id FROM information_schema.processlist WHERE command = 'Sleep' AND time > 5400";
            ResultSet rs = stmt.executeQuery(query);

            // Ejecutar KILL para cada proceso encontrado
            while (rs.next()) {
                int processId = rs.getInt("id");
                System.out.println("Matando proceso SLEEP con id: " + processId);
                stmt.execute("KILL " + processId);
            }

            // Cerrar la conexión y los recursos
            rs.close();
            stmt.close();
            conn.close();
            System.out.println("Conexiones SLEEP de más de 2 horas matadas con éxito.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
