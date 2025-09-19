<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
<%@page import="servidor.DtOferta" %>
<%@page import="servidor.DtPostulacion" %>
<%@page import="servidor.DtPostulante" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.GregorianCalendar" %>
<%@page import="javax.xml.datatype.XMLGregorianCalendar" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Map" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Realizar Ranking</title>
<jsp:include page="/WEB-INF/template/head.jsp" />
<link rel="stylesheet" href="media/styles/bootstrap-icons-1.11.1/bootstrap-icons.css">


</head>
<body>
	<%
	DtOferta oferta = (DtOferta) session.getAttribute("oferta"); 
	Map<DtPostulante,DtPostulacion> postulaciones = (Map<DtPostulante,DtPostulacion>) session.getAttribute("postulantes"); 
	// para fechas
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	GregorianCalendar gregC;
	Date date;
	String fechaFormateada;
	%>
	<jsp:include page="/WEB-INF/template/header.jsp" />
	<div class="container">
		<div class="row">
			<div class="col">
				<h1>Seleccionar postulantes</h1>
				<h4>Oferta: <a target="_blank" href="/trabajouy/consultarOferta?nombre=<%=oferta.getNombre()%>"><%=oferta.getNombre()%></a></h4>
			</div>
		</div>
		<br>
		<div class="alert alert-info" role="alert">
  			¡Asegurate de asignarle una posicion en el ranking a cada postulante!
		</div>

		<div class="row mt-5">
    <div class="col">
        <form action="ranking" method="POST" id="formulario">            
            <table class="table table-hover table-striped">
                <thead>
                    <tr>
                        <th scope="col">Posición</th>
                        <th scope="col">Postulante</th>
                        <th scope="col">Ver Detalles</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    int cantPostulantes = postulaciones.keySet().size();
                    for (Map.Entry<DtPostulante, DtPostulacion> entry : postulaciones.entrySet()) {
                        DtPostulante postulante = entry.getKey();
                        DtPostulacion postulacion = entry.getValue();
                    %>
                    <tr data-postulante-id="<%=postulante.getNickname()%>">
                        <th scope="row">
                                <select required name="pos_<%=postulante.getNickname()%>" class="form-select" onchange="handleSelectChange('<%=postulante.getNickname()%>', this.value)">                                
                                <option value=""> </option>
                                <%
                                for (int i = 1; i <= cantPostulantes; i++) {
                                %>
                                <option value="<%=i%>"><%=i %></option>
                                <%
                                }
                                %>
                            </select>
                        </th>
                        <td>
                            <div class="row">
                                <div class="img-padr col-3 col-sm-2 col-md-2 col-lg-1">
                                    <img class="img-fluid h-100" src="/trabajouy/imagenes?id=data/<%=postulante.getImagen()%>" alt="">
                                </div>
                                <div class="col my-auto">
                                    <a target="_blank" href="/trabajouy/consultarUsuario?nickname=<%=postulante.getNickname()%>"><%=postulante.getNombre() %>
                                        <%=postulante.getApellido() %></a>
                                </div>
                            </div>
                        </td>
                        <td><a type="button" data-bs-toggle="modal" data-bs-target="#modal<%=postulante.getNickname()%>"><i class="bi bi-eye"></i></a></td>
                    </tr>
                    <%
                    }
                    %>
                </tbody>
            </table>
        </form>
    </div>
</div>

		<div class="row">
			<div id="guardarButton" class="col text-center">
				<button type="submit" class="btn btn-primary" id="guardarButton" onclick="return checkGuardarButton();">Guardar</button>
			</div>
		</div>
		
	</div>
	<%
	for (Map.Entry<DtPostulante, DtPostulacion> entry : postulaciones.entrySet()) {
		  DtPostulante postulante = entry.getKey();
		  DtPostulacion postulacion = entry.getValue();
	%>
		<!-- Modal -->
		<div class="modal fade" id="modal<%=postulante.getNickname()%>" tabindex="-1" aria-hidden="true">
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
                        <%
                        	String video = postulacion.getVideo();
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
                              gregC = postulacion.getFecha().toGregorianCalendar();
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
		</div>
	<%
	}
	%>
	<jsp:include page="/WEB-INF/template/footer.jsp" />
	
	
	<script>
    var selectedPositions = {};

    function handleSelectChange(postulanteId, selectedValue) {
        disableOptions(postulanteId, selectedValue);
        reorderRows();
    }

    function disableOptions(postulanteId, selectedValue) {
        for (var postulanteIdFromName in selectedPositions) {
            if (selectedPositions[postulanteIdFromName] === selectedValue) {
                selectedPositions[postulanteIdFromName] = undefined;
                var otherSelectElement = document.querySelector('select[name="pos_' + postulanteIdFromName + '"]');
                otherSelectElement.value = "";
            }
        }
        selectedPositions[postulanteId] = selectedValue;
    }

    function reorderRows() {
        var table = document.querySelector('table');
        var rows = Array.from(table.querySelectorAll('tr[data-postulante-id]'));

        rows.sort(function (a, b) {
            var idA = a.getAttribute('data-postulante-id');
            var idB = b.getAttribute('data-postulante-id');
            var positionA = selectedPositions[idA] || Number.MAX_VALUE;
            var positionB = selectedPositions[idB] || Number.MAX_VALUE;
            return positionA - positionB;
        });

        var tbody = table.querySelector('tbody');
        tbody.innerHTML = '';

        rows.forEach(function (row) {
            tbody.appendChild(row);
        });
    }

    function checkGuardarButton() {
        var selectElements = document.querySelectorAll('select[name^="pos_"]');
        var isAllSelected = true;

        selectElements.forEach(function (selectElement) {
            if (selectElement.value === "") {
                isAllSelected = false;
            }
        });

        if (!isAllSelected) {
            return false;
        }

        document.getElementById("formulario").submit();
        return true;
    }
</script>



	
	
</body>
</html>