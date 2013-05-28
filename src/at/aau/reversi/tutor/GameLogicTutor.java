package at.aau.reversi.tutor;

import at.aau.reversi.bean.Move;
import at.aau.reversi.enums.Field;
import at.aau.reversi.exceptions.InvalidInputException;
import at.aau.reversi.logic.GameLogic;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 24.05.13
 * Time: 11:38
 *
 * This class implements the adapter-Pattern to include the Tutorlibrary. Everything
 * works over network. neither valid moves nor new game field is calculated local.
 *
 */
public class GameLogicTutor implements GameLogic {

    @Override
    public Field[][] calcNewGameField(short xCoord, short yCoord, Field color) {
        return new Field[0][];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean validMove(short xCoord, short yCoord, Field color) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Field[][] getGameField() {
        return new Field[0][];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Move getMoveFromInputstring(String inputString) throws InvalidInputException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean possibleMoves(Field color) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Field endGame() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Integer> getIntermediateResult() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
