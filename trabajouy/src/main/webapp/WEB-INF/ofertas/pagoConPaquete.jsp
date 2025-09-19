<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
    
<%@ page import="com.trabajouy.model.EstadoSesion" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.Set" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ page import="java.util.Date" %>



<!DOCTYPE html>

<%//Usuario usr = (Usuario) session.getAttribute("usuario_logueado");%> 



<html>

<head>
	<meta charset="UTF-8">
	<title>Pago con Paquete</title>
	<jsp:include page="/WEB-INF/template/head.jsp" />
</head>

<body>
    <jsp:include page="/WEB-INF/template/header.jsp" />
	<%
	Map<String, Map<String, Integer>> paqueteTipoCantidadMap = (Map<String, Map<String, Integer>>) request.getAttribute("paqueteTipoCantidadMap");

	%>
	
	<div class="container mt-5">
        <h2>Selecciona un Paquete</h2>
        <h4 class="text-muted">sus paquetes compatibles:</h4>
		
        <!-- Tarjetas de Paquete -->
		<div class="row mt-4">
    		<%
		    Set<String> paquetes = paqueteTipoCantidadMap.keySet();
		
		    String selectedTipo = (String) request.getAttribute("selectedTipo");
		    
		    Map<String, Integer> vencimientosPaquetes = (Map<String, Integer>) request.getAttribute("vencimientosPaquetes");
		
		    for (String paquete : paquetes) {
			        String cardTitle = paquete;
			        Map<String, Integer> tipoCantidadMap = paqueteTipoCantidadMap.get(paquete);
			        int diasRestantes = vencimientosPaquetes.get(paquete);
			        
			        String tipoAlerta;

			        if (diasRestantes > 15) {
			        	tipoAlerta = "alert-success";  // Alerta verde si quedan mas de 15 dias
			        } else if (diasRestantes >= 5) {
			        	tipoAlerta = "alert-warning";  // Alerta amarilla si quedan entre 15 y 5
			        } else {
			        	tipoAlerta = "alert-danger";   // Roja si menos de 5 
			        }

			        
			        // chequear si el selectedTipo esta disponible para el paquete
			        Integer selectedTipoCant = tipoCantidadMap.get(selectedTipo);			      
			        if (selectedTipoCant != null && selectedTipoCant > 0) {
			    %>
			
			    <div class="col-md-4">
			        <div class="card mb-4">
			            <div class="card-body">
			                <h5 class="card-title"><%= cardTitle %></h5>
			                
			                    <ul>
			                        <li>Cuenta con <%= selectedTipoCant %> unidad/es de <%= selectedTipo %></li>
			                    </ul>
			              
			                <div class="alert <%= tipoAlerta %>" role="alert">Este paquete expira en <%= diasRestantes %> días.</div>
							<a href="#" onclick="SeleccionarClick('<%= cardTitle %>');" class="btn btn-info">Seleccionar</a>
			                
			            </div>
			        </div>
			    </div>
			
			    <% }
		    } 
		    %>
		    
		</div>

    </div>
    
    <script>
    
    var nombreOferta = '<%= request.getAttribute("nombreOferta") %>';

    
    function SeleccionarClick(nombrePaquete) {
        console.log("Seleccionar button clicked!");


        
        var selectedTipo = "<%= selectedTipo %>";

        var form = document.createElement("form");
        form.setAttribute("method", "post");
        form.setAttribute("action", "pagoConPaquete");

        var selectedTipoInput = document.createElement("input");
        selectedTipoInput.setAttribute("type", "hidden");
        selectedTipoInput.setAttribute("name", "selectedTipo");
        selectedTipoInput.setAttribute("value", selectedTipo);
        form.appendChild(selectedTipoInput);

        var nombrePaqInput = document.createElement("input");
        nombrePaqInput.setAttribute("type", "hidden");
        nombrePaqInput.setAttribute("name", "nombrePaq");
        nombrePaqInput.setAttribute("value", nombrePaquete);
        form.appendChild(nombrePaqInput);
        
        var selectedTipoInput = document.createElement("input");
        selectedTipoInput.setAttribute("type", "hidden");
        selectedTipoInput.setAttribute("name", "nombreOferta");
        selectedTipoInput.setAttribute("value", nombreOferta); 
        form.appendChild(selectedTipoInput);

        document.body.appendChild(form);
        form.submit();
    }

	</script>


	
    <jsp:include page="/WEB-INF/template/footer.jsp" />    
</body>
</html>