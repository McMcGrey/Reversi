package at.aau.reversi.logic.ai;

import at.aau.reversi.bean.Move;
import at.aau.reversi.enums.Field;
import at.aau.reversi.logic.GameLogicLocalImpl;

/**
 * Created with IntelliJ IDEA.
 * User: arne
 * Date: 28.04.13
 * Time: 22:11
 * To change this template use File | Settings | File Templates.
 */
public class AlphaBethaAIImpl extends AbstractAIImpl implements AI {

    public AlphaBethaAIImpl() {
        logic = new GameLogicLocalImpl();
    }

    @Override
    public Move calcNextStep(Field[][] gameField, Field white, int iterations) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
