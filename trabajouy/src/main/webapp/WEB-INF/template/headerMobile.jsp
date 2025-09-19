<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<%@page import="servidor.DtUsuario" %>
<%@page import="servidor.DtEmpresa" %>

<%
DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");
%>
	
<nav class="py-4 navbar navbar-expand-lg navbar-light bg-light mb-5">
        <div class="container-fluid">
          <a class="navbar-brand border-end pe-3" href="index.html">
            <img width="200" src="media/img/logo-sin-fondo.png" alt="">
          </a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="text-center mb-2 navbar-nav my-auto me-auto mb-lg-0">
              <li class="nav-item dropdown">
                  <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Herramientas
                  </a>
                  
                  <ul class="dropdown-menu">
	                  <li><a class="dropdown-item" href="/trabajouy/listarOfertas">Ofertas</a></li>
                  </ul>
              </li>
          </ul>
            <div class="mt-md-0 col mt-sm-4 text-center d-md-flex justify-content-center mt-md-0 mt-2">
            	<div class="row d-flex">
              		
              		<div class="col d-flex justify-content-start nav-item dropdown">
                		<button class="btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                  			<%=usr.getNombre() %>
                		</button>
                		<ul class="dropdown-menu">
                  			<li><a class="dropdown-item" href="/trabajouy/consultarUsuario?nickname=<%=usr.getNickname()%>">Mi perfil</a></li>
                  			<li><a class="dropdown-item" href="/trabajouy/cerrar-sesion">Cerrar sesión</a></li>
                		</ul>
              		</div>
              		<div class="col p-0 d-flex justify-content-end">
                		<img class="foto-perfil" src="/trabajouy/imagenes?id=data/<%=usr.getImagen() %>" alt="">
                	</div>
            	</div>
          	</div>
      </div>
   </div>
</nav>
