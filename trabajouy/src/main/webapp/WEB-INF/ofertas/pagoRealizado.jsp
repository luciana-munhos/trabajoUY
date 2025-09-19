<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pago confirmado</title>
    <jsp:include page="/WEB-INF/template/head.jsp" />
   
</head>
<body>
    <jsp:include page="/WEB-INF/template/header.jsp" />

    <div class="container vertical-center">
        <div class="card text-center">
            <div class="card-body">
                <h2>Su oferta ha sido enviada con éxito a nuestros moderadores.</h2>
       			 <h4 class="text-muted">En la brevedad será publicada en nuestros catálogos.</h4>
       			 <img class="w-25" src="media/img/pulgarArribaAzul.jpeg" alt="Pulgar Arriba">       			 
       			 <h3 class="text-muted"> ¡Gracias por su preferencia! </h3>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/template/footer.jsp" />

</body>
</html>
