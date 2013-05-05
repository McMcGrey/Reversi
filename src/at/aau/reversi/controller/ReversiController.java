package at.aau.reversi.controller;

import at.aau.reversi.bean.ErrorBean;
import at.aau.reversi.bean.GameBean;
import at.aau.reversi.bean.Move;
import at.aau.reversi.enums.*;
import at.aau.reversi.logic.*;
import at.aau.reversi.logic.ai.*;

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
        if (!isHost) {
            logic = new GameLogicLocalImpl();
        } else {
            logic = new GameLogicNetworkImpl();
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

        if (player.equals(gameBean.getCurrentPlayer()) && gameBean.isGameFieldActive()) {
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
                notifyObservers(new ErrorBean(message, ErrorDisplayType.POPUP));
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

    private AI setAIStrenght(AIType aiType) {
        AI ai;
        if(aiType == AIType.AI_RANDOM) {
            ai = new RandomAIImpl();
        } else if (aiType == AIType.AI_GREEDY) {
            ai = new GreedyAIImpl();
        } else if (aiType == AIType.AI_MINMAX) {
            ai = new MinMaxAIImpl();
        } else if (aiType == AIType.AI_FROMTIERS) {
            ai = new Frontiers();
        } else if (aiType == AIType.AI_REGION) {
            ai = new RegionAIImpl();
        } else {
            ai = new AdaptivAIImpl();
        }
        return ai;
    }
}
