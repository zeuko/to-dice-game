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
    	//			GameStarter (korzystajac z jakiegos IO handlera) zapyta o ilosc graczy, imiona, typ rozgrywki
    	//			stworzy graczy, stworzy gre i zwroci ja tutaj
    	
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
