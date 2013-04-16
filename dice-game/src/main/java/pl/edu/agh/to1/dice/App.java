package pl.edu.agh.to1.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pl.edu.agh.to1.dice.logic.DiceGame;
import pl.edu.agh.to1.dice.logic.Player;


public class App {
    public static void main(String[] args) {
    	
    	int ile;
    	List<Player> players = new ArrayList<Player>();
    	System.out.print("Wpisz liczbê graczy: ");
    	Scanner s = new Scanner(System.in);
    	ile = s.nextInt();
    	
    	System.out.print("Imiona graczy: ");
    	for (int i = 0; i < ile; i++) {
    		players.add(new Player(s.next()));
    	}
    	s.close();
    	
        new DiceGame(players).play();
        
    }
}
