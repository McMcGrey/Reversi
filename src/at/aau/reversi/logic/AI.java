package at.aau.reversi.logic;

public interface AI {
	
	public short[][] calcNextStep(short[][] gameField, short color);
	
}
