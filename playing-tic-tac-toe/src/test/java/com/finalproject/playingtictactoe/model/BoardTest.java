package com.finalproject.playingtictactoe.model;

import lombok.var;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void isWinning() {
        Board board = new Board();
        String[] board_array = {"X","X","X","_","O","_","O","_","O"};
        board.setBoard(board_array);
        assertEquals(true, board.isWinning());
    }

    @Test
    void getBoard() {

        Board board = new Board();
        String[] board_array = {"X","X","X","_","O","_","O","_","O"};
        board.setBoard(board_array);
        assertEquals(Arrays.toString(board_array), Arrays.toString(board.getBoard()));
    }

    @Test
    void setBoard() {
        Board board = new Board();
        String[] board_array = {"X","X","X","_","O","_","O","_","O"};
        board.setBoard(board_array);
        assertEquals(Arrays.toString(board_array), Arrays.toString(board.getBoard()));
    }

    @Test
    void isDraw() {
        Board board = new Board();
        String[] board_array = {"X","O","X","X","O","O","O","X","O"};
        board.setBoard(board_array);
        assertEquals(true, board.isDraw());
    }

    @Test
    void getBoardString() {

        Board board = new Board();
        String[] board_array = {"X","X","X","_","O","_","O","_","O"};
        board.setBoard(board_array);
        assertEquals("XXX_O_O_O",board.getBoardString());
    }
}