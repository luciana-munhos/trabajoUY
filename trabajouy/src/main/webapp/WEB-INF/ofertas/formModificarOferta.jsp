<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>


<%@page import="java.util.Map" %>
<%@page import="java.util.Set" %>
<%@page import="java.util.HashSet" %>

<%
session.setAttribute("ya_existe", "no_existe");
%>

<html>
<head>
	<meta charset="UTF-8">
	<title>Modificar Oferta Laboral</title>
    <jsp:include page="/WEB-INF/template/head.jsp" />

</head>

<body>
    <jsp:include page="/WEB-INF/template/header.jsp" />

    <div class="alert alert-warning d-flex align-items-center justify-content-center" role="alert">
	    <div class="text-center">
	        ¡Ya tienes una oferta con dicho nombre esperando a ser procesada por nuestros moderadores!<br>
	        Puedes cambiar sus datos en esta página.<br>
	        Si deseas dar de alta una oferta desde cero con otro nombre, 
	        <a href="/trabajouy/formAltaOferta"  style="color: blue;">click aquí</a> 
	        para regresar.
	    </div>
	</div>

    <div class="container my-5 p-5">
        <form action="modificarOferta" method="POST" enctype="multipart/form-data" id="formulario">            
            <fieldset class="border rounded-3 p-3">
                <legend class="float-none w-auto px-3">Modificar una Oferta laboral:</legend>
                <h5 class="text-muted">Modifique los datos que desee de la oferta e ingresela para salvar los cambios.</h5>
                <br>
                <div class="form-outline mb-4">
                    <select class="form-select" id="nombreOferta" name="nombreOferta">                    
                        <option value="<%=session.getAttribute("nombreOferta")%>" selected><%=session.getAttribute("nombreOferta")%></option>                       
                    </select>
                    <label class="form-label" for="nombreOfertaSelect" style="color: red;">Nombre de la oferta a modificar</label>
                </div>

                <!-- Descripción -->
                <div class="form-outline mb-4">
                    <textarea name="descripcionOferta" id="descripcionOferta" placeholder="Descripción" class="form-control" required><%=session.getAttribute("descripcionOferta")%></textarea>
                    <label class="form-label">Descripción</label>
                </div>

                <!-- Ciudad y Departamento -->
				<div class="form-outline mb-4">
				    <select class="form-select" id="departamentoSelect" name="departamentoSelect">
				        <option value="" disabled>Select a department</option>
				        <option value="Artigas" <%= "Artigas".equals(session.getAttribute("departamentoSelect")) ? "selected" : "" %>>Artigas</option>
				        <option value="Canelones" <%= "Canelones".equals(session.getAttribute("departamentoSelect")) ? "selected" : "" %>>Canelones</option>
				        <option value="Cerro Largo" <%= "Cerro Largo".equals(session.getAttribute("departamentoSelect")) ? "selected" : "" %>>Cerro Largo</option>
				        <option value="Colonia" <%= "Colonia".equals(session.getAttribute("departamentoSelect")) ? "selected" : "" %>>Colonia</option>
				        <option value="Durazno" <%= "Durazno".equals(session.getAttribute("departamentoSelect")) ? "selected" : "" %>>Durazno</option>
				        <option value="Flores" <%= "Flores".equals(session.getAttribute("departamentoSelect")) ? "selected" : "" %>>Flores</option>
				        <option value="Florida" <%= "Florida".equals(session.getAttribute("departamentoSelect")) ? "selected" : "" %>>Florida</option>
				        <option value="Lavalleja" <%= "Lavalleja".equals(session.getAttribute("departamentoSelect")) ? "selected" : "" %>>Lavalleja</option>
				        <option value="Maldonado" <%= "Maldonado".equals(session.getAttribute("departamentoSelect")) ? "selected" : "" %>>Maldonado</option>
				        <option value="Montevideo" <%= "Montevideo".equals(session.getAttribute("departamentoSelect")) ? "selected" : "" %>>Montevideo</option>
				        <option value="Paysandu" <%= "Paysandu".equals(session.getAttribute("departamentoSelect")) ? "selected" : "" %>>Paysandu</option>
				        <option value="Río Negro" <%= "Río Negro".equals(session.getAttribute("departamentoSelect")) ? "selected" : "" %>>Río Negro</option>
				        <option value="Rivera" <%= "Rivera".equals(session.getAttribute("departamentoSelect")) ? "selected" : "" %>>Rivera</option>
				        <option value="Rocha" <%= "Rocha".equals(session.getAttribute("departamentoSelect")) ? "selected" : "" %>>Rocha</option>
				        <option value="Salto" <%= "Salto".equals(session.getAttribute("departamentoSelect")) ? "selected" : "" %>>Salto</option>
				        <option value="San José" <%= "San José".equals(session.getAttribute("departamentoSelect")) ? "selected" : "" %>>San José</option>
				        <option value="Soriano" <%= "Soriano".equals(session.getAttribute("departamentoSelect")) ? "selected" : "" %>>Soriano</option>
				        <option value="Tacuarembo" <%= "Tacuarembo".equals(session.getAttribute("departamentoSelect")) ? "selected" : "" %>>Tacuarembo</option>
				        <option value="Treinta y Tres" <%= "Treinta y Tres".equals(session.getAttribute("departamentoSelect")) ? "selected" : "" %>>Treinta y Tres</option>
				    </select>
				    <label class="form-label" for="departamentoSelect">Departamento</label>
				</div>


                <div class="form-outline mb-4">
					<input name="ciudadOferta" value="<%=session.getAttribute("ciudadOferta")%>" id="ciudadOferta" placeholder="Ciudad de la oferta" type="text" class="form-control" required pattern="^[A-Za-zÁÉÍÓÚÑáéíóúñüÜ\s'\-]+$">                
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
					<input name="remuneracionOferta" id="remuneracionOferta" placeholder="Remuneracion" value="<%=session.getAttribute("remuneracionOferta")%>" type="text" class="form-control" required pattern="^[1-9][0-9]*$">                                
                    <label class="form-label">Remuneración</label>
               
                    <div class="invalid-feedback" id="remuneracionErrorMessage">
                        La remuneración debe ser un número entero positivo!
    				</div>
                </div>
                
                <!-- Imagen -->
                <div class="form-outline mb-4">
                    <input name="imgOferta" id="imgOferta"  type="file" accept="image/jpeg, image/png" class="form-control" value="<%=session.getAttribute("imgOferta")%>"/>
                    <label class="form-label">Imagen de la oferta (opcional)</label>
                </div>

                <!-- Keywords!!! -->
				<div class="mb-4">
				    <label class="form-label">Keywords (seleccione las que desee):</label>
				    <%
				    Set<String> keys = (Set<String>) request.getAttribute("allKeys");
				    if (!keys.isEmpty()) {
				        Set<String> selectedKeywords = (Set<String>)session.getAttribute("keysOferta");
				        for (String key : keys) {
				            String keywordName = key;
				            boolean isSelected = selectedKeywords != null && selectedKeywords.contains(keywordName);
				    %>
				            <div class="form-check">
				                <input class="form-check-input" type="checkbox" id="<%= keywordName %>" name="selectedKeywords" value="<%= keywordName %>" <%= isSelected ? "checked" : "" %>>
				                <label class="form-check-label" for="<%= keywordName %>"><%= keywordName %></label>
				            </div>
				    <%
				        }
				    } else {
				    %>
				        <p class="text-muted"><em>Por el momento no contamos con keywords en el sistema.</em></p>
				    <% } %>
				</div>



                <!-- Botón GUARDAR CAMBIOS en Oferta -->
                <div id="modificarOfertaBtn" class="text-center pt-0">
					<button type="submit" class="mx-auto btn btn-lg" style="background-color: #d87093;">Salvar cambios</button>               	
                </div>
               
            </fieldset>
        </form>
    </div>
    
  
	<jsp:include page="/WEB-INF/template/footer.jsp" />  
</body>
        

</html>