package com.jarroba.scraping.Scraping_jarroba_java;

import java.sql.*;

public class MySQL_Test {
	public static void main(String[] args) throws SQLException {
        System.out.println("INICIO DE EJECUCIÓN.");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/webscrap?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = conexion.createStatement();
            st.executeUpdate("DROP TABLE IF EXISTS personal;");
            st.executeUpdate("CREATE TABLE personal (`Identificador` int(11) NOT NULL AUTO_INCREMENT, `Nombre` varchar(50) NOT NULL, `Apellidos` varchar(50) NOT NULL, `Telefono` varchar(9) DEFAULT NULL, `Email` varchar(60) DEFAULT NULL, PRIMARY KEY (`Identificador`));");
            st.executeUpdate("INSERT INTO personal (`Identificador`, `Nombre`, `Apellidos`, `Telefono`, `Email`) VALUES (1, 'José', 'Martínez López', '968112233', 'jose@martinezlopez.com'), (2, 'María', 'Gómez Muñoz', '911876876', 'maria@gomezoliver.com'), (3, 'Juan', 'Sánchez Fernández', '922111333', 'juan@sanchezfernandez.com'), (4, 'Ana', 'Murcia Rodríguez', '950999888', 'ana@murciarodriguez.com');");
            ResultSet rs = st.executeQuery("SELECT * FROM personal;");
 
            if (rs != null) {
                System.out.println("El listado de persona es el siguiente:");
 
                while (rs.next()) {
                    System.out.println("  ID: " + rs.getObject("Identificador"));
                    System.out.println("  Nombre completo: " + rs.getObject("Nombre") + " " + rs.getObject("Apellidos"));
                    System.out.println("  Contacto: " + rs.getObject("Telefono") + " " + rs.getObject("Email"));
                    System.out.println("- ");
                }
                rs.close();
            }
            st.close();
 
        }
        catch(SQLException s)
        {
            System.out.println("Error: SQL.");
            System.out.println("SQLException: " + s.getMessage());
        }
        catch(Exception s)
        {
            System.out.println("Error: Varios.");
            System.out.println("SQLException: " + s.getMessage());
        }
        System.out.println("FIN DE EJECUCIÓN.");
    }
}
