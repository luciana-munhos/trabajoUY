<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="java.util.HashSet" %>
<%@page import="java.util.Set" %>
<%@page import="java.util.List" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.SortedMap" %>

<%@page import="servidor.DtUsuario" %>
<%@page import="servidor.DtOferta" %>



<%DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado"); //RECORDAR CAMBIARLO CUANDO ANDE LOGIN%> 
<html>
<head>
<meta charset="UTF-8">
<title>Inicio</title>
<jsp:include page="/WEB-INF/template/head.jsp" />
<link rel="stylesheet" href="media/styles/bootstrap-icons-1.11.1/bootstrap-icons.css">

</head>
<body>
	
	<jsp:include page="/WEB-INF/template/header.jsp" />
	<%
	if(usr == null){
	%>
	<div id="mural-home">
        <div class="container ">
          <div class="row">
            <div class="d-none d-md-block col-md-6">
              <img class="w-100" src="media/img/oficina-con-trabajadores-felices-upscaled.png" alt="">
            </div>
            <div class="my-auto col text-start">
              <h1>Trabajo.uy</h1>
              <p>
                Somos una plataforma en línea diseñada para facilitar la búsqueda y el proceso de selección de empleo tanto para empleadores como para candidatos en Uruguay. Nuestra plataforma actúa como un puente entre empresas que buscan talento y profesionales que desean encontrar oportunidades laborales acordes a sus habilidades y objetivos profesionales.</p>
            </div>
          </div>
        </div>
      </div>
      <%
	}else{
      %>
      <% if ("1".equals(request.getParameter("registrado"))) { %>
    	<div id="success-alert" class="alert text-center m-4 alert-success alert-dismissible fade show" role="alert">
	  		Te has registrado correctamente. Bienvenido!
	  		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
		<script>
		  const urlParams = new URLSearchParams(window.location.search);
		  const registrado = urlParams.get("registrado");

		  if (registrado != null && registrado === "1") {
		    const successAlert = document.getElementById("success-alert");
		    successAlert.style.display = "block";

		    // Cierra el cartel después de 3 segundos 
		    setTimeout(function() {
		      successAlert.style.display = "none";
		    }, 5000);
		  }
		</script>

	  <% } 
	   if ("1".equals(request.getParameter("postulado"))) { %>
    	<div id="p-alert" class="alert text-center m-4 alert-success alert-dismissible fade show" role="alert">
	  		La postulación se registró con éxito..
	  		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
		<script>
		  const urlParams = new URLSearchParams(window.location.search);
		  const post = urlParams.get("postulado");

		  if (post != null && post === "1") {
		    const pAlert = document.getElementById("p-alert");
		    pAlert.style.display = "block";

		    // Cierra el cartel después de 3 segundos 
		    setTimeout(function() {
		      pAlert.style.display = "none";
		    }, 5000);
		  }
		</script>

	  <% } %>

      <div id="mensaje-bienvenida" class="row text-center m-4">
        <h1>¡Hola, <%=usr.getNombre() %>!</h1>
        <h5>Qué bueno que estés de vuelta.</h5>
      </div>
      <%
	}
      %>
      
    <div class="container-fluid">
    	<div class="row pt-4" id="titulo">
          <h2 class="text-center">Mirate las otras ofertas de la plataforma</h2>
        </div>
      <div class="row d-flex" id="cuerpo_principal">
        
        <div id="menu_izq" class="mb-5 col-md-3 col-12">
          <div id="container-keywords" class="container">  
            <div class="row">
              <h3 class="text-center">Keywords</h3>
              <div>
                  <p class="text-center">Selecciona una keyword para filtrar las ofertas</p>
                  <%
                  List<String> keys = (List<String>) request.getAttribute("set_keys");
                  if(!keys.isEmpty()){
                  %>
                  <p>Elija cómo se deben combinar las keywords seleccionadas:</p>
					
					  <strong id="btn_and" class="btn-outline-dark btn btn-sm">TODAS</strong> Mostrar ofertas que contengan todas las keywords seleccionadas.
					  <br>
					  <strong id="btn_or" class="btn-dark btn btn-sm">ALGUNA</strong> Mostrar ofertas que contengan al menos una de las keywords seleccionadas.
						<hr>
                  
	                  <ul id="lista_keywords">
	                  <%
	                  for(String key: keys){
	                  %>
	                  	<li class="py-2 ">
	                  		<button class="key btn btn-outline-dark"><%=key%></button>
	                  	</li>
	                  <%} %>
	                  </ul>
	             <%}else{
	            	 %>
	            	<p class="text-center text-muted">No se encontraron keywords :(</p>	 
              	<%} %>
              </div>
            </div>
          </div>
        </div>
        <%
        List<DtOferta> ofertas = (List<DtOferta>) request.getAttribute("ofertas");
        if(!ofertas.isEmpty()){
        %>
        <div id="mensaje_no_ofertas" class="col d-none">
        	<h4 class="text-muted text-center">No hay ofertas con estas palabras claves :(</h4>
        </div>
        <div id="ofertas" class="col-md-9 col-sm-12 d-flex justify-content-between">
	     <div class="container">
			<div id="row_ofertas" class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4 ">
	     
          <%
          	for(DtOferta oferta : ofertas){
          		String img = oferta.getImagen();
          		Integer cantAnotados = oferta.getPostul().size();
				String descripcion = oferta.getDescripcion();
				if(descripcion.length() > 80){
					descripcion = descripcion.substring(0, 80) + "...";
				}
          		String mensaje_anotados = "";
          		if(cantAnotados == 0)
          			mensaje_anotados = "Todavía no se anotó nadie.";
          		else if(cantAnotados == 1)
          			mensaje_anotados = "¡Ya hay 1 anotado!";
          		else
          			mensaje_anotados = "¡Ya hay "+cantAnotados+" anotados!";
          		
          		//set de keywords de la oferta a String
              List<String> keywords = oferta.getKeywords();
              String keywords_str = "";
              for(String key : keywords){
              	keywords_str += key + " ";
				
              }
          		
	          %>
	          
	          <div class="row mb-3">
	          	<div class="col card_oferta" data-keywords="<%=keywords_str%>">
					<div class="card h-100">
				      	<img src="/trabajouy/imagenes?id=data/<%=img %>" class="card-img-top" alt="Imagen de oferta">
				      	<div class="card-body">
				        	<h5 class="card-title"><%=oferta.getNombre() %></h5>
	                    	<p class="card-text"><%=descripcion %></p>
				      	</div>
				      	<div class="card-body d-flex align-items-end">
	                    	<div class="w-100 d-flex justify-content-between">
	                    		<a class="my-auto py-auto btn btn-primary" href="/trabajouy/consultarOferta?nombre=<%=oferta.getNombre()%>">Leer más</a>
	                    		
	                    		<%
	                    		List<String> favs = (List<String>) request.getAttribute("favs");
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
                    			}
                    			else{
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
				      	<div class="card-footer">
	                    	<p class="card-text"><small class="text-muted"><%=mensaje_anotados %></small></p>
					    </div>
				    </div>
				 </div>
				</div>
				  
	            <%} %>
	            </div>
          </div>
        </div>
        <%
        if(usr == null){
        %>
        <div class="row my-5">
	        <div class="mt-4">
	          <a id="text-reg" href="/trabajouy/formReg">
	            <h4 class="text-center">y más...</h4>
	          </a>
	        </div>
	    </div>
	    <%
	    }
	    %>
        <% }else{ %>
	   		<div class="col">
	   			<p class="text-center text-muted">No se encontraron Ofertas :(</p>	 
	   		</div>
		    <%} %>  
      </div>
      
      </div>  
      
      
      
      
      <%
      if(usr == null){
    	  	Map<String,String> imagenes = (Map<String,String>) request.getAttribute("map_imagenes_empresas");
      		if(imagenes.size()>0){
      %>
      	<div class="mt-5" id="cont-empresas">
        <div class="container">
          <div class="row row-cols-1 row-cols-sm-2">
            <div class="col my-start col-md-8 text-center text-sm-end">
              <h2>Estas son algunas de las <span style="font-size: 1.4em;">empresas</span> que <span style="font-size: 1.4em;">confían</span> en <span style="font-size: 1.5em; font-style: bold;">trabajo.uy</span></h2>
            </div>
            <div class="col d-flex p-0 col-md-4">
              <div id="carouselEmpresas" class="mx-auto mx-sm-2 carousel carousel-dark slide" data-bs-ride="carousel">
                <div class="carousel-inner rounded">
                <%
                int i = 1;
                for(Map.Entry<String,String> img : imagenes.entrySet()){
                	if(i == 1){
                	%>
                	<div class="carousel-item active" data-bs-interval="2000">
                	<a href="/trabajouy/consultarUsuario?nickname=<%=img.getKey()%>">
                      <img src="/trabajouy/imagenes?id=data/<%=img.getValue() %>" class="d-block w-100" alt="Imagen <%=i%>">
                  	</a>
                  	</div>
                  	<%
                	}
                	else{
                	%>
                	<div class="carousel-item" data-bs-interval="2000">
                	<a href="/trabajouy/consultarUsuario?nickname=<%=img.getKey()%>">
                      <img src="/trabajouy/imagenes?id=data/<%=img.getValue() %>" class="d-block w-100" alt="Imagen <%=i %>">
                     </a>
                  	</div>
                	<%
                	} 
                	i++;
                }
                %>
                </div>
                <button class="carousel-control-next" href="#carouselEmpresas" role="button" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <%} 
      	%>

      <div class="container">
        <div class="row">
          <a href="/trabajouy/formReg" id="btn-unirse" class="mt-5 btn btn-primary p-3">Unite ahora</a>
        </div>
      </div>
      <%
      }
      %>
      <script src="media/scripts/Keywords.js"></script>
      <script src="media/scripts/favorito.js"></script>
      
	<jsp:include page="/WEB-INF/template/footer.jsp" />
</body>
</html>