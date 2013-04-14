package at.aau.reversi.logic;

import at.aau.reversi.Field;
import at.aau.reversi.bean.Move;

public interface AI {
	
	public Move calcNextStep(Field[][] fields, Field white);
	
}
