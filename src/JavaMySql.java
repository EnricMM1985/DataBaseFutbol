/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class JavaMySql {

    public static void main(String[] args) {
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
            Connection conn = DriverManager.getConnection(conex, "root", "");

            Statement statement = conn.createStatement();

            String selTable = "select `CodEq`,`Equipo`,`Ciudad`,`Estadio` from equipos";

            statement.execute(selTable);
            ResultSet resultSet = statement.getResultSet();
            while ((resultSet != null) && (resultSet.next())) {
                System.out.println(resultSet.getString(1) + " : " + resultSet.getString(2) + " : " + resultSet.getString(3) + " : " + resultSet.getString(4));
            }
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
    }
}





