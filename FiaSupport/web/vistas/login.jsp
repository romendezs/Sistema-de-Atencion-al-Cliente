<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/imagenes/logo.png">
    <title>FIA Support</title>

    <style>
        body {
            margin: 0;
            padding: 0;
            min-height: 100vh;
            background-color: #f7e9df;
            display: flex;
            justify-content: center;
            align-items: center;
            font-family: Arial, sans-serif;
        }
        .login-container{
            background-color: white;
            width: 600px;
            border-radius: 8px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.3);
            overflow: hidden;
        }
        .welcome-banner{
            background-color: #c94e47;
            color: white;
            padding: 12px 20px;
            font-size: 18px;
            font-weight: 600;
        }
        .content{
            padding: 40px 30px;
            background-color: #f5f5f5;
        }
        .logo-container{
            text-align: center;
            margin-bottom: 30px;
        }
        .login-form {
            background-color: #c97772;
            padding: 30px;
            border-radius: 4px;
            max-width: 400px;
            margin: 0 auto;
        }
        .form-title {
            color: white;
            font-size: 16px;
            font-weight: 600;
            margin-bottom: 20px;
            text-align: left;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            color: white;
            font-size: 14px;
            margin-bottom: 8px;
            font-weight: 500;
        }
        .form-group input {
            width: 100%;
            padding: 10px 12px;
            border: none;
            border-radius: 3px;
            font-size: 14px;
            background-color: white;
        }
        .form-group input::placeholder {
            color: #999;
            font-style: italic;
        }
        .submit-btn {
            width: 100%;
            padding: 12px;
            background-color: #8b4a46;
            color: white;
            border: none;
            border-radius: 3px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .submit-btn:hover {
            background-color: #6d3835;
        }
        .forgot-password {
            text-align: center;
            margin-top: 20px;
        }
        .forgot-password a {
            color: #c94e47;
            font-size: 13px;
            text-decoration: none;
        }
        .forgot-password a:hover {
            text-decoration: underline;
        }
        .error-message {
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 20px;
            font-size: 14px;
            text-align: center;
        }

        /* ===== MODAL Recuperar acceso ===== */
        .modal-overlay{
            display: none;
            position: fixed;
            inset: 0;
            background: rgba(0,0,0,0.45);
            justify-content: center;
            align-items: center;
            z-index: 999;
        }
        .modal-card{
            position: relative;
            width: 420px;
            max-width: 92vw;
            background: #fff;
            border-radius: 8px;
            padding: 22px 20px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.35);
            animation: pop .15s ease-out;
        }
        @keyframes pop { from{transform:scale(.95);opacity:.5} to{transform:scale(1);opacity:1} }

        .modal-close{
            position: absolute;
            top: 8px;
            right: 10px;
            border: none;
            background: transparent;
            font-size: 20px;
            cursor: pointer;
            color: #444;
        }
        .modal-close:hover{ color:#c94e47; }

        .modal-title{
            margin: 0 0 15px 0;
            font-weight: 700;
        }

        .recuperar-input {
            width: 100%;
            padding: 10px;
            border-radius: 4px;
            border: 1px solid #ddd;
            margin-bottom: 12px;
            font-size: 14px;
        }
        .btn-recuperar {
            width: 100%;
            padding: 10px;
            background-color: #c94e47;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
        }
        .btn-recuperar:hover {
            background-color: #a83e38;
        }

        .captcha-row{
            display: flex;
            gap: 8px;
            align-items: center;
            margin-bottom: 10px;
        }
        #captcha-canvas{
            border: 1px solid #ddd;
            border-radius: 4px;
            background: #f7f7f7;
        }
        .captcha-refresh{
            height: 36px;
            width: 36px;
            border-radius: 4px;
            border: 1px solid #ddd;
            background: #fff;
            cursor: pointer;
            font-size: 18px;
        }
        .mensaje-recuperar{
            margin-top: 10px;
            font-size: 14px;
            color: red;
        }
    </style>
</head>

<body>
    <div class="login-container">

        <div class="welcome-banner">¡Bienvenido!</div>

        <div class="content">

            <div class="logo-container">
                <img src="${pageContext.request.contextPath}/imagenes/Logo_FIASupport.png"
                     alt="logo" style="width: 150px"/>
            </div>

            <div class="login-form">
                <h2 class="form-title">Ingresa tu usuario</h2>

                <% 
                    String errorMsg = (String) request.getAttribute("error");
                    if (errorMsg != null) {
                %>
                    <div class="error-message">
                        <%= errorMsg %>
                    </div>
                <% } %>

                <form action="${pageContext.request.contextPath}/login" method="post">
                    <div class="form-group">
                        <label for="carnet">Carnet</label>
                        <input 
                            type="text" 
                            id="carnet" 
                            name="carnet" 
                            placeholder="Ejemplo de carnet valido: YQ05001"
                            required
                        />
                    </div>

                    <div class="form-group">
                        <label for="password">Contraseña</label>
                        <input 
                            type="password" 
                            id="password" 
                            name="password" 
                            placeholder="Digite su contraseña"
                            required
                        />
                    </div>

                    <button type="submit" class="submit-btn">Iniciar Sesión</button>
                </form>

                <div class="forgot-password">
                    <a href="#" onclick="mostrarRecuperar(); return false;">
                        ¿Problemas con el carnet o la contraseña?
                    </a>
                </div>
            </div>

        </div> <!-- /content -->
    </div> <!-- /login-container -->


    <!-- ===== MODAL Recuperar acceso ===== -->
    <div id="recuperar-modal" class="modal-overlay" aria-hidden="true">
        <div class="modal-card">
            <button class="modal-close" onclick="cerrarRecuperar()" aria-label="Cerrar">✕</button>

            <h3 class="modal-title">Recuperar acceso</h3>

            <!-- ANTES era correo, ahora es CARNET -->
            <input type="text" class="recuperar-input" id="correo"
                   placeholder="Ingresa tu carnet (Ejemplo: AZ99011)">

            <div class="captcha-row">
                <canvas id="captcha-canvas" width="180" height="60"></canvas>
                <button type="button" class="captcha-refresh" onclick="generarCaptcha()" title="Nuevo CAPTCHA">
                    ↻
                </button>
            </div>

            <input type="text" class="recuperar-input" id="captcha-input"
                   placeholder="Escribe el texto que ves arriba">

            <button type="button" class="btn-recuperar" onclick="validarRecuperacion()">
                Recuperar Cuenta
            </button>

            <p id="mensaje-recuperar" class="mensaje-recuperar"></p>
        </div>
    </div>


    <script>
        let captchaGenerado = "";

        function mostrarRecuperar() {
            const modal = document.getElementById("recuperar-modal");
            modal.style.display = "flex";
            modal.setAttribute("aria-hidden", "false");
            generarCaptcha();
            document.getElementById("mensaje-recuperar").textContent = "";
            document.getElementById("captcha-input").value = "";
            document.getElementById("correo").value = "";
        }

        function cerrarRecuperar(){
            const modal = document.getElementById("recuperar-modal");
            modal.style.display = "none";
            modal.setAttribute("aria-hidden", "true");
        }

        function generarCaptcha() {
            const chars = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789";
            captchaGenerado = "";
            for (let i = 0; i < 6; i++) {
                captchaGenerado += chars.charAt(Math.floor(Math.random() * chars.length));
            }
            dibujarCaptcha();
        }

        function dibujarCaptcha(){
            const canvas = document.getElementById("captcha-canvas");
            const ctx = canvas.getContext("2d");

            ctx.clearRect(0,0,canvas.width, canvas.height);
            ctx.fillStyle = "#f2f2f2";
            ctx.fillRect(0,0,canvas.width, canvas.height);

            for(let i=0;i<5;i++){
                ctx.strokeStyle = `rgba(0,0,0,${Math.random()*0.4})`;
                ctx.beginPath();
                ctx.moveTo(Math.random()*canvas.width, Math.random()*canvas.height);
                ctx.lineTo(Math.random()*canvas.width, Math.random()*canvas.height);
                ctx.stroke();
            }

            ctx.font = "24px Arial";
            for(let i=0;i<captchaGenerado.length;i++){
                const ch = captchaGenerado[i];
                const x = 15 + i*25 + (Math.random()*4);
                const y = 35 + (Math.random()*10);

                ctx.save();
                ctx.translate(x,y);
                ctx.rotate((Math.random()-0.5)*0.6);
                ctx.fillStyle = `rgb(${50+Math.random()*100},${50+Math.random()*100},${50+Math.random()*100})`;
                ctx.fillText(ch,0,0);
                ctx.restore();
            }

            for(let i=0;i<30;i++){
                ctx.fillStyle = `rgba(0,0,0,${Math.random()*0.3})`;
                ctx.beginPath();
                ctx.arc(Math.random()*canvas.width, Math.random()*canvas.height, 1.2, 0, Math.PI*2);
                ctx.fill();
            }
        }

        async function validarRecuperacion() {
            const carnet = document.getElementById("correo").value.trim(); // aquí es carnet
            const captchaIngresado = document.getElementById("captcha-input").value.trim();
            const mensaje = document.getElementById("mensaje-recuperar");

            // Validar carnet (7 caracteres, letras/números)
            if (carnet === "" || carnet.length !== 7) {
                mensaje.style.color = "red";
                mensaje.textContent = "Ingresa un carnet válido de 7 caracteres.";
                return;
            }

            if (captchaIngresado !== captchaGenerado) {
                mensaje.style.color = "red";
                mensaje.textContent = "CAPTCHA incorrecto. Inténtalo de nuevo.";
                generarCaptcha();
                return;
            }

            try{
                const resp = await fetch("${pageContext.request.contextPath}/recuperar", {
                    method: "POST",
                    headers: {"Content-Type": "application/x-www-form-urlencoded"},
                    // 👇 seguimos mandando "correo" para no cambiar servlet
                    body: new URLSearchParams({ correo: carnet })
                });

                const data = await resp.json();

                if(data.ok){
                    mensaje.style.color = "green";
                    mensaje.textContent =
                        "Tu solicitud ha sido ingresada. Un administrador reseteará tu contraseña pronto.";
                }else{
                    mensaje.style.color = "red";
                    mensaje.textContent = data.msg || "No se pudo procesar tu solicitud.";
                    generarCaptcha();
                }

            }catch(err){
                mensaje.style.color = "red";
                mensaje.textContent = "Error de conexión. Intenta más tarde.";
            }
        }
    </script>

</body>
</html>
