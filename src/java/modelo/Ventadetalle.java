/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author emanu
 */
public class Ventadetalle {
    private int id_venta_detalle;
    private int id_venta;
    private int id_producto;
    private int cantidad;   
    private double precio_unitario;
    Conexion cn;
    
    public Ventadetalle(){}
    public Ventadetalle(int id_venta_detalle, int id_venta, int id_producto, int cantidad, double precio_unitario) {
        this.id_venta_detalle = id_venta_detalle;
        this.id_venta = id_venta;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
    }

    public int getId_venta_detalle() {
        return id_venta_detalle;
    }

    public void setId_venta_detalle(int id_venta_detalle) {
        this.id_venta_detalle = id_venta_detalle;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }
    
    
    public int agregar() {
        int retorno = 0;
        try {
            
            PreparedStatement parametro;
            String query = "INSERT INTO ventas_detalle (id_Venta, id_Producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
            parametro = cn.conexionBD.prepareStatement(query);
            parametro.setInt(1, this.getId_venta());
            parametro.setInt(2, this.getId_producto());
            parametro.setInt(3, this.getCantidad());
            parametro.setDouble(4, this.getPrecio_unitario());
            retorno = parametro.executeUpdate(); 
            cn.cerrar_conexion();
            
        } catch (SQLException ex) {
            System.out.println("Algo salió mal: " + ex.getMessage());
            retorno = 0;
        }
        return retorno;
    }    
}
