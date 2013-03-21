package at.aau.reversi.controller;

import java.util.Observable;

import at.aau.reversi.Constants;
import at.aau.reversi.bean.GameBean;
import at.aau.reversi.logic.AI;
import at.aau.reversi.logic.GameLogic;
import at.aau.reversi.logic.GameLogicLocalImpl;
import at.aau.reversi.logic.GameLogicNetworkImpl;
import at.aau.reversi.logic.WeakAIImpl;

public class ReversiController extends Observable {

	private GameBean gameBean;
	private AI whiteAI;
	private AI blackAI;	
	private GameLogic logic;

	public ReversiController(){
	}
	
	/**
	 * Starts a new Game
	 * @param playerTypeWhite
	 * @param playerTypeBlack
	 * @param isHost When Game over Network and Acts as host
	 */
	public void startGame(short playerTypeWhite, short playerTypeBlack, boolean isHost){
		
		// Init Game
		gameBean = new GameBean();
		
		if(playerTypeWhite == Constants.PLAYER_TYPE_AI){
			whiteAI = new WeakAIImpl();
		}
		if(playerTypeBlack == Constants.PLAYER_TYPE_AI){
			blackAI = new WeakAIImpl();
		}
		if(!isHost){
			logic = new GameLogicLocalImpl();
		}else{
			logic = new GameLogicNetworkImpl();
		}
		
		// When white gamer is human, unfreeze gamefield
		if(playerTypeWhite == Constants.PLAYER_TYPE_HUMAN_PLAYER){
			gameBean.setGameFieldActive(true);
		}
		
		setChanged();
		notifyObservers();
		
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
	

	/**
	 * @return the gameBean
	 */
	public GameBean getGameBean() {
		return gameBean;
	}
	
}
