package pl.edu.agh.to1.dice;

import pl.edu.agh.to1.dice.logic.DiceGame;
import pl.edu.agh.to1.dice.view.GameConfigurator;
import pl.edu.agh.to1.dice.view.SwingGameConfigurator;


public class App {
    public static void main(String[] args) {
   	
    	GameConfigurator configurator = new SwingGameConfigurator();
    	DiceGame game = configurator.loadConfiguration();
        game.play();	
    }
}
