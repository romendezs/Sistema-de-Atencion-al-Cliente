<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>FIA Support</title>
        <style>
            body {
            margin: 0;
            padding: 0;
            height: 100vh;
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
            .header{
                background-color: #c94e47;
                color:white;
                padding: 15px 20px; 
                display: flex;
                align-items: center;
                justify-content: space-between;
            }
            .header-title{
                display: flex;
                align-items: center;
                gap:  8px;
                font-size: 14px;
            }
            .close-btn{
                background: none;
                border: none; 
                color: white; 
                font-size:  20px ;
                cursor: pointer;
                padding: 0;
                width:  20px;
                height:  20px;
                display: flex;
                align-items: center;
                justify-content: center; 
            }
            .welcome-banner{
                background-color: #c94e47;
                color :white; 
                padding: 12px 20px;
                font-size: 18px; 
                font-weight: 600;
            }
            .content{
                padding:  40px 30px;
                background-color: #f5f5f5;
            }
            .logo-container{
                text-align: center;
                margin-bottom: 30px;
            }
            .logo{
                width: 120px;
                height: 120px;
                margin:  0 auto;
            }
            .logo svg{
                width: 100%;
                height: 100%;
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
        /* Contenedor del formulario de recuperación */
        #recuperar-container {
            display: none; /* Oculto al inicio */
            background: #ffffff;
            padding: 20px;
            border-radius: 6px;
            margin-top: 20px;
            box-shadow: 0 3px 10px rgba(0,0,0,0.2);
        }

        /* Inputs del formulario */
        .recuperar-input {
            width: 100%;
            padding: 10px;
            border-radius: 4px;
            border: none;
            margin-bottom: 12px;
        }

        /* Botón recuperar */
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

        /* Caja del captcha */
        #captcha-box {
            background: #f2f2f2;
            padding: 10px;
            border-radius: 4px;
            font-weight: bold;
            text-align: center;
            margin-bottom: 10px;
        }

        </style>
    </head>
    <body>
         <div class="login-container">


        <div class="welcome-banner">
            ¡Bienvenido!
        </div>

        <div class="content">
            <div class="logo-container" style="text-align: center; margin-top:20px">
                <img src="${pageContext.request.contextPath}/imagenes/Logo_FIASupport.png" alt="logo" style="width: 150px"/>               
                </div>
            </div>

            <div class="login-form">
                <h2 class="form-title">Ingresa tu usuario</h2>

                <% 
                    String error = request.getParameter("error");
                    if (error != null && error.equals("1")) {
                %>
                    <div class="error-message">
                        Usuario o contraseña incorrectos
                    </div>
                <% } %>

                <form action="login" method="POST">
                    <div class="form-group">
                        <label for="carnet">Carnet</label>
                        <input 
                            type="text" 
                            id="carnet" 
                            name="carnet" 
                            placeholder="Ejemplo: carnet_Ejemplo-N203601"
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

                    <!---<button type="submit" class="submit-btn">Iniciar Sesión</button>-->
                    <!---<a href="vistasAdministrador/gestionarUsuarios.jsp" class="submit-btn">Iniciar Sesión</a>--->
                    <a href="vistasUsuario/interfazUsuario.jsp" class="submit-btn">Iniciar Sesión</a>
                </form>
            </div>
         </form>

         <div class="forgot-password">
             <a href="#" onclick="mostrarRecuperar(); return false;">
                 ¿Problemas con el carnet o la contraseña?
             </a>
         </div>
    </div>
            <!-- FORMULARIO DESPLEGABLE -->
            <div id="recuperar-container">
                    <h3 style="margin-bottom: 15px;">Recuperar acceso</h3>

                    <input type="email" class="recuperar-input" id="correo" placeholder="Ingresa tu correo institucional">

                    <!-- CAPTCHA -->
                    <div id="captcha-box"></div>
                    <input type="text" class="recuperar-input" id="captcha-input" placeholder="Escribe el número que ves arriba">

                    <button class="btn-recuperar" onclick="validarRecuperacion()">
                        Recuperar Cuenta
                    </button>

                    <p id="mensaje-recuperar" style="color:red; margin-top:10px;"></p>
                </div>

        </div>
    </div>
    <script>
    let captchaGenerado = 0;

    function mostrarRecuperar() {
        const box = document.getElementById("recuperar-container");

        if (box.style.display === "none" || box.style.display === "") {
            box.style.display = "block";
            generarCaptcha();
        } else {
            box.style.display = "none";
        }
    }

    function generarCaptcha() {
        captchaGenerado = Math.floor(1000 + Math.random() * 9000); // 4 dígitos
        document.getElementById("captcha-box").textContent = "CAPTCHA: " + captchaGenerado;
    }

    function validarRecuperacion() {
        const correo = document.getElementById("correo").value;
        const captchaIngresado = document.getElementById("captcha-input").value;
        const mensaje = document.getElementById("mensaje-recuperar");

        if (correo.trim() === "") {
            mensaje.textContent = "Ingresa un correo válido.";
            return;
        }

        if (captchaIngresado != captchaGenerado) {
            mensaje.textContent = "CAPTCHA incorrecto. Inténtalo de nuevo.";
            generarCaptcha();
            return;
        }

        mensaje.style.color = "green";
        mensaje.textContent = "Solicitud enviada correctamente.";
    }
    </script>

    </body>
</html>
