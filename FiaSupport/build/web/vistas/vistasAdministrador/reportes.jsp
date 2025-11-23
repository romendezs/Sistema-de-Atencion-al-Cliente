<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Date"%>
<%@page import="dao.TicketDAO"%>
<%@page import="modelo.Ticket"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width">
  <link rel="icon" type="image/x-icon" href="../../imagenes/logo.png">
  <title>FIA Support</title>

  <!-- Chart.js CDN -->
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

  <style>
    *{margin:0;padding:0;box-sizing:border-box;}
    body{font-family:Arial,sans-serif;background-color:#f7e9df;}

    .header{
      background-color:#c94e47;color:white;
      padding:18px 25px;
      display:flex;justify-content:space-between;align-items:center;
    }
    .header-left{display:flex;gap:12px;}
    .header-left button{
      background:transparent;border:1px solid #f8c4c0;color:white;
      padding:10px 20px;font-weight:bold;border-radius:4px;
      cursor:pointer;transition:all .3s;
    }
    .header-left button:hover{
      background-color:rgba(255,255,255,.1);transform:translateY(-2px);
    }
    .logout-link{
      background-color:#8b4a46;color:white;padding:10px 18px;
      border-radius:6px;text-decoration:none;font-size:14px;
      display:flex;align-items:center;gap:6px;transition:.3s;font-weight:bold;
    }
    .logout-link:hover{
      background-color:#6d3835;transform:translateY(-2px);
      box-shadow:0 3px 8px rgba(0,0,0,.25);
    }

    /* ✅ contenedor más cómodo en 100% */
    .contenedor{
      width:95%;
      margin:16px auto;
      background:#fff;
      border-radius:8px;
      padding:18px;
      box-shadow:0 2px 10px rgba(0,0,0,0.08);
    }

    .titulo{
      font-size:24px;               /* ✅ un pelín menor */
      color:#c94e47;font-weight:bold;margin-bottom:10px;
    }
    .subtitulo{
      font-size:17px;               /* ✅ un pelín menor */
      color:#c94e47;font-weight:bold;margin:18px 0 8px;
    }

    .filtros-grid{
      display:grid;
      grid-template-columns:1fr 1fr;
      gap:10px;                     /* ✅ menos aire */
    }
    .filtro{
      display:flex;flex-direction:column;gap:5px;
      font-size:13px;
    }
    /* ✅ inputs y selects más compactos */
    .filtro input,
    .filtro select{
      padding:8px;
      border:1px solid #ddd;border-radius:6px;
      font-size:13px;
      height:34px;
    }

    .acciones{
      margin-top:10px;
      display:flex;justify-content:flex-end;gap:8px;
    }
    .btn{
      padding:7px 12px;             /* ✅ más compactos */
      border:none;border-radius:6px;
      font-weight:bold;cursor:pointer;font-size:13px;
    }
    .btn-sec{background:#6c757d;color:white;}
    .btn-prim{background:#c94e47;color:white;}

    .grid-reportes{
      display:grid;
      grid-template-columns:1fr 1fr;
      gap:14px;
      margin-top:10px;
    }
    .card{
      background:#fff;
      border:1px solid #eee;
      border-radius:8px;
      padding:12px;
      box-shadow:0 1px 6px rgba(0,0,0,0.06);
      height:300px;                /* ✅ menos alto */
      min-height:300px;
      display:flex;
      flex-direction:column;
      justify-content:center;
    }

    /* ✅ tamaños individuales de chart */
    #chartEstado{
      width:100% !important;
      height:240px !important;
    }
    #chartCategorias{
      width:100% !important;
      height:210px !important;
    }

    /* ✅ tablas más compactas */
    table{
      width:100%;
      border-collapse:collapse;
      margin-top:8px;
      font-size:13px;
    }
    th{
      background:#f8d7da;
      padding:8px;
      border-bottom:2px solid #f1b0b7;
      font-size:13px;
      color:#721c24;
    }
    td{
      padding:8px;
      border-bottom:1px solid #eee;
      font-size:13px;
    }

    .no-resultados{
      text-align:center;padding:18px;color:#666;font-style:italic;
    }
  </style>
</head>

<body>

  <div class="header">
    <div class="header-left">
      <button onclick="location.href='gestionarUsuarios.jsp'">Gestionar Usuarios</button>
      <button onclick="location.href='gestionarTickets.jsp'">Gestionar Tickets</button>
    </div>
    <a href="../logout.jsp" class="logout-link">Cerrar Sesión</a>
  </div>

  <div class="contenedor">
    <div class="titulo">Reportes y Estadísticas</div>

    <%
      // --------- leer filtros (GET) ----------
      String f1 = request.getParameter("fechaInicio");
      String f2 = request.getParameter("fechaFin");
      String estado = request.getParameter("estado");
      String tecnicoStr = request.getParameter("tecnico");

      Date fechaInicio = (f1 != null && !f1.isEmpty()) ? Date.valueOf(f1) : null;
      Date fechaFin = (f2 != null && !f2.isEmpty()) ? Date.valueOf(f2) : null;

      Integer idTecnico = null;
      if (tecnicoStr != null && !tecnicoStr.equals("Todos") && !tecnicoStr.isEmpty()) {
          try { idTecnico = Integer.parseInt(tecnicoStr); } catch(Exception ex){}
      }
      if (estado == null || estado.isEmpty()) estado = "Todos";

      TicketDAO dao = new TicketDAO();

      // --------- datos filtrados (tabla) ----------
      List<Ticket> ticketsFiltrados = dao.listarFiltrados(fechaInicio, fechaFin, estado, idTecnico);

      // --------- datos para gráficos ----------
      int enProceso = dao.contarEnProceso();
      int cerrados = dao.contarCerrados();
      List<Object[]> topCats = dao.top5Categorias();
      List<Object[]> porTecnico = dao.ticketsPorTecnico();
    %>

    <!-- FILTROS -->
    <form method="get" action="reportes.jsp">
      <div class="filtros-grid">
        <div class="filtro">
          <label>Fecha Inicio</label>
          <input type="date" name="fechaInicio" value="<%= (f1!=null?f1:"") %>">
        </div>

        <div class="filtro">
          <label>Fecha Fin</label>
          <input type="date" name="fechaFin" value="<%= (f2!=null?f2:"") %>">
        </div>

        <div class="filtro">
          <label>Nivel de Gravedad</label>
          <select name="gravedad">
            <option>Todos</option>
            <option>Baja</option>
            <option>Media</option>
            <option>Alta</option>
          </select>
        </div>

        <div class="filtro">
          <label>Estado</label>
          <select name="estado">
            <option <%= estado.equals("Todos")?"selected":"" %>>Todos</option>
            <option <%= estado.equals("En Proceso")?"selected":"" %>>En Proceso</option>
            <option <%= estado.equals("Cerrado")?"selected":"" %>>Cerrado</option>
          </select>
        </div>

        <div class="filtro">
          <label>Técnico Asignado (ID)</label>
          <select name="tecnico">
            <option>Todos</option>
            <option value="1" <%= "1".equals(tecnicoStr)?"selected":"" %>>1</option>
            <option value="2" <%= "2".equals(tecnicoStr)?"selected":"" %>>2</option>
            <option value="3" <%= "3".equals(tecnicoStr)?"selected":"" %>>3</option>
          </select>
        </div>
      </div>

      <div class="acciones">
        <button type="button" class="btn btn-sec" onclick="window.location.href='reportes.jsp'">Limpiar</button>
        <button type="submit" class="btn btn-prim">Aplicar Filtros</button>
      </div>
    </form>

    <!-- GRÁFICOS -->
    <div class="grid-reportes">

      <div class="card">
        <div class="subtitulo">Tickets por Estado</div>
        <canvas id="chartEstado"></canvas>
      </div>

      <div class="card">
        <div class="subtitulo">Top 5 - Categorías con Más Tickets</div>
        <canvas id="chartCategorias"></canvas>
      </div>

    </div>

    <!-- TABLA TÉCNICOS -->
    <div class="subtitulo">Tickets por Técnico</div>
    <table>
      <thead>
        <tr>
          <th>Técnico (ID)</th>
          <th>Asignados</th>
          <th>Resueltos</th>
        </tr>
      </thead>
      <tbody>
      <% if (porTecnico == null || porTecnico.isEmpty()) { %>
        <tr><td colspan="3" class="no-resultados">No hay datos de técnicos.</td></tr>
      <% } else {
           for (Object[] row : porTecnico) { %>
        <tr>
          <td><%= row[0] %></td>
          <td><%= row[1] %></td>
          <td><%= row[2] %></td>
        </tr>
      <% } } %>
      </tbody>
    </table>

    <!-- RESULTADOS (tabla con filtros) -->
    <div class="subtitulo">Resultados</div>
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Solicitante</th>
          <th>Título</th>
          <th>Técnico</th>
          <th>Fecha Creación</th>
          <th>Estado</th>
        </tr>
      </thead>
      <tbody>
      <% if (ticketsFiltrados == null || ticketsFiltrados.isEmpty()) { %>
        <tr><td colspan="6" class="no-resultados">No hay tickets con esos filtros.</td></tr>
      <% } else {
           for (Ticket t : ticketsFiltrados) {
              String estadoTxt = (t.getFechaCierre()==null) ? "En Proceso" : "Cerrado";
      %>
        <tr>
          <td><%= t.getIdTicket() %></td>
          <td><%= t.getSolicitante() %></td>
          <td><%= t.getTitulo() %></td>
          <td><%= t.getIdEmpleado() %></td>
          <td><%= t.getFechaCreacion() %></td>
          <td><%= estadoTxt %></td>
        </tr>
      <% } } %>
      </tbody>
    </table>

  </div>

  <!-- =================== SCRIPTS DE GRÁFICOS =================== -->
  <script>
    // -------- Tickets por Estado --------
    const enProceso = <%= enProceso %>;
    const cerrados = <%= cerrados %>;

    new Chart(document.getElementById("chartEstado"), {
      type: "pie",
      data: {
        labels: ["En Proceso", "Cerrados"],
        datasets: [{
          data: [enProceso, cerrados]
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: { legend: { position: "top" } }
      }
    });

    // -------- Top Categorías --------
    const catLabels = [
      <% for (int i=0; i<topCats.size(); i++) {
           Object[] r = topCats.get(i);
      %>
        "Cat <%= r[0] %>"<%= (i<topCats.size()-1?",":"") %>
      <% } %>
    ];

    const catData = [
      <% for (int i=0; i<topCats.size(); i++) {
           Object[] r = topCats.get(i);
      %>
        <%= r[1] %><%= (i<topCats.size()-1?",":"") %>
      <% } %>
    ];

    new Chart(document.getElementById("chartCategorias"), {
      type: "bar",
      data: {
        labels: catLabels,
        datasets: [{
          label: "Cantidad",
          data: catData
        }]
      },
options: {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      x: {
        grid: { display: false },
        ticks: { font: { size: 11 } },
        // ✅ separa más las barras
        barPercentage: 0.6,
        categoryPercentage: 0.6
      },
      y: {
        beginAtZero: true,
        ticks: { precision: 0, font: { size: 11 } }
      }
    },
    datasets: {
      bar: {
        maxBarThickness: 60  // ✅ límite de grosor
      }
    }
  }
    });
  </script>

</body>
</html>
