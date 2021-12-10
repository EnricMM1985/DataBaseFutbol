/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.sql.*;

public class DatabaseFutbol {
    private Connection conn;

    public DatabaseFutbol() {
        try {
            //Para el Driver mysql-connector-java-5.1.47.jar
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
            //String conex = "jdbc:mysql://localhost:3306/futbol_mysql";
            //Connection conn=DriverManager.getConnection(conex,"root",""); 

            //Para el Driver mysql-connector-java-8.0.15.jar
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String conex = "jdbc:mysql://localhost/futbol_mysql?useUnicode=true&"
                    + "useJDBCCompliantTimezoneShift=true&"
                    + "useLegacyDatetimeCode=false&"
                    + "serverTimezone=UTC";
            conn = DriverManager.getConnection(conex, "root", "");

        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Lo sentimos, la base de datos no se encuentra");
        }
    }

    public ResultSet consultaSelect(String sql) {
        ResultSet resultSet = null;
        try {
            Statement statement = conn.createStatement();
            statement.execute(sql);
            resultSet = statement.getResultSet();
        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        }
        return resultSet;
    }

    public boolean inserta(String equipo, String ciudad, String estadio, String quiniela) {
        boolean haSidoInsertado = true;
        try {
            String sql = " INSERT INTO Equipos (equipo, ciudad, estadio, nomquinlfp ) values ('"
                    + equipo + "','" + ciudad + "','" + estadio + "','" + quiniela + "');";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            haSidoInsertado = false;
        }
        return haSidoInsertado;
    }

    public boolean elimina(String equipo) {
        boolean haSidoEliminado = true;
        try {
            String sql = " DELETE from Equipos WHERE codeq=" + equipo;
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            haSidoEliminado = false;
        }
        return haSidoEliminado;
    }
}




