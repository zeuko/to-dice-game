package pl.edu.agh.to1.dice;

import pl.edu.agh.to1.dice.logic.ConsoleGameConfigurator;
import pl.edu.agh.to1.dice.logic.DiceGame;
import pl.edu.agh.to1.dice.logic.GameConfigurator;


public class App {
    public static void main(String[] args) {
   	
    	// TODO - kod ponizej wrzucic w GameStarter:
    	//			GameStarter (korzystajac z jakiegos IO handlera, jezeli chcemy miec kontakt ze swiatem zewnetrznym w jednym miejscu) 
    	//			zapyta o ilosc graczy, imiona, typ rozgrywki
    	//			stworzy graczy, tablice wynikow, stworzy gre i zwroci ja tutaj.
    	//			mysle ze od tego trzeba zaczac, i ze serializacje tez by wypadalo jakos tutaj zaczepic (skoro bedziemy miec tu tablice wynikow,
    	//			mozna jakos z niej wyciagnac punkty kazdego gracza i je zachowac)
    	// TODO: Historia gier, tworzenie tablicy wynikow?, bot
    	
    	GameConfigurator configurator = new ConsoleGameConfigurator();
        DiceGame game = configurator.loadConfiguration();
        game.play();	
    }
}
