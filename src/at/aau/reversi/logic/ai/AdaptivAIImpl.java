package at.aau.reversi.logic.ai;

import at.aau.reversi.bean.Move;
import at.aau.reversi.enums.Field;
import at.aau.reversi.logic.GameLogicLocalImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: arne
 * Date: 28.04.13
 * Time: 22:12
 * To change this template use File | Settings | File Templates.
 */
public class AdaptivAIImpl extends AbstractAIImpl implements AI {
    private List<Integer> pointGraph = new ArrayList<Integer>();
    private AI ai = new GreedyAIImpl();
    private int countToChange;
    public AdaptivAIImpl() {
        logic = new GameLogicLocalImpl();
        pointGraph.add(0);
        countToChange = 0;
    }

    @Override
    public Move calcNextStep(Field[][] gameField, Field color, int iterations) {
        if (countToChange == 1) {
            setAi(gameField, color, iterations);
            countToChange = 0;
        } else {
            countToChange++;
        }

        bestMove = ai.calcNextStep(gameField, color, iterations);
        addToPointGraph(gameField, bestMove, color);

        return bestMove;
    }

    private void setAi(Field[][] gameField, Field color, int iterations) {
        int size = pointGraph.size();
        logic.setGameField(copyArray(gameField));
        Move move = ai.calcNextStep(gameField, color, iterations);
        logic.calcNewGameField(move.getxCoord(), move.getyCoord(), color);
        List<Integer> intermedeateResult = logic.getIntermediateResult();
        int average = ((intermedeateResult.get(0) - intermedeateResult.get(1)) + pointGraph.get(size-1)) / 2;


        if (average < -6 ) {
            ai = new RandomAIImpl();
        }else if (-6 <= average && average < -3) {
            ai = new GreedyAIImpl();
        } else if (-3 <= average && average <= 3) {
            ai = new MinMaxAIImpl();
        } else if (3 < average && average <= 6) {
            ai = new RegionAIImpl();
        } else if (6 < average && average <= 9) {
            ai = new FrontiersAIImpl();
        }
        else {
            ai = new StableAIImpl();
        }
    }

    private void addToPointGraph (Field[][] gameField, Move bestMove, Field color) {
        logic.setGameField(copyArray(gameField));
        logic.calcNewGameField(bestMove.getxCoord(), bestMove.getyCoord(), color);
        List<Integer> intermedeateResult = logic.getIntermediateResult();
        pointGraph.add(intermedeateResult.get(0) - intermedeateResult.get(1));
    }
}
