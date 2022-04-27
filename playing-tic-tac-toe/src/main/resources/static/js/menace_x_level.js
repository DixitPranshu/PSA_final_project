window.transitionToPage = function(href) {
    document.querySelector('body').style.opacity = 0
    setTimeout(function() {
        window.location.href = href
    }, 500)
}

window.addEventListener('DOMContentLoaded', () => {
//    document.querySelector('body').style.opacity = 1
    document.getElementById("easy-x-button").addEventListener("click", easy_x_button_onclick);
    document.getElementById("medium-x-button").addEventListener("click", medium_x_button_onclick);
    document.getElementById("hard-x-button").addEventListener("click", hard_x_button_onclick);


    function easy_x_button_onclick(){
        sessionStorage.setItem("menace-x-level","easy");
        location.href = "menace_o_level.html";
    }
    function medium_x_button_onclick(){
        sessionStorage.setItem("menace-x-level","medium");
        location.href = "menace_o_level.html";
    }
    function hard_x_button_onclick(){
        sessionStorage.setItem("menace-x-level","hard");
        location.href = "menace_o_level.html";
    }




});



