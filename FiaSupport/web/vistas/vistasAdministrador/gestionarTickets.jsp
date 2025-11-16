<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width">

<title>FIA Support</title>
  <style>
    *{ margin: 0;  padding: 0; box-sizing: border-box;}
    
    body{
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
    .logout-link{
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
    .logout-link:hover{
      background-color: #6d3835;
      transform: translateY(-2px);
      box-shadow: 0 3px 8px rgba(0,0,0,0.25);
    }
    .logout-icon {
      width: 18px;
      height: 18px;
      fill: white;
    }
    /*--Gestionar Ticket--*/
    .Gestionar{
      width: 80%;
      margin: 0 auto;
      text-align: left;
      font-size: 26px;
      color: #c94e47;
      font-weight: bold;
    }
    
    /*--TABLA--*/
   .seccion-listado{
      width: 80%;
      margin: 30px auto 0 auto;
     background-color: white;
     border-radius: 8px;
     padding: 20px;
     box-shadow: 0 2px 10px rgba(0,0,0,0.08);
    }
    .caja-buscar{
      margin-bottom: 20px;
    }
     .caja-buscar input{
      padding: 12px 15px;
      width: 100%;
      max-width: 400px;
      border-radius: 6px;
      border: 1px solid #ddd;
      font-size: 14px;
      transition: all 0.3s ease;
    }
    .caja-buscar input:focus{
      outline: none;
      border-color: #c94e47;
      box-shadow: 0 0 0 2px rgba(201, 78, 71, 0.2);
    }
    
    .tabla-tickets{
      margin-top: 15px;
      overflow: hidden;
      border-radius: 6px;
      box-shadow: 0 2px 4px tgba(0,0,0,0.05);
      overflow-x: auto;
    }
    table{
      width: 100%; 
      border-collapse: collapse;
      min-width: 700px;
    }
    th{
      background-color: #f8d7da;
      padding: 15px 12px;
      text-align: left;
      font-weight: bold;
      color: #721c24;
      border-bottom: 2px solid #f1b0b7;
    }
    td{
      padding: 12px;
      border-bottom: 1px solid #eee;
      transition: background-color 0.2s;
    }
    tr:hover td{
      background-color: #f9f9f9;
    }
    
    /*--ESTADO DE TICKETS--*/
    .estado{
      display: inline-block;
      padding: 6px 12px;
      border-radius: 12px;
      font-size: 12px;
      font-weight: bold;
      text-align: center
    }
    .estado-asignar{
      background-color: #fff3cd;
      color: #856404;
    }
    .estado-proceso{
      background-color: #d1ecf1;
      color: #0c5460;
    }
    .estado-completado{
      background-color: #d4edda;
      color: #155724;
    }
    estado-cerrado{
      background-color: #f8d7da;
      color: #721c24
    }
    
    /*--MENSAJE CUANDO NO HAY RESULTADOS--*/
    .no-resultados{
      text-align: center;
      padding: 20px;
      color: #666;
      font-style: italic;
    }
    /* Responsive */
   
    
  </style>
</head>
<body>
  <!--HEADER-->
  <div class="header">
    <div class="header-left">
      <button onclick="location.href='gestionarUsuarios.jsp'">Gestionar Usuarios</button>
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
  <h1 class="Gestionar">Gestionar Ticket</h1>
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
          </tr>
        </thead>
        
        <tbody id="ticketsBody">
          <%-- Aca se deben cargar dinamicamente los tickets, cuando los agreguen, asegurense de que este correctamente enlazado con los paquetes
          <%
            List<Ticket> lista = ticketBd.obtenerTodos();
            for (Ticket t : lista) {
          %>
              <tr>
                <td><%= t.getSolicitante() %></td>
                <td><%= t.getTitulo() %></td>
                <td><%= t.getAsignadoA() %></td>

                <td>
                  <span class="estado 
                    <%= t.getEstado().equals("Asignar") ? "estado-asignar" : "" %>
                    <%= t.getEstado().equals("Proceso") ? "estado-proceso" : "" %>
                    <%= t.getEstado().equals("Completado") ? "estado-completado" : "" %>
                    <%= t.getEstado().equals("Cerrado") ? "estado-cerrado" : "" %>
                  ">
                    <%= t.getEstado() %>
                  </span>
                </td>
              </tr>
          <%
            }
          %>
          --%>
        </tbody>
    </div>
  </div>
      <script>
    // Busca en la tabla
    function searchTable() {
      const term = document.getElementById('searchInput').value.toLowerCase();
      const rows = document.querySelectorAll("#ticketsBody tr");

      let visibleCount = 0;

      rows.forEach(row => {
        let solicitante = row.children[0].textContent.toLowerCase();

        if (solicitante.includes(term)) {
          row.style.display = "";
          visibleCount++;
        } else {
          row.style.display = "none";
        }
      });

      // Si no hay resultados, mensaje
      if (visibleCount === 0) {
        document.getElementById("ticketsBody").innerHTML =
          '<tr><td colspan="4" class="no-resultados">No se encontraron tickets</td></tr>';
      }
    }
  </script>
</body>
</html>