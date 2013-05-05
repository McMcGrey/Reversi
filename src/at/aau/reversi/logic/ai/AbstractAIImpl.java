package at.aau.reversi.logic.ai;

import at.aau.reversi.bean.Move;
import at.aau.reversi.enums.Field;
import at.aau.reversi.logic.GameLogicLocalImpl;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: arne
 * Date: 28.04.13
 * Time: 21:39
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractAIImpl {

    protected Move bestMove = null;
    protected GameLogicLocalImpl logic;
    protected ArrayList<Move> validMoves;
    protected int cornerBias = 10;
    protected int edgeBias = 5;
    protected int semiEdgeBias = -5;
    protected int region4Bias = -10;

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

    protected int countFields(Field[][] gameField, Field color) {
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

    protected int weightMove (Field[][] gameField, Move move, int placeholder, Field color) {
        if ((move.getxCoord() == 0 && move.getyCoord() == 0) ||
                (move.getxCoord() == 0 && move.getyCoord() == 7) ||
                (move.getxCoord() == 7 && move.getyCoord() == 0) ||
                (move.getxCoord() == 7 && move.getyCoord() == 7)) {
            placeholder = placeholder + cornerBias;
        } else if (((move.getxCoord() == 0 && move.getyCoord() == 1) ||
                (move.getxCoord() == 1 && move.getyCoord() == 0) ||
                (move.getxCoord() == 1 && move.getyCoord() == 1)) && !gameField[0][0].equals(color) ||
                ((move.getxCoord() == 0 && move.getyCoord() == 6) ||
                (move.getxCoord() == 1 && move.getyCoord() == 6) ||
                (move.getxCoord() == 1 && move.getyCoord() == 7)) && !gameField[0][7].equals(color) ||
                ((move.getxCoord() == 6 && move.getyCoord() == 0) ||
                (move.getxCoord() == 6 && move.getyCoord() == 1) ||
                (move.getxCoord() == 7 && move.getyCoord() == 1)) && !gameField[7][0].equals(color) ||
                ((move.getxCoord() == 6 && move.getyCoord() == 6) ||
                (move.getxCoord() == 6 && move.getyCoord() == 7) ||
                (move.getxCoord() == 7 && move.getyCoord() == 6)) && !gameField[7][7].equals(color)) {
            placeholder = placeholder + region4Bias;
        } else if (move.getxCoord() == 0 || move.getxCoord() == 7 ||
                move.getyCoord() == 0 || move.getyCoord() == 7) {
            placeholder = placeholder + edgeBias;
        }
        return placeholder;
    }

    protected int getOponetPossibilities (Field[][] gameField, Move move, Field color, Field oponent) {
        logic.setGameField(copyArray(gameField));
        logic.calcNewGameField(move.getxCoord(), move.getyCoord(), color);
        logic.possibleMoves(oponent);
        return getMoves(gameField).size();
    }

    protected int calcOponentMove(Field[][] gameField, Field color, Field oponent, int iterations) {

        ArrayList<Integer> results = new ArrayList<Integer>();
        ArrayList<Integer> innerResults;
        ArrayList<Move> validMovesOponent;
        int counter = 100;
        int innerCounter;

        if (!logic.possibleMoves(oponent)) {
            return countFields(gameField, color);
        }
        validMovesOponent = getMoves(gameField);
        for (Move moveOponent : validMovesOponent) {
            if (iterations == 0) {
                results.add(countFields(logic.calcNewGameField(moveOponent.getxCoord(), moveOponent.getyCoord(), oponent), color));
            } else {
                innerCounter = 100;
                innerResults = new ArrayList<Integer>();
                logic.possibleMoves(color);
                validMoves = getMoves(gameField);
                for (Move move : validMoves) {
                    logic.calcNewGameField(move.getxCoord(), move.getyCoord(), color);
                    innerResults.add(calcOponentMove(gameField, color, oponent, iterations - 1));
                }

                for (Integer res : innerResults) {
                    if (res < innerCounter) {
                        innerCounter = res;
                    }
                }
                results.add(innerCounter);
            }
        }
        for (Integer res : results) {
            if (res < counter) {
                counter = res;
            }
        }

        return counter;
    }

}
