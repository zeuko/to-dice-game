package pl.edu.agh.to1.dice.logic;

import java.util.List;

public class DiceGame {

	// TODO
	//			moze w konstruktorze
	private final int DICE_COUNT = 5;
	private final int REROLL_TIMES = 2;
	private final int SCORES_PER_CATEGORY = 1;
	private final int TURN_NR = ScoreCategory.values().length * SCORES_PER_CATEGORY;
	
	private final List<Player> players;
	private final ScoreTable table;
	
	public DiceGame(List<Player> players) {
		table = new ScoreTable(players, SCORES_PER_CATEGORY);
		this.players = players;
	}

	public void play() {
		for (int turnNr = 1; turnNr <= TURN_NR; turnNr++) {
			for (Player player : players) {
				table.printTable();
				System.out.println("TURN " + turnNr + "\n" + player.toString());
				Score currentTurnScore = takeTurn(player);
				System.out.println("Points awarded: "
						+ currentTurnScore.getPoints());
				table.updateTable(player, currentTurnScore);
			}
		}

		System.out.println("------- FINAL RESULTS -------");
		table.printTable();
		List<Player> winner = table.getWinner();
		if (winner.size() == 1)
			System.out.println("The winner is: " + table.getWinner().get(0)
					+ ". Congratulations!");
		else
			System.out.println("There's a tie! Congratulations!");
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
