package pl.edu.agh.to1.dice.logic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BotPlayer implements Player {

	private final String playerName;
	private final boolean isThree; // od razu kategorie ograniczam do dwoch
	private int diceCount;
	private Map<ScoreCategory, Integer> used = new HashMap<ScoreCategory, Integer>();
	DiceRoll roll;

	public BotPlayer(String playerName, boolean isThree) {
		this.playerName = playerName;
		this.isThree = isThree;
		init();
	}

	public String getName() {
		return playerName;
	}

	public ScoreCategory chooseScoreCategory() {
		ScoreCategory sc = findMax(roll);
		if (sc == null)
			sc = findAvailable();
		int v = used.get(sc);
		used.put(sc, ++v);
		System.out.println(sc);
		return sc;
	}

	public ScoreCategory findAvailable() {
		int limit = 1;
		if (isThree)
			limit = 3;

		Set<ScoreCategory> figures = used.keySet();
		for (ScoreCategory f : figures)
			if (used.get(f) < limit)
				return f;
		return null;
	}

	public ScoreCategory chooseScoreCategoryAgain() {
		System.out.print("Trouble");
		try {
			Thread.sleep(10000);
		} catch (Exception e) {

		}
		return chooseScoreCategory();
	}

	public DiceRoll rollDice(int diceCount) {
		this.diceCount = diceCount;
		DiceRoll dr = new DiceRoll(diceCount);
		System.out.println("His roll: " + dr.toString());
		return dr;
	}

	public DiceRoll rerollDice(DiceRoll diceRoll, int times) {
		int limit = 1;
		if (isThree)
			limit = 3;

		for (int i = 0; i < times; i++) {
			ScoreCategory sc = findMax(diceRoll);
			if (sc != null && used.get(sc) < limit) { // oplacalnosc?
				System.out.println("His roll: " + diceRoll.toString());
				this.roll = diceRoll;
				return diceRoll;
			}

			List<Integer> toFreeze = findToFreeze(diceRoll);
			try {
				diceRoll.freeze(toFreeze);
			} catch (GameLogicException e) {
				System.out.print("Caught GameLogicExeption");
			}
			diceRoll.roll();
		}
		System.out.println("His roll: " + diceRoll.toString());
		this.roll = diceRoll;
		return diceRoll;
	}

	private List<Integer> findToFreeze(DiceRoll roll) {
		List<Integer> toFreeze = new LinkedList<Integer>();
		for (int i = 1; i < 6; ++i) {
			if (roll.count(i) > 0) {
				List<Integer> toAdd = findIndexes(roll, i);
				toFreeze.addAll(toAdd);
			}
		}
		return toFreeze;
	}

	private List<Integer> findIndexes(DiceRoll roll, int pipes) {
		List<Integer> indexes = new LinkedList<Integer>();
		for (int i = 0; i < this.diceCount; ++i) {
			if (roll.getDiceValue(i) == pipes)
				indexes.add(i + 1);
		}
		return indexes;
	}

	private ScoreCategory findMax(DiceRoll roll) {
		Set<ScoreCategory> figures = used.keySet();

		int limit = 1;
		if (isThree)
			limit = 3;

		ScoreCategory max = null;
		int maxValue = 0;

		for (ScoreCategory f : figures) {
			int value = ScoreCategory.computePoints(roll, f);
			if (value > maxValue && used.get(f) < limit) {
				max = f;
				maxValue = value;
			}
		}
		return max;
	}

	private void init() {
		used.put(ScoreCategory.ONES, 0);
		used.put(ScoreCategory.TWOS, 0);
		used.put(ScoreCategory.THREES, 0);
		used.put(ScoreCategory.FOURS, 0);
		used.put(ScoreCategory.FIVES, 0);
		used.put(ScoreCategory.SIXES, 0);
		used.put(ScoreCategory.THREE_OF_A_KIND, 0);
		used.put(ScoreCategory.FOUR_OF_A_KIND, 0);
		used.put(ScoreCategory.FULL_HOUSE, 0);
		used.put(ScoreCategory.LOW_STRAIGHT, 0);
		used.put(ScoreCategory.HIGH_STRAIGHT, 0);
		used.put(ScoreCategory.GENERAL, 0);
		used.put(ScoreCategory.CHANCE, 0);
		used.put(ScoreCategory.EVEN, 0);
		used.put(ScoreCategory.ODD, 0);
	}

	@Override
	public String toString() {
		return "Bot ";
	}

}
