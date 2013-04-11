package at.aau.reversi;

import at.aau.reversi.controller.ReversiController;
import at.aau.reversi.gui.MainFrame;
import at.aau.reversi.logic.GameLogicLocalImpl;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ReversiController controller = new ReversiController();

		MainFrame frame = new MainFrame(controller);
		frame.setVisible(true);
		
		controller.addObserver(frame);

		// Controller Test
		controller.startGame(Constants.PLAYER_TYPE_HUMAN_PLAYER, Constants.PLAYER_TYPE_AI, false);
		controller.fieldClicked(Constants.PLAYER_WHITE, (short)4,(short)4);
		controller.fieldClicked(Constants.PLAYER_WHITE, (short)5,(short)3);
		
		// Test cases
		System.out.println(new GameLogicLocalImpl().validMove((short)5,(short)3, Constants.FIELD_WHITE));
		System.out.println(new GameLogicLocalImpl().validMove((short)3,(short)5, Constants.FIELD_WHITE));
		System.out.println(new GameLogicLocalImpl().validMove((short)3,(short)3, Constants.FIELD_WHITE));
		System.out.println(new GameLogicLocalImpl().validMove((short)1,(short)1, Constants.FIELD_WHITE));

	}

}
