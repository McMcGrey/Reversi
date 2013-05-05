package at.aau.reversi.logic.ai;

import at.aau.reversi.bean.Move;
import at.aau.reversi.enums.Field;
import at.aau.reversi.logic.GameLogicLocalImpl;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: arne
 * Date: 06.05.13
 * Time: 01:02
 * To change this template use File | Settings | File Templates.
 */
public class StabelAIImpl extends AbstractAIImpl implements AI {

    public StabelAIImpl() {
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
            placeholder = weightMove(gameField, move, calcOponentMove(gameField, color, oponent, iterations - 1), color, oponent);
            if (placeholder > result) {
                result = placeholder;
                bestMove = move;
            } else if (placeholder == result) {
                if (getOponetPossibilities(gameField, bestMove, color, oponent) > getOponetPossibilities(gameField, move, oponent, color)) {
                    bestMove = move;
                }
            }
        }
        return  bestMove;
    }

    protected int weightMove (Field[][] gameField, Move move, int placeholder, Field color, Field oponent) {
        if ((move.getxCoord() == 0 && move.getyCoord() == 0) ||
                (move.getxCoord() == 0 && move.getyCoord() == 7) ||
                (move.getxCoord() == 7 && move.getyCoord() == 0) ||
                (move.getxCoord() == 7 && move.getyCoord() == 7)) {
            placeholder = placeholder + cornerBias;
        } else if (!isStabel(gameField, move, color, oponent) &&
                ((move.getxCoord() == 0 && move.getyCoord() == 1) ||
                (move.getxCoord() == 1 && move.getyCoord() == 0) ||
                (move.getxCoord() == 1 && move.getyCoord() == 1) ||
                (move.getxCoord() == 0 && move.getyCoord() == 6) ||
                (move.getxCoord() == 1 && move.getyCoord() == 6) ||
                (move.getxCoord() == 1 && move.getyCoord() == 7) ||
                (move.getxCoord() == 6 && move.getyCoord() == 0) ||
                (move.getxCoord() == 6 && move.getyCoord() == 1) ||
                (move.getxCoord() == 7 && move.getyCoord() == 1) ||
                (move.getxCoord() == 6 && move.getyCoord() == 6) ||
                (move.getxCoord() == 6 && move.getyCoord() == 7) ||
                (move.getxCoord() == 7 && move.getyCoord() == 6)))  {
            placeholder = placeholder + region4Bias;
        } else if (!isStabel(gameField, move, color, oponent) && (
                move.getxCoord() == 0 || move.getxCoord() == 7 ||
                move.getyCoord() == 0 || move.getyCoord() == 7)) {
            placeholder = placeholder + cornerBias;
        } else if (move.getxCoord() == 0 || move.getxCoord() == 7 || move.getyCoord() == 0 || move.getyCoord() == 7 ||
                isStabel(gameField, move, color, oponent)) {
            placeholder = placeholder + edgeBias;
        }

        return placeholder;
    }
}
