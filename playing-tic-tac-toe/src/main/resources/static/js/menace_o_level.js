window.transitionToPage = function(href) {
    document.querySelector('body').style.opacity = 0
    setTimeout(function() {
        window.location.href = href
    }, 500)
}

window.addEventListener('DOMContentLoaded', () => {
//    document.querySelector('body').style.opacity = 1

    document.getElementById("easy-o-button").addEventListener("click", easy_o_button_onclick);
    document.getElementById("medium-o-button").addEventListener("click", medium_o_button_onclick);
    document.getElementById("hard-o-button").addEventListener("click", hard_o_button_onclick);

    function easy_o_button_onclick(){
        sessionStorage.setItem("menace-o-level","easy");
        location.href = "menacegame.html";
    }
    function medium_o_button_onclick(){
        sessionStorage.setItem("menace-o-level","medium");
        location.href = "menacegame.html";
    }
    function hard_o_button_onclick(){
        sessionStorage.setItem("menace-o-level","hard");
        location.href = "menacegame.html";
    }



});



