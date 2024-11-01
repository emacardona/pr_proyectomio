/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import modelo.Conexion;
import modelo.Persona;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author DELL
 */
public class Cliente extends Persona {
    private int id;
    private String nit, correo_elec;
    Conexion cn;
    
    public void Cliente(){}

    public Cliente(){}
    public Cliente(int id, String nombres, String apellidos, String nit, boolean genero, String telefono, String correo_elec, String fecha_ingreso) {
        super(nombres, apellidos, telefono, genero, fecha_ingreso);
        this.id = id;
        this.nit = nit;
        this.correo_elec = correo_elec;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getCorreo_elec() {
        return correo_elec;
    }

    public void setCorreo_elec(String correo_elec) {
        this.correo_elec = correo_elec;
    }
    
    @Override
    public int agregar() {
        int retorno = 0;
        try{
            PreparedStatement parametro;
            cn= new Conexion();
            cn.abrir_conexion();
            String query = "INSERT INTO clientes(nombres, apellidos, nit, genero, telefono, correo_electronico, fecha_ingreso) VALUES(?, ?, ?, ?, ?, ?, ?);";
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getNombres());
            parametro.setString(2, getApellidos());
            parametro.setString(3, getNit());
            parametro.setBoolean(4, getGenero());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getCorreo_elec());
            parametro.setString(7, getFecha_ingreso());
            retorno = parametro.executeUpdate();
            cn.cerrar_conexion();
        } catch(SQLException ex){
            System.out.println("Algo salio mal : " + ex.getMessage());
            retorno = 0;
        }
        return retorno;
    }
    
    
    public DefaultTableModel leer(){
    DefaultTableModel tabla = new DefaultTableModel();
    try {
        cn = new Conexion();
        cn.abrir_conexion();
        String query = "SELECT * FROM clientes;";
        ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
        String encabezado[] = {"id", "nombres", "apellidos", "nit", "genero", "telefono", "correo_electronico", "fecha_ingreso"};
        tabla.setColumnIdentifiers(encabezado);
        String datos[] = new String[8];  // Cambiado a 8 elementos
        while (consulta.next()) {
            datos[0] = consulta.getString("id_cliente");
            datos[1] = consulta.getString("nombres");
            datos[2] = consulta.getString("apellidos");
            datos[3] = consulta.getString("nit");
            datos[4] = consulta.getBoolean("genero") ? "M" : "F";
            datos[5] = consulta.getString("telefono");
            datos[6] = consulta.getString("correo_electronico"); // Corregido a "correo_electronico"
            datos[7] = consulta.getString("fecha_ingreso");
            tabla.addRow(datos);

        }
        cn.cerrar_conexion();
    } catch (SQLException ex) {
        System.out.println("Error en leer " + ex.getMessage());
    }
    return tabla;
}

    
    @Override
    public int actualizar(){
        int retorno = 0;
        try{
            PreparedStatement parametro;
            cn= new Conexion();
            cn.abrir_conexion();
            String query = "UPDATE clientes SET nombres = ?, apellidos = ?, nit = ?, genro = ?, telefono = ?, correo_electronico = ?, fecha_nacimiento = ? WHERE id_clientes = ?;";
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getNombres());
            parametro.setString(2, getApellidos());
            parametro.setString(3, getNit());
            parametro.setBoolean(4, getGenero());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getCorreo_elec());
            parametro.setString(7, getFecha_ingreso());
            retorno = parametro.executeUpdate();
            cn.cerrar_conexion();
        } catch(SQLException ex){
            System.out.println("Algo salio mal : " + ex.getMessage());
            retorno = 0;
        }
        return retorno;
    }
    
    @Override
    public int eliminar(){
        int retorno = 0;
        try{
            PreparedStatement parametro;
            cn= new Conexion();
            cn.abrir_conexion();
            String query = "DELETE FROM clientes WHERE id_cliente = ?;";
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setInt(1, getId());
            retorno = parametro.executeUpdate();
            cn.cerrar_conexion();
        } catch(SQLException ex){
            System.out.println("Error en borrar: " + ex.getMessage());
        }
        return retorno;
    }
}
