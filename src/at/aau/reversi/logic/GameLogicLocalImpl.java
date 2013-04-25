package at.aau.reversi.logic;

import at.aau.reversi.enums.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameLogicLocalImpl extends GameLogicAbstract {

    Field[][] gameField;

    // Reihenfolge um Nachbarn zu durchlaufen
    private final int X_WAY[] = {-1, -1, -1, 0, 1, 1, 1, 0};
    private final int Y_WAY[] = {-1, 0, 1, 1, 1, 0, -1, -1};

    public GameLogicLocalImpl() {
        gameField = new Field[8][8];
        for (Field[] row : gameField) {
            Arrays.fill(row, Field.EMPTY);
        }
        gameField[3][3] = Field.WHITE;
        gameField[4][4] = Field.WHITE;
        gameField[4][3] = Field.BLACK;
        gameField[3][4] = Field.BLACK;
    }

    @Override
    public Field[][] calcNewGameField(short xCoord, short yCoord, Field color) {
        gameField[xCoord][yCoord] = color;
        for (int w = 0; w <= 7; w++) {
            turnStones(xCoord + X_WAY[w], yCoord + Y_WAY[w], color, w);
        }
        return gameField;
    }

    public boolean validMove(short xCoord, short yCoord, Field color) {
        // Feld muss leer und im Spielfeld sein
        if ((gameField[xCoord][yCoord].equals(Field.EMPTY) || gameField[xCoord][yCoord].equals(Field.MAYBE)) && inGamefield(xCoord, yCoord)) {
            // Alle nachbarn muessen ueberprueft werden
            for (int w = 0; w <= 7; w++) {
                // Nachbar muss nur ueberprueft werden wenn er im Spielfeld ist
                if (inGamefield(xCoord + X_WAY[w], yCoord + Y_WAY[w]))
                    // Ist der Nachbar gueltig
                    if (validNeighbour(xCoord + X_WAY[w], yCoord + Y_WAY[w], color))
                        // Handelt es sich um eine gueltige Reihe
                        if (validMove(xCoord + 2 * X_WAY[w], yCoord + 2 * Y_WAY[w], color, w))
                            return true;
            }
        }
        return false;
    }

    /**
     * Ueberprueft, ob ein Stein in den Spielfeldgrenzen liegt
     *
     * @param xCoord Zeile
     * @param yCoord Spalte
     * @return true/false
     */
    private boolean inGamefield(int xCoord, int yCoord) {
        return (xCoord >= 0 && xCoord < 8 && yCoord >= 0 && yCoord < 8);
    }

    /**
     * Ueberprueft, ob angrenzendes Feld gueltig ist,
     * d.h. es darf weder leer noch die eigene Spielfarbe besitzen
     *
     * @param xCoord Zeile
     * @param yCoord Spalte
     * @param color  Spielerfarbe
     * @return true/false
     */
    private boolean validNeighbour(int xCoord, int yCoord, Field color) {
        return (!gameField[xCoord][yCoord].equals(color) && !gameField[xCoord][yCoord].equals(Field.EMPTY) && !gameField[xCoord][yCoord].equals(Field.MAYBE));
    }

    /**
     * Ueberprueft, ob eine gueltige Reihe gefunden wurde
     *
     * @param xCoord Zeile
     * @param yCoord Spalte
     * @param color  Spielerfarbe
     * @param w      Aktuell zu pruefender Weg
     * @return true/false
     */
    private boolean validMove(int xCoord, int yCoord, Field color, int w) {
        if (inGamefield(xCoord, yCoord)) {
            if (gameField[xCoord][yCoord].equals(color)) {
                return true;
            } else if (gameField[xCoord][yCoord].equals(Field.EMPTY) || gameField[xCoord][yCoord].equals(Field.MAYBE)) {
                return false;
            } else {
                // Pruefe naechstes Feld in der Reihe
                return validMove(xCoord + X_WAY[w], yCoord + Y_WAY[w], color, w);
            }
        }
        return false;
    }

    @Override
    public Field[][] getGameField() {
        return gameField;
    }

    public void setGameField(Field[][] gameField) {
        this.gameField = gameField;
    }

    @Override
    public boolean turnStones(int xCoord, int yCoord, Field color, int w) {
        if (!inGamefield(xCoord, yCoord) || gameField[xCoord][yCoord].equals(Field.EMPTY) || gameField[xCoord][yCoord].equals(Field.MAYBE)) {
            return false;
        } else if (gameField[xCoord][yCoord].equals(color)) {
            return true;
        } else {
            if (turnStones(xCoord + X_WAY[w], yCoord + Y_WAY[w], color, w)) {
                gameField[xCoord][yCoord] = color;
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean possibleMoves(Field color) {
        // Search for valid Moves
        boolean validMoves = false;
        for (short xCoord = 0; xCoord < 8; xCoord++) {
            for (short yCoord = 0; yCoord < 8; yCoord++) {
                if (gameField[xCoord][yCoord].equals(Field.MAYBE)) {
                    gameField[xCoord][yCoord] = Field.EMPTY;
                }
                if (validMove(xCoord, yCoord, color)) {
                    gameField[xCoord][yCoord] = Field.MAYBE;
                    validMoves = true;
                }
            }
        }
        return validMoves;
    }

    @Override
    public Field endGame() {
        List<Integer> score = getIntermediateResult();
        if (score.get(0) < score.get(1)) {
            return Field.BLACK;
        } else if (score.get(0) > score.get(1)) {
            return Field.WHITE;
        } else {
            return Field.EMPTY;
        }
    }

    @Override
    public List<Integer> getIntermediateResult() {
        int black =0;
        int white = 0;
        for (short xCoord = 0; xCoord < 8; xCoord++) {
            for (short yCoord = 0; yCoord < 8; yCoord++) {
                if (gameField[xCoord][yCoord].equals(Field.WHITE)) {
                    white++;
                } else if(gameField[xCoord][yCoord].equals(Field.BLACK)) {
                    black++;
                }
            }
        }
        List<Integer> score= new ArrayList<Integer>();
        score.add(white);
        score.add(black);
        return score;
    }
}
