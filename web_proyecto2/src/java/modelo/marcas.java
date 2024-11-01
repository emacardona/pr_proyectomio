/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
/**
 *
 * @author pc
 */
public class marcas {
    private int id_Marca;
    private String marca;
    private Conexion cn;

    public marcas(){}
    public marcas(int id_Marca, String marca) {
        this.id_Marca = id_Marca;
        this.marca = marca;
    }

    
    
    
    public int getId_Marca() {
        return id_Marca;
    }

    public void setId_Marca(int id_Marca) {
        this.id_Marca = id_Marca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public HashMap<String, String> drop_Marca() {
    HashMap<String, String> drop = new HashMap<>();
    try {
        // Establecemos la conexión
        cn = new Conexion();
        
        // Consulta SQL para obtener las marcas
        String query = "SELECT id_Marca as id, marca FROM db_tiendaa.marcas;";
        cn.abrir_conexion();
        
        // Ejecutamos la consulta
        ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
        
     
        while (consulta.next()) {
            drop.put(consulta.getString("id"), consulta.getString("marca"));
        }
        
        // Cerramos la conexión
        cn.cerrar_conexion();
    } catch (SQLException ex) {
         System.out.println("Error al agregar producto: " + ex.getMessage());
    }
    
    return drop;
}
    
}
