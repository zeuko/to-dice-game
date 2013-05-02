package pl.edu.agh.to1.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pl.edu.agh.to1.dice.logic.DiceGame;
import pl.edu.agh.to1.dice.logic.HumanPlayer;
import pl.edu.agh.to1.dice.logic.Player;


public class App {
    public static void main(String[] args) {
    	
    	
    	// TODO - kod ponizej wrzucic w GameStarter:
    	//			GameStarter (korzystajac z jakiegos IO handlera, jezeli chcemy miec kontakt ze swiatem zewnetrznym w jednym miejscu) 
    	//			zapyta o ilosc graczy, imiona, typ rozgrywki
    	//			stworzy graczy, tablice wynikow, stworzy gre i zwroci ja tutaj.
    	//			mysle ze od tego trzeba zaczac, i ze serializacje tez by wypadalo jakos tutaj zaczepic (skoro bedziemy miec tu tablice wynikow,
    	//			mozna jakos z niej wyciagnac punkty kazdego gracza i je zachowac)
    	
    	// ScoreTable jakos boje sie ruszac, zeby nie zepsuc ;P ~Kacper
    	
    	int ile;
    	List<Player> players = new ArrayList<Player>();
    	System.out.print("Wpisz liczbê graczy: ");
    	Scanner s = new Scanner(System.in);
    	ile = s.nextInt();
    	
    	System.out.print("Imiona graczy: ");
    	for (int i = 0; i < ile; i++) {
    		players.add(new HumanPlayer(s.next()));
    	}
    	
        new DiceGame(players).play();
        s.close();
    	
    }
}
