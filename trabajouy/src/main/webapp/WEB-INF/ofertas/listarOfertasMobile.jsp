<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="servidor.DtOferta" %>
<%@page import="java.util.List" %>
<%@page import="servidor.DtPostulacion" %>

<html>
<head>
<meta charset="UTF-8">
<title>Ofertas</title>
<jsp:include page="/WEB-INF/template/head.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/template/headerMobile.jsp" />
	

	<div class="container mt-5">
		<nav class="ms-3 mt-5" aria-label="breadcrumb">
	        <ol class="breadcrumb">
	        <li class="breadcrumb-item"><a href="/trabajouy/home">Home</a></li>
	        <li class="breadcrumb-item active" aria-current="page">Ofertas</li>
	        </ol>
	    </nav>
        <h2>Consulta de Ofertas Laborales</h2>

        <!-- Barrita de busqueda keywords o empresas -->
        <form class="form-inline">
            <div class="form-group mb-2">
                <label for="searchInput" class="sr-only">Buscar:</label>
                <input type="text" class="form-control" id="searchInput" placeholder="Empresa o Keyword">
            </div>
            <button type="submit" class="btn btn-primary mb-2">Buscar</button>
        </form> 
        <br>
		<!-- 
		En caso de que no se haya podido postular con exito coloca mensaje
		 -->
		<% if ("0".equals(request.getParameter("postulado"))) { %>
	    	<div id="error-alert" class="alert text-center m-4 alert-danger alert-dismissible fade show" role="alert">
		  		No se puede postular a dicha oferta pues se encuentra inhabilitada.
		  		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
			</div>
			<script>
			  const urlParams = new URLSearchParams(window.location.search);
			  const pos = urlParams.get("postulado");
	
			  if (pos != null && pos === "0") {
			    const errAlert = document.getElementById("error-alert");
			    errAlert.style.display = "block";
	
			    // Cierra el cartel después de 3 segundos 
			    setTimeout(function() {
			      errAlert.style.display = "none";
			    }, 5000);
			  }
			</script>
	  <% } %>
        <!-- Las Ofertas -->
        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3">
           <%
           List<DtOferta> ofertas = (List<DtOferta>) request.getAttribute("ofertas");
           for(DtOferta oferta: ofertas){
           %>
            <div class="col">
                <div class="card mx-auto mb-4">
                    <img src="/trabajouy/imagenes?id=data/<%=oferta.getImagen() %>" alt="Desarrollador Frontend" style="height: 200px; object-fit: cover;">
                    <div class="card-body">
                        <h3 class="card-title"><%=oferta.getNombre()%></h3>
                        <ul class="card-text">
                            <li>Ciudad: <%=oferta.getCiudad()%></li>
                            <li>Departamento: <%=oferta.getDepartamento()%></li>
                            <li>Horario: <%=oferta.getHorario()%></li>
                            <li>Remuneración: $<%=oferta.getRemuneracion()%></li>
                        </ul>
                        <p><%=oferta.getDescripcion()%></p>
                        <a class="btn btn-info" href="/trabajouy/consultarOferta?nombre=<%=oferta.getNombre()%>">Ver Detalles</a>
                    </div>
                </div>
            </div>
            <%
            }
           	if(ofertas.size()==0){
            %>
            <div class="col">
            	<h3 class="text-muted text-center">No hay ofertas disponibles</h3>
            </div>
            <%
            }
           	%>
        </div>
    </div>

<jsp:include page="/WEB-INF/template/footer.jsp" />
</body>
</html>