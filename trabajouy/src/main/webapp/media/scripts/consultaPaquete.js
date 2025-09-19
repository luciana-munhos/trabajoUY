// script.js
function abrirModal() {
    var modal = document.getElementById("miModal");
    modal.style.display = "block";
}

function redirigir(variable) {   
	var dato1 = "comprar";

	var url = "consultarPaquete?compra=" + encodeURIComponent(dato1) + "&paquete=" + encodeURIComponent(variable);

    window.location.href = url;

    var myModalEl = document.getElementById('myModal')
    var modal = bootstrap.Modal.getInstance(myModalEl) // Returns a Bootstrap modal instance    modal.style.display = "none";
    modal.style.display = "block";

    // Redirigir a la URL deseada
}
