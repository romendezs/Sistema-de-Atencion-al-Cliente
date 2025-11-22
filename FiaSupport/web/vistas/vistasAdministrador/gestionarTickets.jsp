<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width">
  <title>FIA Support</title>
  <style>
    /* TODO TU CSS SIN CAMBIOS */
    *{ margin: 0;  padding: 0; box-sizing: border-box;}
    body{ font-family: Arial, sans-serif; background-color: #f7e9df; }
    .header{ background-color: #c94e47; color:white; padding:18px 25px; display:flex; justify-content:space-between; align-items:center;}
    .header-left{ display:flex; gap:12px;}
    .header-left button{ background:transparent; border:1px solid #f8c4c0; color:white; padding:10px 20px; font-weight:bold; border-radius:4px; cursor:pointer; transition:0.3s;}
    .header-left button:hover{ background-color:rgba(255,255,255,0.1); transform:translateY(-2px);}
    .logout-link{ background-color:#8b4a46; color:white; padding:10px 18px; border-radius:6px; text-decoration:none; font-size:14px; display:flex; align-items:center; gap:6px; transition:0.3s; font-weight:bold; }
    .logout-link:hover{ background-color:#6d3835; transform:translateY(-2px); box-shadow:0 3px 8px rgba(0,0,0,0.25);}
    .Gestionar{ width:80%; margin:20px auto 10px auto; font-size:26px; color:#c94e47; font-weight:bold;}
    .seccion-listado{ width:80%; margin:20px auto; background:#fff; border-radius:8px; padding:25px; box-shadow:0 2px 10px rgba(0,0,0,0.08);}
    .caja-buscar input{ padding:12px 15px; width:100%; max-width:400px; border-radius:6px; border:1px solid #ddd; }
    table{ width:100%; border-collapse:collapse; min-width:800px;}
    th{ background:#f8d7da; padding:15px 12px; border-bottom:2px solid #f1b0b7; font-size:14px; color:#721c24;}
    td{ padding:14px 12px; border-bottom:1px solid #eee; font-size:14px;}
    tr:hover td{ background:#f9f9f9;}
    .estado{ padding:8px 16px; border-radius:15px; font-size:12px; font-weight:bold; min-width:120px; display:inline-block;}
    .estado-sin-asignar{ background:#fff3cd; color:#856404; border:1px solid #ffeaa7;}
    .estado-en-proceso{ background:#d1ecf1; color:#0c5460; border:1px solid #bee5eb;}
    .estado-completado{ background:#d4edda; color:#155724; border:1px solid #c3e6cb;}
    .estado-cerrado{ background:#f8d7da; color:#721c24; border:1px solid #f5c6cb;}
    .action-icons{ display:flex; gap:8px; align-items:center;}
    .action-icon{ cursor:pointer; padding:6px; border-radius:4px; transition:0.2s;}
    .icon-edit{ color:#5bc0de; background:rgba(91,192,222,0.1);}
    .icon-delete{ color:#d9534f; background:rgba(217,83,79,0.1);}
    .no-resultados{ text-align:center; padding:40px; color:#666; font-style:italic; font-size:16px;}
    .solicitante{ font-weight:600; color:#333;}
    .titulo{ color:#555;}
    .asignacion{ color:#666;}
    .asignacion-sin-asignar{ color:#dc3545; font-style:italic;}
    .tiempo-estado{ font-size:11px; opacity:0.8; }
  </style>
</head>

<body>

  <!--HEADER-->
  <div class="header">
    <div class="header-left">
      <button onclick="location.href='gestionarUsuarios.jsp'">Gestionar Usuarios</button>
      <button onclick="location.href='reportes.jsp'">Ver Reportes y Estadísticas</button>
    </div>

    <a href="../logout.jsp" class="logout-link">
      <svg class="logout-icon" viewBox="0 0 24 24">
        <path d="M16 17l1.41-1.41L13.83 12l3.58-3.59L16 7l-5 5 5 5zm-9 4h6v-2H7V5h6V3H7c-1.1 
                 0-2 .9-2 2v14c0 1.1.9 2 2 2z"/>
      </svg>
      Cerrar Sesión
    </a>
  </div>

  <h1 class="Gestionar">Gestionar Tickets</h1>

  <div class="seccion-listado">
    <div class="caja-buscar">
      <input id="searchInput" type="text" placeholder="Buscar por carnet de solicitante" onkeyup="searchTable()">
    </div>

    <div class="tabla-tickets">
      <table id="tablaTickets">
        <thead>
          <tr>
            <th>Solicitante</th>
            <th>Título</th>
            <th>Asignación</th>
            <th>Estado</th>
            <th>Acciones</th>
          </tr>
        </thead>

        <tbody id="ticketsBody">
            
        <%/*
          //Base de datos de tickets
        %>

          <tr>
            <td colspan="5" class="no-resultados">No hay tickets registrados</td>
          </tr>

        <%
          //Base de datos
        %>

          <tr>
            <td class="solicitante"><%= t.getSolicitante() %></td>
            <td class="titulo"><%= t.getTitulo() %></td>

            <td class="asignacion <%= (t.getAsignadoA() == null || t.getAsignadoA().isEmpty()) ? "asignacion-sin-asignar" : "" %>">
              <%= (t.getAsignadoA() == null || t.getAsignadoA().isEmpty()) ? "Sin asignar" : t.getAsignadoA() %>
            </td>

            <td>
              <span class="estado 
                <%= (t.getEstado().equals("Sin Asignar")) ? "estado-sin-asignar" :
                    (t.getEstado().equals("En Proceso")) ? "estado-en-proceso" :
                    (t.getEstado().equals("Completado")) ? "estado-completado" :
                                                            "estado-cerrado" %>">
                <%= t.getEstado() %>
                <span class="tiempo-estado"><%= t.getTiempoTranscurrido() %></span>
              </span>
            </td>

            <td>
              <div class="action-icons">
                <span class="action-icon icon-edit" onclick="editarTicket(<%= t.getIdTicket() %>)">✏️</span>
                <span class="action-icon icon-delete" onclick="eliminarTicket(<%= t.getIdTicket() %>)">🗑️</span>
              </div>
            </td>
          </tr>

        <%
            }
          }*/
        %>

        </tbody>
      </table>
    </div>
  </div>

  <script>
    function searchTable() {
      const term = document.getElementById('searchInput').value.toLowerCase();
      const rows = document.querySelectorAll("#ticketsBody tr");

      rows.forEach(row => {
        let solicitante = row.children[0]?.textContent.toLowerCase();
        row.style.display = solicitante && solicitante.includes(term) ? "" : "none";
      });
    }

    function editarTicket(id) {
      if (confirm("¿Desea editar este ticket?")) {
        window.location.href = "editarTicket.jsp?id=" + id;
      }
    }

    function eliminarTicket(id) {
      if (confirm("¿Está seguro de eliminar este ticket?")) {
        window.location.href = "TicketServlet?action=delete&id=" + id;
      }
    }
  </script>

</body>
</html>
