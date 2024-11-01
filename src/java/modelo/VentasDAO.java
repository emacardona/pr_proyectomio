/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import modelo.Venta;
import modelo.Ventadetalle;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author emanu
 */
public class VentasDAO {
    Conexion cn;
    
    public VentasDAO() {
        cn = new Conexion(); // Inicializa tu conexión aquí
        cn.abrir_conexion();
    }
    
    public Venta obtenerVentaPorId(int idVenta) {
    Venta venta = null;
    try {
        String query = "SELECT * FROM ventas WHERE id_Venta = ?"; // Cambiar 'compras' por 'ventas'
        PreparedStatement parametro = cn.conexionBD.prepareStatement(query);
        parametro.setInt(1, idVenta);
        ResultSet rs = parametro.executeQuery();

        if (rs.next()) {
            venta = new Venta(
                rs.getInt("id_venta"),
                rs.getInt("no_factura"),
                rs.getString("serie"),
                rs.getString("fecha_factura"),
                rs.getInt("id_cliente"),
                rs.getInt("id_empleado"),
                rs.getString("fecha_ingreso")
            );
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener la venta: " + ex.getMessage());
    }
    return venta; // Retorna el objeto Compra o null si no se encontró
}
    
    public void eliminarDetallesPorIdVenta(int idVenta) {
    try {
        String query = "DELETE FROM ventas_detalle WHERE id_Venta = ?";
        PreparedStatement parametro = cn.conexionBD.prepareStatement(query);
        parametro.setInt(1, idVenta);
        parametro.executeUpdate(); // Ejecutar la consulta
    } catch (SQLException ex) {
        System.out.println("Error al eliminar detalles: " + ex.getMessage());
    }
}
    
   public void agregarVentaYDetalles(Venta venta, List<Ventadetalle> detalles) throws SQLException {
    PreparedStatement parametroVenta = null;
    PreparedStatement parametroDetalle = null;
    PreparedStatement parametroProducto = null;
    PreparedStatement parametroExistencia = null;

    try {
        cn = new Conexion(); 
        cn.abrir_conexion(); 
        cn.conexionBD.setAutoCommit(false); // Iniciar transacción

        // 1. Insertar en la tabla ventas
        String queryVenta = "INSERT INTO ventas (no_factura, serie, fecha_factura, id_cliente, id_empleado, fecha_ingreso) VALUES (?, ?, ?, ?, ?, ?)";
        parametroVenta = cn.conexionBD.prepareStatement(queryVenta, PreparedStatement.RETURN_GENERATED_KEYS);
        
        parametroVenta.setInt(1, venta.getNo_factura());
        parametroVenta.setString(2, venta.getSerie());
        parametroVenta.setString(3, venta.getFecha_factura());
        parametroVenta.setInt(4, venta.getId_cliente());
        parametroVenta.setInt(5, venta.getId_empleado());
        parametroVenta.setString(6, venta.getFecha_ingreso());

        int filasAfectadas = parametroVenta.executeUpdate();
        if (filasAfectadas == 0) {
            throw new SQLException("No se pudo insertar la venta.");
        }

        // Obtener el ID de la venta recién creada
        ResultSet generatedKeys = parametroVenta.getGeneratedKeys();
        int idNuevaVenta = 0;
        if (generatedKeys.next()) {
            idNuevaVenta = generatedKeys.getInt(1);
        }

        if (detalles.isEmpty()) {
            System.out.println("No se encontraron detalles para insertar.");
            cn.conexionBD.rollback();
            return;
        }
        
        // 2. Insertar en la tabla ventas_detalle
        String queryDetalle = "INSERT INTO ventas_detalle (id_Venta, id_Producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        parametroDetalle = cn.conexionBD.prepareStatement(queryDetalle);

        for (Ventadetalle detalle : detalles) {
            System.out.println("Insertando detalle: ID Venta: " + idNuevaVenta + 
                               ", ID Producto: " + detalle.getId_producto() + 
                               ", Cantidad: " + detalle.getCantidad() + 
                               ", Precio Unitario: " + detalle.getPrecio_unitario());

            // Verificar existencia del producto
            String queryExistencia = "SELECT existencia FROM productos WHERE id_producto = ?";
            parametroExistencia = cn.conexionBD.prepareStatement(queryExistencia);
            parametroExistencia.setInt(1, detalle.getId_producto());
            ResultSet rsExistencia = parametroExistencia.executeQuery();

            if (rsExistencia.next()) {
                int existenciaActual = rsExistencia.getInt("existencia");
                if (existenciaActual < detalle.getCantidad()) {
                    throw new SQLException("No hay suficientes existencias para el producto con ID: " + detalle.getId_producto());
                }
            } else {
                throw new SQLException("Producto no encontrado con ID: " + detalle.getId_producto());
            }

            // Insertar detalle de la venta
            parametroDetalle.setInt(1, idNuevaVenta);
            parametroDetalle.setInt(2, detalle.getId_producto());
            parametroDetalle.setInt(3, detalle.getCantidad());
            parametroDetalle.setDouble(4, detalle.getPrecio_unitario());

            int filasDetallesAfectadas = parametroDetalle.executeUpdate();
            if (filasDetallesAfectadas == 0) {
                throw new SQLException("No se pudo insertar el detalle de la venta.");
            }

            // 3. Actualizar el saldo del producto
            String queryProducto = "UPDATE productos SET existencia = existencia - ? WHERE id_producto = ?";
            parametroProducto = cn.conexionBD.prepareStatement(queryProducto);
            
            parametroProducto.setInt(1, detalle.getCantidad()); // Disminuir existencia
            parametroProducto.setInt(2, detalle.getId_producto()); // ID del producto

            int filasProductoAfectadas = parametroProducto.executeUpdate();
            if (filasProductoAfectadas == 0) {
                throw new SQLException("No se pudo actualizar la existencia del producto con ID: " + detalle.getId_producto());
            }
        }

        cn.conexionBD.commit(); // Confirmar transacción

    } catch (SQLException ex) {
        if (cn != null) {
            cn.conexionBD.rollback(); // Revertir cambios en caso de error
        }
        System.out.println("Error al agregar venta y detalles: " + ex.getMessage());
    } finally {
       try {
           if (parametroVenta != null) {
               parametroVenta.close();
           }
           if (parametroDetalle != null) {
               parametroDetalle.close();
           }
           if (parametroProducto != null) {
               parametroProducto.close();
           }
           if (parametroExistencia != null) {
               parametroExistencia.close();
           }
           if (cn != null) {
               cn.cerrar_conexion(); 
           }
       } catch (SQLException e) {
           System.out.println("Error al cerrar recursos: " + e.getMessage());
       }
    }
}
    
    
    public void actualizarVentaYDetalles(Venta venta, List<Ventadetalle> detalles) {
    PreparedStatement parametroVenta = null;
    PreparedStatement parametroDetalle = null;

    try {
        cn = new Conexion(); 
        cn.abrir_conexion(); 

        // 1. Actualizar en la tabla ventas
        String queryVenta = "UPDATE ventas SET no_factura = ?, serie = ?, fecha_factura = ?, id_cliente = ?, id_empleado = ?, fecha_ingreso = ? WHERE id_Venta = ?";
        parametroVenta = cn.conexionBD.prepareStatement(queryVenta);
        
        parametroVenta.setInt(1, venta.getNo_factura());
        parametroVenta.setString(2, venta.getSerie());
        parametroVenta.setString(3, venta.getFecha_factura());
        parametroVenta.setInt(4, venta.getId_cliente()); // Asegúrate de establecer el ID del cliente
        parametroVenta.setInt(5, venta.getId_empleado()); // Asegúrate de establecer el ID del empleado
        parametroVenta.setString(6, venta.getFecha_ingreso());
        parametroVenta.setInt(7, venta.getId_venta()); // Establecer el ID para actualizar

        int filasActualizadas = parametroVenta.executeUpdate();
        
        if (filasActualizadas == 0) {
            throw new SQLException("No se pudo actualizar la venta.");
        }

        // 2. Eliminar los detalles existentes antes de agregar los nuevos
        eliminarDetallesPorIdVenta(venta.getId_venta());

        // 3. Insertar los nuevos detalles
        String queryDetalle = "INSERT INTO ventas_detalle (id_Venta, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        parametroDetalle = cn.conexionBD.prepareStatement(queryDetalle);

        for (Ventadetalle detalle : detalles) {
            detalle.setId_venta(venta.getId_venta()); 

            parametroDetalle.setInt(1, detalle.getId_venta());
            parametroDetalle.setInt(2, detalle.getId_producto());
            parametroDetalle.setInt(3, detalle.getCantidad());
            parametroDetalle.setDouble(4, detalle.getPrecio_unitario());

            // Ejecutar la consulta de detalle
            parametroDetalle.executeUpdate();
        }

    } catch (SQLException ex) {
        System.out.println("Error al actualizar venta y detalles: " + ex.getMessage());
    } finally {
       try {
           if (parametroVenta != null) {
               parametroVenta.close();
           }
           if (parametroDetalle != null) {
               parametroDetalle.close();
           }
           if (cn != null) {
               cn.cerrar_conexion(); 
           }
       } catch (SQLException e) {
           System.out.println("Error al cerrar recursos: " + e.getMessage());
       }
    }
}
    
    public boolean eliminarVentaYDetalles(int idVenta) {
    PreparedStatement parametroDetalle = null;
    PreparedStatement parametroVenta = null;

    try {
        cn = new Conexion(); // Inicializa la conexión
        cn.abrir_conexion(); // Abre la conexión

        // 1. Eliminar los detalles de la compra
        String queryDetalle = "DELETE FROM ventas_detalle WHERE id_Venta = ?";
        parametroDetalle = cn.conexionBD.prepareStatement(queryDetalle);
        parametroDetalle.setInt(1, idVenta);
        parametroDetalle.executeUpdate(); // Ejecutar la consulta para eliminar detalles

        // 2. Eliminar la compra
        String queryVenta = "DELETE FROM ventas WHERE id_Venta = ?";
        parametroVenta = cn.conexionBD.prepareStatement(queryVenta);
        parametroVenta.setInt(1, idVenta);
        int filasAfectadas = parametroVenta.executeUpdate(); // Ejecutar la consulta para eliminar compra

        return filasAfectadas > 0; // Retorna true si se eliminó al menos una fila

    } catch (SQLException ex) {
        System.out.println("Error al eliminar venta y detalles: " + ex.getMessage());
        return false; // Retorna false si hubo un error
    } finally {
        try {
            if (parametroDetalle != null) {
                parametroDetalle.close();
            }
            if (parametroVenta != null) {
                parametroVenta.close();
            }
            if (cn != null) {
                cn.cerrar_conexion(); // Cierra la conexión
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
}
    
}
