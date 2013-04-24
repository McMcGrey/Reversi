package at.aau.reversi.gui;

import at.aau.reversi.bean.ErrorBean;
import at.aau.reversi.bean.GameBean;
import at.aau.reversi.bean.Move;
import at.aau.reversi.controller.ReversiController;
import at.aau.reversi.enums.Player;
import at.aau.reversi.exceptions.InvalidInputException;
import at.aau.reversi.logic.GameLogicLocalImpl;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 16.04.13
 * Time: 11:22
 * To change this template use File | Settings | File Templates.
 */
public class ConsoleInterface implements Observer {

    ReversiController controller;

    public ConsoleInterface(ReversiController controller) {
        this.controller = controller;
    }

    private Move printInputPrompt(Player color) {

        if (color == Player.BLACK) {
            System.out.print("Schwarz ist am Zug, bitte Feld eingaben: ");
        } else {
            System.out.print("Weiß ist am Zug, bitte Feld eingaben: ");
        }


        Move m = null;

        while (m == null) {
            try {
                Scanner scanner = new Scanner(System.in);
                String input = scanner.next();

                m = new GameLogicLocalImpl().getMoveFromInputstring(input);

            } catch (InvalidInputException ex) {
                System.out.print("Ungueltiger Zug, bitte neuen Zug im Format [A-H][1-8]: ");
            }
        }

        return m;
    }

    private void printGameField(GameBean bean) {
        //GameField
        System.out.println("   | A | B | C | D | E | F | G | H ");
        for (int xCoord = 0; xCoord <= 7; xCoord++) {
            System.out.println("------------------------------------");
            System.out.print(" " + (xCoord + 1));
            for (int yCoord = 0; yCoord <= 7; yCoord++) {
                System.out.print(" | " + bean.getGameField()[xCoord][yCoord]);
            }
            System.out.print("\r\n");
        }
    }

    @Override
    public void update(Observable o, Object arg) {

        if (arg instanceof GameBean) {

            GameBean gameBean = (GameBean) arg;

            printGameField(gameBean);

            if (gameBean.isGameFieldActive()) {

                Move m = printInputPrompt(gameBean.getCurrentPlayer());
                controller.fieldClicked(gameBean.getCurrentPlayer(), m.getxCoord(), m.getyCoord());

            }

        } else if (arg instanceof ErrorBean) {

            ErrorBean errorBean = (ErrorBean) arg;

            System.out.println("Achtung!! " + errorBean.getErrorMessage());

        }

    }
}
