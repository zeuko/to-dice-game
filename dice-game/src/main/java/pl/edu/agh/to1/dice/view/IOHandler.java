package pl.edu.agh.to1.dice.view;

import java.util.List;

import pl.edu.agh.to1.dice.logic.DiceRoll;
import pl.edu.agh.to1.dice.logic.Player;
import pl.edu.agh.to1.dice.logic.ScoreCategory;

public interface IOHandler {

	public void showWinner(List<Player> winner, String finalTable);

	public void showTable(String string);

	public void showPoints(int i);

	public void newTurn(int turnNr, String string);

	public ScoreCategory getScoreCategory();

	public ScoreCategory chooseScoreCategoryAgain();

	public DiceRoll rollDice(int diceCount);

	public DiceRoll rerollDice(DiceRoll roll, int times);

	public void init();

}
