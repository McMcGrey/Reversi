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

    public MinMaxAIImpl() {
        logic = new GameLogicLocalImpl();
    }

    @Override
    public Move calcNextStep(Field[][] gameField, Field color, int iterations) {

        Field oponent = (color.equals(Field.WHITE) ? Field.BLACK : Field.WHITE);
        ArrayList<Move> validMoves = getMoves(gameField);
        ArrayList<Move> validMovesOponent;
        int result = 0;
        Move bestMove = validMoves.get(0);

        for (Move move : validMoves) {
            logic.setGameField(copyArray(gameField));
            for (int iter = 1; iter <= iterations; iter++) {
                logic.calcNewGameField(move.getxCoord(), move.getyCoord(), color);
                if (!logic.possibleMoves(oponent)) {
                    return move;
                }
                validMovesOponent = getMoves(gameField);
                ArrayList<Integer> results = new ArrayList<Integer>();
                int i = 100;
                for (Move moveOponent : validMovesOponent) {
                    results.add(countFields(logic.calcNewGameField(moveOponent.getxCoord(), moveOponent.getyCoord(), oponent), color));
                }
                if (iter == iterations) {
                    for (Integer res : results) {
                        if (res < i) {
                            i = res;
                        }
                    }
                    if (i > result) {
                        bestMove = move;
                    }
                }
            }
        }
        return bestMove;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
