package pl.edu.agh.to1.dice;

import pl.edu.agh.to1.dice.logic.ConsoleGameConfigurator;
import pl.edu.agh.to1.dice.logic.DiceGame;
import pl.edu.agh.to1.dice.logic.GameConfigurator;


public class App {
    public static void main(String[] args) {
   	
    	// TODO: Wypadaloby tworzyc tablice wynikow gdzie indziej
    	
    	GameConfigurator configurator = new ConsoleGameConfigurator();
        DiceGame game = configurator.loadConfiguration();
        game.play();	
    }
}
