package pl.edu.agh.to1.dice.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// brak d�oker�w
// zasady dla wielu kosci nieco zmodyfikowane: pominiete wymaganie dla generala 
//	 		(nie wymaga punktu: premie za kolejne wyrzucone genera�y po pierwszym obowi�zuj� pod 
//				warunkiem, �e �aden z poprzednich genera��w nie by� zapisany do kategorii innej ni� "genera�", 
//				kiedy ta by�a wolna);
// premie: 	pr�g = 63*((scoresInCategory-1)/2) + 1;		// 1, 2 kosci: 63, 3,4 kosci: 126...	
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
	
	@Override
	public String toString() {
		
		StringBuilder s = new StringBuilder();
		//
		String stringFormatFixed = "%15s";
		String stringFormatAdjustable = "%"+(1+scoresInCategory*4)+"s";
		String integerFormat = "%"+(1+scoresInCategory*4)+"d";
		String newline = "\n";
		// line of proper length, for neat table look
		String line = "\n---------------";
		for(int i = 0; i <= scoreTable.size(); i++) { line += "----------";	}
		for(int i = 0; i <= scoresInCategory; i++) { line += "----";	}
		
		// header (category & players)
		s.append(line);
		s.append(newline);
		
		s.append(String.format(stringFormatFixed, "category".toUpperCase()));
		for (Player p : scoreTable.keySet()) {
			s.append(String.format(stringFormatAdjustable, p.getName()));
		}
		
		s.append(line);
		s.append(newline);
		
		// categories & scores
		for (ScoreCategory c : ScoreCategory.values()) {
			s.append(String.format(stringFormatFixed, c.toString().toLowerCase().replaceAll("_", " ")));
			for (Player p : scoreTable.keySet()) {
				s.append(String.format(stringFormatAdjustable, getPointsOnly(p, c)));
			}
			
			if (c == ScoreCategory.SIXES) {
				s.append(line);
				s.append(newline);
				s.append(String.format(stringFormatFixed, "bonus"));
				for (Player p : scoreTable.keySet()) {
					s.append(String.format(integerFormat, getBonusPointsIfDue(p)));
				}
				s.append(line);
				s.append(newline);
				s.append(String.format(stringFormatFixed, "sum"));
				for (Player p : scoreTable.keySet()) {
					s.append(String.format(integerFormat,sumUpperTable.get(p) + getBonusPointsIfDue(p)));
				}
				s.append(line);
			
			}
			
			s.append(newline);
		}
		
		s.append(line);
		s.append(newline);
		s.append(String.format(stringFormatFixed, "sum"));
		for (Player p : scoreTable.keySet()) {
			s.append(String.format(integerFormat, sumLowerTable.get(p)));
		}
		s.append(line);
		s.append(newline);
		s.append(String.format(stringFormatFixed, "total"));
		for (Player p : scoreTable.keySet()) {
			s.append(String.format(integerFormat, computeTotal(p)));
		}
		s.append(line);
		s.append(newline);
		return s.toString();
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
		return sumLowerTable.get(p)+sumUpperTable.get(p)+getBonusPointsIfDue(p);
	}
	
	/**
	 * Returns bonus points for player passed as argument, or 0 if player
	 * doesn't meet the requirements for receiving bonus.
	 * 
	 * @param p
	 * @return
	 */
	private int getBonusPointsIfDue(Player p) {
		return ((sumUpperTable.get(p) > bonusThreshold) ? bonusValue : 0);
	}
	
}
	

