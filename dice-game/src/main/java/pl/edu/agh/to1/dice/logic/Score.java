package pl.edu.agh.to1.dice.logic;

public class Score {
	
	private final DiceRoll diceRoll;
	private final ScoreCategory category;
	private final int score;
	
	public Score(DiceRoll diceRoll, ScoreCategory category) {
		this.diceRoll = diceRoll;
		this.category = category;
		score = ScoreCategory.computePoints(diceRoll, category);
	}
	
	public ScoreCategory getCategory() {
		return category;
	}

	public int getPoints() {
		return score;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("points for roll ");
		s.append(diceRoll.toString());
		s.append(" in category ");
		s.append(category);
		s.append(": ");
		s.append(score);
		return s.toString();
	}
	
}
