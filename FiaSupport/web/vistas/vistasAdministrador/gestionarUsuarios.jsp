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
        }

        /*---HEADER---*/
        .header {
            background-color: #c94e47;
            color: white;
            padding: 18px 25px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .header-left {
            display: flex;
            gap: 12px;
        }

        .header-left button {
            background: transparent;
            border: 1px solid #f8c4c0;
            color: white;
            padding: 10px 20px;
            font-weight: bold;
            border-radius: 4px;
            cursor: pointer;
            transition: 0.3s;
        }

        .header-left button:hover {
            background-color: rgba(255,255,255,0.1);
            transform: translateY(-2px);
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
            font-weight: bold;
            transition: 0.3s;
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

        /* ---- TITULO ---- */
        .Gestionar {
            width: 80%;
            margin: 20px auto 10px auto;
            text-align: left;
            font-size: 26px;
            color: #c94e47;
            font-weight: bold;
        }

        /* --- TARJETA DE USUARIO --- */
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
            padding: 12px 35px;
            cursor: pointer;
            border-radius: 5px;
            display: block;
            margin: 20px auto 0;
            font-weight: bold;
            font-size: 16px;
            transition: 0.3s;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        .btn-crear:hover {
            background-color: #c9302c;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.15);
        }

        /* --- TABLA --- */
        .listado-section {
            width: 80%;
            margin: 30px auto;
        }

        .search-box input {
            padding: 10px;
            width: 100%;
            max-width: 400px;
            border-radius: 3px;
            border: 1px solid #ccc;
        }

        .search-box input:focus {
            outline: none;
            border-color: #c94e47;
            box-shadow: 0 0 0 2px rgba(201,78,71,0.2);
        }

        .usuarios-table {
            margin-top: 15px;
            background-color: white;
            border-radius: 5px;
            overflow: hidden;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            background-color: #f8d7da;
            padding: 12px;
            text-align: left;
        }

        td {
            padding: 12px;
            border-bottom: 1px solid #eee;
        }

        .action-icons {
            display: flex;
            gap: 10px;
            align-items: center;
        }

        .action-icon {
            cursor: pointer;
            transition: 0.2s;
            padding: 5px;
            border-radius: 3px;
        }

        .action-icon:hover {
            transform: scale(1.2);
        }

        .icon-edit {
            color: #5bc0de;
            background-color: rgba(91,192,222,0.1);
        }

        .icon-delete {
            color: #d9534f;
            background-color: rgba(217,83,79,0.1);
        }

        /* --- MODAL DE CONFIRMACIÓN --- */
        .modal-overlay {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.5);
            z-index: 1000;
            justify-content: center;
            align-items: center;
        }

        .modal-content {
            background-color: white;
            border-radius: 8px;
            width: 90%;
            max-width: 600px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.3);
            overflow: hidden;
            max-height: 90vh;
            overflow-y: auto;
        }

        .modal-header {
            background-color: #5bc0de;
            color: white;
            padding: 20px;
            text-align: center;
            font-weight: bold;
            font-size: 18px;
        }

        .modal-body {
            padding: 25px;
        }

        .modal-body p {
            margin-bottom: 20px;
            line-height: 1.5;
            color: #555;
            text-align: center;
        }

        .confirmation-text {
            font-weight: bold;
            color: #d9534f;
            background-color: #f8d7da;
            padding: 10px;
            border-radius: 4px;
            margin: 15px 0;
            border: 1px solid #f5c6cb;
        }

        .confirmation-input {
            width: 100%;
            padding: 10px;
            border: 2px solid #ddd;
            border-radius: 4px;
            margin: 15px 0;
            text-align: center;
            font-size: 14px;
        }

        .confirmation-input:focus {
            outline: none;
            border-color: #d9534f;
        }

        .modal-footer {
            padding: 20px;
            display: flex;
            gap: 10px;
            justify-content: center;
            border-top: 1px solid #eee;
        }

        .btn-confirm {
            background-color: #d9534f;
            color: white;
            border: none;
            padding: 10px 25px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            transition: 0.3s;
        }

        .btn-confirm:disabled {
            background-color: #cccccc;
            cursor: not-allowed;
        }

        .btn-confirm:not(:disabled):hover {
            background-color: #c9302c;
        }

        .btn-cancel {
            background-color: #6c757d;
            color: white;
            border: none;
            padding: 10px 25px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            transition: 0.3s;
        }

        .btn-cancel:hover {
            background-color: #5a6268;
        }

        .btn-update {
            background-color: #5bc0de;
            color: white;
            border: none;
            padding: 10px 25px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            transition: 0.3s;
        }

        .btn-update:hover {
            background-color: #46b8da;
        }

        .edit-subtitle {
            font-size: 16px;
            color: #5bc0de;
            margin-bottom: 20px;
            text-align: center;
            font-weight: bold;
        }

        .modal-form-group {
            margin-bottom: 15px;
        }

        .modal-form-group label {
            font-weight: bold;
            margin-bottom: 5px;
            display: block;
        }
    </style>
</head>

<body>

    <!-- HEADER -->
    <div class="header">
        <div class="header-left">
            <button onclick="location.href='gestionarTickets.jsp'">Gestionar Tickets</button>
            <button onclick="location.href='reportes.jsp'">Ver Reportes y Estadísticas</button>
        </div>

        <a href="../logout.jsp" class="logout-link">
            <svg class="logout-icon" viewBox="0 0 24 24">
                <path d="M16 17l1.41-1.41L13.83 12l3.58-3.59L16 7l-5 5 5 5zm-9 4h6v-2H7V5h6V3H7
                c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2z"/>
            </svg>
            Cerrar Sesión
        </a>
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
                    <input type="text" name="nombres" required
                           value="${usuario != null ? usuario.nombres : ''}">
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
                    <input type="text" name="apellidos" required
                           value="${usuario != null ? usuario.apellidos : ''}">
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

    <!-- LISTADO -->
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
                        <th>Acciones</th>
                    </tr>
                </thead>

                <tbody>
                    <c:choose>
                        <c:when test="${not empty listUsuarios}">
                            <c:forEach var="usuario" items="${listUsuarios}">
                                <tr>
                                    <td>${usuario.carnet}</td>
                                    <td>${usuario.nombres}</td>
                                    <td>${usuario.apellidos}</td>
                                    <td>
                                        <div class="action-icons">
                                            <span class="action-icon icon-edit"
                                                  onclick="showUpdateModal('${usuario.carnet}', '${usuario.nombres}', '${usuario.apellidos}', ${usuario.esEstudiante}, '${usuario.facultad}', '${usuario.carrera}')">✏️</span>

                                            <span class="action-icon icon-delete"
                                                  onclick="showDeleteModal('${usuario.carnet}', '${usuario.nombres}', '${usuario.apellidos}')">🗑️</span>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>

                        <c:otherwise>
                            <tr>
                                <td colspan="4" style="text-align:center; padding:20px; color:#777;">
                                    No hay usuarios registrados
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>

    <!-- MODAL DE CONFIRMACIÓN PARA ELIMINAR -->
    <div class="modal-overlay" id="deleteModal">
        <div class="modal-content">
            <div class="modal-header">
                Confirmación de Eliminar Usuario
            </div>
            <div class="modal-body">
                <p>Estás a punto de eliminar todos los datos que pertenecen al usuario <strong id="userCarnet"></strong>. Confirma la acción escribiendo <strong>Eliminar</strong>.</p>
                
                <div class="confirmation-text" id="userInfo">
                    <!-- Aquí se muestra la información del usuario -->
                </div>
                
                <input type="text" id="confirmationInput" class="confirmation-input" 
                       placeholder="Escriba 'Eliminar' para confirmar" 
                       onkeyup="validateConfirmation()">
            </div>
            <div class="modal-footer">
                <button class="btn-cancel" onclick="closeDeleteModal()">Cancelar</button>
                <button class="btn-confirm" id="confirmDeleteBtn" onclick="proceedWithDelete()" disabled>
                    Eliminar Usuario
                </button>
            </div>
        </div>
    </div>
  
    <!-- Modal de Confirmación para Editar Usuario -->
    <div class="modal-overlay" id="updateModal">
        <div class="modal-content">
            <div class="modal-header">
                Editar Usuario
            </div>
            <div class="modal-body">
     
                
                <form id="updateUserForm" action="UsuarioServlet?action=update" method="post">
                    <input type="hidden" id="updateCarnet" name="carnet" value="">
                    
                    <div class="modal-form-group">
                        <label>Nombres:</label>
                        <input type="text" id="updateNombres" name="nombres" required class="form-group input" style="width: 100%;">
                    </div>
                    
                    <div class="modal-form-group">
                        <label>Apellidos:</label>
                        <input type="text" id="updateApellidos" name="apellidos" required class="form-group input" style="width: 100%;">
                    </div>
                    
                    <div class="modal-form-group">
                        <label>Estudiante:</label>
                        <div class="radio-group">
                            <label>
                                <input type="radio" name="estudiante" value="Si" id="estudianteSi" onchange="toggleModalEstudianteFields(true)"> Sí
                            </label>
                            <label>
                                <input type="radio" name="estudiante" value="No" id="estudianteNo" onchange="toggleModalEstudianteFields(false)"> No
                            </label>
                        </div>
                    </div>
                    
                    <div class="modal-form-group">
                        <label>Facultad:</label>
                        <select name="facultad" id="updateFacultadSelect" class="form-group select" style="width: 100%;">
                            <option value="">Seleccione...</option>
                            <option value="Ingeniería y Arquitectura">Ingeniería y Arquitectura</option>
                            <option value="Ciencias">Ciencias</option>
                            <option value="Medicina">Medicina</option>
                            <option value="Derecho">Derecho</option>
                            <option value="Economía">Economía</option>
                        </select>
                    </div>
                    
                    <div class="modal-form-group">
                        <label>Carrera:</label>
                        <select name="carrera" id="updateCarreraSelect" class="form-group select" style="width: 100%;">
                            <option value="">Seleccione...</option>
                            <option value="Ingeniería de Sistemas Informáticos">Ingeniería de Sistemas Informáticos</option>
                            <option value="Ingeniería Civil">Ingeniería Civil</option>
                            <option value="Ingeniería Industrial">Ingeniería Industrial</option>
                            <option value="Ingeniería Eléctrica">Ingeniería Eléctrica</option>
                            <option value="Ingeniería Mecánica">Ingeniería Mecánica</option>
                            <option value="Matemáticas">Matemáticas</option>
                            <option value="Física">Física</option>
                            <option value="Química">Química</option>
                            <option value="Biología">Biología</option>
                        </select>
                    </div>
                    
                    <div class="modal-footer">
                        <button type="button" class="btn-cancel" onclick="closeUpdateModal()">Cancelar</button>
                        <button type="submit" class="btn-update">Actualizar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- SCRIPTS -->
    <script>
        let currentCarnetToDelete = '';
        let currentUserName = '';

        function showUpdateModal(carnet, nombres, apellidos, esEstudiante, facultad, carrera) {
            // Llenar el formulario con los datos del usuario
            document.getElementById('editSubtitle').textContent = 'Editando ' + carnet;
            document.getElementById('updateCarnet').value = carnet;
            document.getElementById('updateNombres').value = nombres;
            document.getElementById('updateApellidos').value = apellidos;
            
            // Configurar radio buttons de estudiante
            if (esEstudiante) {
                document.getElementById('estudianteSi').checked = true;
                document.getElementById('estudianteNo').checked = false;
            } else {
                document.getElementById('estudianteSi').checked = false;
                document.getElementById('estudianteNo').checked = true;
            }
            
            // Configurar facultad y carrera
            document.getElementById('updateFacultadSelect').value = facultad;
            document.getElementById('updateCarreraSelect').value = carrera;
            
            // Actualizar estado de campos según si es estudiante
            toggleModalEstudianteFields(esEstudiante);
            
            // Mostrar modal
            document.getElementById('updateModal').style.display = 'flex';
        }

        function closeUpdateModal() {
            document.getElementById('updateModal').style.display = 'none';
        }

        function toggleModalEstudianteFields(isEstudiante) {
            let fac = document.getElementById("updateFacultadSelect");
            let car = document.getElementById("updateCarreraSelect");

            fac.disabled = !isEstudiante;
            car.disabled = !isEstudiante;

            fac.style.background = car.style.background =
                isEstudiante ? "white" : "#eee";

            if (!isEstudiante) {
                fac.value = "";
                car.value = "";
            }
        }

        function showDeleteModal(carnet, nombres, apellidos) {
            currentCarnetToDelete = carnet;
            currentUserName = nombres + ' ' + apellidos;
            
            document.getElementById('userCarnet').textContent = carnet;
            document.getElementById('userInfo').textContent = nombres + ' ' + apellidos + ' (' + carnet + ')';
            document.getElementById('confirmationInput').value = '';
            document.getElementById('confirmDeleteBtn').disabled = true;
            
            document.getElementById('deleteModal').style.display = 'flex';
        }

        function closeDeleteModal() {
            document.getElementById('deleteModal').style.display = 'none';
            currentCarnetToDelete = '';
            currentUserName = '';
        }

        function validateConfirmation() {
            const input = document.getElementById('confirmationInput');
            const button = document.getElementById('confirmDeleteBtn');
            
            if (input.value.trim().toLowerCase() === 'eliminar') {
                button.disabled = false;
            } else {
                button.disabled = true;
            }
        }

        function proceedWithDelete() {
            if (!document.getElementById('confirmDeleteBtn').disabled) {
                closeDeleteModal();
                location.href = 'UsuarioServlet?action=delete&carnet=' + currentCarnetToDelete;
            }
        }

        function toggleEstudianteFields(isEstudiante) {
            let fac = document.getElementById("facultadSelect");
            let car = document.getElementById("carreraSelect");

            fac.disabled = !isEstudiante;
            car.disabled = !isEstudiante;

            fac.style.background = car.style.background =
                isEstudiante ? "white" : "#eee";

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

        // Cerrar modales si se hace clic fuera del contenido
        document.getElementById('deleteModal').addEventListener('click', function(e) {
            if (e.target === this) {
                closeDeleteModal();
            }
        });

        document.getElementById('updateModal').addEventListener('click', function(e) {
            if (e.target === this) {
                closeUpdateModal();
            }
        });

        // Cerrar modales con tecla Escape
        document.addEventListener('keydown', function(e) {
            if (e.key === 'Escape') {
                closeDeleteModal();
                closeUpdateModal();
            }
        });

        // Mantener estado correcto de facultad/carrera al editar
        window.onload = () => {
            const radio = document.querySelector("input[name='estudiante']:checked");
            if (radio)
                toggleEstudianteFields(radio.value === "Si");
        };
    </script>

</body>
</html>