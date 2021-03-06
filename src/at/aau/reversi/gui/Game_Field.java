package at.aau.reversi.gui;

import at.aau.reversi.bean.ErrorBean;
import at.aau.reversi.bean.GameBean;
import at.aau.reversi.bean.Move;
import at.aau.reversi.controller.ReversiController;
import at.aau.reversi.enums.AIType;
import at.aau.reversi.enums.ErrorDisplayType;
import at.aau.reversi.enums.Player;
import at.aau.reversi.enums.PlayerType;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;
import java.util.regex.Pattern;

public class Game_Field extends JFrame implements Observer, Runnable {

    private JTextField numw;
    private JTextField numb;
    private JTextField rule_output;
    private JLabel Backgroundfield;
    private JLabel Background;
    private JLabel Background1;
    private JLabel Background2;
    private JLabel Background3;
    private JFrame frame;
    private Draw_Game_Field gameFieldPanel;
    private ReversiController controller;
    private GameBean gameBean;
    private Stack eventStack = new Stack();
    private boolean isRunning = true;
    private JTextField txtA;
    private JTextField txtB;
    private JTextField txtC;
    private JTextField txtD;
    private JTextField txtE;
    private JTextField txtF;
    private JTextField txtG;
    private JTextField txtH;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    private JTextField textField_6;
    private JTextField textField_7;
    private JTextField KI_Auswahl;
    private JTextField txtMeineIpAdresse;
    private JTextField Ip_output;
    private JIpTextField ipTextFieldin;
    //private JTextField txtWarteAufVerbindung;
    //private AIType AIwhite = AIType.AI_GREEDY;
    private AIType aiBlack = AIType.AI_ADAPTIV;
    private boolean isSpeedGame = false;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Game_Field window = new Game_Field();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
    }

    public Game_Field(ReversiController controller) {
        init();
        this.controller = controller;
        Thread t = new Thread(this);
        t.start();
    }

    /**
     * Create the frame.
     *
     * @return
     */
    public void init() {

        // Betriebssystembezogener Button/Fensterlook

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
        }


        frame = new JFrame();
        frame.setTitle(" Reversi ");
        try {
            frame.setIconImage(ImageIO.read(new File("src/at/aau/reversi/gui/images/JframeIcon.png")));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 690, 560);
        frame.setResizable(false);
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
                frame.setBounds(0, 0, 690, 560);
                controller.setNotKilled(false);
                repaint();
                ((CardLayout) frame.getContentPane().getLayout()).show(
                        frame.getContentPane(), "Spielauswahl");
                frame.setTitle("Spielauswahl");

            }
        });
        mnSpiel.add(mntmNewGame);

        JMenuItem mntmBeenden = new JMenuItem("Beenden");
        mntmBeenden.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                isRunning = false;

            }
        });
        mnSpiel.add(mntmBeenden);


        JMenu mnKi = new JMenu("Ansicht");
        mnKi.setHorizontalAlignment(SwingConstants.RIGHT);
        menuBar.add(mnKi);

        JCheckBoxMenuItem mntmStufe1 = new JCheckBoxMenuItem("Moeglichen Zuege anzeigen");
        mntmStufe1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.togglePossibleMovesVisibility();
                repaint();
            }
        });
        mnKi.add(mntmStufe1);

        JMenuItem mntmStufe2 = new JMenuItem("Tipp anzeigen");
        mntmStufe2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            getTipp();
            repaint();
            }
        });
        mnKi.add(mntmStufe2);


        // MENUEBAR ENDE


        // STARTBILDSCHIRM ANFANG

        JButton btnNewGame = new JButton("Neues Spiel");
        btnNewGame.setToolTipText("Neues Spiel");
        btnNewGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Spielauswahl");
                frame.setTitle("Spielauswahl");

            }
        });
        btnNewGame.setBounds(193, 153, 288, 87);
        start_site.add(btnNewGame);

        JButton btnRules = new JButton("Spielregeln");
        btnRules.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Spielregeln");
                frame.setTitle("Spielregeln");
            }
        });
        btnRules.setBounds(193, 276, 288, 87);
        start_site.add(btnRules);

        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnExit.setBounds(568, 465, 89, 23);
        start_site.add(btnExit);

        Background = new JLabel(new ImageIcon("src/at/aau/reversi/gui/images/background1.png"));
        Background.setForeground(Color.WHITE);
        Background.setBounds(0, 0, 700, 600);
        start_site.add(Background);

        //STARTBILDSCHIRM ENDE


        //FENSTER 2 SPIELAUSWAHL

        JPanel game_variation_site = new JPanel();
        frame.getContentPane().add(game_variation_site, "Spielauswahl");
        game_variation_site.setLayout(null);


        JButton btnSpielerVsComputer = new JButton("Spieler vs Computer");
        btnSpielerVsComputer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                startSinglePlayer();
                controller.setNotKilled(false);
                while (gameBean == null) {

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                }
                ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Optionen");
                frame.setTitle("Optionen");

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
                System.exit(0);
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

        Background = new JLabel(new ImageIcon("src/at/aau/reversi/gui/images/background1.png"));
        Background.setBounds(0, 0, 700, 600);
        game_variation_site.add(Background);


        //FENSTER 2 SPIELAUSWAHL ENDE


        // SPIELOPTIONEN ANFANG

        JPanel options = new JPanel();
        frame.getContentPane().add(options, "Optionen");
        options.setLayout(null);

        KI_Auswahl = new JTextField();
        KI_Auswahl.setText("Schwierigkeitsgrad auswaehlen : ");
        KI_Auswahl.setBounds(80, 50, 300, 23);
        options.add(KI_Auswahl);
        KI_Auswahl.setColumns(10);
        KI_Auswahl.setBorder(null);
        KI_Auswahl.setOpaque(false);
        KI_Auswahl.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        Font ki_auswahl = new Font("Verdana", Font.BOLD, 14);
        KI_Auswahl.setFont(ki_auswahl);
        KI_Auswahl.setForeground(Color.BLACK);
        KI_Auswahl.setHorizontalAlignment(JTextField.LEFT);


        JRadioButton rb1 = new JRadioButton("Grad 1: Random");
        rb1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            aiBlack = AIType.AI_RANDOM;
            eventStack.push(new ErrorBean(
                "Schwierigkeit auf Random gesetzt",
                ErrorDisplayType.INLINE));
            }
        });
        rb1.setBounds(80, 80, 160, 23);
        rb1.setOpaque(false);
        options.add(rb1);

        JRadioButton rb2 = new JRadioButton("Grad 2: Greedy");
        rb2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            aiBlack = AIType.AI_GREEDY;
            eventStack.push(new ErrorBean(
                "Schwierigkeit auf Greedy gesetzt",
                ErrorDisplayType.INLINE));
            }
        });
        rb2.setBounds(80, 120, 160, 23);
        rb2.setOpaque(false);
        options.add(rb2);

        JRadioButton rb3 = new JRadioButton("Grad 3: MinMax");
        rb3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            aiBlack = AIType.AI_MINMAX;
            eventStack.push(new ErrorBean(
                "Schwierigkeit auf MinMax gesetzt",
                ErrorDisplayType.INLINE));
            }
        });
        rb3.setBounds(80, 160, 160, 23);
        rb3.setOpaque(false);
        options.add(rb3);

        JRadioButton rb4 = new JRadioButton("Grad 4: Regions");
        rb4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            aiBlack = AIType.AI_REGION;
            eventStack.push(new ErrorBean(
                "Schwierigkeit auf Regions gesetzt",
                ErrorDisplayType.INLINE));
            }
        });
        rb4.setBounds(80, 200, 160, 23);
        rb4.setOpaque(false);
        options.add(rb4);

        JRadioButton rb5 = new JRadioButton("Grad 5: Frontiers");
        rb5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            aiBlack = AIType.AI_FROMTIERS;
            eventStack.push(new ErrorBean(
                "Schwierigkeit auf Frontiers gesetzt",
                ErrorDisplayType.INLINE));
            }
        });
        rb5.setOpaque(false);
        rb5.setBounds(80, 240, 160, 23);
        options.add(rb5);

        JRadioButton rb6 = new JRadioButton("Grad 6: Stable Disk's");
        rb6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            aiBlack = AIType.AI_STABLE;
            eventStack.push(new ErrorBean(
                "Schwierigkeit auf Stable Disk's gesetzt",
                ErrorDisplayType.INLINE));
            }
        });
        rb6.setBounds(80, 280, 200, 23);
        rb6.setOpaque(false);
        options.add(rb6);

        JRadioButton rb7 = new JRadioButton("Grad 7: Breaking Groups");
        rb7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            aiBlack = AIType.AI_GROUP;
            eventStack.push(new ErrorBean(
                "Schwierigkeit auf Breaking Groups gesetzt",
                ErrorDisplayType.INLINE));
            }
        });
        rb7.setBounds(80, 320, 200, 23);
        rb7.setOpaque(false);
        options.add(rb7);

        JRadioButton rb8 = new JRadioButton("Evaporation");
        rb8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aiBlack = AIType.AI_EVAPORATION;
                eventStack.push(new ErrorBean(
                        "Schwierigkeit auf Evaporation gesetzt",
                        ErrorDisplayType.INLINE));
            }
        });
        rb8.setBounds(80, 360, 200, 23);
        rb8.setOpaque(false);
        options.add(rb8);

        JRadioButton rb9 = new JRadioButton("Adaptiv");
        rb9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aiBlack = AIType.AI_ADAPTIV;
                eventStack.push(new ErrorBean(
                        "Schwierigkeit auf Adaptiv gesetzt",
                        ErrorDisplayType.INLINE));
            }
        });
        rb9.setBounds(80, 400, 200, 23);
        rb9.setOpaque(false);
        options.add(rb9);

        ButtonGroup g1 = new ButtonGroup();
        g1.add(rb1);
        g1.add(rb2);
        g1.add(rb3);
        g1.add(rb4);
        g1.add(rb5);
        g1.add(rb6);
        g1.add(rb7);
        g1.add(rb8);
        g1.add(rb9);
        
        JCheckBox chckbxSpeedgaming = new JCheckBox("Speedgaming");
        chckbxSpeedgaming.setOpaque(false);
		chckbxSpeedgaming.setBounds(319, 217, 160, 23);
		chckbxSpeedgaming.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                isSpeedGame = (isSpeedGame == false) ? true : false;
			}
		});
		options.add(chckbxSpeedgaming);
		
        

        JButton btnBack2 = new JButton("Back");
        btnBack2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ((CardLayout) frame.getContentPane().getLayout()).show(
                        frame.getContentPane(), "Startseite");
                frame.setTitle("Startseite");

            }
        });
        btnBack2.setBounds(10, 465, 89, 23);
        options.add(btnBack2);

        JButton btnnext = new JButton("Spiel starten");
        btnnext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {




                startSinglePlayer();

                ((CardLayout) frame.getContentPane().getLayout()).show(
                        frame.getContentPane(), "Spielfeld");
                frame.setTitle("Spielfeld");


                while (gameBean == null) {

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        });

        btnnext.setBounds(508, 465, 150, 23);
        options.add(btnnext);

        Background3 = new JLabel(new ImageIcon(
                "src/at/aau/reversi/gui/images/Backgroundfield.png"));
        Background3.setBounds(40,40, 480, 400);
        options.add(Background3);

        Background = new JLabel(new ImageIcon(
                "src/at/aau/reversi/gui/images/background1.png"));
        Background.setBounds(0, 0, 700, 600);
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

        JButton btnExit3 = new JButton("Exit");
        btnExit3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnExit3.setBounds(568, 465, 89, 23);
        rules.add(btnExit3);

        Background = new JLabel(new ImageIcon("src/at/aau/reversi/gui/images/Spielregeln.png"));
        Background.setBounds(0, -43, 700, 600);
        rules.add(Background);

        //SPIELREGELN ENDE

        // Auswahl des Multiplayer

        JPanel multiplayer_site = new JPanel();
        frame.getContentPane().add(multiplayer_site, "Multiplayer Auswahl");
        multiplayer_site.setLayout(null);


        JButton SpielerVsSpielerNT = new JButton("Spieler vs Spieler ( Netzwerk )");
        SpielerVsSpielerNT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                boolean lokal = true;

                ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Netzwerkauswahl");
                frame.setTitle("Netzwerkauswahl");

            }
        });
        SpielerVsSpielerNT.setBounds(193, 153, 288, 87);
        multiplayer_site.add(SpielerVsSpielerNT);

        JButton SpielerVsSpielderLokal = new JButton("Spieler vs Spieler ( Hot Seat )");
        SpielerVsSpielderLokal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                boolean NT = true;

                ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Spielfeld");
                frame.setTitle("Spielfeld");

                startMultiPlayer();

            }
        });
        SpielerVsSpielderLokal.setBounds(193, 276, 288, 87);
        multiplayer_site.add(SpielerVsSpielderLokal);

        JButton btnBack_1 = new JButton("Back");
        btnBack_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Spielauswahl");
                frame.setTitle("Spielauswahl");
            }
        });
        btnBack_1.setBounds(10, 465, 89, 23);
        multiplayer_site.add(btnBack_1);

        JButton btnExit4 = new JButton("Exit");
        btnExit4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnExit4.setBounds(568, 465, 89, 23);
        multiplayer_site.add(btnExit4);

        Background = new JLabel(new ImageIcon("src/at/aau/reversi/gui/images/background1.png"));
        Background.setBounds(0, 0, 700, 600);
        multiplayer_site.add(Background);

        // Auswahl des Multiplayer ENDE

        // Netzwerkauswahl

        JPanel NT_Site = new JPanel();
        frame.getContentPane().add(NT_Site, "Netzwerkauswahl");
        NT_Site.setLayout(null);

        JButton Server = new JButton("Server");
        Server.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Server Seite");
                frame.setTitle("Server Seite");

                new StartServerGameThread().start();



            }
        });
        Server.setBounds(193, 153, 288, 87);
        NT_Site.add(Server);

        JButton Client = new JButton("Client");
        Client.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Client Seite");
                frame.setTitle("Client Seite");

                //startGameAsClient();

                // Todo: listener

//                ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Spielfeld");
//                frame.setTitle("Spiel als Client");

            }
        });

        Client.setBounds(193, 276, 288, 87);
        NT_Site.add(Client);



        JButton btnBack_2 = new JButton("Back");
        btnBack_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Multiplayer Auswahl");
                frame.setTitle("Spielauswahl");
            }
        });


        btnBack_2.setBounds(10, 465, 89, 23);
        NT_Site.add(btnBack_2);

        JButton btnExit5 = new JButton("Exit");
        btnExit5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit( 0 );
            }
        });
        btnExit4.setBounds(568, 465, 89, 23);
        NT_Site.add(btnExit4);

        Background = new JLabel(new ImageIcon("src/at/aau/reversi/gui/images/background1.png"));
        Background.setBounds(0, 0, 700, 600);
        NT_Site.add(Background);

        // Ende Netzwerkauswahl



        // Server Seite

        JPanel Server_Site = new JPanel();
        frame.getContentPane().add(Server_Site, "Server Seite");
        Server_Site.setLayout(null);

        txtMeineIpAdresse = new JTextField();
        txtMeineIpAdresse.setText("Meine IP Adresse :");
        txtMeineIpAdresse.setBounds(196, 226, 211, 20);
        Server_Site.add(txtMeineIpAdresse);
        txtMeineIpAdresse.setColumns(10);
        txtMeineIpAdresse.setBorder(null);
        txtMeineIpAdresse.setOpaque(false);
        txtMeineIpAdresse.setBorder(javax.swing.BorderFactory
                .createEmptyBorder());
        Font ipout = new Font("Verdana", Font.BOLD, 16);
        txtMeineIpAdresse.setFont(ipout);
        txtMeineIpAdresse.setForeground(Color.BLACK);
        txtMeineIpAdresse.setHorizontalAlignment(JTextField.CENTER);

        /*txtWarteAufVerbindung = new JTextField();
        txtWarteAufVerbindung.setText("warte auf verbindung !");
        txtWarteAufVerbindung.setBounds(196, 392, 271, 27);
        Server_Site.add(txtWarteAufVerbindung);
        txtWarteAufVerbindung.setColumns(14);
        txtWarteAufVerbindung.setBorder(null);
        txtWarteAufVerbindung.setOpaque(false);
        txtMeineIpAdresse.setBorder(javax.swing.BorderFactory
                .createEmptyBorder());
        Font Serverout = new Font("Verdana", Font.BOLD, 16);
        txtWarteAufVerbindung.setFont(Serverout);
        txtWarteAufVerbindung.setForeground(Color.BLACK);
        txtWarteAufVerbindung.setHorizontalAlignment(JTextField.CENTER);
         */
		
		 JLabel loadb = new JLabel("Ladebalken"); loadb = new JLabel(new
		 ImageIcon("src/at/aau/reversi/gui/images/Ladebalken2.gif"));
		 loadb.setBounds(126, 392, 400, 35); 
		 Server_Site.add(loadb);
		 

        JButton btnBackS = new JButton("Back");
        btnBackS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ((CardLayout) frame.getContentPane().getLayout()).show(
                        frame.getContentPane(), "Netzwerkauswahl");
                frame.setTitle("Startseite");
            }
        });
        btnBackS.setBounds(10, 465, 89, 23);
        Server_Site.add(btnBackS);

        JButton btnExit6 = new JButton("Exit");
        btnExit6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnExit6.setBounds(568, 465, 89, 23);
        Server_Site.add(btnExit6);

        Enumeration ifaces = null;
        try {
            ifaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        while (ifaces.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface) ifaces.nextElement();



            Enumeration addrs = ni.getInetAddresses();

            while (addrs.hasMoreElements()) {

                InetAddress ia = (InetAddress) addrs.nextElement();



                if (Pattern.matches(".*:.*", ia.getHostAddress()) == false
                        && !ia.getHostAddress().equals("127.0.0.1")) {

                    // ipTextFieldout.setAddress(ia.getHostAddress());
                    // System.out.println(ia.getHostAddress());

                    Ip_output = new JTextField();
                    Ip_output.setText(ia.getHostAddress());
                    Ip_output.setBounds(196, 272, 271, 27);
                    Server_Site.add(Ip_output);
                    Ip_output.setColumns(10);
                    Ip_output.setBorder(null);
                    Ip_output.setOpaque(false);
                    Ip_output.setBorder(javax.swing.BorderFactory
                            .createEmptyBorder());
                    Font ip_out = new Font("Verdana", Font.BOLD, 16);
                    Ip_output.setFont(ip_out);
                    Ip_output.setForeground(Color.BLACK);
                    Ip_output.setHorizontalAlignment(JTextField.CENTER);

                }
            }
        }
        Background2 = new JLabel(new ImageIcon(
                "src/at/aau/reversi/gui/images/Backgroundfield.png"));
        Background2.setBounds(185, 208, 310, 120);
        Server_Site.add(Background2);

        Background = new JLabel(new ImageIcon(
                "src/at/aau/reversi/gui/images/background1.png"));
        Background.setBounds(0, 0, 700, 600);
        Server_Site.add(Background);

        // Ende Server Seite

        // CLient Seite

        JPanel Client_Site = new JPanel();
        frame.getContentPane().add(Client_Site, "Client Seite");
        Client_Site.setLayout(null);

        ipTextFieldin = new JIpTextField(
                JIpTextField.IPVersion.IPV4);
        ipTextFieldin.setBounds(196, 272, 271, 27);
        String ip = ipTextFieldin.getText();
        Client_Site.add(ipTextFieldin);

        txtMeineIpAdresse = new JTextField();
        txtMeineIpAdresse.setText("Ip Adresse eintragen :");
        txtMeineIpAdresse.setBounds(196, 226, 211, 20);
        Client_Site.add(txtMeineIpAdresse);
        txtMeineIpAdresse.setColumns(10);
        txtMeineIpAdresse.setBorder(null);
        txtMeineIpAdresse.setOpaque(false);
        txtMeineIpAdresse.setBorder(javax.swing.BorderFactory
                .createEmptyBorder());
        Font ipoutc = new Font("Verdana", Font.BOLD, 16);
        txtMeineIpAdresse.setFont(ipoutc);
        txtMeineIpAdresse.setForeground(Color.BLACK);
        txtMeineIpAdresse.setHorizontalAlignment(JTextField.CENTER);

        JButton connect = new JButton(" Verbinden ");
        connect.setBounds(245, 420, 201, 40);
        connect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                startGameAsClient();
            }
        });
        Client_Site.add(connect);

        JButton btnBackC = new JButton("Back");
        btnBackC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ((CardLayout) frame.getContentPane().getLayout()).show(
                        frame.getContentPane(), "Netzwerkauswahl");
                frame.setTitle("Startseite");
            }
        });
        btnBackC.setBounds(10, 465, 89, 23);
        Client_Site.add(btnBackC);

        JButton btnExit7 = new JButton("Exit");
        btnExit7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnExit7.setBounds(568, 465, 89, 23);
        Client_Site.add(btnExit7);

        Background1 = new JLabel(new ImageIcon(
                "src/at/aau/reversi/gui/images/Backgroundfield.png"));
        Background1.setBounds(185, 208, 310, 120);
        Client_Site.add(Background1);

        Background = new JLabel(new ImageIcon(
                "src/at/aau/reversi/gui/images/background1.png"));
        Background.setBounds(0, 0, 700, 600);
        Client_Site.add(Background);

        // Ende Client Seite

        // SPIELFELD ANFANG

        JPanel play_site = new JPanel();
        frame.getContentPane().add(play_site, "Spielfeld");
        play_site.setBackground(Color.WHITE);
        play_site.setBorder(new EmptyBorder(5, 5, 5, 5));
        play_site.setLayout(null);
        JPanel Field = new Draw_Game_Field();


        Field.addMouseListener(new MouseAdapter() {

            int posx;
            int posy;

            @Override
            public void mouseClicked(MouseEvent e) {

                posx = 0;
                posy = 0;

                int x = e.getX();
                int y = e.getY();

                if (x < 50) {

                    posx = 0;
                } else if (x > 51 && x < 100) {

                    posx = 1;
                } else if (x > 101 && x < 150) {

                    posx = 2;
                } else if (x > 151 && x < 200) {

                    posx = 3;
                } else if (x > 201 && x < 250) {

                    posx = 4;
                } else if (x > 251 && x < 300) {

                    posx = 5;

                } else if (x > 301 && x < 350) {

                    posx = 6;
                } else {

                    posx = 7;
                }

                if (y < 50) {

                    posy = 0;
                } else if (y > 51 && y < 100) {

                    posy = 1;
                } else if (y > 101 && y < 150) {

                    posy = 2;
                } else if (y > 151 && y < 200) {

                    posy = 3;
                } else if (y > 201 && y < 250) {

                    posy = 4;
                } else if (y > 251 && y < 300) {

                    posy = 5;
                } else if (y > 301 && y < 350) {

                    posy = 6;

                } else {
                    posy = 7;
                }

                handleClick(new Move((short) posx, (short) posy));
            }
        });
        Field.setBackground(Color.WHITE);
        Field.setBounds(40, 60, 400, 400);
        Field.setOpaque(false);
        play_site.add(Field);

        numw = new JTextField();
        numw.setEditable(false);
        numw.setBounds(561, 77, 50, 30);
        play_site.add(numw);
        numw.setColumns(10);
        numw.setOpaque(false);
        numw.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        Font fontw = new Font("Verdana", Font.BOLD, 16);
        numw.setFont(fontw);
        numw.setForeground(Color.BLACK);
        numw.setHorizontalAlignment(JTextField.CENTER);

        numb = new JTextField();
        numb.setEditable(false);
        numb.setBounds(561, 196, 50, 30);
        play_site.add(numb);
        numb.setColumns(10);
        numb.setOpaque(false);
        numb.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        Font fontb = new Font("Verdana", Font.BOLD, 16);
        numb.setFont(fontb);
        numb.setForeground(Color.BLACK);
        numb.setHorizontalAlignment(JTextField.CENTER);


        rule_output = new JTextField();
        rule_output.setEditable(false);
        rule_output.setBounds(40, 481, 400, 27);
        play_site.add(rule_output);
        rule_output.setColumns(10);

        txtA = new JTextField();
        txtA.setEditable(false);
        txtA.setHorizontalAlignment(SwingConstants.CENTER);
        txtA.setBorder(null);
        txtA.setColumns(10);
        txtA.setOpaque(false);
        txtA.setText(" A");
        txtA.setBounds(40, 42, 50, 20);
        play_site.add(txtA);

        txtB = new JTextField();
        txtB.setEditable(false);
        txtB.setText(" B");
        txtB.setOpaque(false);
        txtB.setHorizontalAlignment(SwingConstants.CENTER);
        txtB.setColumns(10);
        txtB.setBorder(null);
        txtB.setBounds(89, 42, 50, 20);
        play_site.add(txtB);

        txtC = new JTextField();
        txtC.setEditable(false);
        txtC.setText(" C");
        txtC.setOpaque(false);
        txtC.setHorizontalAlignment(SwingConstants.CENTER);
        txtC.setColumns(10);
        txtC.setBorder(null);
        txtC.setBounds(140, 42, 50, 20);
        play_site.add(txtC);

        txtD = new JTextField();
        txtD.setEditable(false);
        txtD.setText(" D");
        txtD.setOpaque(false);
        txtD.setHorizontalAlignment(SwingConstants.CENTER);
        txtD.setColumns(10);
        txtD.setBorder(null);
        txtD.setBounds(191, 42, 50, 20);
        play_site.add(txtD);

        txtE = new JTextField();
        txtE.setEditable(false);
        txtE.setText(" E");
        txtE.setOpaque(false);
        txtE.setHorizontalAlignment(SwingConstants.CENTER);
        txtE.setColumns(10);
        txtE.setBorder(null);
        txtE.setBounds(241, 42, 50, 20);
        play_site.add(txtE);

        txtF = new JTextField();
        txtF.setEditable(false);
        txtF.setText(" F");
        txtF.setOpaque(false);
        txtF.setHorizontalAlignment(SwingConstants.CENTER);
        txtF.setColumns(10);
        txtF.setBorder(null);
        txtF.setBounds(290, 42, 50, 20);
        play_site.add(txtF);

        txtG = new JTextField();
        txtG.setEditable(false);
        txtG.setText(" G");
        txtG.setOpaque(false);
        txtG.setHorizontalAlignment(SwingConstants.CENTER);
        txtG.setColumns(10);
        txtG.setBorder(null);
        txtG.setBounds(340, 42, 50, 20);
        play_site.add(txtG);

        txtH = new JTextField();
        txtH.setEditable(false);
        txtH.setText(" H");
        txtH.setOpaque(false);
        txtH.setHorizontalAlignment(SwingConstants.CENTER);
        txtH.setColumns(10);
        txtH.setBorder(null);
        txtH.setBounds(390, 42, 50, 20);
        play_site.add(txtH);

        textField = new JTextField();
        textField.setEditable(false);
        textField.setBorder(null);
        textField.setOpaque(false);
        textField.setText("1");
        textField.setBounds(30, 60, 20, 50);
        play_site.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setEditable(false);
        textField_1.setText("2");
        textField_1.setOpaque(false);
        textField_1.setColumns(10);
        textField_1.setBorder(null);
        textField_1.setBounds(30, 110, 20, 50);
        play_site.add(textField_1);

        textField_2 = new JTextField();
        textField_2.setEditable(false);
        textField_2.setText("3");
        textField_2.setOpaque(false);
        textField_2.setColumns(10);
        textField_2.setBorder(null);
        textField_2.setBounds(30, 161, 20, 50);
        play_site.add(textField_2);

        textField_3 = new JTextField();
        textField_3.setEditable(false);
        textField_3.setText("4");
        textField_3.setOpaque(false);
        textField_3.setColumns(10);
        textField_3.setBorder(null);
        textField_3.setBounds(30, 211, 20, 50);
        play_site.add(textField_3);

        textField_4 = new JTextField();
        textField_4.setEditable(false);
        textField_4.setText("5");
        textField_4.setOpaque(false);
        textField_4.setColumns(10);
        textField_4.setBorder(null);
        textField_4.setBounds(30, 260, 20, 50);
        play_site.add(textField_4);

        textField_5 = new JTextField();
        textField_5.setEditable(false);
        textField_5.setText("6");
        textField_5.setOpaque(false);
        textField_5.setColumns(10);
        textField_5.setBorder(null);
        textField_5.setBounds(30, 310, 20, 50);
        play_site.add(textField_5);

        textField_6 = new JTextField();
        textField_6.setEditable(false);
        textField_6.setText("7");
        textField_6.setOpaque(false);
        textField_6.setColumns(10);
        textField_6.setBorder(null);
        textField_6.setBounds(30, 360, 20, 50);
        play_site.add(textField_6);

        textField_7 = new JTextField();
        textField_7.setEditable(false);
        textField_7.setText("8");
        textField_7.setOpaque(false);
        textField_7.setColumns(10);
        textField_7.setBorder(null);
        textField_7.setBounds(30, 410, 20, 50);
        play_site.add(textField_7);

        JLabel black_stone = new JLabel("numb_image");
        black_stone.setIcon(new ImageIcon("src/at/aau/reversi/gui/images/white.png"));
        black_stone.setBounds(509, 67, 50, 50);
        play_site.add(black_stone);

        JLabel white_stone = new JLabel("numw_image");
        white_stone.setIcon(new ImageIcon("src/at/aau/reversi/gui/images/black.png"));
        white_stone.setBounds(509, 186, 50, 50);
        play_site.add(white_stone);

        Backgroundfield = new JLabel(new ImageIcon("src/at/aau/reversi/gui/images/Backgroundfield.png"));
        Backgroundfield.setBounds(23, 41, 434, 438);
        play_site.add(Backgroundfield);

        Background = new JLabel(new ImageIcon("src/at/aau/reversi/gui/images/background1.png"));
        Background.setBounds(0, 0, 700, 600);
        play_site.add(Background);

        gameFieldPanel = (Draw_Game_Field) Field;


        // SPIELFELD ENDE

    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        eventStack.push(arg);
    }

    public JFrame getFrame() {
        return frame;
    }

    private void startSinglePlayer() {
        controller.startGame(PlayerType.HUMAN_PLAYER, PlayerType.AI, AIType.AI_RANDOM, aiBlack, false, isSpeedGame);
    }

    private void startMultiPlayer() {
        controller.startGame(PlayerType.HUMAN_PLAYER, PlayerType.HUMAN_PLAYER, AIType.AI_RANDOM, AIType.AI_RANDOM, false, false);
    }

    private void handleClick(Move m) {
        new HandleClickThread(m).start();
    }

    private void startGameAsServer(){
        controller.startGame(PlayerType.HUMAN_PLAYER, PlayerType.NETWORK, AIType.AI_RANDOM, AIType.AI_RANDOM, true, false);
    }

    private void startGameAsClient(){
        try {

            InetAddress address;

            //Wenn keine IP ausgewaehlt wurde, dann localhost
            if(ipTextFieldin.getIpAddressString().equals("...")){
                address = InetAddress.getByName("localhost");
            }else{
                address = ipTextFieldin.getIpAddress();
            }

            controller.setServerAddress(address);
            controller.startGame(PlayerType.NETWORK, PlayerType.HUMAN_PLAYER, AIType.AI_RANDOM, AIType.AI_RANDOM, false, false);
        } catch (UnknownHostException e) {
            eventStack.push(new ErrorBean("Ungültige Server-Adresse", ErrorDisplayType.POPUP));
        }

    }

    @Override
    public void run() {

        while (isRunning) {


            if(!eventStack.isEmpty()){

                Object o;
                synchronized (this){
                    o = eventStack.pop();
                }

                if (o instanceof GameBean) {

                    ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), frame.getTitle());

                    this.gameBean = (GameBean) o;
                    gameFieldPanel.updateGameField(gameBean);

                    String message;
                    if (gameBean.getCurrentPlayer().equals(Player.WHITE)) {
                        message = "Weiss ist am Zug";
                    } else {
                        message = "Schwarz ist am Zug";
                    }
                    rule_output.setText(message);
                    numw.setText("" + gameBean.getWhite());
                    numb.setText("" + gameBean.getBlack());


                } else if (o instanceof ErrorBean) {

                    ErrorBean errorBean = (ErrorBean) o;
                    if (errorBean.getErrorDisplayType().equals(ErrorDisplayType.INLINE)) {
                        rule_output.setText(errorBean.getErrorMessage());
                    } else if(errorBean.getErrorDisplayType().equals(ErrorDisplayType.NETWORK)){
                        String array[] = new String[2];
                        array[0] = "Auf KI umstellen";
                        array[1] = "Spiel beenden";

                        int result = JOptionPane.showOptionDialog(this, errorBean.getErrorMessage(), "Netzwerkfehler",JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, array, null);

                        if(result == 1){
                            ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Startseite");
                            frame.setTitle("Startseite");
                        }else if(result == 0){

                            controller.setKIAfterConnectionLoss();
                        }

                    }else if(errorBean.getErrorDisplayType().equals(ErrorDisplayType.PROGRAM_FLOW)){

                        ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Spielfeld");
                        frame.setTitle("Netzwerkspiel");

                    }else {
                        JOptionPane.showMessageDialog(this, errorBean.getErrorMessage());
                    }

                }
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);

    }


    class HandleClickThread extends Thread {

        Move m;

        HandleClickThread(Move m) {
            this.m = m;
        }

        @Override
        public void run() {

            controller.fieldClicked(gameBean.getCurrentPlayer(), m.getxCoord(), m.getyCoord());

        }
    }

    class StartServerGameThread extends Thread{

        @Override
        public void run() {

            startGameAsServer();

        }
    }

    public void testStartSinglePlayer(){
        startSinglePlayer();

        while (gameBean == null) {

            try {
                Thread.sleep(50);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

        }
        ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Spielfeld");
        frame.setTitle("Spielfeld");
    }
    public void getTipp() {
        controller.getTipp();
    }
}
