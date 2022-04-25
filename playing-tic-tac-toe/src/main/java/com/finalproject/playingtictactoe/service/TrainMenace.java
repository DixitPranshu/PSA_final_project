package com.finalproject.playingtictactoe.service;

import com.finalproject.playingtictactoe.model.Board;
import com.finalproject.playingtictactoe.model.MenacePlayer;

public class TrainMenace {

    public void save_menace_level(String level, MenacePlayer menacePlayer1, MenacePlayer menacePlayer2){
        GameService gameService = new GameService();
        MenacePlayer newMenacePlayer = new MenacePlayer(0,0,0);

        if(menacePlayer1.getNum_win()> menacePlayer2.getNum_win()){
            newMenacePlayer.setMatch_boxes(menacePlayer2.getMatch_boxes());
            newMenacePlayer.setNum_win(menacePlayer2.getNum_win());
            newMenacePlayer.setNum_draw(menacePlayer2.getNum_draw());
            newMenacePlayer.setNum_lose(menacePlayer2.getNum_lose());
        }
        else{
            newMenacePlayer.setMatch_boxes(menacePlayer1.getMatch_boxes());
            newMenacePlayer.setNum_win(menacePlayer1.getNum_win());
            newMenacePlayer.setNum_draw(menacePlayer1.getNum_draw());
            newMenacePlayer.setNum_lose(menacePlayer1.getNum_lose());
        }
        newMenacePlayer.setGame_active(false);
        gameService.save_menace(level, newMenacePlayer);
    }
    public void train_menace(){

        MenacePlayer menacePlayer1 = new MenacePlayer(0,0,0);
        MenacePlayer menacePlayer2 = new MenacePlayer(0,0,0);

        for(int i = 0;i<100000;i++){
            if(i==100){
                save_menace_level("easy", menacePlayer1, menacePlayer2);
            }
            else if(i==500){
                save_menace_level("medium", menacePlayer1, menacePlayer2);
            }
            menacePlayer1.start_game();
            menacePlayer2.start_game();
            System.out.println("Playing game "+i+"\n");
            Board board = new Board();

            while(true){
                int move = menacePlayer1.get_move(board);
                if(move==-1){
                    menacePlayer1.lose_game();
                    menacePlayer2.win_game();
                    break;
                }
                board.playMove(move,"X");
                if(board.isWinning()){
                    menacePlayer1.win_game();
                    menacePlayer2.lose_game();
                    break;
                }
                if(board.isDraw()) {
                    menacePlayer1.draw_game();
                    menacePlayer2.draw_game();
                    break;
                }
                int move2 = menacePlayer2.get_move(board);
                if(move2==-1){
                    menacePlayer2.lose_game();
                    menacePlayer1.win_game();
                    break;
                }
                board.playMove(move2, "O");
                if(board.isWinning()){
                    menacePlayer2.win_game();
                    menacePlayer1.lose_game();
                    break;
                }
            }
//            menacePlayer1.print_stats();
//            menacePlayer2.print_stats();
        }
        save_menace_level("hard", menacePlayer1, menacePlayer2);


    }


}
