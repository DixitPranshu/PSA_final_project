package com.finalproject.playingtictactoe.model;

import com.finalproject.playingtictactoe.PlayingTicTacToeApplication;
import org.slf4j.Logger;

import java.util.*;

public class MenacePlayer {
    Logger logger = PlayingTicTacToeApplication.logger;
    Hashtable<String, ArrayList<Integer>> match_boxes;
    Hashtable<String, ArrayList<Integer>> states_lost;
    int num_win;
    int num_draw;
    int num_lose;
    boolean game_active;
    Hashtable<String, Integer> moves_played;
    String empty_char = "_";
    public MenacePlayer(int num_win, int num_draw, int num_lose) {
        this.num_win = num_win;
        this.num_draw = num_draw;
        this.num_lose = num_lose;
//        this.moves_played = new Hashtable<>();
        this.match_boxes = new Hashtable<>();
        this.states_lost = new Hashtable<>();
        this.game_active = true;
    }

    public boolean isGame_active() {
        return game_active;
    }

    public void setGame_active(boolean game_active) {
        this.game_active = game_active;
    }

    public void start_game(){
        this.moves_played = new Hashtable<>();
//        this.match_boxes = new Hashtable<>();
    }
    public float[] get_move_with_max_prob(ArrayList<Integer> new_beads_repeated){

        Map<Integer, Integer> freq_map = get_frequency_count(new_beads_repeated);
        Set<Integer> bead_keys = freq_map.keySet();

        float move_with_max_prob[] = new float[2];
        float total_beads = new_beads_repeated.size();
        for (Integer key : bead_keys) {
            float bead_count = freq_map.get(key);
            float prob = bead_count/total_beads;
            if(prob>move_with_max_prob[1]){
                move_with_max_prob[0] = key;
                move_with_max_prob[1] = prob;
            }
        }
        return move_with_max_prob;
    }

     public ArrayList<Integer> addNewBeads(String[] board_array){
         ArrayList<Integer> new_beads = new ArrayList<Integer>();
         ArrayList<Integer> new_beads_repeated = new ArrayList<Integer>();
         for (int i =0;i<board_array.length; i++) {
             if (board_array[i].equals(empty_char)) {
                 new_beads.add(i);
             }
         }

         int n_copies = (new_beads.size()+2)/2;
         for(int i =0;i<new_beads.size();i++){
             new_beads_repeated.addAll(Collections.nCopies(n_copies, new_beads.get(i)));
         }
         return new_beads_repeated;
     }

    public int get_move(Board board, boolean train){
        String[] board_array = board.getBoard();
        ArrayList<Integer> new_beads_repeated = new ArrayList<Integer>();

        int rand_bead = -1;
        String board_str=board.getBoardString();

        if(!this.match_boxes.containsKey(board_str)){
            new_beads_repeated = addNewBeads(board_array);
            this.match_boxes.put(board_str, new_beads_repeated);
        }
        new_beads_repeated = this.match_boxes.get(board_str);
        Random rand = new Random();
        if(new_beads_repeated.size()>0){

            if(!train){

                float[] move_with_max_prob = get_move_with_max_prob(new_beads_repeated);
                logger.info("Move probability: {}",move_with_max_prob[1]);
                rand_bead = (int) move_with_max_prob[0];
                this.moves_played.put(board_str, (int) move_with_max_prob[0]);

//                while(get_move_with_max_prob(rand_bead, new_beads, new_beads_repeated)<0.9){
//                    rand_bead = new_beads_repeated.get(rand.nextInt(new_beads_repeated.size()));
//                }
            }
            else{
                rand_bead = new_beads_repeated.get(rand.nextInt(new_beads_repeated.size()));
                this.moves_played.put(board_str, rand_bead);
            }

        }
        if(rand_bead==-1){
            // System.out.println("lost: "+this.states_lost.get(board_str));
//            System.out.println("Match box beads before adding: "+this.match_boxes.get(board_str));
            new_beads_repeated = addNewBeads(board_array);
            this.match_boxes.put(board_str,new_beads_repeated);
//            System.out.println("Match box beads after adding: "+this.match_boxes.get(board_str));
            rand_bead = new_beads_repeated.get(rand.nextInt(new_beads_repeated.size()));
            this.moves_played.put(board_str, rand_bead);
//             System.out.println("hi: "+new_beads_repeated);
        }
        return rand_bead;
    }

    public void win_game(){
        Set<String> board_keys = this.moves_played.keySet();

             for (String key : board_keys) {
                 ArrayList<Integer> beads = this.match_boxes.get(key);
                 // System.out.println("Beads before adding: "+get_frequncy_count(beads));
                 beads.add(this.moves_played.get(key));
                 beads.add(this.moves_played.get(key));
                 beads.add(this.moves_played.get(key));
                 // System.out.println("Game won");
                 // System.out.println("Beads after adding: "+get_frequncy_count(beads));
             }
        this.num_win+=1;
    }
    public Map<Integer, Integer> get_frequency_count(ArrayList<Integer> beads){

        Map<Integer, Integer> freq_map = new HashMap<>();
        for(int i=0;i<beads.size();i++){
            int key = beads.get(i);
            if(!freq_map.containsKey(key)){
                freq_map.put(key,1);
            }
            else{
                freq_map.put(beads.get(i),freq_map.get(key)+1);
            }
        }
        return freq_map;

    }
    public void draw_game(){
        Set<String> board_keys = this.moves_played.keySet();
        for (String key : board_keys) {
            ArrayList<Integer> beads = this.match_boxes.get(key);
            beads.add(this.moves_played.get(key));
        }
        this.num_draw+=1;
    }

    public void lose_game(){
        Set<String> board_keys = this.moves_played.keySet();
        for (String key : board_keys) {
            ArrayList<Integer> beads = this.match_boxes.get(key);
            // System.out.println("Beads before removing: "+get_frequncy_count(beads));
            int index = beads.indexOf(this.moves_played.get(key));
            beads.remove(index);
            // System.out.println("Game lost");
            // System.out.println("Beads after removing: "+get_frequncy_count(beads));
//            this.states_lost.put(key,beads);
        }
        this.num_lose+=1;
    }

    public void print_stats(){
         System.out.println("Have learnt "+ this.match_boxes.size()+" boards");
         System.out.println("W: "+this.num_win+" D: "+this.num_draw+" L: "+this.num_lose);
    }


    public int getNum_win() {
        return num_win;
    }

    public int getNum_draw() {
        return num_draw;
    }

    public int getNum_lose() {
        return num_lose;
    }

    public Hashtable<String, ArrayList<Integer>> getMatch_boxes() {
        return match_boxes;
    }

    public void setMatch_boxes(Hashtable<String, ArrayList<Integer>> match_boxes) {
        this.match_boxes.putAll(match_boxes);
    }

    public void setNum_win(int num_win) {
        this.num_win = num_win;
    }

    public void setNum_draw(int num_draw) {
        this.num_draw = num_draw;
    }

    public void setNum_lose(int num_lose) {
        this.num_lose = num_lose;
    }

    public Hashtable<String, Integer> getMoves_played() {
        return moves_played;
    }

    public void setMoves_played(Hashtable<String, Integer> moves_played) {
        this.moves_played = moves_played;
    }
}
