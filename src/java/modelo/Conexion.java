/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
    /**
 *
 * @author emanu
 */
public class Conexion {
    public Connection conexionBD;
    private final String puerto = "3306";
    private final String bd = "bd_proyecto";
    private final String urlConexion = String.format("jdbc:mysql://localhost:%s/%s?serverTimezone=UTC", puerto, bd);
    private final String usuario = "root";
    private final String contra = "Neto45328341";
    private final String jdbc = "com.mysql.cj.jdbc.Driver";
    
    
    public void abrir_conexion() {
        try {
            Class.forName(jdbc);
            conexionBD = DriverManager.getConnection(urlConexion, usuario, contra);
            System.out.println("Conexion Exitosa...");
        } catch(ClassNotFoundException | SQLException ex){
            System.out.println("algo salio mal: ( " + ex.getMessage());
    }
    }
     
    public void cerrar_conexion(){
        try {
            conexionBD.close();

        } catch(SQLException ex){
            System.out.println("algo salio mal: ( " + ex.getMessage());
    }
  }
}
