<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width">
  <link rel="icon" type="image/x-icon" href="../../imagenes/logo.png">
  <title>FIA Support</title>
  <style>
    *{
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }
    body{
      font-family: Arial, sans-serif;
      background-color: #f7e9df;
      margin: 0;
      padding: 0;
      color: #333;
    }
    /*--HEADER--*/
    .header{
      background-color: #c94e47;
      color: white;
      padding: 18px 25px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
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
      background-color: rgba(255, 255, 255, 0.1);
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
    .logout-icon{
      width: 18px;
      height: 18px;
      fill: white;
    }
    /*--Ver Reportes--*/
    .ver-reportes{
      width: 80%;
      margin: 0 auto;
      text-align: left;
      font-size: 26px;
      color: #c94e47;
      font-weight: bold;
    }
    /*--Subtitulo--*/
    .subtitulo{
      font-size: 18px;
      color: #c94e47;
      margin-bottom: 15px;
      font-weight: bold;
    }
    /*--Contenedor principal--*/
    .contenedor-principal{
      width: 80%;
      margin: 20px auto;
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 20px;
    }
    /*--Filtros--*/
    .seccion-filtros{
      background-color: white;
      border-radius: 8px;
      padding: 20px;
      box-shadow: 0 2px 10px rgba(0,0,0,0.08);
      grid-column: 1/ -1;
    }
    .filtros-grid{
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
      gap: 15px;
      margin-bottom: 15px;
    }
    .grupo-filtro{
      display: flex;
      flex-direction: column;
    }
    .grupo-filtro label{
      font-weight: bold;
      margin-bottom: 5px;
      color: #555;
      font-size: 14px;
    }
    .grupo-filtro select,
    .grupo-filtro input{
      padding: 10px 12px;
      border-radius: 6px;
      border: 1px solid #ddd;
      font-size: 14px;
      transition: all 0.3s ease;
    }
    .grupo-filtro select:focus,
    .grupo-filtro input:focus{
      outline: none;
      border-color: #c94e47;
      box-shadow: 0 0 0 2px rgba(201, 78, 71, 0.2);
    }
    /*--BOTONES--*/
    .botones-filtro{
      display: flex;
      gap: 10px;
      justify-content: flex-end;
    }
    .btn{
      padding: 10px 20px;
      border: none;
      border-radius: 6px;
      font-weight: bold;
      cursor: pointer;
      transition:all 0.3s ease;
    }
    .btn-primario{
      background-color: #c94e47;
      color: white;
    }
    .btn-primario:hover{
      background-color: #b0453e;
      transform: translateY(-2px);
    }
    .btn-secundario{
      background-color: #6c757d;
      color: white;
    }
    .btn-secundario:hover{
      background-color: #5a6268;
      transform: translateY(-2px);
    }
    /*--Tarjetas de Métricas--*/
    .seccion-metricas{
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
      gap: 20px;
      grid-column: 1 / -1;
    }
    
    .tarjeta-metrica{
      background-color: white;
      border-radius: 8px;
      padding: 20px;
      box-shadow: 0 2px 10px rgba(0,0,0,0.08);
      text-align: center;
      transition: transform 0.3s ease;
    }
    
    .tarjeta-metrica:hover{
      transform: translateY(-5px);
    }
    
    .valor-metrica{
      font-size: 2.5rem;
      font-weight: bold;
      color: #c94e47;
      margin: 10px 0;
    }
    
    .titulo-metrica{
      font-size: 14px;
      color: #666;
      text-transform: uppercase;
      letter-spacing: 1px;
    }
    
    .tendencia{
      font-size: 12px;
      margin-top: 5px;
    }
    
    .tendencia.positiva{
      color: #28a745;
    }
    
    .tendencia.negativa{
      color: #dc3545;
    }
    
    /*--Gráficos y Tablas--*/
    .seccion-graficos{
      background-color: white;
      border-radius: 8px;
      padding: 20px;
      box-shadow: 0 2px 10px rgba(0,0,0,0.08);
    }
    
    .seccion-tablas{
      background-color: white;
      border-radius: 8px;
      padding: 20px;
      box-shadow: 0 2px 10px rgba(0,0,0,0.08);
    }
    
    .subtitulo{
      font-size: 18px;
      color: #c94e47;
      margin-bottom: 15px;
      font-weight: bold;
    }
    
    /*--Gráfico de ejemplo--*/
    .grafico-contenedor{
      height: 300px;
      background-color: #f8f9fa;
      border-radius: 6px;
      display: flex;
      align-items: center;
      justify-content: center;
      margin-bottom: 15px;
      border: 1px dashed #ddd;
    }
    /*--Tablas--*/
    .tabla-reportes{
      width: 100%;
      border-collapse: collapse;
      margin-top: 10px;
    }
    .tabla-reportes th{
      background-color: #f8d7da;
      padding: 12px 10px;
      text-align: left;
      font-weight: bold;
      color: #721c24;
      border-bottom: 2px solid #f1b0b7;
      font-size: 14px;
    }
    .tabla-reportes td{
      padding: 10px;
      border-bottom: 1px solid #eee;
      font-size: 14px;
    }
    .tabla-reportes tr:hover td{
      background-color: #f9f9f9;
    }
    /*--Botones para exportar--*/
    .botones-exportacion{
      display: flex;
      gap: 10px;
      margin-top: 20px;
      justify-content: flex-end;
    }
    
    @media (max-width: 1024px){
      .contenedor-principal{
        grid-template-columns: 1fr;
      }
    }
    @media (max-width: 768px){
      .header{
        flex-direction: column;
        gap: 15px;
      }
    }
    .contenedor-principal{
      width: 95%;
    }
    .filtros-grid{
      grid-template-columns: 1fr;
    }
    .seccion-metricas{
      grid-template-columns: 1fr;
    }
  </style>
</head>
<body>
  <!--HEADER-->
  <div class="header">
    <div class="header-left">
      <button onclick="location.href='gestionarUsuarios.jsp'">Gestionar Usuarios</button>
      <button onclick="location.href='gestionarTickets.jsp'">Gestionar Tickets</button>
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
  <h1 class="ver-reportes">Reportes y Estadísticas</h1>
  <div class="contenedor-principal">
    <!--Sección de filtros-->
    <div class="seccion-filtros"> 
      <h2 class="subtitulo">Filtros de Reporte</h2> 
      <form id="formFiltros" method="post" action="reportes.jsp"> 
        <div class="filtros-grid"> 
          <div class="grupo-filtro"> 
            <label for="fechaInicio">Fecha Inicio</label> 
            <input type="date" id="fechaInicio" name="fechaInicio"> 
          </div> 
          <div class="grupo-filtro"> 
            <label for="fechaFin">Fecha Fin</label> 
            <input type="date" id="fechaFin" name="fechaFin"> 
          </div> <div class="grupo-filtro"> 
          <label for="tipoTicket">Nivel de Gravedad</label> 
          <select id="tipoTicket" name="tipoTicket"> 
            <option value="">Todos</option> 
            <option value="alta">Alta</option> 
            <option value="media">Media</option> 
            <option value="baja">Baja</option> 
          </select> 
          </div> 
          <div class="grupo-filtro"> 
            <label for="estado">Estado</label> 
            <select id="estado" name="estado"> 
              <option value="">Todos</option> 
              <option value="pendiente">Pendiente</option> 
              <option value="enproceso">En proceso</option>
              <option value="atendido">Atendido</option> 
              <option value="cancelado">Cancelado</option> 
              <option value="cerrado">Cerrado</option> </select> 
          </div> 
          <div class="grupo-filtro"> 
            <label for="tecnico">Técnico Asignado</label> 
            <select id="tecnico" name="tecnico">
              <option value="">Todos</option> 
              <!--Los tecnicos se cargan desde la base de datos--> 
            </select>
          </div>
        </div> 
        <!--Botones-->
        <div class="botones-filtro">
          <button type="button" class="btn btn-secundario" onclick="limpiarFiltros()">Limpiar</button>
          <button type="submit" class="btn btn-primario">Aplicar Filtros</button> 
        </div> 
      </form> 
    </div>
     <!-- Sección de Gráficos -->
    <div class="seccion-graficos">
      <h2 class="subtitulo">Tickets por Estado</h2>
      <div class="grafico-contenedor">
        <!-- Aquí iría un gráfico generado con una librería como Chart.js -->
        <p>Gráfico de distribución de tickets por estado</p>
      </div>
      
      <h2 class="subtitulo">Tickets por Categoría</h2>
      <div class="grafico-contenedor">
        <!-- Aquí iría un gráfico de barras o pastel -->
        <p>Gráfico de tickets por categoría</p>
      </div>
    </div>
    <!--Seccion de tablas de reporte-->
    <div class="seccion-tablas">
      <h2 class="subtitulo">Tickets por Técnico</h2>
      <table class="tabla-reportes">
        <thead>
          <tr>
            <th>Técnico</th>
            <th>Asignados</th>
            <th>Resueltos</th>
            <th>Tiempo Promedio</th>
            <th>Satisfacción</th>
          </tr>
        </thead>
        <tbody>
          <!--Los reportes se cargan desde la base de datos--> 
        </tbody>
      </table>
      
      <h2 class="subtitulo" style="margin-top: 20px;">Top 5 - Categorías con Más Tickets</h2>
      <table class="tabla-reportes">
        <thead>
          <tr>
            <th>Categoría</th>
            <th>Cantidad</th>
            <th>Porcentaje</th>
          </tr>
        </thead>
        <tbody>
        </tbody>
      </table>
    </div>
    <!--Botones para exportar-->
    <div class="botones-exportacion">
      <button type="button" class="btn btn-secundario" onclick="exportarPDF()"> Exportar PDF</button>
      <button type="button" class="btn btn-secundario" onclick="exportarExcel()"> Exportar Excel</button>
    </div>
  </div>
  <script>
    //Función para limpiar los filtros
    function limpiarFiltros()
    {
      document.getElementById('formFiltros').reset();
    }
    //Funciones de exportación 
    function exportarPDF()
    {
      alert('Exportación a PDF en desarrollo');
    }
    function exportarExcel()
    {
      alert('Exportación a Excel en desarrollo');
    }
    
    //Establecer fecha por defecto (últimos 30 días)
    window.onload = function()
    {
      const fechaFin = new Date();
      const fechaInicio = new Date();
      fechaInicio.setDate(fechaInicio.getDate()-30);
      
      document.getElementById('fechaFin').valueAsDate = fechaFin;
      document.getElementById('fechaInicio').valueAsDate = fechaInicio;
    };
  </script>
</body>
</html>   