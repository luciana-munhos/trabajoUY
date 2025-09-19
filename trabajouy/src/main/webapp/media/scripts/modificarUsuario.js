var originalFormValues = {};

// Llena originalFormValues con los valores iniciales al cargar la página
document.addEventListener('DOMContentLoaded', function () {
  var formElements = document.querySelectorAll('.needs-validation input, .needs-validation textarea');
  formElements.forEach(function (element) {
    originalFormValues[element.name] = element.value;
  });
});


function validarFecha() {
    var inputFecha = document.getElementById('fecha');
    var fechaSeleccionada = new Date(inputFecha.value);
    var fechaActual = new Date();

    var fechaMinima = new Date('1900-01-01');
    if (fechaSeleccionada > fechaActual || fechaSeleccionada < fechaMinima ) {
        inputFecha.value = ''; // Limpia el campo si la fecha no es válida
        
    }
}



// Example starter JavaScript for disabling form submissions if there are invalid fields
(function () {
  'use strict'

  // Fetch all the forms we want to apply custom Bootstrap validation styles to
  var forms = document.querySelectorAll('.needs-validation')

  // Loop over them and prevent submission
  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        } else {
          var formElements = form.querySelectorAll('input, textarea');
          var isFormModified = false;

          formElements.forEach(function (element) {
            if (originalFormValues[element.name] !== element.value) {
              isFormModified = true;
            }
          });

          if (!isFormModified) {
            	event.preventDefault();
          // Mostrar el mensaje de error en el div con la clase "invalid-feedback"
	         	var errorElement = form.querySelector('#nombre-error'); // Cambia "nombre-error" por el id correspondiente
	          	if (errorElement) {
	            errorElement.style.display = 'block';
	          }
        
          }
        

			
			
			
		}

        form.classList.add('was-validated')
      }, false)
    })
})()