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
	<div class="container p-3">
		<div class="row mt-4">
            <div class="text-center col">
                <a class="mx-auto" href="/trabajouy/home">
                    <img class="w-25 mx-auto" src="media/img/logo-sin-fondo.png" alt="">
                </a>
            </div>            
        </div>
        <div class="row text-center">
			<div class="col">
	        	<p class="mb-0">¿No tenés una cuenta?</p>
	        	<a href="/trabajouy/formReg">Registrate ahora</a>
	        </div>
        </div>
		<%
		if((boolean) request.getAttribute("invalid_data")){
		%>
		<div class="alert alert-danger" role="alert">
		  Cualquiera pusiste.
		</div>	
		<%	
		}
		%>
		<div class="row w-75 mx-auto">
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
    </div>
	
</body>
</html>