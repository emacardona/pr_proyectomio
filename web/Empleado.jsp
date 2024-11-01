<%-- 
    Document   : Empleado
    Created on : 13/10/2024, 10:25:21 p. m.
    Author     : DELL
--%>
<%@page import="Modelo.Puesto"%>
<%@page import="Modelo.Empleado"%>
<%@page import="java.util.HashMap"%>
<%@page import="javax.swing.table.DefaultTableModel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Empleados</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    </head>
    <body>
        <div class="container">
        <!-- Card for Modals -->
        <div class="form-group">
            <div class="card">
                <div class="card-header">
                    <h4>Gestión de Empleados y Puestos</h4>
                </div>
                <div class="card-body">
                    <!-- Button trigger for Empleado Nuevo Modal -->
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modal_empleado">
                        Empleado Nuevo
                    </button>
                    
                    <!-- Button trigger for Puesto Nuevo Modal -->
                    <button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#modal_puesto">
                        Puesto Nuevo
                    </button>
                </div>
            </div>
        </div>

        <!-- Empleado Nuevo Modal -->
        <div class="modal fade" id="modal_empleado" tabindex="-1" role="dialog" aria-labelledby="modal_empleado" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-body">
                        <!-- Formulario de Empleado -->
                        <form action="Controlador?menu=Empleado" method="post" class="form-group">

                            <label for="lbl_id">ID</label>
                            <input type="text" name="txt_id" id="txt_id" class="form-control" value="0" readonly>

                            <label for="lbl_nombres">Nombres</label>
                            <input type="text" name="txt_nombres" id="txt_nombres" class="form-control" placeholder="Primer nombre Segundo nombre" required>

                            <label for="lbl_apellidos">Apellidos</label>
                            <input type="text" name="txt_apellidos" id="txt_apellidos" class="form-control" placeholder="Primer apellido Segundo apellido" required>

                            <label for="lbl_direccion">Dirección</label>
                            <input type="text" name="txt_direccion" id="txt_direccion" class="form-control" placeholder="Guatemala" required>

                            <label for="lbl_telefono">Teléfono</label>
                            <input type="number" name="txt_telefono" id="txt_telefono" class="form-control" placeholder="12345678" required>

                            <label for="lbl_dpi">DPI</label>
                            <input type="number" name="txt_dpi" id="txt_dpi" class="form-control" placeholder="3027405800101" required>

                            <label for="lbl_genero">Género</label>
                            <div>
                                <label>
                                    <input type="radio" name="txt_genero" value="true" required> Masculino
                                </label>
                                <label>
                                    <input type="radio" name="txt_genero" value="false" required> Femenino
                                </label>
                            </div>

                            <label for="lbl_fn">Nacimiento</label>
                            <input type="date" name="txt_fn" id="txt_fn" class="form-control" required>

                            <label for="lbl_puesto">Puesto</label>
                            <select name="drop_puesto" id="drop_puesto" class="form-control">
                                <%
                                    Puesto puesto = new Puesto();
                                    HashMap<String, String> drop = puesto.drop_puesto();
                                    for (String i : drop.keySet()) {
                                        out.write("<option value='" + i + "'>" + drop.get(i) + "</option>");
                                    }
                                %>
                            </select>

                            <br/>

                            <label for="lbl_fl">Inicio Laboral</label>
                            <input type="date" name="txt_fl" id="txt_fl" class="form-control" required>

                            <label for="lbl_fi">Fecha Ingreso</label>
                            <input type="datetime-local" name="txt_fi" id="txt_fi" class="form-control" required>

                            <br/>

                            <!-- Botones de acción -->
                            <button  name="btn_agregar" id="btn_agregar" value="agregar" class="btn btn-primary btn-lg">Agregar</button>
                            <button  name="btn_actualizar" id="btn_actualizar" value="actualizar" class="btn btn-success btn-lg">Actualizar</button>
                            <button  name="btn_eliminar" id="btn_eliminar" value="eliminar" class="btn btn-danger btn-lg" onclick="javascript:if(!confirm('Desea eliminar'))return false">Eliminar</button>
                            <button type="button" class="btn btn-warning btn-lg" data-bs-dismiss="modal">Cerrar</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>


        <!-- Puesto Nuevo Modal -->
        <div class="modal fade" id="modal_puesto" tabindex="-1" role="dialog" aria-labelledby="modal_puestoLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-body">
                        <form action="sr_puesto" method="post" class="form-group">
                            <label for="lbl_id_puesto">ID</label>
                            <input type="text" name="txt_id_puesto" id="txt_id_puesto" class="form-control" value="0" readonly>
                            <label for="lbl_nombre_puesto">Nombre del Puesto</label>
                            <input type="text" name="txt_nombre_puesto" id="txt_nombre_puesto" class="form-control" required placeholder="Ingrese el nombre del puesto">
                            <br/>
                            <button  name="action" id="btn_agregar" value="agregar" class="btn btn-primary btn-lg">Agregar</button>
                            <button  name="action" id="btn_actualizar" value="actualizar" class="btn btn-success btn-lg">Actualizar</button>
                            <button  name="action" id="btn_eliminar" value="eliminar" class="btn btn-danger btn-lg" onclick="javascript:if(!confirm('Desea eliminar'))return false">Eliminar</button>
                            <button type="button" class="btn btn-warning btn-lg" data-bs-dismiss="modal">Cerrar</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
                      
          

            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombres</th>
                        <th>Apellidos</th>
                        <th>Direccion</th>
                        <th>Telefono</th>
                        <th>DPI</th>
                        <th>Genero</th>
                        <th>Nacimiento</th>
                        <th>Puesto</th>
                        <th>Inicio Laboral</th>
                        <th>Fecha Ingreso</th>
                    </tr>
                </thead>
                <tbody id="tbl_empleados">
                    <%
                        Empleado empleado = new Empleado();
                        DefaultTableModel tabla = new DefaultTableModel();
                        tabla = empleado.leer();
                        for (int t = 0; t < tabla.getRowCount(); t++) {
                            out.println("<tr data-id='" + tabla.getValueAt(t, 0) + "'>");
                            out.println("<td>" + tabla.getValueAt(t, 0) + "</td>");
                            out.println("<td>" + tabla.getValueAt(t, 1) + "</td>");
                            out.println("<td>" + tabla.getValueAt(t, 2) + "</td>");
                            out.println("<td>" + tabla.getValueAt(t, 3) + "</td>");
                            out.println("<td>" + tabla.getValueAt(t, 4) + "</td>");
                            out.println("<td>" + tabla.getValueAt(t, 5) + "</td>");
                            out.println("<td>" + tabla.getValueAt(t, 6) + "</td>");
                            out.println("<td>" + tabla.getValueAt(t, 7) + "</td>");
                            out.println("<td>" + tabla.getValueAt(t, 8) + "</td>");
                            out.println("<td>" + tabla.getValueAt(t, 9) + "</td>");
                            out.println("<td>" + tabla.getValueAt(t, 10) + "</td>");
                            out.println("</tr>");
                        }
                    %>
                </tbody>
            </table>
            <br/>
            <br/>
                
            <!-- Tabla para mostrar los puestos -->
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre del Puesto</th>
                    </tr>
                </thead>
                <tbody id="tbl_puestos">
                    <%
                        DefaultTableModel tablaP = puesto.leer(); 
                        for (int t = 0; t < tablaP.getRowCount(); t++) {
                            out.println("<tr data-id='" + tablaP.getValueAt(t, 0) + "'>");
                            out.println("<td>" + tablaP.getValueAt(t, 0) + "</td>");
                            out.println("<td>" + tablaP.getValueAt(t, 1) + "</td>");
                            out.println("</tr>");
                        }
                    %>
                </tbody>
            </table>

        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
        <script>
            function limpiar(){
                $("#txt_id").val(0);
                $("#txt_nombres").val('');
                $("#txt_apellidos").val('');
                $("#txt_direccion").val('');
                $("#txt_telefono").val('');
                $("#txt_dpi").val('');
                $("#txt_genero").val('');
                $("#txt_fn").val('');
                $("#drop_puesto").val(1);
                $("#txt_fl").val('');
                $("#txt_fi").val('');
            }

            $('#tbl_empleados').on('click', 'tr td', function (event) {
                var target, id, id_p, codigo, nombres, apellidos, direccion, telefono, dpi, genero, nacimiento, registro_laboral, ingreso;
                target = $(event.target);
                id = target.parent().data('id');
                id_p = target.parent().data('id_p');
                nombres = target.parent("tr").find("td").eq(0).html();
                apellidos = target.parent("tr").find("td").eq(1).html();
                direccion = target.parent("tr").find("td").eq(2).html();
                telefono = target.parent("tr").find("td").eq(3).html();
                dpi = target.parent("tr").find("td").eq(4).html();
                genero = target.parent("tr").find("td").eq(5).html();
                nacimiento = target.parent("tr").find("td").eq(6).html();
                registro_laboral = target.parent("tr").find("td").eq(7).html();
                ingreso = target.parent("tr").find("td").eq(8).html();

                $("#txt_id").val(id);
                $("#txt_nombres").val(nombres);
                $("#txt_apellidos").val(apellidos);
                $("#txt_direccion").val(direccion);
                $("#txt_telefono").val(telefono);
                $("#txt_dpi").val(dpi);
                $("#txt_genero").val(genero);
                $("#txt_fn").val(nacimiento);
                $("#drop_puesto").val(id_p);
                $("#txt_fl").val(registro_laboral);
                $("#txt_fi").val(ingreso);
                $("#modal_empleado").modal('show');
            });
            
            function limpiarPuesto() {
                $("#txt_nombre_puesto").val('');
                $("#txt_id_puesto").val('');
                $("#modal_puestoLabel").text("Agregar Nuevo Puesto");
            }

            $('#tbl_puestos').on('click', 'tr', function (event) {
                var target, id, nombre_puesto;
                target = $(event.target);
                id = target.parent().data('id');
                nombre_puesto = $(this).find("td").eq(0).text();

                $("#txt_id_puesto").val(id);
                $("#txt_nombre_puesto").val(nombre_puesto);
                $("#modal_puesto").modal('show');
            });
        </script>
    </body>
</html>
