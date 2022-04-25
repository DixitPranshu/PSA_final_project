package com.finalproject.playingtictactoe;

import com.finalproject.playingtictactoe.service.TrainMenace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlayingTicTacToeApplication {

	public static void main(String[] args) {
		TrainMenace trainMenace = new TrainMenace();
		trainMenace.train_menace();
		SpringApplication.run(PlayingTicTacToeApplication.class, args);
	}

}
