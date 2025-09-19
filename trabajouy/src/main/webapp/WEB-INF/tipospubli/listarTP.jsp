<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="com.trabajouy.model.EstadoSesion" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.Set"%>

<%@page import="servidor.DtTipoPublicacion" %>
<%@page import="servidor.DtUsuario" %>
<%@page import="servidor.DtEmpresa" %>
<%@page import="servidor.DtPostulante" %>




<%
DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");
%>

<html>

<head>
    <meta charset="UTF-8">
    <title>Listar Tipos de Publicacion</title>
    <jsp:include page="/WEB-INF/template/head.jsp" />
</head>

<body>
    <jsp:include page="/WEB-INF/template/header.jsp" />

    <div class="container mt-5">
        <h2>Nuestros Tipos de Publicacion</h2>
        <h4 class="text-muted">¡una amplia gama de elecciones que potencian tu alcance!</h4>
        <br>

        <!-- Tipos de Publicacion -->
        <div class="row">
        		<%
                Set<DtTipoPublicacion> tps = (Set<DtTipoPublicacion>) request.getAttribute("tiposPubli");
                if (tps.isEmpty()) {
                %>
                <div class="col-md-12">
                    <div class="alert alert-danger" role="alert">
                        No tenemos tipos de publi por el momento :( 
                        <br>
                        ¡Regresa en la brevedad!
                    </div>
                </div>
                <% } else { 
                	for (DtTipoPublicacion tpubli : tps) { 
                	%>
                    
                    <div class="col-md-4">
                        <div class="card mb-4 tipos-publi">
                            <div class="card-body">
                                <h4 class="card-title">
                                    <%=tpubli.getNombre()%>
                                </h4>
                                <h6 class="card-text">
                                    <%=tpubli.getDescripcion()%>
                                </h6>
                                <ul class="card-text">
                                    <li>Exposición de nivel <%=tpubli.getExposicion()%></li>
                                    <li>Duración de <%=tpubli.getDuracion()%> días</li>
									<li>Costo de $<%=new java.text.DecimalFormat("#").format(tpubli.getCosto())%></li>
                                </ul>
                                <div class="text-center">
                                    <!--  ACA REDIRIGE A CONSULTA DEL RESPECTIVO TIPO PUBLI !!! -->
                                    <a class="btn btn-primary pink-button" href="/trabajouy/consultarTipoPubli?nombreTP=<%=tpubli.getNombre()%>">Detalles completos</a>
                                     
                                </div>
                            </div>
                        </div>
                    </div>
                    <% } 
                	     if (usr instanceof DtEmpresa){ %>
                	       <div class="card mt-4 mx-auto custom-card">
                	          <div class="card-body">
                	              <h5 class="card-title">¿Quieres dar de alta una oferta?</h5>
                	              <p class="card-text">¡Es muy fácil!</p>
                	              <!--  ACA REDIRIGIR A ALTA DE OFERTA !!! -->
                	              <a href="/trabajouy/formAltaOferta" class="btn btn-primary">Ir a la página de alta</a>
                	          </div>
             	          </div>
              	        <% 
              	         }                 	      
                	} %>
        </div>
    </div>



   
        
        
	
    <jsp:include page="/WEB-INF/template/footer.jsp" />
</body>
</html>