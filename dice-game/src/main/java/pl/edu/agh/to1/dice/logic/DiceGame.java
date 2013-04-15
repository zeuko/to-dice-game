package pl.edu.agh.to1.dice.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DiceGame {

	public static final int DICE_COUNT = 5;
	private final ScoreTable table;
	private final Scanner scanner = new Scanner(System.in);
	private final List<Player> players;

	public DiceGame(List<Player> players) {
		table = new ScoreTable(players);
		this.players = players;
	}

	public void play() {
		System.out.println("Playing Dice");

		for (int turnNr = 1; turnNr <= 13; turnNr++) {
			for (Player player : players) {
				table.printTable();
				System.out.println("TURN " + turnNr + "\n" + player.toString());
				Score currentTurnScore = takeTurn(player);
				System.out.println("Points awarded: " + currentTurnScore.getPoints());
				table.updateTable(player, currentTurnScore);
			}
		}
		
		System.out.println("------- FINAL RESULTS -------");
		table.printTable();
		List<Player> winner = table.getWinner();
		if (winner.size() == 1)
			System.out.println("The winner is: "+table.getWinner().get(0)+". Congratulations!");
		else
			System.out.println("There's a tie! Congratulations!");
		scanner.close();
	}

	private Score takeTurn(Player player) {

		DiceRoll roll = new DiceRoll(DICE_COUNT);

		System.out.println("Your dice roll:  " + roll.toString());
		rerollDice(roll);
		System.out.println("Your dice roll:  " + roll.toString());
		rerollDice(roll);
		System.out.println("Your dice roll:  " + roll.toString());

		ScoreCategory category = getScoreCategory();
		Score score = new Score(roll, category);

		while (!table.isLegal(player, score)) {
			System.out.print("Category already used! Try again:  ");
			category = getScoreCategory();
			score = new Score(roll, category);
		}
		return score;
	}

	private ScoreCategory getScoreCategory() {
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

	private boolean rerollDice(DiceRoll roll) {
		boolean done = false;
		System.out.print("Choose dice you want to freeze: ");
		while (!done) {
			try {
				String choice = scanner.nextLine().toLowerCase().trim();
				if (choice.equals("")) {
					return false;
				}
				List<Integer> c = new ArrayList<Integer>();
				for (String s : choice.split(",| ")) {
					c.add(Integer.parseInt(s.trim()));
				}
				roll.freeze(c);
				roll.roll();
				done = true;
			} catch (GameLogicException | NumberFormatException e) {
				System.out
						.print("Invalid dice given! Choose again, and check your choice twice before accepting: ");
				done = false;
			}
		}
		return true;
	}

}
