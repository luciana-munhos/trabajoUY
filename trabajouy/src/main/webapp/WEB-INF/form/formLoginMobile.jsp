<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Iniciar sesión</title>
<jsp:include page="/WEB-INF/template/head.jsp" />

</head>
<body>
	<div class="container my-5 p-5">
		<div class="row">
            <div class="text-center col">
                <a class="mx-auto" href="/trabajouy/home">
                    <img class="w-75 mx-auto" src="media/img/logo-sin-fondo.png" alt="">
                </a>
            </div>            
        </div>
		<%
		if(request.getAttribute("invalid_data") != null && (boolean) request.getAttribute("invalid_data")){
		%>
		<div class="alert alert-danger" role="alert">
		  Cualquiera pusiste.
		</div>	
		<%	
		}
		%>
        <form action="iniciar-sesion" method="POST">
            <fieldset class="border rounded-3 p-3">
                <legend class="float-none w-auto px-3">Iniciar Sesión</legend>
                    <!-- Nick/Mail input -->
                <div class="form-outline mb-4 col-lg-6 col-md-8 mx-auto">
                    <input required name="nickMail" placeholder="Ingrese su nickname o correo" type="text" class="form-control" />
                    <label class="form-label">Nickname/Correo</label>
                </div>

                <!-- Contra input -->
                <div class="row mb-1">
                    <div class="col-lg-6 col-md-8 mx-auto">
                    <div class="form-outline">
                        <input required name="password" type="password" class="form-control" />
                        <label class="form-label">Password</label>
                    </div>
                    </div>
                </div>

                <div class="text-center pt-4">
					<input class="mx-auto btn btn-custom-pink btn-lg" type="submit" value="Entrar" onclick="submit()">
                </div> 

            </fieldset>
          </form>
    </div>
	
</body>
</html>