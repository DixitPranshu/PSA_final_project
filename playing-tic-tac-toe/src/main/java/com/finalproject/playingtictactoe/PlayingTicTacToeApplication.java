package com.finalproject.playingtictactoe;

import com.finalproject.playingtictactoe.model.Board;
import com.finalproject.playingtictactoe.model.MenacePlayer;
import com.finalproject.playingtictactoe.service.GameService;
import com.finalproject.playingtictactoe.service.TrainMenace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlayingTicTacToeApplication {

	private static TrainMenace trainMenace;
	public static final Logger logger = LoggerFactory.getLogger(PlayingTicTacToeApplication.class);
	public static void main(String[] args) {
		trainMenace = new TrainMenace();
		trainMenace.train_menace();
		SpringApplication.run(PlayingTicTacToeApplication.class, args);
	}
}
