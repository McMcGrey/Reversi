package at.aau.reversi.controller;

import java.util.Observable;

import at.aau.reversi.Constants;
import at.aau.reversi.bean.ErrorBean;
import at.aau.reversi.bean.GameBean;
import at.aau.reversi.bean.Move;
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
	private short playerTypeWhite;
	private short playerTypeBlack;

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
		
		this.playerTypeBlack = playerTypeBlack;
		this.playerTypeWhite = playerTypeWhite;
		
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
		gameBean.setGameField(logic.getGameField());
		
		// When white gamer is human, unfreeze gamefield
		if(playerTypeWhite == Constants.PLAYER_TYPE_HUMAN_PLAYER){
			gameBean.setGameFieldActive(true);
		}
		gameBean.setCurrentPlayer(Constants.PLAYER_WHITE);
		
		setChanged();
		notifyObservers(gameBean);
		
	}
	
	/**
	 * This method is called from the user interface when a Field
	 * was clicked
	 * @param player White or black
	 * @param xCoord X Coordinate of gameField
	 * @param yCoord Y Coordinate of gameField
	 */
	public void fieldClicked(short player, short xCoord, short yCoord){
		// TODO: 
		
		if(player == gameBean.getCurrentPlayer() && gameBean.isGameFieldActive()){
			
			short color = (player == Constants.FIELD_WHITE) ? Constants.FIELD_WHITE : Constants.FIELD_WHITE;
			
			if(logic.validMove(xCoord, yCoord, color)){
				
				// Spielzug durchfuehren
				applyMove(xCoord, yCoord, color);
				
				// Wenn AI notwendig ist, AI ausfuehren
				applyAI();
				
			}else{
				setChanged();
				notifyObservers(new ErrorBean("Ungültiger Zug", Constants.ERROR_DISPLAY_TYPE_INLINE));
			}
			
		}else{
			
			setChanged();
			notifyObservers(new ErrorBean("Zur Zeit ist kein Zug möglich", Constants.ERROR_DISPLAY_TYPE_INLINE));
			
		}
	}
	
	private void applyMove(short xCoord, short yCoord, short color){
		// Spielzug durchfuehren
		gameBean.setGameField(logic.calcNewGameField(xCoord, yCoord, color));
		gameBean.toggleCurrentPlayer();
		
		// Wenn ein menschlicher Spieler am Zug ist, Spielfeld feigeben
		if((gameBean.getCurrentPlayer() == Constants.PLAYER_WHITE && playerTypeWhite == Constants.PLAYER_TYPE_HUMAN_PLAYER)
				|| (gameBean.getCurrentPlayer() == Constants.PLAYER_BLACK && playerTypeBlack == Constants.PLAYER_TYPE_HUMAN_PLAYER)){
			gameBean.setGameFieldActive(true);
		}else{
			gameBean.setGameFieldActive(false);
		}
		
		// Spielfeld benachrichtigen
		setChanged();
		notifyObservers(gameBean);
	}
	

	/**
	 * @return the gameBean
	 */
	public GameBean getGameBean() {
		return gameBean;
	}
	
	private void applyAI(){
		
		if(gameBean.getCurrentPlayer() == Constants.PLAYER_WHITE){
			if(whiteAI != null){
				
				Move move = whiteAI.calcNextStep(gameBean.getGameField(), Constants.FIELD_WHITE);
				applyMove(move.getxCoord(), move.getyCoord(), Constants.FIELD_WHITE);
				
			}
		}else if(gameBean.getCurrentPlayer() == Constants.PLAYER_BLACK){
			if(blackAI != null){
				
				Move move = blackAI.calcNextStep(gameBean.getGameField(), Constants.FIELD_WHITE);
				applyMove(move.getxCoord(), move.getyCoord(), Constants.FIELD_BLACK);
				
			}
		}
	}
	
}
