<%@ page language="java" contentType="text/html" 
	pageEncoding="UTF-8"%>    
<!DOCTYPE html>
<%@page import="servidor.DtPaquete" %>
<%@page import="java.util.List" %>


<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="media/styles/bootstrap-5.3.1-dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="media/styles/listarPaquete.css">

    <link rel="stylesheet" href="media/styles/home.css">
    <link rel="stylesheet" href="media/styles/general.css">
<title>Listar Paquetes</title>
<jsp:include page="/WEB-INF/template/head.jsp" />
</head>
<body>
	<jsp:include page="/WEB-INF/template/header.jsp" />
		
	<div class="container">
		<div class="row">
			<div class="col text-center border-warning border-start border-end border-5">
				<h1>Paquetes de Tipos de Publicación de Ofertas Laborales</h1>
			</div>
		</div>
	</div>	
		
    <div class="container mt-5" id="listaPaquetes">
    	
    	<%
    	List<DtPaquete> paqs = (List<DtPaquete>) request.getAttribute("paquetes");
		if(paqs!=null){
    	%>
    	<div class="row">
    		<div class="col">
		        <table class="table w-auto mx-auto" id="miTabla">
		            <thead>
		                <tr class="table-primary text-center">                       
		                    <th scope="col">Imagen</th>
		                    <th scope="col" class="w-75">
			                    <div class="d-flex justify-content-between col-auto">
			                    	<p class="my-auto">Nombre</p>
			                    	<input class="w-75 form-control form-control-sm" type="text" id="busqueda" placeholder="Buscar por nombre">
			                    </div>
		                     </th>
		                    <th scope="col">Costo</th>
		                    <th scope="col">InfoCompleta</th>
		                </tr>
		            </thead>
		            <tbody>
			            <% 
							for(DtPaquete p: paqs){
						%>
			                <tr>                
			                    <td class="img"><img src="/trabajouy/imagenes?id=data/<%=p.getImagen() %>" alt="Mi imagen" class="imagenPequenia">
			                    <td class="nombre"> <%=p.getNombre() %></td>
			                    <td class="costo"><%=p.getCosto() %> </td>
			                    <td>
			                    	<a class="btn btn-primary btn-sm" href="/trabajouy/consultarPaquete?dato=<%=p.getNombre()%>&ubic=listarPaquetes">Ver detalles</a>                   
								</td>
			                </tr>
		                <% 
		                	} 
		
		                %> 
		                  
		            </tbody>
		        </table>
		     </div>
        </div>
        
        <div class="row" id="paginacion">
	        <div class="col">
	        	<button class="btn btn-primary btn-sm" id="anterior" onclick="paginar(-1)">Anterior</button>
	            <span id="paginaActual">1</span>
	            <button class="btn btn-primary btn-sm" id="siguiente" onclick="paginar(1)">Siguiente</button>
	        </div>
            
        </div>
        <%
		}else{
		%>
			<div class="row">
				<div class="col">
					<h3 class="text-center text-muted">Aún no contamos con paquetes :(</h3>
				</div>
			</div>
		<%
		}
        %>
       
    </div>




	<jsp:include page="/WEB-INF/template/footer.jsp" />
	<script src="media/scripts/listarPaquete.js"></script>
	
</body>
</html>