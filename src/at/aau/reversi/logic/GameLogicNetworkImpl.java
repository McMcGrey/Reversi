package at.aau.reversi.logic;

import at.aau.reversi.constants.Constants;
import at.aau.reversi.enums.Field;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class GameLogicNetworkImpl extends GameLogicAbstract {


    public GameLogicNetworkImpl(InetAddress serverAddress) throws IOException {
        //Socket socket = new Socket(serverAddress, Constants.SERVER_PORT);
    }

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
