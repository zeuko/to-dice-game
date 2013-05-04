package pl.edu.agh.to1.dice.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HumanPlayer implements Player {

	private static int playerIDcounter = 1;

	private final int playerID = playerIDcounter++;
	private final String playerName;

	// TODO wrzucic to w IO handler 
	private Scanner scanner = new Scanner(System.in);

	public HumanPlayer(String playerName) {
		this.playerName = playerName;
	}

	public ScoreCategory chooseScoreCategory() {
		System.out.println("Categories: 1 2 3 4 5 6 3ki 4ki f ms ds g sz");
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

	public String getName() {
		return playerName;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Player ");
		stringBuilder.append(playerName);
		stringBuilder.append(" (");
		stringBuilder.append(playerID);
		stringBuilder.append(")");
		return stringBuilder.toString();
	}

	public ScoreCategory chooseScoreCategoryAgain() {
		System.out.println("Invalid choice.");
		return chooseScoreCategory();
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

}
