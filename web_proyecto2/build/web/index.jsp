<%-- 
    Document   : index
    Created on : 20 oct 2024, 6:35:51 p.m.
    Author     : pc
--%>

<%@page import="modelo.marcas"%>
<%@page import="modelo.Marca"%> 
<%@page import="javax.swing.table.DefaultTableModel"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>  
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Productos</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>
<body>
    <h1 style="text-align: center;">Registro de Productos</h1>
    <div class="container">
        <form action="sr_producto" method="post" class="form-group">
            <label for="lbl_id"><b>ID:</b></label>
            <input type="text" name="txt_id" id="txt_id" class="form-control" value="0" readonly>

            <label for="lbl_producto"><b>Producto:</b></label>
            <input type="text" name="txt_producto" id="txt_producto" class="form-control" placeholder="Ejemplo: Producto" required>

            <label for="lbl_marca"><b>Marca:</b></label>
            <select name="drop_marca" id="drop_marca" class="form-control"> 
                <% 
                marcas marcas = new marcas();
                HashMap<String, String> drop = marcas.drop_Marca();
                for(String i: drop.keySet()){
                    out.println("<option value='" + i + "'> "+drop.get(i)+" </option>");
                }
                %>
            </select>

            <label for="lbl_descripcion"><b>Descripcion:</b></label>
            <input type="text" name="txt_descripcion" id="txt_descripcion" class="form-control" placeholder="Ejemplo: Descripcion" required>

            <label for="lbl_imagen"><b>Imagen:</b></label>
            <input type="text" name="txt_imagen" id="txt_imagen" class="form-control" placeholder="Ejemplo: Ingrese una URL" onchange="updateImagePreview()">
            <img id="img_preview" src="" alt="Vista previa" style="display: none; width: 100px; height: auto; margin-top: 20px;"/>

            <label for="lbl_precio_costo"><b>Precio Costo:</b></label>
            <input type="number" name="txt_precio_costo" id="txt_precio_costo" class="form-control" placeholder="Ejemplo: 500.00" required>

            <label for="lbl_precio_venta"><b>Precio Venta:</b></label>
            <input type="number" name="txt_precio_venta" id="txt_precio_venta" class="form-control" placeholder="Ejemplo: 650.00" required>

            <label for="lbl_existencia"><b>Existencia:</b></label>
            <input type="number" name="txt_existencia" id="txt_existencia" class="form-control" required>

            <label for="lbl_fecha_ingreso"><b>Fecha Ingreso:</b></label>
            <input type="date" name="txt_fecha_ingreso" id="txt_fecha_ingreso" class="form-control" required>

            <br>
            <button type="submit" name="btn_agregar" id="btn_agregar" value="agregar" class="btn btn-primary">Agregar</button>
            <button type="submit" name="btn_modificar" id="btn_modificar" value="modificar" class="btn btn-success">Modificar</button>
            <button type="submit" name="btn_eliminar" id="btn_eliminar" value="eliminar" class="btn btn-warning">Eliminar</button>
        </form>
    </div> 

    <div class="container">
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Producto</th>
                    <th>Descripción</th>
                    <th>Imagen</th>
                    <th>Precio Costo</th>
                    <th>Precio Venta</th>
                    <th>Existencia</th>
                    <th>Fecha Ingreso</th>
                    <th>Marca</th>
                </tr>
            </thead>
            <tbody id="tbl_productos">
                <%
                Marca marca = new Marca();
                DefaultTableModel tabla = marca.leer();
                for (int t = 0; t < tabla.getRowCount(); t++) {
                    out.println("<tr data-id='" + tabla.getValueAt(t, 0) + "' data-id_Marca='" + tabla.getValueAt(t, 2) + "'>");
                    out.println("<td>" + tabla.getValueAt(t, 1) + "</td>"); 
                    out.println("<td>" + tabla.getValueAt(t, 3) + "</td>"); 
                    out.println("<td><img src='" + tabla.getValueAt(t, 4) + "' alt='Imagen' style='width: 100px; height: auto;'/></td>"); // Imagen
                    out.println("<td>" + tabla.getValueAt(t, 5) + "</td>"); 
                    out.println("<td>" + tabla.getValueAt(t, 6) + "</td>"); 
                    out.println("<td>" + tabla.getValueAt(t, 7) + "</td>"); 
                    out.println("<td>" + tabla.getValueAt(t, 8) + "</td>"); 
                    out.println("<td>" + tabla.getValueAt(t, 9) + "</td>"); 
                    out.println("</tr>");
                }
                %>
            </tbody>
        </table>
    </div> 

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>

    <script type="text/javascript">
        function updateImagePreview() {
            var imageUrl = $("#txt_imagen").val(); 
            if (imageUrl) {
                $("#img_preview").attr("src", imageUrl).show(); 
            } else {
                $("#img_preview").hide(); 
            }
        }
        
      $('#tbl_productos').on('click','tr td', function(evt){
               var target, id, id_Marca,producto, Descripcion,Imagen,precio_costo,precio_venta,existencia,fecha_ingreso;
               target = $(event.target);
               id = target.parent().data('id');
            id_Marca = target.parent().data('id_Marca');
            producto = target.parent("tr").find("td").eq(0).html();
            descripcion = target.parent("tr").find("td").eq(1).html();
            imagen = target.parent("tr").find("td").eq(2).find("img").attr("src");
            precio_costo = target.parent("tr").find("td").eq(3).html();
            precio_venta = target.parent("tr").find("td").eq(4).html();
            existencia = target.parent("tr").find("td").eq(5).html();
            fecha_ingreso = target.parent("tr").find("td").eq(6).html();
            
            
            $("#txt_id").val(id);
            $("#txt_producto").val(producto);
            $("#txt_descripcion").val(descripcion);
            $("#txt_imagen").val(imagen);
            $("#txt_precio_costo").val(precio_costo);
            $("#txt_precio_venta").val(precio_venta);
            $("#txt_existencia").val(existencia);
            $("#txt_fecha_ingreso").val(fecha_ingreso);
            $("#drop_marca").val(id_marca); 
               
});
    </script>
</body>
</html>


 
