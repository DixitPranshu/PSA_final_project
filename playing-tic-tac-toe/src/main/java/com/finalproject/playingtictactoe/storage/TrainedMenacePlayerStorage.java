package com.finalproject.playingtictactoe.storage;

import com.finalproject.playingtictactoe.model.MenacePlayer;

import java.util.HashMap;
import java.util.Map;

public class TrainedMenacePlayerStorage {

    private static Map<String, MenacePlayer> menacePlayers;
    private static TrainedMenacePlayerStorage instance;

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
