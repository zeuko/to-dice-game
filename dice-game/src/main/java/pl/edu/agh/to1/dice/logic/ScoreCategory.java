package pl.edu.agh.to1.dice.logic;

import java.util.HashMap;
import java.util.Map;

public enum ScoreCategory {
	ONES, TWOS, THREES,	FOURS, FIVES, SIXES, THREE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, 
	LOW_STRAIGHT, HIGH_STRAIGHT, GENERAL, CHANCE;
	
	private final static Map<String, ScoreCategory> TYPE = new HashMap<String, ScoreCategory>();
	
	// MAP INITIALIZATION
	static {	TYPE.put("1", ONES);
				TYPE.put("2", TWOS);
				TYPE.put("3", THREES);
				TYPE.put("4", FOURS);
				TYPE.put("5", FIVES);
				TYPE.put("6", SIXES);
				TYPE.put("3ki", THREE_OF_A_KIND);
				TYPE.put("4ki", FOUR_OF_A_KIND);
				TYPE.put("f", FULL_HOUSE);
				TYPE.put("ms", LOW_STRAIGHT);
				TYPE.put("ds", HIGH_STRAIGHT);
				TYPE.put("g", GENERAL);
				TYPE.put("sz", CHANCE);
			}
	
	public static ScoreCategory getResult(String resultType) throws GameLogicException {
		if (TYPE.get(resultType) == null)
			throw new GameLogicException("invalid score category symbol");
		return TYPE.get(resultType);
	}
		
	public static int computePoints(DiceRoll diceRoll, ScoreCategory category) {
		switch (category) {
			case CHANCE:
				return diceRoll.sum();
			case ONES:
				return diceRoll.count(1)*1;
			case TWOS:
				return diceRoll.count(2)*2;
			case THREES:
				return diceRoll.count(3)*3;
			case FOURS:
				return diceRoll.count(4)*4;
			case FIVES:
				return diceRoll.count(5)*5;
			case SIXES:
				return diceRoll.count(6)*6;
			case THREE_OF_A_KIND:
				if (diceRoll.hasEqualUnique(3))
					return diceRoll.sum();
			case FOUR_OF_A_KIND:
				if (diceRoll.hasEqualUnique(4))
					return diceRoll.sum();
			case FULL_HOUSE:
				if (diceRoll.hasEqualUnique(2,3))
					return 25;
			case LOW_STRAIGHT:
				if (diceRoll.hasEqualUnique(1,1,1,1,1) && diceRoll.contains(1))
					return 30;
			case HIGH_STRAIGHT:
				if (diceRoll.hasEqualUnique(1,1,1,1,1) && diceRoll.contains(6))
					return 40;
			case GENERAL:
				if (diceRoll.hasEqualUnique(5))
					return 50;
			default:
				break;
		}

		return 0;
	}
	
}
