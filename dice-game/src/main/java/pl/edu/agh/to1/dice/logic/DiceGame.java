package pl.edu.agh.to1.dice.logic;

import java.util.List;


// TODO:	pozbyc sie System.out.println() ;]
//		Mozna zrefaktoryzowac do Model-View, ale czy nam to narazie potrzebne? ~Kacper
// 		Na razie kompletnie niepotrzebne, tak tylko na przysz³oœæ tu zasygnalizowa³am ¿eby siê
//		do tego nei przywi¹zywaæ specjalnie. /Patrycja
public class DiceGame {

	private final int DICE_COUNT;
	private final int REROLL_TIMES;
	private final int SCORES_PER_CATEGORY;
	private final int NR_OF_TURNS;
	
	private final List<Player> players;
	private final ScoreTable table;
	
	DiceGame(List<Player> players, int diceCount, int rerollTimes, int scoresPerCategory) {
		DICE_COUNT = diceCount;
		REROLL_TIMES = rerollTimes;
		SCORES_PER_CATEGORY = scoresPerCategory;
		NR_OF_TURNS = ScoreCategory.values().length * SCORES_PER_CATEGORY;
		table = new ScoreTable(players, SCORES_PER_CATEGORY);
		this.players = players;
	}

	public void play() {
		for (int turnNr = 1; turnNr <= NR_OF_TURNS; turnNr++) {
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
