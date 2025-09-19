<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<%@page import="servidor.DtOferta" %>
<%@page import="servidor.DtPostulacion" %>
<%@page import="servidor.DtPostulante" %>

<%@page import="java.util.List" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.Map.Entry" %>

<%@ page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.GregorianCalendar" %>
<%@page import="javax.xml.datatype.XMLGregorianCalendar" %>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Exponer Ranking</title>
<jsp:include page="/WEB-INF/template/head.jsp" />
<link rel="stylesheet" href="media/styles/bootstrap-icons-1.11.1/bootstrap-icons.css">


</head>
<body>
	<%
	DtOferta oferta = (DtOferta) request.getAttribute("oferta"); 
    List<Entry<DtPostulante, DtPostulacion>> postulaciones = (List<Entry<DtPostulante, DtPostulacion>>) request.getAttribute("postulantes");
    
    /* Formato de la fecha */
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");		
	GregorianCalendar gregC;
    Date date;
    String formatoFecha; 
			
	%>
	<jsp:include page="/WEB-INF/template/header.jsp" />
	<div class="container">
		<div class="row">
			<div class="col">
				<h1>Exponer Ranking</h1>
				<h4>Oferta: <a target="_blank" href="/trabajouy/consultarOferta?nombre=<%=oferta.getNombre()%>"><%=oferta.getNombre()%></a></h4>
			</div>
		</div>
		<br>

		<div class="row mt-5">
    		<div class="col">
	            <table class="table table-striped ">
				    <thead>
				        <tr>
				            <th scope="col">#</th>
				            <th scope="col">Nombre</th>
				            <th scope="col">Apellido</th>
				            <th scope="col">Nickname</th>
				            <th scope="col">Fecha de Postulacion</th>
				            <th scope="col">Ver Perfil</th>
				        </tr>
				    </thead>
				    <tbody>
				        <%
				            int rowNumber = 1;
					        for (Entry<DtPostulante, DtPostulacion> entry : postulaciones) {
		                        DtPostulante postulante = entry.getKey();
		                        DtPostulacion postulacion = entry.getValue();
				        %>
				        <tr data-postulante-id="<%=postulante.getNickname()%>">
							<td style="color: pink;"><strong><%=rowNumber%></strong></td>				       
				            <td><%=postulante.getNombre()%></td>
				            <td><%=postulante.getApellido()%></td>
				            <td><%=postulante.getNickname()%></td>
				            
				            <% 
							gregC = postulacion.getFecha().toGregorianCalendar();
    						date = gregC.getTime();
    						formatoFecha = dateFormat.format(date); 
    						%>
    
				            <td><%=formatoFecha%></td>
				            <td>
				                <a target="_blank" href="/trabajouy/consultarUsuario?nickname=<%=postulante.getNickname()%>">Ver Perfil</a>
				            </td>
				        </tr>
				        <%
				            rowNumber++;
				        }
				        %>
				    </tbody>
				</table>
    		</div>
		</div>

	<br>
	<br>
	<br>
	<br>
	</div>
	<jsp:include page="/WEB-INF/template/footer.jsp" />
	
	
</body>
</html>