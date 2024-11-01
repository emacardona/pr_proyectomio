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
import modelo.Proveedor;

/**
 *
 * @author pc
 */
public class sr_proveedor extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    Proveedor proveedor; 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet sr_proveedor</title>");
            out.println("</head>");
            out.println("<body>");
         
            // aguardalos parámetros enviados desde el formulario
            String id = request.getParameter("txt_id");
            String nombreProveedor = request.getParameter("txt_proveedor");
            String nit = request.getParameter("txt_nit");
            String direccion = request.getParameter("txt_direccion");
            String telefono = request.getParameter("txt_telefono");
            
            // Validaciones 
            // Verifica si el nombre del proveedor está vacío
            if (nombreProveedor == null || nombreProveedor.isEmpty()) {
                out.println("<h1>Error: El nombre del proveedor es obligatorio</h1>");
                out.println("<a href='index.jsp'>Regresar</a>");
                return;
            }
               // Valida que el NIT tenga 12 dígitos
            if (nit == null || nit.isEmpty() || !nit.matches("\\d{12}")) {
                out.println("<h1>Error: El NIT es obligatorio y debe contener 12 dígitos</h1>");
                out.println("<a href='index.jsp'>Regresar</a>");
                return;
            }
             // Verifica si la dirección está vacía
            if (direccion == null || direccion.isEmpty()) {
                out.println("<h1>Error: La dirección es obligatoria</h1>");
                out.println("<a href='index.jsp'>Regresar</a>");
                return;
            }
            // Valida que el teléfono tenga entre 8 dígitos
            if (telefono == null || telefono.isEmpty() || !telefono.matches("\\d{8}")) {
                out.println("<h1>Error: El teléfono es obligatorio y debe contener entre 8  dígitos</h1>");
                out.println("<a href='index.jsp'>Regresar</a>");
                return;
            }
            
           // Instancia el objeto Proveedor y asigna los valores obtenidos
            Proveedor proveedor = new Proveedor();
            proveedor.setProveedor(nombreProveedor);
            proveedor.setNit(nit);
            proveedor.setDireccion(direccion);
            proveedor.setTelefono(telefono);
           proveedor.setId(Integer.parseInt(id));
           
           
             //Boton Agregar             
            if ("agregar".equals(request.getParameter("btn_agregar"))) {
                
            
            if (proveedor.agregar() > 0){
                response.sendRedirect("index.jsp");
            }else{
                out.println("<h1>Error..........</h1>");
                out.println("<a herf='index.jsp'>Regresar</a>");
            }
            }
            //Boton Modificar
             if ("modificar".equals(request.getParameter("btn_modificar"))) {
                
            
            if (proveedor.modificar() > 0){
                response.sendRedirect("index.jsp");
            }else{
                out.println("<h1>Error..........</h1>");
                out.println("<a herf='index.jsp'>Regresar</a>");
            }
            }
             //Boton Eliminar
             if ("eliminar".equals(request.getParameter("btn_eliminar"))) {
                
            
            if (proveedor.eliminar() > 0){
                response.sendRedirect("index.jsp");
            }else{
                out.println("<h1>Error..........</h1>");
                out.println("<a herf='index.jsp'>Regresar</a>");
            }
            }
            
          
           
            
           
             
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
