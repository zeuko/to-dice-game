package pl.edu.agh.to1.dice.view;

import pl.edu.agh.to1.dice.logic.DiceGame;

/**
 * 
 * @author Kacper
 * 
 *         Implement this interface to abstract away a particular method of
 *         loading game configuration (console, XML, etc)
 * 
 */
public interface GameConfigurator {
	DiceGame loadConfiguration();
}
