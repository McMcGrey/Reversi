package at.aau.reversi.controller;

import java.util.Observable;

public class ReversiController extends Observable {

	private short[][] gameField;
	
	public short[][] getGameField(){
		return this.gameField;
	}
	
}
