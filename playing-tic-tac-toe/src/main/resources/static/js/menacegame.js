window.addEventListener('DOMContentLoaded', () => {
    document.getElementById("start").addEventListener("click", start_game);
    document.getElementById("start_new_game").addEventListener("click", start_new_game);
    const tiles = Array.from(document.querySelectorAll('.tile'));
    const playerDisplay = document.querySelector('.display-player');
    const resetButton = document.querySelector('#reset');
    const announcer = document.querySelector('.announcer');
    let board = [];
    function reset_board() {
        for(var i=0;i<9;i++){
            board[i]='_';
        }
        return board;
    }
    reset_board();
    let currentPlayer = 'X';
    let isGameActive = true;

    const PLAYERX_WON = 'MENACE_PLAYERX_WON';
    const PLAYERO_WON = 'MENACE_PLAYERO_WON';
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
    let roundWon = false;
    function handleResultValidation() {

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
                announcer.innerHTML = 'Menace Player <span class="playerO">O</span> Won';
                break;
            case PLAYERX_WON:
                announcer.innerHTML = 'Menace Player <span class="playerX">X</span> Won';
                break;
            case TIE:
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


    function get_index(orig_board, new_board){
        for(var i=0;i<board.length;i++){
            if(orig_board[i]!=new_board[i]){
                return i;
            }
        }
        return -1;
    }

    function sleep (time) {
      return new Promise((resolve) => setTimeout(resolve, time));
    }

    function start_new_game(){
            location.href="index.html";
        }

    function start_game(){

    if(board.includes(empty_char) && roundWon!=true){
            var data = JSON.stringify({
                      "board": board,
                      "menacePlayerId": "easy"});
            var xhr = new XMLHttpRequest();
            xhr.withCredentials = true;
            xhr.onreadystatechange = function() {
              if(this.readyState === 4) {
                sleep(500).then(() => {
                    console.log(xhr.responseText);
                    var json = JSON.parse(xhr.responseText);
                    var new_board = json.board
                    var new_index = get_index(board, new_board);
                    if(new_index!=-1){
                        tile = tiles[new_index];
                        tile.innerText = currentPlayer;
                        tile.classList.add(`player${currentPlayer}`);
                        updateBoard(new_index);
                        console.log(board);
                        handleResultValidation();
                        changePlayer();
                        start_game();
                    }
                });

              }
            };
            xhr.open("POST", "http://localhost:8080/game/playWithMenace");
            xhr.setRequestHeader("Access-Control-Allow-Origin", "*");
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.send(data);

            handleResultValidation();
//            changePlayer();


        }
    }

    const userAction = (tile, index) => {
        if(isValidAction(tile) && isGameActive) {
            tile.innerText = currentPlayer;
            tile.classList.add(`player${currentPlayer}`);
            updateBoard(index);
            var level = sessionStorage.getItem("level");

        }
    }
    
    const resetBoard = () => {
        reset_board();
        isGameActive = true;
        announcer.classList.add('hide');
        roundWon = false;
        if (currentPlayer === 'O') {
            changePlayer();
        }

        tiles.forEach(tile => {
            tile.innerText = '';
            tile.classList.remove('playerX');
            tile.classList.remove('playerO');
        });
    }

    tiles.forEach( (tile, index) => {
        tile.addEventListener('click', () => userAction(tile, index));
    });

    resetButton.addEventListener('click', resetBoard);
});