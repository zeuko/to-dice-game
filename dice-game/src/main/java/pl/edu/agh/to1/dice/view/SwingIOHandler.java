package pl.edu.agh.to1.dice.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import pl.edu.agh.to1.dice.logic.DiceRoll;
import pl.edu.agh.to1.dice.logic.GameLogicException;
import pl.edu.agh.to1.dice.logic.Player;
import pl.edu.agh.to1.dice.logic.ScoreCategory;

public class SwingIOHandler implements IOHandler {
    	
   	private boolean diceSaved = false;
	private Object waitForClick = new Object();
	private JFrame mainFrame = new JFrame();
	private JTextArea scoreField = new JTextArea();
	private JTextField newsField = new JTextField();
	private JPanel dicePanel = new JPanel();
	private JPanel scoreCatPanel = new JPanel();
	private JCheckBox diceCheckBoxes[] = new JCheckBox[5];
	ButtonGroup buttonCategoryGroup = new ButtonGroup();
	private int diceNr;
	private boolean choosingScoreCategory = false;
	private String currentPlayer = "";
    
	public void showWinner(List<Player> winner, String finalTable) {
		scoreField.setText(finalTable);
		mainFrame.repaint();
	}

	public void showTable(String table) {
		scoreField.setText(table);
		System.out.println(table);
		mainFrame.pack();
		mainFrame.repaint();
	}

	public void showPoints(int i) {
		// NOT NEEDED

	}

	public void newTurn(int turnNr, String playerName) {
		for(int i=0; i<diceNr; ++i){
		    diceCheckBoxes[i].setSelected(false);
		}
		currentPlayer = "Player " + playerName;

	}

	public ScoreCategory getScoreCategory() {
	    	synchronized(waitForClick) {
	    	    while(buttonCategoryGroup.getSelection() == null) {
	    		choosingScoreCategory = true;
    			try {
				waitForClick.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	    }
	    	    diceSaved = false;
	    	}
	    	choosingScoreCategory = false;
	    	
	    String command = buttonCategoryGroup.getSelection().getActionCommand();
		System.out.println("Chose " + command);
		buttonCategoryGroup.getSelection().setEnabled(false);
    	buttonCategoryGroup.clearSelection();
		return ScoreCategory.getCategoryMap().get(command);
	}

	public DiceRoll rollDice(int diceCount) {
	    	diceNr = diceCount;
		DiceRoll dr = new DiceRoll(diceCount);
		for(int i=0; i<diceCount; ++i) {
			diceCheckBoxes[i].setText(""+dr.getDiceValue(i));
		}
		newsField.setText(currentPlayer+ " 1 rzut");
		System.out.println("Rolled " + dr);
		mainFrame.repaint();
		synchronized(waitForClick) {
			while(!diceSaved) {
				try {
					waitForClick.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			diceSaved = false;
		}
		return dr;
	}

	public ScoreCategory chooseScoreCategoryAgain() {
		return getScoreCategory();
	}

	public DiceRoll rerollDice(DiceRoll roll, int times) {
	    	
	    	for(int j=0; j<times; ++j){
	    	    List<Integer> toFreeze = saveToFreeze(diceNr);
	    	    try{
	    		roll.freeze(toFreeze);
	    	    }catch( GameLogicException e ){
	    		System.out.println("GameLogicException");
	    	    }
	    	
	    	    roll.roll();
	    	    for(int i=0; i<5; ++i) {
	    			diceCheckBoxes[i].setText(""+roll.getDiceValue(i));
	    	    }
	    	    String text = currentPlayer + " " + (j+2) + " rzut";
	    	    newsField.setText(text);
	    	    mainFrame.repaint();
	    	    synchronized(waitForClick) {
	    		while(!diceSaved) {
	    			    try {
					waitForClick.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    		}
			diceSaved = false;
	    	    }
	    	    
	    	}
	    	newsField.setText(currentPlayer + " wybierz kategorie");
	    	mainFrame.repaint();
		return roll;
	}
	
	private List<Integer> saveToFreeze(int diceNr){
	    List<Integer> toFreeze = new LinkedList<Integer>();
	    for(int i=0; i<diceNr; ++i){
		if( diceCheckBoxes[i].isSelected() )
		    toFreeze.add(i+1);
	    }
	    return toFreeze;
	}

	public void init() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShow();
			}
		});
	}
	
	
	public void createAndShow() {
		mainFrame = new JFrame("DICE GAME");
		mainFrame.setSize(600, 500);
		BorderLayout mainPanelLayout = new BorderLayout();
		mainFrame.setLayout(mainPanelLayout);
		Container pane = mainFrame.getContentPane();
		pane.add(scoreField, BorderLayout.LINE_START);
		scoreField.setEditable(false);
		pane.add(newsField, BorderLayout.PAGE_START);
		newsField.setEditable(false);
		
		dicePanel.setLayout(new FlowLayout());
		for(int i=0; i<5; i++) {
			diceCheckBoxes[i] = new JCheckBox();
			diceCheckBoxes[i].setText("");
			dicePanel.add(diceCheckBoxes[i]);
		}
		JButton diceSelectedButton = new JButton("Go!");
		diceSelectedButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent actionEvent) {
				System.out.println("CLICK");
				synchronized(waitForClick) {
					diceSaved = true;
					
					waitForClick.notifyAll();
					if(!choosingScoreCategory)
					    System.out.println("Clicked! diceSaved=" + diceSaved);
				}
			}
			
		});
		dicePanel.add( diceSelectedButton );
		
		scoreCatPanel.setLayout(new BoxLayout(scoreCatPanel, BoxLayout.Y_AXIS));
		
		for( final Entry<String, ScoreCategory> entry : ScoreCategory.getCategoryMap().entrySet() ) {
			JRadioButton button = new JRadioButton(entry.getKey());
			button.setActionCommand(entry.getKey());
			scoreCatPanel.add( button );
			buttonCategoryGroup.add( button );
		}
		pane.add(dicePanel, BorderLayout.PAGE_END);
		pane.add(scoreCatPanel, BorderLayout.LINE_END);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
}