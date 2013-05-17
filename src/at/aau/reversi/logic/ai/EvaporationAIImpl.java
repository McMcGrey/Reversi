package at.aau.reversi.logic.ai;

import at.aau.reversi.bean.Move;
import at.aau.reversi.enums.Field;
import at.aau.reversi.logic.GameLogicLocalImpl;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: arne
 * Date: 17.05.13
 * Time: 21:10
 * To change this template use File | Settings | File Templates.
 */
public class EvaporationAIImpl extends AbstractAIImpl implements AI {

    public EvaporationAIImpl() {
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

        bestMove = getNextMove(gameField, color, oponent);
        return bestMove;
    }

    private Move getNextMove(Field[][] gameField, Field color, Field oponent) {
        ArrayList<Integer> results = new ArrayList<Integer>();
        int placeholder = 100;
        int result = 100;
        Move bestMove = validMoves.get(0);
        Field[][] gameField2 = gameField;

        for (Move move : validMoves) {
            logic.setGameField(copyArray(gameField2));
            placeholder = getOponetPossibilities(gameField, move, color, oponent);
            System.out.println(move.getxCoord() + " " + move.getyCoord() + ";" + placeholder);
            if (placeholder < result) {
                result = placeholder;
                bestMove = move;
            System.out.println("Taken" + result);
            } /*else if (placeholder == result) {

            }*/
        }
        System.out.println("------------");
        return  bestMove;
    }
}
