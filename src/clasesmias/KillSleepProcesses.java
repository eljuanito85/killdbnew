/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasesmias;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author juani
 */
public class KillSleepProcesses {
    // Configuración de la conexión a la base de datos

    public static void main(String[] args) {

        String jdbc_url = "jdbc:mariadb://localhost/killsleepprocesses";
        String jdbc_user = "root";
        String jdbc_password = "";

        ArchivoTexto txt = new ArchivoTexto();
        String nomArch = "config.txt";

        ArrayList<String> lineas = new ArrayList<>();
        lineas = txt.lineasArch(nomArch);

        int longitud = lineas.size();
        String aux = "";

        File carpResultados = new File("resultados");
        if (!carpResultados.exists()) {
            carpResultados.mkdir();
        }

        String nombreArchResult = "resultados/" + String.valueOf(System.currentTimeMillis()) + ".txt";

        File resultados = new File(nombreArchResult);

        for (int i = 0; i < longitud; i++) {

            aux = lineas.get(i);

            if (aux.startsWith("jdbc:mysql")) {
                String arrayAux[];
                arrayAux = aux.split(", ");

                if (arrayAux.length == 3) {
                    jdbc_url = arrayAux[0];
                    jdbc_user = arrayAux[1];
                    jdbc_password = arrayAux[2];

                    try {
                        // Conexión a la base de datos
                        Connection conn = DriverManager.getConnection(jdbc_url, jdbc_user, jdbc_password);

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
                        System.out.println("Conexiones SLEEP de más de 1:30 h cerradas con éxito.");
                        txt.Escribir(resultados, aux);
                        txt.Escribir(resultados, "Conexiones SLEEP de más de 1:30 h cerradas con éxito.");
                        txt.Escribir(resultados, "--------------------------------------------------------");

                    } catch (Exception e) {
                        e.printStackTrace();
                        txt.Escribir(resultados, aux);
                        txt.Escribir(resultados, "Error " + e);
                        txt.Escribir(resultados, "--------------------------------------------------------");
                    }

                } else {
                    txt.Escribir(resultados, aux);
                    txt.Escribir(resultados, "Error de arrayAux");
                    txt.Escribir(resultados, "--------------------------------------------------------");
                }

            } else {
                if (!aux.equals("")) {
                    txt.Escribir(resultados, aux);
                    txt.Escribir(resultados, "No empieza con jdbc");
                    txt.Escribir(resultados, "--------------------------------------------------------");
                } else {
                    txt.Escribir(resultados, aux);
                    txt.Escribir(resultados, "La línea está vacía");
                    txt.Escribir(resultados, "--------------------------------------------------------");
                }
            }
        }

    }

}
