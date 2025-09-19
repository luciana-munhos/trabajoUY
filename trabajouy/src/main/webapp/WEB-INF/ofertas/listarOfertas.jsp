<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="servidor.DtOferta" %>
<%@page import="java.util.List" %>

<html>
<head>
<meta charset="UTF-8">
<title>Ofertas</title>
<jsp:include page="/WEB-INF/template/head.jsp" />
<link rel="stylesheet" href="media/styles/bootstrap-icons-1.11.1/bootstrap-icons.css">

</head>
<body>
<jsp:include page="/WEB-INF/template/header.jsp" />
	

	<div class="container mt-5">
		<nav class="ms-3 mt-5" aria-label="breadcrumb">
	        <ol class="breadcrumb">
	        <li class="breadcrumb-item"><a href="/trabajouy/home">Home</a></li>
	        <li class="breadcrumb-item active" aria-current="page">Ofertas</li>
	        </ol>
	    </nav>
        <h2>Consulta de Ofertas Laborales</h2>

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
        <div class="container">
        <div"id="row_ofertas" class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
           <%
           List<String> favs = (List<String>) request.getAttribute("favs");
           List<DtOferta> ofertas = (List<DtOferta>) request.getAttribute("ofertas");
           for(DtOferta oferta: ofertas){
         	Integer cantAnotados = oferta.getPostul().size();
      	   String mensaje_anotados = "";
     		if(cantAnotados == 0)
     			mensaje_anotados = "Todavía no se anotó nadie.";
     		else if(cantAnotados == 1)
     			mensaje_anotados = "¡Ya hay 1 anotado!";
     		else
     			mensaje_anotados = "¡Ya hay "+cantAnotados+" anotados!";
           %>
           <div class="row mb-3">
            <div class="col card_oferta">
                <div class="card h-100 mx-auto mb-4">
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
                    </div>
                    <div class="card-body d-flex justify-content-between align-items-end">
                    	<div class="w-100 row" >
                    		<div class="col d-flex justify-content-between">
                    			<a class="btn btn-info my-auto py-auto"  href="/trabajouy/consultarOferta?nombre=<%=oferta.getNombre()%>">Ver Detalles</a>
                    			<%
                    			if(favs != null){
                    			%>
                    			<div class="d-flex align-items-center text-center">
                    			<%
                    			String id = "fav:"+oferta.getNombre().replaceAll("\\s+","_");
                    			if(favs.contains(oferta.getNombre())){
                    			%>
                    			<a title="Quitar de favoritos" type="button"><i id="<%=id %>" class="estrella bi bi-star-fill text-warning"></i></a>
                    			<%
                    			}else{
                    			%>
                    			<a title="Marcar como favoritos" type="button"><i id="<%=id %>" class="estrella bi bi-star text-warning"></i></a>
                    			<%
                    			}
                    			id = "cant:"+oferta.getNombre().replaceAll("\\s+","_");
                    			%>
                    			<span class="ps-2 text-muted" id="<%=id%>">(<%=oferta.getCantFavoritos()%>)</span>
                    			</div>
                    			<%
                    			}else{
                    				int cantFavs = oferta.getCantFavoritos();
                    				if(cantFavs == 0)
                    					mensaje_anotados = "Nadie la ha marcado como favorito.";
                    				else if(cantFavs == 1)
                    					mensaje_anotados = "Marcada como favorito por 1 usuario.";
                    				else
                    					mensaje_anotados = "Marcada como favorito por "+cantFavs+" usuarios.";
                    			}
                    			%>
                    		</div>
                    	</div>
                    </div>
                    <div class="card-footer">
                    	<p class="card-text"><small class="text-muted"><%=mensaje_anotados %></small></p>
				    </div>
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
    </div>
<script src="media/scripts/favorito.js"></script>
<jsp:include page="/WEB-INF/template/footer.jsp" />
</body>
</html>