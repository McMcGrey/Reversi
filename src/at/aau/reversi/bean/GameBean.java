package at.aau.reversi.bean;

import at.aau.reversi.enums.Field;
import at.aau.reversi.enums.Player;

import java.util.Arrays;

public class GameBean {
	
	public GameBean(){
		// Set up a game field
		gameField = new Field[8][8];
		for(Field[] row : gameField) {
			Arrays.fill(row, Field.EMPTY);
		}
		// White beginns
		currentPlayer = Player.WHITE;
		// Controller has to activate gameField;
		gameFieldActive = false;
	}

	/**
	 * Short Array representing the GameField<br/>
	 * Values are defined in <code>at.aau.reversi.Constants</code>
	 */
	private Field[][] gameField;
	/**
	 * 2 Possibilities (PLAYER_WHITE/PLAYER_BLACK)<br/>
	 * Values are defined in <code>at.aau.reversi.Constants</code>
	 */
	private Player currentPlayer;
	/**
	 * When this field is true, the human in front of the computer is allowed to play
	 */
	private boolean gameFieldActive;
	
	/**
	 * @return the gameField
	 */
	public Field[][] getGameField() {
		return gameField;
	}
	/**
	 * @param gameField the gameField to set
	 */
	public void setGameField(Field[][] gameField) {
		this.gameField = gameField;
	}
	/**
	 * @return the currentPlayer
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	/**
	 * @param currentPlayer the currentPlayer to set
	 */
	public void setCurrentPlayer(Player currentPlayer) {
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
	
	public void toggleCurrentPlayer(){
		currentPlayer = (currentPlayer == Player.WHITE) ? Player.BLACK : Player.WHITE;
	}
}
