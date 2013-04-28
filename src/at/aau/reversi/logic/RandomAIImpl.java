package at.aau.reversi.logic;

import at.aau.reversi.bean.Move;
import at.aau.reversi.enums.Field;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: arne
 * Date: 28.04.13
 * Time: 21:34
 * To change this template use File | Settings | File Templates.
 */

/**
 * The RandomAII looks at all possible moves and picks one move by random<br/>
 * (Takes any move)
 */
public class RandomAIImpl extends AbstractAIImpl implements AI {


    public RandomAIImpl() {
        logic = new GameLogicLocalImpl();
    }

    @Override
    public Move calcNextStep(Field[][] gameField, Field white) {

        // Delay bevor AI spielt
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<Move> validMoves = getMoves(gameField);

        return validMoves.get(getRandom(validMoves));
    }

    public int getRandom(ArrayList<Move> array) {
        int randomMove = new Random().nextInt(array.size());
        return randomMove;
    }


}
