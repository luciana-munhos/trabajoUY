var todas_cards = Array.from(document.getElementsByClassName("card_oferta"));
var container_cards = document.getElementById("row_ofertas");
var msj = document.getElementById("mensaje_no_ofertas");
var keywords = document.getElementsByClassName("key");

var keywords_selected = {};

var btn_and = document.getElementById("btn_and");
var btn_or = document.getElementById("btn_or");

var and = false;

btn_and.addEventListener("click ", function() {
    and = true;
    btn_and.classList.remove("btn-outline-dark");
    btn_and.classList.add("btn-dark");
    btn_or.classList.remove("btn-dark");
    btn_or.classList.add("btn-outline-dark");
    filtrarTarjetas();
});

btn_or.addEventListener("click", function() {
    and = false;
    btn_and.classList.remove("btn-dark");
    btn_and.classList.add("btn-outline-dark");
    btn_or.classList.remove("btn-outline-dark");
    btn_or.classList.add("btn-dark");

    filtrarTarjetas();
});

function filtrarTarjetas(){
    var newCards = [];
        
    for (let j = 0; j < todas_cards.length; j++) {
        var keywordsActual = todas_cards[j].getAttribute('data-keywords').split(' ');
    
        let ponerCarta = false;
    
        // Si no hay ninguna keyword seleccionada, no mostrar la tarjeta
        if (Object.keys(keywords_selected).every(keyword => !keywords_selected[keyword])) {
            ponerCarta = false;
        } else {
            let tieneUnaKeyword = false;
            let tieneTodasKeywords = true;
    
            for (let keyword of Object.keys(keywords_selected)) {
                if (keywords_selected[keyword]) {
                    if (and && !keywordsActual.includes(keyword)) {
                        tieneTodasKeywords = false;
                        break;
                    } else if (!and && keywordsActual.includes(keyword)) {
                        tieneUnaKeyword = true;
                        break;
                    }
                }
            }
    
            // Si no tiene ninguna keyword pero hay al menos una seleccionada, no mostrar la tarjeta
            if (!tieneTodasKeywords && tieneUnaKeyword) {
                ponerCarta = false;
            } else {
                // Agregar a newCards si cumple las condiciones
                ponerCarta = (and && tieneTodasKeywords) || (!and && tieneUnaKeyword);
            }
        }
    
        // Agregar a newCards si debe incluirse
        if (ponerCarta) {
            newCards.push(todas_cards[j]);
        }
    }
    

        

        // Limpiar el contenedor antes de agregar las nuevas tarjetas
        container_cards.innerHTML = "";
        
        //si no se selecciono ninguna carta mostralas todas
        var cond = false;
        for (let keyword of Object.keys(keywords_selected)) {
            if (keywords_selected[keyword]) {
                cond = true;
                break;
            }
        }
        if (!cond) {
            newCards = todas_cards;
        }

        // Agregar las nuevas tarjetas
        for (let k = 0; k < newCards.length; k++) {
            container_cards.appendChild(newCards[k]);
        }

        if (newCards.length === 0) {
            msj.classList.remove("d-none");
            msj.classList.add("d-block");
        } else {
            msj.classList.remove("d-block");
            msj.classList.add("d-none");
        }
}


for (let i = 0; i < keywords.length; i++) {
    // Inicializar el diccionario de keywords seleccionados
    keywords_selected[keywords[i].innerHTML] = false;
    keywords[i].addEventListener("click", function() {
        var keyword = this.innerHTML;
        
        // Toggle the selection for the keyword
        keywords_selected[keyword] = !keywords_selected[keyword];
        
        // Toggle the CSS classes based on selection
        if (keywords_selected[keyword]) {
            keywords[i].classList.add("btn-dark");
            keywords[i].classList.remove("btn-outline-dark");
        } else {
            keywords[i].classList.add("btn-outline-dark");
            keywords[i].classList.remove("btn-dark");
        }

        filtrarTarjetas();
    });
}
