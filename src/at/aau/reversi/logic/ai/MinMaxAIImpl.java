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

        for (Move move : validMoves) {
            logic.setGameField(copyArray(gameField));
            logic.calcNewGameField(move.getxCoord(), move.getyCoord(), color);
            if (!logic.possibleMoves(oponent)) {
                return move;
            }
            placeholder = calcOponentMove(gameField, color, oponent);
            for (int i = 2; i <= iterations; i++){
                placeholder = calcMove(gameField, color, oponent);

            }
            if (placeholder > result) {
                bestMove = move;
            }
        }
        return bestMove;
    }

    private int calcOponentMove(Field[][] gameField, Field color, Field oponent) {

        ArrayList<Integer> results = new ArrayList<Integer>();
        ArrayList<Move> validMovesOponent;
        int counter = 100;

        if (!logic.possibleMoves(oponent)) {
            return countFields(gameField, color);
        }
        validMovesOponent = getMoves(gameField);
        for (Move moveOponent : validMovesOponent) {
            results.add(countFields(logic.calcNewGameField(moveOponent.getxCoord(), moveOponent.getyCoord(), oponent), color));
        }
        for (Integer res : results) {
            if (res < counter) {
                counter = res;
            }
        }

        return counter;
    }

    private int calcMove(Field[][] gameField, Field color, Field oponent) {

        ArrayList<Integer> results = new ArrayList<Integer>();
        int counter = 100;

        logic.possibleMoves(color);
        validMoves = getMoves(gameField);
        for (Move moveOponent : validMoves) {
            logic.calcNewGameField(moveOponent.getxCoord(), moveOponent.getyCoord(), color);
            results.add(calcOponentMove(gameField, color , oponent));
        }

        for (Integer res : results) {
            if (res < counter) {
                counter = res;
            }
        }

        return counter;

    }

}
