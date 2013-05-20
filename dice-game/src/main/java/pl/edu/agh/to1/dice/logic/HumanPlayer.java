package pl.edu.agh.to1.dice.logic;

import pl.edu.agh.to1.dice.view.IOHandler;

public class HumanPlayer implements Player {

	private static int playerIDcounter = 1;

	private final int playerID = playerIDcounter++;
	private final String playerName;
	private final IOHandler ioHandler;
	
	public HumanPlayer(String playerName, IOHandler ioh) {
		this.playerName = playerName;
		this.ioHandler = ioh;
	}

	public String getName() {
		return playerName;
	}

	public ScoreCategory chooseScoreCategory() {
		return ioHandler.getScoreCategory();
	}

	public ScoreCategory chooseScoreCategoryAgain() {
		return ioHandler.chooseScoreCategoryAgain();
	}

	public DiceRoll rollDice(int diceCount) {
		return ioHandler.rollDice(diceCount);
	}

	public DiceRoll rerollDice(DiceRoll roll, int times) {
		return ioHandler.rerollDice(roll, times);	
	}

	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Player ");
		stringBuilder.append(playerName);
		stringBuilder.append(" (");
		stringBuilder.append(playerID);
		stringBuilder.append(")");
		return stringBuilder.toString();
	}

}
