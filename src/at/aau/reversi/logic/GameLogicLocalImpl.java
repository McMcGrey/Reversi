package at.aau.reversi.logic;

import at.aau.reversi.bean.Move;
import at.aau.reversi.enums.Field;

import java.util.ArrayList;
import java.util.Arrays;

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
        if (gameField[xCoord][yCoord] == Field.EMPTY && inGamefield(xCoord, yCoord)) {
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
        return (gameField[xCoord][yCoord] != color && gameField[xCoord][yCoord] != Field.EMPTY);
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
            if (gameField[xCoord][yCoord] == color) {
                return true;
            } else if (gameField[xCoord][yCoord] == Field.EMPTY) {
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
        if (!inGamefield(xCoord, yCoord) || gameField[xCoord][yCoord].equals(Field.EMPTY)) {
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
    public ArrayList<Move> possibleMoves(Field color) {
        // Search for valid Moves
        ArrayList<Move> validMoves = new ArrayList<Move>();
        for (short xCoord = 0; xCoord < 8; xCoord++) {
            for (short yCoord = 0; yCoord < 8; yCoord++) {
                if (validMove(xCoord, yCoord, color)) {
                    System.out.println("Possible Move: " + xCoord + ", " + yCoord);
                    validMoves.add(new Move(xCoord, yCoord));
                }
            }
        }
        return validMoves;
    }
}
