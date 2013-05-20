package pl.edu.agh.to1.dice.logic;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import pl.edu.agh.to1.dice.view.IOHandler;


public class DiceGame {
	private final int DICE_COUNT;
	private final int REROLL_TIMES;
	private final int SCORES_PER_CATEGORY;
	private final int NR_OF_TURNS;
	
	private final List<Player> players;
	private final ScoreTable table;
	
	private IOHandler view;
	
	DiceGame(List<Player> players, int diceCount, int rerollTimes, int scoresPerCategory, IOHandler ioh) {
		DICE_COUNT = diceCount;
		REROLL_TIMES = rerollTimes;
		SCORES_PER_CATEGORY = scoresPerCategory;
		NR_OF_TURNS = ScoreCategory.values().length * SCORES_PER_CATEGORY;
		table = new ScoreTable(players, SCORES_PER_CATEGORY);
		this.players = players;
		view = ioh;
	}

	public void play() {
		view.init();
		for (int turnNr = 1; turnNr <= NR_OF_TURNS; turnNr++) {
			for (Player player : players) {
				view.showTable(table.toString());
				view.newTurn(turnNr, player.toString());
				Score currentTurnScore = takeTurn(player);
				view.showPoints(currentTurnScore.getPoints());
				table.updateTable(player, currentTurnScore);
			}
		}
		view.showWinner(table.getWinner(), table.toString());
		
		try {
			HighScore.getInstance().update(players, table, SCORES_PER_CATEGORY);
		} catch (IOException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Encountered error when trying to update high scores! :(");
		}
	}

	private Score takeTurn(Player player) {

		DiceRoll roll = player.rollDice(DICE_COUNT);
		roll = player.rerollDice(roll, REROLL_TIMES);
	
		ScoreCategory category = player.chooseScoreCategory();
		Score score = new Score(roll, category);
		
		while (!table.isLegal(player, score)) {
			category = player.chooseScoreCategoryAgain();
			score = new Score(roll, category);
		}

		return score;
	}



}
