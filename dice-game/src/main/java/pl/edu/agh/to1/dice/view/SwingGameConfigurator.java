package pl.edu.agh.to1.dice.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import pl.edu.agh.to1.dice.logic.DiceGame;
import pl.edu.agh.to1.dice.logic.GameBuilder;

public class SwingGameConfigurator implements GameConfigurator {

	private JFrame configurationWindow;
	GameBuilder gameBuilder = new GameBuilder();
	private Lock lock = new ReentrantLock();
	private boolean gameDone;
	private Condition gameDoneC = lock.newCondition();
	private JCheckBox botOrNot1 = new JCheckBox();
	private JCheckBox botOrNot2 = new JCheckBox();
	private JTextField player1 = new JTextField(16);
	private JTextField player2 = new JTextField(16);

	public SwingGameConfigurator() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShow();
			}
		});
	}

	public void createAndShow() {
		configurationWindow = new JFrame("DICE GAME");
		configurationWindow.setSize(400, 400);
		configurationWindow.setVisible(true);

		
		// UPPER PANEL
		JPanel playersPanel = createPlayerChoicePanel();
		configurationWindow.add(playersPanel);
		
		
		// LOWER PANEL (BUTTON)
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createEmptyBorder());
		buttonPanel.setSize(400, 100);
		configurationWindow.add(buttonPanel, BorderLayout.SOUTH);

		JButton load = new JButton("Start Game");
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lock.lock();
				try {
					gameDone = true;
					gameDoneC.signal();
				} finally {
					lock.unlock();
				}
			}
		});
		load.setSize(new Dimension(180, 60));
		load.setPreferredSize(new Dimension(180, 60));
		buttonPanel.add(load);
		

	}

	private JPanel createPlayerChoicePanel() {
		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		panel.setLayout(layout);
		panel.setBorder(BorderFactory.createTitledBorder("Configure players"));
		
		JLabel mainLabel = new JLabel("Check box if you want a player to be bot :)");
		JLabel mainLabel2 = new JLabel("Please choose only one bot!");
		JLabel label1 = new JLabel("Player 1:");
		JLabel label2 = new JLabel("Player 2:");
		
		layout.setHorizontalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.LEADING)
								.addComponent(mainLabel)
								.addComponent(mainLabel2).addComponent(label1)
								.addComponent(label2))
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.LEADING)
								.addComponent(player1).addComponent(player2))
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.LEADING)
								.addComponent(botOrNot1)
								.addComponent(botOrNot2)));
		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup(GroupLayout.Alignment.CENTER)
								.addComponent(mainLabel))
				.addGroup(
						layout.createParallelGroup(GroupLayout.Alignment.CENTER)
								.addComponent(mainLabel2))
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(label1).addComponent(player1)
								.addComponent(botOrNot1))
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(label2).addComponent(player2)
								.addComponent(botOrNot2)));
		return panel;
	}

	public DiceGame loadConfiguration() {
		lock.lock();
		IOHandler ioh = new SwingIOHandler();
		try {
			while (!gameDone) {
				gameDoneC.await();
			}
			if (botOrNot1.isSelected()) {
				gameBuilder.addBot(player1.getText());
			} else {
				gameBuilder.addPlayer(player1.getText(), ioh);
			}

			if (botOrNot2.isSelected()) {
				gameBuilder.addBot(player2.getText());
			} else {
				gameBuilder.addPlayer(player2.getText(), ioh);
			}

		} catch (InterruptedException e) {
			Logger.getLogger(SwingGameConfigurator.class.getName()).log(
					Level.SEVERE, null, e);
		} finally {
			lock.unlock();
		}

		configurationWindow.dispose();
		return gameBuilder.create(ioh);
	}

}
