<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="java.util.List" %>

<%@page import="servidor.DtUsuario" %>
<%@page import="servidor.DtOferta" %>


<%DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");%>
<html>
<head>
<meta charset="UTF-8">
<title>Inicio</title>
<jsp:include page="/WEB-INF/template/head.jsp" />
</head>
<body>
	
	<jsp:include page="/WEB-INF/template/headerMobile.jsp" />
	
      <div id="mensaje-bienvenida" class="row text-center m-4">
        <h1>¡Hola, <%=usr.getNombre() %>!</h1>
        <h5>Qué bueno que estés de vuelta.</h5>
      </div>
      
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
        <div id="ofertas" class="col-md-9 col-12 d-flex justify-content-between">
        <div class=container>
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
		                    <a class="btn btn-primary" href="/trabajouy/consultarOferta?nombre=<%=oferta.getNombre()%>">Leer más</a>
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
        <%} %>
      
     </div>  
     </div>
     
    <script src="media/scripts/Keywords.js"></script>
	<jsp:include page="/WEB-INF/template/footer.jsp" />
</body>
</html>