var dest = "level.html";
function human_button_onclick(){
    sessionStorage.setItem("button", "human-button");
    location.href=dest;
}


function menace_button_onclick(){
    sessionStorage.setItem("button", "menace-button");
    location.href="menacegame.html";
}

function train_menace_button_onclick(){
    sessionStorage.setItem("button", "train-menace-button");
    location.href="trainmenace.html";
}
