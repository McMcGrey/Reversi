package at.aau.reversi;

import at.aau.reversi.controller.ReversiController;
import at.aau.reversi.enums.Field;
import at.aau.reversi.enums.Player;
import at.aau.reversi.enums.PlayerType;
import at.aau.reversi.gui.MainFrame;
import at.aau.reversi.logic.GameLogic;
import at.aau.reversi.logic.GameLogicLocalImpl;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ReversiController controller = new ReversiController();

		MainFrame frame = new MainFrame(controller);
		frame.setVisible(true);

        GameLogic gameLogic = new GameLogicLocalImpl();
		
		controller.addObserver(frame);

		// Controller Test
		controller.startGame(PlayerType.HUMAN_PLAYER, PlayerType.AI, false);
		controller.fieldClicked(Player.WHITE, (short) 4,(short) 4);
		controller.fieldClicked(Player.BLACK, (short) 5, (short) 3);
		
		// Test cases
        System.out.println(gameLogic.validMove((short) 5, (short) 3, Field.WHITE));
        System.out.println(gameLogic.validMove((short) 3, (short) 5, Field.WHITE));
        System.out.println(gameLogic.validMove((short) 3, (short) 3, Field.WHITE));
        System.out.println(gameLogic.validMove((short) 1, (short) 1, Field.WHITE));

        //GameField
        System.out.println("   |   A   |   B   |   C   |   D   |   E   |   F   |   G   |   H   ");
        for (int xCoord = 0; xCoord<=7; xCoord++) {
            System.out.println("-------------------------------------------------------------------");
            System.out.print(" " + (xCoord));
            for (int yCoord = 0; yCoord <= 7; yCoord++){
                 System.out.print(" | " + gameLogic.getGameField()[xCoord][yCoord]);
            }
            System.out.print("\r\n");
        }



	}

}
