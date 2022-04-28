package com.finalproject.playingtictactoe.model;

import lombok.Data;

@Data
public class GamePlay {

    private String[] board;
    private String menacePlayerId;
    private String gameMode;
    private String move_num;
}
