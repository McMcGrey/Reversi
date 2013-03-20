package at.aau.reversi.bean;

import at.aau.reversi.Constants;

public class GameBean {
	
	public GameBean(){
		// Set up a game field
		gameField = new short[8][8];
		// White beginns
		currentPlayer = Constants.PLAYER_WHITE;
		// Controller has to activate gameField;
		gameFieldActive = false;
	}

	/**
	 * Short Array representing the GameField<br/>
	 * Values are defined in <code>at.aau.reversi.Constants</code>
	 */
	private short[][] gameField;
	/**
	 * 2 Possibilities (PLAYER_WHITE/PLAYER_BLACK)<br/>
	 * Values are defined in <code>at.aau.reversi.Constants</code>
	 */
	private short currentPlayer;
	/**
	 * When this field is true, the human in front of the computer is allowed to play
	 */
	private boolean gameFieldActive;
	
	/**
	 * @return the gameField
	 */
	public short[][] getGameField() {
		return gameField;
	}
	/**
	 * @param gameField the gameField to set
	 */
	public void setGameField(short[][] gameField) {
		this.gameField = gameField;
	}
	/**
	 * @return the currentPlayer
	 */
	public short getCurrentPlayer() {
		return currentPlayer;
	}
	/**
	 * @param currentPlayer the currentPlayer to set
	 */
	public void setCurrentPlayer(short currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	/**
	 * @return the gameFieldActive
	 */
	public boolean isGameFieldActive() {
		return gameFieldActive;
	}
	/**
	 * @param gameFieldActive the gameFieldActive to set
	 */
	public void setGameFieldActive(boolean gameFieldActive) {
		this.gameFieldActive = gameFieldActive;
	}
	
}
