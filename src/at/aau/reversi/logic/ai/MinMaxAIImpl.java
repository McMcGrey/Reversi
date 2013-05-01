package at.aau.reversi.logic.ai;

import at.aau.reversi.bean.Move;
import at.aau.reversi.enums.Field;
import at.aau.reversi.logic.GameLogicLocalImpl;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: arne
 * Date: 28.04.13
 * Time: 22:10
 * To change this template use File | Settings | File Templates.
 */
public class MinMaxAIImpl extends AbstractAIImpl implements AI {

    private ArrayList<Move> validMoves;

    public MinMaxAIImpl() {
        logic = new GameLogicLocalImpl();
    }

    @Override
    public Move calcNextStep(Field[][] gameField, Field color, int iterations) {

        Field oponent = (color.equals(Field.WHITE) ? Field.BLACK : Field.WHITE);
        validMoves = getMoves(gameField);
        ArrayList<Integer> results = new ArrayList<Integer>();
        int placeholder;
        int result = 0;
        Move bestMove = validMoves.get(0);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Move move : validMoves) {
            logic.setGameField(copyArray(gameField));
            logic.calcNewGameField(move.getxCoord(), move.getyCoord(), color);
            if (!logic.possibleMoves(oponent)) {
                return move;
            }
            placeholder = calcOponentMove(gameField, color, oponent, iterations - 1);
            if (placeholder > result) {
                bestMove = move;
            }
        }
        return bestMove;
    }

    private int calcOponentMove(Field[][] gameField, Field color, Field oponent, int iterations) {

        ArrayList<Integer> results = new ArrayList<Integer>();
        ArrayList<Integer> innerResults;
        ArrayList<Move> validMovesOponent;
        int counter = 100;
        int innerCounter;

        if (!logic.possibleMoves(oponent)) {
            return countFields(gameField, color);
        }
        validMovesOponent = getMoves(gameField);
        for (Move moveOponent : validMovesOponent) {
            if (iterations == 0) {
                results.add(countFields(logic.calcNewGameField(moveOponent.getxCoord(), moveOponent.getyCoord(), oponent), color));
            } else {
                innerCounter = 100;
                innerResults = new ArrayList<Integer>();
                logic.possibleMoves(color);
                validMoves = getMoves(gameField);
                for (Move move : validMoves) {
                    logic.calcNewGameField(move.getxCoord(), move.getyCoord(), color);
                    innerResults.add(calcOponentMove(gameField, color, oponent, iterations - 1));
                }

                for (Integer res : innerResults) {
                    if (res < innerCounter) {
                        innerCounter = res;
                    }
                }
            }
        }
        for (Integer res : results) {
            if (res < counter) {
                counter = res;
            }
        }

        return counter;
    }

    /*private int calcMove(Field[][] gameField, Field color, Field oponent, int iterations) {

        ArrayList<Integer> innerResukts = new ArrayList<Integer>();
        int innerCounter = 100;

        logic.possibleMoves(color);
        validMoves = getMoves(gameField);
        for (Move move : validMoves) {
            logic.calcNewGameField(move.getxCoord(), move.getyCoord(), color);
            innerResukts.add(calcOponentMove(gameField, color, oponent, iterations));
        }

        for (Integer res : innerResukts) {
            if (res < innerCounter) {
                innerCounter = res;
            }
        }

        return innerCounter;

    }*/

}
