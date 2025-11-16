<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FIA Support</title>

    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }

        body {
            font-family: Arial, sans-serif;
            background-color: #f7e9df;
            margin: 0;
            padding: 0;
        }
         /*---HEADER---*/
    .header{
      background-color: #c94e47;
      color: white;
      padding: 18px 25px;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    .header-left{
      display: flex;
      gap: 12px;
    }
    .header-left button{
      background: transparent;
      border: 1px solid #f8c4c0;
      color: white;
      padding: 10px 20px;
      font-weight: bold;
      border-radius: 4px;
      cursor: pointer;
      transition: all 0.3s ease;
    }
    .header-left button:hover{
      background-color: rgba(255,255,255,0.1);
      transform: translateY(-2px);
    }
    .header-right .logout-btn{
      border-color: #ffffff;
    }
        .logout-link {
            background-color: #8b4a46;
            color: white;
            padding: 10px 18px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 14px;
            display: flex;
            align-items: center;
            gap: 6px;
            transition: all 0.3s ease;
            font-weight: bold;
        }

        .logout-link:hover {
            background-color: #6d3835;
            transform: translateY(-2px);
            box-shadow: 0 3px 8px rgba(0,0,0,0.25);
        }

        .logout-icon {
            width: 18px;
            height: 18px;
            fill: white;
        }
       /* ---- Gestionar Usuario---- */
        .Gestionar {
          width: 80%;
          margin: 0 auto;
          text-align: left;
          font-size: 26px;
          color: #c94e47;
          font-weigth: bold;
        }

        /* --- TARJETA --- */
        .nuevo-usuario {
            background-color: white;
            border: 2px solid #ddd;
            border-radius: 5px;
            padding: 20px;
            width: 80%;
            margin: 0 auto 20px auto;
        }

        .nuevo-usuario h3 {
            color: #d9534f;
            font-size: 20px;
            margin-bottom: 15px;
        }

        /* --- FORMULARIO --- */
        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
            margin-bottom: 15px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        .form-group label {
            font-weight: bold;
            margin-bottom: 5px;
        }

        .form-group input,
        .form-group select {
            padding: 8px;
            border-radius: 3px;
            border: 1px solid #ccc;
        }

        .radio-group {
            display: flex;
            gap: 20px;
            align-items: center;
            margin-top: 5px;
        }

        .btn-crear {
            background-color: #d9534f;
            color: white;
            border: none;
            padding: 10px 30px;
            cursor: pointer;
            border-radius: 3px;
            display: block;
            margin: 20px auto 0;
        }

        /* --- TABLA --- */
        .listado-section {
            width: 80%;
            margin: 30px auto 0 auto;
        }

        .search-box input {
            padding: 10px;
            width: 100%;
            max-width: 400px;
            border-radius: 3px;
            border: 1px solid #ccc;
        }
        
        .search-box input:focus{
            outline: none;
            border-color: #c94e47;
            box-shadow: 0 0 0 2px rgba(201, 78, 71, 0.2);
        }

        .usuarios-table {
            margin-top: 15px;
            background-color: white;
            border-radius: 5px;
            overflow: hidden;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        table { width: 100%; border-collapse: collapse; }
        th {
            background-color: #f8d7da;
            padding: 12px;
            text-align: left;
        }
        td { padding: 12px; border-bottom: 1px solid #eee; }

        .action-icons { display: flex; gap: 10px; align-items: center; }
        .action-icon { cursor: pointer; transition: 0.2s; }
        .action-icon:hover { transform: scale(1.2); }

        .icon-edit { color: #5bc0de; }
        .icon-delete { color: #d9534f; }

    </style>
</head>

<body>

    <!-- HEADER -->
    <div class="header">
        <div class="header-left">
            <button onclick="location.href='gestionarTickets.jsp'">Gestionar Tickets</button>
            <button onclick="location.href='reportes.jsp'">Ver Reportes y Estadísticas</button>
      </div>
        <div class="header-right">
            <a href="../logout.jsp" class="logout-link">
                <svg class="logout-icon" viewBox="0 0 24 24">
                    <path d="M16 17l1.41-1.41L13.83 12l3.58-3.59L16 7l-5 5 5 5zm-9 4h6v-2H7V5h6V3H7c-1.1 
                    0-2 .9-2 2v14c0 1.1.9 2 2 2z"/>
                </svg>
                Cerrar Sesión
            </a>
        </div>
    </div>
    <h1 class="Gestionar">Gestionar Usuarios</h1>

    <!-- FORMULARIO DE USUARIO -->
    <div class="nuevo-usuario">
        <h3>+ Nuevo Usuario</h3>

        <form id="userForm" action="UsuarioServlet?action=${usuario != null ? 'update' : 'create'}" method="post">

            <div class="form-row">
                <div class="form-group">
                    <label>Carnet:</label>
                    <input type="text" name="carnet"
                        value="${usuario != null ? usuario.carnet : ''}"
                        ${usuario != null ? "readonly" : ""}
                        required>
                </div>

                <div class="form-group">
                    <label>Estudiante:</label>
                    <div class="radio-group">
                        <label>
                            <input type="radio" name="estudiante" value="Si"
                                ${usuario == null || usuario.esEstudiante ? "checked" : ""}
                                onchange="toggleEstudianteFields(true)"> Sí
                        </label>

                        <label>
                            <input type="radio" name="estudiante" value="No"
                                ${usuario != null && !usuario.esEstudiante ? "checked" : ""}
                                onchange="toggleEstudianteFields(false)"> No
                        </label>
                    </div>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label>Nombres:</label>
                    <input type="text" name="nombres"
                           value="${usuario != null ? usuario.nombres : ''}"
                           required>
                </div>

                <div class="form-group">
                    <label>Facultad:</label>
                    <select name="facultad" id="facultadSelect">
                        <option value="">Seleccione...</option>
                        <option value="Ingeniería">Ingeniería</option>
                        <option value="Ciencias">Ciencias</option>
                        <option value="Medicina">Medicina</option>
                        <option value="Derecho">Derecho</option>
                        <option value="Economía">Economía</option>
                        <option value="Arquitectura">Arquitectura</option>
                    </select>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label>Apellidos:</label>
                    <input type="text" name="apellidos"
                           value="${usuario != null ? usuario.apellidos : ''}"
                           required>
                </div>

                <div class="form-group">
                    <label>Carrera:</label>
                    <select name="carrera" id="carreraSelect">
                        <option value="">Seleccione...</option>
                        <option value="Sistemas">Ingeniería en Sistemas</option>
                        <option value="Civil">Ingeniería Civil</option>
                        <option value="Industrial">Ingeniería Industrial</option>
                        <option value="Eléctrica">Ingeniería Eléctrica</option>
                        <option value="Mecánica">Ingeniería Mecánica</option>
                        <option value="Matemáticas">Matemáticas</option>
                        <option value="Física">Física</option>
                        <option value="Química">Química</option>
                        <option value="Biología">Biología</option>
                    </select>
                </div>
            </div>

            <button type="submit" class="btn-crear">${usuario != null ? "Actualizar" : "Crear"}</button>

        </form>
    </div>

    <!-- TABLA -->
    <div class="listado-section">
        <h2>Listado</h2>

        <div class="search-box">
            <input id="searchInput" type="text" placeholder="Buscar por carnet..." onkeyup="searchTable()">
        </div>

        <div class="usuarios-table">
            <table id="usuariosTable">
                <thead>
                    <tr>
                        <th>Carnet</th>
                        <th>Nombres</th>
                        <th>Apellidos</th>
                    </tr>
                </thead>

                <tbody>
                    <c:choose>
                        <c:when test="${not empty listUsuarios}">
                            <c:forEach var="usuario" items="${listUsuarios}">
                                <tr>
                                    <td>
                                        <div class="action-icons">
                                            <span class="action-icon icon-edit"
                                                onclick="editUsuario('${usuario.carnet}')">✏️</span>

                                            <span class="action-icon icon-delete"
                                                onclick="deleteUsuario('${usuario.carnet}')">🗑️</span>

                                            <span>${usuario.carnet}</span>
                                        </div>
                                    </td>

                                    <td>${usuario.nombres}</td>
                                    <td>${usuario.apellidos}</td>
                                </tr>
                            </c:forEach>
                        </c:when>

                        <c:otherwise>
                            <tr>
                                <td colspan="3" style="text-align:center; padding:20px; color:#777;">
                                    No hay usuarios registrados
                                </td>
                            </tr>
                        </c:otherwise>

                    </c:choose>

                </tbody>
            </table>
        </div>
    </div>

    <!-- SCRIPTS -->
    <script>
        function cerrarSesion() {
            if (confirm('¿Desea cerrar sesión?')) {
                location.href = 'logout.jsp';
            }
        }

        function editUsuario(carnet) {
            location.href = 'UsuarioServlet?action=edit&carnet=' + carnet;
        }

        function deleteUsuario(carnet) {
            if (confirm('¿Eliminar este usuario?')) {
                location.href = 'UsuarioServlet?action=delete&carnet=' + carnet;
            }
        }

        function toggleEstudianteFields(isEstudiante) {
            let fac = document.getElementById("facultadSelect");
            let car = document.getElementById("carreraSelect");

            fac.disabled = !isEstudiante;
            car.disabled = !isEstudiante;
            fac.style.background = car.style.background = isEstudiante ? "white" : "#eee";

            if (!isEstudiante) {
                fac.value = "";
                car.value = "";
            }
        }

        function searchTable() {
            let filter = document.getElementById("searchInput").value.toUpperCase();
            let rows = document.getElementById("usuariosTable").getElementsByTagName("tr");

            for (let i = 1; i < rows.length; i++) {
                let txt = rows[i].innerText.toUpperCase();
                rows[i].style.display = txt.includes(filter) ? "" : "none";
            }
        }

        window.onload = () => {
            const radio = document.querySelector("input[name='estudiante']:checked");
            if (radio) toggleEstudianteFields(radio.value === "Si");
        };
    </script>

</body>
</html>
