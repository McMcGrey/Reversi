package at.aau.reversi.network;

import at.aau.reversi.bean.Move;
import at.aau.reversi.enums.NetworkPackageType;

/**
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 06.05.13
 * Time: 20:24
 * To change this template use File | Settings | File Templates.
 */
public class MovePackage extends NetworkPackage {

    protected Move move;

    public MovePackage(Move move) {
        super(NetworkPackageType.MOVE);
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }
}
