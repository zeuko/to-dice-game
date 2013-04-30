package pl.edu.agh.to1.dice.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreTable {
		
	private final int scores_in_category;
	private final Map<Player, Map<ScoreCategory, List<Score>>> scoreTable = new HashMap<Player, Map<ScoreCategory, List<Score>>>();
	private final Map<Player, Integer> sumTableUp = new HashMap<Player, Integer>();
	private final Map<Player, Integer> sumTableDown = new HashMap<Player, Integer>();
	
	public ScoreTable(List<Player> players, int scoreInCategory) {
		scores_in_category = scoreInCategory;
		for (Player player : players) {
			scoreTable.put(player, new HashMap<ScoreCategory, List<Score>>());
			for(ScoreCategory c : ScoreCategory.values()) {
				scoreTable.get(player).put(c, new ArrayList<Score>());
			}
			sumTableDown.put(player, 0);
			sumTableUp.put(player, 0);
		}
	}
	
	/**
	 * Informs if given player can be awarded with given type of points.
	 * 
	 * @param player  
	 * @param points
	 * @return 
	 */
	public boolean isLegal(Player player, Score points) {
		return scoreTable.get(player).get(points.getCategory()).size() < scores_in_category;
	}
	
	/**
	 * Updates player's points in table.
	 * 
	 * @param player
	 * @param score
	 */
	public void updateTable(Player player, Score score) {
		scoreTable.get(player).get(score.getCategory()).add(score);	
		if (score.getCategory().ordinal() <= ScoreCategory.SIXES.ordinal()) {
			sumTableDown.put(player, sumTableDown.get(player) + score.getPoints());
		} else {
			sumTableUp.put(player, sumTableUp.get(player) + score.getPoints());
		}
	}
	
	public void printTable() {
		String line = "\n---------------";
		for(int i = 0; i <= scoreTable.size(); i++) { line += "----------";	}
		for(int i = 0; i <= scores_in_category; i++) { line += "----";	}
		
		System.out.println(line);
		System.out.format("%15s", "category".toUpperCase());
		for (Player p : scoreTable.keySet()) {
			System.out.format("%"+(4+scores_in_category*4)+"s", p.getName());
		}
		
		System.out.println(line);
		
		for (ScoreCategory c : ScoreCategory.values()) {
			System.out.format("%15s", c.toString().toLowerCase().replaceAll("_", " "));
			for (Player p : scoreTable.keySet()) {
				System.out.format("%"+scores_in_category*4+"s", getPointsOnly(p, c));
			}
			
			if (c == ScoreCategory.SIXES) {
				
				System.out.println(line);
				System.out.format("%15s", "bonus");
				for (Player p : scoreTable.keySet()) {
					System.out.format("%10d",sumTableDown.get(p) > 63 ? 35 : 0);
				}
				
				System.out.println(line);
				System.out.format("%15s", "sum");
				for (Player p : scoreTable.keySet()) {
					System.out.format("%10d",sumTableDown.get(p));
				}
				System.out.print(line);
			
			}
			
			System.out.println();
		}
		
		System.out.println(line.trim());
		System.out.format("%15s", "sum");
		for (Player p : scoreTable.keySet()) {
			System.out.format("%10d",sumTableUp.get(p));
		}
		
		System.out.println(line);
		System.out.format("%15s", "total");
		for (Player p : scoreTable.keySet()) {
			System.out.format("%10d", computeTotal(p));
		}
		
		System.out.println(line);
		System.out.println();
	}

	private String getPointsOnly(Player p, ScoreCategory c) {
		List<Score> pointsList = scoreTable.get(p).get(c);
		StringBuilder pointsBuilder = new StringBuilder("");
		for (Score s : pointsList)
			pointsBuilder.append(s.getPoints()).append(" ");
		return pointsBuilder.toString().trim();
	}

	public List<Player> getWinner() {
		List<Player> winner = new ArrayList<Player>();
		int currMaxPoints = 0;
		
		for (Player p : scoreTable.keySet()) {
			int playerScore = computeTotal(p);
			if (playerScore > currMaxPoints) {
				winner.clear();
				winner.add(p);
				currMaxPoints = playerScore;
			} else if (playerScore == currMaxPoints) {
				winner.add(p);
			}
		}
		
		return winner;
	}
	
	private int computeTotal(Player p) {
		return sumTableUp.get(p)+sumTableDown.get(p)+ ((sumTableDown.get(p) > 63) ? 35 : 0);
	}
	
}
	

