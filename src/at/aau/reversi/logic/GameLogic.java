package at.aau.reversi.logic;

import at.aau.reversi.enums.Field;

public interface GameLogic {
	
	/**
	 * 
	 * @param xCoord X Coordinate of gameField
	 * @param yCoord Y Coordinate of gameField
	 * @param color White or black as Constant
	 * @return
	 */
	public Field[][] calcNewGameField(short xCoord, short yCoord, Field color);
	
	/**
	 * 
	 * @param xCoord X Coordinate of gameField
	 * @param yCoord Y Coordinate of gameField
	 * @param color White or black as Constant
	 * @return True when the move is valid
	 */
	public boolean validMove(short xCoord, short yCoord, Field color);
	
	public Field[][] getGameField();
}
