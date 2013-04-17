package at.aau.reversi;

import at.aau.reversi.controller.ReversiController;
import at.aau.reversi.enums.PlayerType;
import at.aau.reversi.gui.ConsoleInterface;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {

        ReversiController controller = new ReversiController();

		/*MainFrame frame = new MainFrame(controller);
        frame.setVisible(true);
		
		controller.addObserver(frame);*/

        ConsoleInterface cns = new ConsoleInterface(controller);
        controller.addObserver(cns);

        // Controller Test
        controller.startGame(PlayerType.HUMAN_PLAYER, PlayerType.HUMAN_PLAYER, false);
        /*controller.fieldClicked(Player.WHITE, (short) 2,(short) 4);
		controller.fieldClicked(Player.BLACK, (short) 5, (short) 3);*/

//		// Test cases
//        System.out.println(gameLogic.validMove((short) 5, (short) 3, Field.WHITE));
//        System.out.println(gameLogic.validMove((short) 3, (short) 5, Field.WHITE));
//        System.out.println(gameLogic.validMove((short) 3, (short) 3, Field.WHITE));
//        System.out.println(gameLogic.validMove((short) 1, (short) 1, Field.WHITE));


    }

}
