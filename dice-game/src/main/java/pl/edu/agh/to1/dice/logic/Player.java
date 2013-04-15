package pl.edu.agh.to1.dice.logic;

public class Player {
	
	private static int playerIDcounter = 0;
	
	private final int playerID = playerIDcounter++;
	private final String playerName;
	
	public Player(String playerName) {
		this.playerName = playerName;
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
	
	
}
