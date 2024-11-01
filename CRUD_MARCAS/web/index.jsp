<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="modelo.ConexionBD"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Marcas</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }

        h1 {
            text-align: center;
            color: #333;
        }

        /* Estilo del formulario */
        form {
            display: flex;
            justify-content: center;
            margin-bottom: 20px;
        }

        input[type="text"] {
            padding: 10px;
            margin-right: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            width: 250px;
            font-size: 16px;
        }

        input[type="button"] {
            padding: 10px 15px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }

        input[type="button"]:hover {
            background-color: #218838;
        }

        /* Estilo de la tabla */
        #tablaMarcas {
            margin: 0 auto;
            width: 80%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        table {
            width: 100%;
            border: 1px solid #ddd;
            border-radius: 4px;
            overflow: hidden;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #007bff;
            color: white;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        /* Estilos para el modal */
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.7);
        }

        .modal-content {
            background-color: #fefefe;
            margin: 10% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 50%;
            border-radius: 4px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.5);
            text-align: center; /* Centrar el texto */
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }

        button {
            padding: 10px 15px;
            margin: 10px 5px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: #0056b3;
        }

        /* Estilo del modal de confirmación */
        .modal-confirm {
            background-color: #fff;
            padding: 30px;
            border-radius: 5px;
            text-align: center;
        }

        .modal-confirm h2 {
            margin-bottom: 20px;
        }

        .modal-confirm p {
            margin-bottom: 20px;
        }

        .modal-confirm button {
            margin: 5px;
        }
    </style>
    <script>
        function enviarDatos(accion) {
            var idMarca = document.getElementById('id_Marca').value;
            var marca = document.getElementById('marca').value;

            var xhr = new XMLHttpRequest();
            xhr.open("POST", "sr_marca", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    // Limpiar los campos después de la acción
                    document.getElementById('marca').value = '';
                    document.getElementById('id_Marca').value = '';

                    // Actualizar la tabla con la respuesta del servlet
                    document.getElementById('tablaMarcas').innerHTML = xhr.responseText;
                    closeModal();
                }
            };
            xhr.send("id_Marca=" + encodeURIComponent(idMarca) + "&marca=" + encodeURIComponent(marca) + "&accion=" + accion);
        }

        function obtenerMarcas() {
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "sr_marca", true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    document.getElementById('tablaMarcas').innerHTML = xhr.responseText;
                }
            };
            xhr.send();
        }

        function seleccionarFila(id, marca) {
            document.getElementById('id_Marca').value = id;
            document.getElementById('marca').value = marca; // Guardar la marca en el campo de texto
            openModal();
        }

        function openModal() {
            document.getElementById('myModal').style.display = "block";
        }

        function closeModal() {
            document.getElementById('myModal').style.display = "none";
        }

        function confirmarModificar() {
            var nuevaMarca = prompt("Introduce el nuevo nombre de la marca:");
            if (nuevaMarca && nuevaMarca.trim() !== "") {
                document.getElementById('marca').value = nuevaMarca; // Actualizar el campo de marca
                enviarDatos('modificar');
            } else {
                alert("El nombre de la marca no puede estar vacío.");
            }
        }

        function eliminar() {
            // Abrir modal de confirmación
            var modalConfirm = document.getElementById('modalConfirm');
            modalConfirm.style.display = "block";
        }

        function closeConfirmModal() {
            document.getElementById('modalConfirm').style.display = "none";
        }

        function confirmarEliminar() {
            enviarDatos('eliminar'); // Llama a la función de eliminar sin confirm
            closeConfirmModal();
        }
    </script>
</head>
<body onload="obtenerMarcas();">

    <h1>Gestión de Marcas</h1>

    <!-- Formulario para ingresar o modificar una marca -->
    <form onsubmit="return false;">
        <input type="hidden" id="id_Marca" name="id_Marca">
        <input type="text" id="marca" name="marca" placeholder="Ingrese la marca" required>
        <input type="button" value="Agregar" onclick="enviarDatos('agregar');">
    </form>

    <!-- Tabla simple de marcas -->
    <div id="tablaMarcas"></div>

    <!-- Modal -->
    <div id="myModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <p>¿Qué deseas hacer con esta marca?</p>
            <button onclick="confirmarModificar()">Modificar</button>
            <button onclick="eliminar()">Eliminar</button>
        </div>
    </div>

    <!-- Modal de confirmación para eliminar -->
    <div id="modalConfirm" class="modal" style="display:none;">
        <div class="modal-content modal-confirm">
            <h2>Confirmación</h2>
            <p>¿Estás seguro de que deseas eliminar esta marca?</p>
            <button onclick="confirmarEliminar()">Sí</button>
            <button onclick="closeConfirmModal()">No</button>
        </div>
    </div>

</body>
</html>
