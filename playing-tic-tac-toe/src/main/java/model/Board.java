package model;

import java.util.Arrays;

public class Board {
    String empty_char = "_";
    String[] boardArray = new String[9];
    public Board() {
        Arrays.fill(this.boardArray, empty_char);
    }
    public String[] getBoard(){
        return this.boardArray;
    }
//    public boolean valid_move(int move){
//        if(0<=move && move<=8 && board[move].equals(" ")){
//            return true;
//        }
//        return false;
//    }
    public void setBoard(String[] boardArray) {
        for (int i = 0; i < boardArray.length; i++) {
            this.boardArray[i] = boardArray[i];
        }
    }

    public boolean isWinning(){
        
        if(((!this.boardArray[0].equals(empty_char)) && ((this.boardArray[0].equals(this.boardArray[1]) && this.boardArray[1].equals(this.boardArray[2])) ||
                                (this.boardArray[0].equals(this.boardArray[3]) && this.boardArray[3].equals(this.boardArray[6])) ||
                                (this.boardArray[0].equals(this.boardArray[4]) && this.boardArray[4].equals(this.boardArray[8])))) ||
                ((!this.boardArray[4].equals(empty_char)) && ((this.boardArray[1].equals(this.boardArray[4]) && this.boardArray[4].equals(this.boardArray[7])) ||
                                (this.boardArray[3].equals(this.boardArray[4]) && this.boardArray[4].equals(this.boardArray[5])) ||
                                (this.boardArray[2].equals(this.boardArray[4]) && this.boardArray[4].equals(this.boardArray[6])))) ||
                ((!this.boardArray[8].equals(empty_char)) && ((this.boardArray[2].equals(this.boardArray[5]) && this.boardArray[5].equals(this.boardArray[8])) ||
                                (this.boardArray[6].equals(this.boardArray[7]) && this.boardArray[7].equals(this.boardArray[8]))))){
            return true;
        }
        return false;
    }

    public boolean isDraw(){
        for(int i =0;i<this.boardArray.length;i++){
            if(this.boardArray[i].equals(empty_char)){
                return false;
            }
        }
        return true;
    }

    public void playMove(int position, String marker){
        this.boardArray[position] = marker;
    }
}
