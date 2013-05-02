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

    private ArrayList<Move> validMoves;
    private int cornerBias = 15;
    private int edgeBias = 5;
    private int region4Bias = -15;

    public RegionAIImpl() {
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
            placeholder = weightMove(move, calcOponentMove(gameField, color, oponent, iterations - 1));
            System.out.println(move.getxCoord() + ", " + move.getyCoord() + "; " + placeholder);
            if (placeholder > result) {
                bestMove = move;
                System.out.println("Taken");
            }
        }
        System.out.println("---------------------");
        return bestMove;
    }

    private int calcOponentMove(Field[][] gameField, Field color, Field oponent, int iterations) {

        ArrayList<Integer> results = new ArrayList<Integer>();
        ArrayList<Integer> innerResults;
        ArrayList<Move> validMovesOponent;
        int counter = 100;
        int innerCounter;
        int placeholder;

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
                    placeholder = weightMove(move, calcOponentMove(gameField, color, oponent, iterations - 1));
                    innerResults.add(placeholder);

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

    private int weightMove (Move move, int placeholder) {
        if ((move.getxCoord() == 0 && move.getyCoord() == 0) ||
                (move.getxCoord() == 0 && move.getyCoord() == 7) ||
                (move.getxCoord() == 7 && move.getyCoord() == 0) ||
                (move.getxCoord() == 7 && move.getyCoord() == 7)) {
            placeholder = placeholder + cornerBias;
        } else if ((move.getxCoord() == 0 && move.getyCoord() == 1) ||
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
                (move.getxCoord() == 7 && move.getyCoord() == 6)) {
            placeholder = placeholder + region4Bias;
        } else if (move.getxCoord() == 0 || move.getxCoord() == 7 ||
                move.getyCoord() == 0 || move.getyCoord() == 7) {
            placeholder = placeholder + edgeBias;
        }
        return placeholder;
    }
}
