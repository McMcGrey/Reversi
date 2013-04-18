package at.aau.reversi.logic;

import at.aau.reversi.bean.Move;
import at.aau.reversi.enums.Field;

import java.util.ArrayList;

/**
 * The WeakAII looks at all possible moves and takes the move which has the most own stones as result<br/>
 * (Takes the move which "turns" the most stones)
 */
public class WeakAIImpl implements AI {

    GameLogicLocalImpl logic;

    public WeakAIImpl() {
        logic = new GameLogicLocalImpl();
    }

    @Override
    public Move calcNextStep(Field[][] gameField, Field color) {

        logic.setGameField(copyArray(gameField));

        // Search for valid Moves
        ArrayList<Move> validMoves = logic.possibleMoves(color);

        // Search for best move
        Move best = null;
        int bestCount = 0;
        for (Move move : validMoves) {
            if (best == null) {
                best = move;
                logic.setGameField(copyArray(gameField));
                bestCount = countFields(logic.calcNewGameField(best.getxCoord(), best.getyCoord(), color), color);
            } else {
                logic.setGameField(copyArray(gameField));
                int tempCnt = countFields(logic.calcNewGameField(move.getxCoord(), move.getyCoord(), color), color);
                if (tempCnt > bestCount) {
                    bestCount = tempCnt;
                    best = move;
                }
            }
        }

        return best;
    }

    private int countFields(Field[][] gameField, Field color) {
        //todo: move countFields to better place
        int count = 0;
        for (short i = 0; i < 8; i++) {
            for (short z = 0; z < 8; z++) {

                if (gameField[i][z].equals(color)) {
                    count++;
                }

            }
        }
        return count;
    }

    private void printGameField(Field[][] gameField) {
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

    private Field[][] copyArray(Field[][] gameField) {

        Field[][] copy = new Field[8][8];
        for (int i = 0; i < 8; i++) {
            for (int z = 0; z < 8; z++) {
                copy[i][z] = gameField[i][z];
            }
        }

        return copy;
    }

}
