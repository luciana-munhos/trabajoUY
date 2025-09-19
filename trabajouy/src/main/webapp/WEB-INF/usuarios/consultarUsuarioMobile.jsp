<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.Set" %>
<%@page import="java.util.List" %>
<%@page import="servidor.DtUsuario" %>
<%@page import="servidor.DtPostulante" %>
<%@page import="servidor.DtCompra" %>
<%@page import="servidor.DtEmpresa" %>
<%@page import="servidor.DtOferta" %>
<%@page import="servidor.DtPostulacion" %>
<%@page import="servidor.DtPaquete" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.GregorianCalendar" %>
<%@page import="javax.xml.datatype.XMLGregorianCalendar" %>

<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Consulta de Usuario</title>
	<jsp:include page="/WEB-INF/template/head.jsp" />
</head>

<body>
	<jsp:include page="/WEB-INF/template/headerMobile.jsp" />
	
	<!-- consigo Usuario y su tipo -->
	<%
DtUsuario u = (DtUsuario) request.getAttribute("usuario_consultado");
if (u == null){
	// no existe el usuario que se quiere consultar
	%>
	<br></br>
	<h2 class="text-start text-center"> El usuario que intenta consultar no existe </h2>
	<%
}else{
	
	String tipoU = (String) request.getAttribute("usuario_tipo");
	String nick = u.getNickname();
	String nom = u.getNombre();
	String ap = u.getApellido();
	String im = u.getImagen();
	String mail = u.getCorreo();
	
	// para fechas
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	// para fechas
	GregorianCalendar gregC;
	Date date;
	String fechaFormateada;
	
	// si un usuario consulta su perfil -> miPerfil = true
	DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");
	Boolean miPerfil = usr != null && nick.equals(usr.getNickname());
	%>
	
	<nav class="ms-3 mt-5" aria-label="breadcrumb">
	<ol class="breadcrumb">
	<li class="breadcrumb-item"><a href="/trabajouy/home">Home</a></li>
	<%if (miPerfil){
	%>
    <li class="breadcrumb-item active" aria-current="page">Mi perfil</li>
	</ol>
	</nav>		
	<%
	}else{
	%>
    <li class="breadcrumb-item active" aria-current="page"><%=u.getNickname()%></li>
	</ol>
	</nav>
	<%}
	
	if (tipoU.equals("Postulante")){ // consulta a Postulante
		DtPostulante p = (DtPostulante) u;
	%>	
	      <div class="container pt-5 pt-md-3">
	      
	        <div class="row">
	          <div class="my-auto col-12 col-md-5 col-lg-5">
	            <!-- foto y links -->
	            <img src= "/trabajouy/imagenes?id=data/<%=im%>" class="rounded w-100" alt="">
	          </div>
	          <div class="border-warning border-end border-5 d-none d-md-block my-auto col-12 col-md-5 col-lg-5">
	              <!-- cartelito -->
	              <h1 class="text-center"> CONSULTA DE USUARIO </h1>
	              <h2 class="text-center"> <%=nom%> <%=ap%></h2>
	          </div>
	  
	          <!-- pgs -->
	        <div class="row"> 
	          <div class="col container ps-5 mt-5">
	              <div class="row text-center">
	              <ul class="nav nav-underline">
	                  <li class="nav-item">
	                  <a class="nav-link active" data-bs-toggle="tab" href="#infoBasica">Informacion Basica</a>
	                  </li>
	                  <%
	                  if (miPerfil){
	                  %>
		                  <li class="nav-item">
		                  <a class="nav-link" data-bs-toggle="tab" href="#postulaciones">Postulaciones</a>
		                  </li>
	                  <%
	                  }
	                  %>
	              </ul>
	              </div>
	          </div>
	         </div> 
	  
	  	     <!--  estructura de contenido de paginas  -->
	          <div class="tab-content border-top border-2 ">
	          	  <!-- primera pagina -->
	              <div class="tab-pane container active" id="infoBasica">
	                <div class="container pt-3">
	                  <h4 class="text-start subtitulo-box">
	                      INFORMACION BASICA 
	                    </h4>
	                    <div class="row">
	                      <div class="col">
	                      <div class="table-responsive">
	                        <table class="table">
	                          <tbody>
	                            <tr>
	                              <th scope="row">Nickname</th>
	                              <td><%=nick%></td>
	                            </tr>
	                            <tr>
	                              <th scope="row">Nombre</th>
	                              <td><%=nom%> <%=ap%></td>
	                            </tr>
	                            <tr>
	                              <th scope="row">Correo electronico</th>
	                              <td><%=mail%></td>
	                            </tr>
	                            <tr>
	                              <th scope="row">Fecha de nacimiento</th>
	                              <%
	                           // defino formato deseado de fecha
	                              gregC = p.getFechaNac().toGregorianCalendar();
	                              date = gregC.getTime();
	                              fechaFormateada = dateFormat.format(date);
	                              %>
	                              <td><%=fechaFormateada%></td>
	                            </tr>
	                            <tr>
	                              <th scope="row">Nacionalidad</th>
	                              <td><%=p.getNacionalidad()%></td>
	                            </tr>
	                          </tbody>
	                        </table>
	                        </div> <!--  fin table responsive -->
	                      </div> 
	                    </div>
	                    
	                    
		                </div>
	                
	              </div> <!-- fin primera pagina -->
	              
	              <%
	              if (miPerfil){
	              %>
	              
	              <!-- segunda pagina -->
	              <div class="tab-pane container fade" id="postulaciones">
	                    <div class="row pt-3"> <!--  para ver si se arregla -->
	                     <div class="container"> <!-- para ver si se arregla -->
	                      
	                      <div class="row">
	                          <h4 class="text-start subtitulo-box">
	                          POSTULACIONES
	                          </h4>
	                      </div>
	                      
	                      <!-- consigo postulaciones -->
	                      <% 
	                      // las postulaciones pueden estar en la request o con una op p.getPostulaciones() (dtpost + id_oferta necesito)
	                      List<DtPostulacion> postulaciones = (List<DtPostulacion>) request.getAttribute("postulaciones_propias");
	            		  if (postulaciones.isEmpty()){
	            		  %>
		                    <div class="row">
		                        <h5 class="text-center d-flex justify-content-center mt-4">
		                          Sus postulaciones: no hay
		                        </h5>
		                    </div>
		                  <%
	            		  }else{ // SEPARAR EN 3 PAGINAS
	            			  List<DtPostulacion> esperando_resultado = (List<DtPostulacion>) request.getAttribute("esperando_resultado");
	            			  List<DtPostulacion> con_resultado = (List<DtPostulacion>) request.getAttribute("con_resultado");
	            			  List<DtPostulacion> sin_resultado = (List<DtPostulacion>) request.getAttribute("sin_resultado"); // finalizaron
		                  %> 
		                  
						    <!-- pgs postulaciones -->
					        <div class="row"> 
					          <div class="col container">
					              <div class="row text-center">
					              <ul class="nav nav-underline">
					                  <li class="nav-item">
					                  <a class="nav-link active" data-bs-toggle="tab" href="#esperando_res">Esperando</a>
					                  </li>
					                  <li class="nav-item">
					                  <a class="nav-link" data-bs-toggle="tab" href="#con_res">Con resultado</a>
					                  </li>
					                  <li class="nav-item">
					                  <a class="nav-link" data-bs-toggle="tab" href="#sin_res">Finalizadas</a>
					                  </li>
					              </ul>
					              </div>
					          </div>
					         </div> 
					         
					         
					         <div class="tab-content border-top border-2">  <!--  IMPORTANTISIMO DE NO OLVIDAR!!!!!!!!!!!!! -->
							                  
			                  <div class="tab-pane container active" id="esperando_res">
			                  <%if (esperando_resultado.isEmpty()){%> <!-- esperando_resultado.isEmpty()  -->
			                  	<h5 class="pt-3">No hay postulaciones</h5>
			                  <%}else{ %>
			                    <h5 class="pt-3">Aún no se han seleccionado postulantes en las siguientes ofertas</h5>
			                  	<!-- info de postulaciones esperando res -->
		                        <div class="row d-flex m-1 mt-4">
		                          <div id ="ofertas" class="col-md-12 col-sm-12 justify-content-between"> 
		                            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
		                        
		                            <%
		                            DtOferta of = null;
		                            int i = 0;
		                            for (DtPostulacion pos: esperando_resultado){
		                            	of = pos.getMiOferta();
		                            %>
		                              <!-- mostrar info de una postulacion -->
		                              <div class="col">
		                                <div class="card_oferta mx-auto card">
		                                  <div class="container row g-0">
		                                    <div class="col-md-12">
		                                      <img class="w-100" src="/trabajouy/imagenes?id=data/<%=of.getImagen()%>" class="img-fluid rounded-start" alt="...">
		                                    </div>
		                                    <div class="col-md-12">
		                                      <div class="card-body">
		                                      <!--  aca necesito el nombre de la empresa q tiene la oferta, crear funcion  -->
		                                        <h5 class="card-title"><%=of.getNombre()%></h5>
		                                        <p class="card-text"><%=of.getNickEmpresa()%></p>
		                                        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modal-info-postulacion-e<%=i%>">Ver detalles</button>
		                                        <div class="my-2">
		                                        	<p class="card-text mb-0"><small class="text-muted">Fecha de postulacion:</small></p>
			                                        <%
			                                         // defino formato deseado de fecha
			                                         gregC = pos.getFecha().toGregorianCalendar();
			       	                              	 date = gregC.getTime();
			       	                             	 fechaFormateada = dateFormat.format(date);
			                                         %>
			                                        <p class="card-text"><small class="text-muted"><%=fechaFormateada%></small></p>
		                                        </div>
		                                        
		                                        <a class="btn btn-secondary btn-sm" href="/trabajouy/consultarOferta?nombre=<%=of.getNombre()%>">Info oferta</a>
		                                      </div> <!--  fin card body -->
		                                    </div>
		                                  </div>
		                                </div> <!--  fin card_oferta mx-auto card -->
		                              </div> <!--  fin col -->
		                              
		                              <!-- modal asociado a esa postulacion --> 
									  <div class="modal fade" id="modal-info-postulacion-e<%=i%>" tabindex="-1" aria-labelledby="modal-info-postulacion" aria-hidden="true">
									    <div class="modal-dialog">
									        <div class="modal-content">
									            <div class="modal-header">
									                <h1 class="modal-title fs-5" id="exampleModalLabel">Información de la postulación</h1>
									                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
									            </div>
									            <div class="modal-body">
									                <div class="container">
									                    <div class="row">
									                        <h5>CV Reducido</h5>
									                        <p><%= pos.getCVR() %></p>
									                    </div>
									                    <div class="row">
									                        <h5>Motivación</h5>
									                        <p><%= pos.getMotivacion() %></p>
									                    </div>
									                    <%
								                        	String video = pos.getVideo();
								                        	if(video != null && !video.equals("")){
								                        %>
									                    
														<div class="row">
														   <h5>Video de presentación</h5>
														   <div class="col-12 text-center"> <!-- Utiliza la clase "col-12" para ocupar todo el ancho de la fila -->
														       <div class="embed-responsive embed-responsive-16by9"> <!-- Utiliza las clases de "embed-responsive" para crear un contenedor responsivo -->
														            <div class="video-container">
														            <iframe class="embed-responsive-item" src="https://www.youtube.com/embed/<%= video %>" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
														        	</div>
														        </div>
														    </div>
														</div>
														
														<%
								                        }
								                        %>
								                        
								                        <div class="row">
									                        <%
									                     	  // defino formato deseado de fecha
								                              gregC = pos.getFecha().toGregorianCalendar();
								                              date = gregC.getTime();
								                              fechaFormateada = dateFormat.format(date);
															%>
									                            <p class="text-muted">Fecha de postulación: <%=fechaFormateada%></p>
								                        </div>
									                </div>
									            </div>
									            <div class="modal-footer">
                                                    <div class="container-fluid">
                                                        <div class="row justify-content-between">
                                                            <div class="col">
									                			<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
									            			</div>
									            		</div>
									            	</div>
									            </div>
									        </div>
									    </div>
									  </div> <!--  fin modal asociado a una postulacion -->
		                            <%	
		                            	i = i + 1;
		                            }
		                            %>
		                            </div>
		                          </div>
		                        </div>
		                        <%} %>
			                  </div> <!-- fin tab-pane esperando_res -->
			                  
				                  
				              <div class="tab-pane container fade" id="con_res">
				              <%if (con_resultado.isEmpty()){%> <!-- con_resultado.isEmpty()  -->
			                  	<h5 class="pt-3">No hay postulaciones</h5>
			                  <%}else{ %>
			                  	<h5 class="pt-3">Aqui se encuentran las ofertas con resultados</h5>
			                  	<!-- info de postulaciones con res -->
		                        <div class="row d-flex m-1 mt-4">
		                          <div id ="ofertas" class="col-md-12 col-sm-12 justify-content-between"> 
		                            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
		                        
		                            <%
		                            DtOferta of = null;
		                            int i = 0;
		                            for (DtPostulacion pos: con_resultado){
		                            	of = pos.getMiOferta();
		                            %>
		                              <!-- mostrar info de una postulacion -->
		                              <div class="col">
		                                <div class="card_oferta mx-auto card">
		                                  <div class="container row g-0">
		                                    <div class="col-md-12">
		                                      <img class="w-100" src="/trabajouy/imagenes?id=data/<%=of.getImagen()%>" class="img-fluid rounded-start" alt="...">
		                                    </div>
		                                    <div class="col-md-12">
		                                      <div class="card-body">
		                                      <!--  aca necesito el nombre de la empresa q tiene la oferta, crear funcion  -->
		                                        <h5 class="card-title"><%=of.getNombre()%></h5>
		                                        <p class="card-text"><%=of.getNickEmpresa()%></p>
		                                        <div class = "row">   
		                                        	<div class = "col-md-6 pt-1">        
		                                        		<button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modal-info-postulacion-cr<%=i%>">Postulación</button>
		                                            </div>
		                                            <div class = "col-md-6 pt-1">
			                                        	<button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#modal-resultado-postulacion<%=i%>">Resultado</button>
			                                        </div>
		                                        </div>
		                                        
		                                        <div class="my-2">
		                                        	<p class="card-text mb-0"><small class="text-muted">Fecha de postulación:</small></p>
			                                        <%
			                                         // defino formato deseado de fecha
			                                         gregC = pos.getFecha().toGregorianCalendar();
			       	                              	 date = gregC.getTime();
			       	                             	 fechaFormateada = dateFormat.format(date);
			                                         %>
			                                        <p class="card-text"><small class="text-muted"><%=fechaFormateada%></small></p>
			                                    </div>
		                                        <a class="btn btn-secondary btn-sm" href="/trabajouy/consultarOferta?nombre=<%=of.getNombre()%>">Info oferta</a>
		                                      </div> <!--  fin card body -->
		                                    </div>
		                                  </div>
		                                </div> <!--  fin card_oferta mx-auto card -->
		                              </div> <!--  fin col -->
		                              
		                              <!-- modal asociado a esa postulacion con resultado--> 
									  <div class="modal fade" id="modal-info-postulacion-cr<%=i%>" tabindex="-1" aria-labelledby="modal-info-postulacion" aria-hidden="true">
									    <div class="modal-dialog">
									        <div class="modal-content">
									            <div class="modal-header">
									                <h1 class="modal-title fs-5" id="exampleModalLabel">Información de la postulación</h1>
									                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
									            </div>
									            <div class="modal-body">
									                <div class="container">
									                    <div class="row">
									                        <h5>CV Reducido</h5>
									                        <p><%= pos.getCVR() %></p>
									                    </div>
									                    <div class="row">
									                        <h5>Motivación</h5>
									                        <p><%= pos.getMotivacion() %></p>
									                    </div>
									                    <%
								                        	String video = pos.getVideo();
								                        	if(video != null && !video.equals("")){
								                        %>
									                    <div class="row">
								                        	<h5>Video de presentación</h5>
								                        	<div class="text-center">
								                        		<div class="video-container">
								                        			<iframe width="400" height="215" src="https://www.youtube.com/embed/<%= video %>" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
								                        		</div>
								                        	</div>
								                        </div>
								                        <%
								                        }
								                        %>
								                        <div class="row">
									                        <%
									                     	  // defino formato deseado de fecha
								                              gregC = pos.getFecha().toGregorianCalendar();
								                              date = gregC.getTime();
								                              fechaFormateada = dateFormat.format(date);
															%>
									                            <p class="text-muted">Fecha de postulación: <%=fechaFormateada%></p>
								                        </div>
								                        
									                </div>
									            </div>
									            <div class="modal-footer">
                                                        <div class="row justify-content-between">
                                                            <div class="col">
									                			<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
									            			</div>
									            		</div>
									            </div>
									        </div>
									    </div>
									  </div> <!--  fin modal asociado a una postulacion -->
									  
									  <!--  modal resultado  -->
									   <div class="modal fade" id="modal-resultado-postulacion<%=i%>" tabindex="-1" aria-labelledby="modal-info-postulacion" aria-hidden="true">
									    <div class="modal-dialog">
									        <div class="modal-content">
									            <div class="modal-header">
									                <h1 class="modal-title fs-5" id="exampleModalLabel">Resultado de la postulación</h1>
									                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
									            </div>
									            <div class="modal-body">
									                <div class="container">
									                    <div class="row">
									                        <h5>Posición en el ranking</h5>
									                        <p class="text-success">Puesto <%=pos.getOrden()%></p>
									                    </div>
									                    <div class="row">
									                    <%
								                            // defino formato deseado de fecha
								                            gregC = pos.getFechaSelec().toGregorianCalendar();
								                            date = gregC.getTime();
								                            fechaFormateada = dateFormat.format(date);    
								                         %>
									                        <h5>Fecha de selección</h5>
									                        <p><%=fechaFormateada%></p>
									                    </div>
									                    <div class="row">
									                        <h5>Muchas gracias!</h5>
									                    </div>
									                </div>
									            </div>
									            <div class="modal-footer">
                                                    <div class="container-fluid">
                                                        <div class="row justify-content-between">
                                                            <div class="col">
                                                            	<a target="_blank" href="/trabajouy/pdfPostulacion?oferta_pdf=<%=of.getNombre()%>">Descargar PDF</a>    
                                                            </div>
                                                            <div class="col">
									                			<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
									            			</div>
									            		</div>
									            	</div>
									            </div>
									        </div>
									    </div>
									  </div> <!--  fin modal resultado -->
		                            <%	
		                            	i = i + 1;
		                            }
		                            %>
		                            </div>
		                          </div>
		                        </div>
		                       <%} %>
			                  </div>
			                  
			                  <div class="tab-pane container fade" id="sin_res">
			                  <%if (sin_resultado.isEmpty()){%> <!-- sin_resultado.isEmpty()  -->
			                  	<h5 class="pt-3">No hay postulaciones</h5>
			                  <%}else{ %>
			                  	<!-- info de postulaciones sin res -->
			                  	<h5 class="pt-3">Lamentamos que estas ofertas no hayan resultado</h5>
		                        <div class="row d-flex m-1 mt-4">
		                          <div id ="ofertas" class="col-md-12 col-sm-12 justify-content-between"> 
		                            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
		                        
		                            <%
		                            DtOferta of = null;
		                            int i = 0;
		                            for (DtPostulacion pos: sin_resultado){ // recorrer sin_resultado
		                            	of = pos.getMiOferta();
		                            %>
		                              <!-- mostrar info de una postulacion -->
		                              <div class="col">
		                                <div class="card_oferta mx-auto card">
		                                  <div class="container row g-0">
		                                    <div class="col-md-12">
		                                      <img class="w-100" src="/trabajouy/imagenes?id=data/<%=of.getImagen()%>" class="img-fluid rounded-start" alt="...">
		                                    </div>
		                                    <div class="col-md-12">
		                                      <div class="card-body">
		                                      <!--  aca necesito el nombre de la empresa q tiene la oferta, crear funcion  -->
		                                        <h5 class="card-title"><%=of.getNombre()%></h5>
		                                        <p class="card-text"><%=of.getNickEmpresa()%></p>
		                                        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modal-info-postulacion-sr<%=i%>">Ver detalles</button>
		                                        <div class="my-2">
		                                        	<p class="card-text mb-0"><small class="text-muted">Fecha de postulacion:</small></p>
			                                        <% // formato fecha
										              gregC = pos.getFecha().toGregorianCalendar();
					       	                          date = gregC.getTime();
					       	                          fechaFormateada = dateFormat.format(date);
									                %>
			                                        <p class="card-text"><small class="text-muted"><%=fechaFormateada%></small></p>
		                                        </div>
		                                        
		                                        <a class="btn btn-secondary btn-sm" href="/trabajouy/consultarOferta?nombre=<%=of.getNombre()%>">Info oferta</a>
		                                      </div> <!--  fin card body -->
		                                    </div>
		                                  </div>
		                                </div> <!--  fin card_oferta mx-auto card -->
		                              </div> <!--  fin col -->
		                              
		                              <!-- modal asociado a esa postulacion sin resultado --> 
									  <div class="modal fade" id="modal-info-postulacion-sr<%=i%>" tabindex="-1" aria-labelledby="modal-info-postulacion" aria-hidden="true">
									    <div class="modal-dialog">
									        <div class="modal-content">
									            <div class="modal-header">
									                <h1 class="modal-title fs-5" id="exampleModalLabel">Información de la postulación</h1>
									                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
									            </div>
									            <div class="modal-body">
									                <div class="container">
									                    <div class="row">
									                        <h5>CV Reducido</h5>
									                        <p><%= pos.getCVR() %></p>
									                    </div>
									                    <div class="row">
									                        <h5>Motivación</h5>
									                        <p><%= pos.getMotivacion() %></p>
									                    </div>
									                    <%										                	
								                        	String video = pos.getVideo();
								                        	if(video != null && !video.equals("")){
								                        %>
									                    <div class="row">
								                        	<h5>Video de presentación</h5>
								                        	<div class="text-center">
								                        	<div class="video-container">
								                        		<iframe width="400" height="215" src="https://www.youtube.com/embed/<%= video %>" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
								                        	</div>
								                        	</div>
								                        </div>
								                        <%
								                        }
								                        %>
								                        <div class="row">
									                        <%
									                     	  // defino formato deseado de fecha
								                              gregC = pos.getFecha().toGregorianCalendar();
								                              date = gregC.getTime();
								                              fechaFormateada = dateFormat.format(date);
															%>
									                            <p class="text-muted">Fecha de postulación: <%=fechaFormateada%></p>
								                        </div>
									                </div>
									            </div>
									            <div class="modal-footer">
									                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
									            </div>
									        </div>
									    </div>
									  </div> <!--  fin modal asociado a una postulacion -->
		                            <%	
		                            	i = i + 1;
		                            }
		                            %>
		                            </div>
		                          </div>
		                        </div>
		                       <%} %>
			                  </div>
			                  
			                  
			                 </div> <!--  fin todo tipo de postulacion content -->
		                  
		                 
	                      
	                      
	                      <%
	            		  } // fin del else si hay postulaciones para mostrar
	                      %>
	                  </div>
	              </div>
	              
	              <%	  
	              }
	              %>
	        
			  </div> <!--  fin de  segunda pagina de #postulaciones-->
			  
			</div> <!--  fin de estructura de paginas grande -->


	        </div> <!--  este y el de abajo agarran a todo lo del postulante -->
	      </div> <!-- fin container grande de postulante-->

	<%	
	}else{ // consulta a Empresa
		DtEmpresa e = (DtEmpresa) u;
	%>	
	    <div class="container pt-5 pt-md-3">
	      <div class="row">
	      
	        <div class="my-auto col-12 col-md-5 col-lg-5">
	          <!-- foto y links -->
	            <img src= "/trabajouy/imagenes?id=data/<%=im%>" class="rounded w-100" alt="">
	        </div>
	        <div class="border-warning border-end border-5 d-none d-md-block my-auto col-12 col-md-5 col-lg-5">
	            <!-- cartelito -->
	            <h1 class="text-center"> CONSULTA DE USUARIO </h1>
	            <h2 class="text-center"> <%=nick%></h2>
	            <h3 class="text-center">
	                <%=nom%> <%=ap%>
	            </h3>
	        </div>

	        <!-- pgs -->
	      <div class="row">
	        <div class="col container ps-5 mt-5">
	            <div class="row text-center">
	            <ul class="nav nav-underline">
	                <li class="nav-item">
	                <a class="nav-link active" data-bs-toggle="tab" href="#infoBasica">Informacion Basica</a>
	                </li>
	                <li class="nav-item">
	                <a class="nav-link" data-bs-toggle="tab" href="#ofertas">Ofertas</a>
	                </li>
	                <%
	                if (miPerfil){
	                %>
	                <li class="nav-item">
	                <a class="nav-link" data-bs-toggle="tab" href="#paquetes">Paquetes</a>
	                </li>
	                <%
	                }
	                %>
	            </ul>
	            </div>
	        </div>
	      </div> <!-- fin cabezal de pgs -->


			<!--  estructura de las paginas -->
	        <div class="tab-content border-top border-2">
	        
	        	<!--  primera pagina de info basica -->
	            <div class="tab-pane container active" id="infoBasica">
	              <div class="container pt-3">
	                <h4 class="text-start subtitulo-box">
	                    INFORMACION BASICA 
	                  </h4>
	                 
	                <div class = "container pt-3">
	                <div class="row">
	                    <div class="col">
	                    <div class="table-responsive">
	                      <table class="table">
	                        <tbody>
	                          <tr>
	                            <th scope="row">Nickname</th>
	                            <td><%=nick%></td>
	                          </tr>
	                          <tr>
	                            <th scope="row">Nombre</th>
	                            <td><%=nom%> <%=ap%></td>
	                          </tr>
	                          <tr>
	                            <th scope="row">Correo electronico</th>
	                            <td><%=mail%></td>
	                          </tr>
	                          <tr>
								  <th scope="row">Link</th>
								  <td class="text-truncate"><%= e.getLink() %></td>
								</tr>

	                        </tbody>
	                      </table>
	                     </div> <!--  de table responsive -->
	                    </div>
	                  </div>
	                  </div> <!--  fin container copiado de descripcion -->

	                  <div class="container pt-3 mt-5">
	                    <!-- debe estar debajo en la primera pg tmb -->
	                  <div class="row subtitulo-box">
	                    <h4>
	                      DESCRIPCION
	                    </h4>
	                  </div>
	                  <div class="row sub">
	                    <div class="col border-start border-warning border-5">
	                      <p class="p-2"><%=e.getDescripcion()%></p>
	                    </div>
	                  </div>
	                </div>
	              </div>
	            </div>
	            
	            <!-- segunda pg -->
	            <div class="tab-pane container fade" id="ofertas">
	              
	                <div class="container pt-3">
	                    <div class="row">
	                        <h4 class="text-start subtitulo-box">
	                        OFERTAS LABORALES
	                        </h4>
	                    </div>
	                    <div class="row text-center">
	                    <ul class="nav nav-underline">
	                        <li class="nav-item">
	                        <a class="nav-link active" data-bs-toggle="tab" href="#confirmadas">Confirmadas</a>
	                        </li>
	                       	<%
	                       	if (miPerfil){
	                       	%>
		                        <li class="nav-item">
		                        <a class="nav-link" data-bs-toggle="tab" href="#rechazadas">Rechazadas</a>
		                        </li>
		                        <li class="nav-item">
		                        <a class="nav-link" data-bs-toggle="tab" href="#pendientes">Pendientes</a>
		                        </li>
	                        <%
	                       	}
	                       	%>
	                    </ul>
	                    </div>
	                </div>

	                <!-- las pgs dentro de las pgs-->
	                <div class="tab-content border-top border-2"> <!--  agregue esto q esta en postulacion de border -->
	                    <div class="tab-pane container active" id="confirmadas">
	                      <div class="container">
	                      	<% 
	                      	List<DtOferta> ofConfirmadas = (List<DtOferta>) request.getAttribute("ofertas_confirmadas");
	                      	// existe e.getDTOfertasConfirmadas() q retorna set dt oferta
	                      	%>
	                      	
	                      	<!--  agregado  -->
	                      	
	                      	<div class="row pt-3"> <!--  a ver si se arreglaa -->
	                      	<div class="container"> <!--  a ver si se arreglaa -->

	                      	
	                      	
	                      	<!--  fin agregado  -->
	                      	
	                      	<div class="row"> <!--  lo agregue por las du -->
	                        <h5 class="text-center justify-content-center mt-4"> <!--  chau d-flex -->
	                          Sus ofertas confirmadas
	                        </h5>
	                        </div> <!--  fin por las du -->
	                        <div class="row d-flex m-1 mt-4">
	                          <div id="ofertas" class="col-md-12 col-sm-12 justify-content-between">
	                            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
	                            
	                            <% 
	                            if (ofConfirmadas.isEmpty()){ //row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4 
	                            %>
	                              <div class="row">
	   	                          <h5 class="text-center d-flex justify-content-center mt-4">
	   	                            Sus ofertas confirmadas: no hay
	   	                          </h5>
	   	                          </div>
	   	                        <%
	                            }else{
	                            	for (DtOferta oc: ofConfirmadas){
	                            %>
	                                <!-- carta de una oferta confirmada  -->
		                              <div class="col">
		                                <div class="card_oferta mx-auto card">
		                                  <div class="container row g-0">
		                                    <div class="col-md-12">
		                                      <img class="w-100" src="/trabajouy/imagenes?id=data/<%=oc.getImagen()%>" class="img-fluid rounded-start" alt="...">
		                                    </div>
		                                    <div class="col-md-12">
		                                      <div class="card-body">
		                                        <h5 class="card-title"><%=oc.getNombre()%></h5>
		                                        <p class="card-text"><%=oc.getDescripcion()%></p>
		                                        <a class="btn btn-primary btn-sm" href="/trabajouy/consultarOferta?nombre=<%=oc.getNombre()%>">Leer más</a>
		                                      </div>
		                                    </div>
		                                  </div>
		                                </div>
		                              </div>
	                            <%	
	                            	}
	                            }
	                            %>
	                         
	                            </div>
	                          </div>
	                        </div>
	                        
	                        
	                        
	                        <!-- ultimo agregado -->
	                        </div> <!--  fin de a ver si se arreglaa -->
	                      	</div> <!--  fin de a ver si se arreglaa -->
	                        
	                        <!-- fin ultimo agregado -->
	                        
	                        
	                        
	                      </div> <!-- fin container cards ofertas confirmadas -->
	                    </div>
	                    
	                    
	               <%
	               if(miPerfil){
	               %>
	                	
	                    <div class="tab-pane container fade" id="rechazadas">
	                      <div class="container">
	                      	<% 
	                      	List<DtOferta> ofRechazadas = (List<DtOferta>) request.getAttribute("ofertas_rechazadas");
	                      	%>
	                        <h5 class="text-center d-flex justify-content-center mt-4">
	                          Sus ofertas rechazadas
	                        </h5>
	                        <div class="row d-flex m-1 mt-4">
	                          <div id="ofertas" class="col-md-12 col-sm-12 justify-content-between">
	                            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4 ">
	                            <% 
	                            if (ofRechazadas.isEmpty()){
	                            %>
	                              <div class="row">
	   	                          <h5 class="text-center d-flex justify-content-center mt-4">
	   	                            Sus ofertas rechazadas: no hay
	   	                          </h5>
	   	                          </div>
	   	                        <%
	                            }else{
	                            	for (DtOferta or: ofRechazadas){
	                            %>
	                                <!-- carta de una oferta rechazada  -->
		                              <div class="col">
		                                <div class="card_oferta mx-auto card">
		                                  <div class="container row g-0">
		                                    <div class="col-md-12">
		                                      <img class="w-100" src="/trabajouy/imagenes?id=data/<%=or.getImagen()%>" class="img-fluid rounded-start" alt="...">
		                                    </div>
		                                    <div class="col-md-12">
		                                      <div class="card-body">
		                                        <h5 class="card-title"><%=or.getNombre()%></h5>
		                                        <p class="card-text"><%=or.getDescripcion()%></p>
		                                        <a class="btn btn-primary btn-sm" href="/trabajouy/consultarOferta?nombre=<%=or.getNombre()%>">Leer más</a>
		                                      </div>
		                                    </div>
		                                  </div>
		                                </div>
		                              </div>
	                            <%	
	                            	}
	                            }
	                            %>
	                         
	                            </div>
	                          </div>
	                        </div>
	                      </div> <!-- fin container cards ofertas rechazadas -->
	                    </div>
	                    
	                    
	                   <div class="tab-pane container fade" id="pendientes">
	                      <div class="container">
	                      	<% 
	                      	List<DtOferta> ofPendientes = (List<DtOferta>) request.getAttribute("ofertas_pendientes");
	                      	%>
	                        <h5 class="text-center d-flex justify-content-center mt-4">
	                          Sus ofertas pendientes
	                        </h5>
	                        <div class="row d-flex m-1 mt-4">
	                          <div id="ofertas" class="col-md-12 col-sm-12 justify-content-between">
	                            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4 ">
	                            <% 
	                            if (ofPendientes.isEmpty()){
	                            %>
	                              <div class="row">
	   	                          <h5 class="text-center d-flex justify-content-center mt-4">
	   	                            Sus ofertas pendientes: no hay
	   	                          </h5>
	   	                          </div>
	   	                        <%
	                            }else{
	                            	for (DtOferta op: ofPendientes){
	                            %>
	                                <!-- carta de una oferta rechazada  -->
		                              <div class="col">
		                                <div class="card_oferta mx-auto card">
		                                  <div class="container row g-0">
		                                    <div class="col-md-12">
		                                      <img class="w-100" src="/trabajouy/imagenes?id=data/<%=op.getImagen()%>" class="img-fluid rounded-start" alt="...">
		                                    </div>
		                                    <div class="col-md-12">
		                                      <div class="card-body">
		                                        <h5 class="card-title"><%=op.getNombre()%></h5>
		                                        <p class="card-text"><%=op.getDescripcion()%></p>
		                                        <a class="btn btn-primary btn-sm" href="/trabajouy/consultarOferta?nombre=<%=op.getNombre()%>">Leer más</a>
		                                      </div>
		                                    </div>
		                                  </div>
		                                </div>
		                              </div>
	                            <%	
	                            	}
	                            }
	                            %>
	                         
	                            </div>
	                          </div>
	                        </div>
	                      </div> <!-- fin container cards ofertas pendientes -->
	                    </div>
	                    
	                    <%
	                     } // fin mi perfil de ofertas mostradas
	                    %>
	                    
	            </div> 
	          </div>	 <!--  fin segunda pg  -->
	                     
	            <%
	            if (miPerfil){
	            %>
	            <div class="tab-pane container fade" id="paquetes">
	              <div class="container">
	                <div class="row pt-3">
	                    <div class="container">
	                      <div class="row">
	                        <h4 class="text-start subtitulo-box">
	                          PAQUETES
	                        </h4>
	                      </div>
	                      <%
	                      List<DtCompra> compras = (List<DtCompra>) request.getAttribute("paquetes_propios"); 
	                      if (compras.isEmpty()){
	                            %>
	                              <div class="row">
	   	                          <h5 class="text-center d-flex justify-content-center mt-4">
	   	                            Sus paquetes: no hay
	   	                          </h5>
	   	                          </div>
	   	                        <%
	                      }else{
	                      %>
	                      <h5 class="text-center d-flex justify-content-center mt-4">
	                        Sus compras de paquetes
	                      </h5>
	                      <div class="row d-flex m-1 mt-4">
	                        <div id="ofertas" class="col-md-12 col-sm-12 justify-content-between">
	                          <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4 ">
	                            
	                            <%
	                            String fechaCompra;
	                            for (DtCompra comp: compras){
	                            	DtPaquete paq = comp.getPaquete();
	                            %>
	                            	
	                            <!--  primer paquete  -->
	                            <div class="col">
	                              <div class="card_oferta mx-auto card">
	                                <div class="container row g-0">
	                                  <div class="col-md-12">
	                                    <img class="w-100" src="/trabajouy/imagenes?id=data/<%=paq.getImagen() %>"  class="img-fluid rounded-start" alt="...">
	                                  </div>
	                                  <div class="col-md-12">
	                                    <div class="card-body">
	                                      <h5 class="card-title">Paquete <%=paq.getNombre()%></h5>
	                                      <p class="card-text"><%=paq.getDescripcion() %></p>
	                                      <a class="btn btn-primary btn-sm" href="/trabajouy/consultarPaquete?dato=<%=paq.getNombre()%>">Leer más</a>
	                                      <%
	                                      gregC = comp.getFechaCompra().toGregorianCalendar();
							             date = gregC.getTime();
							             fechaCompra = dateFormat.format(date);    
	                                      %>
	                                      <p class="card-text"><small class="text-muted">Fecha de compra: <%=fechaCompra %></small></p>
	                                      <p class="card-text"><small class="text-muted">Periodo <%=paq.getValidez() %> días</small></p>
	                                    </div>
	                                  </div>
	                                </div>
	                              </div>
	                            </div>
	                            
	                            <%
	                            }
	                            %>
	                            
	                          </div>
	                        </div>
	                      </div>
	                      <%
	                      } // else: si hay paquetes
	                      %>
	                      
	                    </div> <!-- fin container cards paquetes -->
	                  </div>
	              </div>
	            </div> <!-- fin pg de paquetes -->
	            
	            <%
	            }
	            %>
	            

	      </div> <!--  fin estructura de paginas -->
	      
	    </div> <!-- agarra todo empresa -->
	   </div> <!--  agarra todo empresa -->
		
	<%
	} // fin empresa
	
} // fin u != null
	%>
	
	<jsp:include page="/WEB-INF/template/footer.jsp" />
</body>
</html>