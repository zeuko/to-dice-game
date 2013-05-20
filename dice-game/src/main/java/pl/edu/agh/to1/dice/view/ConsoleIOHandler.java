package pl.edu.agh.to1.dice.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pl.edu.agh.to1.dice.logic.DiceRoll;
import pl.edu.agh.to1.dice.logic.GameLogicException;
import pl.edu.agh.to1.dice.logic.Player;
import pl.edu.agh.to1.dice.logic.ScoreCategory;

public class ConsoleIOHandler implements IOHandler {
	
	private Scanner scanner = new Scanner(System.in);

	public void showTable(String table) {
		System.out.println(table);
	}

	public void showPoints(int points) {
		System.out.println("Points awarded: "+points);
		
	}

	public void newTurn(int turnNr, String playerName) {
		System.out.println("TURN " + turnNr + "\n" + playerName);
	}

	public ScoreCategory getScoreCategory() {
		System.out.println("Categories: 1 2 3 4 5 6 3ki 4ki f ms ds g sz parz nieparz");
		System.out.print("Your choice:  ");
		ScoreCategory sc = null;
		boolean done = false;
		while (!done) {
			try {
				String choice = scanner.nextLine().toLowerCase().trim();
				sc = ScoreCategory.getResult(choice);
				done = true;
			} catch (GameLogicException e) {
				System.out.println("Unknown option, try again: ");
				done = false;
			}
		}
		return sc;

	}

	public ScoreCategory chooseScoreCategoryAgain() {
		System.out.println("Invalid category, choose again!");
		return getScoreCategory();
	}

	public DiceRoll rollDice(int diceCount) {
		DiceRoll dr = new DiceRoll(diceCount);
		System.out.println("Your roll: "+ dr.toString());
		return dr;
	}

	public DiceRoll rerollDice(DiceRoll roll, int times) {
		for (int i = 0; i < times; i++) {
			boolean done = false;
			System.out.print("Choose dice you want to freeze: ");
			while (!done) {
				try {
					String choice = scanner.nextLine().toLowerCase().trim();
					if (choice.equals("")) {
						done = true;
						roll.roll();
						break;
					}
					List<Integer> c = new ArrayList<Integer>();
					for (String s : choice.split(",| ")) {
						c.add(Integer.parseInt(s.trim()));
					}
					roll.freeze(c);
					roll.roll();
					done = true;
				} catch (GameLogicException e) {
					System.out
							.print("Invalid dice given! Choose again, and check your choice twice before accepting: ");
					done = false;
				} catch (NumberFormatException e) {
					System.out
							.print("Invalid dice given! Choose again, and check your choice twice before accepting: ");
					done = false;
				}
			}
			System.out.println("Your roll: "+ roll.toString());
		}
		return roll;

	}

	public void showWinner(List<Player> winner, String finalTable) {
		System.out.println("------- FINAL RESULTS -------");
		System.out.println(finalTable);
		if (winner.size() == 1)
			System.out.println("The winner is: " + winner.get(0)
					+ ". Congratulations!");
		else {
			System.out.println("There's a tie! Congratulations!");
			System.out.print("The winner are: \n");
			for(Player p : winner) {
				System.out.print("\t"+p+ " ");
			}
		}
	}

	public void init() {
		// TODO Auto-generated method stub
		
	}


}
