document.addEventListener("DOMContentLoaded", function () {
    var seleccionado = document.getElementById("elegir-usr");
    var form = document.getElementById("formulario");
    var contra = document.getElementById("con");
    var contra2 = document.getElementById("con2");
	actualizarCampos();
    contra2.addEventListener("input", function() {
        if (contra.value === contra2.value) {
            //contra.classList.remove("is-invalid");
            contra2.classList.remove("is-invalid");
            //contra.classList.add("is-valid");
            contra2.classList.add("is-valid");
            document.getElementById("passNO").style.display = "none";
        } else {
           // contra.classList.remove("is-valid");
            contra2.classList.remove("is-valid");
            //contra.classList.add("is-invalid");
            contra2.classList.add("is-invalid");
        }
    });

	function actualizarCampos() {
    var opcionSeleccionada = seleccionado.value;
    var agregarP = document.getElementById("postulanteSelected");
    var agregarE = document.getElementById("empresaSelected");
    
    if (opcionSeleccionada == "1") {
        agregarE.style.display = "flex";
        agregarP.style.display = "none";
    }else if(opcionSeleccionada == "2") {
        agregarP.style.display = "flex";
        agregarE.style.display = "none";
    }
}

	
    seleccionado.addEventListener("change",function(){
        actualizarCampos();
    })

function validarFormulario() {
        var contra = document.getElementById("con").value;
        var contra2 = document.getElementById("con2").value;
        if (contra != contra2) {
            document.getElementById("passNO").style.display = "block";
            return false; // Devuelve false para prevenir el envío del formulario
        }
        document.getElementById("passNO").style.display = "none";
        return true;
    }

function completoCamposVisibles(){
	var opcionSeleccionada = seleccionado.value;
    if(opcionSeleccionada == "1"){
        //chequeo descripcion
        var d = document.getElementById("des");
        if(d.value.length < 10){
			document.getElementById("desError").style.display = "block";
			d.focus();
			return false;
		}
		else{
			document.getElementById("desError").style.display = "none";
		}
    }else{
        //chequeo pais y fecha
        var p = document.getElementById("pais");
        var f = document.getElementById("fecha");
        if(p.value.length < 2){
			document.getElementById("paisError").style.display = "block";
			p.focus();
			return false;
		}else{
			document.getElementById("paisError").style.display = "none";
		}
		if(f.value==""  || !validarFecha()){
			document.getElementById("fechaError").style.display = "block";
			f.focus();
			return false;
		}else{
			document.getElementById("fechaError").style.display = "none";
		}
    }
    return true;
}

function validarFecha(){
	var inputFecha = document.getElementById("fecha");
    var fechaSeleccionada = new Date(inputFecha.value);
    var fechaActual = new Date();
    var fechaMinima = new Date('1900-01-01');
    if (fechaSeleccionada > fechaActual || fechaSeleccionada < fechaMinima) {
        inputFecha.value =""; // Limpia el campo si la fecha no es válida
        return false;
    }
	return true;
}

    form.addEventListener("submit",function(event){
        if(!validarFormulario() || !completoCamposVisibles()){
            event.preventDefault();       
    	}
    })

});





