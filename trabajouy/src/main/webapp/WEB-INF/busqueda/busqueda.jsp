<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
   
<%@page import="servidor.DtEmpresa" %>
<%@page import="servidor.DtOferta" %>

<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>


 
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Resultados de la busqueda</title>
<jsp:include page="/WEB-INF/template/head.jsp" />
<link rel="stylesheet" href="media/styles/bootstrap-icons-1.11.1/bootstrap-icons.css">

</head>
<%
String busqueda = (String) request.getParameter("buscar");
List<DtEmpresa> empresas = (List<DtEmpresa>) request.getAttribute("empresas_busqueda");
List<DtOferta> ofertas = (List<DtOferta>) request.getAttribute("ofertas_busqueda");
%>
<body>
	<jsp:include page="/WEB-INF/template/header.jsp"/>
	
	<div class="container">
		<div class=row>
			<div class="col">
				<h1>Resultados de la busqueda:</h1>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<h3>"<%=busqueda %>"</h3>
			</div>
		</div>
	</div>
	
	<div class="container mt-5">
		<div class="row">
			<div class="col">
				<h5>Empresas</h5>
			</div>
		</div>
		<div class="row">
		<%
		if(empresas.size() == 0){
		%>
		<h6>No se encontraron empresas :( </h6>
		<%
		}else{
		%>
			<ul class="list-group">
			<%
			for(DtEmpresa usr: empresas){
			%>
			  <li class="list-group-item"><a href="/trabajouy/consultarUsuario?nickname=<%=usr.getNickname() %> "><%=usr.getNickname() %> - <%=usr.getNombre() %> <%=usr.getApellido() %></a></li>
			<%
			}
			%>
			</ul>
		<%
		}
		%>
		</div>
	</div>
	
	<div class="container mt-3">
		<div class="row">
			<div class="col">
				<h5>Ofertas</h5>
			</div>
		</div>
		<div class="row">
		<%
		if(ofertas.size()==0){
		%>	
			<h6>No se encontraron ofertas :(</h6>
		<%
		}else{
		%>
			<ul class="list-group">
			<%
		  	List<String> favs = (List<String>) request.getAttribute("favs");

			for(DtOferta ofe: ofertas){
			%>
			  <li class="list-group-item">
			  <div class="d-flex justify-content-between text-center align-items-center">
			  	<a href="/trabajouy/consultarOferta?nombre=<%=ofe.getNombre()%>">
			  		<%=ofe.getNombre() %>
			  	</a>
			  	<%
       			if(favs != null){
       			%>
	       			<div class="d-flex align-items-center">
	       			<%
	       			String id = "fav:"+ofe.getNombre().replaceAll("\\s+","_");
	       			if(favs.contains(ofe.getNombre())){
		       			%>
		       			<a title="Quitar de favoritos" type="button"><i id="<%=id %>" class="estrella bi bi-star-fill text-warning"></i></a>
		       			<%
	       			}else{
		       			%>
		       			<a title="Marcar como favoritos" type="button"><i id="<%=id %>" class="estrella bi bi-star text-warning"></i></a>
		       			<%
	       			}
	       			id = "cant:"+ofe.getNombre().replaceAll("\\s+","_");
	       			%>
	       			<span class="ps-2 text-muted" id="<%=id%>">(<%=ofe.getCantFavoritos()%>)</span>
	       			</div>
	       			<%
       			}
       			else{
       			%>
       				<span class="ps-2 text-muted">(<%=ofe.getCantFavoritos()%> favoritos)</span>
       				
       			<%
       			}
       			%>
       			</div>
			  </li>
			<%
			}
			%>
			</ul>
		<%
		}
		%>
		</div>
	</div>
	<script src="media/scripts/favorito.js"></script>
	
	<jsp:include page="/WEB-INF/template/footer.jsp"/>
</body>
</html>