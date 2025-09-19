document.addEventListener("DOMContentLoaded", function() {
    var boton = document.getElementById("seguirUsuario");

    boton.addEventListener("click", function() {
        // Obtener la URL actual incluyendo la cadena de consulta
        var url = window.location.href;
        // Obtener la cadena de consulta de la URL
        var queryString = url.split("?")[1];
        
        if (queryString) {
            // Parsear la cadena de consulta en un objeto URLSearchParams
            var params = new URLSearchParams(queryString);
            // Obtener el valor del parámetro "nickname"
            var nickname = params.get("nickname");

            // Redirigir a la URL del servlet deseado y pasar el nickname como parámetro
            window.location.href = "/trabajouy/seguimiento?dejar=0&nickname=" + nickname;
        }
    });
});

document.addEventListener("DOMContentLoaded", function() {
    var boton = document.getElementById("dejarDeSeguirUsuario");

    boton.addEventListener("click", function() {
        // Obtener la URL actual incluyendo la cadena de consulta
        var url = window.location.href;
        // Obtener la cadena de consulta de la URL
        var queryString = url.split("?")[1];
        
        if (queryString) {
            // Parsear la cadena de consulta en un objeto URLSearchParams
            var params = new URLSearchParams(queryString);
            // Obtener el valor del parámetro "nickname"
            var nickname = params.get("nickname");

            // Redirigir a la URL del servlet deseado y pasar el nickname como parámetro
            window.location.href = "/trabajouy/seguimiento?dejar=1&nickname=" + nickname;
        }
    });
});