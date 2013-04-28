package at.aau.reversi.logic;

import at.aau.reversi.bean.Move;
import at.aau.reversi.enums.Field;

import java.util.ArrayList;

/**
 * The GreedyAII looks at all possible moves and takes the move which has the most own stones as result<br/>
 * (Takes the move which "turns" the most stones)
 */
public class GreedyAIImpl extends AbstractAIImpl implements AI {

    public GreedyAIImpl() {
        logic = new GameLogicLocalImpl();
    }

    @Override
    public Move calcNextStep(Field[][] gameField, Field color) {

        // Delay bevor AI spielt
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<Move> validMoves = getMoves(gameField);

        // Search for best move
        Move best = null;
        int bestCount = 0;
        for (Move move : validMoves) {
            if (best == null) {
                best = move;
                logic.setGameField(copyArray(gameField));
                bestCount = countFields(logic.calcNewGameField(best.getxCoord(), best.getyCoord(), color), color);
            } else {
                logic.setGameField(copyArray(gameField));
                int tempCnt = countFields(logic.calcNewGameField(move.getxCoord(), move.getyCoord(), color), color);
                if (tempCnt > bestCount) {
                    bestCount = tempCnt;
                    best = move;
                }
            }
        }

        return best;
    }

    private int countFields(Field[][] gameField, Field color) {
        //todo: move countFields to better place
        int count = 0;
        for (short i = 0; i < 8; i++) {
            for (short z = 0; z < 8; z++) {

                if (gameField[i][z].equals(color)) {
                    count++;
                }

            }
        }
        return count;
    }

}
