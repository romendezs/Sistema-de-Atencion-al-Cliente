<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FIA Support</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7e9df;
            margin: 0;
            padding: 0;
        }

        /* ---- HEADER ---- */
        .header {
            background-color: #c94e47;
            color: white;
            padding: 18px 25px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 4px 12px rgba(0,0,0,0.25);
        }

        .header-left {
            font-size: 17px;
            font-weight: bold;
            letter-spacing: 0.5px;
        }

        .header-right {
            display: flex;
            align-items: center;
            gap: 12px;
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

        /* ---- BIENVENIDA ---- */
        .welcome {
            text-align: center;
            font-size: 26px;
            color: #c94e47;
            margin: 25px 0 15px 0;
            font-weight: bold;
        }

        /* ---- TARJETA DE NUEVO TICKET ---- */
        .nuevo-ticket {
            background-color: white;
            width: 80%;
            max-width: 700px;
            margin: 0 auto 30px auto;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.2);
        }

        .nuevo-ticket h3 {
            color: #c94e47;
            font-size: 20px;
            margin-bottom: 20px;
            font-weight: bold;
        }

        .form-group {
            margin-bottom: 18px;
        }

        .form-group label {
            font-weight: bold;
            margin-bottom: 5px;
            display: block;
            font-size: 14px;
            color: #333;
        }

        .form-group input,
        .form-group textarea {
            width: 100%;
            padding: 12px;
            border-radius: 6px;
            border: 1px solid #ccc;
            font-size: 14px;
        }

        .form-group textarea {
            resize: vertical;
            height: 100px;
        }

        .btn-crear {
            background-color: #8b4a46;
            color: white;
            padding: 12px 35px;
            border: none;
            border-radius: 6px;
            font-size: 15px;
            cursor: pointer;
            display: block;
            margin: 0 auto;
            transition: 0.3s;
        }

        .btn-crear:hover {
            background-color: #6d3835;
        }

        /* ---- TABLA DE TICKETS ---- */
        .tickets-section {
            width: 90%;
            max-width: 1000px;
            margin: auto;
        }

        .tickets-section h2 {
            color: #c94e47;
            font-size: 22px;
            font-weight: bold;
            margin-bottom: 15px;
        }

        .tickets-table {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.15);
            overflow: hidden;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            background-color: #f2beb8;
            padding: 14px;
            text-align: left;
            font-size: 15px;
            color: #5a1e1e;
        }

        td {
            padding: 14px;
            border-bottom: 1px solid #eee;
            font-size: 14px;
        }

        tr:hover {
            background-color: #fceeee;
        }

        .estado {
            padding: 6px 12px;
            border-radius: 6px;
            font-size: 13px;
            font-weight: bold;
            display: inline-block;
        }

        .estado-pendiente { background-color: #ffb9b9; color: #7a1b1b; }
        .estado-cerrado   { background-color: #e8b5ad; color: #7a1b1b; }
        .estado-terminado { background-color: #93e99f; color: #0f551e; }
        .estado-asignado  { background-color: #ffd495; color: #7d4e00; }

        /* Tooltip */
        .tooltip {
            position: relative;
            cursor: pointer;
        }

        .tooltip .tooltiptext {
            visibility: hidden;
            opacity: 0;
            width: 180px;
            background-color: #333;
            color: white;
            padding: 6px;
            border-radius: 6px;
            transition: 0.3s;
            text-align: center;
            position: absolute;
            bottom: 120%;
            left: 50%;
            transform: translateX(-50%);
        }

        .tooltip:hover .tooltiptext {
            visibility: visible;
            opacity: 1;
        }
    </style>
</head>

<body>

    <!-- HEADER -->
    <div class="header">
        <div class="header-left">
            Ver Reportes y Estadísticas
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

    <h1 class="welcome">Bienvenido, ${sessionScope.nombreUsuario}</h1>

    <!-- NUEVO TICKET -->
    <div class="nuevo-ticket">
        <h3>+ Nuevo Ticket</h3>

        <form action="TicketServlet?action=create" method="post">
            <div class="form-group">
                <label>Asunto:</label>
                <input type="text" name="asunto" required placeholder="Escribir un asunto">
            </div>

            <div class="form-group">
                <label>Descripción:</label>
                <textarea name="descripcion" required placeholder="Escribir una descripción"></textarea>
            </div>

            <button type="submit" class="btn-crear">Crear</button>
        </form>
    </div>

    <!-- LISTA DE TICKETS -->
    <div class="tickets-section">
        <h2>Tus Tickets</h2>

        <div class="tickets-table">
            <table>
                <thead>
                    <tr>
                        <th>Asunto</th>
                        <th>Estado</th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach var="ticket" items="${listTickets}">
                        <tr>
                            <td>
                                <div class="tooltip">
                                    ${ticket.asunto}
                                    <span class="tooltiptext">Hacer clic para ver más</span>
                                </div>
                            </td>

                            <td>
                                <c:choose>
                                    <c:when test="${ticket.estado == 'Pendiente'}">
                                        <span class="estado estado-pendiente">${ticket.estado}</span>
                                    </c:when>

                                    <c:when test="${ticket.estado == 'Cerrado'}">
                                        <span class="estado estado-cerrado">${ticket.estado}</span>
                                    </c:when>

                                    <c:when test="${ticket.estado == 'Terminado'}">
                                        <span class="estado estado-terminado">${ticket.estado}</span>
                                    </c:when>

                                    <c:otherwise>
                                        <span class="estado estado-asignado">${ticket.estado}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>

            </table>
        </div>
    </div>

    <script>
        function cerrarSesion() {
            if (confirm("¿Está seguro que desea cerrar sesión?")) {
                window.location.href = "vistas/logout.jsp";
            }
        }
    </script>

</body>
</html>
