function mostrarOcultarLi() {
    var seleccion = document.getElementById("ordenarPor").value;
    var liRanking = document.getElementById("liRanking");
    var liSinRanking = document.getElementById("liSinRanking");
    var liVigente = document.getElementById("liVigente");
    var liNoVigente = document.getElementById("liNoVigente");
    var vigente = document.getElementById("vigente");
    var ranking = document.getElementById("ranking");
		
	
    if (seleccion === "ranking") {
        liRanking.style.display = "block";
        liSinRanking.style.display = "block";
        liVigente.style.display = "none";
        liNoVigente.style.display = "none";
        ranking.click();
    } else if (seleccion === "vigencia") {
        liRanking.style.display = "none";
        liSinRanking.style.display = "none";
        liVigente.style.display = "block";
        liNoVigente.style.display = "block";
        vigente.click();
    }
}

// Llamar a la función al cargar la página para mostrar la opción predeterminada.
mostrarOcultarLi();


