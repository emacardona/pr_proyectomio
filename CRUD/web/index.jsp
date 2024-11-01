<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="modelo.ConexionBD" %>
<%@ page import="java.text.SimpleDateFormat" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulario de Cliente</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 10px;
        }
        h1, h2 {
            text-align: center;
            color: #333;
            margin: 10px 0;
        }
        .container {
            display: flex;
            justify-content: space-between;
            max-width: 1000px;
            margin: 0 auto;
            padding: 50px;
            background: #e6f7ff;
            border-radius: 4px;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
        }
        .formulario {
            flex: 1;
            margin-right: 50px;
            background: #ffffff;
            padding: 20px;
            border-radius: 4px;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
        }
        form {
            display: grid;
            gap: 5px;
        }
        label {
            font-weight: bold;
            font-size: 14px;
        }
        input[type="text"], input[type="email"], input[type="date"], select {
            padding: 6px;
            border: 1px solid #ccc;
            border-radius: 4px;
            width: 100%;
            box-sizing: border-box;
            font-size: 14px;
        }
        input[type="submit"] {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 8px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
        .tabla-container {
            flex: 2;
            overflow-y: auto;
            max-height: 300px;
            background: #ffffff;
            padding: 20px;
            border-radius: 4px;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
        }
        table {
            width: 100%;
            border-collapse: collapse;
            border: 2px solid #007bff;
            border-radius: 4px;
            overflow: hidden;
        }
        th, td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
            font-size: 12px;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
    </style>
    <script>
        function seleccionarFila(index) {
            const fila = document.getElementById('cliente-data-' + index);
            const clienteData = fila.dataset;

            document.getElementById('index').value = index;
            document.getElementById('nombre').value = clienteData.nombre;
            document.getElementById('apellidos').value = clienteData.apellidos;
            document.getElementById('nit').value = clienteData.nit;
            document.getElementById('genero').value = clienteData.genero;
            document.getElementById('telefono').value = clienteData.telefono;
            document.getElementById('correo').value = clienteData.correoElectronico;
            document.getElementById('fecha_ingreso').value = clienteData.fechaIngreso; // Se utiliza el formato correcto
        }
    </script>
</head>
<body>
<div class="container">
    <div class="formulario">
        <h1>Registro de Cliente</h1>

        <% 
            String mensaje = (String) request.getAttribute("mensaje");
            if (mensaje != null) {
        %>
            <p style="color: red; text-align: center;"><strong><%= mensaje %></strong></p>
        <%
            }
        %>

        <form name="clienteForm" action="sr_clientes" method="POST">
            <input type="hidden" id="index" name="index">
            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombres" required>
            
            <label for="apellidos">Apellidos:</label>
            <input type="text" id="apellidos" name="apellidos" required>
            
            <label for="nit">NIT:</label>
            <input type="text" id="nit" name="nit" required>
            
            <label for="genero">Género:</label>
            <select id="genero" name="genero">
                <option value="1">Masculino</option>
                <option value="0">Femenino</option>
                <option value="2">Otro</option>
            </select>
            
            <label for="telefono">Teléfono:</label>
            <input type="text" id="telefono" name="telefono" required>
            
            <label for="correo">Correo Electrónico:</label>
            <input type="email" id="correo" name="correo_electronico" required>
            
            <label for="fecha_ingreso">Fecha de Ingreso:</label>
            <input type="date" id="fecha_ingreso" name="fecha_ingreso" required>
            
            <input type="submit" name="accion" value="Registrar Cliente">
            <input type="submit" name="accion" value="Modificar Cliente">
            <input type="submit" name="accion" value="Eliminar Cliente">
        </form>
    </div>

    <div class="tabla-container">
        <h2>Lista de Clientes</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombres</th>
                    <th>Apellidos</th>
                    <th>NIT</th>
                    <th>Género</th>
                    <th>Teléfono</th>
                    <th>Correo Electrónico</th>
                    <th>Fecha de Ingreso</th>
                </tr>
            </thead>
            <tbody>
                <%
                    Connection conn = null;
                    PreparedStatement stmt = null;
                    ResultSet rs = null;

                    try {
                        conn = ConexionBD.obtenerConexion();
                        String sql = "SELECT id_Cliente, nombres, apellidos, NIT, genero, telefono, correo_electronico, fecha_ingreso FROM clientes";
                        stmt = conn.prepareStatement(sql);
                        rs = stmt.executeQuery();

                        SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd");

                        while (rs.next()) {
                            int id = rs.getInt("id_Cliente");
                            String nombre = rs.getString("nombres");
                            String apellidos = rs.getString("apellidos");
                            String nit = rs.getString("NIT");
                            int genero = rs.getInt("genero");
                            String telefono = rs.getString("telefono");
                            String correoElectronico = rs.getString("correo_electronico");
                            java.sql.Date fechaIngresoSQL = rs.getDate("fecha_ingreso");
                            String fechaFormatoCorrecto = fechaIngresoSQL != null ? formatoSalida.format(fechaIngresoSQL) : "";

                %>
                            <tr id="cliente-data-<%= id %>" data-nombre="<%= nombre %>" data-apellidos="<%= apellidos %>" data-nit="<%= nit %>" data-genero="<%= genero %>" data-telefono="<%= telefono %>" data-correo-electronico="<%= correoElectronico %>" data-fecha-ingreso="<%= fechaFormatoCorrecto %>" onclick="seleccionarFila(<%= id %>)">
                                <td><%= id %></td>
                                <td><%= nombre %></td>
                                <td><%= apellidos %></td>
                                <td><%= nit %></td>
                                <td><%= genero == 1 ? "Masculino" : (genero == 0 ? "Femenino" : "Otro") %></td>
                                <td><%= telefono %></td>
                                <td><%= correoElectronico %></td>
                                <td><%= fechaFormatoCorrecto %></td>
                            </tr>
                <%
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        if (rs != null) try { rs.close(); } catch (SQLException e) {}
                        if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
                        if (conn != null) try { conn.close(); } catch (SQLException e) {}
                    }
                %>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
