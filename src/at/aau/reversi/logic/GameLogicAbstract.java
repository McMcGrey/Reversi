package at.aau.reversi.logic;

import at.aau.reversi.bean.Move;
import at.aau.reversi.enums.Field;
import at.aau.reversi.exceptions.InvalidInputException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 16.04.13
 * Time: 19:00
 * To change this template use File | Settings | File Templates.
 */
public abstract class GameLogicAbstract implements GameLogic {

    // Reihenfolge um Nachbarn zu durchlaufen
    protected final int X_WAY[] = {-1, -1, -1, 0, 1, 1, 1, 0};
    protected final int Y_WAY[] = {-1, 0, 1, 1, 1, 0, -1, -1};

    protected Field[][] gameField;

    @Override
    public Move getMoveFromInputstring(String input) throws InvalidInputException {

        if (input.length() != 2) {
            throw new InvalidInputException();
        }

        Move move = new Move();

        input = input.toLowerCase();
        char firstChar = input.charAt(0);
        short yCoord;
        switch (firstChar) {
            case 'a':
                yCoord = 0;
                break;
            case 'b':
                yCoord = 1;
                break;
            case 'c':
                yCoord = 2;
                break;
            case 'd':
                yCoord = 3;
                break;
            case 'e':
                yCoord = 4;
                break;
            case 'f':
                yCoord = 5;
                break;
            case 'g':
                yCoord = 6;
                break;
            case 'h':
                yCoord = 7;
                break;
            default:
                throw new InvalidInputException();
        }

        move.setyCoord(yCoord);

        char secondChar = input.charAt(1);
        if (!Character.isDigit(secondChar)) {
            throw new InvalidInputException();
        }
        short secondCoord = (short) Character.getNumericValue(secondChar);
        if (secondCoord < 1 || secondCoord > 8) {
            throw new InvalidInputException();
        }


        move.setxCoord(((short) (secondCoord - 1)));

        return move;
    }

    @Override
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
    public boolean inGamefield(int xCoord, int yCoord) {
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
