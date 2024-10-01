package gui;

public interface GameBoardInterface {

	void populateBoard(); //what are the necessary elements we need to add to our grid?
	void displayBoard();
	void clearBoard();
	void takeTurns(); //Switch current player
	void displayResults(); //Display result of the game
	boolean isWinner();
	boolean isFull(); //determines if the board is "full"
	boolean isEmpty();
}
