<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registrar Usuario</title>
<jsp:include page="/WEB-INF/template/head.jsp" />
</head>

<body>
    <div class="container p-3">
        <div class="row mt-3">
            <div class="text-center col">
                <a class="mx-auto" href="/trabajouy/home">
                    <img class="w-25 mx-auto" src="media/img/logo-sin-fondo.png" alt="">
                </a>
            </div>            
        </div>
        <div class="row">
        	<div class="col text-center">
        		<p class="mb-0">¿Ya tenés una cuenta?</p>
        		<a href="/trabajouy/formLogin">Logueate ahora</a>
        	</div>
        </div>
        <div class="row w-75 mx-auto">        
	        <form action="registrarse" method="POST" enctype="multipart/form-data" id="formulario">
	            <fieldset class="border rounded-3 p-3">
	                <legend class="float-none w-auto px-3">Registrar Usuario</legend>
	                <%if(request.getParameter("estado")!=null && request.getParameter("estado").equals("invalido")){ %>
	                <div class="bg-danger text-uppercase mx-auto border rounded-3 py-3 mb-3">
	                    Debe ingresar un nuevo par nickname, email. Pues ya existe un usuario con dichas credenciales.
	                </div>
	                <%}  %>
	                <!-- Nick input -->
	                <div class="form-outline mb-2">
	                    <input name="nick" id="nick" placeholder="Nickname unico en el sistema" type="text" class="form-control"
	                        required />
	                    <label class="form-label">Nickname</label>
	                </div>
	                <div id="nickNO" class="text-danger mt-0 mb-2" style="display: none;">
	                    El nickname NO es valido pues ya se encuentra en uso.
	                </div>
	                <div id="nickSI" class="text-success mt-0 mb-2" style="display: none;">
	                    Nickname valido.
	                </div>
	                <script>
	                	document.getElementById("nick").addEventListener("blur", function() {
	                    var nick = this.value;
	
	                    //Sol AJAX
	                    var xhr = new XMLHttpRequest();
	                    xhr.onreadystatechange = function() {
	                        if (xhr.readyState === 4 && xhr.status === 200) {
	                            var xmlResponse = xhr.responseXML;
	                            var valido = xmlResponse.getElementsByTagName("valid")[0].textContent;
	
	                            if (valido === "true") {
	                            	document.getElementById("nickSI").style.display = "block";
	                            	document.getElementById("nickNO").style.display = "none";
	                            } else {
	                            	document.getElementById("nickSI").style.display = "none";
	                            	document.getElementById("nickNO").style.display = "block";
	                            }
	                        }
	                    };
	
	                    xhr.open("GET", "registrarse?nick=" + nick, true);
	                    xhr.send();
	                });
	                </script>
	                <!-- 2 column grid layout para nombre y apellido dos filas en celular -->
	                <div class="row mb-4">
	                    <div class="col-md-6 col-12">
	                        <div class="form-outline">
	                            <input name="nombre" type="text" pattern="^[A-Za-zÁÉÍÓÚÑáéíóúñüÜ\s'\-]+$" title="Solo letras permitidas" class="form-control" <% if (request.getParameter("estado")!=null && request.getParameter("estado").equals("invalido")) { %>
	    						value="<%= request.getParameter("nombre") %>" <% } %> required />
	                            <label class="form-label">Nombre</label>
	                        </div>
	                    </div>
	                    <div class="col-md-6 col-12">
	                        <div class="form-outline">
	                            <input name="apellido" type="text" class="form-control" <% if (request.getParameter("estado")!=null && request.getParameter("estado").equals("invalido")) { %>
	    						value="<%= request.getParameter("apellido") %>" <% } %> required pattern="^[A-Za-zÁÉÍÓÚÑáéíóúñüÜ\s'\-]+$" title="Solo letras son permitidas"/>
	                            <label class="form-label">Apellido</label>
							</div>
	                    </div>
	                </div>
	
	                <!-- Email input -->
	                <div class="form-outline mb-4">
	                    <input pattern="[^ ]+" name="mail" type="email" class="form-control" required />
	                    <label class="form-label">Email</label>
	                </div>
	
	                <!-- Contra input -->
	                <div class="row mb-4">
	                    <div class="col-md-6 col-12">
	                        <div class="form-outline">
	                            <input id="con" name="con" type="password" <% if (request.getParameter("estado")!=null && request.getParameter("estado").equals("invalido")) { %>
	    						value="<%= request.getParameter("contra") %>" <% } %> class="form-control" required pattern="[^ ]+" title="No se permiten espacios en blanco"/>
	                            <label class="form-label">Password</label>
	                        </div>
	                    </div>
	                    <div class="col-md-6 col-12">
	                        <div class="form-outline">
	                            <input id="con2" name="con2" type="password" class="form-control" <% if (request.getParameter("estado")!=null && request.getParameter("estado").equals("invalido")) { %>
	    						value="<%= request.getParameter("contra2") %>" <% } %> required />
	                            <label class="form-label">Confirmar Password</label>
	                        </div>
	                        <div id="passNO" class="text-danger" style="display: none;">
	                            Las passwords no coinciden
	                        </div>
	                    </div>
	                </div>
	
	                <!--File input-->
	                <% if (request.getParameter("estado")!=null &&  request.getParameter("estado").equals("invalido") && session.getAttribute("pathImg") != null) { %>
	                <div class="form-outline mb-4">
	    
	    				<img id="imagenPreseleccionada" src="<%= (String) session.getAttribute("pathImg") %>" style="max-width: 25%;">
	    				<p class="text-success">Si desea modificar la imagen, seleccione una nueva.</p>
	
					</div>
					<%} %>
	                <div class="form-outline mb-4">
	                    <input name="img" type="file" accept="image/jpeg image/png" class="form-control" value="<% request.getParameter("img"); %>"/>
	                    <label class="form-label">Imagen Opcional</label>
	                </div>
	
	                <!--Seleccionar empresa o postulante-->
	                <div class="form-outline col-12 mb-5">
	                    <select name="elegir" class="select form-select" id="elegir-usr" required>
	                        <option value="" disabled selected>-- Registrarse como --</option>
	                        <option value="1">Empresa</option>
	                        <option value="2">Postulante</option>
	                    </select>
	                    <script>
	                    	<% if(request.getParameter("estado")!=null && request.getParameter("estado").equals("invalido")){ %>
						    	var tipo = "<%= request.getParameter("elegir") %>";		
						    	document.getElementById("elegir-usr").value = tipo;
						    <% } %>
						</script>
	                </div>
	                <div class="row mb-4" id="postulanteSelected" style="display: none;">
	                    <div class="col-md-6 col-12">
	                        <div class="form-outline">
	                            <input name="fecha" id="fecha" type="date" class="form-control" <% if (request.getParameter("estado")!=null && request.getParameter("estado").equals("invalido")) { %>
	    						value="<%= request.getParameter("fecha") %>" <% } %>/>
	                            <label class="form-label">Fecha de Nacimiento</label>
	                        </div>
	                        <div id="fechaError" class="text-danger" style="display: none;">
	                            Las fecha de nacimiento es obligatoria y debe ser la propia.
	                        </div>
	                    </div>
	                    <div class="col-md-6 col-12">
	                        <div class="form-outline">
	                            <input name="pais" id="pais" type="text" class="form-control" <% if (request.getParameter("estado")!=null && request.getParameter("estado").equals("invalido")) { %>
	    						value="<%= request.getParameter("pais") %>" <% } %>  pattern="^[A-Za-zÁÉÍÓÚÑáéíóúñüÜ\s'\-]+$" title="Solo letras son permitidas"/>
	                            <label class="form-label">Nacionalidad</label>
	                        </div>
	                        <div id="paisError" class="text-danger" style="display: none;">
	                            Se debe proporcionar un país válido.
	                        </div>
	                    </div>
	                </div>
	                <div class="row mb-4" id="empresaSelected" style="display: none;">
	                    <div class="form-outline mb-4">
	                        <input pattern="^(?!.*\s).*$" name="link" type="url" class="form-control" <% if (request.getParameter("estado")!=null && request.getParameter("estado").equals("invalido")) { %>
	    						value="<%= request.getParameter("link") %>" <% } %> />
	                        <label class="form-label">Link <span class="fst-italic">(opcional)</span></label>
	                    </div>
	                    <div class="form-outline mb-0">
	                        <textarea maxlength="1000" name="des" class="col-md-12" rows="7" id="des"><% if (request.getParameter("estado") != null && request.getParameter("estado").equals("invalido")) {
	    						out.print(request.getParameter("des")); } %></textarea>
	
	                        <label class="form-label">Descripcion</label>
	                    </div>
					    <div id="desError" class="text-danger" style="display: none;">
	                        La descripción debe contener entre 10 y 1000 caracteres.
						</div>
	                </div>
	
	                <!--Boton Registrarse-->
	                <div class="text-center pt-0">
	                    <button type="submit" class="mx-auto btn btn-lg"
	                        style="background-color: #d87093;">Registrarse</button>
	                </div>
	
	            </fieldset>
	        </form>
        </div>
    </div>


    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="media/styles/bootstrap-5.3.1-dist/js/bootstrap.min.js"></script>
    <script src="media/scripts/Formulario.js"></script>
</body>

</html>