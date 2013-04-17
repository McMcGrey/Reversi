package at.aau.reversi.gui;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;
import javax.swing.Box;

public class Game_Field extends JFrame {

	private JTextField numw;
	private JTextField numb;
	private JTextField rule_output;
	private JLabel Background;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game_Field window = new Game_Field();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Game_Field(){
		init();
	}
	
	/**
	 * Create the frame.
	 * @return 
	 */
	public void init() {
		
		// Betriebssystembezogener Button/Fensterlook
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		frame = new JFrame();
		frame.setTitle(" Reversi ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 693, 568);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		
		// STARTBILDSCHIRM
		
		JPanel start_site = new JPanel();
		frame.getContentPane().add(start_site, "Startseite");
		start_site.setLayout(null);
		
		
		//MEN� BAR ANFANG
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnSpiel = new JMenu("Spiel");
		menuBar.add(mnSpiel);
		
		JMenuItem mntmNewGame = new JMenuItem("Neues Spiel");
		mntmNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Spielauswahl");
				frame.setTitle("Spielauswahl");
				
			}
		});
		mnSpiel.add(mntmNewGame);
		
		
		JMenuItem mntmBeenden = new JMenuItem("Beenden");
		mntmBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit( 0 );
				
			}
		});
		mnSpiel.add(mntmBeenden);
		
		JMenu mnAnsicht = new JMenu("Ansicht");
		menuBar.add(mnAnsicht);
		
		JMenu mnKi = new JMenu("KI");
		mnKi.setHorizontalAlignment(SwingConstants.RIGHT);
		menuBar.add(mnKi);
		
		JMenu mnHilfe = new JMenu("Hilfe");
		menuBar.add(mnHilfe);
		
		// MEN�BAR ENDE
		
		
		// STARTBILDSCHIRM ANFANG
		
		JButton btnNewGame = new JButton("Neues Spiel");
		btnNewGame.setToolTipText("Neues Spiel");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Spielauswahl");
				frame.setTitle("Spielauswahl");

			}
		});
		btnNewGame.setBounds(237, 124, 171, 69);
		start_site.add(btnNewGame);
		
		JButton btnRules = new JButton("Spielregeln");
		btnRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Spielregeln");
				frame.setTitle("Spielregeln");
			}
		});
		btnRules.setBounds(237, 217, 171, 69);
		start_site.add(btnRules);
		
		JButton btnOptions = new JButton("Optionen");
		btnOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Optionen");
				frame.setTitle("Optionen");
			}
		});
		btnOptions.setBounds(237, 297, 171, 69);
		start_site.add(btnOptions);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit( 0 );
			}
		});
		btnExit.setBounds(532, 439, 89, 23);
		start_site.add(btnExit);
		
		Background=new JLabel(new ImageIcon(Game_Field.class.getResource("/Reversie/Background1.jpg")));
		Background.setForeground(Color.WHITE);
		Background.setBounds(0, 0, 677, 509);
		start_site.add(Background);
		
		//STARTBILDSCHIRM ENDE
		
		
		
		//FENSTER 2 SPIELAUSWAHL
		
		JPanel game_variation_site = new JPanel();
		frame.getContentPane().add(game_variation_site, "Spielauswahl");
		game_variation_site.setLayout(null);
		
		
		JButton btnSpielerVsComputer = new JButton("Spieler vs Computer");
		btnSpielerVsComputer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Spielfeld");
				frame.setTitle("Spielfeld");
				
			}
		});
		btnSpielerVsComputer.setBounds(193, 153, 288, 87);
		game_variation_site.add(btnSpielerVsComputer);
		
		JButton btnSpielerVsSpieler = new JButton("Spieler vs Spieler");
		btnSpielerVsSpieler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Multiplayer Auswahl");
				frame.setTitle("Multiplayer Auswahl");
				
			}
		});
		btnSpielerVsSpieler.setBounds(193, 276, 288, 87);
		game_variation_site.add(btnSpielerVsSpieler);
		
		JButton btnExit_1 = new JButton("Exit");
		btnExit_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit( 0 );
			}
		});
		btnExit_1.setBounds(568, 465, 89, 23);
		game_variation_site.add(btnExit_1);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Startseite");
				frame.setTitle("Startseite");
			}
		});
		btnBack.setBounds(10, 465, 89, 23);
		game_variation_site.add(btnBack);
		
		Background=new JLabel(new ImageIcon(Game_Field.class.getResource("/Reversie/Background1.jpg")));
		Background.setBounds(0, 0, 677, 509);
		game_variation_site.add(Background);
		
		
		//FENSTER 2 SPIELAUSWAHL ENDE
		
		//OPTIONEN ANFANG
		
		JPanel options = new JPanel();
		frame.getContentPane().add(options, "Optionen");
		options.setLayout(null);
		
		JButton btnBack2 = new JButton("Back");
		btnBack2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Startseite");
				frame.setTitle("Startseite");
			}
		});
		btnBack2.setBounds(10, 465, 89, 23);
		options.add(btnBack2);
		
		Background=new JLabel(new ImageIcon(Game_Field.class.getResource("/Reversie/Background1.jpg")));
		Background.setBounds(0, 0, 677, 509);
		options.add(Background);
		
		// OPTIONEN ENDE
		
		//SPIELREGELN ANFANG
		
		JPanel rules = new JPanel();
		frame.getContentPane().add(rules, "Spielregeln");
		rules.setLayout(null);
		
		
		JButton btnBack3 = new JButton("Back");
		btnBack3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Startseite");
				frame.setTitle("Startseite");
			}
		});
		btnBack3.setBounds(10, 465, 89, 23);
		rules.add(btnBack3);
		
		Background=new JLabel(new ImageIcon(Game_Field.class.getResource("/Reversie/Background1.jpg")));
		Background.setBounds(0, 0, 677, 509);
		rules.add(Background);
		
		//SPIELREGELN ENDE
		
		// Auswahl des Multiplayer 
		
		JPanel multiplayer_site = new JPanel();
		frame.getContentPane().add(multiplayer_site, "Multiplayer Auswahl");
		multiplayer_site.setLayout(null);
			
		
		JButton SpielerVsSpielerNT = new JButton("Spieler vs Spieler ( Netzwerk )");
		SpielerVsSpielerNT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean lokal = true ;
				
				((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Spielfeld");
				frame.setTitle("Spielfeld");
				
			}
		});
		SpielerVsSpielerNT.setBounds(200, 138, 263, 84);
		multiplayer_site.add(SpielerVsSpielerNT);
		
		JButton SpielerVsSpielderLokal = new JButton("Spieler vs Spieler ( Hot Seat )");
		SpielerVsSpielderLokal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean NT = true ;
				
				((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Spielfeld");
				frame.setTitle("Spielfeld");
				
			}
		});
		SpielerVsSpielderLokal.setBounds(200, 270, 263, 84);
		multiplayer_site.add(SpielerVsSpielderLokal);
		
		JButton btnBack_1 = new JButton("Back");
		btnBack3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Spielregeln");
				frame.setTitle("Spielregeln");
			}
		});
		btnBack_1.setBounds(10, 475, 89, 23);
		multiplayer_site.add(btnBack_1);
		
		Background=new JLabel(new ImageIcon(Game_Field.class.getResource("/Reversie/Background1.jpg")));
		Background.setBounds(0, 0, 677, 509);
		multiplayer_site.add(Background);

		// Auswahl des Multiplayer ENDE
		
		// SPIELFELD ANFANG
		
		JPanel play_site = new JPanel();
		frame.getContentPane().add(play_site, "Spielfeld");
		play_site.setBackground(Color.WHITE);
		play_site.setBorder(new EmptyBorder(5, 5, 5, 5));
		play_site.setLayout(null);	
		JPanel Field = new Draw_Game_Field();
		
		
		
		Field.addMouseListener(new MouseAdapter() {
			
			public int posx;
			public int posy;

			@Override
			public void mouseClicked(MouseEvent e) {
				
				posx = 0;
				posy = 0;
				
				int x = e.getX();
				int y = e.getY();
				
				if (x < 50) {
					
					posx = 0;	
				}else
				if (x > 51 && x < 100) {

					posx = 1;
				}else
				if (x > 101 && x < 150) {

					posx = 2;
				}else
				if (x > 151 && x < 200 ){
					
					posx = 3;	
				}else
				if ( x > 201 && x < 250){
					
					posx = 4;
				}else
				if ( x >251 && x < 300){
					
					posx = 5;
					
				}else
				if ( x >301 && x < 350){
					
					posx = 6;
				} else {
					
					posx = 7;
				}
				
				if ( y < 50){
					
					posy = 0;		
				}else
				if ( y > 51 && y < 100){
					
					posy = 1;		
				}else
				if ( y > 101 && y < 150){
					
					posy = 2;		
				}else
				if ( y > 151 && y < 200){
					
					posy = 3;		
				}else
				if ( y > 201 && y < 250){
					
					posy = 4;		
				}else
				if ( y > 251 && y < 300){
					
					posy = 5;		
				}else
				if ( y > 301 && y < 350){
					
					posy = 6;
					
				} else {
					posy = 7;		
				}
			}
		});
		Field.setBackground(Color.WHITE);
		Field.setBounds(40, 60, 400, 400);
		Field.setOpaque(false);
		play_site.add(Field);
		
		numw = new JTextField();
		numw.setEditable(false);
		numw.setBounds(541, 77, 96, 30);
		play_site.add(numw);
		numw.setColumns(10);
		
		numb = new JTextField();
		numb.setEditable(false);
		numb.setBounds(541, 196, 97, 30);
		play_site.add(numb);
		numb.setColumns(10);
		
		rule_output = new JTextField();
		rule_output.setEditable(false);
		rule_output.setBounds(40, 471, 400, 27);
		play_site.add(rule_output);
		rule_output.setColumns(10);
		
		JLabel white_stone = new JLabel("numb_image");
		white_stone.setIcon(new ImageIcon(Game_Field.class.getResource("/Reversie/white.png")));
		white_stone.setBounds(459, 67, 50, 50);
		play_site.add(white_stone);
		
		JLabel lblNewLabel = new JLabel("numw_image");
		lblNewLabel.setIcon(new ImageIcon(Game_Field.class.getResource("/Reversie/black.png")));
		lblNewLabel.setBounds(459, 186, 50, 50);
		play_site.add(lblNewLabel);
		
		Background=new JLabel(new ImageIcon(Game_Field.class.getResource("/Reversie/Background1.jpg")));
		Background.setBounds(0, 0, 677, 509);
		play_site.add(Background);
		
		
		
		
		
		// SPIELFELD ENDE

		
	}
}