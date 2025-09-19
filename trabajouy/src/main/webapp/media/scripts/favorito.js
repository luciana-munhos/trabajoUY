var estrellas = document.getElementsByClassName("estrella");

for (let j = 0; j < estrellas.length; j++) {
    estrellas[j].addEventListener("click", function() {
		var estrella = estrellas[j];
        var id = this.id;
        var nombreOferta = id.split(":")[1];
        var marcar = "fav";
        var cant = document.getElementById("cant:" + nombreOferta);
        //cant is (int). get that value and add 1 or -1. remove parentesis
        var cantInt = parseInt(cant.innerHTML.substring(1, cant.innerHTML.length - 1));
        if (estrella.classList.contains("bi-star-fill")) {
            estrella.classList.remove("bi-star-fill");
            estrella.classList.add("bi-star");
            //change title to "Marcar como favorito"
            estrella.title = "Marcar como favorito";
            marcar = "nofav";
            cant.innerHTML = "(" + (cantInt - 1) + ")";
        } else {
            estrella.classList.remove("bi-star");
            estrella.classList.add("bi-star-fill");
            estrella.title = "Quitar de favoritos";
            cant.innerHTML = "(" + (cantInt + 1) + ")";
        }   
        //call the servlet to update the database
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "favorito", true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        //change _ to white space in nombreOferta
        nombreOferta = nombreOferta.replace(/_/g, " ");
        console.log(nombreOferta)
        xhr.send("nombreOferta=" + nombreOferta + "&marcar=" + marcar);

    });
}