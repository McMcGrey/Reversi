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
}
