<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="com.trabajouy.model.EstadoSesion" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.Set"%>

<%@page import="servidor.DtTipoPublicacion" %>

<%@ page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.GregorianCalendar" %>
<%@page import="javax.xml.datatype.XMLGregorianCalendar" %>

<html>
<head>
	<meta charset="UTF-8">
	<title>Consultar Tipo de Publicación</title>
    <jsp:include page="/WEB-INF/template/head.jsp" />
</head>

<body>
    <jsp:include page="/WEB-INF/template/header.jsp" />
	
	<% 
	DtTipoPublicacion tpubli = (DtTipoPublicacion) request.getAttribute("tipoPubli"); 
	
	/* Formato de la fecha */
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");		
	GregorianCalendar gregC = tpubli.getFechaAlta().toGregorianCalendar();
    Date date = gregC.getTime();
    String formatoFecha = dateFormat.format(date);    
    %>
    
	

    <div class="container full-height-card">
    
    	<!-- botoncito para volver atras -->
        <button class="btn btn-primary back-button" onclick="goBack()" style=" justify-content: center; align-items: center;">
   			 < Regresar <
		</button>
		<br>
		<br>

        
        <h2>Información del Tipo de Publicación</h2>
        <h4 class="text-muted">todos los detalles acerca del tipo de publicacion seleccionado...</h4>
        <br>
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <div class="card">
                    <div class="card-body">
                        <div class="card-text">                      
                            <p><strong class="text-success">Nombre:  </strong><%=tpubli.getNombre()%></p>
                            <p><strong class="text-success">Descripcion:  </strong><%=tpubli.getDescripcion()%></p>
                            <p><strong class="text-success">Nivel de Exposición:  </strong><%=tpubli.getExposicion()%></p>
                            <p><strong class="text-success">Duracion:  </strong><%=tpubli.getDuracion()%> dias</p>
                            <p><strong class="text-success">Costo:  </strong><%="$"+ new java.text.DecimalFormat("#").format(tpubli.getCosto())%></p>
                            <p><strong class="text-success">Fecha de Alta:  </strong><%=formatoFecha%></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br>
        
    </div>
    
    <br>
    
    <jsp:include page="/WEB-INF/template/footer.jsp" />
    
    <!-- JavaScript para ir a la pagina anterior (la q te trajo a esta, ya sea listar tipos publi u otra cualq) -->
    <script>
        function goBack() {
            window.history.back();
        }
    </script>
    
</body>
</html>