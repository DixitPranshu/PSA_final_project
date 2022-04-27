package com.finalproject.playingtictactoe.service;

import com.finalproject.playingtictactoe.storage.TrainedMenacePlayerStorage;
import com.finalproject.playingtictactoe.model.Board;
import com.finalproject.playingtictactoe.model.Game;
import com.finalproject.playingtictactoe.model.GamePlay;
import com.finalproject.playingtictactoe.model.MenacePlayer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;
@Service
@AllArgsConstructor
public class GameService {

    public void save_matches_played(ArrayList<ArrayList<String>> matches_played){
        TrainedMenacePlayerStorage.getInstance().setMatches_played(matches_played);
    }


    public String save_menace(String level, MenacePlayer menacePlayer){
        return TrainedMenacePlayerStorage.getInstance().setMenacePlayers(level, menacePlayer);
    }

    public void update_menace(String id, MenacePlayer menacePlayer){
        TrainedMenacePlayerStorage.getInstance().updateMenacePlayer(id,menacePlayer);
    }

    public GamePlay play_move(String[] board, String id){
        GamePlay gamePlay = new GamePlay();
        gamePlay.setBoard(board);
        gamePlay.setMenacePlayerId(id);
        return gamePlay;
    }
}
