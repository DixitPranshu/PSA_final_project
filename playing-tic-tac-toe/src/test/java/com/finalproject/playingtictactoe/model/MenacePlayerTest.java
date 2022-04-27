package com.finalproject.playingtictactoe.model;

import lombok.var;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MenacePlayerTest {

    @Test
    void isGame_active() {
        MenacePlayer menacePlayer = new MenacePlayer(0,0,0);
        assertEquals(true, menacePlayer.isGame_active());
    }

    @Test
    void setGame_active() {

        MenacePlayer menacePlayer = new MenacePlayer(0,0,0);
        menacePlayer.setGame_active(false);
        assertEquals(false, menacePlayer.isGame_active());
    }

    @Test
    void start_game() {
        MenacePlayer menacePlayer = new MenacePlayer(0,0,0);
        menacePlayer.start_game();
        assertEquals(0, menacePlayer.moves_played.size());
    }

    @Test
    void get_move_with_max_prob() {

        ArrayList<Integer> beads = new ArrayList<Integer>(Arrays.asList(0,0,0,0,1,1,1,1,2,2,2,2,2,2,3,3,3,4,4,5,5,6,7,7,8,8));
        var menacePlayer = new MenacePlayer(0,0,0);
        float[] output = {2,(float) 6/26};
        assertEquals(Arrays.toString(output),Arrays.toString(menacePlayer.get_move_with_max_prob(beads)));
    }

    @Test
    void addNewBeads() {

        String[] board_array = {"_","_","_","_","_","_","_","_","_"};
        var menacePlayer = new MenacePlayer(0,0,0);
        ArrayList<Integer> new_beads_repeated = new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,1,1,1,1,1,2,2,2,2,2,3,3,3,3,3,4,4,4,4,4,5,5,5,5,5,6,6,6,6,6,7,7,7,7,7,8,8,8,8,8));
        assertEquals(new_beads_repeated, menacePlayer.addNewBeads(board_array));
    }

    @Test
    void get_move() {

        Board board = new Board();
        String[] board_array = {"O","_","O","_","X","_","X","_","_"};
        var menacePlayer = new MenacePlayer(0,0,0);
        board.setBoard(board_array);
        menacePlayer.start_game();
        assertEquals(1, menacePlayer.get_move(board, false));
    }

    @Test
    void win_game() {


    }

    @Test
    void get_frequency_count() {
        ArrayList<Integer> new_beads_repeated = new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,1,1,1,1,1,2,2,2,2,2,3,3,3,3,3,4,4,4,4,4,5,5,5,5,5,6,6,6,6,6,7,7,7,7,7,8,8,8,8,8));
        var menacePlayer =  new MenacePlayer(0,0,0);
        Map<Integer, Integer> freq_map = new HashMap<Integer, Integer>(){{
            put(0,5);
            put(1,5);
            put(2,5);
            put(3,5);
            put(4,5);
            put(5,5);
            put(6,5);
            put(7,5);
            put(8,5);
        }};
        assertEquals(freq_map, menacePlayer.get_frequency_count(new_beads_repeated));
    }
}