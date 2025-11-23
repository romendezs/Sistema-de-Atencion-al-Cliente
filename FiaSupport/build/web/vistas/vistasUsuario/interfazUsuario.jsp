<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="modelo.Usuario"%>

<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    String nombreCompleto = (u != null) ? u.getNombreCompleto() : "";
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/x-icon" href="../../imagenes/logo.png">
    <title>FIA Support</title>

    <style>
        body {font-family: Arial, sans-serif;background-color: #f7e9df;margin: 0;padding: 0;}

        .header {background-color: #c94e47;color: white;padding: 18px 25px;display: flex;justify-content: space-between;align-items: center;box-shadow: 0 4px 12px rgba(0,0,0,0.25);}
        .header-left {font-size: 17px;font-weight: bold;}
        .logout-link {background-color:#8b4a46;color:white;padding:10px 18px;border-radius:6px;text-decoration:none;font-size:14px;font-weight:bold;transition:.3s;}
        .logout-link:hover {background-color:#6d3835;transform:translateY(-2px);box-shadow:0 3px 8px rgba(0,0,0,0.25);}

        .welcome {text-align: center;font-size: 26px;color: #c94e47;margin: 25px 0 15px 0;font-weight: bold;}

        .nuevo-ticket {background-color: white;width: 80%;max-width: 700px;margin: 0 auto 30px auto;padding: 25px;border-radius: 10px;box-shadow: 0 4px 20px rgba(0,0,0,0.2);}
        .nuevo-ticket h3 {color: #c94e47;font-size: 20px;margin-bottom: 20px;font-weight: bold;}

        .form-group {margin-bottom: 18px;}
        .form-group label {font-weight: bold;margin-bottom: 5px;display: block;font-size: 14px;color: #333;}
        .form-group input,.form-group textarea,.form-group select {width: 100%;padding: 12px;border-radius: 6px;border: 1px solid #ccc;font-size: 14px;}
        .form-group textarea {resize: vertical;height: 100px;}

        .mensaje-prioridad{margin-top:10px;font-weight:bold;font-size:14px;color:#8b4a46;padding:8px 10px;background:#fff3f2;border:1px solid #f2beb8;border-radius:6px;display:none;}

        .btn-crear {background-color: #8b4a46;color: white;padding: 12px 35px;border: none;border-radius: 6px;font-size: 15px;cursor: pointer;display: block;margin: 0 auto;transition: 0.3s;}
        .btn-crear:hover {background-color: #6d3835;}

        .tickets-section {width: 90%;max-width: 1000px;margin: auto;}
        .tickets-section h2 {color: #c94e47;font-size: 22px;font-weight: bold;margin-bottom: 15px;}

        .tickets-table {background-color: white;border-radius: 10px;box-shadow: 0 4px 15px rgba(0,0,0,0.15);overflow: hidden;}
        table {width: 100%;border-collapse: collapse;}
        th {background-color: #f2beb8;padding: 14px;text-align: left;font-size: 15px;color: #5a1e1e;}
        td {padding: 14px;border-bottom: 1px solid #eee;font-size: 14px;}
        tr:hover {background-color: #fceeee;}

        .estado {padding: 6px 12px;border-radius: 6px;font-size: 13px;font-weight: bold;display: inline-block;}
        .estado-pendiente { background-color: #ffb9b9; color: #7a1b1b; }
        .estado-cerrado   { background-color: #e8b5ad; color: #7a1b1b; }
        .estado-terminado { background-color: #93e99f; color: #0f551e; }
        .estado-asignado  { background-color: #ffd495; color: #7d4e00; }

        .tooltip { position: relative; cursor: pointer; }
        .tooltip .tooltiptext {
            visibility: hidden;opacity: 0;width: 180px;background-color: #333;color: white;padding: 6px;border-radius: 6px;
            transition: 0.3s;text-align: center;position: absolute;bottom: 120%;left: 50%;transform: translateX(-50%);
        }
        .tooltip:hover .tooltiptext { visibility: visible; opacity: 1; }

        .asunto-click {cursor: pointer;color: #c94e47;text-decoration: underline;transition: color 0.2s;}
        .asunto-click:hover {color: #8b4a46;}
    </style>
</head>

<body>

    <div class="header">
        <div class="header-left"><%= nombreCompleto %></div>
        <a href="<%= request.getContextPath() %>/logout" class="logout-link">Cerrar Sesión</a>
    </div>

    <h1 class="welcome">Bienvenido, <%= nombreCompleto %></h1>

    <div class="nuevo-ticket">
        <h3>+ Nuevo Ticket</h3>

        <form action="<%= request.getContextPath() %>/TicketServlet?action=create" method="post">

            <div class="form-group">
                <label>Categoría (ordenada por prioridad):</label>
                <select name="categoria" id="categoria" required>
                    <option value="">Seleccione una opción</option>
                    <option value="1">Revisión de Notas en Parciales (Prioridad 1)</option>
                    <option value="2">Revisión Tareas/Proyectos (Prioridad 2)</option>
                    <option value="3">Consulta sobre Clases y Horarios (Prioridad 3)</option>
                    <option value="4">Consultas varias (Prioridad 4)</option>
                    <option value="5">Motivo de inasistencia (Prioridad 5)</option>
                    <option value="6">Cambio de grupo (Lab/Discu/Teórico) (Prioridad 6)</option>
                </select>
                <div id="mensajePrioridad" class="mensaje-prioridad"></div>
            </div>

            <div class="form-group">
                <label>Fecha del suceso:</label>
                <input type="date" name="fechaSuceso" required>
            </div>

            <div class="form-group">
                <label>Descripción:</label>
                <textarea name="descripcion" required placeholder="Explique el motivo de la solicitud..."></textarea>
            </div>

            <button type="submit" class="btn-crear">Crear</button>
        </form>

        <c:if test="${not empty sessionScope.msg}">
            <div style="margin-top:12px;color:green;font-weight:bold;">
                ${sessionScope.msg}
            </div>
            <c:remove var="msg" scope="session"/>
        </c:if>
        <c:if test="${not empty sessionScope.error}">
            <div style="margin-top:12px;color:#c94e47;font-weight:bold;">
                ${sessionScope.error}
            </div>
            <c:remove var="error" scope="session"/>
        </c:if>
    </div>

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
                                    <span class="asunto-click"
                                          onclick="alert('Descripción: ${ticket.descripcion}')">
                                        ${ticket.titulo}
                                    </span>
                                    <span class="tooltiptext">Click para ver detalle rápido</span>
                                </div>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${ticket.estado == 'Pendiente'}">
                                        <span class="estado estado-pendiente">${ticket.estado}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="estado estado-cerrado">${ticket.estado}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>

                    <c:if test="${empty listTickets}">
                        <tr><td colspan="2" style="padding:18px;color:#666;font-style:italic;">No tienes tickets aún.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>

    <script>
        const categoriaSel = document.getElementById("categoria");
        const msgPrioridad = document.getElementById("mensajePrioridad");

        categoriaSel.addEventListener("change", function() {
            const prioridad = parseInt(this.value);
            let msg = "";

            if (prioridad === 1 || prioridad === 2) {
                msg = "⚠️ Prioridad Alta: atención en 1 a 2 días hábiles.";
            } else if (prioridad === 3 || prioridad === 4) {
                msg = "⏳ Prioridad Media: resolución en 3 a 5 días hábiles.";
            } else if (prioridad === 5 || prioridad === 6) {
                msg = "📅 Prioridad Baja: resolución en 6 a 7 días hábiles.";
            }

            if (msg) {
                msgPrioridad.style.display = "block";
                msgPrioridad.textContent = msg;
            } else {
                msgPrioridad.style.display = "none";
                msgPrioridad.textContent = "";
            }
        });
    </script>

</body>
</html>
