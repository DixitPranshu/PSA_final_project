package com.finalproject.playingtictactoe.model;

import lombok.Data;

@Data
public class Game {

    private String gameId;
    private MenacePlayer menacePlayer;
//    private Player player2;
//    private GameStatus status;
    private Board board;

}