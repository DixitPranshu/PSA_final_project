package com.finalproject.playingtictactoe.controller;

import com.finalproject.playingtictactoe.PlayingTicTacToeApplication;
import com.finalproject.playingtictactoe.storage.TrainedMenacePlayerStorage;
import com.finalproject.playingtictactoe.model.*;
import com.finalproject.playingtictactoe.service.GameService;
import com.finalproject.playingtictactoe.service.TrainMenace;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    @CrossOrigin
    @PostMapping("/trainMenace")
    public ResponseEntity<ArrayList<String>> trainMenace(@RequestBody GameMove request){
        ArrayList<ArrayList<String>> matches_played = TrainedMenacePlayerStorage.getInstance().getMatches_played();
        ArrayList<String> moves_played = matches_played.get(Integer.parseInt(request.getGameNum()));
        return ResponseEntity.ok(moves_played);
    }

    @CrossOrigin
    @PostMapping("/playWithMenace")
    public ResponseEntity<GamePlay> gamePlay(@RequestBody GamePlay request){
        Logger logger = PlayingTicTacToeApplication.logger;
        boolean onlinePlay = true;
        String gameMode = request.getGameMode();
        if(gameMode.equals("menace")){
            onlinePlay = false;
        }
        String menace_id = request.getMenacePlayerId();
        Map<String, MenacePlayer> players = TrainedMenacePlayerStorage.getInstance().getMenacePlayers();
        MenacePlayer menacePlayer = TrainedMenacePlayerStorage.getInstance().getMenacePlayers().get(menace_id);

        if(!menacePlayer.isGame_active()){
            menacePlayer.start_game();
            menacePlayer.setGame_active(true);
        }

        String[] boardArray = request.getBoard();
        Board board = new Board();
        board.setBoard(boardArray);
//        logger.info("Player X Plays: {}",board.getBoardString());
        if(board.isWinning()){
            menacePlayer.lose_game();
            menacePlayer.setGame_active(false);
            gameService.update_menace(menace_id, menacePlayer);
            return ResponseEntity.ok(gameService.play_move(board.getBoard(), menace_id));
        }

        if(board.isDraw()) {
            menacePlayer.draw_game();
            menacePlayer.setGame_active(false);
            gameService.update_menace(menace_id, menacePlayer);
            return ResponseEntity.ok(gameService.play_move(board.getBoard(), menace_id));
        }

        int move = menacePlayer.get_move(board, onlinePlay, false);

        board.playMove(move,"O");
        logger.info("Player O Plays: {}",board.getBoardString());
        if(board.isWinning()){
            menacePlayer.win_game();
            menacePlayer.setGame_active(false);
        }
        if(board.isDraw()) {
            menacePlayer.draw_game();
            menacePlayer.setGame_active(false);
        }
        gameService.update_menace(menace_id, menacePlayer);
        return ResponseEntity.ok(gameService.play_move(board.getBoard(), menace_id));
    }
}
