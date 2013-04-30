package at.aau.reversi.logic.ai;

import at.aau.reversi.enums.Field;
import at.aau.reversi.bean.Move;

public interface AI {

    public Move calcNextStep(Field[][] gameField, Field color, int iterations);

}
