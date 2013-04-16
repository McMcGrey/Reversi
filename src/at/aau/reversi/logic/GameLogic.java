package at.aau.reversi.logic;

import at.aau.reversi.bean.Move;
import at.aau.reversi.enums.Field;
import at.aau.reversi.exceptions.InvalidInputException;

public interface GameLogic {

    /**
     * @param xCoord X Coordinate of gameField
     * @param yCoord Y Coordinate of gameField
     * @param color  White or black as Constant
     * @return
     */
    public Field[][] calcNewGameField(short xCoord, short yCoord, Field color);

    /**
     * @param xCoord X Coordinate of gameField
     * @param yCoord Y Coordinate of gameField
     * @param color  White or black as Constant
     * @return True when the move is valid
     */
    public boolean validMove(short xCoord, short yCoord, Field color);

    public Field[][] getGameField();

    public Move getMoveFromInputstring(String inputString) throws InvalidInputException;

    public void turnStones(short xCoord, short yCoord, short dx, short dy, Field color);
}
