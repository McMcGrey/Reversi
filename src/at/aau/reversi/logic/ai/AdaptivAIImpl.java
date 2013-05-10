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
        if (countToChange%2 == 0 && countToChange != 0) {
            setAi(gameField, color, iterations);
        }
        countToChange++;

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


        if ((average < -9 && !isMainPhase())) {
            ai = new RandomAIImpl();
            System.out.println("Random" + average);
        }else if ((average < -6 && !isMainPhase()) || average < -6 && isMainPhase()) {
            ai = new GreedyAIImpl();
            System.out.println("Greedy" + average);
        } else if ((average < -3 && !isMainPhase()) || average < -4 && isMainPhase()) {
            ai = new MinMaxAIImpl();
            System.out.println("MinMax" + average);
        } else if ((average < 0 && !isMainPhase()) || average < 2 && isMainPhase()) {
            ai = new RegionAIImpl();
            System.out.println("Region" + average);
        } else if ((average < 3 && !isMainPhase())|| average < 6 && isMainPhase()) {
            ai = new FrontiersAIImpl();
            System.out.println("Frontiers" + average);
        }else if((average < 6 && !isMainPhase()) || average < 10 && isMainPhase()) {
            ai = new StableAIImpl();
            System.out.println("Stable" + average);
        } else {
            ai = new GroupAIImpl();
            System.out.println("Group" + average);
        }
    }

    private void addToPointGraph (Field[][] gameField, Move bestMove, Field color) {
        logic.setGameField(copyArray(gameField));
        logic.calcNewGameField(bestMove.getxCoord(), bestMove.getyCoord(), color);
        List<Integer> intermedeateResult = logic.getIntermediateResult();
        pointGraph.add(intermedeateResult.get(0) - intermedeateResult.get(1));
    }

    private boolean isMainPhase(){
        if (countToChange <= 40 && countToChange >= 10) return true;
        return false;
    }
}
