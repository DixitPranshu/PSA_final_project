package com.finalproject.playingtictactoe.service;

import com.finalproject.playingtictactoe.Storage.TrainedMenacePlayerStorage;
import com.finalproject.playingtictactoe.model.GamePlay;
import com.finalproject.playingtictactoe.model.MenacePlayer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameService {


//    public Game createGame(MenacePlayer menace_player) {
//        Game game = new Game();
//        Board board = new Board();
//        game.setBoard(board);
//        game.setGameId(UUID.randomUUID().toString());
//        game.setMenacePlayer(menace_player);
////        game.setStatus(NEW);
////        GameStorage.getInstance().setGame(game);
//        return game;
//    }
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
