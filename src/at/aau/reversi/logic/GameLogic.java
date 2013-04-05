package at.aau.reversi.logic;

public interface GameLogic {
	
	/**
	 * 
	 * @param xCoord X Coordinate of gameField
	 * @param yCoord Y Coordinate of gameField
	 * @param color White or black as Constant
	 * @return
	 */
	public short[][] calcNewGameField(short xCoord, short yCoord, short color);
	
	/**
	 * 
	 * @param xCoord X Coordinate of gameField
	 * @param yCoord Y Coordinate of gameField
	 * @param color White or black as Constant
	 * @return True when the move is valid
	 */
	public boolean validMove(short xCoord, short yCoord, short color);
}
