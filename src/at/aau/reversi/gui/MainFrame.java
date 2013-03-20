package at.aau.reversi.gui;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import at.aau.reversi.controller.ReversiController;

public class MainFrame extends JFrame implements Observer {

	private JPanel contentPane;
	/**
	 * Controller Variable um dem Controller aenderungen Mitzuteilen
	 */
	private ReversiController controller;

	/**
	 * Create the frame.
	 */
	public MainFrame(ReversiController controller) {
		this.controller = controller;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	@Override
	public void update(Observable o, Object arg) {

		// TODO: Nachdem diese Methode aufgerufen wurde muss das Spielfeld aktualisiert werden
		
	}
}
