<%@ page language="java" contentType="text/html" 
	pageEncoding="UTF-8"%>    
<!DOCTYPE html>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Map" %>
<%@page import="servidor.DtUsuario" %>
<%@page import="java.util.Set" %>
<%@page import="servidor.DtEmpresa" %>
<%@page import="servidor.DtPostulante" %>


<html>

<head>
	<meta charset="UTF-8">

	<link rel="stylesheet" href="media/styles/modificarE.css">
	<link rel="stylesheet" href="media/styles/general.css">

	<title>Modificar Usuario</title>
	<jsp:include page="/WEB-INF/template/head.jsp" />

</head>

<body>

	<jsp:include page="/WEB-INF/template/header.jsp" />

	<div class="container my-4 p-4">

		<% DtUsuario user=(DtUsuario) request.getAttribute("user"); %>
			<form class="border row g-3 needs-validation" novalidate id="miFormulario"
				action="modificarUsuario?modificar=true" method="POST" enctype="multipart/form-data">
				<p class="  float-none w-auto px-3">Modificar Datos Basicos</p>
				<div class="form-outline mb-4">
					<div class="form-outline mb-4">
						<div class="contenedor">
							<img class="imagen" src="/trabajouy/imagenes?id=data/<%=user.getImagen()%>" alt="">
							<div class="informacion">
								<input id="img" name="img" type="file" accept="image/jpeg, image/png"
									class="form-control" />
							</div>
						</div>
					</div>
				</div>
				<br>
				<!-- nickName -->
				<div class="col-md-6">
					<label for="mail" class="form-label">Correo Electronico</label>
					<input id="mail" name="mail" value="<%=user.getCorreo() %>" type="text" class="form-control"
						disabled />
				</div>
				<div class="col-md-6">
					<label for="nick" class="form-label">Nickname</label>
					<input required id="nick" name="nick" value="<%=user.getNickname() %>" type="text"
						class="form-control" disabled />
				</div>
				<!-- Nombre -->
				<div class="col-md-4">
					<label for="nombre" class="form-label">Nombre</label>
					<input id="nombre" name="nombre" value="<%=user.getNombre() %>" type="text" class="form-control"
						required pattern="^[A-Za-zÁÉÍÓÚÑáéíóúñüÜ\s'\-]+$" title="Solo letras son permitidas" />
					<div class="invalid-feedback">
						solo puede contener letras
					</div>
				</div>
				<div class="col-md-4">
					<label for="apellido" class="form-label">Apellido</label>
					<input id="apellido" name="apellido" value="<%=user.getApellido() %>" type="text"
						class="form-control" required pattern="^[A-Za-zÁÉÍÓÚÑáéíóúñüÜ\s'\-]+$" title="Solo letras son permitidas" />
					<div class="invalid-feedback">
						solo puede contener letras
					</div>
				</div>
				<div class="col-md-4">
					<label for="password" class="form-label">Password</label>
					<input id="password" name="password" value="<%=user.getContra()%>" type="password"
						class="form-control" required pattern="[^ ]+" title="No se permiten espacios en blanco" />
					<div class="invalid-feedback">
						Contraseña no valida (no se permiten espacios)
					</div>
				</div>
				<%if(user instanceof DtEmpresa){ DtEmpresa userEmp=(DtEmpresa) user; // Conversión a DTEmpresa %>

					<input id="estadoU" name="estadoU" value="esEmpresa" type="hidden" class="form-control"
						onchange="validarForm()" />
					<div class="form-outline mb-8">
						<label for="link" class="form-label">Link Web </label>
						<input id="link" name="link" value="<%=userEmp.getLink() %>" type="text" class="form-control"
							pattern="^(?!.*\s).*$" title="No se permiten espacios entre las letras"  />
						<div class="invalid-feedback">
							No se permiten espacios entre las letras
						</div>
					</div>
					<div class="form-outline mb-4">
						<label for="desc" class="form-label">Descripcion</label>
						<textarea maxlength="1000" required id="desc" name="desc" class="form-control" rows="4"
							cols="50"><%=userEmp.getDescripcion()%></textarea>
						<div class="invalid-feedback">
							Descripción debe tener menos de 1000 caracteres.
						</div>
					</div>
					<%} else{ DtPostulante userPost=(DtPostulante) user; String fecha=(String)
						request.getAttribute("miFecha"); %>

						<input id="estadoU" name="estadoU" value="esPostulante" type="hidden" class="form-control" />
						<div class="col-md-6">
							<label for="fecha" class="form-label">Fecha de Nacimiento </label>
							<input required id="fecha" name="fecha" value="<%=fecha%>" type="date" class="form-control"
								onchange="validarFecha()" />
							<div class="invalid-feedback">
								la fecha debe ser mayor a 1900 y menor a la fecha actual
							</div>
						</div>
						<div class="col-md-6">
							<label for="nac" class="form-label">Nacionalidad </label>
							<input required id="nac" name="nac" value="<%=userPost.getNacionalidad() %>" type="text"
								class="form-control" required pattern="^[A-Za-zÁÉÍÓÚÑáéíóúñüÜ\s'\-]+$" title="Solo letras son permitidas" />
							<div class="invalid-feedback">
								Solo puede contener letras
							</div>
						</div>
						<% } %>
							<div id="nombre-error" class="invalid-feedback " style="display: none; font-size: 18px;">
								No se han hecho modificaciones.
							</div>
							  <!-- Botón -->
							<div class="text-center pt-0">
								<a href="consultarUsuario?nickname=<%=user.getNickname() %>"
									class="mx-auto btn btn-lg mr-9 mb-4" style="background-color: #d87093;">Cancelar</a>
								<button type="submit" class="mx-auto btn btn-lg ml-15 mb-4"
									style="background-color: #d87093;">Validar y Enviar</button>
							</div> 
			</form>
	</div>
	<script src="media/scripts/modificarUsuario.js"></script>
			<script src="media/scripts/consultarUsuario.js"></script>
	
	<jsp:include page="/WEB-INF/template/footer.jsp" />
</body>
</html>