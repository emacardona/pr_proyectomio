/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package modelo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "sr_marca", urlPatterns = {"/sr_marca"})
public class sr_marca extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            mostrarMarcas(out); // Mostrar marcas en respuesta a la solicitud GET
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        String idMarca = request.getParameter("id_Marca");
        String marca = request.getParameter("marca");

        if (accion != null) {
            switch (accion) {
                case "agregar":
                    if (marca != null && !marca.trim().isEmpty()) {
                        guardarMarcaEnBD(marca);
                    }
                    break;
                case "modificar":
                    if (idMarca != null && marca != null && !marca.trim().isEmpty()) {
                        modificarMarcaEnBD(Integer.parseInt(idMarca), marca);
                    }
                    break;
                case "eliminar":
                    if (idMarca != null) {
                        eliminarMarcaEnBD(Integer.parseInt(idMarca));
                    }
                    break;
            }
        }

        // Responder con la lista actualizada de marcas en formato HTML
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            mostrarMarcas(out); // Mostrar marcas después de la acción
        }
    }

    private void guardarMarcaEnBD(String marcaNombre) {
        String sql = "INSERT INTO marcas (marca) VALUES (?)";
        try (Connection con = new ConexionBD().conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, marcaNombre);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores
        }
    }

    private void modificarMarcaEnBD(int idMarca, String nuevaMarca) {
        String sql = "UPDATE marcas SET marca = ? WHERE id_Marca = ?";
        try (Connection con = new ConexionBD().conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevaMarca);
            ps.setInt(2, idMarca);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores
        }
    }

    private void eliminarMarcaEnBD(int idMarca) {
        String sql = "DELETE FROM marcas WHERE id_Marca = ?";
        try (Connection con = new ConexionBD().conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMarca);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores
        }
    }

    private void mostrarMarcas(PrintWriter out) {
        String sql = "SELECT id_Marca, marca FROM marcas";
        try (Connection con = new ConexionBD().conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            out.println("<table border='1'>");
            out.println("<tr><th>ID Marca</th><th>Marca</th></tr>");
            while (rs.next()) {
                int idMarca = rs.getInt("id_Marca");
                String marca = rs.getString("marca");
                out.println("<tr onclick=\"seleccionarFila('" + idMarca + "', '" + marca + "')\">");
                out.println("<td>" + idMarca + "</td>");
                out.println("<td>" + marca + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        } catch (SQLException e) {
            out.println("<p>Error al obtener las marcas: " + e.getMessage() + "</p>");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para gestionar marcas";
    }
}
