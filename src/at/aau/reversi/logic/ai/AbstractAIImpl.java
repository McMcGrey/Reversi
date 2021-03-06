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
    final int cornerBias = 10;
    final int edgeBias = 5;
    final int groupBias = 2;
    final int region4Bias = -10;
    private final int X_WAY[] = {-1, -1, -1, 0, 1, 1, 1, 0};
    private final int Y_WAY[] = {-1, 0, 1, 1, 1, 0, -1, -1};

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

        ArrayList<Move> validMoves = new ArrayList<Move>();
        // Search for valid Moves
        for (short xCoord = 0; xCoord < 8; xCoord++) {
            for (short yCoord = 0; yCoord < 8; yCoord++) {
                if (gameField[xCoord][yCoord].equals(Field.MAYBE)) {
                    Move move = new Move(xCoord, yCoord);
                    validMoves.add(move);
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
        gameField = logic.calcNewGameField(move.getxCoord(), move.getyCoord(), color);
        gameField = possibleMoves(gameField, oponent);
        int count = 0;
        for (short i = 0; i < 8; i++) {
            for (short z = 0; z < 8; z++) {

                if (gameField[i][z].equals(Field.MAYBE)) {
                    count++;
                }

            }
        }
        return count;
    }

    protected boolean isStable(Field[][] gameField, Move move, Field color, Field oponent) {
        ArrayList<Boolean> stabels = new ArrayList<Boolean>();
        for (int w = 0; w <= 7; w++) {
            stabels.add(checkStable(gameField, move.getxCoord() + X_WAY[w], move.getyCoord() + Y_WAY[w], color, oponent, w));
        }
        for (int w = 0; w <= 3; w++) {
            if (stabels.get(w).equals(false) && stabels.get(w + 4).equals(false)){
                return false;
            }
        }
        return true;
    }
    private boolean checkStable(Field[][] gameField, int xCoord, int yCoord, Field color, Field oponent, int w) {
        if (!logic.inGamefield(xCoord, yCoord)) {
            return true;
        } else if (gameField[xCoord][yCoord].equals(oponent) || gameField[xCoord][yCoord].equals(Field.MAYBE) || gameField[xCoord][yCoord].equals(Field.EMPTY)) {
            return false;
        } else {
            return checkStable(gameField, xCoord + X_WAY[w], yCoord + Y_WAY[w], color, oponent, w);
        }
    }

    protected boolean breakingGroup(Field[][] gameField, Move move, Field color, Field oponent) {
        ArrayList<Boolean> group = new ArrayList<Boolean>();
        boolean x = false;
        for (int w = 0; w <= 7; w++) {
            group.add(checkGroup(gameField, move.getxCoord() + X_WAY[w], move.getyCoord() + Y_WAY[w], color, oponent, w));
        }
        for (int w = 0; w <= 3; w++) {
            if (group.get(w).equals(true) && group.get(w + 4).equals(true)){
                x = true;
            }
        }
        return x;
    }

    private boolean checkGroup(Field[][] gameField, int xCoord, int yCoord, Field color, Field oponent, int w) {
        if (!logic.inGamefield(xCoord, yCoord) || gameField[xCoord][yCoord].equals(Field.MAYBE) || gameField[xCoord][yCoord].equals(Field.EMPTY)) {
            return false;
        } else if (gameField[xCoord][yCoord].equals(oponent)) {
            return true;
        } else {
            return checkGroup(gameField, xCoord + X_WAY[w], yCoord + Y_WAY[w], color, oponent, w);
        }
    }


    protected int calcOponentMove(Field[][] gameField, Field color, Field oponent, int iterations) {

        ArrayList<Integer> results = new ArrayList<Integer>();
        ArrayList<Integer> innerResults;
        ArrayList<Move> validMovesOponent;
        int counter = 100;
        int innerCounter;
        Field[][] gameField2 = gameField;

        logic.setGameField(copyArray(gameField2));
        gameField = possibleMoves(gameField2, oponent);
        validMovesOponent = getMoves(gameField);
        if (validMovesOponent.isEmpty()) {
            return countFields(gameField, color);
        }
        for (Move moveOponent : validMovesOponent) {
            if (iterations == 0) {
                results.add(countFields(logic.calcNewGameField(moveOponent.getxCoord(), moveOponent.getyCoord(), oponent), color));
            } else {
                innerCounter = 100;
                innerResults = new ArrayList<Integer>();
                gameField = possibleMoves(gameField, color);
                validMoves = getMoves(gameField);
                for (Move move : validMoves) {
                    gameField = logic.calcNewGameField(move.getxCoord(), move.getyCoord(), color);
                    innerResults.add(calcOponentMove(gameField2, color, oponent, iterations - 1));
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

    public Field[][] possibleMoves(Field[][] gameField, Field color) {
        // Search for valid Moves
        for (short xCoord = 0; xCoord < 8; xCoord++) {
            for (short yCoord = 0; yCoord < 8; yCoord++) {
                if (logic.validMove(xCoord, yCoord, color)) {
                    gameField[xCoord][yCoord] = Field.MAYBE;
                } else if (gameField[xCoord][yCoord].equals(Field.MAYBE) || gameField[xCoord][yCoord].equals(Field.TIPP)) {
                    gameField[xCoord][yCoord] = Field.EMPTY;
                }
            }
        }
        return gameField;
    }

}
