package at.aau.reversi.logic;

import at.aau.reversi.enums.Field;

public class GameLogicNetworkImpl extends GameLogicAbstract {

    @Override
    public Field[][] calcNewGameField(short xCoord, short yCoord, Field color) {
        // TODO Algorithm which sets the Colors
        return null;
    }

    @Override
    public boolean validMove(short xCoord, short yCoord, Field color) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Field[][] getGameField() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean turnStones(int xCoord, int yCoord, Field color, int w) {
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }
}
