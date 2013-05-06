package at.aau.reversi.logic;

import at.aau.reversi.enums.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameLogicLocalImpl extends GameLogicAbstract {

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
}
