window.addEventListener('DOMContentLoaded', () => {
    document.getElementById("start").addEventListener("click", start_new_game);
    document.getElementById("player_x").addEventListener("click", choose_player_x);
    document.getElementById("player_o").addEventListener("click", choose_player_o);
    document.getElementById("display").style.visibility = "hidden";
    const tiles = Array.from(document.querySelectorAll('.tile'));
    const playerDisplay = document.querySelector('.display-player');
    const resetButton = document.querySelector('#reset');
    const announcer = document.querySelector('.announcer');
    let level = sessionStorage.getItem("level");
    let board = [];
    let user_player = '_';
    function reset_board() {
        for(var i=0;i<9;i++){
            board[i]='_';
        }
        return board;
    }
    reset_board();
    let currentPlayer = 'X';
    let isGameActive = true;

    const PLAYERX_WON = 'PLAYERX_WON';
    const PLAYERO_WON = 'PLAYERO_WON';
    const TIE = 'TIE';
    var empty_char = '_';

    /*
        Indexes within the board
        [0] [1] [2]
        [3] [4] [5]
        [6] [7] [8]
    */

    const winningConditions = [
        [0, 1, 2],
        [3, 4, 5],
        [6, 7, 8],
        [0, 3, 6],
        [1, 4, 7],
        [2, 5, 8],
        [0, 4, 8],
        [2, 4, 6]
    ];

    function handleResultValidation() {
        let roundWon = false;
        for (let i = 0; i <= 7; i++) {
            const winCondition = winningConditions[i];
            const a = board[winCondition[0]];
            const b = board[winCondition[1]];
            const c = board[winCondition[2]];
            if (a === '_' || b === '_' || c === '_') {
                continue;
            }
            if (a === b && b === c) {
                roundWon = true;
                break;
            }
        }

    if (roundWon) {
            announce(currentPlayer === 'X' ? PLAYERX_WON : PLAYERO_WON);
            isGameActive = false;
            return;
        }

    if (!board.includes(empty_char))
        announce(TIE);
    }

    const announce = (type) => {
        switch(type){
            case PLAYERO_WON:
                if(user_player == "o"){
                    play_audio("win");
                }
                else{
                    play_audio("lose");
                }
                announcer.innerHTML = 'Player <span class="playerO">O</span> Won';
                break;
            case PLAYERX_WON:
                if(user_player == "o"){
                    play_audio("lose");
                }
                else{
                    play_audio("win");
                }
                announcer.innerHTML = 'Player <span class="playerX">X</span> Won';
                break;
            case TIE:
                play_audio("draw");
                announcer.innerText = 'Tie';
        }
        announcer.classList.remove('hide');
    };

    const isValidAction = (tile) => {
        if (tile.innerText === 'X' || tile.innerText === 'O'){
            return false;
        }

        return true;
    };

    const updateBoard =  (index) => {
        board[index] = currentPlayer;
    }

    const changePlayer = () => {
        playerDisplay.classList.remove(`player${currentPlayer}`);
        currentPlayer = currentPlayer === 'X' ? 'O' : 'X';
        playerDisplay.innerText = currentPlayer;
        playerDisplay.classList.add(`player${currentPlayer}`);
    }

    function choose_player_o(){
        document.getElementById("display").style.visibility = "visible";
        get_move();
        user_player = "o";
    }

    function choose_player_x(){
        document.getElementById("display").style.visibility = "visible";
        user_player = "x";
    }

    function get_index(orig_board, new_board){
        for(var i=0;i<board.length;i++){
            if(orig_board[i]!=new_board[i]){
                return i;
            }
        }
        return -1;
    }

    function start_new_game(){
        location.href="index.html";
    }

    function sleep (time) {
          return new Promise((resolve) => setTimeout(resolve, time));
    }

    function get_move(){
        var data = JSON.stringify({
                          "board": board,
                          "menacePlayerId": level,
                          "gameMode": "human"
                        });
        var xhr = new XMLHttpRequest();
        xhr.withCredentials = true;
        xhr.addEventListener("readystatechange", function() {
            if(this.readyState === 4) {
                var json = JSON.parse(xhr.responseText);
                var new_board = json.board
                var new_index = get_index(board, new_board);
                sleep(500).then(() => {
                    update_board_from_api(new_index);
                });
            }
        });
        xhr.open("POST", "http://localhost:8080/game/playWithMenace");
        xhr.setRequestHeader("Access-Control-Allow-Origin", "*");
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(data);
    }

    function update_board_from_api(new_index){
        tile = tiles[new_index];
        tile.innerText = currentPlayer;
        play_audio("move");
        tile.classList.add(`player${currentPlayer}`);
        updateBoard(new_index);
        console.log(board);
        handleResultValidation();
        changePlayer();
    }

    function play_audio(type){
        var audio_path = "";
        if(type=="move"){
            audio_path = "audios/move.wav";
        }
        else if(type=="win"){
            audio_path = "audios/win.mp3";
        }
        else if(type=="lose"){
            audio_path = "audios/lose.mp3";
        }
        else{
            audio_path = "audios/draw.mp3";
        }
        var audio = document.getElementById("audio");
        audio.src = audio_path;
        audio.play();
    }
    const userAction = (tile, index) => {

        if(isValidAction(tile) && isGameActive ) {
            if(user_player!='_'){
                tile.innerText = currentPlayer;
                play_audio("move");
                tile.classList.add(`player${currentPlayer}`);
                updateBoard(index);
                handleResultValidation();
                changePlayer();
                get_move();
            }
            else{
                alert("Choose a player first");
            }
        }
        else{
            alert("Invalid Move");
        }

    }
    
    const resetBoard = () => {
        user_player = "_";
        reset_board();
        isGameActive = true;
        announcer.classList.add('hide');
        announcer.textContent = '&nbsp';
        if (currentPlayer === 'O') {
            changePlayer();
        }

        tiles.forEach(tile => {
            tile.innerText = '';
            tile.classList.remove('playerX');
            tile.classList.remove('playerO');
        });
        document.getElementById("display").style.visibility = "hidden";
    }

    tiles.forEach( (tile, index) => {
        tile.addEventListener('click', () => userAction(tile, index));
    });

    resetButton.addEventListener('click', resetBoard);
});