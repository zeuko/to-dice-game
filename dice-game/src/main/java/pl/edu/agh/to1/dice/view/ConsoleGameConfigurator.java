package pl.edu.agh.to1.dice.view;

import java.util.Scanner;

import pl.edu.agh.to1.dice.logic.DiceGame;
import pl.edu.agh.to1.dice.logic.GameBuilder;

public class ConsoleGameConfigurator implements GameConfigurator {
	private GameBuilder gameBuilder = new GameBuilder();
	private Scanner scanner = new Scanner(System.in);

	public DiceGame loadConfiguration() {
		System.out.println("Welcome to DiceGame!");
		DiceGame game = null;
		printHelp();
		String cmd;
		IOHandler ioh = new ConsoleIOHandler();
		do {
			cmd = scanner.next();
			if (cmd.equals("player")) {
				gameBuilder.addPlayer(scanner.next(), ioh);
			} else if (cmd.equals("bot")) {
				gameBuilder.addBot(scanner.next());

				// TODO: zmienic dice i category na wybor trybu: zwykle/potrojne
				// kosci
			} else if (cmd.equals("dice")) {
				gameBuilder.setDiceCount(scanner.nextInt());
			} else if (cmd.equals("category")) {
				gameBuilder.setScoresPerCategory(scanner.nextInt());
			} else if (cmd.equals("start")) {
				game = gameBuilder.create(ioh);
			} else if (cmd.equals("-h")) {
				printHelp();
			} else {
				System.out
						.println("Unsupported operation! Type -h to get help.");
			}
		} while (!cmd.equals("start"));

		return game;
	}

	private void printHelp() {
		System.out
				.println("player player_name - adds a player with player_name name");
		System.out.println("bot bot_name - adds a bot with bot_name name");
		System.out
				.println("dice n - changes dice count of each player to n (default "
						+ gameBuilder.getDefaultDiceCount() + ")");
		System.out
				.println("category n - changes score per category to n (default "
						+ gameBuilder.getDefaultScoresPerCategory() + ")");
		System.out.println("start - to start the game.");
	}

}
