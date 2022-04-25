package com.finalproject.playingtictactoe.controller;

import com.finalproject.playingtictactoe.storage.TrainedMenacePlayerStorage;
import com.finalproject.playingtictactoe.model.Board;
import com.finalproject.playingtictactoe.model.GamePlay;
import com.finalproject.playingtictactoe.model.MenacePlayer;
import com.finalproject.playingtictactoe.service.GameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    @CrossOrigin
    @PostMapping("/playWithMenace")
    public ResponseEntity<GamePlay> gamePlay(@RequestBody GamePlay request){
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
        int move = menacePlayer.get_move(board);
//        if(move==-1){
//
//            break;
//        }
        board.playMove(move,"O");
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
