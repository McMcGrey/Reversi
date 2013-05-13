package at.aau.reversi.controller;

import at.aau.reversi.bean.ErrorBean;
import at.aau.reversi.bean.GameBean;
import at.aau.reversi.bean.Move;
import at.aau.reversi.enums.*;
import at.aau.reversi.logic.*;
import at.aau.reversi.logic.ai.*;
import at.aau.reversi.network.Gameclient;
import at.aau.reversi.network.Gameserver;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Observable;

public class ReversiController extends Observable {

    private GameBean gameBean;
    private AI whiteAI;
    private AI blackAI;
    private GameLogic logic;
    private PlayerType playerTypeWhite;
    private PlayerType playerTypeBlack;
    private AIType aiTypeWhite;
    private AIType aiTypeBlack;
    private Gameserver server;
    private InetAddress serverAddress;

    public ReversiController() {
    }

    /**
     * Starts a new Game
     *
     * @param playerTypeWhite
     * @param playerTypeBlack
     * @param isHost          When Game over Network and Acts as host
     */
    public void startGame(PlayerType playerTypeWhite, PlayerType playerTypeBlack, AIType aiTypeWhite, AIType aiTypeBlack, boolean isHost) {

        // Init Game
        gameBean = new GameBean();

        // When a server exists, remove it from Observer-Pattern
        if(server != null){
            server.killServer();
            deleteObserver(server);
            server = null;
        }
        if(logic != null && logic instanceof Gameclient){
            ((Gameclient)logic).killClient();
            logic = null;
        }
        if(isHost){
            try {
                server = new Gameserver(this);
            } catch (IOException e) {
                setChanged();
                notifyObservers(new ErrorBean("Es hat sich kein Client verbunden", ErrorDisplayType.POPUP));
                return;
            }
            addObserver(server);
        }

        this.playerTypeBlack = playerTypeBlack;
        this.playerTypeWhite = playerTypeWhite;

        if (playerTypeWhite == PlayerType.AI) {
            whiteAI = setAIStrenght(aiTypeWhite);
        }else{
            whiteAI = null;
        }
        if (playerTypeBlack == PlayerType.AI) {
            blackAI = setAIStrenght(aiTypeBlack);
        }else{
            blackAI = null;
        }

        if(playerTypeWhite.equals(PlayerType.NETWORK) && !isHost){
            try {
                //logic = new GameLogicNetworkImpl(serverAddress);
                logic = new Gameclient(serverAddress, this);

            } catch (IOException e) {
                setChanged();
                notifyObservers(new ErrorBean("Fehler beim Verbinden zum Server", ErrorDisplayType.POPUP));
            }
        }else{
            logic = new GameLogicLocalImpl();
        }

        logic.possibleMoves(Field.WHITE);
        gameBean.setGameField(logic.getGameField());

        // When white gamer is human, unfreeze gamefield
        if (playerTypeWhite == PlayerType.HUMAN_PLAYER) {
            gameBean.setGameFieldActive(true);
        }
        gameBean.setCurrentPlayer(Player.WHITE);

        setChanged();
        notifyObservers(gameBean);

    }

    /**
     * This method is called from the user interface when a Field
     * was clicked
     *
     * @param player White or black
     * @param xCoord X Coordinate of gameField
     * @param yCoord Y Coordinate of gameField
     */
    public void fieldClicked(Player player, short xCoord, short yCoord) {

        fieldClicked(player, xCoord,yCoord,false);
    }

    public void fieldClicked(Player player, short xCoord, short yCoord, boolean network) {

        boolean valid = true;
        if((gameBean.getCurrentPlayer().equals(Player.BLACK)&&playerTypeBlack.equals(PlayerType.NETWORK)&&!network)
                ||(gameBean.getCurrentPlayer().equals(Player.WHITE)&&playerTypeWhite.equals(PlayerType.NETWORK)&&!network)){
            valid = false;
        }

        if (player.equals(gameBean.getCurrentPlayer()) && valid) {
            // The white player has the color white, this is setted here
            Field color = (player.equals(Player.WHITE)) ? Field.WHITE : Field.BLACK;
            //Field color = Field.WHITE;
            if (gameBean.getGameField()[xCoord][yCoord].equals(Field.MAYBE)) {

                // Spielzug durchfuehren
                applyMove(xCoord, yCoord, color);

                // Spielfeld mit Zug des aktuellen Spielers aktualisieren
                setChanged();
                notifyObservers(gameBean);

                // Wenn AI notwendig ist, AI ausfuehren
                applyAI();

                setChanged();
                notifyObservers(gameBean);

            } else {
                setChanged();
                notifyObservers(new ErrorBean("Ungültiger Zug", ErrorDisplayType.INLINE));
            }

        } else {

            setChanged();
            notifyObservers(new ErrorBean("Zur Zeit ist kein Zug möglich", ErrorDisplayType.INLINE));

        }

    }

    private void applyMove(short xCoord, short yCoord, Field color) {
        // Spielzug durchfuehren
        gameBean.setGameField(logic.calcNewGameField(xCoord, yCoord, color));
        if(!logic.possibleMoves((gameBean.getCurrentPlayer().equals(Player.WHITE)) ? Field.BLACK : Field.WHITE)) {
            if (!logic.possibleMoves((gameBean.getCurrentPlayer().equals(Player.WHITE)) ? Field.WHITE : Field.BLACK)) {
                endGame();
            } else {
                String message = "Zur Zeit ist kein Zug möglich, "
                        + ((gameBean.getCurrentPlayer().equals(Player.WHITE)) ? "weiss ist am Zug": "schwarz ist am Zug");
                setChanged();
                notifyObservers(new ErrorBean(message, ErrorDisplayType.INLINE));
                // Check if AI has to set the next move
                applyAI();
            }
        } else {
            gameBean.toggleCurrentPlayer();
        }

        // Wenn ein menschlicher Spieler am Zug ist, Spielfeld feigeben
        if ((gameBean.getCurrentPlayer().equals(Player.WHITE) && playerTypeWhite.equals(PlayerType.HUMAN_PLAYER))
                || (gameBean.getCurrentPlayer().equals(Player.BLACK) && playerTypeBlack.equals(PlayerType.HUMAN_PLAYER))) {
            gameBean.setGameFieldActive(true);
        } else {
            gameBean.setGameFieldActive(false);
        }

        List<Integer> score = logic.getIntermediateResult();
        gameBean.setWhite(score.get(0));
        gameBean.setBlack(score.get(1));

        // Spielfeld benachrichtigen
//        setChanged();
//        notifyObservers(gameBean);
    }


    /**
     * @return the gameBean
     */
    public GameBean getGameBean() {
        return gameBean;
    }

    private void applyAI() {

        if (gameBean.getCurrentPlayer().equals(Player.WHITE)) {
            if (whiteAI != null) {

                Move move = whiteAI.calcNextStep(gameBean.getGameField(), Field.WHITE, 3);
                applyMove(move.getxCoord(), move.getyCoord(), Field.WHITE);

            }
        } else if (gameBean.getCurrentPlayer().equals(Player.BLACK)) {
            if (blackAI != null) {

                Move move = blackAI.calcNextStep(gameBean.getGameField(), Field.BLACK, 3);
                applyMove(move.getxCoord(), move.getyCoord(), Field.BLACK);

            }
        }
    }

    private void endGame() {
        String winner;
        switch (logic.endGame()) {
            case WHITE:
                winner = "Weiss gewinnt.";
                break;
            case BLACK:
                winner = "Schwarz gewinnt.";
                break;
            case EMPTY:
                winner = "Unentschieden.";
                break;
            default:
                winner="";
        }
        setChanged();
        notifyObservers(new ErrorBean("Spielende: " + winner, ErrorDisplayType.POPUP));
    }
    
    public void getTipp() {
    	AI ai = new GroupAIImpl();
    	Field color = gameBean.getCurrentPlayer().equals(Player.WHITE) ? Field.WHITE : Field.BLACK;
        Move move = ai.calcNextStep(gameBean.getGameField(), color, 3);
    	gameBean.getGameField()[move.getxCoord()][move.getyCoord()] = Field.TIPP;

        setChanged();
        notifyObservers(gameBean);
    }

    private AI setAIStrenght(AIType aiType) {
        AI ai;
        if(aiType == AIType.AI_RANDOM) {
            ai = new RandomAIImpl();
        } else if (aiType == AIType.AI_GREEDY) {
            ai = new GreedyAIImpl();
        } else if (aiType == AIType.AI_MINMAX) {
            ai = new MinMaxAIImpl();
        } else if (aiType == AIType.AI_FROMTIERS) {
            ai = new FrontiersAIImpl();
        } else if (aiType == AIType.AI_REGION) {
            ai = new RegionAIImpl();
        } else if (aiType == AIType.AI_STABLE) {
            ai = new StableAIImpl();
        } else if (aiType == AIType.AI_GROUP) {
            ai = new GroupAIImpl();
        } else {
            ai = new AdaptivAIImpl();
        }
        return ai;
    }

    public void setServerAddress(InetAddress serverAddress) {
        this.serverAddress = serverAddress;
    }

    public void sendErrorMessageToObservers(ErrorBean errorbean){
        setChanged();
        notifyObservers(errorbean);
    }

    public void notifyControllerAtClientGame(GameBean bean){
        this.gameBean = bean;

        if(gameBean.getCurrentPlayer().equals(Player.BLACK)){
            gameBean.setGameFieldActive(true);
        }

        setChanged();
        notifyObservers(gameBean);
    }

    public void handleErrorBeanFromClientToController(ErrorBean bean){

        setChanged();
        notifyObservers(bean);
    }

    public void setKIAfterConnectionLoss(){

        if(playerTypeWhite.equals(PlayerType.NETWORK)){

            playerTypeWhite = PlayerType.AI;
            whiteAI = new AdaptivAIImpl();

            ((Gameclient)logic).killClient();
            logic = new GameLogicLocalImpl();

            applyAI();

            setChanged();
            notifyObservers(gameBean);

        }else if(playerTypeBlack.equals(PlayerType.NETWORK)){

        }

    }
}
