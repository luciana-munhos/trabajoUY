<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<%@page import="servidor.DtUsuario" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Validar Password</title>

<jsp:include page="/WEB-INF/template/head.jsp" />

</head>
<body>
	  <%DtUsuario user = (DtUsuario) request.getAttribute("user"); %>

	
	
	<div class="container">

    <div class="row justify-content-center align-items-center" style="height: 100vh;">
    	<nav class="ms-3 mt-5" aria-label="breadcrumb">
			<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="/trabajouy/home">Home</a></li>
			<li class="breadcrumb-item"><a href="/trabajouy/consultarUsuario?nickname=<%=user.getNickname()%>">Mi perfil</a></li>
			</ol>
		</nav>	
       	<div class="col-lg-6 col-md-8">
            <form action=validarPassword method="POST">
                <fieldset class="border rounded-3 p-3">
                
                    <legend class="float-none w-auto px-3">Validar Contraseña</legend>
                        <!-- Nick/Mail input -->
                    <div class="form-outline mb-4">
                        <input  disabled name="nickMail" placeholder="<%=user.getNickname() %>" type="text" class="form-control" />
                        <label class="form-label">Nickname</label>
                    </div>

                    <!-- Contra input -->
                    <div class="form-outline mb-4">
                        <input required name="password" type="password" class="form-control"/>
                        <label class="form-label">Password</label>
                        <% 
                        	boolean verifico = (boolean) request.getAttribute("verifico");
                        	if(verifico){
                        %>
                        
							<div class="alert alert-danger" role="alert">
								Contraseña incorrecta					
					      	</div>	
				      	<% } %>
                    </div>

					<div class="mx-auto  col-5 gap-2 d-md-flex justify-content-md-end ">  		
						
						<a href="consultarUsuario?nickname=<%=user.getNickname() %>" class="btn-primary me-md-2 btn btn-lg" style="background-color: #d87093;">Cancelar</a>
						<button type="submit" class="btn-primary btn btn-lg" style="background-color: #d87093;">Aceptar</button>                        	
					                      	
					</div>
                </fieldset>
            </form>
        </div>
    </div>
</div>
	
</body>
</html>