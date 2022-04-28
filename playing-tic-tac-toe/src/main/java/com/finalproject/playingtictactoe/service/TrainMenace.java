package com.finalproject.playingtictactoe.service;

import com.finalproject.playingtictactoe.PlayingTicTacToeApplication;
import com.finalproject.playingtictactoe.model.Board;
import com.finalproject.playingtictactoe.model.MenacePlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Set;


public class TrainMenace {
//
    Logger logger = PlayingTicTacToeApplication.logger;
    public ArrayList<ArrayList<String>> matches_played = new ArrayList<>();

    public void save_matches_played(ArrayList<ArrayList<String>> matches_played){

        GameService gameService = new GameService();
        gameService.save_matches_played(matches_played);
    }
    public void save_menace_level(String level, MenacePlayer menacePlayer1, MenacePlayer menacePlayer2){
        GameService gameService = new GameService();
        MenacePlayer newMenacePlayer = new MenacePlayer(0,0,0);
        Hashtable<String, ArrayList<Integer>> menacePlayer1matchboxes = menacePlayer1.getMatch_boxes();
        Hashtable<String, ArrayList<Integer>> menacePlayer2matchboxes = menacePlayer2.getMatch_boxes();
        for(String key: menacePlayer1matchboxes.keySet()){

            if(!menacePlayer2matchboxes.containsKey(key)){
                menacePlayer2matchboxes.put(key, menacePlayer1matchboxes.get(key));
            }
            else{
                    ArrayList<Integer> match_boxes = menacePlayer2matchboxes.get(key);
                    match_boxes.addAll(menacePlayer1matchboxes.get(key));
                    menacePlayer2matchboxes.put(key, match_boxes);
            }
        }
        newMenacePlayer.setMatch_boxes(menacePlayer2matchboxes);
        newMenacePlayer.setGame_active(false);
        gameService.save_menace(level, newMenacePlayer);
    }
    public void train_menace(){

        MenacePlayer menacePlayer1 = new MenacePlayer(0,0,0);
        MenacePlayer menacePlayer2 = new MenacePlayer(0,0,0);


        for(int i = 0;i<100000;i++){
            ArrayList<String> moves_played = new ArrayList<>();
            if(i==100){
                save_menace_level("easy", menacePlayer1, menacePlayer2);
            }
            else if(i==1000){
                save_menace_level("medium", menacePlayer1, menacePlayer2);
            }
            menacePlayer1.start_game();
            menacePlayer2.start_game();
            Board board = new Board();
            String win = "LOST";
            while(true){
                int move = menacePlayer1.get_move(board, false, true);
                if(move==-1){
                    menacePlayer1.lose_game();
                    menacePlayer2.win_game();
                    break;
                }
                board.playMove(move,"X");
                moves_played.add(board.getBoardString());
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
                int move2 = menacePlayer2.get_move(board, false, true);
                if(move2==-1){
                    menacePlayer2.lose_game();
                    menacePlayer1.win_game();
                    break;
                }
                board.playMove(move2, "O");
                moves_played.add(board.getBoardString());
                if(board.isWinning()){
                    win="WON";
                    menacePlayer2.win_game();
                    menacePlayer1.lose_game();
                    break;
                }
            }
            float num_win = menacePlayer2.getNum_win();
            float num_loss = menacePlayer2.getNum_lose();
            float num_draw = menacePlayer2.getNum_draw();
            float p = 0;
            if(num_win >0){
                p =  (num_win+num_draw)/(num_win+num_loss+num_draw);
            }

            logger.info("Game {} {} p = {}",i+1,win,p);
//            menacePlayer1.print_stats();
//            menacePlayer2.print_stats();
            this.matches_played.add(moves_played);
        }
        save_menace_level("hard", menacePlayer1, menacePlayer2);
        save_matches_played(matches_played);
    }

    public ArrayList<ArrayList<String>> getMatches_played() {
        return matches_played;
    }

    public void setMatches_played(ArrayList<ArrayList<String>> matches_played) {
        this.matches_played = matches_played;
    }
}
