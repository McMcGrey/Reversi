package at.aau.reversi.logic;

import at.aau.reversi.bean.Move;

public interface AI {
	
	public Move calcNextStep(short[][] gameField, short color);
	
}
