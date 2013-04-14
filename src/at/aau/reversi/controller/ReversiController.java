package at.aau.reversi.controller;

import java.util.Observable;

import at.aau.reversi.enums.ErrorDisplayType;
import at.aau.reversi.enums.Field;
import at.aau.reversi.enums.Player;
import at.aau.reversi.enums.PlayerType;
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
	private PlayerType playerTypeWhite;
	private PlayerType playerTypeBlack;

	public ReversiController(){
	}
	
	/**
	 * Starts a new Game
	 * @param playerTypeWhite
	 * @param playerTypeBlack
	 * @param isHost When Game over Network and Acts as host
	 */
	public void startGame(PlayerType playerTypeWhite, PlayerType playerTypeBlack, boolean isHost){
		
		// Init Game
		gameBean = new GameBean();
		
		this.playerTypeBlack = playerTypeBlack;
		this.playerTypeWhite = playerTypeWhite;
		
		if(playerTypeWhite == PlayerType.AI){
			whiteAI = new WeakAIImpl();
		}
		if(playerTypeBlack == PlayerType.AI){
			blackAI = new WeakAIImpl();
		}
		if(!isHost){
			logic = new GameLogicLocalImpl();
		}else{
			logic = new GameLogicNetworkImpl();
		}
		gameBean.setGameField(logic.getGameField());
		
		// When white gamer is human, unfreeze gamefield
		if(playerTypeWhite == PlayerType.HUMAN_PLAYER){
			gameBean.setGameFieldActive(true);
		}
		gameBean.setCurrentPlayer(Player.WHITE);
		
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
	public void fieldClicked(Player player, short xCoord, short yCoord){
		// TODO: 
		
		if(player == gameBean.getCurrentPlayer() && gameBean.isGameFieldActive()){
			// I'm not getting this :(
			//Field color = (player == Field.WHITE) ? Field.WHITE : Field.WHITE;
			Field color = Field.WHITE;
			
			if(logic.validMove(xCoord, yCoord, color)){
				
				// Spielzug durchfuehren
				applyMove(xCoord, yCoord, color);
				
				// Wenn AI notwendig ist, AI ausfuehren
				applyAI();
				
			}else{
				setChanged();
				notifyObservers(new ErrorBean("Ungültiger Zug", ErrorDisplayType.INLINE));
			}
			
		}else{
			
			setChanged();
			notifyObservers(new ErrorBean("Zur Zeit ist kein Zug möglich", ErrorDisplayType.INLINE));
			
		}
	}
	
	private void applyMove(short xCoord, short yCoord, Field color){
		// Spielzug durchfuehren
		gameBean.setGameField(logic.calcNewGameField(xCoord, yCoord, color));
		gameBean.toggleCurrentPlayer();
		
		// Wenn ein menschlicher Spieler am Zug ist, Spielfeld feigeben
		if((gameBean.getCurrentPlayer() == Player.WHITE && playerTypeWhite == PlayerType.HUMAN_PLAYER)
				|| (gameBean.getCurrentPlayer() == Player.BLACK && playerTypeBlack == PlayerType.HUMAN_PLAYER)){
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
		
		if(gameBean.getCurrentPlayer() == Player.WHITE){
			if(whiteAI != null){
				
				Move move = whiteAI.calcNextStep(gameBean.getGameField(), Field.WHITE);
				applyMove(move.getxCoord(), move.getyCoord(), Field.WHITE);
				
			}
		}else if(gameBean.getCurrentPlayer() == Player.BLACK){
			if(blackAI != null){
				
				Move move = blackAI.calcNextStep(gameBean.getGameField(), Field.WHITE);
				applyMove(move.getxCoord(), move.getyCoord(), Field.BLACK);
				
			}
		}
	}
	
}
