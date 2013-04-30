package pl.edu.agh.to1.dice.logic;

public interface Player {

	String getName();
	ScoreCategory chooseScoreCategory();
	ScoreCategory chooseScoreCategoryAgain();	// TODO cos z tym zrobic, bo nieladnie
	DiceRoll rollDice(int diceCount);
	DiceRoll rerollDice(DiceRoll diceRoll, int times);
}
