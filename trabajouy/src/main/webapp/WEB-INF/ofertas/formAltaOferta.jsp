<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="com.trabajouy.model.EstadoSesion" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.Set" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Arrays" %>




<%@page import="servidor.DtUsuario" %>

<%DtUsuario usr = (DtUsuario) request.getAttribute("usuario_consultado");
Set<String> tipopubliadq = (Set<String>) request.getAttribute("tiposDePublicacion");
%>

<html>

<head>
	<meta charset="UTF-8">
	<title>Alta de Oferta Laboral</title>
    <jsp:include page="/WEB-INF/template/head.jsp" />

</head>

<body>
    <jsp:include page="/WEB-INF/template/header.jsp" />
   
    
	<div class="container my-5 p-5">
		<%
		String existe = request.getParameter("ya_existe"); 
		if(existe!=null && existe.equals("existe")){	
		%>
		<div class="row">
			<div class="col">
				<div class="alert alert-danger d-flex align-items-center justify-content-center" role="alert">
				    <p class="text-center">
				        Ya existe una oferta que fue dada de alta con ese nombre, por favor elige otro.
				    </p>
				</div>
			</div>
		</div>
		<%
		} 
		%>
        <form action="altaOferta" method="POST" enctype="multipart/form-data" id="formulario">            
            <fieldset class="border rounded-3 p-3">
                <legend class="float-none w-auto px-3">Ingresar Oferta Laboral</legend>
                <!-- Nombre de la oferta -->
                <div class="form-outline mb-4">
                    <input name="nombreOferta" id="nombreOferta" placeholder="Nombre de la oferta" type="text" class="form-control" required>                
                    <label class="form-label">Nombre de la oferta</label>
                </div>
                <!-- Descripción -->
                <div class="form-outline mb-4">
                    <textarea name="descripcionOferta" id="descripcionOferta" placeholder="Descripción de la oferta" class="form-control" required></textarea>             
                    <label class="form-label">Descripción</label>
                </div>
                <!-- Ciudad y Departamento -->
                <div class="form-outline mb-4">
					<select name="departamentoSelect" id="departamentoSelect" class="form-select" required>
                        <option value="" disabled selected>Seleccione un departamento</option>
                        <option value="Artigas">Artigas</option>
                        <option value="Canelones">Canelones</option>
                        <option value="Cerro Largo">Cerro Largo</option>
                        <option value="Colonia">Colonia</option>
                        <option value="Durazno">Durazno</option>
                        <option value="Flores">Flores</option>
                        <option value="Florida">Florida</option>
                        <option value="Lavalleja">Lavalleja</option>
                        <option value="Maldonado">Maldonado</option>
                        <option value="Montevideo">Montevideo</option>
                        <option value="Paysandu">Paysandu</option>
                        <option value="Río Negro">Río Negro</option>
                        <option value="Rivera">Rivera</option>
                        <option value="Rocha">Rocha</option>
                        <option value="Salto">Salto</option>
                        <option value="San José">San José</option>
                        <option value="Soriano">Soriano</option>
                        <option value="Tacuarembo">Tacuarembo</option>
                        <option value="Treinta y Tres">Treinta y Tres</option>
                    </select>
                    <label class="form-label" for="departamentoSelect">Departamento</label>
                </div>

                <div class="form-outline mb-4">
					<input name="ciudadOferta" id="ciudadOferta" placeholder="Ciudad de la oferta" type="text" class="form-control" required pattern="^[A-Za-zÁÉÍÓÚÑáéíóúñüÜ\s'\-]+$">                
                    <label class="form-label">Ciudad</label>
                </div>

                <!-- Horario -->
                <div class="row mb-4">
                    <div class="col-md-6 col-12">
                        <div class="form-outline">
                            <input name="hinicioOferta" id="hinicioOferta" type="time" class="form-control" required/>
                        </div>
                        <label class="form-label">Hora de inicio</label>
                    </div>
                    <div class="col-md-6 col-12">
                        <div class="form-outline">
                            <input name="hfinOferta" id="hfinOferta" type="time" class="form-control" required/>
                        </div>
                        <label class="form-label">Hora de finalización</label>
                    </div>
                </div>

                <!-- Remuneración -->
                <div class="form-outline mb-4">
					<input name="remuneracionOferta" id="remuneracionOferta" placeholder="Remuneracion" type="text" class="form-control" required pattern="^[1-9][0-9]*$">                                
                    <label class="form-label">Remuneración</label>
               
                    <div class="invalid-feedback" id="remuneracionErrorMessage">
                        La remuneración debe ser un número entero positivo!
    				</div>
                </div>

                
                <!--File input // Imagen Oferta (opcional) -->
	                <% if (request.getParameter("estado")!=null &&  request.getParameter("estado").equals("invalido") && session.getAttribute("pathImg") != null) { %>
	                <div class="form-outline mb-4">	   
	    				<img id="imagenPreseleccionada" src="<%= (String) session.getAttribute("pathImg") %>" style="max-width: 25%;">
	    				<p class="text-success">Si desea modificar la imagen, seleccione una nueva.</p>	
					</div>
					<%} %>
	                <div class="form-outline mb-4">
	                    <input name="imgOferta" type="file" accept="image/jpeg image/png" class="form-control" value="<% request.getParameter("img"); %>"/>
	                    <label class="form-label">Imagen de la oferta (opcional)</label>
	                </div>

                <!-- Keywords!!! -->
                <div class="mb-4">
                    <label class="form-label">Keywords (seleccione las que desee):</label>
                    <%
                    Set<String> keys = (Set<String>) request.getAttribute("allKeys");
				    if (!keys.isEmpty()) {
				        for (String key : keys) {
			          	%>
			             <div class="form-check">
					        <input class="form-check-input" type="checkbox" id="<%=key%>" name="selectedKeywords" value="<%=key%>">
					        <label class="form-check-label" for="<%=key%>"><%=key%></label>
	   					 </div>
			            <%} 
	             	} else { %>
		            	<p class="text-muted"><em>Por el momento no contamos con keywords en el sistema.</em></p>	 
	             	<% }%>           
                </div>

                <!-- Sección de Tipo de Publicación -->
                <div class="form-outline mb-4">
				<select name="tpOferta" id="tpOferta" class="form-select" required onchange="validarTipoOferta()">
				
				        <%
				        Set<String> tipos = (Set<String>) request.getAttribute("allTipos");
				        if (tipos != null) {
				            for (String tpubli : tipos) {				                
						        %>
								<option value="<%=tpubli%>"><%=tpubli%></option>
	        					<script>
							      var <%=tpubli%> = '<%=tpubli%>';
							    </script>
						        <%
				            }
				        } else {
					        %>
					        <option value="" disabled selected>No hay tipos de publicación disponibles por el momento</option>
					        <%
				        }
				        %>
				        
				    </select>
				    <label class="form-label" for="tipoPublicacionSelect">Tipo de Publicación</label>
				</div>
				
			
              <!-- Sección de Pago -->
				<div class="mb-4">
				    <label class="form-label">¿Cómo desea pagar?</label>
				    <div class="form-check">
				        <input class="form-check-input" type="radio" name="pago" id="pagoGeneral" value="pagoGeneral" checked>
				        <label class="form-check-label" for="pagoGeneral">
				            Pago General
				        </label>
				    </div>
				    <div class="form-check">
						<input class="form-check-input" type="radio" name="pago" id="pagoConPaquete" value="pagoConPaquete">
				        <label class="form-check-label" for="pagoConPaquete">
				            Pago con Paquete
				        </label>
				    </div>
				</div>

                <!-- Botón Ingresar Oferta -->               
                <div id="ingresarOfertaBtn" class="text-center pt-0">
					<button type="submit" class="mx-auto btn btn-lg" style="background-color: #d87093;" onclick="validarEntradas()">Ingresar oferta</button>               	
				</div>

            </fieldset>
        </form>
    </div>
    
    <script>
    
    function validarEntradas() {    	

        var nombreOferta = document.getElementById('nombreOferta').value;
        var descripcionOferta = document.getElementById('descripcionOferta').value;
        var departamento = document.getElementById('departamentoSelect').value;
        var ciudad = document.getElementById('ciudadOferta').value;
        var hinicio = document.getElementById('hinicioOferta').value;
        var hfin = document.getElementById('hfinOferta').value;
        var remuneracion = document.getElementById('remuneracionOferta').value;
        var tpOferta = document.getElementById('tpOferta').value;

        // Verificar si hay algun campo vacio
        if (
            nombreOferta.trim() === '' ||
            descripcionOferta.trim() === '' ||
            departamento.trim() === '' ||
            ciudad.trim() === '' ||
            hinicio.trim() === '' ||
            hfin.trim() === '' ||
            remuneracion.trim() === '' ||
            tpOferta.trim() === ''
        ) {
            alert('Por favor complete todos los campos obligatorios.');
            return false;
        }

        // Es obligatorio que elija un metodo de pago
        var pagoGeneral = document.getElementById('pagoGeneral').checked;
        var pagoConPaquete = document.getElementById('pagoConPaquete').checked;

        if (!pagoGeneral && !pagoConPaquete) {
            alert('Por favor seleccione un método de pago.');
            return false;
        }     	        
        return true; 

    }
            
	var tiposDePublicacionArray = <%= Arrays.toString(tipopubliadq.toArray()) %>;
		
			
	  function validarTipoOferta() {
	    var tpOferta = document.getElementById('tpOferta').value;
	    
	    //chequeo q el tipo de publi seleccionado este en el tiposDePublicacionArray (q tiene los adquiridos en algun paquete)
	    var tipoEsAdquirido = tiposDePublicacionArray.includes(tpOferta);
	
	    // desactivo o activo el pago paquete segun haga falta
	    var pagoConPaquete = document.getElementById('pagoConPaquete');
	    
	    //y lo deschequeo si habia sido elegido anteriormente
	    if (pagoConPaquete) {
	        document.getElementById('pagoConPaquete').checked = false;
	    }
	    
	    if (tipoEsAdquirido) {
	      pagoConPaquete.disabled = false;
	    } else {
	    	pagoConPaquete.disabled = true;
	    }
	  }
	
	  
		// Hago la validacion tambien cuando la pagina es cargada, pues puede suceder
		// que el tipo de publi seleccionado por default no este disponible para pago con paquete
	    window.onload = function () {
	        validarTipoOferta();
	    };
	

	</script>
	
    <jsp:include page="/WEB-INF/template/footer.jsp" />    
</body>
</html>