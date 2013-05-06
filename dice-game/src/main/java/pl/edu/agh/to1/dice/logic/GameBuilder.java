package pl.edu.agh.to1.dice.logic;

import java.util.LinkedList;
import java.util.List;

public class GameBuilder {
	private List<Player> players = new LinkedList<Player>();
	private final int DEFAULT_REROLL_TIMES = 2;
	private final int DEFAULT_DICE_COUNT = 5;
	private final int DEFAULT_SCORES_PER_CATEGORY = 1;
	private int rerollTimes = DEFAULT_REROLL_TIMES;
	private int diceCount = DEFAULT_DICE_COUNT;
	private int scoresPerCategory = DEFAULT_SCORES_PER_CATEGORY;
	
	public GameBuilder addPlayer(String playerName) {
		players.add( new HumanPlayer(playerName) );
		return this;
	}
	
	public GameBuilder addBot(String botName) { 
	    //zakladam, ze wariant gry wybrany przed tworzeniem botow
	    	if( scoresPerCategory == DEFAULT_SCORES_PER_CATEGORY)
	    	    players.add( new BotPlayer(botName, false) );
	    	else
	    	    players.add( new BotPlayer(botName, true) );
	    	
		return this;
		
	}
	
	public int getDefaultRerollTimes() {
		return DEFAULT_REROLL_TIMES;
	}
	
	public int getDefaultDiceCount() {
		return DEFAULT_DICE_COUNT;
	}
	
	public int getDefaultScoresPerCategory() {
		return DEFAULT_SCORES_PER_CATEGORY;
	}
	
	public GameBuilder setRerollTimes(int times) {
		rerollTimes = times;
		return this;
	}
	
	public GameBuilder setDiceCount(int diceCount) {
		this.diceCount = diceCount;
		return this;
	}
	
	public GameBuilder setScoresPerCategory(int scoresPerCategory) {
		this.scoresPerCategory = scoresPerCategory;
		return this;
	}
	
	public DiceGame create() {
		return new DiceGame(players, diceCount, rerollTimes, scoresPerCategory);
	}
}
