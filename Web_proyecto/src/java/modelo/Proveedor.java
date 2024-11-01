/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.ResultSet;
import java.util.HashMap;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author pc
 */
public class Proveedor extends Persona{
    private String proveedor; 
    private Conexion cn;  //conexión a la base de datos

    
    public Proveedor(){} 
    // Constructor y atributos heredados de la clase Persona
    public Proveedor(String proveedor, int id, String nit, String direccion, String telefono) {
        super(id, nit, direccion, telefono);
        this.proveedor = proveedor;
    }

    // Getters y setters 
    
    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    


// Método para leer los registros de proveedores desde la base de datos
public DefaultTableModel leer() {
    DefaultTableModel tabla = new DefaultTableModel();
    try {
        // Crear la conexión a la base de datos
        cn = new Conexion();
        cn.abrir_conexion();
        
        // Consulta para leer los proveedores
        String query = "SELECT id_Proveedor as id, proveedor, nit, direccion, telefono FROM proveedores;"; 
        
        ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
        
        // Definir encabezados de la tabla
        String encabezado[] = { "id", "Proveedor", "NIT", "Dirección", "Teléfono" };
        tabla.setColumnIdentifiers(encabezado);
        
        // almacena los datos de cada fila
        String datos[] = new String[5]; 
        
        // Llenar la tabla con los datos obtenidos
        while (consulta.next()) {
            datos[0] = consulta.getString("id"); // Capturamos el id_Proveedor
            datos[1] = consulta.getString("proveedor");
            datos[2] = consulta.getString("nit");
            datos[3] = consulta.getString("direccion");
            datos[4] = consulta.getString("telefono");
            tabla.addRow(datos);
        }
        
        // Cerrar la conexión
        cn.cerrar_conexion();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    
    return tabla; 
}

    

     // Método para agregar un nuevo proveedor en la base de datos
    public int agregar (){
        int retorno = 0; 
        try{
            
            PreparedStatement parametro;
            cn = new Conexion ();
            String query ="INSERT INTO proveedores(proveedor,nit,direccion,telefono)VALUES(?,?,?,?);";
            cn.abrir_conexion();
            parametro = (PreparedStatement )cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getProveedor());
            parametro.setString(2, getNit());
            parametro.setString(3, getDireccion());
            parametro.setString(4, getTelefono());
            
            retorno = parametro.executeUpdate();
            
            
            
            cn.cerrar_conexion();
        }catch(SQLException ex){
            
            System.out.println(ex.getMessage());
            retorno = 0; 
        }
        return retorno;
        
    }
    
   public int modificar  (){
        int retorno = 0; 
        try{
            
            PreparedStatement parametro;
            cn = new Conexion ();
            String query ="update proveedores set proveedor=?,nit=?,direccion=?,telefono=? where id_Proveedor=?;";
            cn.abrir_conexion();
            parametro = (PreparedStatement )cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getProveedor());
            parametro.setString(2, getNit());
            parametro.setString(3, getDireccion());
            parametro.setString(4, getTelefono());
            parametro.setInt(5, getId());
            retorno = parametro.executeUpdate();
            
            
            
            cn.cerrar_conexion();
        }catch(SQLException ex){
            
            System.out.println(ex.getMessage());
            retorno = 0; 
        }
        return retorno;
        
    }
   public int eliminar (){
        int retorno = 0; 
        try{
            
            PreparedStatement parametro;
            cn = new Conexion ();
            String query ="delete from proveedores where id_Proveedor=?;";
            cn.abrir_conexion();
            parametro = (PreparedStatement )cn.conexionBD.prepareStatement(query);
            parametro.setInt(1, getId());
            retorno = parametro.executeUpdate();
            
            
            
            cn.cerrar_conexion();
        }catch(SQLException ex){
            
            System.out.println(ex.getMessage());
            retorno = 0; 
        }
        return retorno;
        
    }
}
