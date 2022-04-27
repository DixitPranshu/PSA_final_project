package com.finalproject.playingtictactoe.storage;

import com.finalproject.playingtictactoe.model.MenacePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TrainedMenacePlayerStorage {

    private static Map<String, MenacePlayer> menacePlayers;
    private static TrainedMenacePlayerStorage instance;
    private static ArrayList<ArrayList<String>> matches_played;

    public  Map<String, MenacePlayer> getMenacePlayers() {
        return menacePlayers;
    }

    public String setMenacePlayers(String level, MenacePlayer menacePlayer) {
//        String id = "e3dabe99-03d1-40b0-a956-d005797c8a5e";
        menacePlayers.put(level, menacePlayer);
        return level;
    }

    public void updateMenacePlayer(String id, MenacePlayer menacePlayer){
        menacePlayers.put(id, menacePlayer);
    }


    public ArrayList<ArrayList<String>> getMatches_played() {
        return matches_played;
    }

    public void setMatches_played(ArrayList<ArrayList<String>> matches_played) {
        TrainedMenacePlayerStorage.matches_played = matches_played;
    }

    private TrainedMenacePlayerStorage(){

        menacePlayers = new HashMap<>();
    }

    public static synchronized TrainedMenacePlayerStorage getInstance(){
        if(instance == null){
            instance = new TrainedMenacePlayerStorage();
        }
        return instance;
    }


}
