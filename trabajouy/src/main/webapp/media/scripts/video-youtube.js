var form = document.getElementById("postulars");

function esURLdeYouTube(url) {
    // Expresión regular para verificar URLs de YouTube
    var youtubeRegExp = /^(https?:\/\/)?(www\.)?youtube\.com\/watch\?v=[\w-]+(&\S*)?|^((http|https):\/\/)?(www\.)?youtu\.be\/[\w-]+(&\S*)?$/;

    // Verifica si la URL coincide con el patrón de YouTube
    return youtubeRegExp.test(url);
}

form.addEventListener("submit", function (event) {
    var url = document.getElementById("input_vid").value;
    if (url.length > 0 && !esURLdeYouTube(url)) {
        event.preventDefault();
        alert("La URL proporcionada no es un enlace válido de YouTube.");
    }
});