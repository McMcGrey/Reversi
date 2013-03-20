package at.aau.reversi;

import java.awt.EventQueue;

import at.aau.reversi.controller.ReversiController;
import at.aau.reversi.gui.MainFrame;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ReversiController controller = new ReversiController();

		MainFrame frame = new MainFrame(controller);
		frame.setVisible(true);
		
		controller.addObserver(frame);

	}

}
