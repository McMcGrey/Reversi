package at.aau.reversi;

import at.aau.reversi.controller.ReversiController;
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
		controller.startGame(Constants.PLAYER_TYPE_HUMAN_PLAYER, Constants.PLAYER_TYPE_AI, false);
		controller.fieldClicked(Constants.PLAYER_WHITE, (short)4,(short)4);
		//controller.fieldClicked(Constants.PLAYER_WHITE, (short)5,(short)3);
		
		// Test cases
	    System.out.println(gameLogic.validMove((short)5,(short)3, Constants.FIELD_WHITE));
		System.out.println(gameLogic.validMove((short)3,(short)5, Constants.FIELD_WHITE));
		System.out.println(gameLogic.validMove((short)3,(short)3, Constants.FIELD_WHITE));
		System.out.println(gameLogic.validMove((short)1,(short)1, Constants.FIELD_WHITE));

        //GameField
        System.out.println("   | A | B | C | D | E | F | G | H ");
        for (int yCoord = 0; yCoord<=7; yCoord++) {
            System.out.println("-----------------------------------");
            System.out.print(" " + (yCoord+1));
            for (int xCoord = 0; xCoord <= 7; xCoord++){
                 System.out.print(" | " + gameLogic.getGameField()[yCoord][xCoord]);
            }
            System.out.print("\r\n");
        }



	}

}
