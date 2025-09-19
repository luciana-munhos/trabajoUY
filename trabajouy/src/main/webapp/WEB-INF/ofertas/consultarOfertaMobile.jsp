<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="servidor.DtOferta" %>
<%@page import="servidor.DtUsuario" %>
<%@page import="servidor.DtPostulante" %>
<%@page import="java.util.Set" %>
<%@page import="java.util.List" %>
<%@page import="servidor.DtPostulacion" %>
<%@page import="servidor.DtPaquete" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.GregorianCalendar" %>
<%@page import="javax.xml.datatype.XMLGregorianCalendar" %>


<html>
<head>
<meta charset="UTF-8">
<title>Consultar oferta</title>
<jsp:include page="/WEB-INF/template/head.jsp" />
</head>
<body>
	<%
	DtOferta oferta = (DtOferta) request.getAttribute("oferta_a_consultar"); 
	DtUsuario usr = (DtUsuario) session.getAttribute("usuario_logueado");
	//boolean es_duenio_oferta =false;
    boolean es_duenio_oferta = (boolean) request.getAttribute("duenio_oferta");
    DtPostulacion pos = (DtPostulacion) request.getAttribute("postulacion");
    boolean ya_postulo = pos != null;
	%>
	<jsp:include page="/WEB-INF/template/headerMobile.jsp" />
	<nav class="ms-3 mt-5" aria-label="breadcrumb">
        <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="/trabajouy/home">Home</a></li>
        <li class="breadcrumb-item"><a href="/trabajouy/listarOfertas">Ofertas</a></li>
        <li class="breadcrumb-item active" aria-current="page"><%=oferta.getNombre()%></li>
        </ol>
    </nav>
    
    <div class="container-fluid px-4  mb-2 mt-3">
        <div class="row">
            <div class="col text-center border-warning border-start border-end border-5">
                <h1>CONSULTA DE OFERTA</h1>
                <h4><%=oferta.getNombre() %></h4>
                <p><%=oferta.getDescripcion() %></p>
            </div>
        </div>
    </div>
    
    <div class="container-fluid mx-md-0 mx-auto">
        <div class="row ml-0 p-0">
            <div class="col-12 col-md-7 p-0"  id="img-oferta">
                <img class="w-100" src="/trabajouy/imagenes?id=data/<%=oferta.getImagen() %>" alt="">
            </div>
            <div class="col-12 mx-auto col-md-5 m-0 ">
                <div class="container mt-md-0 mt-4 text-center text-md-end">
                    <div class="row border-bottom">
                        <h4 class="subtitulo-box">Detalles de la oferta</h4>
                        <%
                        if(es_duenio_oferta){
                        %>
                        <p class="text-muted">Estado: <span id="estado-oferta"><%=oferta.getEstado()%></span></p>
                        <%
                        }
                        %>
                        <p class="text-muted">Publicacion <%=oferta.getTipoPublicacion() %></p>
                    </div>
                    <div class="row mt-2">
                        <div class="container">
                            <div class="row border-bottom py-2">
                                <div class="col">
                                    <h6>Horario:</h6>
                                </div>
                                <div class="col">
                                    <p><%=oferta.getHorario() %></p>
                                </div>
                            </div>
                            <div class="row border-bottom py-2">
                                <div class="col">
                                    <h6>Remuneracion:</h6>
                                </div>
                                <div class="col">
                                    <p>$<%=oferta.getRemuneracion() %></p>
                                </div>
                            </div>
                            <div class="row border-bottom py-2">
                                <div class="col">
                                    <h6>Ciudad:</h6>
                                </div>
                                <div class="col">
                                    <p><%=oferta.getCiudad()%></p>
                                </div>
                            </div>
                            <div class="row border-bottom py-2">
                                <div class="col">
                                    <h6>Departamento:</h6>
                                </div>
                                <div class="col">
                                    <p><%=oferta.getDepartamento()%></p>
                                </div>
                            </div>
                        </div>                    
                    </div>
                    <div class="row">
                        <div class="box-k-tp accordion accordion-flush" id="acordion-keywords">
                            <div class="accordion-item">
                                <h2 class="accordion-header">
                                    <button class="fw-bold accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseOne" aria-expanded="false" aria-controls="flush-collapseOne">
                                    Keywords
                                    </button>
                                </h2>
                                <div id="flush-collapseOne" class="accordion-collapse collapse" data-bs-parent="#acordion-keywords">
                                    <div class="container accordion-body">
                                        <div class="row">
                                            <div class="col d-flex justify-content-end">
                                                <%
                                                List<String> keywords = oferta.getKeywords();
                                                if(keywords.size()==0){
                                                	%>
                                                	<p class="text-muted text-center">No hay ninguna keyword asociada :(</p>
                                                	<%
                                                }
                                                else{
                                                %>
                                                <p class=""> 
                                                	<%
                                                	for(String k: keywords){
                                                	%>
                                                    <a href=""><span class="m-1 btn btn-info"><%=k%></span></a>
                                                    <%
                                                    }
                                                	%>
                                                </p>
                                                <%
                                                }
                                                %>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	GregorianCalendar gregC;
    	Date date;
    	String fechaFormateada;
        gregC = oferta.getFechaAlta().toGregorianCalendar();
        date = gregC.getTime();
        fechaFormateada = dateFormat.format(date);
        %>
        
        <div class="row">
            <p class="text-end text-muted">Dada de alta el <%=fechaFormateada %> por <a href="/trabajouy/consultarUsuario?nickname=<%=oferta.getNickEmpresa()%>"><%=oferta.getNickEmpresa()%></a></p>
        </div>
        
        <%
        if(!es_duenio_oferta){
            if(ya_postulo){
            %>
            <div class="container-fluid m-0">
		        <div class="row">
		            <div class="col d-flex justify-content-end">
                		<button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#modal-info-postulacion">Ver postulación</button>
            		</div>
		        </div>
		    </div>
		    
		        <div class="modal fade" id="modal-info-postulacion" tabindex="-1" aria-labelledby="modal-info-postulacion" aria-hidden="true">
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
			                            <p><%=pos.getCVR()%></p>
			                        </div>
			                        <div class="row">
			                            <h5>Motivación</h5>
			                            <p><%=pos.getMotivacion()%></p>
			                        </div>
			                        <% if(!(pos.getVideo() == null) && !pos.getVideo().equals("")) {%>
			                        <div class="row">
			                        	<h5>Video de presentación</h5>
			                        	<div class="text-center">
			                        	<div class="video-container">
			                        		<iframe width="400" height="215" src="https://www.youtube.com/embed/<%= pos.getVideo() %>" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
			                        	</div>
			                        	</div>
			                        </div>
			                        <%
			                        }
			                        // defino formato deseado de fecha
		                              gregC = pos.getFecha().toGregorianCalendar();
		                              date = gregC.getTime();
		                              fechaFormateada = dateFormat.format(date);
			                        
			                        %>
			                        <div class="row">
			                            <p class="text-muted">Fecha de postulación: <%=fechaFormateada%></p>
			                        </div>
			                    </div>
			                </div>
			                <div class="modal-footer">
			                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
			                </div>
			            </div>
			        </div>
			    </div>
            <%
            }else if(usr == null){ //es visitante
            %>
             <div class="container-fluid m-0">
		        <div class="row">
		            <div class="col d-flex justify-content-end">
                		<a class="fs-3 btn btn-primary" href="/trabajouy/formReg">Postularse ahora</a>
            
		            </div>
		        </div>
		    </div>
            <%
            }else if(usr instanceof DtPostulante){ //es postulante y no se postulo
            %>
             <div class="container-fluid m-0">
		        <div class="row">
		            <div class="col d-flex justify-content-end">
		            	<button class="btn-postularse btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalPostulacion">Postularse</button>            
		            </div>
		        </div>
		    </div>
		    <div class="modal fade" id="modalPostulacion" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
		        <div class="modal-dialog">
		            <div class="modal-content">
		            <div class="modal-header">
		                <h1 class="modal-title fs-5" id="staticBackdropLabel">Postularse a oferta laboral</h1>
		                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		            </div>
		            <div class="modal-body">
		                <form action="postularse" method="POST" id="postulars">
		                	<input type="hidden" name="oferta" value="<%=oferta.getNombre() %>">
		                    <div class="form-group">
		                        <label for="textarea_cv">CV Reducido</label>
		                        <textarea required class="form-control" name="cv_abreviado" id="textarea_cv" cols="30" rows="5"></textarea>
		                    </div>
		                    <div class="form-group">
		                        <label for="textarea_mot">Motivación</label>
		                        <textarea required class="form-control" name="motivacion" id="textarea_mot" cols="30" rows="5"></textarea>
		                    </div>
		                    <div class="form-group">
		                        <label for="input_vid">Video de presentación</label>
		                        <input class="form-control" type="url" id="input_vid" name="video">
		                    </div>
		                    <div class="text-center mt-2">
		                    	<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
		               			<button type="submit" class="btn btn-primary" style="background-color: #d87093;">Postularse</button>
		                    </div>
		                </form>
		            </div>
		            <div class="modal-footer">
		                
		            </div>
		            </div>
		        </div>
		    </div>
            <%
            }
        }else{//es el dueño
        %>
        	<div class="container-fluid mt-3 border-top pt-4">
		        <div class="row mx-3 tex">
		            <div class="col subtitulo-box text-center">
		                <h3>Postulaciones y paquetes</h3>
		            </div>
		        </div>
		        <div class="row">
		            <ul class="nav nav-tabs d-flex justify-content-center">
		                <li class="nav-item">
		                    <a class="nav-link active" data-bs-toggle="tab" href="#of-postulaciones">Postulaciones</a>
		                </li>
		                <li class="nav-item">
		                    <a class="nav-link" data-bs-toggle="tab" href="#of-paquetes">Paquetes</a>
		                </li>
		            </ul>
		            
		            <div class="tab-content">
		                <div class="tab-pane container active" id="of-postulaciones">
		                    <div class="text-center mt-md-0 ps-md-0 px-md-3 pt-4 ">
		                        <div class="row">
		                            <div class="col container-fluid">
		                                <div class="row d-flex justify-content-center">
		                                    <div class="col">
		                                        
		                                        <% 
		                                        List<DtUsuario> postulados = (List<DtUsuario>) request.getAttribute("postulados");
		                                        if(postulados.size()==0){
		                                        %>
		                                        <h4 class="text-center text-muted">Aún no hay postulaciones</h4>
		                                        <%
		                                        }else{
		                                        	%>
		                                        	<ul class="list-group text-start">
		                                        	<%
			                                       	int j = 1;
			                                        for(DtUsuario usuario : postulados){
			                                        %>
		                                            <li class="list-group-item">
		                                                <div class="container">
		                                                    <div class="row">
		                                                        <div class="img-padr col-3 col-sm-2 col-md-2 col-lg-1">
		                                                            <img class="img-fluid h-100" src="/trabajouy/imagenes?id=data/<%=usuario.getImagen()%>" alt="">
		                                                        </div>
		                                                        <div class="col my-auto">
		                                                            <a href="/trabajouy/consultarUsuario?nickname=<%=usuario.getNickname()%>">
		                                                                <%=usuario.getNombre()%> <%=usuario.getApellido() %>
		                                                            </a>
		                                                        </div>
		                                                        <div class="col my-auto d-flex justify-content-end">
		                                                            <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modal-pos-u<%=j%>">Ver detalles</button>
		                                                        </div>
		                                                    </div>
		                                                </div>
		                                            </li>
		                                            <%
		                                            j++;
		                                            }
			                                        %>
			                                        </ul>
			                                        <%
		                                        }
		                                        %>
		                                    </div>
		                                </div>
		                            </div>
		                        </div>
		                    </div>
		                </div>
		                <div class="tab-pane container fade" id="of-paquetes">
		                    <div class="row pt-4">
		                        <div class="container">
		                            <div class="row d-flex justify-content-center">
		                                <div class="col-10">
		                                    <ul class="list-group text-start">
		                                        <li class="list-group-item">
		                                            <div class="container">
		                                                <div class="row">
		                                                <%
			                                            DtPaquete paq = oferta.getDTPaqueteAsociado();
			                                            if(paq!=null){
			                                            %>
		                                                    <div class="img-padr col-3 col-sm-2 col-md-2 col-lg-1">
		                                                        <img class="img-fluid" src="/trabajouy/imagenes?id=data/<%=paq.getImagen() %>" alt="">
		                                                    </div>
		                                                    <div class="col my-auto">
		                                                        <a href="/trabajouy/consultarPaquete?dato=<%=paq.getNombre()%>">
		                                                            <%=paq.getNombre()%>
		                                                        </a>
		                                                    </div>
	                                                    <%
	                                                    }else{%>
	                                                    	<p class= "text-muted text-center">No se utilizó ningún paquete.</p>
	                                                    <%	
	                                                    }
	                                                    %>
		                                                </div>
		                                            </div>
		                                        </li>
		                                    </ul>
		                                </div>
		                            </div>
		                        </div>
		                    </div>
		                </div>
		                </div>
		            </div>
		                
		            <div class="col-md-7 ps-md-5 ps-0">
		                
		            </div>
		        
        <%
        int i = 1;
        List<DtPostulacion> postulaciones = (List<DtPostulacion>) request.getAttribute("postulaciones");
        for(DtPostulacion postulacion : postulaciones){
    	%>
    	    <div class="modal fade" id="modal-pos-u<%=i %>" tabindex="-1" aria-hidden="true">
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
		                            <p><%=postulacion.getCVR()%> </p>
		                        </div>
		                        <div class="row">
		                            <h5>Motivación</h5>
		                            <p><%=postulacion.getMotivacion()%> </p>
		                        </div>
		                        <% if(!(pos.getVideo() == null) && !pos.getVideo().equals("")) {%>
		                        <div class="row">
		                        	<h5>Video de presentación</h5>
		                        	<div class="text-center">
		                        	<div class="video-container">
		                        		<iframe width="400" height="215" src="https://www.youtube.com/embed/<%= postulacion.getVideo() %>" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
		                        	</div>
		                        	</div>
		                        </div>
		                        <%
		                        }
		                        // defino formato deseado de fecha
	                              gregC = pos.getFecha().toGregorianCalendar();
	                              date = gregC.getTime();
	                              fechaFormateada = dateFormat.format(date);
		                        %>
		                        <div class="row">
		                            <p class="text-muted">Fecha de alta: <%=fechaFormateada%></p>
		                        </div>
		                    </div>
		                </div>
		                <div class="modal-footer">
		                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
		                </div>
		            </div>
		        </div>
		    </div>
    	<%
    	i++;
    	}
        }
        %>
        </div>
	</div>
	<jsp:include page="/WEB-INF/template/footer.jsp" />
	<script src="media/scripts/video-youtube.js"></script>
</body>
</html>