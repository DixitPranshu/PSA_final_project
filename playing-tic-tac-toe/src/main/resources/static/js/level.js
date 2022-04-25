window.transitionToPage = function(href) {
    document.querySelector('body').style.opacity = 0
    setTimeout(function() {
        window.location.href = href
    }, 500)
}

window.addEventListener('DOMContentLoaded', () => {
//    document.querySelector('body').style.opacity = 1
    document.getElementById("easy-button").addEventListener("click", easy_button_onclick);
    document.getElementById("medium-button").addEventListener("click", medium_button_onclick);
    document.getElementById("hard-button").addEventListener("click", hard_button_onclick);

    var dest = "game.html";
    function easy_button_onclick(){
        sessionStorage.setItem("level", "easy");
        location.href=dest;
//        transitionToPage(dest);
    }
    function medium_button_onclick(){
        sessionStorage.setItem("level", "medium");
        location.href=dest;
//        transitionToPage(dest);
    }
    function hard_button_onclick(){
        sessionStorage.setItem("level", "hard");
        location.href=dest;
//        transitionToPage(dest);
    }
});



