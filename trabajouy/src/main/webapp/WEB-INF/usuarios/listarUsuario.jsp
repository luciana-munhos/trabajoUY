<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.Set" %>
<%@page import="servidor.DtUsuario" %>

<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Listar Usuarios</title>
	<jsp:include page="/WEB-INF/template/head.jsp" />
</head>

<body>
	<jsp:include page="/WEB-INF/template/header.jsp" />
	<nav class="ms-3 mt-5" aria-label="breadcrumb">
		<ol class="breadcrumb">
		<li class="breadcrumb-item"><a href="/trabajouy/home">Home</a></li>
		<li class="breadcrumb-item active" aria-current="page">Usuarios</li>
		</ol>
	</nav>
	
	<!--  consigo los Usuarios y chequeo conjunto vacio -->
	<% 
	Map<String,DtUsuario> usrs = new HashMap<>();
	usrs = (Map<String,DtUsuario>) request.getAttribute("lista_usuarios");
	if (!usrs.isEmpty()){
		String nick;
	%>
		<h4 class="text-start text-center">Listado de Usuarios</h4>
		<ul class="list-group text-center d-flex justify-content-center">
		<% 
		for (DtUsuario u: usrs.values()) {
			nick = u.getNickname();
		%>
			<li class="list-group-item"><a href="/trabajouy/consultarUsuario?nickname=<%=nick%>"><%=nick%></a> ~ <%=u.getNombre()%> <%=u.getApellido()%></li>
		<%
		}
		%>
	    </ul>
	<%
	}else{
	%>
		<h4 class="text-start text-center">Listado de Usuarios vacio</h4>
		<br></br>
		<h2 class="text-start text-center"> No hay usuarios registrados! :( </h2>
	<%
	}
	%>
	
	<jsp:include page="/WEB-INF/template/footer.jsp" />
</body>

</html>
