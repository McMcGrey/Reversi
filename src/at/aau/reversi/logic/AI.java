package at.aau.reversi.logic;

import at.aau.reversi.enums.Field;
import at.aau.reversi.bean.Move;

public interface AI {

    public Move calcNextStep(Field[][] gameField, Field white);

}
