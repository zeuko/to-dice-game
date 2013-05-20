package pl.edu.agh.to1.dice.view;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import pl.edu.agh.to1.dice.logic.DiceRoll;
import pl.edu.agh.to1.dice.logic.Player;
import pl.edu.agh.to1.dice.logic.ScoreCategory;

public class SwingIOHandler implements IOHandler {

	public void showWinner(List<Player> winner, String finalTable) {
		// TODO Auto-generated method stub

	}

	public void showTable(String string) {
		// TODO Auto-generated method stub

	}

	public void showPoints(int i) {
		// TODO Auto-generated method stub

	}

	public void newTurn(int turnNr, String string) {
		// TODO Auto-generated method stub

	}

	public ScoreCategory getScoreCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	public ScoreCategory chooseScoreCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	public DiceRoll rollDice(int diceCount) {
		// TODO Auto-generated method stub
		return null;
	}

	public ScoreCategory chooseScoreCategoryAgain() {
		// TODO Auto-generated method stub
		return null;
	}

	public DiceRoll rerollDice(DiceRoll roll, int times) {
		// TODO Auto-generated method stub
		return null;
	}

	public void init() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShow();
			}
		});
	}
	
	private JFrame configurationWindow = new JFrame();
	public void createAndShow() {
		configurationWindow = new JFrame("DICE GAME");
		configurationWindow.setSize(600, 400);
		configurationWindow.setVisible(true);
	}
}