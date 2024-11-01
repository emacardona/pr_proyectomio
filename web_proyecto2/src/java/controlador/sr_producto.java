/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Marca;

public class sr_producto extends HttpServlet {

    Marca marca;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Validar parámetros requeridos
            try {
                String producto = request.getParameter("txt_producto");
                int idMarca = Integer.parseInt(request.getParameter("drop_marca"));
                String descripcion = request.getParameter("txt_descripcion");
                String imagen = request.getParameter("txt_imagen"); 
                double precioCosto = Double.parseDouble(request.getParameter("txt_precio_costo"));
                double precioVenta = Double.parseDouble(request.getParameter("txt_precio_venta"));
                int existencia = Integer.parseInt(request.getParameter("txt_existencia"));
                String fechaIngreso = request.getParameter("txt_fecha_ingreso");
                int id = Integer.parseInt(request.getParameter("txt_id")); // Obtener ID

                // Crear instancia de Marca
                marca = new Marca(producto, idMarca, id, existencia, descripcion, imagen, fechaIngreso, precioCosto, precioVenta);

                // Verificar qué botón fue presionado y ejecutar la acción correspondiente
                if (request.getParameter("btn_agregar") != null) {
                    agregarProducto(response, out);
                } else if (request.getParameter("btn_modificar") != null) {
                    modificarProducto(response, out);
                } else if (request.getParameter("btn_eliminar") != null) {
                    eliminarProducto(response, out);
                } else {
                    out.println("<h1>Acción no válida.</h1>");
                    out.println("<a href='index.jsp'>Regresar</a>");
                }

            } catch (NumberFormatException e) {
                out.println("<h1>Error en los parámetros: " + e.getMessage() + "</h1>");
                out.println("<a href='index.jsp'>Regresar</a>");
            }
        }
    }

    private void agregarProducto(HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            if (marca.agregar() > 0) {
                response.sendRedirect("index.jsp");
            } else {
                out.println("<h1>Error al agregar producto.</h1>");
                out.println("<a href='index.jsp'>Regresar</a>");
            }
        } catch (Exception e) {
            out.println("<h1>Error al agregar producto: " + e.getMessage() + "</h1>");
            out.println("<a href='index.jsp'>Regresar</a>");
        }
    }

    private void modificarProducto(HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            if (marca.modificar() > 0) {
                response.sendRedirect("index.jsp");
            } else {
                out.println("<h1>Error al modificar producto.</h1>");
                out.println("<a href='index.jsp'>Regresar</a>");
            }
        } catch (Exception e) {
            out.println("<h1>Error al modificar producto: " + e.getMessage() + "</h1>");
            out.println("<a href='index.jsp'>Regresar</a>");
        }
    }

    private void eliminarProducto(HttpServletResponse response, PrintWriter out) throws IOException {
        try {
            if (marca.eliminar() > 0) {
                response.sendRedirect("index.jsp");
            } else {
                out.println("<h1>Error al eliminar producto.</h1>");
                out.println("<a href='index.jsp'>Regresar</a>");
            }
        } catch (Exception e) {
            out.println("<h1>Error al eliminar producto: " + e.getMessage() + "</h1>");
            out.println("<a href='index.jsp'>Regresar</a>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para gestión de productos";
    }
}
