package pl.edu.agh.to1.dice.logic;


// tu mam problem - trzeba tego playera tak napisac, zeby niezaleznie od implementacji kazdy typ gracza gral
// wg takiej samej rozgrywki (czyli np. BotPlayer i HumanPlayer maja taka sama kolejnosc czynnosci w rollDice i rerollDice itp itd)

// dodac metode freezeDice() ? 

public interface Player {

	// getter
	String getName();
	
	/**
	 * ScoreCategory choice.
	 * @return chosen ScoreCategory
	 */
	ScoreCategory chooseScoreCategory();
	
	/**
	 * Next ScoreCategory choice. Probably should be removed. :P
	 * @return chosen ScoreCategory
	 */
	ScoreCategory chooseScoreCategoryAgain();	// TODO cos z tym zrobic, bo nieladnie
	
	/**
	 * Makes a roll with given number of dice.
	 * 
	 * @param diceCount	how many dice are supposed to be rolled
	 * @return DiceRoll object containing rolled dice
	 */
	DiceRoll rollDice(int diceCount);
	
	/**
	 * Similar to rollDice, but makes next roll based on given DiceRoll. Allows to
	 * provide additional implementation around rerolling dice (freezing).
	 * 
	 * @param DiceRoll object containing previously rolled dice
	 * @param times how many times roll is supposed to be repeated
	 */
	DiceRoll rerollDice(DiceRoll diceRoll, int times);
}
