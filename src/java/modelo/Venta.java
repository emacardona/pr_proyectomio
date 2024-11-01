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
    
    public class Venta {
    private int id_venta;
    private int no_factura;
    private String serie;
    private String fecha_factura;
    private int id_cliente;
    private int id_empleado;
    private String fecha_ingreso;
    Conexion cn;

    public Venta(){}

    public Venta(int id_venta, int no_factura, String serie, String fecha_factura, int id_cliente, int id_empleado, String fecha_ingreso) {
        this.id_venta = id_venta;
        this.no_factura = no_factura;
        this.serie = serie;
        this.fecha_factura = fecha_factura;
        this.id_cliente = id_cliente;
        this.id_empleado = id_empleado;
        this.fecha_ingreso = fecha_ingreso;
    }
    
    
    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public int getNo_factura() {
        return no_factura;
    }

    public void setNo_factura(int no_factura) {
        this.no_factura = no_factura;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getFecha_factura() {
        return fecha_factura;
    }

    public void setFecha_factura(String fecha_factura) {
        this.fecha_factura = fecha_factura;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }
    
    
    public int obtenerUltimoNum() {
        int ultimoNumero = 0;
        try {
            PreparedStatement parametro;
            cn = new Conexion();
            cn.abrir_conexion();

            String query = "SELECT no_factura FROM ventas ORDER BY no_factura DESC LIMIT 1;";
            parametro = cn.conexionBD.prepareStatement(query);
            ResultSet rs = parametro.executeQuery();

            if (rs.next()) {
                ultimoNumero = rs.getInt("no_factura");
            }

            cn.cerrar_conexion();
        } catch (SQLException ex) {
            System.out.println("Error al obtener último número de orden: " + ex.getMessage());
        }
        return ultimoNumero;
    }
    
    
    public int agregar() {
        int retorno = 0;
        try {
            PreparedStatement parametro;
            cn = new Conexion();
            cn.abrir_conexion();

            // Consulta SQL para insertar una nueva compra
            String query = "INSERT INTO ventas (no_factura,serie,fecha_factura,id_cliente,id_empleado,fecha_ingreso) VALUES (?, ?, ?, ?, ?, ?)";
            parametro = cn.conexionBD.prepareStatement(query);

            parametro.setInt(1, no_factura);
            parametro.setString(2, serie);
            parametro.setString(3, fecha_factura);
            parametro.setInt(4, id_cliente);
            parametro.setInt(5, id_empleado);
            parametro.setString(6, fecha_ingreso);
            
            retorno = parametro.executeUpdate(); // Ejecutar la consulta
            cn.cerrar_conexion();
        } catch (SQLException ex) {
            System.out.println("Algo salió mal: " + ex.getMessage());
            retorno = 0;
        }
        return retorno;
    }
    
    
   public DefaultTableModel leer() {
    DefaultTableModel tabla = new DefaultTableModel();
    try {
        cn = new Conexion();
        cn.abrir_conexion();
        
        String query = "SELECT v.id_Venta, v.no_factura, v.serie, v.fecha_factura, " +
                       "c.id_Cliente, e.id_Empleado, v.fecha_ingreso, pr.id_producto, pr.producto , vd.cantidad, vd.precio_unitario " +
                       "FROM ventas AS v " +
                       "INNER JOIN clientes AS c ON v.id_Cliente = c.id_Cliente " +
                       "INNER JOIN empleados AS e ON v.id_Empleado = e.id_Empleado " +
                       "INNER JOIN ventas_detalle AS vd ON v.id_Venta = vd.id_Venta " +
                       "INNER JOIN productos AS pr ON vd.id_producto = pr.id_producto " +
                       "ORDER BY v.id_Venta ASC;"; // Asegúrate de ordenar por no_orden_compra
        ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);

        String encabezado[] = {"ID Venta", "Número de Factura", "Serie", "Fecha de Factura", "ID Cliente", "ID Empleado", 
                               "Fecha de Ingreso", "ID Producto", "Producto", "Cantidad", "Precio Unitario"};
        tabla.setColumnIdentifiers(encabezado);
        
        // Datos
         while (consulta.next()) {
            Object[] datos = new Object[11];
            datos[0] = consulta.getInt("id_venta");
            datos[1] = consulta.getString("no_factura");
            datos[2] = consulta.getString("serie");
            datos[3] = consulta.getString("fecha_factura"); 
            datos[4] = consulta.getString("id_cliente"); 
            datos[5] = consulta.getString("id_empleado");
            datos[6] = consulta.getInt("id_producto");
            datos[7] = consulta.getString("producto"); 
            datos[8] = consulta.getInt("cantidad");  
            datos[9] = consulta.getDouble("precio_unitario");
            datos[10] = consulta.getString("fecha_ingreso"); 
            tabla.addRow(datos);
        }
        
        cn.cerrar_conexion();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return tabla;
}
   
   public int actualizar() {
    int retorno = 0;
    try {
        PreparedStatement parametro;
        cn = new Conexion();
        cn.abrir_conexion();

        // Consulta SQL para actualizar una compra
        String query = "UPDATE ventas SET serie= ?, fecha_factura = ?, id_cliente = ?, id_empleado = ?, fecha_ingreso = ? WHERE id_venta = ?;";
        parametro = cn.conexionBD.prepareStatement(query);

        // Establecer los parámetros
        parametro.setString(1, this.serie);
        parametro.setString(2, this.fecha_factura);
        parametro.setInt(3, this.id_cliente);
        parametro.setInt(4, this.id_empleado);
        parametro.setString(5, this.fecha_ingreso);
        parametro.setInt(6, this.id_venta); 

        retorno = parametro.executeUpdate(); // Ejecutar la consulta
        cn.cerrar_conexion();
    } catch (SQLException ex) {
        System.out.println("Algo salió mal: " + ex.getMessage());
        retorno = 0;
    }
    return retorno;
}
   
   public int eliminar() {
    int retorno = 0;
    try {
        PreparedStatement parametro;
        cn = new Conexion();
        cn.abrir_conexion();
        
        // Consulta SQL para eliminar una compra
        String query = "DELETE FROM ventas WHERE id_venta = ?;";
        parametro = cn.conexionBD.prepareStatement(query);
        
        // Establecer el parámetro
        parametro.setInt(1, this.getId_venta()); // Asegúrate de que el ID esté establecido
        
        retorno = parametro.executeUpdate(); // Ejecutar la consulta
        cn.cerrar_conexion();
    } catch (SQLException ex) {
        System.out.println("Error al borrar: " + ex.getMessage());
    }
    return retorno;
}

    

      
}
