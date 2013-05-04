package pl.edu.agh.to1.dice.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// brak d¿okerów
// zasady dla wielu kosci nieco zmodyfikowane: pominiete wymaganie dla generala 
//	 		(nie wymaga punktu: premie za kolejne wyrzucone genera³y po pierwszym obowi¹zuj¹ pod 
//				warunkiem, ¿e ¿aden z poprzednich genera³ów nie by³ zapisany do kategorii innej ni¿ "genera³", 
//				kiedy ta by³a wolna);
// premie: 	próg = 63*((scoresInCategory-1)/2) + 1;		// 1, 2 kosci: 63, 3,4 kosci: 126...	
//			punkty za bonus = 35*scoreInCategory;		


public class ScoreTable {
		
	private final Integer bonusThreshold;
	private final int bonusValue;
	private final int scoresInCategory;
	
	// mapa mapujaca mape na liste :P
	private final Map<Player, Map<ScoreCategory, List<Score>>> scoreTable = new HashMap<Player, Map<ScoreCategory, List<Score>>>();
	
	/**
	 * Total result from upper part of score table. 
	 */
	private final Map<Player, Integer> sumLowerTable = new HashMap<Player, Integer>();
	
	/**
	 * Total result from lower part of score table. 
	 */
	private final Map<Player, Integer> sumUpperTable = new HashMap<Player, Integer>();
	
	
	/**
	 * Creates new ScoreTable. Must be given list of players and number of scores allowed in single category.
	 * 
	 * @param players list of players
	 * @param scoreInCategory number of scores allowed in each category
	 */
	public ScoreTable(List<Player> players, int scoreInCategory) {
		this.scoresInCategory = scoreInCategory;
		this.bonusThreshold = 63*((scoresInCategory-1)/2) + 1;	// 1, 2 kosci: 63, 3,4 kosci: 126...	
		this.bonusValue = 35*scoresInCategory;
		
		for (Player player : players) {
			scoreTable.put(player, new HashMap<ScoreCategory, List<Score>>());
			for(ScoreCategory c : ScoreCategory.values()) {
				scoreTable.get(player).put(c, new ArrayList<Score>());
			}
			sumUpperTable.put(player, 0);
			sumLowerTable.put(player, 0);
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
		return scoreTable.get(player).get(points.getCategory()).size() < scoresInCategory;
	}
	
	
	/**
	 * Updates player's points in table assuming that operation is legal game move:
	 * requires calling isLegal(Player,Score) previously.
	 * When is't impossible to assign points to player passed as argument throws IllegalArgumentException.
	 * 
	 * @param player
	 * @param score
	 */
	public void updateTable(Player player, Score score) {
		
		if(!isLegal(player, score)) {
			throw new IllegalArgumentException("given score cannot be assigned to player - move is not legal");
		}
		
		scoreTable.get(player).get(score.getCategory()).add(score);	
		if (score.getCategory().ordinal() <= ScoreCategory.SIXES.ordinal()) {
			sumUpperTable.put(player, sumUpperTable.get(player) + score.getPoints());
		} else {
			if (score.getCategory() == ScoreCategory.GENERAL) {
				sumLowerTable.put(player, sumLowerTable.get(player) + score.getPoints()*scoreTable.get(player).get(score.getCategory()).size());
			} else {
				sumLowerTable.put(player, sumLowerTable.get(player) + score.getPoints());
			}
		}
	}
	
	/*
	 * A bit less mess. 
	 */
	public void printTable() {
		
		//
		String stringFormatFixed = "%15s";
		String stringFormatAdjustable = "%"+(1+scoresInCategory*4)+"s";
		String integerFormat = "%"+(1+scoresInCategory*4)+"d";
		
		// line of proper length, for neat table look
		String line = "\n---------------";
		for(int i = 0; i <= scoreTable.size(); i++) 	{ line += "----------";	}
		for(int i = 0; i <= scoresInCategory; i++) 		{ line += "----";		}
		
		// header (category & players)
		System.out.println(line);
		System.out.format(stringFormatFixed, "category".toUpperCase());
		for (Player p : scoreTable.keySet()) {
			System.out.format(stringFormatAdjustable, p.getName());
		}
		System.out.println(line);
		
		// categories & scores
		for (ScoreCategory c : ScoreCategory.values()) {
			System.out.format(stringFormatFixed, c.toString().toLowerCase().replaceAll("_", " "));
			for (Player p : scoreTable.keySet()) {
				System.out.format(stringFormatAdjustable, getPointsOnly(p, c));
			}
			
			if (c == ScoreCategory.SIXES) {
				System.out.println(line);
				System.out.format(stringFormatFixed, "bonus");
				for (Player p : scoreTable.keySet()) {
					System.out.format(integerFormat, getBonusPointsIfShould(p));
				}
				
				System.out.println(line);
				System.out.format(stringFormatFixed, "sum");
				for (Player p : scoreTable.keySet()) {
					System.out.format(integerFormat,sumUpperTable.get(p) + getBonusPointsIfShould(p));
				}
				System.out.print(line);
			
			}
			
			System.out.println();
		}
		
		System.out.println(line);
		System.out.format(stringFormatFixed, "sum");
		for (Player p : scoreTable.keySet()) {
			System.out.format(integerFormat,sumLowerTable.get(p));
		}
		
		System.out.println(line);
		System.out.format(stringFormatFixed, "total");
		for (Player p : scoreTable.keySet()) {
			System.out.format(integerFormat, computeTotal(p));
		}
		
		System.out.println(line);
		System.out.println();
	}

	/**
	 * Gets string containing specified player's points in given category,
	 * formatted in appropriate way (ex. "12  0  3", when there are three scores in single category  allowed)
	 * 
	 * @param p
	 * @param c
	 * @return
	 */
	private String getPointsOnly(Player p, ScoreCategory c) {
		List<Score> pointsList = scoreTable.get(p).get(c);
		StringBuilder pointsBuilder = new StringBuilder("");
		for (Score s : pointsList)
			pointsBuilder.append(s.getPoints()).append("  ");
		return pointsBuilder.toString().trim();
	}

	/**
	 * 
	 * @return
	 */
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
		return sumLowerTable.get(p)+sumUpperTable.get(p)+getBonusPointsIfShould(p);
	}
	
	private int getBonusPointsIfShould(Player p) {
		return ((sumUpperTable.get(p) > bonusThreshold) ? bonusValue : 0);
	}
	
}
	

