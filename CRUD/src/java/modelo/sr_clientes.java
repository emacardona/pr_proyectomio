/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package modelo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@WebServlet("/sr_clientes")


public class sr_clientes extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los datos del formulario
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String nit = request.getParameter("nit");
        String generoStr = request.getParameter("genero");
        int genero = Integer.parseInt(generoStr); // Convertir a int para la base de datos
        String telefono = request.getParameter("telefono");
        String correo = request.getParameter("correo_electronico");
        String fechaIngreso = request.getParameter("fecha_ingreso");
        String accion = request.getParameter("accion");
        String index = request.getParameter("index");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConexionBD.obtenerConexion();

            if ("Registrar Cliente".equals(accion)) {
                // Insertar cliente en la base de datos
                String sql = "INSERT INTO clientes (nombres, apellidos, NIT, genero, telefono, correo_electronico, fecha_ingreso) VALUES (?, ?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, nombres);
                stmt.setString(2, apellidos);
                stmt.setString(3, nit);
                stmt.setInt(4, genero); // Usamos setInt
                stmt.setString(5, telefono);
                stmt.setString(6, correo);
                stmt.setString(7, fechaIngreso);
                stmt.executeUpdate();
                request.setAttribute("mensaje", "Cliente registrado exitosamente.");

            } else if ("Modificar Cliente".equals(accion)) {
                if (index != null && !index.isEmpty()) {
                    // Actualizar cliente
                    String sql = "UPDATE clientes SET nombres=?, apellidos=?, NIT=?, genero=?, telefono=?, correo_electronico=?, fecha_ingreso=? WHERE id_Cliente=?";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, nombres);
                    stmt.setString(2, apellidos);
                    stmt.setString(3, nit);
                    stmt.setInt(4, genero); // Usamos setInt
                    stmt.setString(5, telefono);
                    stmt.setString(6, correo);
                    stmt.setString(7, fechaIngreso);
                    stmt.setInt(8, Integer.parseInt(index)); // Asegurarse de que index se convierte correctamente
                    stmt.executeUpdate();
                    request.setAttribute("mensaje", "Cliente modificado exitosamente.");
                } else {
                    request.setAttribute("mensaje", "No se especificó el cliente a modificar.");
                }
            } else if ("Eliminar Cliente".equals(accion)) {
                if (index != null && !index.isEmpty()) {
                    // Eliminar cliente
                    String sql = "DELETE FROM clientes WHERE id_Cliente=?";
                    stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, Integer.parseInt(index));
                    stmt.executeUpdate();
                    request.setAttribute("mensaje", "Cliente eliminado exitosamente.");
                } else {
                    request.setAttribute("mensaje", "No se especificó el cliente a eliminar.");
                }
            } else {
                request.setAttribute("mensaje", "Acción no válida.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al interactuar con la base de datos: " + e.getMessage());
        } finally {
            ConexionBD.cerrarConexion(conn);
        }

        // Redirigir de nuevo a la página JSP para mostrar el resultado
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}

