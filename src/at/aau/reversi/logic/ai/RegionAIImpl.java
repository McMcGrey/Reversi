package at.aau.reversi.logic.ai;

import at.aau.reversi.bean.Move;
import at.aau.reversi.enums.Field;
import at.aau.reversi.logic.GameLogicLocalImpl;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: arne
 * Date: 02.05.13
 * Time: 21:27
 * To change this template use File | Settings | File Templates.
 */
public class RegionAIImpl extends AbstractAIImpl implements AI {

    public RegionAIImpl() {
        logic = new GameLogicLocalImpl();
    }

    @Override
    public Move calcNextStep(Field[][] gameField, Field color, int iterations) {
        Field oponent = (color.equals(Field.WHITE) ? Field.BLACK : Field.WHITE);
        validMoves = getMoves(gameField);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        bestMove = getNextMove(gameField, color, oponent, iterations);
        return bestMove;
    }

    private Move getNextMove(Field[][] gameField, Field color, Field oponent, int iterations) {
        ArrayList<Integer> results = new ArrayList<Integer>();
        int placeholder;
        int result = 0;
        Move bestMove = validMoves.get(0);

        for (Move move : validMoves) {
            logic.setGameField(copyArray(gameField));
            logic.calcNewGameField(move.getxCoord(), move.getyCoord(), color);
            if (!logic.possibleMoves(oponent)) {
                return move;
            }

            placeholder = weightMove(move, calcOponentMove(gameField, color, oponent, iterations - 1));
            if (placeholder > result) {
                result = placeholder;
                bestMove = move;
            }
        }
        return  bestMove;
    }

}
