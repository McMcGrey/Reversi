package at.aau.reversi.logic;

import at.aau.reversi.bean.Move;
import at.aau.reversi.enums.Field;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: arne
 * Date: 28.04.13
 * Time: 21:39
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractAIImpl {

    GameLogicLocalImpl logic;

    protected Field[][] copyArray(Field[][] gameField) {

        Field[][] copy = new Field[8][8];
        for (int i = 0; i < 8; i++) {
            for (int z = 0; z < 8; z++) {
                copy[i][z] = gameField[i][z];
            }
        }

        return copy;
    }

    protected void printGameField(Field[][] gameField) {
        //GameField
        System.out.println("   | A | B | C | D | E | F | G | H ");
        for (int xCoord = 0; xCoord <= 7; xCoord++) {
            System.out.println("-----------------------------------");
            System.out.print(" " + (xCoord + 1));
            for (int yCoord = 0; yCoord <= 7; yCoord++) {
                System.out.print(" | " + gameField[yCoord][xCoord]);
            }
            System.out.print("\r\n");
        }
    }

    protected ArrayList<Move> getMoves(Field[][] gameField) {

        logic.setGameField(copyArray(gameField));

        ArrayList<Move> validMoves = new ArrayList<Move>();
        // Search for valid Moves
        for (short xCoord = 0; xCoord < 8; xCoord++) {
            for (short yCoord = 0; yCoord < 8; yCoord++) {
                if (gameField[xCoord][yCoord].equals(Field.MAYBE)) {
                    validMoves.add(new Move(xCoord, yCoord));
                }
            }
        }

        return validMoves;
    }
}
