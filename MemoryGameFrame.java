package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MemoryGameFrame extends JFrame {
	
	private JPanel jpMain;
	private MemoryBoard jpTTTBoard;
	private ScoreBoard scoreBoard;
	private Player currPlayer, player1, player2;
	
	public MemoryGameFrame(){
		player1 = new Player("Wonderwoman");
		player2 = new Player("Superman");
		currPlayer = player1;
		
		jpMain = new JPanel();
		jpMain.setLayout(new BorderLayout());
		
		
		jpTTTBoard = new MemoryBoard();
		scoreBoard = new ScoreBoard();
		
		jpMain.add(jpTTTBoard, BorderLayout.CENTER);
		jpMain.add(scoreBoard, BorderLayout.NORTH);
		
		
		add(jpMain);
		setSize(500,500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}//closing bracket TicTacToe_GUI constructor
	
	private class ScoreBoard extends JPanel {
		private JLabel player1score, player2score;
		
		private ScoreBoard() {
			setLayout(new GridLayout(1, 2));
			player1score = new JLabel("Wonderwoman: 0");
			player2score = new JLabel("Superman: 0");
			add(player1score);
			add(player2score);
		}
		
		private void updateScoreLabels() {
			if(currPlayer.equals(player1)){
				scoreBoard.player1score.setText(String.format("Wonderwoman: " + player1.getPlayerScore()));
			}
			else if(currPlayer.equals(player2)){
				scoreBoard.player2score.setText(String.format("Superman: " + player2.getPlayerScore()));
			}	
		}
	}
	
	private class MemoryBoard extends JPanel implements GameBoardInterface, ActionListener{
		private JButton [][] board;
		private int numClicks = 0;
		private static final int NUM_ROWS = 4;
		private static final int NUM_COLS = 4;
		private static ArrayList<JButton> matches = new ArrayList<JButton>();
		private static final int NUM_MATCHES_NEEDED_TO_WIN = 3;
		
		private MemoryBoard(){
			setLayout(new GridLayout(NUM_ROWS,NUM_COLS));
			populateBoard();
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btnClicked = (JButton) e.getSource();

			if (numClicks%2==0 && numClicks > 0) {
				ArrayList<JButton> pickedLetters = currPlayer.getPickedLetters();
				
				//getting the current letters picked to compare
				String letter1 = pickedLetters.get(0).getText();
				String letter2 = pickedLetters.get(1).getText();

				if (letter1 == letter2) { //if there is a match
					currPlayer.incrementScore();
					matches.add(pickedLetters.get(0));
					matches.add(pickedLetters.get(1));
					
					//update score labels
					scoreBoard.updateScoreLabels();
					
				} else { //otherwise, clear the board of non-matching pairs
					clearNonMatches();
				}
				
				//whether there was a match or not, the currPlayer pickedLetters should be cleared and turn switches
				currPlayer.clearPickedLetters();
				System.out.println(currPlayer.getPlayerSymbol() + " - " + currPlayer.getPlayerScore());
				takeTurns();
			}
			
			//if numClicks is not a multiple of two, we should add that guess to the pickedLetters arrayList
			currPlayer.addPickedLetters(btnClicked);
			btnClicked.setEnabled(false);
			numClicks++;			

			
			//check to see if the board is full-> this implies that someone just guessed the last pair
			//so we need to increment their score and display the results
			if(isFull()) {
				//display the results
				currPlayer.incrementScore();
				displayResults();
			}
		}
		
		private void clearNonMatches() {
			for(int row=0; row<board.length; row++){
				for(int col=0; col < board[row].length; col++){
					if(!matches.contains(board[row][col])){
						board[row][col].setForeground(Color.WHITE);
							board[row][col].setEnabled(true);
					}
				}
			}
		}

		@Override
		public void populateBoard() {
			String [] letters = {"a", "b", "c", "d", "e", "f", "g", "h"};
			ArrayList<String> list = new ArrayList<String>();
			
			for (int i = 0; i < letters.length; i++) {
				list.add(letters[i]);
				list.add(letters[i]);
			} //{"a", "a", "b", "b"....}
			
			Collections.shuffle(list);
			
			board = new JButton[NUM_ROWS][NUM_COLS];
			//iterate through, creating and adding the buttons
			for(int row=0; row<board.length; row++){
				for(int col=0; col < board[row].length; col++){
					
					board[row][col] = new JButton(list.get(0));
					list.remove(0);

					Font bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 36);
					board[row][col].setForeground(Color.WHITE);
					board[row][col].setFont(bigFont);
					board[row][col].addActionListener(this);//respond to clicks :-)
					board[row][col].setEnabled(true);

					this.add(board[row][col]);//add current JButton to the TicTacToeBoard JPanel

				}
			}
		}


		@Override
		public void clearBoard() {
			// TODO Auto-generated method stub
			//iterate through and board[row][col].setText("")
			for(int row=0; row<board.length; row++){
				for(int col=0; col < board[row].length; col++){
					board[row][col].setText("");
					board[row][col].setEnabled(true);
				}
			}
			
		}

		@Override
		public void takeTurns() {
			if(currPlayer.equals(player1)){
				currPlayer = player2;
			}
			else{
				currPlayer = player1;
			}
			
		}

		@Override
		public boolean isFull() {
			for(int row=0; row<board.length; row++){
				for(int col=0; col<board[row].length; col++){
					if(board[row][col].isEnabled()){
						return false;//found an enabled cell... game can continue
					}
				}
			}
			return true;
		}

		@Override
		public void displayResults() {
			
			if(player1.getPlayerScore() > player2.getPlayerScore()) {
				String result = String.format("Results: \nWonderwoman: %s Superman: %s\nWINNER: Wonderwoman!", player1.getPlayerScore(), player2.getPlayerScore());
				JOptionPane.showMessageDialog(null, result);
			} else if (player1.getPlayerScore() < player2.getPlayerScore()) {
				String result = String.format("Results: \nWonderwoman: %s Superman: %s\nWINNER: Superman!", player1.getPlayerScore(), player2.getPlayerScore());
				JOptionPane.showMessageDialog(null, result);
			} else {
				String result = String.format("Results: \nWonderwoman: %s Superman: %s\nIt's a TIE!", player1.getPlayerScore(), player2.getPlayerScore());
				JOptionPane.showMessageDialog(null, result);
			}
			
		}
		
		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean isWinner() {
			
			return false;//NOT WINNER in ANY ROW,COL,MAIN, or SECONDARY DIAGONAL
		}
		
		@Override
		public void displayBoard() {
			
			
		}

		
	}//closing bracket for inner TicTacToeBoard class
	
}
