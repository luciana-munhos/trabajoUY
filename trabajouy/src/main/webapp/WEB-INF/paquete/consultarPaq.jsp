<%@ page language="java" contentType="text/html" 
	pageEncoding="UTF-8"%>    
<!DOCTYPE html>
<%@page import="servidor.DtPaquete" %>
<%@page import="servidor.DtUsuario" %>
<%@page import="servidor.DtCompra" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.List" %>
<%@page import="servidor.DtCompra.CantActualTP" %>
<%@page import="servidor.DtPaquete.CantTotales" %>

<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="media/styles/bootstrap-5.3.1-dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="media/styles/consultarPaquete.css">

    <link rel="stylesheet" href="media/styles/home.css">
    <link rel="stylesheet" href="media/styles/general.css">
<title>Consultar Paquete</title>
<jsp:include page="/WEB-INF/template/head.jsp" />

</head>
<body>
	<jsp:include page="/WEB-INF/template/header.jsp" />

        <div class="container mt-5">
          <div class="row">
            <div class="border-warning border-end border-start border-5 d-block d-md-none my-auto col-12 col-md-5 col-lg-5">
                <!-- cartelito -->
                <h1 class="text-center"> CONSULTA DE PAQUETE </h1>          
            </div>
          </div><br><br>
          <div class="border-warning border-end border-5 d-none d-md-block my-auto col-12 col-md-5 col-lg-5">
            <!-- cartelito -->
            <div class="centrar"></div>
            <h1 class="text-center" > CONSULTA DE PAQUETE </h1>
          </div><br><br>
            <div class="row">
              <div class="col-md-6 col-lg-4">
                <div class="container links-perfil">
                  <div class="row">
                  <% 
                    DtPaquete infPaq = (DtPaquete) request.getAttribute("paquete");
                  	DtCompra misCompras = (DtCompra) request.getAttribute("infoCompra");
                  	boolean esEmpresa = (boolean) request.getAttribute("esEmpresa");
    				boolean noCompro = (boolean) request.getAttribute("noCompro");

                  %> <!-- CAMBIAR POR infPaq.getImg() -->
                    <img class="mt-5 mb-5 img-fluid mx-auto rounded-circle"          
                    src="/trabajouy/imagenes?id=data/<%=infPaq.getImagen() %>" 
                    alt="">
                  </div>
                  <div class="row">
                    
                      	<%                       
                      	 	if(!infPaq.getTiposPub().isEmpty()){                    				                   			
                      	 %>
                       <p><strong>Tipos de Publicación a Oferta Laboral</strong></p> 
                        <div class=" ml-3">
                            <table id="miTabla" class="ml-5">
                              <thead>
                                <tr>                       
                                    <th>Nombre</th>
                                    <%if(esEmpresa && !noCompro){ %>
                                    	<th>Cantidad Actual</th>
                                    	<th>Cantidad Total</th>         
                                    <% }else{ %>
                                   		<th>Cantidad</th>
                                    <% } %>                                                             
                                </tr>
                              </thead>
                              <tbody>
                              	<%
                              							
                              	for (servidor.DtPaquete.CantTotales.Entry tp : infPaq.getCantTotales().getEntry()){
                              	%>
                                <tr>        
                                  	<td><a class="tarjeta" href="consultarTipoPubli?nombreTP=<%=tp.getKey()%>"><%=tp.getKey() %></a></td>               
									<% if(!esEmpresa || noCompro){ %>
										
                                  		<td><%=tp.getValue()%></td>
                                    <% }else{ 
											for (servidor.DtCompra.CantActualTP.Entry tps : misCompras.getCantActualTP().getEntry()){
												if (tp.getKey().equals(tps.getKey())) {
                                    	%>
                                    	<td><%=tps.getValue() %></td>
                                		<% }} %>
                                  		<td><%=tp.getValue() %></td>
                                    <% } %>                                                                       
                                </tr>          
                                <% } %>                     
                              </tbody>
                            </table>
                          </div><br>
                          <% } else{ %>
                         <p><strong> No tiene Tipos de Publicación a Oferta Laboral</strong></p> 
                         <% }              
        				 	String ubicacion = (String) request.getAttribute("ubic");
        				 	if ("listarPaquetes".equals(ubicacion)) {  

        				 %>                  
                         
                         		<a href=listarPaquetes>Lista De Paquetes </a>
                         
                         <%}
							else{		
								DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");
						  %>     
						  
						  	<a href="consultarUsuario?nickname=<%=usr.getNickname()%>">Volver a mi perfil</a>
						  
						 <% } %>       
                    
                  </div>
                </div>
              </div>
              <div class="col-lg-8 col-md-6">
                <div class="container">
                  <div class="row separadores">
                    <div class="container">
                      <div class="row">
                        <h2 class="text-start subtitulo-box" class="text-start mt-5" style="font-size: 2em;">INFORMACION DEL PAQUETE</h2>
                      </div>
                    </div>
                    <div class="details container text-start">
                      <div class="row">
                        <div class="col-8">
                          <h6 class="">Nombre</h6>
                        </div>
                        <div class="col-4">
                          <p><%=infPaq.getNombre() %></p>
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-8">
                          <h6>Descripcion:</h6>
                        </div>
                        <div class="col-4">
                          <p><%=infPaq.getDescripcion() %></p>
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-8">
                          <h6>Validez:</h6>
                        </div>
                        <div class="col-4">
                          <p><%=infPaq.getValidez() %></p>
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-8">
                          <h6>Descuento:</h6>
                        </div>
                        <div class="col-4">
                          <p><%=infPaq.getDescuento() %></p>
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-8">
                          <h6>Fecha de Alta:</h6>
                        </div>
                        <div class="col-4">
                        	<% %>
                          <p><%=infPaq.getFechaAlta().getDay() %> / <%=infPaq.getFechaAlta().getMonth()%> / <%=infPaq.getFechaAlta().getYear() %></p>
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-8">
                          <h6>Costo:</h6>
                        </div>
                        <div class="col-4">
                          <p><%=infPaq.getCosto() %></p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>           
              </div>
            </div>
          </div><br>

        
	<%
		// ver si cuando ya tiene el paquete hacer que desaparesca el boton
		if(esEmpresa ){	
			if(noCompro){
	%>
		   	
		<!-- Button trigger modal -->
		<div class="contenedor">
		  <button type="button" class="boton-comprar" data-bs-toggle="modal" data-bs-target="#modalCompra">
		    Comprar
		  </button>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="modalCompra" tabindex="-1" aria-labelledby="modalCompraLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header" >
		        <h5 class="modal-title" id="modalCompraLabel">Comprar Paquete</h5>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" onclick="redirigir('<%=infPaq.getNombre() %>')" aria-label="Close"></button>
		      </div>
		      <div class="modal-body">
		        Se ha comprado con Exito.
		      </div>
		    </div>
		  </div>
		</div>
		<% }else{ %>	
			<div class="contenedor">
			<a href="/trabajouy/home" class="boton-comprar" data-bs-toggle="modal" data-bs-target="#modalCompra" style="text-decoration: none;">Ver info compra</a>
			</div>
			
			<div class="modal fade" id="modalCompra" tabindex="-1" aria-labelledby="modalCompraLabel" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h1 class="modal-title fs-5" id="modalCompraLabel">Info de compra</h1>
			        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			      </div>
			      <div class="modal-body">
			        <%
			        DtCompra infoCompra = (DtCompra) request.getAttribute("infoCompra");
			        String fechaVen = (String) request.getAttribute("fechaVenc");
			        String fechaCom = (String) request.getAttribute("fechaCompra");
			        
			        %>
			        <div class="container">
                       <div class="row">
                           <h5>Fecha de compra</h5>
                           <p><%=fechaCom%></p>
                       </div>
                       <div class="row">
                           <h5>Fecha de vencimiento</h5>
                           <p><%=fechaVen%></p>
                       </div>
                       <%
                       boolean vencido = (boolean) request.getAttribute("estaVencido");
                       if(vencido){
                       %>
                       <div class="row">
                           <p class="text-muted">¡Está vencido!</p>
                       </div>
                       <%} %>
                    </div>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
			      </div>
			    </div>
			  </div>
			</div>
		<% }} %>
		
		<jsp:include page="/WEB-INF/template/footer.jsp" />
		<script src="media/scripts/consultaPaquete.js"></script>
		
</body>
</html>