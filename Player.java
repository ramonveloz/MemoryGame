package gui;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;

public class Player implements Comparable<Player> {
	private String playerSymbol;
	private String playerName;
	private int score;
	private ArrayList<JButton> pickedLetters = new ArrayList<JButton>();
	
	public Player(){
		playerSymbol = " * ";
		playerName = "Play Doe";
		score = 0;
	}
	public Player(String name){
		this();
		if(name !=null){
			playerName = name;
			playerSymbol = ""+name.charAt(0);
		}
	}

	public Player(String symbol, String name){
		this();
		if(name !=null){
			playerName = name;
			playerSymbol = symbol;
		}
	}
	
	protected void addPickedLetters(JButton e) {
		pickedLetters.add(e);
	}

	protected void incrementScore(){
		score++;
	}
	
	protected void clearPickedLetters() {
		pickedLetters.clear();
	}
	protected ArrayList<JButton> getPickedLetters() {
		return pickedLetters;
	}

	public int getPlayerScore(){
		return score;
	}
	public String getPlayerSymbol(){
		return playerSymbol;
	}
	public String getPlayerName(){
		return playerName;
	}
	
	@Override
	public String toString(){
		return String.format("Player [ name=%20s, symbol=%2s, number of games=%02d, wins=%02d, losses=%02d, draws=%02d ]"
				,playerName,playerSymbol, score);
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Player){
			Player otherP = (Player)o;
			if(this.playerName.equalsIgnoreCase(otherP.playerName)){
				if(this.playerSymbol.equalsIgnoreCase(otherP.playerSymbol)){
					if(this.score == otherP.score){
						return true;
					}
				}
			}
			
		}
		return false;
	}
	
	@Override
	public int compareTo(Player otherP) {
		if(otherP !=null){
			if(this.score < otherP.score){
				return -1;
			}
			else if(this.score > otherP.score){
				return 1;
			}
		}
		return 0;
	}
}

