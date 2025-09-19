const tabla = document.getElementById('miTabla');
const filas = tabla.tBodies[0].rows;
const paginacion = document.getElementById('paginacion');
let paginaActual = 1;
const filasPorPagina = 6;



function paginar(direccion) {
    const nuevaPagina = paginaActual + direccion;
    if (nuevaPagina >= 1 && nuevaPagina <= Math.ceil(filas.length / filasPorPagina)) {
        buscarNombre2(nuevaPagina);
    }
}

buscarNombre2(paginaActual);

function buscarNombre(){
    buscarNombre2(1);
}
// Función para realizar la búsqueda
function buscarNombre2(pagina) {
   
    var input = document.getElementById('busqueda');
    var filtro = input.value.toUpperCase();
    var tabla = document.getElementById('miTabla');
    var filas = tabla.getElementsByTagName('tr');
    var contador = 0;
    var seguidor =0;
 
    for (var i = 0; i < filas.length; i++) {

        var celdaNombre = filas[i].getElementsByTagName('td')[1];
        if (celdaNombre) {
            var textoCelda = celdaNombre.textContent || celdaNombre.innerText;
            if ((textoCelda.toUpperCase().indexOf(filtro) > -1) && (contador < filasPorPagina*pagina) && contador >= filasPorPagina*(pagina-1) ) {
                contador++;
                filas[i].style.display = '';
                
                
            } else {
                if(textoCelda.toUpperCase().indexOf(filtro) > -1){
                    contador++;
                }
                filas[i].style.display = 'none';
            }
           
        }
    } 
        paginaActual = pagina;
        document.getElementById('paginaActual').textContent = paginaActual;
        document.getElementById('anterior').disabled = paginaActual === 1;
        document.getElementById('siguiente').disabled = contador <= filasPorPagina*pagina;  
}

// Asociar la función de búsqueda al evento 'input'
document.getElementById('busqueda').addEventListener('input', buscarNombre);


