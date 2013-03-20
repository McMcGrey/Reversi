package at.aau.reversi.controller;

import java.util.Observable;

import at.aau.reversi.bean.GameBean;

public class ReversiController extends Observable {

	public ReversiController(){
		gameBean = new GameBean();
	}
	
	/**
	 * Starts a new Game
	 * @param playerTypeWhite
	 * @param playerTypeBlack
	 */
	public void startGame(short playerTypeWhite, short playerTypeBlack){
		
	}
	
	/**
	 * This method is called from the user interface when a Field
	 * was clicked
	 * @param player White or black
	 * @param xCoord X Coordinate of gameField
	 * @param yCoord Y Coordinate of gameField
	 */
	public void fieldClicked(short player, int xCoord, int yCoord){
		// TODO: 
	}
	
	private GameBean gameBean;

	/**
	 * @return the gameBean
	 */
	public GameBean getGameBean() {
		return gameBean;
	}
	
}
