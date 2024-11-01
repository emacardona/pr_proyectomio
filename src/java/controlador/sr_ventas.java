/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlador;
import modelo.Venta;
import modelo.VentasDAO;
import modelo.Ventadetalle;
import modelo.Marca;
import modelo.Cliente;
import modelo.Empleado;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 import java.util.logging.Level;
 import java.util.logging.Logger;

/**
 *
 * @author emanu
 */
@WebServlet(name = "sr_ventas", urlPatterns = {"/sr_ventas"})
public class sr_ventas extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException , SQLException {
        response.setContentType("text/html;charset=UTF-8");
        handleNuevaVenta(request, response);
    }
           
        
    private void handleNuevaVenta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
    String action = request.getParameter("btn_agregar") != null ? "agregar" :
                    request.getParameter("btn_modificar") != null ? "actualizar" :
                    request.getParameter("btn_eliminar") != null ? "eliminar" : null;
	VentasDAO ventasDAO = new VentasDAO(); // Crear instancia de ComprasDAO
	// Obtener los parámetros de venta

        String serie = request.getParameter("txt_serie");
        String fechaFactura = request.getParameter("txt_fecha_factura");
	String idClienteStr = request.getParameter("txt_id_cliente"); // ID del cliente
        String idEmpleadoStr = request.getParameter("txt_id_empleado"); // ID del empleado
        String fechaIngreso = request.getParameter("txt_fecha_ingreso");
        String idVentaStr = request.getParameter("txt_id_venta"); // ID de la venta, solo en caso de actualización
        
        System.out.println("ID Cliente: " + idClienteStr);
        System.out.println("ID Empleado: " + idEmpleadoStr);
        
	// Validar que los campos requeridos no estén vacíos
	if (serie == null || serie.isEmpty() || fechaFactura == null || fechaFactura.isEmpty() ||
            idClienteStr == null || idClienteStr.isEmpty() || idEmpleadoStr == null || 
            idEmpleadoStr.isEmpty() || fechaIngreso == null || fechaIngreso.isEmpty() ||
            (action.equals("actualizar") && (idVentaStr == null || idVentaStr.isEmpty()))) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

	int idCliente;
        int idEmpleado;
	int idVenta = -1; // Valor por defecto
	try {
		idCliente = Integer.parseInt(idClienteStr);
		idEmpleado = Integer.parseInt(idEmpleadoStr);

		Venta nuevaVenta;
		switch(action) {
			case "agregar":
				Venta ventaInstance = new Venta();
				int nuevoNumeroFactura = ventaInstance.obtenerUltimoNum() + 1;
				// Crear una nueva instancia de Compra
				nuevaVenta = new Venta(nuevoNumeroFactura, nuevoNumeroFactura, serie, fechaFactura, idCliente,idEmpleado,fechaIngreso);
				// Aquí debes recoger los detalles (productos) que se van a agregar
				List <Ventadetalle> detalles = obtenerDetallesDesdeFormulario(request); // Método para obtener detalles
				// Agregar la compra y sus detalles
				ventasDAO.agregarVentaYDetalles(nuevaVenta, detalles); // Llama al nuevo método
				response.sendRedirect("index.jsp");
				break;

			case "actualizar":
				idVenta = Integer.parseInt(idVentaStr); // Obtener el ID de compra para actualizar
				
                                Venta ventaExistente = ventasDAO.obtenerVentaPorId(idVenta);
				if(ventaExistente != null) {
					// Crear una nueva instancia de Compra con los datos actualizados
					nuevaVenta = new Venta(idVenta, ventaExistente.getNo_factura(), serie, fechaFactura, idCliente,idEmpleado,fechaIngreso);
					// Aquí debes recoger los detalles actualizados desde el formulario
					List <Ventadetalle> detallesActualizados = obtenerDetallesDesdeFormulario(request); // Método para obtener detalles actualizados
					// Actualizar la compra y sus detalles
					ventasDAO.actualizarVentaYDetalles(nuevaVenta, detallesActualizados); // Llama al nuevo método
					response.sendRedirect("index.jsp");
				} else {
					response.getWriter().println("<h1>No se encontró la Venta</h1>");
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}
				break;
			case "eliminar":
				idVenta = Integer.parseInt(idVentaStr); // Obtener el ID de compra para eliminar
				if(ventasDAO.eliminarVentaYDetalles(idVenta)) { // Llama al nuevo método
					response.sendRedirect("index.jsp");
				} else {
					response.getWriter().println("<h1>No se pudo eliminar la compra</h1>");
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}
				break;
			default:
				response.sendRedirect("index.jsp");
				break;
		}
	} catch (NumberFormatException e) {
            
		response.sendRedirect("index.jsp");
	}
}
     
    
    
         @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(sr_ventas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(sr_ventas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }


    
    public List<Ventadetalle> obtenerDetallesDesdeFormulario(HttpServletRequest request) {
        List<Ventadetalle> detalles = new ArrayList<>();
        
        String[] idsProductos = request.getParameterValues("id_producto[]");
        String[] cantidades = request.getParameterValues("cantidad[]"); 
        String[] preciosUnitarios = request.getParameterValues("precio_unitario[]");

        // Validar que los datos del formulario se están recuperando correctamente
        System.out.println("ID Productos: " + java.util.Arrays.toString(idsProductos));
        System.out.println("Cantidades: " + java.util.Arrays.toString(cantidades));
        System.out.println("Precios Unitarios: " + java.util.Arrays.toString(preciosUnitarios));

        // Validar que los arreglos tengan el mismo tamaño
        if (idsProductos != null && cantidades != null && preciosUnitarios != null) {
            if (idsProductos.length != cantidades.length || idsProductos.length != preciosUnitarios.length) {
                System.out.println("Los arreglos de detalles tienen tamaños inconsistentes.");
                return detalles; // Retorna la lista vacía si los tamaños no coinciden
            }

            for (int i = 0; i < idsProductos.length; i++) {
                try {
                    Ventadetalle detalle = new Ventadetalle();
                    detalle.setId_producto(Integer.parseInt(idsProductos[i])); // Establecer ID del producto
                    detalle.setCantidad(Integer.parseInt(cantidades[i])); // Establecer cantidad
                    detalle.setPrecio_unitario(Double.parseDouble(preciosUnitarios[i])); // Establecer precio unitario
                    
                    detalles.add(detalle); // Agregar a la lista
                } catch (NumberFormatException e) {
                    System.out.println("Error al parsear un valor en el índice " + i + ": " + e.getMessage());
                }
            }
        }
        
        return detalles; // Devolver la lista de detalles
    }
}