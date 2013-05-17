package at.aau.reversi.logic.ai;

import at.aau.reversi.bean.Move;
import at.aau.reversi.enums.Field;
import at.aau.reversi.logic.GameLogicLocalImpl;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: arne
 * Date: 28.04.13
 * Time: 22:11
 * To change this template use File | Settings | File Templates.
 */
public class FrontiersAIImpl extends AbstractAIImpl implements AI {

    public FrontiersAIImpl() {
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
        Field[][] gameField2 = gameField;

        for (Move move : validMoves) {
            logic.setGameField(copyArray(gameField2));
            if (getOponetPossibilities(gameField2, move, color, oponent) == 0) {
                return move;
            } else {
                gameField = logic.calcNewGameField(move.getxCoord(), move.getyCoord(), color);
            }
            placeholder = weightMove(gameField, move, calcOponentMove(gameField, color, oponent, iterations - 1), color);
            if (placeholder > result) {
                result = placeholder;
                bestMove = move;
            } else if (placeholder == result) {
                logic.setGameField(copyArray(gameField));
                if ( getOponetPossibilities(gameField, bestMove, color, oponent) > getOponetPossibilities(gameField, move, color, oponent)) {
                    bestMove = move;
                }
            }
        }
        return  bestMove;
    }

}
